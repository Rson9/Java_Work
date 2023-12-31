package user;

import dao.Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static dao.Dao.getConn;

public class Teacher extends User{

    public Teacher(String id, String name) {
        super(id, name);
    }

    public Teacher() {
    }

//更新教师密码
    public static void updatePass(Teacher teacher) {
        Connection connection = null;
        PreparedStatement p = null;
        ResultSet resultSet = null;
        String sql = "update teachertab set password=?" + "where id =?";
        try
        {
            connection = getConn();
            p = connection.prepareStatement(sql);
            p.setString(1,teacher.getPassword());
            p.setString(2,teacher.getId());
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
}