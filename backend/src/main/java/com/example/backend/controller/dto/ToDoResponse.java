package com.example.backend.controller.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class ToDoResponse {

    private Long id;
    private String description;
    private boolean isDone;
    private Date dueDate;
    private Date createdAt;
}
