package com.ritik.spring_boot.to_do_management.service;

import com.ritik.spring_boot.to_do_management.dto.ToDoDto;
import com.ritik.spring_boot.to_do_management.entity.ToDo;

import java.util.List;

public interface ToDoService {
    ToDoDto addToDo(ToDoDto toDo);
    ToDoDto getToDo(Long Id);
    List<ToDoDto> getAllToDo();
    ToDoDto updateToDo(ToDoDto toDoDto, Long Id);
    void deleteToDo(Long Id);
    ToDoDto completeToDo(Long Id);
    ToDoDto inCompleteToDo(Long Id);
}
