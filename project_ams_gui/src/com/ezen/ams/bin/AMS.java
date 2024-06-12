package com.ezen.ams.bin;

import com.ezen.ams.view.BankFrame;

import java.awt.*;

public class AMS {
    public static void main(String[] args) {
        BankFrame bankFrame = new BankFrame("BANK AMS");
        bankFrame.initComponents(); // 화면 초기화
        bankFrame.eventRegister();
        bankFrame.setSize(620, 450);
        bankFrame.setResizable(false);
        setCenterScreeen(bankFrame);
        bankFrame.setVisible(true);

    }

    public static void setCenterScreeen(Frame frame){
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        int screenWidth = toolkit.getScreenSize().width;
        int screenHeight = toolkit.getScreenSize().height;
        int frameWidth = frame.getWidth();
        int frameHeight = frame.getHeight();
        int locationX = (screenWidth - frameWidth) / 2;
        int locationY = (screenHeight - frameHeight) / 2;
        frame.setLocation(locationX, locationY);
    }
}
