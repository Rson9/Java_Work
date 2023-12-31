package user;

import java.util.Scanner;

public class User
{
    protected String id; // 学/职号
    protected String name; // 姓名
    protected String password;//密码
    Scanner reader = new Scanner(System.in);

    public User(String id, String name)
    {
        this.id = id;
        this.name = name;
    }
    public User(){}

    public String getId()
    {
        return id;
    }

    public String getName()
    {
        return name;
    }
    public String getPassword(){return password;}

    public void setId(String id)
    {
        this.id = id;
    }
    public void setName(String name)
    {
        this.name = name;
    }

    public void setPassword(String password){this.password=password;
        }
}