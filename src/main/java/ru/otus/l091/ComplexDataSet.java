package ru.otus.l091;

/**
 * Complex Data Set class as example.
 */
public class ComplexDataSet extends DataSet {
    private boolean f0 = true;
    private byte f1 = 1;
    private char f2 = 'f';
    private short f3 = 3;
    private int f4 = 4;
    private long f5 = 5L;
    private float f6 = 6.6f;
    private double f7 = 7.7;
    private String f8 = "f8";
    private final UsersDataSet uds;

    protected ComplexDataSet(long id) {
        super(id);
        uds = new UsersDataSet(id);
        uds.setName("uds");
        uds.setAge(34);
    }

    public void setF0(boolean f0) {
        this.f0 = f0;
    }

    public void setF1(byte f1) {
        this.f1 = f1;
    }

    public void setF2(char f2) {
        this.f2 = f2;
    }

    public void setF3(short f3) {
        this.f3 = f3;
    }

    public void setF4(int f4) {
        this.f4 = f4;
    }

    public void setF5(long f5) {
        this.f5 = f5;
    }

    public void setF6(float f6) {
        this.f6 = f6;
    }

    public void setF7(double f7) {
        this.f7 = f7;
    }

    public void setF8(String f8) {
        this.f8 = f8;
    }

    @Override
    public String toString() {
        return "ComplexDataSet{ " +
            "f0=" + f0 + ", f1=" + f1 + ", f2=" + f2 +
            ", f3=" + f3 + ", f4=" + f4 + ", f5=" + f5 +
            ", f6=" + f6 + ", f7=" + f7 + ", f8='" + f8 +
            "', uds=" + uds +
            " }";
    }
}
