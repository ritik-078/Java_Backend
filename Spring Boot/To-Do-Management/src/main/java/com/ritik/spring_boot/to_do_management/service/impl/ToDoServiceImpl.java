package com.ritik.spring_boot.to_do_management.service.impl;

import com.ritik.spring_boot.to_do_management.dto.ToDoDto;
import com.ritik.spring_boot.to_do_management.entity.ToDo;
import com.ritik.spring_boot.to_do_management.exceptions.ResourceNotFoundException;
import com.ritik.spring_boot.to_do_management.repository.ToDoRepository;
import com.ritik.spring_boot.to_do_management.service.ToDoService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ToDoServiceImpl implements ToDoService {
    private ToDoRepository toDoRepository;
    private ModelMapper modelMapper;

    @Override
    public ToDoDto addToDo(ToDoDto toDoDto) {

        ToDo toDo = modelMapper.map(toDoDto, ToDo.class);

        ToDo savedtoDo = toDoRepository.save(toDo);

        ToDoDto savedtoDoDto = modelMapper.map(savedtoDo, ToDoDto.class);

        return savedtoDoDto;

    }

    @Override
    public ToDoDto getToDo(Long Id) {
        ToDo toDo = toDoRepository.findById(Id).orElseThrow(
                () -> new ResourceNotFoundException("ToDo not found with id:" + Id)
        );
        return modelMapper.map(toDo, ToDoDto.class);
    }

    @Override
    public List<ToDoDto> getAllToDo() {
        List<ToDo> allToDo = toDoRepository.findAll();
        return allToDo.stream().map((toDo) -> modelMapper.map(toDo, ToDoDto.class)).collect(Collectors.toList());

    }

    @Override
    public ToDoDto updateToDo(ToDoDto toDoDto, Long Id) {
        ToDo toDo = toDoRepository.findById(Id).orElseThrow(
                () -> new ResourceNotFoundException("ToDO not found with id:" + Id)
        );

        toDo.setTitle(toDoDto.getTitle());
        toDo.setDescription(toDoDto.getDescription());
        toDo.setCompleted(toDoDto.isCompleted());

        ToDo updatedtoDo = toDoRepository.save(toDo);

        return modelMapper.map(updatedtoDo, ToDoDto.class);
    }

    @Override
    public void deleteToDo(Long Id) {
        toDoRepository.findById(Id).orElseThrow(
                () -> new ResourceNotFoundException("ToDO not found with id:" + Id)
        );
        toDoRepository.deleteById(Id);
    }

    @Override
    public ToDoDto completeToDo(Long Id) {
        ToDo toDo = toDoRepository.findById(Id).orElseThrow(
                () -> new ResourceNotFoundException("ToDO not found with id:" + Id)
        );

        toDo.setCompleted(Boolean.TRUE);
        ToDo updatedtoDo = toDoRepository.save(toDo);

        return modelMapper.map(updatedtoDo, ToDoDto.class);
    }

    @Override
    public ToDoDto inCompleteToDo(Long Id) {
        ToDo toDo = toDoRepository.findById(Id).orElseThrow(
                () -> new ResourceNotFoundException("ToDO not found with id:" + Id)
        );

        toDo.setCompleted(Boolean.FALSE);
        ToDo updatedtoDo = toDoRepository.save(toDo);

        return modelMapper.map(updatedtoDo, ToDoDto.class);
    }
}
