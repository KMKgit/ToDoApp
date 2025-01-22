package com.example.backend.repository;

import com.example.backend.entity.ToDo;
import org.springframework.data.jpa.domain.Specification;

import java.util.Date;

public class ToDoSpecification {

    private ToDoSpecification() {
    }

    public static Specification<ToDo> descriptionContains(String description) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("description"), "%" + description + "%");
    }

    public static Specification<ToDo> isDone() {
        return (root, query, criteriaBuilder) -> criteriaBuilder.isTrue(root.get("done"));
    }

    public static Specification<ToDo> isNotDone() {
        return (root, query, criteriaBuilder) -> criteriaBuilder.isFalse(root.get("done"));
    }

    public static Specification<ToDo> dueDateEquals(Date dueDate) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(criteriaBuilder.function("DATE", Date.class, root.get("dueDate")), dueDate);
    }
}
