package dao;

import user.Student;
import user.Teacher;

import java.sql.*;

import static dao.Dao.getConn;

public class Boolequal {
    public static boolean equalpas(boolean flag, String id, String password) throws SQLException {
        Connection connection = null;
        PreparedStatement p = null;
        ResultSet resultSet = null;
        String StborTtb = flag ? "studentab" : "teachertab";
        String sql = "select id,password from "+ StborTtb+ " where ID=?";
        try {
            connection = getConn();
            p = connection.prepareStatement(sql);
            p.setString(1,id);
            resultSet = p.executeQuery();
          if(resultSet.next()){
                if (resultSet.getString("password").equals(password)) {
                    return true;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            Dao.close(resultSet, p, connection);
        }
        return false;
    }
    /**
     * @判断密码并且更改
     */
    public static boolean eqlpassSet(boolean flag,String id,String Oldpass,String Newpass) {
       String Tabname = flag?"studentab":"teachertab";
            Connection connection = null;
            PreparedStatement p = null;
            ResultSet resultSet = null;
            String sql = "select password from " +Tabname+" where id = ?";
            try {
                connection = getConn();
                p = connection.prepareStatement(sql);
                p.setString(1, id);
                resultSet = p.executeQuery();
                if (resultSet.next()){
                    if(flag){
                        Student student = CountDao.getStudentById(id);
                        if (resultSet.getString("password").equals(Oldpass)) {
                            if (student != null) {
                                student.setPassword(Newpass);
                                Student.updatePass(student);
                            }
                            return true;
                        }
                    }
                    else{
                        Teacher teacher = CountDao.getTeacherById(id);
                        if (resultSet.getString("password").equals(Oldpass)) {
                            if (teacher != null) {
                                teacher.setPassword(Newpass);
                                Teacher.updatePass(teacher);
                            }
                            return true;
                        }
                    }

                    }
            }

    catch (SQLException e) {
                e.printStackTrace();
            } finally {
                Dao.close(resultSet, p, connection);
            }

        System.out.println("No");
            return false;
    }
    /*
     * 通过名称找到课程信息
     */
    public static boolean findCourseById(String courseid){
        Connection connection = null;
        PreparedStatement p = null;
        ResultSet resultSet = null;
        String sql = "select * from coursetab where no=?";
        try
        {
            connection = getConn();
            p = connection.prepareStatement(sql);
            p.setString(1, courseid);
            resultSet = p.executeQuery();
            if(resultSet.next())
            {
                return true;
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
        System.out.println("No");
        return false;
    }
}