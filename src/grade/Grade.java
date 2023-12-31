package grade;
public class Grade
{
    private String studentid; // 该成绩所对应的学生学号
    private String courseid; // 该成绩所对应的课程编号
    private double grade; // 成绩

    public Grade(String studentid, String courseid, double grade) {
        this.studentid = studentid;
        this.courseid = courseid;
        this.grade = grade;
    }

    public Grade() {
    }

    public String getStudentid() {
        return studentid;
    }

    public void setStudentid(String studentid) {
        this.studentid = studentid;
    }

    public String getCourseid() {
        return courseid;
    }

    public void setCourseid(String courseid) {
        this.courseid = courseid;
    }

    public double getGrade() {
        return grade;
    }

    public void setGrade(double grade) {
        this.grade = grade;
    }
}