package org.test.restAPI.repository.search;

import org.springframework.data.domain.Page;
import org.test.restAPI.dto.PageRequestDTO;
import org.test.restAPI.dto.TodoDTO;

public interface TodoSearch {

    Page<TodoDTO> list(PageRequestDTO pageRequestDTO);
}
