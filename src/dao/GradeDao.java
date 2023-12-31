package dao;

import course.Course;
import grade.Grade;
import grade.Grademenu;
import grade.Show;
import user.Student;
import user.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GradeDao extends Dao
{
    //数据库中新增成绩信息
    public static void insert(Grade grade)
    {
        Connection connection = null;
        PreparedStatement p = null;
        ResultSet resultSet = null;
        String sql = "insert into gradetab(student,course,grade) values(?,?,?)";
        try
        {
            connection = getConn();
            p = connection.prepareStatement(sql);
            p.setString(1,grade.getStudentid());
            p.setString(2,grade.getCourseid());
            p.setDouble(3,grade.getGrade());
            p.executeUpdate();
            //更新课程余量
            CourseDao.updateAllowance(grade.getCourseid());

        }
        catch (SQLException e)
        {
            e.printStackTrace();
            System.out.println("数据插入失败！");
        }
        finally
        {
            Dao.close(resultSet, p, connection);
        }
    }
    //通过学号和课程号判断该学生是否已经选择了该课程
    public static boolean getGradeByStudentAndCourse(String stuId, String courseId)
    {
        Connection connection = null;
        PreparedStatement p = null;
        ResultSet resultSet = null;
        String sql = "select no from gradetab where student=? and course=? ";
        try
        {
            connection = getConn();
            p = connection.prepareStatement(sql);
            p.setString(1,stuId);
            p.setString(2, courseId);
            resultSet = p.executeQuery();
            return resultSet.next();
        }
        catch (SQLException e)
        {
            return false;
        }
        finally
        {
            Dao.close(resultSet, p, connection); // 关闭操作
        }
    }

    /**
     * @通过学号判断是否选择了课程
     */
    public static boolean boolCoursesSelectByStudentid(String stuid)
    {
        List<Course> allCourses = CourseDao.findCourse("all"); // 查询所有课程
        Connection connection = null;
        PreparedStatement p = null;
        ResultSet resultSet = null;
        String sql = "select * from gradetab g where student=?";
        try
        {
            connection = getConn();
            p = connection.prepareStatement(sql);
            p.setString(1,stuid);
            resultSet = p.executeQuery();
            if(resultSet.next()) return true;
        }catch (SQLException e)
        {
            return false;
        }
        finally
        {
            Dao.close(resultSet, p, connection);
        }
        return false;
    }



   //  通过学生查找其成绩信息
    public static ArrayList<Show> findGradesByStudent(String stuid)
    {
        ArrayList<Show> results = new ArrayList<>();
        Connection connection = null;
        PreparedStatement p = null;
        ResultSet resultSet = null;
        String sql = "select g.grade, c.score, c.no from gradetab g left join coursetab c on g.course=c.no where g.student=? order by course";
        try
        {
            connection = getConn();
            p = connection.prepareStatement(sql);
            p.setString(1,stuid);
            resultSet = p.executeQuery();
            while(resultSet.next())
                {
                    double gra = resultSet.getDouble("g.grade"); // 课程成绩
                    String ID = resultSet.getString("c.no");// 课程号
                    double sco = 0;
                    if(gra>=60){
                        sco = resultSet.getDouble("c.score");
                    }
                    String name = CourseDao.getCourseByNo(Integer.parseInt(ID)).getName();
                    Show grade = new Show(ID,name,gra, sco);
                    results.add(grade);
                }
            }
        catch (SQLException e)
        {e.printStackTrace();
            return null;
        }
        finally
        {
            Dao.close(resultSet, p, connection); // 关闭操作
        }
        return results;

    }

    /*
     * 更新成绩信息
     */
    public static void update(double gra, String stuid, String courseid)
    {
        Connection connection = null;
        PreparedStatement p = null;
        ResultSet resultSet = null;
        String sql = "update gradetab set grade= ? where student = ? and course = ?";
        try
        {
            connection = getConn();
            p = connection.prepareStatement(sql);
            p.setDouble(1,gra);
            p.setString(2,stuid);
            p.setString(3,courseid);
            p.executeUpdate();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        finally
        {
            Dao.close(resultSet, p, connection);
        }
    }

}