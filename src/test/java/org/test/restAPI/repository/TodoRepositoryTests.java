package org.test.restAPI.repository;


import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.test.restAPI.domain.Todo;
import org.test.restAPI.dto.PageRequestDTO;

import java.time.LocalDate;
import java.util.stream.IntStream;

@SpringBootTest
@Log4j2
public class TodoRepositoryTests {

    @Autowired
    private TodoRepository todoRepository;

    @Test
    public void testsInsert(){

        IntStream.rangeClosed(1,100).forEach(i -> {

            LocalDate dueDate = LocalDate.now().plusDays(i % 10);

            Todo todo = Todo.builder()
                    .title("Todo.." + i)
                    .writer("user" + (i% 10))
                    .dueDate(dueDate)
                    .build();

            todoRepository.save(todo);
        });

    }

    @Test
    public void testSearch(){
        PageRequestDTO pageRequestDTO = PageRequestDTO.builder()
                .keyword("1")
                .from(LocalDate.of(2022,06,10))
                .to(LocalDate.of(2022,06,13))
                .build();

        todoRepository.list(pageRequestDTO);

    }


}
