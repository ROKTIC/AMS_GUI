package com.ezen.ams.model;

/**
 * 일상생활의 은행계좌(객체)를 표현한 클래스(설계도)
 * 객체 추상화
 */
public class Account{
    public static String bankName = "EZEN Bank";
    public static final int MIN_BALANCE = 0;
    public static final int MAX_BALANCE = 1000000;

    private String accountNum;   // 계좌번호
    private String accountOwner; // 예금주명
    private int passwd;          // 비밀번호
    private long balance;        // 잔액

    public Account() { }

    public Account(String accountNum, String accountOwner, int passwd, long balance) {
        this.accountNum = accountNum;
        this.accountOwner = accountOwner;
        this.passwd = passwd;
        this.balance = balance;
    }
    
    // setter/getter 메소드
    public void setAccountNum(String accountNum) {
        this.accountNum = accountNum;
    }
    public void setAccountOwner(String accountOwner) {
        this.accountOwner = accountOwner;
    }
    public void setPasswd(int passwd) {
        this.passwd = passwd;
    }

    public void setBalance(long balance) {
        this.balance = balance;
    }

    public String getAccountNum() {
        return accountNum;
    }

    public String getAccountOwner() {
        return accountOwner;
    }

    public int getPasswd() {
        return passwd;
    }

    /**
     * 계좌 입금 처리
     * @param money 입금하고자 하는 금액
     * @return 입금 후 잔액 반환
     */
    public long deposit(long money) {
        balance += money;
        return balance;
    }

    // 출금 기능 정의
    public long withdraw(long money){
        balance -= money;
        return balance;
    }

    // 잔액 조회 기능 정의
    public long getBalance(){
        return balance;
    }

    // 비밀번호 확인 기능 정의
    public boolean checkPasswd(int passwd){
       return this.passwd == passwd;
    }

    @Override
    public String toString() {
        return "Account{" +
                "accountNum='" + accountNum + '\'' +
                ", accountOwner='" + accountOwner + '\'' +
                ", passwd=" + passwd +
                ", balance=" + balance +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if(!(o instanceof Account)) return false;
        return toString().equals(o.toString());
    }

    @Override
    public int hashCode() {
        return toString().hashCode();
    }
}
