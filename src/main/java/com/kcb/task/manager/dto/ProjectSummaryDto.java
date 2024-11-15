package com.kcb.task.manager.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProjectSummaryDto {

    private Long projectId;
    private String projectName;
    private Long toDoCount;
    private Long inProgressCount;
    private Long doneCount;

    public ProjectSummaryDto(Long id, String name, Map<String, Long> taskCounts) {
    }
}
