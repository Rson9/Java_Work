package main;

import dao.Boolequal;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class LoginFrame extends JFrame implements ActionListener{

    JPanel panel;
    JLabel label,label2;
    JButton loginButton,exitButton;
    JTextField jTextField;
    JPasswordField passwordField;
    boolean flag;
    String userid;

    public LoginFrame(boolean flag) {
        this.flag = flag;
        this.setTitle("用户登录界面");
        this.setSize(250,180);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        panel = new JPanel();
        panel.setLayout(new FlowLayout());//设置为流式布局
        label = new JLabel("用户名");
        label2 = new JLabel("密码");
        loginButton = new JButton("登录");
        loginButton.addActionListener(this);//监听事件
        exitButton = new JButton("返回");
        exitButton.addActionListener(this);//监听事件
        jTextField = new JTextField(16);//设置文本框的长度
        passwordField = new JPasswordField(16);//设置密码框

        panel.add(label);//把组件添加到面板panel
        panel.add(jTextField);
        panel.add(label2);
        panel.add(passwordField);
        panel.add(loginButton);
        panel.add(exitButton);

        this.add(panel);//实现面板panel

        this.setVisible(true);//设置可见
    }

    public void actionPerformed(ActionEvent e) {//处理事件
        if (e.getSource()==loginButton) {
                try {
                    if (Boolequal.equalpas(flag,jTextField.getText(),passwordField.getText())) {
                        userid = jTextField.getText();
                        JOptionPane.showMessageDialog(null, "登录成功！");
                        this.dispose();
                        try {
                            new menuShow(userid,flag);
                        } catch (Exception ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                    else {
                        JOptionPane.showMessageDialog(null, "用户名或密码错误！");
                    }

                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
        }
            if (e.getSource()==exitButton) {
                    this.dispose();
                    new menuFrame();
            }
        }
    }
