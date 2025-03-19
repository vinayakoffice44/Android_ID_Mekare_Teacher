package com.example.id_maker_teacher.Model.API_Model;

import com.google.gson.annotations.SerializedName;

public class ResponseSingleValueModel<T> {

    private int code;
    private String message;
    private String status;
    @SerializedName("data")
    private T data;  // ✅ Now it can hold any object

    public int getCode() { return code; }
    public String getMessage() { return message; }
    public String getStatus() { return status; }
    public T getData() { return data; }

}
