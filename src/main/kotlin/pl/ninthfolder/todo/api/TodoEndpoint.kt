package pl.ninthfolder.todo.api

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus.*
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException
import pl.ninthfolder.todo.application.dto.NewTodo
import pl.ninthfolder.todo.application.TodoService
import pl.ninthfolder.todo.application.dto.UpdatedTodo
import pl.ninthfolder.todo.domain.todo.Todo
import pl.ninthfolder.todo.infrastructure.persistance.exception.TodoNotFoundException
import java.net.URI

@RestController
@RequestMapping("/api/todos")
class TodoEndpoint(val todoService: TodoService) {

    @GetMapping(produces = ["application/json"], path = ["/{todoId}"])
    @ResponseBody
    @ResponseStatus(OK)
    fun getTodo(@PathVariable todoId: String): ResponseEntity<Todo> {
        try {
            return ResponseEntity.ok(todoService.getTodo(todoId))
        } catch (tnfe: TodoNotFoundException) {
            throw ResponseStatusException(NOT_FOUND, "Todo with id: $todoId not found", tnfe)
        }
    }

    @GetMapping(produces = ["application/json"])
    @ResponseBody
    @ResponseStatus(OK)
    fun getTodos(@RequestParam(required = false, defaultValue = "ALL") status: String): ResponseEntity<List<Todo>> =
            ResponseEntity.ok(todoService.getAllTodos(status))

    @PostMapping(consumes = ["application/json"])
    @ResponseStatus(CREATED)
    fun createTodo(@RequestBody todoRequest: NewTodo): ResponseEntity<Unit> {
        val todo = todoService.createTodo(todoRequest)
        return ResponseEntity.created(URI.create("/api/todos/${todo.id}")).build()
    }

    @PutMapping(consumes = ["application/json"], path = ["/{todoId}"])
    @ResponseStatus(NO_CONTENT)
    fun updateTodo(@PathVariable todoId: String, @RequestBody updatedTodo: UpdatedTodo): ResponseEntity<Unit> {
        todoService.updateTodo(todoId, updatedTodo)
        return ResponseEntity.noContent().build()
    }

    @DeleteMapping(path = ["/{todoId}"])
    @ResponseStatus(NO_CONTENT)
    fun deleteTodo(@PathVariable todoId: String): ResponseEntity<Unit> {
        todoService.deleteTodo(todoId)
        return ResponseEntity.noContent().build()
    }

    companion object {
        val logger: Logger = LoggerFactory.getLogger(TodoEndpoint::class.java)
    }
}