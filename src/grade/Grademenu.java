package grade;

import dao.CourseDao;
import dao.GradeDao;
import main.menuShow;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Grademenu implements ActionListener, TableModelListener {
    boolean flag;
    String userid;
    JFrame frame = new JFrame();
    JLabel label = new JLabel("没有成绩信息");
    JTable stutable = new JTable(new StuTableModel());
    JTable teaTeachtable;
    TeateachTableModel temp;
    JButton fanhui;
    JPanel pandel = new JPanel(new FlowLayout());
    JPanel teatableside = new JPanel(new BorderLayout());
    public Grademenu(String id, boolean flag) {
        this.flag =flag;
        this.userid=id;
        intiComponent();
    }

    public String getUserid() {
        return userid;
    }

    private void intiComponent() {
        frame.setTitle("成绩管理");
        fanhui = new JButton("返回");
       //学生成绩查看
        if(flag){
            if(GradeDao.boolCoursesSelectByStudentid(userid)){
                pandel.add(new JScrollPane(stutable));
            }
            else{
                //无信息添加显示
            label.setFont(new Font("宋体", Font.PLAIN,22));
            pandel.add(label);
            }
            pandel.add(fanhui);

            frame.add(pandel);
        }

        //教师录入成绩
        else{
            if(CourseDao.TeainsertGrade(userid) == null){
                label.setFont(new Font("宋体", Font.PLAIN,22));
                pandel.add(label);
                pandel.add(fanhui);
                frame.add(pandel);
            }else{
                temp = new TeateachTableModel(CourseDao.TeainsertGrade(userid));
                temp.addTableModelListener(this);
                teaTeachtable = new JTable(temp);

                teatableside.add(teaTeachtable.getTableHeader(),BorderLayout.NORTH);
                teatableside.add(teaTeachtable,BorderLayout.CENTER);
                teatableside.add(fanhui,BorderLayout.EAST);
                frame.add(teatableside);
            }

        }
        fanhui.addActionListener(this);
        frame.setLocation(500,350);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        frame.dispose();
        if(e.getSource() == fanhui){
            try {
                new menuShow(userid,flag);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        }

    }

    @Override
    public void tableChanged(TableModelEvent e) {
        int row = e.getFirstRow();
        // 获得此行成绩信息的值
        int col = e.getColumn();
        var grade1 = temp.getValueAt(row, col);
        // 将变化的值赋给第4列
        temp.setValueAt(grade1, row,col);
        // 系统重新绘制表格
        teaTeachtable.repaint();
    }

    //学生
    private static class StuTableModel extends AbstractTableModel{
        String[] columnNames =
                { "课程编号", "课程名", "成绩","获得学分"};
        ArrayList<Show> data  = GradeDao.findGradesByStudent(menuShow.getUserid());

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
            if(columnIndex == 1) return  data.get(rowIndex).getCoursename();
            if(columnIndex == 2) return  data.get(rowIndex).getGrade();
            else return  data.get(rowIndex).getScore();
        }

    }

    //用于显示教师教授的课程
    private static class TeateachTableModel extends AbstractTableModel{
        ArrayList<GradeinsertShow> gradeinsertShows;
        public TeateachTableModel(ArrayList<GradeinsertShow> e){
            this.gradeinsertShows = e;
        }

        String[] str = new String[]{"课程号","课程名","学号","姓名","成绩"};


        @Override
        public int getRowCount() {
            return gradeinsertShows.size();
        }

        @Override
        public int getColumnCount() {
            return str.length;
        }

        @Override
        public String getColumnName(int i) {
            return str[i];
        }

        @Override
        //设置成绩可编辑
        public boolean isCellEditable(int i, int i1) {
            return i1 == 4;
        }

        @Override
        public Object getValueAt(int i, int i1) {
            if(i1==0) return gradeinsertShows.get(i).getCourseid();
            if(i1==1)return gradeinsertShows.get(i).getCoursename();
            if(i1==2)return gradeinsertShows.get(i).getStuid();
            if(i1==3)return gradeinsertShows.get(i).getStuname();
            else return gradeinsertShows.get(i).getGrade();
        }

        @Override
        public void setValueAt(Object aValue, int rowIndex,int columIndex) {
            String va = aValue.toString();
            if(Double.parseDouble(va) < 0 || Double.parseDouble(va) >100) JOptionPane.showMessageDialog(null,"成绩修改错误","错误",JOptionPane.ERROR_MESSAGE);
            else{
                gradeinsertShows.get(rowIndex).setGrade(Double.parseDouble(va));
                GradeDao.update(Double.parseDouble(va),gradeinsertShows.get(rowIndex).getStuid(),gradeinsertShows.get(rowIndex).getCourseid());
            }

        }
    }

    }
