package com.example.backend.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ToDoFilterRequest {

    private String description;
    @JsonProperty("isDone")
    private boolean isDone;
    @JsonProperty("isNotDone")
    private boolean isNotDone;
    private Date dueDate;

}
