package grade;

public class GradeinsertShow {
    String courseid,coursename,stuid,stuname;
    double grade;

    public GradeinsertShow(String courseid, String coursename, String stuid, String stuname, double grade) {
        this.courseid = courseid;
        this.coursename = coursename;
        this.stuid = stuid;
        this.stuname = stuname;
        this.grade = grade;
    }

    public String getCourseid() {
        return courseid;
    }

    public void setCourseid(String courseid) {
        this.courseid = courseid;
    }

    public String getCoursename() {
        return coursename;
    }

    public void setCoursename(String coursename) {
        this.coursename = coursename;
    }

    public String getStuid() {
        return stuid;
    }

    public void setStuid(String stuid) {
        this.stuid = stuid;
    }

    public String getStuname() {
        return stuname;
    }

    public void setStuname(String stuname) {
        this.stuname = stuname;
    }

    public double getGrade() {
        return grade;
    }

    public void setGrade(double grade) {
        this.grade = grade;
    }
}
