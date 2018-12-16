package pl.folder.todo.api

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus.CREATED
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import pl.folder.todo.api.dto.TodoRequest
import pl.folder.todo.domain.todo.Todo
import pl.folder.todo.domain.todo.TodoStatus
import java.time.Instant

@RestController("/api/todo")
class TodoEndpoint {

    @PostMapping(consumes = ["application/json"])
    @ResponseStatus(CREATED)
    fun createTodo(@RequestBody todoRequest: TodoRequest) {
        val todo = todoRequest.createTodo()
        logger.info("Todo $todo created")
    }

    companion object {
        val logger: Logger = LoggerFactory.getLogger(TodoEndpoint::class.java)
    }

    fun TodoRequest.createTodo(): Todo =
        Todo(
            this.content,
            TodoStatus.NEW,
            Instant.now(),
            Instant.now()
        )
}