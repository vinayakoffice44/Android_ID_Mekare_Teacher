package com.example.id_maker_teacher.Model;

public class ClassModel {
    private int classId;
    private String teacherName;
    private String teacherPhone;
    private String className;
    private String div;
    private int organizationId;
    private String organizationName;
    private String organizationAddress;
    private String organizationWebsite;
    private String organizationPhone;
    private String organizationEmail;
    private String organizationLogoPath;
    private String organizationInstructionTitle;
    private String organizationInstructionDescription;
    private String organizationPrincipalSignature;

    // Constructor
    public ClassModel() {}

    public ClassModel(String className, String div) {
        this.className = className;
        this.div = div;
    }

    public ClassModel(int classId, String teacherName, String teacherPhone, String className, String div,
                      int organizationId, String organizationName, String organizationAddress, String organizationWebsite,
                      String organizationPhone, String organizationEmail, String organizationLogoPath,
                      String organizationInstructionTitle, String organizationInstructionDescription,
                      String organizationPrincipalSignature) {
        this.classId = classId;
        this.teacherName = teacherName;
        this.teacherPhone = teacherPhone;
        this.className = className;
        this.div = div;
        this.organizationId = organizationId;
        this.organizationName = organizationName;
        this.organizationAddress = organizationAddress;
        this.organizationWebsite = organizationWebsite;
        this.organizationPhone = organizationPhone;
        this.organizationEmail = organizationEmail;
        this.organizationLogoPath = organizationLogoPath;
        this.organizationInstructionTitle = organizationInstructionTitle;
        this.organizationInstructionDescription = organizationInstructionDescription;
        this.organizationPrincipalSignature = organizationPrincipalSignature;
    }
    public ClassModel( String teacherName, String teacherPhone, String className, String div,
                      int organizationId, String organizationName, String organizationAddress, String organizationWebsite,
                      String organizationPhone, String organizationEmail, String organizationLogoPath,
                      String organizationInstructionTitle, String organizationInstructionDescription,
                      String organizationPrincipalSignature) {
        this.classId = classId;
        this.teacherName = teacherName;
        this.teacherPhone = teacherPhone;
        this.className = className;
        this.div = div;
        this.organizationId = organizationId;
        this.organizationName = organizationName;
        this.organizationAddress = organizationAddress;
        this.organizationWebsite = organizationWebsite;
        this.organizationPhone = organizationPhone;
        this.organizationEmail = organizationEmail;
        this.organizationLogoPath = organizationLogoPath;
        this.organizationInstructionTitle = organizationInstructionTitle;
        this.organizationInstructionDescription = organizationInstructionDescription;
        this.organizationPrincipalSignature = organizationPrincipalSignature;
    }

    // Getters and Setters
    public int getClassId() { return classId; }
    public void setClassId(int classId) { this.classId = classId; }

    public String getTeacherName() { return teacherName; }
    public void setTeacherName(String teacherName) { this.teacherName = teacherName; }

    public String getTeacherPhone() { return teacherPhone; }
    public void setTeacherPhone(String teacherPhone) { this.teacherPhone = teacherPhone; }

    public String getClassName() { return className; }
    public void setClassName(String className) { this.className = className; }

    public String getDiv() { return div; }
    public void setDiv(String div) { this.div = div; }

    public int getOrganizationId() { return organizationId; }
    public void setOrganizationId(int organizationId) { this.organizationId = organizationId; }

    public String getOrganizationName() { return organizationName; }
    public void setOrganizationName(String organizationName) { this.organizationName = organizationName; }

    public String getOrganizationAddress() { return organizationAddress; }
    public void setOrganizationAddress(String organizationAddress) { this.organizationAddress = organizationAddress; }

    public String getOrganizationWebsite() { return organizationWebsite; }
    public void setOrganizationWebsite(String organizationWebsite) { this.organizationWebsite = organizationWebsite; }

    public String getOrganizationPhone() { return organizationPhone; }
    public void setOrganizationPhone(String organizationPhone) { this.organizationPhone = organizationPhone; }

    public String getOrganizationEmail() { return organizationEmail; }
    public void setOrganizationEmail(String organizationEmail) { this.organizationEmail = organizationEmail; }

    public String getOrganizationLogoPath() { return organizationLogoPath; }
    public void setOrganizationLogoPath(String organizationLogoPath) { this.organizationLogoPath = organizationLogoPath; }

    public String getOrganizationInstructionTitle() { return organizationInstructionTitle; }
    public void setOrganizationInstructionTitle(String organizationInstructionTitle) { this.organizationInstructionTitle = organizationInstructionTitle; }

    public String getOrganizationInstructionDescription() { return organizationInstructionDescription; }
    public void setOrganizationInstructionDescription(String organizationInstructionDescription) { this.organizationInstructionDescription = organizationInstructionDescription; }

    public String getOrganizationPrincipalSignature() { return organizationPrincipalSignature; }
    public void setOrganizationPrincipalSignature(String organizationPrincipalSignature) { this.organizationPrincipalSignature = organizationPrincipalSignature; }
}

