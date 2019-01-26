package pl.ninthfolder.todo.api

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus.OK
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import pl.ninthfolder.todo.api.dto.TodoRequest
import pl.ninthfolder.todo.application.TodoService
import pl.ninthfolder.todo.domain.todo.Todo

@RestController
@RequestMapping("/api/todo")
class TodoEndpoint(val todoService: TodoService) {

    @PostMapping(consumes = ["application/json"])
    @ResponseStatus(OK)
    fun createTodo(@RequestBody todoRequest: TodoRequest): ResponseEntity<Unit> {
        todoService.createTodo(todoRequest)
        return ResponseEntity.ok().build() // TODO - change status to created and return location for getting specific todo
    }

    @GetMapping(produces = ["application/json"])
    @ResponseBody
    @ResponseStatus(OK)
    fun getTodos(): ResponseEntity<List<Todo>> = ResponseEntity.ok(todoService.getAllTodos())

    @GetMapping(produces = ["application/json"], path = ["/{todoId}"])
    @ResponseBody
    @ResponseStatus(OK)
    fun getTodoById(@PathVariable todoId: String): ResponseEntity<Todo> {
        return ResponseEntity.ok(todoService.getTodoById(todoId))
    }

    companion object {
        val logger: Logger = LoggerFactory.getLogger(TodoEndpoint::class.java)
    }
}