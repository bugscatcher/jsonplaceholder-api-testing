package com.github.bugscatcher.dto;

import lombok.Data;

@Data
public class PostDTO {
    private int userId;
    private int id;
    private String title;
    private String body;
}
