package dao;

import java.sql.*;

public class Dao
{
    /*
     * 数据库的连接
     *
     */
    public static Connection getConn()
    {
        String driverName="com.mysql.cj.jdbc.Driver";
        String dbURL="jdbc:mysql://localhost:3306/bigg";
        String userName="root";
        String userPwd="123456";
        Connection con=null;
        try{
            Class.forName(driverName);
            con= DriverManager.getConnection(dbURL,userName,userPwd);
        }catch(Exception e)
        {
            System.out.println("数据库连接失败");
        }
        return con;
    }
    /*
     * 数据库连接使用后，必要的关闭操作
     */
    public static void close(ResultSet rs, PreparedStatement p, Connection conn) {
        if (rs != null)
        {
            try
            {
                rs.close(); // 关闭结果集
            } catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        if (p != null)
        {
            try
            {
                p.close(); // 关闭SQL语句
            } catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        if (conn != null)
        {
            try
            {
                conn.close(); // 关闭数据库连接
            } catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }
}