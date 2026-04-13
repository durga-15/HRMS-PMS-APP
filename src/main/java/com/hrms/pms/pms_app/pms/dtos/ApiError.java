package com.hrms.pms.pms_app.pms.dtos;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;

public record ApiError(
        int status,
        String error,
        String message,
        String path,
        OffsetDateTime timeStamp
) {

    public static ApiError of(int status, String error, String message, String path){
        return new ApiError(status, error, message, path, OffsetDateTime.now(ZoneOffset.UTC));
    }
}
