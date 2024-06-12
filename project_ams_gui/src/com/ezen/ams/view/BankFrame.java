package com.ezen.ams.view;

import com.ezen.ams.model.*;
import com.ezen.ams.util.Validator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

public class BankFrame extends Frame {
    private AccountService accountService;    //   private 접근 제한

    Choice accountChoice, sortAccountChoice;
    TextField accountNumTF, accountOwnerTF, passwdTF, depositMoneyTF, borrowMoneyTF;
    Button checkBtn, deleteBtn, searchBtn, checkAllBtn, newAccountBtn;
    TextArea listTA;
    Label accountKindLabel, accountNumLabel, accountOwnerLabel, passwdLabel, depositMoneyLabel, borrowMoneyLabel, accountListLabel
            ,sortInfoLabel;

    public BankFrame(String title) {
        super(title);
        accountService = new AccountService();  // 생성자로 초기화

        /** dummy 데이터 */
        accountService.addAccount(new Account("1111-1111", "dummy1", 1111, 30000));
        accountService.addAccount(new Account("1111-2222", "dummy2", 2222, 50000));
        accountService.addAccount(new Account("1111-3333", "dummy3", 3333, 10000));
        accountService.addAccount(new MinusAccount("9999-1111", "dummy1", 4444, 20000, 50000));
        accountService.addAccount(new MinusAccount("9999-2222", "dummy2", 5555, 10000, 150000));
        accountService.addAccount(new MinusAccount("9999-3333", "dummy3", 6666, 6000, 90000));

        accountKindLabel = new Label("계좌종류");
        accountChoice = new Choice();
//        accountChoice.add("입출금 계좌");
//        accountChoice.add("마이너스 계좌");
        accountChoice.add("---- ----");
        AccountType[] accountTypes = AccountType.values();
        for (AccountType accountType : accountTypes) {
//            accountChoice.add(accountType.toString());   // Enum 에서 영어 목록
            accountChoice.add(accountType.getName());  // Enum 에서 한글 목록
        }

        sortAccountChoice = new Choice();
        sortAccountChoice.add("-보 기-");
        SortType[] sortTypes = SortType.values();
        for (SortType sortType : sortTypes) {
            sortAccountChoice.add(sortType.getName());
        }

        accountNumLabel = new Label("계좌번호");
        accountNumTF = new TextField();
        accountOwnerLabel = new Label("예금주명");
        accountOwnerTF = new TextField();
        passwdLabel = new Label("비밀번호");
        passwdTF = new TextField();
        depositMoneyLabel = new Label("입금금액");
        depositMoneyTF = new TextField();
        borrowMoneyLabel = new Label("대출금액");
        borrowMoneyTF = new TextField();

        checkBtn = new Button("조 회");
        deleteBtn = new Button(" 삭 제");
        searchBtn = new Button("검 색");
        newAccountBtn = new Button("신규등록");
        newAccountBtn.setEnabled(false); // 실행 시 계좌 추가 버튼 비활성화를 디폴트로
        checkAllBtn = new Button("전체조회");
        accountListLabel = new Label("계좌목록");
        sortInfoLabel = new Label("정렬 조건>>");
        listTA = new TextArea();
//        unitLabel = new Label("(단위 : 원)");
        listTA.setBackground(new Color(240, 240, 240));
    }

    public AccountService getAccountService() {          // setter 는 필요 없음
        return accountService;
    }

    public void initComponents() {
        setLayout(null);
        accountKindLabel.setBounds(35, 45, 50, 30);
        accountChoice.setBounds(100, 50, 120, 30);
        accountNumLabel.setBounds(35, 75, 50, 30);
        accountNumTF.setBounds(100, 80, 200, 25);
        checkBtn.setBounds(310, 80, 70, 25);
        deleteBtn.setBounds(385, 80, 70, 25);
        accountOwnerLabel.setBounds(35, 110, 50, 30);
        accountOwnerTF.setBounds(100, 115, 200, 25);
        searchBtn.setBounds(310, 115, 70, 25);
        passwdLabel.setBounds(35, 145, 50, 30);
        passwdTF.setBounds(100, 150, 200, 25);
        depositMoneyLabel.setBounds(315, 145, 50, 30);
        depositMoneyTF.setBounds(385, 150, 200, 25);
        borrowMoneyLabel.setBounds(35, 180, 50, 30);
        borrowMoneyTF.setBounds(100, 185, 200, 25);
        newAccountBtn.setBounds(310, 185, 70, 25);
        checkAllBtn.setBounds(385, 185, 70, 25);
        accountListLabel.setBounds(35, 220, 50, 30);
        sortInfoLabel.setBounds(415,220,70,30);
        sortAccountChoice.setBounds(485, 220, 100, 30);
        listTA.setBounds(30, 250, 555, 170);

        add(accountKindLabel);
        add(accountChoice);
        add(accountNumLabel);
        add(accountNumTF);
        add(checkBtn);
        add(deleteBtn);
        add(accountOwnerLabel);
        add(accountOwnerTF);
        add(searchBtn);
        add(passwdLabel);
        add(passwdTF);
        add(depositMoneyLabel);
        add(depositMoneyTF);
        add(borrowMoneyLabel);
        add(borrowMoneyTF);
        add(newAccountBtn);
        add(checkAllBtn);
        add(accountListLabel);
        add(sortInfoLabel);
        add(sortAccountChoice);
        add(listTA);

        setEnable(false);
        printAccounts();
    }

    /**
     * 각각의 이벤트 소스에 이벤트 리스너 등록
     */
    public void eventRegister() {
//        종료 처리
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                exit();
            }
        });

//        계좌 종류 선택 이벤트 처리
        accountChoice.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    selectAccountType();
                }
            }
        });

        //    계좌번호를 이용한 조회 이벤트 처리
        checkBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                findByAccountNumber();
            }
        });

//          계좌번호를 이용한 삭제 버튼 이벤트 처리
        deleteBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteByAccountNumber();
            }
        });

//        예금주 명을 이용한 검색 이벤트 처리
        searchBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                findByAccountOwner();
            }
        });

        //        신규 계좌 등록 이벤트 처리
        newAccountBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openAccount();
            }
        });

//        전체 계좌 목록 이벤트 처리
        checkAllBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                printAccounts();
            }
        });

//        모든 텍스트 필드 포커스 이벤트 처리
        Component[] components = getComponents();
        for (Component component : components) {
            if (component instanceof TextField) {
                component.addFocusListener(new FocusAdapter() {
                    @Override
                    public void focusGained(FocusEvent e) {  // 포커스를 얻었을 때
                        TextField tf = (TextField) component;
                        tf.setText("");
                        tf.setForeground(Color.black);  // 포커스를 얻었을 때 글자색 다시 검정색으로..
                    }
                });
            }
        }

        //        정렬 종류 선택 이벤트 처리
        sortAccountChoice.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    selectSortType();
                }
            }
        });

    } // eventRegister End

    //     * 신규 계좌 등록 기능
    private void openAccount() {
        String accountNumber = null;
        String accountOwner = null;
        int password = 0;
        long restMoney = 0L;
        long borrowMoney = 0L;

        accountNumber = accountNumTF.getText();
        accountOwner = accountOwnerTF.getText();
        String inputPasswd = passwdTF.getText();
        String inputMoney = depositMoneyTF.getText();

//        boolean empty = false;
        // #1. 1차 검증 : 입력 필드에 값이 입력 안되었거나 또는 빈공백문자 입력 검증
        if (!Validator.isText(accountNumber)) {
            fieldError(accountNumTF, "필수입력 항목입니다.");
        }

        if (!Validator.isText(accountOwner)) {
            fieldError(accountOwnerTF, "필수입력 항목입니다.");
        }

        if (!Validator.isText(inputPasswd)) {
            fieldError(passwdTF, "필수입력 항목입니다.");
        }

        if (!Validator.isText(inputMoney)) {
            fieldError(depositMoneyTF, "필수입력 항목입니다.");
        }

        // #2. 2차 검증 : 유효한 입력 형식 검증
        if (!Validator.isNumber(inputPasswd)) {
            fieldError(passwdTF, "숫자형식이어야 합니다.");
        }

        if (!Validator.isNumber(inputMoney)) {
            fieldError(depositMoneyTF, "숫자형식이어야 합니다.");
        }

        password = Integer.parseInt(inputPasswd);
        restMoney = Long.parseLong(inputMoney);

        // 신규 계좌 생성
        Account newAccount = null;
        // 입출금 계좌인 경우
        if (accountChoice.getSelectedItem().equals(AccountType.valueOf("ACCOUNT").getName())) {
            newAccount = new Account(accountNumber, accountOwner, password, restMoney);
        } else if (accountChoice.getSelectedItem().equals(AccountType.valueOf("MINUS_ACCOUNT").getName())) {
            String inputBorrowMoney = borrowMoneyTF.getText();
            if (!Validator.isText(inputBorrowMoney)) {
                fieldError(borrowMoneyTF, "필수입력 항목입니다.");
            }

            if (!Validator.isNumber(inputBorrowMoney)) {
                fieldError(borrowMoneyTF, "숫자형식이어야 합니다.");
            }
            borrowMoney = Long.parseLong(inputBorrowMoney);
            newAccount = new MinusAccount(accountNumber, accountOwner, password, restMoney, borrowMoney);
        }

        accountService.addAccount(newAccount);
        // 사용자에게 신규등록 메시지 보여주기..
        showMessage("※ 신규계좌(" + newAccount.getAccountNum() + ")가 정상 등록되었습니다.");
        setClearField();
    }

    //    예금주 명으로 계좌 검색
    private void findByAccountOwner() {
        if (!accountOwnerTF.isEnabled()) {
            accountOwnerTF.setEnabled(true);
            accountOwnerTF.requestFocus();
        }

        String findAccountOwner = accountOwnerTF.getText();
        if (!Validator.isText(findAccountOwner)) {
            fieldError(accountOwnerTF, "예금주를 입력하세요");
        } else {
            List<Account> findAccounts = accountService.findAccountByAccountOwner(findAccountOwner);
            System.out.println(findAccounts);  // 데이터 체크 & 디버깅용 프린트문

            if (!findAccounts.isEmpty()) {  // List 가 비어있지 않았다면.
                listTA.setForeground(Color.black);
                listTA.setText("");
                printHeader();
                for (Account findAccount : findAccounts) {
                    String accountType = null;
                    String accountNumber = null;
                    String accountOwner = null;
                    long balance = 0L;
                    long borrowMoney = 0L;

                    if (findAccount instanceof MinusAccount) {
                        accountType = AccountType.valueOf("MINUS_ACCOUNT").getName();
                        borrowMoney = ((MinusAccount) findAccount).getBorrowMoney();
                    } else accountType = AccountType.valueOf("ACCOUNT").getName();

                    accountNumber = findAccount.getAccountNum();
                    accountOwner = findAccount.getAccountOwner();
                    balance = findAccount.getBalance();

                    printRow(accountType, accountNumber, accountOwner, balance, borrowMoney);
                }
            } else showErrorMessage(">> 예금주를 찾을 수 없습니다 ");
        }
    }

    //    계좌번호로 조회 버튼
    private void findByAccountNumber() {
        if (!accountNumTF.isEnabled()) {
            accountNumTF.setEnabled(true);
            accountNumTF.requestFocus();
        }

        String findAccountNumber = accountNumTF.getText();
        if (!Validator.isText(findAccountNumber)) {
            fieldError(accountNumTF, "검색 계좌 번호를 입력하세요");
            return;
        } else {
            Account findAccount = accountService.findAccount(findAccountNumber);

            if (findAccount != null) {
                listTA.setForeground(Color.black);
                listTA.setText("");
                printHeader();
                String accountType = null;
                long borrowMoney = 0L;
                if (findAccount instanceof MinusAccount) {
                    accountType = AccountType.valueOf("MINUS_ACCOUNT").getName();
                    borrowMoney = ((MinusAccount) findAccount).getBorrowMoney();
                } else {
                    accountType = AccountType.valueOf("ACCOUNT").getName();
                    borrowMoney = 0L;
                }

                printRow(accountType, findAccount.getAccountNum(), findAccount.getAccountOwner(), findAccount.getBalance(), borrowMoney);
            } else {
                showErrorMessage(">> 조회하고자 하는 계좌가 존재하지 않습니다 ");
            }
        }
    }

    //    삭제 버튼
    private void deleteByAccountNumber() {
        if (!accountNumTF.isEnabled()) {   // 삭제 버튼 클릭 시 계좌번호 TextField 활성화
            accountNumTF.setEnabled(true);
            accountNumTF.requestFocus();
        }
        String findAccountNumber = accountNumTF.getText(); // 텍스트 필드의 계좌번호를 받아서

        Account findAccount = accountService.findAccount(findAccountNumber);

        if (!Validator.isText(findAccountNumber)) {
            fieldError(accountNumTF, "검색 계좌 번호를 입력하세요");
            return;
        } else {
            if (findAccount != null) {
                if (findAccountNumber.equals(findAccount.getAccountNum())) {
                    accountService.removeAccount(findAccountNumber);
                    JOptionPane.showMessageDialog(null, findAccountNumber + " 계좌가 삭제 완료되었습니다.", "삭제 완료",
                            JOptionPane.INFORMATION_MESSAGE);  // swing 으로 삭제 완료에 대한 알림창 띄워주기
                    printHeader();
                    printAccounts();
                }
            } else showErrorMessage(">> 삭제하고자 하는 계좌가 없습니다.");
        }
    }

    /**
     * 유효성 검증 에러 메시지 출력
     *
     * @param tf     에러 메시지를 출력하고자 하는
     * @param errMsg
     */
    private void fieldError(TextField tf, String errMsg) {
        tf.setForeground(Color.RED);
        tf.setText(errMsg);
    }

    //        사용자 메시지 보여주기 기능
    private void showMessage(String message) {
        listTA.setForeground(Color.BLACK);
        listTA.setText(message);

    }

    //    오류 메시지 보여주기 기능
    private void showErrorMessage(String errorMessage) {
        listTA.setForeground(Color.RED);
        listTA.setText(errorMessage);
    }

    //        계좌 종류 선택 기능
    private void selectAccountType() {
        if (accountChoice.getSelectedIndex() == 0) {    // index 0 : = 계좌목록 선택 =
            setClearField(); // 카테고리 변경 시 모든 텍스트 필드 초기화
            setEnable(false);
            newAccountBtn.setEnabled(false);
        } else {
            String accountType = accountChoice.getSelectedItem();
            accountNumTF.setEnabled(true);
            accountOwnerTF.setEnabled(true);
            passwdTF.setEnabled(true);
            depositMoneyTF.setEnabled(true);

            newAccountBtn.setEnabled(true);

            if (accountType.equals(AccountType.valueOf("ACCOUNT").getName())) {
                setClearField(); // 카테고리 변경 시 모든 텍스트 필드 초기화
                borrowMoneyTF.setBackground(Color.LIGHT_GRAY);
                borrowMoneyTF.setEnabled(false);
            } else if (accountType.equals(AccountType.valueOf("MINUS_ACCOUNT").getName())) {
                setClearField(); // 카테고리 변경 시 모든 텍스트 필드 초기화
                borrowMoneyTF.setBackground(Color.white);
                borrowMoneyTF.setEnabled(true);
            }
        }
    }

    //    정렬 종류 선택 기능
    private void selectSortType() {
        int sortedAccount = sortAccountChoice.getSelectedIndex();
        if (sortedAccount == 0) {
            listTA.setText("");
        } else {
            Collection<Account> accountsList = accountService.getAccounts();
            List<Account> sortedAccountsList = new ArrayList<>(accountsList); // TreeMap 은 형태가 좀 달라서 바로 List 로 형변환이 안되서 새로 List 를 선언해서 받아줘야 함
            String type = sortAccountChoice.getSelectedItem();

            switch (type) {
                case "계좌번호" :    // 나중에 enum 으로 수정하기
                    Collections.sort(sortedAccountsList, new AccountNumberComparator());
                    printSortedAccounts(sortedAccountsList);
                    break;
                case "예 금 주":   // 나중에 enum 으로 수정하기
                    Collections.sort(sortedAccountsList, new AccountOwnerComparator());
                    printSortedAccounts(sortedAccountsList);
                    break;
                case "잔  액":   // 나중에 enum 으로 수정하기
                    Collections.sort(sortedAccountsList, new BalanceComparator());
                    printSortedAccounts(sortedAccountsList); // 매개변수 유무를 이용한 메소드 오버로딩으로 코드 리팩토링하기
                    break;
            }
        }
    }

    //  전체 컴포넌트 비활성화 기능
    private void setEnable(boolean enable) {
       /* accountNumTF.setEnabled(enable);
        accountOwnerTF.setEnabled(enable);
        passwdTF.setEnabled(enable);
        depositMoneyTF.setEnabled(enable);*/
        Component[] components = getComponents();
        for (Component component : components) {
            if (component instanceof TextField) {
//                TextField tf = (TextField) component;
//                tf.setEnabled(enable);
                component.setEnabled(enable);
            }
        }
    }

    //    모든 텍스트 필드 초기화
    private void setClearField() {
        Component[] components = getComponents();
        for (Component component : components) {
            if (component instanceof TextField) {
                ((TextField) component).setText("");
            }
        }
        accountNumTF.requestFocus(); // 커서 포인터 깜빡깜빡
    }

    //        테이블 헤더 출력 기능
    private void printHeader() {
        listTA.append("======================================================\n");
        String header = String.format("%1$10s %2$20s %3$20s %4$22s %5$25s",
                "계좌 종류", "계좌 번호", "예금주", "잔  액", "대  출  액");

        listTA.append(header + "\n");
        listTA.append("======================================================\n");

    }

    //   해당 되는 계좌 리스트 1줄 출력
    private void printRow(String accountType, String accountNumber, String accountOwner, long balance, long borrowMoney) {
        String row = String.format("%1$10s %2$22s %3$18s %4$,22d %5$,20d", accountType, accountNumber, accountOwner, balance, borrowMoney);
        listTA.append(row + "\n"); // append 는 \n 붙혀주기
    }



    //    정렬 조건으로 전체계좌 출력 기능
    private void printSortedAccounts(List<Account> accounts) {
        listTA.setText("");
        listTA.setForeground(Color.black);

        if (accounts.isEmpty()) {
            showErrorMessage("⛔ 등록된 계좌가 없습니다 ⛔");
        }
        printHeader();

        for (Account account : accounts) {
            String accountType = null;
            String accountNumber = null;
            String accountOwner = null;
            long balance = 0L;
            long borrowMoney = 0L;

            if (account instanceof MinusAccount) {
                accountType = AccountType.valueOf("MINUS_ACCOUNT").getName();
                borrowMoney = ((MinusAccount) account).getBorrowMoney();
            } else accountType = AccountType.valueOf("ACCOUNT").getName();

            accountNumber = account.getAccountNum();
            accountOwner = account.getAccountOwner();
            balance = account.getBalance();

            printRow(accountType, accountNumber, accountOwner, balance, borrowMoney);
        }
    }

    //        list TextField 에 전체 계좌 목록 출력 기능
    private void printAccounts() {
        listTA.setText("");
        listTA.setForeground(Color.black);

        Collection<Account> accounts = accountService.getAccounts();
        if (accounts.isEmpty()) {
            showErrorMessage("⛔ 등록된 계좌가 없습니다 ⛔");
//            Swing API 활용
//            JOptionPane.showMessageDialog(null,"⛔ 등록된 계좌가 없습니다 ⛔", "검색 결과",
//                    JOptionPane.ERROR_MESSAGE); // 정상적인 메시지는 INFORMATION_MESSAGE

//            Yes or No 창
/*
            int result = JOptionPane.showConfirmDialog(null,"검색 결과", "확인창", JOptionPane.INFORMATION_MESSAGE);
            if(result == JOptionPane.OK_OPTION){
                System.out.println(" OK ");
            }else System.out.println(" NO ");
*/
            return;
        }
        printHeader();

        for (Account account : accounts) {
            String accountType = null;
            String accountNumber = null;
            String accountOwner = null;
            long balance = 0L;
            long borrowMoney = 0L;

            if (account instanceof MinusAccount) {
                accountType = AccountType.valueOf("MINUS_ACCOUNT").getName();
                borrowMoney = ((MinusAccount) account).getBorrowMoney();
            } else accountType = AccountType.valueOf("ACCOUNT").getName();

            accountNumber = account.getAccountNum();
            accountOwner = account.getAccountOwner();
            balance = account.getBalance();

            printRow(accountType, accountNumber, accountOwner, balance, borrowMoney);
        }
    }

    //  프로그램 종료 기능
    private void exit() {   // 접근 제한자 private
//        OS 로 부터 빌려온 리소스 반납
        dispose();

//        화면에서 안보이게
        setVisible(false);

        System.exit(0);
    }

//    기타 등등
}
