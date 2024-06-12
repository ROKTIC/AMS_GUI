package com.ezen.ams.model;

public enum SortType {
    ACCOUNT_NUMBER("계좌번호"),
    ACCOUNT_OWNER("예 금 주"),
    BALANCE("잔  액");

    private String name;
    
    SortType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
