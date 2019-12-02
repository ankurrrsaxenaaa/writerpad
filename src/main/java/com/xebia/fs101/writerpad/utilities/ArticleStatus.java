package com.xebia.fs101.writerpad.utilities;

public enum ArticleStatus {
    DRAFT("DRAFT"),
    PUBLISHED("PUBLISHED");

    private String status;

    ArticleStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
