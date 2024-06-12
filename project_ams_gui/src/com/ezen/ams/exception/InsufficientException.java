package com.ezen.ams.exception;

/**
 * 비즈니스 관련 예외를 추상화 한 사용자 정의 예외 클래스
 *
 */
public class InsufficientException extends  Exception{
    private int messageCode;
    public InsufficientException(){
        super("잔액이 부족합니다.");
    }

    public InsufficientException(String message, int messageCode) {
        super(message);
        this.messageCode = messageCode;
    }

    public int getMessageCode() {
        return messageCode;
    }

    public void setMessageCode(int messageCode) {
        this.messageCode = messageCode;
    }

    @Override
    public String toString() {
        return "InsufficientException{" +
                "messageCode=" + messageCode +
                "} " + super.toString();
    }
}
