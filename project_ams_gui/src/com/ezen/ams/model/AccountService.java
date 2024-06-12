package com.ezen.ams.model;

import com.ezen.ams.exception.InsufficientException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.TreeMap;

/**
 * TreeMap 콜렉션을 이용한 계좌목록 관리
 */
public class AccountService {

    private TreeMap<String, Account> accounts;

    public AccountService() {
        accounts = new TreeMap<>();
    }

    public Collection<Account> getAccounts() {
        return accounts.values();
    }

    public int getCount() {
        return accounts.size();
    }

    // 계좌 생성
    public void addAccount(Account account) {
        accounts.put(account.getAccountNum(), account);
    }

    // 입금
    public long depositAccount(String accountNum, long money) throws InsufficientException {
        if (money < 1000) {
            throw new InsufficientException("입금하고자 하는 금액이 1,000원 미만이거나 음수일 수 없습니다.", 100);
        }

        Account findAccount = accounts.get(accountNum);
        if (findAccount == null) {
            throw new InsufficientException("입금하고자 하는 계좌가 존재하지 않습니다.", 200);
        }
        return findAccount.deposit(money);
    }

    // 출금
    public long withdrawAccount(String accountNum, long money) throws InsufficientException {
        if (money < 1000) {
            throw new InsufficientException("출금하고자 하는 금액이 1,000원 미만이거나 음수일 수 없습니다.", 110);
        }

        Account findAccount = accounts.get(accountNum);
        if (findAccount == null) {
            // 비즈니즈 예외 발생했기 때문에 강제 예외 발생
            throw new InsufficientException("출금하고자 하는 계좌가 존재 하지 않습니다.", 200);
        }

        if (findAccount.getBalance() < money) {
            throw new InsufficientException("출금 잔액이 부족합니다.", 230);

        }
        return findAccount.withdraw(money);
    }

    // 계좌번호로 계좌 검색
    public Account findAccount(String accountNum) {
        return accounts.get(accountNum);
    }

    // 예금주 이름으로 계좌 검색
    public List<Account> findAccountByAccountOwner(String accountOwner) {
        // 구현하세요...
        List<Account> findAccounts = new ArrayList<>();
        Collection<Account> values = accounts.values();
        for (Account account : values) {
            String owner = account.getAccountOwner();
            if (owner.equals(accountOwner)) {
                findAccounts.add(account);
            }
        }
        return findAccounts;
    }

    // 계좌 삭제
    public boolean removeAccount(String accountNum) {
        Account account = accounts.remove(accountNum);
        if (account != null) {
            return true;
        }
        return false;
    }

    //  테스트를 위한 main
    public static void main(String[] args) {

        AccountService accountService = new AccountService();
        accountService.addAccount(new Account("1111-2222", "가나다", 1111, 10000));
        accountService.addAccount(new Account("1111-3333", "라마바", 2222, 20000));
        accountService.addAccount(new MinusAccount("2222-1111", "김대출", 1111, 10000, 100000));

        Collection<Account> accounts = accountService.getAccounts();    // getAccounts() 리턴 타입이 컬렉션
        if (!accounts.isEmpty()) {
            for (Account account : accounts) {
                System.out.println(account);
            }
        }

        String findAccountNumber = "1111-2222";
        Account findAccount = accountService.findAccount(findAccountNumber);
        if (findAccount != null) {
            System.out.println("검색된 계좌 : " + findAccount.toString());
        } else System.out.println("검색 계좌 X");

    } // main end
}




