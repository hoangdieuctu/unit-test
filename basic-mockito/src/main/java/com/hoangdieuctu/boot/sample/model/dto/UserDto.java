package com.hoangdieuctu.boot.sample.model.dto;

public class UserDto {
    private String id;
    private String name;
    private Long timestamp;

    public UserDto(String id, String name) {
        this.id = id;
        this.name = name;
        this.timestamp = System.currentTimeMillis();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }
}
