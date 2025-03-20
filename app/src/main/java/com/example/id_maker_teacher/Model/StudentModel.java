package com.example.id_maker_teacher.Model;

public class StudentModel {
    private int studentId;
    private String studentFullName;
    private String studentRollNumber;
    private String studentClass;
    private String dateOfBirth;
    private String div;
    private String bloodGroup;
    private String profileImage;
    private String parentOneTitle;
    private String parentOneName;
    private String parentOnePhone;
    private String parentTwoTitle;
    private String parentTwoName;
    private String parentTwoPhone;
    private String homeAddress;

    String class_Id;

    // Constructor
    public StudentModel() {

    }

    public StudentModel(int studentId, String studentFullName, String studentRollNumber, String studentClass, String dateOfBirth, String div, String bloodGroup, String profileImage) {
        this.studentId = studentId;
        this.studentFullName = studentFullName;
        this.studentRollNumber = studentRollNumber;
        this.studentClass = studentClass;
        this.dateOfBirth = dateOfBirth;
        this.div = div;
        this.bloodGroup = bloodGroup;
        this.profileImage = profileImage;
    }

    public StudentModel(int studentId, String studentRollNumber, String studentFullName , String studentClass, String dateOfBirth, String div,
                        String bloodGroup, String profileImage, String parentOneTitle, String parentOneName,
                        String parentOnePhone, String parentTwoTitle, String parentTwoName, String parentTwoPhone, String homeAddress,String class_Id) {
        this.studentId = studentId;
        this.studentRollNumber = studentRollNumber;
        this.studentFullName = studentFullName;
        this.studentClass = studentClass;
        this.dateOfBirth = dateOfBirth;
        this.div = div;
        this.bloodGroup = bloodGroup;
        this.profileImage = profileImage;
        this.parentOneTitle = parentOneTitle;
        this.parentOneName = parentOneName;
        this.parentOnePhone = parentOnePhone;
        this.parentTwoTitle = parentTwoTitle;
        this.parentTwoName = parentTwoName;
        this.parentTwoPhone = parentTwoPhone;
        this.homeAddress = homeAddress;
        this.class_Id = class_Id;
    }

    public String getClass_Id() {
        return class_Id;
    }

    public void setClass_Id(String class_Id) {
        this.class_Id = class_Id;
    }

    public String getStudentRollNumber() {
        return studentRollNumber;
    }

    public void setStudentRollNumber(String studentRollNumber) {
        this.studentRollNumber = studentRollNumber;
    }

    // Getters and Setters
    public int getStudentId() { return studentId; }
    public void setStudentId(int studentId) { this.studentId = studentId; }

    public String getStudentFullName() { return studentFullName; }
    public void setStudentFullName(String studentFullName) { this.studentFullName = studentFullName; }

    public String getStudentClass() { return studentClass; }
    public void setStudentClass(String studentClass) { this.studentClass = studentClass; }

    public String getDateOfBirth() { return dateOfBirth; }
    public void setDateOfBirth(String dateOfBirth) { this.dateOfBirth = dateOfBirth; }

    public String getDiv() { return div; }
    public void setDiv(String div) { this.div = div; }

    public String getBloodGroup() { return bloodGroup; }
    public void setBloodGroup(String bloodGroup) { this.bloodGroup = bloodGroup; }

    public String getProfileImage() { return profileImage; }
    public void setProfileImage(String profileImage) { this.profileImage = profileImage; }

    public String getParentOneTitle() { return parentOneTitle; }
    public void setParentOneTitle(String parentOneTitle) { this.parentOneTitle = parentOneTitle; }

    public String getParentOneName() { return parentOneName; }
    public void setParentOneName(String parentOneName) { this.parentOneName = parentOneName; }

    public String getParentOnePhone() { return parentOnePhone; }
    public void setParentOnePhone(String parentOnePhone) { this.parentOnePhone = parentOnePhone; }

    public String getParentTwoTitle() { return parentTwoTitle; }
    public void setParentTwoTitle(String parentTwoTitle) { this.parentTwoTitle = parentTwoTitle; }

    public String getParentTwoName() { return parentTwoName; }
    public void setParentTwoName(String parentTwoName) { this.parentTwoName = parentTwoName; }

    public String getParentTwoPhone() { return parentTwoPhone; }
    public void setParentTwoPhone(String parentTwoPhone) { this.parentTwoPhone = parentTwoPhone; }

    public String getHomeAddress() { return homeAddress; }
    public void setHomeAddress(String homeAddress) { this.homeAddress = homeAddress; }
}
