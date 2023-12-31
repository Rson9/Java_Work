package grade;

public class TeaShow extends Show{
    int allowance;
    public TeaShow(String aNo, String name, double score, int allowance) {
        this.allowance = allowance;
        this.no=aNo;
        this.coursename=name;
        this.score = score;
    }

    public int getAllowance() {
        return allowance;
    }
}
