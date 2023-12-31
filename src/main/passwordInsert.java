package main;

import dao.Boolequal;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class passwordInsert extends JFrame implements ActionListener {
    JPanel passIns_menu;
    JButton Yes,No;
    JPasswordField passwordField,passwordField2;
    JLabel label,label2;
    boolean flag;
    String userid;
    public passwordInsert(String id,boolean flag){
        this.flag = flag;
        this.userid=id;
            passIns_menu = new JPanel();
            this.setTitle("密码修改界面");
            this.setSize(250,180);
            this.setLocationRelativeTo(null);
            this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            passIns_menu.setLayout(new FlowLayout());//设置为流式布局
            label = new JLabel("原密码");
            label2 = new JLabel("新密码");
            Yes = new JButton("确认");
            Yes.addActionListener(this);//监听事件
            No = new JButton("返回");
            No.addActionListener(this);//监听事件
            passwordField = new JPasswordField(16);//设置密码框
            passwordField2 = new JPasswordField(16);//设置密码框

            passIns_menu.add(label);//把组件添加到面板panel
            passIns_menu.add(passwordField);
            passIns_menu.add(label2);
            passIns_menu.add(passwordField2);
            passIns_menu.add(Yes);
            passIns_menu.add(No);

            this.add(passIns_menu);//实现面板panel

            this.setVisible(true);//设置可见
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        this.dispose();
        if(e.getSource() == Yes){
            if (Boolequal.eqlpassSet(flag,userid,passwordField.getText(),passwordField2.getText())) {
                JOptionPane.showMessageDialog(null, "密码修改成功！");
                try {
                    //改密码后重新登录
                    new menuFrame();
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }
            else {
                JOptionPane.showMessageDialog(null, "原密码错误,请重试!");
                new passwordInsert(userid,flag);
            }

        }

        //返回按钮处理
        if(e.getSource() == No){
            try {
                new menuShow(userid,flag);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        }

    }
}
