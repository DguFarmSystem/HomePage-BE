# image-watchdog

Railway cron 서비스(`image-watchdog`, 15분마다)로 실행되는 이미지 감시 스크립트입니다.

- `CHECK_URL`(기본: 수상 이미지 1장)을 `/api/s3/files/**`로 요청해 200인지 확인합니다.
- 버킷이 **서명 오류(403 `SignatureDoesNotMatch` / `InvalidAccessKeyId`)**를 주면 `homepage-be`를 자동 재배포합니다.
  - 원인: 버킷 자격증명이 재설정되면 실행 중인 앱은 옛 키로 서명합니다. Railway는 재배포 때만 새 값을 넣습니다.
  - 최근 60분 안에 배포가 있었다면 반복 재배포를 막기 위해 알림만 보냅니다.
- 그 밖의 오류는 재배포하지 않고 알림만 보냅니다. 문제가 있었던 실행은 실패(exit 1)로 끝나 Railway 실행 기록에 남습니다.

## 환경변수 (Railway `image-watchdog` 서비스)

| 이름 | 설명 |
|---|---|
| `RAILWAY_PROJECT_TOKEN` | 프로젝트 토큰 (Project Settings → Tokens, production 환경) |
| `TARGET_SERVICE_ID` / `TARGET_ENVIRONMENT_ID` | 재배포할 `homepage-be` 서비스·환경 ID |
| `CHECK_URL` | 확인할 이미지 주소 |
| `WEBHOOK_URL` (선택) | Discord/Slack 웹훅. 설정하면 알림을 보냄 |

## 배포·확인

```sh
cd ops/image-watchdog
railway up -s image-watchdog --ci          # 배포
python3 check.py --selftest                # 판정 로직 자체 점검
railway cron run --service image-watchdog --yes && railway cron list --service image-watchdog
```
