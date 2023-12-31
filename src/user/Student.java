package user;

import dao.Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static dao.Dao.getConn;

public class Student extends User {
    protected String major; // 专业

    public Student(String id, String name, String major) {
        super(id, name);
        this.major=major;

    }

    public Student() {}


    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }
    //更新学生密码
    public static void updatePass(Student s)
    {

        Connection connection = null;
        PreparedStatement p = null;
        ResultSet resultSet = null;
        String sql = "update studentab set password=?" + "where id =?";
        try
        {
            connection = getConn();
            p = connection.prepareStatement(sql);
            p.setString(1,s.getPassword());
            p.setString(2,s.getId());
            p.executeUpdate();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            System.out.println("数据更新失败！");
        }
        finally
        {
            Dao.close(null, p, connection);
        }
    }

    @Override
    public String toString() {
        return   "姓名: " +name+ " 学号= " + id +" 专业: " + major;
    }
}
