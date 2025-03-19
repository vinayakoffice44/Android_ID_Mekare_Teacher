package com.example.id_maker_teacher.Model.API_Model;

public class ResponseModel {
    private int code;
    private String message;
    private String status;
    private Object data; // ✅ Store actual data as a list


    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public String getStatus() {
        return status;
    }

    public Object getData() {
        return data;
    }
}
