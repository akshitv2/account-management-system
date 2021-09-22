package com.example.barclays.accountmanagementsystem.dto;

public class ApiResponse {
    Boolean success;
    String desc;

    public ApiResponse(Boolean success, String desc) {
        this.success = success;
        this.desc = desc;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

}
