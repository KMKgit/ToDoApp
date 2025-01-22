package com.example.backend.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ToDoRequest {

    @NotBlank
    private String description;
    @JsonProperty("isDone")
    private boolean isDone;
    private Date dueDate;
}
