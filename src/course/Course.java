package course;

import java.util.Scanner;

public class Course
{
    private int no; // 课程编号
    private String name; // 课程名称
    private double score; // 课程学分
    private int allowance; //课余量
    private String teacherid; //授课教师id
    Scanner reader = new Scanner(System.in);

    public Course(int no, String name, double score,int allowance,String teacherid)
    {
        this.no = no;
        this.name = name;
        this.score = score;
        this.allowance = allowance;
        this.teacherid=teacherid;
    }

    public Course(int no, String name, double score, int allowance) {
        this.no = no;
        this.name = name;
        this.score = score;
        this.allowance = allowance;
    }

    public Course(String name,double score,int allowance)
    {
        this.name = name;
        this.score = score;
        this.allowance=allowance;
    }

    public int getNo()
    {
        return no;
    }

    public void setNo(int no)
    {
        this.no = no;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public double getScore()
    {
        return score;
    }

    public void setScore(double score)
    {
        this.score = score;
    }

    public int getAllowance() {
        return allowance;
    }

    public void setAllowance(String up) {
        if (up.equals("jian"))this.allowance -= 1;
        if(up.equals("jia")) this.allowance +=1;
    }

    @Override
    public String toString()
    {
        return this.no + "\t\t" + this.name + "\t\t"+this.score + "\t\t" + this.allowance;
    }
    public String show()
    {
        return "课程名: " + this.name + "  课程学分: "+this.score + "  课余量: " + this.allowance;
    }

    /*
     * 重写equals方法，方便后续使用removeAll方法
     */
    public boolean equals(Object obj)
    {
        if(this == obj)
            return true;
        if(obj == null || getClass() != obj.getClass())
            return false;
        Course course = (Course) obj;
        return course.getNo() == this.getNo() && course.getName().equals(this.getName()) && course.getScore() == this.getScore() && course.getAllowance() == this.getAllowance();
    }

    public String getTeacherid() {
        return teacherid;
    }

    public void setTeacherid(String teacherid) {
        this.teacherid = teacherid;
    }
}