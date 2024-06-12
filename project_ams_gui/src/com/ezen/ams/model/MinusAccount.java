package com.ezen.ams.model;

public class MinusAccount extends Account {

    private long borrowMoney;

    public MinusAccount(){ }

    public MinusAccount(String accountNum, String accountOwner, int passwd, long balance, long borrowMoney) {
        super(accountNum, accountOwner, passwd, balance);
        this.borrowMoney = borrowMoney;
    }

    public long getBorrowMoney() {
        return borrowMoney;
    }

    public void setBorrowMoney(long borrowMoney) {
        this.borrowMoney = borrowMoney;
    }

    @Override
    public long getBalance() {
        return super.getBalance() - borrowMoney ;
    }

    @Override
    public String toString() {
        return "MinusAccount{" +
                "borrowMoney=" + borrowMoney +
                "} " + super.toString();
    }
}
