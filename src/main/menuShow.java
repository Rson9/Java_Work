package main;

import course.CourseOperation;
import grade.GradeOperation;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
public class menuShow extends JFrame implements ActionListener {
    JPanel menuShow;
    JButton teamenu;
    JButton stumenu4,stumenu1,stumenu2,stumenu3;
    JButton teamenu4,teamenu1,teamenu2,teamenu3;
    JLabel showTest;
    boolean flag;
    static String userid;
    public menuShow(String id,boolean flag) throws Exception {
        this.flag = flag;
        userid=id;
        menuShow = new JPanel();
        menuShow.setLayout(null);

        //学生界面
        if(flag){

            stumenu1 = new JButton("1----密码更改");
            stumenu2 = new JButton("2----选课管理");
            stumenu3 = new JButton("3----成绩查询");
            stumenu4 = new JButton("4----退出系统");

            //监听事件
            stumenu1.addActionListener(this);
            stumenu2.addActionListener(this);
            stumenu3.addActionListener(this);
            stumenu4.addActionListener(this);

            //添加按钮
            menuShow.add(stumenu1);
            menuShow.add(stumenu2);
            menuShow.add(stumenu3);
            menuShow.add(stumenu4);

            //按钮位置设置
            stumenu1.setBounds(240,180,120,20);
            stumenu2.setBounds(240,210,120,20);
            stumenu3.setBounds(240,240,120,20);
            stumenu4.setBounds(240,270,120,20);

            //显示文本
            showTest = new JLabel("学生选课与成绩查询系统");

        }
        else{

            teamenu1 = new JButton("1----密码更改");
            teamenu2 = new JButton("2----课程信息管理");
            teamenu3 = new JButton("3----成绩信息管理");
            teamenu4 = new JButton("4----退出系统");

            //监听事件
            teamenu1.addActionListener(this);
            teamenu2.addActionListener(this);
            teamenu3.addActionListener(this);
            teamenu4.addActionListener(this);

            //添加按钮
            menuShow.add(teamenu1);
            menuShow.add(teamenu2);
            menuShow.add(teamenu3);
            menuShow.add(teamenu4);

            //按钮位置设置
            teamenu1.setBounds(200,180,200,20);
            teamenu2.setBounds(200,210,200,20);
            teamenu3.setBounds(200,240,200,20);
            teamenu4.setBounds(200,270,200,20);

            //显示文本
            showTest = new JLabel("教师选课与成绩管理系统");

        }
        showTest.setBounds(180, 60, 250, 80);
        showTest.setFont(new Font("宋体",0,22));
        menuShow.add(showTest);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.add(menuShow);
        this.setBounds(500,350,600,400);
        this.setVisible(true);

    }

    public static String getUserid() {
        return userid;
    }

    /**
     * 点击事件处理
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        this.dispose();
        //修改密码
        if(e.getSource() ==  stumenu1 || e.getSource() == teamenu1){
                new passwordInsert(userid,flag);
        }
        //选课管理
        else if(e.getSource() ==  stumenu2 || e.getSource() == teamenu2){
           new CourseOperation(userid,flag);
        }
        //成绩管理
        else if(e.getSource() ==  stumenu3||e.getSource() == teamenu3){
            new GradeOperation(userid,flag);
        }
        else{
            new menuFrame();
        }

    }
}
