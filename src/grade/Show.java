package grade;

import java.util.Objects;

public  class Show{
    String no,coursename;
    double grade,score;//成绩,学分

    public Show(String no, String coursename, double grade, double score) {
        this.no = no;
        this.coursename = coursename;
        this.grade = grade;
        this.score = score;
    }

    @Override
    public String toString() {
        return "Show{" +
                "no='" + no + '\'' +
                ", coursename='" + coursename + '\'' +
                ", grade=" + grade +
                ", score=" + score +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Show show)) return false;
        return Double.compare(show.grade, grade) == 0 && Double.compare(show.score, score) == 0 && Objects.equals(no, show.no) && Objects.equals(coursename, show.coursename);
    }

    @Override
    public int hashCode() {
        return Objects.hash(no, coursename, grade, score);
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getCoursename() {
        return coursename;
    }

    public void setCoursename(String coursename) {
        this.coursename = coursename;
    }

    public double getGrade() {
        return grade;
    }

    public void setGrade(double grade) {
        this.grade = grade;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }


    public Show() {
    }
}
