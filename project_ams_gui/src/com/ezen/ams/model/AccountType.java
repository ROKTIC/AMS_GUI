package com.ezen.ams.model;

public enum AccountType {

    ACCOUNT("입출금"),
    MINUS_ACCOUNT("마이너스");

    private String name;

    private AccountType(String name){  // new 안되도록 private
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
