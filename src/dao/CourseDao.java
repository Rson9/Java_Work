package dao;

import course.Course;
import grade.GradeinsertShow;
import grade.TeaShow;

import java.sql.*;
import java.util.ArrayList;

public class CourseDao extends Dao
{
    /**
     * @插入成绩
     */
    public static void insert(Course course,String teacherid)
    {
        Connection connection = null;
        PreparedStatement p = null;
        ResultSet resultSet = null;
        String sql = "insert into coursetab(name,score,allowance,teacherid) values(?,?,?,?)";
        try
        {
            connection = getConn();
            p = connection.prepareStatement(sql);
            p.setString(1,course.getName());
            p.setDouble(2,course.getScore());
            p.setDouble(3,course.getAllowance());
            p.setString(4,teacherid);
            p.executeUpdate();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            System.out.println("数据插入失败！");
        }
        finally
        {
            Dao.close(null, p, connection);
        }
    }
    /**
     * @通过课程编号找到课程
     */
    public static Course getCourseByNo(int aNo)
    {
        Connection connection = null;
        PreparedStatement p = null;
        ResultSet resultSet = null;
        String sql = "select * from coursetab where no=? ";
        try
        {
            connection = getConn();
            p = connection.prepareStatement(sql);
            p.setInt(1,aNo);
            resultSet = p.executeQuery();
            if(resultSet.next())
            {
                String name = resultSet.getString("name");
                double score = resultSet.getDouble("score");
                int allowance = resultSet.getInt("allowance");
                String teacherid = resultSet.getString("teacherid");
                return new Course(aNo,name,score,allowance,teacherid);
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            return null;
        }
        finally
        {
            Dao.close(resultSet, p, connection);
        }
        return null;
    }

    //查询选课程信息   根据flag的值分为可选查询和全部查询
    public static ArrayList<Course> findCourse(String flag)
    {
        ArrayList<Course> results = new ArrayList<>();
        Connection connection = null;
        PreparedStatement p = null;
        ResultSet resultSet = null;
        String sql = flag.equals("can")?"select * from coursetab c  where c.Allowance > 0":"select * from coursetab";
        try
        {
            connection = getConn();
            p = connection.prepareStatement(sql);
            resultSet = p.executeQuery();
            while(resultSet.next())
            {
                int no = resultSet.getInt("no");
                String name= resultSet.getString("name");
                double score = resultSet.getDouble("score");
                int allowance = resultSet.getInt("Allowance");
                Course course = new Course(no,name,score,allowance);
                results.add(course);
            }
            return results;
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            return null;
        }
        finally
        {
            Dao.close(resultSet, p, connection);
        }

    }


    /*
     * 课程信息的更新
     */
    public static void update(Course course)
    {
        Connection connection = null;
        PreparedStatement p = null;
        ResultSet resultSet = null;
        String sql = "update coursetab set name=?,score=?" + "where no=?";
        try
        {
            connection = getConn();
            p = connection.prepareStatement(sql);
            p.setString(1,course.getName());
            p.setDouble(2,course.getScore());
            p.setInt(3,course.getNo());
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
    /*
     * 课程信息的删除
     */
    public static void delete(Course course)
    {
        Connection connection = null;
        PreparedStatement p = null;
        ResultSet resultSet = null;
        String sql = "delete from coursetab where no=?";
        try
        {
            connection = getConn();
            p = connection.prepareStatement(sql);
            p.setInt(1,course.getNo());
            p.executeUpdate();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            System.out.println("数据删除失败！");
        }
        finally
        {
            Dao.close(null, p, connection);
        }
    }

    //更新课余量
    public static void updateAllowance(String courseid) {
        Course course = CourseDao.getCourseByNo(Integer.parseInt(courseid));
        Connection connection = null;
        PreparedStatement p = null;
        ResultSet resultSet = null;
        String sql = "update coursetab set Allowance=?" + "where no=?";
        try
        {
            connection = getConn();
            p = connection.prepareStatement(sql);
            p.setString(1, String.valueOf(course.getAllowance()-1));
            p.setString(2, String.valueOf(course.getNo()));
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

    /**
     * 学生退选课程判断
     */
    public static boolean withdrawCourse(String stuid, String courseId){
        Connection connection = null;
        PreparedStatement p = null;
        Statement p1;
        String sql = "delete from gradetab where student = ? and course = ?";  //删除学生选课信息
        String sql1 = "update coursetab set allowance = allowance + 1 where no = '" + courseId + "'"; //恢复课程中的课余量
        try {
            connection = getConn();
            p = connection.prepareStatement(sql);
            p.setString(1,stuid);
            p.setString(2,courseId);
            p.executeUpdate();
            p1 = connection.createStatement();
            p1.executeUpdate(sql1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        finally {
            Dao.close(null, p, connection);
        }
        return true;
    }

    /**
     *  教师查看教授的课程
     */
    public static ArrayList<TeaShow> TeateachFind(String teacherid) {
        Connection connection = null;
        PreparedStatement p = null;
        ResultSet resultSet = null;
        ArrayList<TeaShow> myCourse = new ArrayList<>();
        String sql = "select * from coursetab where teacherid=? ";
        try
        {
            connection = getConn();
            p = connection.prepareStatement(sql);
            p.setString(1,teacherid);
            resultSet = p.executeQuery();
            while(null!= resultSet && resultSet.next())
            {
                String name = resultSet.getString("name");
                String aNo = resultSet.getString("no");
                double score = resultSet.getDouble("score");
                int allowance = resultSet.getInt("allowance");
                myCourse.add(new TeaShow(aNo,name,score,allowance));
            }
            if(!myCourse.isEmpty()) return myCourse;
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
     * @return gradeinsertShow
     * @descripition 进行成绩操作时列出学生和课程信息
     */
    public static ArrayList<GradeinsertShow> TeainsertGrade(String teacherid) {
        Connection connection = null;
        PreparedStatement p = null;
        ResultSet resultSet = null;
        ArrayList<GradeinsertShow> list = new ArrayList<>();
        String sql = "select  c.no,c.name,s.id,s.name,g.grade " +
                "from coursetab c inner join gradetab g on g.course = c.no" +
                " inner join studentab s on g.student=s.id where teacherid=? order by c.no,s.id ";
        try
        {
            connection = getConn();
            p = connection.prepareStatement(sql);
            p.setString(1,teacherid);
            resultSet = p.executeQuery();
            while(null!= resultSet && resultSet.next())
            {
                String cno = resultSet.getString("c.no");
                String cname = resultSet.getString("c.name");
                String sid = resultSet.getString("s.id");
                String sname = resultSet.getString("s.name");
                double ggrade = resultSet.getDouble("g.grade");
                list.add(new GradeinsertShow(cno,cname,sid,sname,ggrade));
            }
            if(!list.isEmpty()) return list;
        }
        catch (SQLException e)
        {
            return null;
        }
        finally
        {
            Dao.close(resultSet, p, connection);
        }
        return null;
    }


}