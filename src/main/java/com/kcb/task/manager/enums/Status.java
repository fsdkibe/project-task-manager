package com.kcb.task.manager.enums;

public enum Status {
    TO_DO,
    IN_PROGRESS,
    DONE;
    public static Status fromString(String status) {
        return Status.valueOf(status.toUpperCase());
    }
}