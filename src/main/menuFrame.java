package main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class menuFrame extends JFrame implements ActionListener {
    JPanel menu;
    JButton tealogin,stulogin,teaRegistration,stuRegistration,exit;
    JLabel showTest;

    public menuFrame(){
        this.setTitle("主界面");
        menu = new JPanel();
        menu.setLayout(null);
        tealogin = new JButton("教师登录");
        stulogin = new JButton("学生登录");
        teaRegistration = new JButton("教师注册");
        stuRegistration = new JButton("学生注册");
        exit = new JButton("退出程序");

        //监听事件
        tealogin.addActionListener(this);
        stulogin.addActionListener(this);
        teaRegistration.addActionListener(this);
        stuRegistration.addActionListener(this);
        exit.addActionListener(this);

        //添加按钮
        menu.add(tealogin);
        menu.add(stulogin);
        menu.add(teaRegistration);
        menu.add(stuRegistration);
        menu.add(exit);

        //按钮位置设置
        tealogin.setBounds(240,180,120,20);
        stulogin.setBounds(240,210,120,20);
        teaRegistration.setBounds(240,240,120,20);
        stuRegistration.setBounds(240,270,120,20);
        exit.setBounds(240,300,120,20);
        //显示文本
        showTest = new JLabel("欢迎来到选课系统");
        showTest.setBounds(220, 60, 180, 80);
        showTest.setFont(new Font("宋体",0,22));
        menu.add(showTest);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.add(menu);
        this.setBounds(500,350,600,400);
        this.setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        this.dispose();
        if(e.getSource() == tealogin ){
            new LoginFrame(false);
        }
        else if(e.getSource() == stulogin){
            new LoginFrame(true);
        }
        else if(e.getSource() == exit){
            System.exit(0);
        }
        else if(e.getSource() == teaRegistration) new RegistrationFrame(false);
        else new RegistrationFrame(true);
    }
}
