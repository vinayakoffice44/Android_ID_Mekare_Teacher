package com.example.id_maker_teacher.Model;

public class OrganizationModel {
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
    private String organizationPassword;

    private String organizationTemplateId;
    private String TemplatePhotoShapeId;

    // Constructor
    public OrganizationModel() {}

    public String getOrganizationTemplateId() {
        return organizationTemplateId;
    }

    public void setOrganizationTemplateId(String organizationTemplateId) {
        this.organizationTemplateId = organizationTemplateId;
    }

    public String getTemplatePhotoShapeId() {
        return TemplatePhotoShapeId;
    }

    public void setTemplatePhotoShapeId(String templatePhotoShapeId) {
        TemplatePhotoShapeId = templatePhotoShapeId;
    }

    // Getters and Setters
    public int getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(int organizationId) {
        this.organizationId = organizationId;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    public String getOrganizationAddress() {
        return organizationAddress;
    }

    public void setOrganizationAddress(String organizationAddress) {
        this.organizationAddress = organizationAddress;
    }

    public String getOrganizationWebsite() {
        return organizationWebsite;
    }

    public void setOrganizationWebsite(String organizationWebsite) {
        this.organizationWebsite = organizationWebsite;
    }

    public String getOrganizationPhone() {
        return organizationPhone;
    }

    public void setOrganizationPhone(String organizationPhone) {
        this.organizationPhone = organizationPhone;
    }

    public String getOrganizationEmail() {
        return organizationEmail;
    }

    public void setOrganizationEmail(String organizationEmail) {
        this.organizationEmail = organizationEmail;
    }

    public String getOrganizationLogoPath() {
        return organizationLogoPath;
    }

    public void setOrganizationLogoPath(String organizationLogoPath) {
        this.organizationLogoPath = organizationLogoPath;
    }

    public String getOrganizationInstructionTitle() {
        return organizationInstructionTitle;
    }

    public void setOrganizationInstructionTitle(String organizationInstructionTitle) {
        this.organizationInstructionTitle = organizationInstructionTitle;
    }

    public String getOrganizationInstructionDescription() {
        return organizationInstructionDescription;
    }

    public void setOrganizationInstructionDescription(String organizationInstructionDescription) {
        this.organizationInstructionDescription = organizationInstructionDescription;
    }

    public String getOrganizationPrincipalSignature() {
        return organizationPrincipalSignature;
    }

    public void setOrganizationPrincipalSignature(String organizationPrincipalSignature) {
        this.organizationPrincipalSignature = organizationPrincipalSignature;
    }

    public String getOrganizationPassword() {
        return organizationPassword;
    }

    public void setOrganizationPassword(String organizationPassword) {
        this.organizationPassword = organizationPassword;
    }
}

