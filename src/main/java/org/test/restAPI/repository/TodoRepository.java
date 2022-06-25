package org.test.restAPI.repository;

import org.test.restAPI.repository.search.TodoSearch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.test.restAPI.domain.Todo;

public interface TodoRepository extends JpaRepository<Todo, Long>, TodoSearch {
}
