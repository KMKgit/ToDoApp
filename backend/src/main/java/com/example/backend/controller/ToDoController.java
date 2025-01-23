package com.example.backend.controller;

import com.example.backend.controller.dto.ToDoFilterRequest;
import com.example.backend.controller.dto.ToDoMapper;
import com.example.backend.controller.dto.ToDoRequest;
import com.example.backend.controller.dto.ToDoResponse;
import com.example.backend.service.ToDoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/todo")
@RequiredArgsConstructor
@Slf4j
public class ToDoController {

    private final ToDoService toDoService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ToDoResponse> findAllToDos(
            @RequestParam(name = "page") Integer page,
            @RequestParam(name = "rowsPerPage") Integer pageSize,
            @RequestParam(name = "description", required = false) String description,
            @RequestParam(name = "isDone", required = false, defaultValue = "false") boolean isDone,
            @RequestParam(name = "isNotDone", required = false, defaultValue = "false") boolean isNotDone,
            @RequestParam(name = "dueDate", required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date dueDate) {
        log.info("Got GET request to find all todos");

        ToDoFilterRequest request = new ToDoFilterRequest(description, isDone, isNotDone, dueDate);
        return toDoService.findAll(request, page, pageSize).stream().map(ToDoMapper::toDto).toList();
    }

    @GetMapping("/total-number")
    @ResponseStatus(HttpStatus.OK)
    public Long findTotalNumber(
            @RequestParam(name = "description", required = false) String description,
            @RequestParam(name = "isDone", required = false, defaultValue = "false") boolean isDone,
            @RequestParam(name = "isNotDone", required = false, defaultValue = "false") boolean isNotDone,
            @RequestParam(name = "dueDate", required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date dueDate) {
        log.info("Got GET request to find all todos");

        ToDoFilterRequest request = new ToDoFilterRequest(description, isDone, isNotDone, dueDate);
        return toDoService.find(request);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ToDoResponse create(@RequestBody ToDoRequest request) {
        log.info("Got POST request to create todo with request {}", request);

        return ToDoMapper.toDto(toDoService.create(request));
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ToDoResponse editById(@PathVariable Long id,
                                 @RequestBody ToDoRequest request) {
        log.info("Got PATCH request to edit todo with id {}", id);

        return ToDoMapper.toDto(toDoService.editById(id, request));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        log.info("Got DELETE request to delete todo with id {}", id);

        toDoService.delete(id);
    }
}
