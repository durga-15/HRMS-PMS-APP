package com.hrms.pms.pms_app.pms.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EmpResponseDto {

    private String message;
//    private String username;
//    private String password;
}
