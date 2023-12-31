package course;

import dao.Boolequal;
import dao.CourseDao;
import dao.GradeDao;
import grade.Grade;
import grade.TeaShow;
import main.menuShow;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Objects;

public class CourseAll extends JFrame implements ActionListener {
    JFrame frame = new JFrame();
    MyTableModel stutable;
    JTable teatable;
    JTable temp;
    JPanel pandel = new JPanel(new FlowLayout());
    JTextField text = new JTextField(8);
    JPanel tableside = new JPanel(new BorderLayout());
    JButton xuanke,tuixuan,fanhui;
    JCheckBoxMenuItem  myCourse = new JCheckBoxMenuItem("我的课程",true);
    JCheckBoxMenuItem newCourse = new JCheckBoxMenuItem("发布课程",false);
    final JMenuBar menuBar = new JMenuBar();
    static String userid;
    static boolean flag;

    public CourseAll(String id,boolean flag)
    {
        userid=id;
        CourseAll.flag =flag;
        intiComponent();
    }

    /**
     * 初始化窗体组件
     */
    private void intiComponent()
    {
        fanhui = new JButton("返回");
        if(flag){
            stutable= new MyTableModel();
            temp = new JTable(stutable);
            xuanke = new JButton("选课");
            tuixuan = new JButton("退选");
            tableside.add(temp.getTableHeader(),BorderLayout.NORTH);
            tableside.add(temp,BorderLayout.CENTER);
            pandel.add(tableside);
            pandel.add(text);
            pandel.add(xuanke);
            pandel.add(tuixuan);
            pandel.add(fanhui);
            frame.add(pandel);

            xuanke.addActionListener(this);
            tuixuan.addActionListener(this);
        }else {
            JMenu menu = new JMenu("查询发布");
                menu.add(myCourse);
                menu.add(newCourse);
            menuBar.add(menu);
            frame.setJMenuBar(menuBar);
            if(CourseDao.TeateachFind(userid) == null){
                JLabel tips = new JLabel("暂无您的课程信息!");
                tableside.add(tips);
            }
            else{
                teatable  = new JTable(new TeaTable(CourseDao.TeateachFind(userid)));
                tableside.add(teatable.getTableHeader(),BorderLayout.NORTH);
                tableside.add(teatable,BorderLayout.CENTER);
            }
                tableside.add(fanhui,BorderLayout.EAST);
                frame.add(tableside);

                myCourse.addActionListener(this);
                newCourse.addActionListener(this);

        }
        fanhui.addActionListener(this);

        frame.setLocation(500,350);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();


    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == xuanke){

                //判断课程是否存在
                if(!Boolequal.findCourseById(text.getText())){
                    JOptionPane.showMessageDialog(null,"不存在这个编号的课程!");
                }

                //判断课程是否已经被选
                else if(!GradeDao.getGradeByStudentAndCourse(userid,text.getText())){
                    //判断课余量
                        if(Objects.requireNonNull(CourseDao.getCourseByNo(Integer.parseInt(text.getText()))).getAllowance()>0){
                            Grade newGrade = new Grade(userid,text.getText(),0);
                            GradeDao.insert(newGrade);
                            JOptionPane.showMessageDialog(null,"选课成功");
                            stutable.setValueAt("xuanke", Integer.parseInt(text.getText()),3);
                            temp.repaint();
                         }
                        else{
                            JOptionPane.showMessageDialog(null,"当前课程课余量不足");
                        }

                    }
                else{
                    JOptionPane.showMessageDialog(null,"当前课程已选!");
                }
            }
        if(e.getSource() == tuixuan){
            //判断课程是否存在
            if(!Boolequal.findCourseById(text.getText())){
                JOptionPane.showMessageDialog(null,"不存在这个编号的课程!");
            }

            //判断课程是否已经被选
            else if(GradeDao.getGradeByStudentAndCourse(userid,text.getText())){
                //退选课程
                if (CourseDao.withdrawCourse(userid,text.getText())) {
                    JOptionPane.showMessageDialog(null,"退选成功!");
                    stutable.setValueAt("tuixuan", Integer.parseInt(text.getText()),3);
                    temp.repaint();
                }
                else  JOptionPane.showMessageDialog(null,"退选失败!");
        }
            else  JOptionPane.showMessageDialog(null,"当前课程还未选!");

    }
        if(e.getSource() == fanhui){
            frame.dispose();
            try {
                new menuShow(userid,flag);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        }
        if(e.getSource() == myCourse){
            if(myCourse.getState()){
                newCourse.setState(false);
            }
            else{
                myCourse.setState(true);
            }

        }
        if(e.getSource() == newCourse){
            frame.dispose();
            if(newCourse.getState()){
                myCourse.setState(false);

            }
            //增加课程界面
            new AddCourseFrame(userid);
        }
}

    private static class AddCourseFrame implements ActionListener {

        JFrame menu = new JFrame();
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        Box box = Box.createVerticalBox();
        JLabel name,allowance,score,Show;
        JTextField nametext,allowancetext,scoretext;
        JButton enter,fanhui;
        String userid;
        public AddCourseFrame(String id) {
            menu.setTitle("发布页");

            this.userid=id;
            name = new JLabel("课程名");
            allowance=new JLabel("课余量");
            score = new JLabel("课程学分");
            enter = new JButton("确认");
            fanhui = new JButton("取消");
            Show = new JLabel("新增课程");
            Show.setFont(new Font("宋体", Font.BOLD,15));

            nametext = new JTextField(16);
            allowancetext = new JTextField(16);
            scoretext = new JTextField(16);

            enter.addActionListener( this);
            fanhui.addActionListener(this);
            box.add(Show);
            panel.add(name);
            panel.add(nametext);
            panel.add(allowance);
            panel.add(allowancetext);
            panel.add(score);
            panel.add(scoretext);

            panel.add(enter);
            panel.add(fanhui);
            box.add(panel);
            menu.add(box,BorderLayout.NORTH);

            menu.setLocation(500,350);
            menu.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            menu.setVisible(true);
            menu.pack();

        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if(e.getSource() == enter){
                if(nametext.getText().equals("") || scoretext.getText().equals("") || allowancetext.getText().equals("")){
                    JOptionPane.showMessageDialog(null, "请将必填项补充完整","警告", JOptionPane.WARNING_MESSAGE);
                }
                Course newourse = new Course(nametext.getText(),Double.parseDouble(scoretext.getText()),Integer.parseInt(allowancetext.getText()));
                CourseDao.insert(newourse,userid);
                JOptionPane.showMessageDialog(null, "新增课程成功!","成功",JOptionPane.PLAIN_MESSAGE);
                nametext.setText("");
                scoretext.setText("");
                allowancetext.setText("");
            }
            if (e.getSource() == fanhui){
                menu.dispose();
                new CourseAll(userid,flag);
            }
        }
    }

    //学生
    private static class MyTableModel extends AbstractTableModel{
        /*
         * 这里和刚才一样，定义列名和每个数据的值
         */
        String[] columnNames =
                { "编号", "课程名", "学分", "课余量" };
        ArrayList<Course> data = CourseDao.findCourse("all");


        // 以下为继承自AbstractTableModle的方法，可以自定义
        /**
         * 得到列名
         */
        @Override
        public String getColumnName(int column)
        {
            return columnNames[column];
        }

        /**
         * 重写方法，得到表格列数
         */
        @Override
        public int getColumnCount()
        {
            return columnNames.length;
        }

        /**
         * 得到表格行数
         */
        @Override
        public int getRowCount()
        {
            return data.size();
        }

        /**
         * 得到数据所对应对象
         */
        @Override
        public Object getValueAt(int rowIndex, int columnIndex)
        {
            if(columnIndex == 0) return data.get(rowIndex).getNo();
            if(columnIndex == 1) return  data.get(rowIndex).getName();
            if(columnIndex == 2) return  data.get(rowIndex).getScore();
            else return  data.get(rowIndex).getAllowance();
        }

        /**
         * 得到指定列的数据类型
         */
        @Override
        public Class<?> getColumnClass(int columnIndex)
        {
            return data.get(columnIndex).getClass();
        }

        /**
         * 指定设置数据单元是否可编辑.这里设置"姓名","学号"不可编辑
         */
        @Override
        public boolean isCellEditable(int rowIndex, int columnIndex)
        {
            return false;
        }

        @Override
        public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
            String str = aValue.toString();
            if(str.equals("xuanke")){
                data.get(rowIndex-1).setAllowance("jian");
            }
            if(str.equals("tuixuan")){
                data.get(rowIndex-1).setAllowance("jia");
            }
        }
    }

       //教师
    private static class TeaTable extends AbstractTableModel{

        String[] teaHave = {"编号","课程名","学分","课余量"};
        ArrayList<TeaShow> list;

        public TeaTable(ArrayList<TeaShow> list) {
            this.list = list;
        }

        @Override
        public String getColumnName(int column)
           {
               return teaHave[column];
           }


           @Override
           public int getColumnCount() {
               return teaHave.length;
           }

        @Override
        public int getRowCount() {
            return list.size();
        }




        @Override
        public Object getValueAt(int rowIndex, int columnIndex)
        {
            if(columnIndex == 0) return list.get(rowIndex).getNo();
            if(columnIndex == 1) return  list.get(rowIndex).getCoursename();
            if(columnIndex == 2) return  list.get(rowIndex).getScore();
            else return  list.get(rowIndex).getAllowance();
        }
           @Override
           public boolean isCellEditable(int rowIndex, int columnIndex)
           {
               return false;
           }
    }
}