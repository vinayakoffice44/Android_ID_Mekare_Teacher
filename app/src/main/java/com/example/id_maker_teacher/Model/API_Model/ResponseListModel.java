package com.example.id_maker_teacher.Model.API_Model;

import java.util.List;

public class ResponseListModel<T> { // ✅ Make it generic for any data type
    private int code;
    private String message;
    private String status;
    private List<T> data; // ✅ Store actual data as a list

    public int getCode() { return code; }
    public String getMessage() { return message; }
    public String getStatus() { return status; }
    public List<T> getData() { return data; }
}