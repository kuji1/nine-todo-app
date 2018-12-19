package pl.folder.todo.api

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus.OK
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import pl.folder.todo.api.dto.TodoRequest
import pl.folder.todo.domain.todo.Todo
import pl.folder.todo.domain.todo.TodoStatus
import java.time.Instant

@RestController("/api/todo")
class TodoEndpoint {

    @PostMapping(consumes = ["application/json"])
    @ResponseStatus(OK)
    fun createTodo(@RequestBody todoRequest: TodoRequest): ResponseEntity<Unit> {
        val todo = todoRequest.createTodo()
        logger.info("Todo $todo created")
        return ResponseEntity.ok().build()
    }

    @GetMapping(produces = ["application/json"])
    @ResponseBody
    fun getTodos(): ResponseEntity<List<Todo>> {
        val todo1 = Todo("test 1", TodoStatus.NEW, Instant.now(), Instant.now())
        val todo2 = Todo("test 1", TodoStatus.NEW, Instant.now(), Instant.now())
        val todo3 = Todo("test 1", TodoStatus.NEW, Instant.now(), Instant.now())
        return ResponseEntity.ok(listOf(todo1, todo2, todo3))
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