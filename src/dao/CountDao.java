package dao;

import user.Student;
import user.Teacher;
import user.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CountDao extends Dao
{
    /**
     * @通过学生学号找到学生(判断账号是否存在)
     */
    public static Student getStudentById(String aId)
    {
        Connection connection = null;
        PreparedStatement p = null;
        ResultSet resultSet = null;
        String sql = "select * from studentab where ID=?";
        try
        {
            connection = getConn();
            p = connection.prepareStatement(sql);
            p.setString(1,aId);
            resultSet = p.executeQuery();
            if(resultSet.next())
            {
                String id = resultSet.getString("id");
                String name = resultSet.getString("name");
                String major = resultSet.getString("major");
                return new Student(id,name,major);
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        finally
        {
            Dao.close(resultSet, p, connection);
        }
        return null;
    }
    /**
     * @账号注册
     */
    public static boolean insert(String userid,String pass,String name,boolean sot,String major)
    {
        Connection connection = null;
        PreparedStatement p = null;
        String sql;
        try
        {
            connection = getConn();
            if(sot){
                sql ="insert into studentab(ID,name,major,password) values(?,?,?,?)";
                p = connection.prepareStatement(sql);
                p.setString(1,userid);
                p.setString(2,name);
                p.setString(3,major);
                p.setString(4,pass);

            }
            else{
                sql = "insert into teachertab(ID,name,password) values(?,?,?)";
                p = connection.prepareStatement(sql);
                p.setString(1,userid);
                p.setString(2,name);
                p.setString(3,pass);
            }
            p.executeUpdate();
           return true;
        }
        catch (SQLException e)
        {
           return false;
        }
        finally
        {
            Dao.close(null, p, connection); // 执行关闭操作
        }
    }
    /**
    * @通过职工号找到教师
    * */
    public static Teacher getTeacherById(String id) {
        Connection connection = null;
        PreparedStatement p = null;
        ResultSet resultSet = null;
        String sql = "select * from teachertab where ID=?";
        try
        {
            connection = getConn();
            p = connection.prepareStatement(sql);
            p.setString(1,id);
            resultSet = p.executeQuery();
            if(resultSet.next())
            {
                String Id = resultSet.getString("id");
                String name = resultSet.getString("name");
                return new Teacher(id,name);
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        finally
        {
            Dao.close(resultSet, p, connection);
        }
        return null;
    }
    /**
     * @通过姓名找到学生
     */
    public static Student getStudentByName(String name)
    {
        Connection connection = null;
        PreparedStatement p = null;
        ResultSet resultSet = null;
        String sql = "select * from studentab where name=?";
        try
        {
            connection = getConn();
            p = connection.prepareStatement(sql);
            p.setString(1,name);
            resultSet = p.executeQuery();
            if(resultSet.next())
            {
                String id = resultSet.getString("id");
                String major = resultSet.getString("major");
                return new Student(id,name,major);
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        finally
        {
            Dao.close(resultSet, p, connection);
        }
        return null;
    }

    /**
     * @通过学号删除学生,同时删除学生所有信息(管理员使用)
     */
    public static void delete(Student student)
    {
        Connection connection = null;
        PreparedStatement p = null;
        ResultSet resultSet = null;
        String sql = "delete from studentab where id=?";
        try
        {
            connection = getConn();
            p = connection.prepareStatement(sql);
            p.setString(1,student.getId());
            p.executeUpdate();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            System.out.println("数据删除失败！");
        }
        finally
        {
            Dao.close(resultSet, p, connection);
        }
    }


}