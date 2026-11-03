"""이미지 감시: 버킷 서명 오류(403)면 homepage-be를 재배포하고 알린다. Railway cron으로 15분마다 실행."""
import json, os, re, sys, urllib.request, urllib.error
from datetime import datetime, timezone, timedelta

CHECK_URL = os.environ.get("CHECK_URL", "https://api.farmsystem.kr/api/s3/files/award/award_1.png")
CRED_ERRORS = {"SignatureDoesNotMatch", "InvalidAccessKeyId"}  # 키가 바뀌어 앱이 옛 키로 서명할 때 나는 오류
COOLDOWN = timedelta(minutes=60)  # 재배포 직후 반복 재배포 방지
GQL = "https://backboard.railway.com/graphql/v2"


def classify(status, body):
    """(상태, 응답 본문) → 'ok' | 'cred' | 'other'"""
    if status == 200:
        return "ok"
    code = re.search(r"<Code>([^<]+)</Code>", body or "")
    return "cred" if status == 403 and code and code.group(1) in CRED_ERRORS else "other"


def fetch(url):
    try:
        with urllib.request.urlopen(urllib.request.Request(url, headers={"User-Agent": "farm-image-watchdog"}), timeout=20) as r:
            return r.status, ""
    except urllib.error.HTTPError as e:
        return e.code, e.read(2000).decode("utf-8", "replace")
    except Exception as e:  # 연결 실패 등
        return 0, str(e)


def gql(query):
    req = urllib.request.Request(GQL, json.dumps({"query": query}).encode(), {
        "Content-Type": "application/json", "Project-Access-Token": os.environ["RAILWAY_PROJECT_TOKEN"]})
    with urllib.request.urlopen(req, timeout=30) as r:
        data = json.load(r)
    if data.get("errors"):
        raise RuntimeError(data["errors"])
    return data["data"]


def notify(msg):
    print(msg)
    hook = os.environ.get("WEBHOOK_URL")
    if hook:  # Discord(content) / Slack(text) 둘 다 받도록
        try:
            urllib.request.urlopen(urllib.request.Request(hook, json.dumps({"content": msg, "text": msg}).encode(),
                                                          {"Content-Type": "application/json"}), timeout=15)
        except Exception as e:
            print("webhook 실패:", e)


def main():
    status, body = fetch(CHECK_URL)
    kind = classify(status, body)
    if kind == "ok":
        print("OK", status)
        return 0
    if kind == "other":
        notify(f"⚠️ [farm image-watchdog] 이미지 확인 실패 (HTTP {status}) — 키 오류가 아니라 자동 재배포하지 않음: {body[:200]}")
        return 1
    svc, env = os.environ["TARGET_SERVICE_ID"], os.environ["TARGET_ENVIRONMENT_ID"]
    last = gql(f'{{ deployments(first: 1, input: {{ serviceId: "{svc}", environmentId: "{env}" }}) {{ edges {{ node {{ createdAt }} }} }} }}')
    edges = last["deployments"]["edges"]
    if edges:
        created = datetime.fromisoformat(edges[0]["node"]["createdAt"].replace("Z", "+00:00"))
        if datetime.now(timezone.utc) - created < COOLDOWN:
            notify(f"⚠️ [farm image-watchdog] 버킷 서명 오류가 계속됨 — 최근 배포({created:%m-%d %H:%M} UTC) 후 60분 이내라 재배포 보류, 확인 필요")
            return 1
    gql(f'mutation {{ serviceInstanceRedeploy(serviceId: "{svc}", environmentId: "{env}") }}')
    notify("🔄 [farm image-watchdog] 버킷 서명 오류(403) 감지 → homepage-be 자동 재배포 시작")
    return 1  # 문제가 있었다는 기록을 남기려고 실패로 종료


def selftest():
    assert classify(200, "") == "ok"
    assert classify(403, "<Error><Code>SignatureDoesNotMatch</Code></Error>") == "cred"
    assert classify(403, "<Error><Code>InvalidAccessKeyId</Code></Error>") == "cred"
    assert classify(403, "<Error><Code>AccessDenied</Code></Error>") == "other"
    assert classify(404, "<Error><Code>NoSuchKey</Code></Error>") == "other"
    assert classify(0, "timed out") == "other"
    print("selftest ok")


if __name__ == "__main__":
    selftest() if "--selftest" in sys.argv else sys.exit(main())
