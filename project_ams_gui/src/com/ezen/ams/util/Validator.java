package com.ezen.ams.util;

/**
 * 데이터 유효성 검증용 클래스 ( 유틸리티 클래스 )
 */
public class Validator {
    
    /**
     * 데이터 입력 여부 검증
     * @param value     검증하고자 하는 문자열
     * @return          값이 있을 경우 true, 없을 경우 false
     */
    public static boolean isText(String value) {
        if(value != null && value.trim().length() != 0){
            return true;
        }
        return false;
    }

    public static boolean isId(String inputId) { // 알파벳과 숫자는 true, 특수문자나 그 외는 false;

        if(inputId == null) return false;

        for (int i = 0; i < inputId.length(); i++) {
            char ch = inputId.charAt(i);
            if (!Character.isAlphabetic(ch) && !Character.isDigit(ch)){
                return false;
            }
        }
        return true;
    }

    public static boolean isNumber(String value) { // 입력값이 숫자인지 아닌지,
        for (int i = 0; i < value.length(); i++) {
            char ch = value.charAt(i);
            if (!Character.isDigit(ch)){
                return false;
            }
        }
        return true;
    }
}
