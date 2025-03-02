package org.farmsystem.homepage.domain.common.util;

import java.util.HashMap;
import java.util.Map;

public class JamoUtil {

    // 초성, 중성, 종성 배열
    private static final String[] INITIALS = {
            "ㄱ", "ㄲ", "ㄴ", "ㄷ", "ㄸ", "ㄹ", "ㅁ", "ㅂ", "ㅃ",
            "ㅅ", "ㅆ", "ㅇ", "ㅈ", "ㅉ", "ㅊ", "ㅋ", "ㅌ", "ㅍ", "ㅎ"
    };

    private static final String[] MEDIALS_SIMPLE = {
            "ㅏ", "ㅐ", "ㅑ", "ㅒ", "ㅓ", "ㅔ", "ㅕ", "ㅖ",
            "ㅗ", "ㅘ", "ㅙ", "ㅚ", "ㅛ", "ㅜ", "ㅝ", "ㅞ", "ㅟ", "ㅠ",
            "ㅡ", "ㅢ", "ㅣ"
    };

    private static final String[] FINALS_SIMPLE = {
            "", "ㄱ", "ㄲ", "ㄳ", "ㄴ", "ㄵ", "ㄶ", "ㄷ", "ㄹ", "ㄺ",
            "ㄻ", "ㄼ", "ㄽ", "ㄾ", "ㄿ", "ㅀ", "ㅁ", "ㅂ", "ㅄ", "ㅅ",
            "ㅆ", "ㅇ", "ㅈ", "ㅊ", "ㅋ", "ㅌ", "ㅍ", "ㅎ"
    };

    // 합용 중성을 구성하는 단일 자모 분해 매핑
    private static final Map<String, String[]> COMPOUND_MEDIALS = new HashMap<>();
    static {
        COMPOUND_MEDIALS.put("ㅘ", new String[]{"ㅗ", "ㅏ"});
        COMPOUND_MEDIALS.put("ㅙ", new String[]{"ㅗ", "ㅐ"});
        COMPOUND_MEDIALS.put("ㅚ", new String[]{"ㅗ", "ㅣ"});
        COMPOUND_MEDIALS.put("ㅝ", new String[]{"ㅜ", "ㅓ"});
        COMPOUND_MEDIALS.put("ㅞ", new String[]{"ㅜ", "ㅔ"});
        COMPOUND_MEDIALS.put("ㅟ", new String[]{"ㅜ", "ㅣ"});
        COMPOUND_MEDIALS.put("ㅢ", new String[]{"ㅡ", "ㅣ"});
    }

    // 합용 종성을 구성하는 단일 자모 분해 매핑
    private static final Map<String, String[]> COMPOUND_FINALS = new HashMap<>();
    static {
        COMPOUND_FINALS.put("ㄳ", new String[]{"ㄱ", "ㅅ"});
        COMPOUND_FINALS.put("ㄵ", new String[]{"ㄴ", "ㅈ"});
        COMPOUND_FINALS.put("ㄶ", new String[]{"ㄴ", "ㅎ"});
        COMPOUND_FINALS.put("ㄺ", new String[]{"ㄹ", "ㄱ"});
        COMPOUND_FINALS.put("ㄻ", new String[]{"ㄹ", "ㅁ"});
        COMPOUND_FINALS.put("ㄼ", new String[]{"ㄹ", "ㅂ"});
        COMPOUND_FINALS.put("ㄽ", new String[]{"ㄹ", "ㅅ"});
        COMPOUND_FINALS.put("ㄾ", new String[]{"ㄹ", "ㅌ"});
        COMPOUND_FINALS.put("ㄿ", new String[]{"ㄹ", "ㅍ"});
        COMPOUND_FINALS.put("ㅀ", new String[]{"ㄹ", "ㅎ"});
        COMPOUND_FINALS.put("ㅄ", new String[]{"ㅂ", "ㅅ"});
    }

    /**
     * 한글 문자열을 분해하여, 입력한 자모 순서대로 반환합니다.
     * 예) "뷁" -> "ㅂㅜㅔㄹㄱ" (만약 음절 분해 결과가 compound 중성/종성이면 그 구성 단일 자모들을 순서대로 반환)
     */
    public static String convertToJamo(String input) {
        if (input == null) return null;
        StringBuilder sb = new StringBuilder();
        for (char ch : input.toCharArray()) {
            if (ch >= 0xAC00 && ch <= 0xD7A3) { // 한글 음절 범위
                int syllableIndex = ch - 0xAC00;
                int initialIndex = syllableIndex / (21 * 28);
                int medialIndex = (syllableIndex % (21 * 28)) / 28;
                int finalIndex = syllableIndex % 28;

                // 초성 (항상 단일 자모)
                String initial = INITIALS[initialIndex];
                sb.append(initial);

                // 중성 처리 (단일 자모라면 그대로, 합용이면 매핑 사용)
                String medial = MEDIALS_SIMPLE[medialIndex];
                if (COMPOUND_MEDIALS.containsKey(medial)) {
                    for (String m : COMPOUND_MEDIALS.get(medial)) {
                        sb.append(m);
                    }
                } else {
                    sb.append(medial);
                }

                // 종성 처리 (존재할 경우)
                if (finalIndex != 0) {
                    String finalJamo = FINALS_SIMPLE[finalIndex];
                    if (COMPOUND_FINALS.containsKey(finalJamo)) {
                        for (String f : COMPOUND_FINALS.get(finalJamo)) {
                            sb.append(f);
                        }
                    } else {
                        sb.append(finalJamo);
                    }
                }
            } else {
                sb.append(ch);
            }
        }
        return sb.toString();
    }
}
