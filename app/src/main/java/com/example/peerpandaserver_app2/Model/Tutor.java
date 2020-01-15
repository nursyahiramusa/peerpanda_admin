package com.example.peerpandaserver_app2.Model;

public class Tutor {
    private String course;
    private String courseTeach;
    private String cpass;
    private String name;
    private String pass;
    private String sem;
    private String StuID;
    private String tutorID;
    private String tutorImage;
    private String gender;
    private String price;
    private String campus;
    private String phoneno;
    private String coursename;
    private String courseGrade;

    public Tutor() {
    }
    public Tutor(String course, String courseTeach, String name, String sem, String StuID, String price, String coursename) {
        this.course = course;
        this.courseTeach = courseTeach;
        this.name = name;
        this.sem = sem;
        this.StuID = StuID;
        this.price = price;
        this.coursename = coursename;
        //7
    }
    public Tutor(String course, String courseTeach, String name, String sem, String stuID,
                 String tutorID, String gender, String price, String campus, String phoneno,
                 String coursename, String courseGrade) {
        this.course = course;
        this.courseTeach = courseTeach;
        this.name = name;
        this.sem = sem;
        this.StuID = stuID;
        this.tutorID = tutorID;
        this.gender = gender;
        this.price = price;
        this.campus = campus;
        this.phoneno = phoneno;
        this.coursename = coursename;
        this.courseGrade = courseGrade;
        //12
    }
    public Tutor(String course, String courseTeach, String cpass, String name, String pass,
                 String sem, String stuID, String tutorID, String tutorImage, String gender,
                 String price, String campus, String phoneno, String coursename) {
        this.course = course;
        this.courseTeach = courseTeach;
        this.cpass = cpass;
        this.name = name;
        this.pass = pass;
        this.sem = sem;
        StuID = stuID;
        this.tutorID = tutorID;
        this.tutorImage = tutorImage;
        this.gender = gender;
        this.price = price;
        this.campus = campus;
        this.phoneno = phoneno;
        this.coursename = coursename;
        //14
    }

    public Tutor(String name, String courseTeach, String tutorImage) {
        this.name = name;
        this.courseTeach = courseTeach;
        this.tutorImage = tutorImage;
        //3
    }
    public Tutor(String course, String courseTeach, String name, String sem, String tutorID,
                 String tutorImage, String gender, String price, String campus, String phoneno,
                 String coursename) {
        this.course = course;
        this.courseTeach = courseTeach;
        this.name = name;
        this.sem = sem;
        this.tutorID = tutorID;
        this.tutorImage = tutorImage;
        this.gender = gender;
        this.price = price;
        this.campus = campus;
        this.phoneno = phoneno;
        this.coursename = coursename;
        //11
    }


    public Tutor(String course, String courseTeach, String name, String sem, String stuID,
                 String gender, String price, String phoneno, String coursename) {
        this.course = course;
        this.courseTeach = courseTeach;
        this.name = name;
        this.sem = sem;
        this.StuID = stuID;
        this.gender = gender;
        this.price = price;
        this.phoneno = phoneno;
        this.coursename = coursename;
        //9
    }

    public Tutor(String name) {
        this.name = name;
    }


    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getCourseTeach() {
        return courseTeach;
    }

    public void setCourseTeach(String courseTeach) {
        this.courseTeach = courseTeach;
    }

    public String getCpass() {
        return cpass;
    }

    public void setCpass(String cpass) {
        this.cpass = cpass;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getSem() {
        return sem;
    }

    public void setSem(String sem) {
        this.sem = sem;
    }

    public String getStuID() {
        return StuID;
    }

    public void setStuID(String stuID) {
        StuID = stuID;
    }

    public String getTutorID() {
        return tutorID;
    }

    public void setTutorID(String tutorID) {
        this.tutorID = tutorID;
    }

    public String getTutorImage() {
        return tutorImage;
    }

    public void setTutorImage(String tutorImage) {
        this.tutorImage = tutorImage;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getCampus() {
        return campus;
    }

    public void setCampus(String campus) {
        this.campus = campus;
    }

    public String getPhoneno() {
        return phoneno;
    }

    public void setPhoneno(String phoneno) {
        this.phoneno = phoneno;
    }

    public String getCoursename() {
        return coursename;
    }

    public void setCoursename(String coursename) {
        this.coursename = coursename;
    }

    public String getCourseGrade() {
        return courseGrade;
    }

    public void setCourseGrade(String courseGrade) {
        this.courseGrade = courseGrade;
    }
}
