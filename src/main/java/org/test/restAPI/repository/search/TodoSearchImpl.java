package org.test.restAPI.repository.search;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import org.test.restAPI.domain.QTodo;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.test.restAPI.domain.Todo;
import org.test.restAPI.dto.PageRequestDTO;
import org.test.restAPI.dto.TodoDTO;

import java.util.List;

public class TodoSearchImpl extends QuerydslRepositorySupport implements TodoSearch{

    public TodoSearchImpl() {
        super(Todo.class);
    }

    @Override
    public Page<TodoDTO> list(PageRequestDTO pageRequestDTO) {

        QTodo todo = QTodo.todo;

        JPQLQuery<Todo> jpqlQuery = from (todo);

        if(pageRequestDTO.getKeyword() != null){

            jpqlQuery.where(todo.title.contains(pageRequestDTO.getKeyword()));
        }

        if(pageRequestDTO.getFrom() != null && pageRequestDTO.getTo() != null){

            jpqlQuery.where(todo.dueDate.between(pageRequestDTO.getFrom(), pageRequestDTO.getTo()));
        }

        if(pageRequestDTO.getCompleted() != null){

            jpqlQuery.where(todo.complete.eq(pageRequestDTO.getCompleted()));
        }

        JPQLQuery<TodoDTO> dtojpqlQuery = jpqlQuery.select(Projections.bean(TodoDTO.class,
                todo.tno,
                todo.title,
                todo.writer,
                todo.complete,
                todo.dueDate
        ));

        Pageable pageable = PageRequest.of(pageRequestDTO.getPage() - 1,
                pageRequestDTO.getSize(), Sort.by("tno").descending());

        this.getQuerydsl().applyPagination(pageable, dtojpqlQuery);

        List<TodoDTO> dtoList = dtojpqlQuery.fetch();

        long totalCount = dtojpqlQuery.fetchCount();

        return new PageImpl<>(dtoList, pageable, totalCount);
    }
}
