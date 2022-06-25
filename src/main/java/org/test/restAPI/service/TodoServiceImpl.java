package org.test.restAPI.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.test.restAPI.domain.Todo;
import org.test.restAPI.dto.PageRequestDTO;
import org.test.restAPI.dto.PageResponseDTO;
import org.test.restAPI.dto.TodoDTO;
import org.test.restAPI.repository.TodoRepository;

import java.util.Optional;


@Log4j2
@RequiredArgsConstructor
@Service
public class TodoServiceImpl implements TodoService{

    private final ModelMapper modelMapper;
    private final TodoRepository todoRepository;

    @Override
    public Long register(TodoDTO todoDTO) {

        Todo todo = modelMapper.map(todoDTO, Todo.class);

        Long tno = todoRepository.save(todo).getTno();

        return tno;
    }

    @Override
    public TodoDTO read(Long tno) {

        Optional<Todo> result = todoRepository.findById(tno);

        Todo todo = result.orElseThrow();

        return modelMapper.map(todo, TodoDTO.class);
    }

    @Override
    public PageResponseDTO<TodoDTO> list(PageRequestDTO pageRequestDTO) {

        Page<TodoDTO> result = todoRepository.list(pageRequestDTO);

        return PageResponseDTO.<TodoDTO>withAll()
                .pageRequestDTO(pageRequestDTO)
                .dtoList(result.toList())
                .total((int)result.getTotalElements())
                .build();
    }

    @Override
    public void remove(Long tno) {
        todoRepository.deleteById(tno);
    }
}
