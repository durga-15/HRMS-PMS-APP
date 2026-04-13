package com.hrms.pms.pms_app.pms.dtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ApiResponse<T> {

    private String message;
    private T data;
}
