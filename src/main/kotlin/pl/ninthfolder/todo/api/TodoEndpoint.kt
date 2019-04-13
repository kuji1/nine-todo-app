package pl.ninthfolder.todo.api

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import pl.ninthfolder.todo.api.dto.NewTodo
import pl.ninthfolder.todo.api.dto.UpdatedTodo
import pl.ninthfolder.todo.domain.todo.Todo

@RestController
@RequestMapping("/api/todos")
interface TodoEndpoint {

    @GetMapping(produces = ["application/json"], path = ["/{todoId}"])
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    fun getTodo(@PathVariable todoId: String): ResponseEntity<Todo>

    @GetMapping(produces = ["application/json"])
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    fun getTodos(@RequestParam(required = false, defaultValue = "ALL") status: String): ResponseEntity<List<Todo>>

    @PostMapping(consumes = ["application/json"])
    @ResponseStatus(HttpStatus.CREATED)
    fun createTodo(@RequestBody newTodo: NewTodo): ResponseEntity<Unit>

    @PutMapping(consumes = ["application/json"], path = ["/{todoId}"])
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun updateTodo(@PathVariable todoId: String, @RequestBody updatedTodo: UpdatedTodo): ResponseEntity<Unit>

    @DeleteMapping(path = ["/{todoId}"])
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteTodo(@PathVariable todoId: String): ResponseEntity<Unit>
}