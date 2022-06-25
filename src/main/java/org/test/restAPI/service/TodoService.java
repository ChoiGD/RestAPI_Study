package org.test.restAPI.service;

import org.test.restAPI.dto.PageRequestDTO;
import org.test.restAPI.dto.PageResponseDTO;
import org.test.restAPI.dto.TodoDTO;

public interface TodoService {

    Long register(TodoDTO todoDTO);

    TodoDTO read(Long tno);

    PageResponseDTO<TodoDTO> list(PageRequestDTO pageRequestDTO);

    void remove(Long tno);
}
