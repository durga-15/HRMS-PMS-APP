package com.hrms.pms.pms_app.pms.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RevisionTypeResponseDto {

    private UUID id;
    private String revisionName;
    private RevisionCategory category;
    private Boolean isActive;
    private Instant createdAt;
    private Instant updatedAt;
}
