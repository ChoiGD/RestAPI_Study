package org.test.restAPI.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.test.restAPI.dto.PageRequestDTO;
import org.test.restAPI.dto.PageResponseDTO;
import org.test.restAPI.dto.TodoDTO;
import org.test.restAPI.service.TodoService;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@Log4j2
@RequestMapping("/api/todo")
public class TodoController {

    private final TodoService todoService;

    @PostMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Long> register(@RequestBody TodoDTO todoDTO){

        log.info(todoDTO);

        Long tno = todoService.register(todoDTO);

        return Map.of("tno", tno);
    }

    @GetMapping("/{tno}")
    public TodoDTO read(@PathVariable("tno") Long tno){

        log.info("read tno: " + tno);

        return todoService.read(tno);
    }


    @GetMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public PageResponseDTO<TodoDTO> list(PageRequestDTO pageRequestDTO){

        return todoService.list(pageRequestDTO);
    }



}
