package pl.ninthfolder.todo.api

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
import pl.ninthfolder.todo.api.dto.TodoRequest
import pl.ninthfolder.todo.application.TodoService
import pl.ninthfolder.todo.domain.todo.Todo

@RestController("/api/todo")
class TodoEndpoint(val todoService: TodoService) {

    @PostMapping(consumes = ["application/json"])
    @ResponseStatus(OK)
    fun createTodo(@RequestBody todoRequest: TodoRequest): ResponseEntity<Unit> {
        todoService.createTodo(todoRequest)
        return ResponseEntity.ok().build()
    }

    @GetMapping(produces = ["application/json"])
    @ResponseBody
    fun getTodos(): ResponseEntity<List<Todo>> {
        return ResponseEntity.ok(todoService.getTodos())
    }

    companion object {
        val logger: Logger = LoggerFactory.getLogger(TodoEndpoint::class.java)
    }
}