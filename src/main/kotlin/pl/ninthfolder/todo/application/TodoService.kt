package pl.ninthfolder.todo.application

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus.NOT_FOUND
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.server.ResponseStatusException
import pl.ninthfolder.todo.api.TodoEndpoint
import pl.ninthfolder.todo.application.dto.NewTodo
import pl.ninthfolder.todo.application.dto.UpdatedTodo
import pl.ninthfolder.todo.domain.todo.Todo
import pl.ninthfolder.todo.domain.todo.TodoRepository
import pl.ninthfolder.todo.infrastructure.persistance.exception.TodoNotFoundException
import java.net.URI
import java.time.Instant

@Component
class TodoService(
        val todoRepository: TodoRepository
): TodoEndpoint {

    override fun getTodo(@PathVariable todoId: String): ResponseEntity<Todo> {
        try {
            return ResponseEntity.ok(todoRepository.findById(todoId))
        } catch (tnfe: TodoNotFoundException) {
            throw ResponseStatusException(NOT_FOUND, "Todo with id: $todoId not found", tnfe)
        }
    }

    override fun getTodos(@RequestParam(required = false, defaultValue = "ALL") status: String): ResponseEntity<List<Todo>> {
        return ResponseEntity.ok(todoRepository.findAllByStatus(status))
    }

    override fun createTodo(@RequestBody newTodo: NewTodo): ResponseEntity<Unit> {
        val todo = todoRepository.save(Todo.fromNewTodo(newTodo))
        return ResponseEntity.created(URI.create("/api/todos/${todo.id}")).build()
    }

    override fun updateTodo(@PathVariable todoId: String, @RequestBody updatedTodo: UpdatedTodo): ResponseEntity<Unit> {
        val todo = todoRepository.findById(todoId)
        todo.title = updatedTodo.title
        todo.content = updatedTodo.content
        todo.status = updatedTodo.status
        todo.modifiedOn = Instant.now()
        todoRepository.save(todo)
        return ResponseEntity.noContent().build()
    }

    override fun deleteTodo(@PathVariable todoId: String): ResponseEntity<Unit> {
        todoRepository.deleteById(todoId)
        return ResponseEntity.noContent().build()
    }

    companion object {
        val logger: Logger = LoggerFactory.getLogger(TodoService::class.java)
    }
}