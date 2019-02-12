package pl.ninthfolder.todo.api

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus.NO_CONTENT
import org.springframework.http.HttpStatus.OK
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import pl.ninthfolder.todo.application.dto.NewTodo
import pl.ninthfolder.todo.application.TodoService
import pl.ninthfolder.todo.application.dto.UpdatedTodo
import pl.ninthfolder.todo.domain.todo.Todo

@RestController
@RequestMapping("/api/todos")
class TodoEndpoint(val todoService: TodoService) {

    @GetMapping(produces = ["application/json"])
    @ResponseBody
    @ResponseStatus(OK)
    fun getAllTodos(): ResponseEntity<List<Todo>> = ResponseEntity.ok(todoService.getAllTodos())

    @PostMapping(consumes = ["application/json"])
    @ResponseStatus(OK)
    fun createNewTodo(@RequestBody todoRequest: NewTodo): ResponseEntity<Unit> {
        todoService.createTodo(todoRequest)
        return ResponseEntity.ok().build() // TODO - change status to created and return location for getting specific todo
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