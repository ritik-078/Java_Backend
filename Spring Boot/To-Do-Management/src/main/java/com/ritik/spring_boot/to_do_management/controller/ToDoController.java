package com.ritik.spring_boot.to_do_management.controller;

import com.ritik.spring_boot.to_do_management.dto.ToDoDto;
import com.ritik.spring_boot.to_do_management.service.ToDoService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("todos")
public class ToDoController {

    private ToDoService toDoService;


    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<ToDoDto> addToDo(@RequestBody ToDoDto toDoDto){
        ToDoDto savedtoDoDto = toDoService.addToDo(toDoDto);

        return new ResponseEntity<>(savedtoDoDto, HttpStatus.CREATED);
    }

    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @GetMapping("{id}")
    public ResponseEntity<ToDoDto> getToDoById(@PathVariable("id") Long Id){
        ToDoDto toDoDto = toDoService.getToDo(Id);

        return new ResponseEntity<>(toDoDto, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @GetMapping("all")
    public ResponseEntity<List<ToDoDto>> getAllToDo(){
        List<ToDoDto> alltoDoDto = toDoService.getAllToDo();

        return new ResponseEntity<>(alltoDoDto, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("{id}")
    public ResponseEntity<ToDoDto> updateToDo(@RequestBody ToDoDto toDoDto, @PathVariable("id") Long Id){
        ToDoDto updatedtoDoDto = toDoService.updateToDo(toDoDto, Id);

        return new ResponseEntity<>(updatedtoDoDto, HttpStatus.OK);
    }


    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteToDo(@PathVariable("id") Long Id){
        toDoService.deleteToDo(Id);

        return new ResponseEntity<>("ToDo Deleted Successfully with id:" + Id, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @PatchMapping("{id}/complete")
    public ResponseEntity<ToDoDto> completeToDo(@PathVariable("id") Long Id) {
        ToDoDto updatedtoDoDto = toDoService.completeToDo(Id);

        return new ResponseEntity<>(updatedtoDoDto, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @PatchMapping("{id}/in-complete")
    public ResponseEntity<ToDoDto> inCompleteToDo(@PathVariable("id") Long Id) {
        ToDoDto updatedtoDoDto = toDoService.inCompleteToDo(Id);

        return new ResponseEntity<>(updatedtoDoDto, HttpStatus.OK);
    }

}
