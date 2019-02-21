package pl.ninthfolder.todo.api

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus.*
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import pl.ninthfolder.todo.application.dto.NewTodo
import pl.ninthfolder.todo.application.TodoService
import pl.ninthfolder.todo.application.dto.UpdatedTodo
import pl.ninthfolder.todo.domain.todo.Todo
import java.net.URI

@RestController
@RequestMapping("/api/todos")
class TodoEndpoint(val todoService: TodoService) {

    @GetMapping(produces = ["application/json"])
    @ResponseBody
    @ResponseStatus(OK)
    fun getAllTodos(
            @RequestParam(required = false, defaultValue = "ALL") status: String
    ): ResponseEntity<List<Todo>> = ResponseEntity.ok(todoService.getAllTodos(status))

    @PostMapping(consumes = ["application/json"])
    @ResponseStatus(CREATED)
    fun createNewTodo(@RequestBody todoRequest: NewTodo): ResponseEntity<Unit> {
        val todo = todoService.createTodo(todoRequest)
        return ResponseEntity.created(URI.create("/api/todos/${todo.id}")).build()
    }

    @GetMapping(produces = ["application/json"], path = ["/{todoId}"])
    @ResponseBody
    @ResponseStatus(OK)
    fun getTodo(@PathVariable todoId: String): ResponseEntity<Todo> {
        return ResponseEntity.ok(todoService.getTodo(todoId))
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