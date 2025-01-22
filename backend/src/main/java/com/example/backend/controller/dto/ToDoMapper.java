package com.example.backend.controller.dto;

import com.example.backend.entity.ToDo;

public class ToDoMapper {
    private ToDoMapper() {
        throw new IllegalStateException("Utility class");
    }

    public static ToDoResponse toDto(ToDo toDo) {
        ToDoResponse response = new ToDoResponse();
        response.setId(toDo.getId());
        response.setDescription(toDo.getDescription());
        response.setDone(toDo.isDone());
        response.setDueDate(toDo.getDueDate());
        response.setCreatedAt(toDo.getCreatedAt());
        return response;
    }
}
