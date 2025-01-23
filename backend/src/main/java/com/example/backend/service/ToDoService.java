package com.example.backend.service;

import com.example.backend.controller.dto.ToDoFilterRequest;
import com.example.backend.controller.dto.ToDoRequest;
import com.example.backend.entity.ToDo;
import com.example.backend.repository.ToDoRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.backend.repository.ToDoSpecification.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class ToDoService {

    private final ToDoRepository toDoRepository;

    @Transactional
    public ToDo create(ToDoRequest request) {
        log.info("Starting to create todo with request {}", request);

        ToDo toDo = new ToDo();

        toDo.setDescription(request.getDescription());
        toDo.setDone(request.isDone());
        toDo.setDueDate(request.getDueDate());
        return toDoRepository.save(toDo);
    }

    @Transactional
    public ToDo editById(Long id, ToDoRequest request) {
        log.info("Starting to edit todo with id {} and request {}", id, request);

        ToDo toDo = toDoRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Cannot find todo with id: " + id));

        toDo.setDescription(request.getDescription());
        toDo.setDone(request.isDone());
        toDo.setDueDate(request.getDueDate());
        return toDoRepository.save(toDo);
    }

    @Transactional
    public void delete(Long id) {
        log.info("Starting to delete todo with id {}", id);

        toDoRepository.deleteById(id);
    }

    public List<ToDo> findAll(ToDoFilterRequest request) {
        log.info("Starting to find all todos with request {}", request);

        Specification<ToDo> spec = Specification.where(null);

        if (request.getDescription() != null) {
            spec = spec.and(descriptionContains(request.getDescription()));
        }

        if (request.isDone() && !request.isNotDone()) {
            spec = spec.and(isDone());
        }

        if (request.isNotDone() && !request.isDone()) {
            spec = spec.and(isNotDone());
        }

        if (request.getDueDate() != null) {
            spec = spec.and(dueDateEquals(request.getDueDate()));
        }

        Sort sort = Sort.by(Sort.Order.asc("done"), Sort.Order.asc("dueDate"));

        return toDoRepository.findAll(spec, sort);
    }
}
