package com.example.firebasertdatabase;

public class Student {
    private int Grade;
    private int StuClass;
    private String StuName;
    private String StuID;
    private boolean CanVaccinate;
    private StuVaccine Vaccination, Vaccination1;

    public Student(int grade, int stuClass, String stuName, String stuID, boolean canVaccinate, StuVaccine vaccination, StuVaccine vaccination1) {
        Grade = grade;
        StuClass = stuClass;
        StuName = stuName;
        StuID = stuID;
        CanVaccinate = canVaccinate;
        Vaccination = vaccination;
        Vaccination1 = vaccination1;
    }

    public Student() {
    }

    /**
     * @return The grade.
     */
    public int getGrade() {
        return Grade;
    }

    public void setGrade(int grade) {
        Grade = grade;
    }

    public int getStuClass() {
        return StuClass;
    }

    public void setStuClass(int stuClass) {
        StuClass = stuClass;
    }

    public String getStuName() {
        return StuName;
    }

    public void setStuName(String stuName) {
        StuName = stuName;
    }

    public String getStuID() {
        return StuID;
    }

    public void setStuID(String stuID) {
        StuID = stuID;
    }

    public boolean isCanVaccinate() {
        return CanVaccinate;
    }

    public void setCanVaccinate(boolean canVaccinate) {
        CanVaccinate = canVaccinate;
    }

    public StuVaccine getVaccination() {
        return Vaccination;
    }

    public void setVaccination(StuVaccine vaccination) {
        Vaccination = vaccination;
    }


    public StuVaccine getVaccination1() {
        return Vaccination1;
    }

    public void setVaccination1(StuVaccine vaccination1) {
        Vaccination1 = vaccination1;
    }
}
