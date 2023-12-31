package main;

import dao.CountDao;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

public class RegistrationFrame extends JFrame implements ActionListener {
    JPanel panel;
    JLabel label,label2;
    JButton regisButton,exitButton;
    JTextField jTextField;
    JPasswordField passwordField;
    String userid,major;
    boolean flag;

    public RegistrationFrame(boolean flag) {
        this.flag =flag;
        this.setTitle("用户注册界面");
        this.setSize(250,220);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        panel = new JPanel();
        panel.setLayout(new FlowLayout());//设置为流式布局
        label = new JLabel("用户名");
        label2 = new JLabel("密码");
        regisButton = new JButton("注册");
        regisButton.addActionListener(this);//监听事件
        exitButton = new JButton("返回");
        exitButton.addActionListener(this);//监听事件
        jTextField = new JTextField(16);//设置文本框的长度
        passwordField = new JPasswordField(16);//设置密码框

        panel.add(label);//把组件添加到面板panel
        panel.add(jTextField);
        panel.add(label2);
        panel.add(passwordField);
        panel.add(regisButton);
        panel.add(exitButton);

        this.add(panel);//实现面板panel

        this.setVisible(true);//设置可见
    }

    public void actionPerformed(ActionEvent e) {//处理事件
        if (e.getSource()==regisButton) {
            userid = jTextField.getText();
            //先判断格式是否正确
            if(!Registrationcheck.checkAccount(jTextField.getText())) JOptionPane.showMessageDialog(null,"账号必须是10位数字,第一位不能是0","警告",JOptionPane.WARNING_MESSAGE);
            else if(!Registrationcheck.checkPassword(new String(passwordField.getPassword())))  JOptionPane.showMessageDialog(null,"密码必须包含大小写字母和数字的组合，可以使用特殊字符，长度在8-10之间","警告",JOptionPane.WARNING_MESSAGE );

            //再判断账号是否已经存在
            else if(flag && CountDao.getStudentById(userid) != null) JOptionPane.showMessageDialog(null,"账号已经存在","警告",JOptionPane.WARNING_MESSAGE);
            else if(!flag && CountDao.getTeacherById(userid) != null) JOptionPane.showMessageDialog(null,"账号已经存在","警告",JOptionPane.WARNING_MESSAGE);
            else {
                this.dispose();
                new JComboBoxFrame(flag);
            }
        }
            if (e.getSource()==exitButton) {
                this.dispose();
                new menuFrame();
            }
        }
 public class JComboBoxFrame implements ActionListener {
         JFrame main;
         JPanel root;
         JButton enter,fanhui;
         JLabel messageLabel,name;
         JComboBox<String> comboBox = null;
         JTextField nametext;


         public JComboBoxFrame(boolean flag) {
                 main = new JFrame();
                 root = new JPanel(new FlowLayout());      //定义面板容器
                 setContentPane(root);


                 name = new JLabel("姓名:");
                 nametext = new JTextField(16);
                 enter = new JButton("确认");
                 fanhui = new JButton("取消");
                 root.add(name);
                 root.add(nametext);
                 if(flag){
                     messageLabel = new JLabel("请选择你的专业：");             //定义标签组件
                     messageLabel.setBounds(31, 25, 130, 15);    //设置标签组件大小
                     root.add(messageLabel);
                     //定义字符串数组对象
                     String[] constellations = {
                             "网络工程", "软件工程", "集成", "物联网",
                             "计算机", "大数据", "网络安全"
                     };
                     comboBox = new JComboBox<>(constellations);                    //定义下拉列表框组件
                     comboBox.setBounds(130, 22, 100, 21);     //设置列表大小
                     root.add(comboBox);                                         //将下拉列表添加到面板
                 }
                 root.add(enter);
                 root.add(fanhui);
                 main.add(root);
                 enter.addActionListener(this);
                 fanhui.addActionListener(this);


                 //设置窗口风格
                 main.setLocation(500,350);
                 main.setVisible(true);
                 main.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                 main.pack();

         }

     @Override
     public void actionPerformed(ActionEvent e) {
           if(e.getSource() == enter){
               if(nametext.getText() == null){
                   JOptionPane.showMessageDialog(null,"姓名不能为空！","警告",JOptionPane.WARNING_MESSAGE );
               } else if (!Registrationcheck.checkName(nametext.getText())) {
                   JOptionPane.showMessageDialog(null,"姓名格式不正确！必须为一个中文字符或更多","警告",JOptionPane.WARNING_MESSAGE );
               } else{
                   if(flag) major = Objects.requireNonNull(comboBox.getSelectedItem()).toString().trim();
                   if(CountDao.insert(userid,new String(passwordField.getPassword()), nametext.getText(), flag,major)){
                       JOptionPane.showMessageDialog(null,"注册成功！" );
                       main.dispose();
                       new menuFrame();
                   }}

         }
           if(e.getSource() == fanhui){
               main.dispose();
               new RegistrationFrame(flag);
           }
     }
 }
}

