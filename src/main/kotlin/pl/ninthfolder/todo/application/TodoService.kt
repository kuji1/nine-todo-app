package pl.ninthfolder.todo.application

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import pl.ninthfolder.todo.api.TodoEndpoint
import pl.ninthfolder.todo.application.dto.NewTodo
import pl.ninthfolder.todo.application.dto.UpdatedTodo
import pl.ninthfolder.todo.domain.todo.Todo
import pl.ninthfolder.todo.domain.todo.TodoRepository
import pl.ninthfolder.todo.domain.todo.TodoStatus.NEW
import java.time.Instant

@Service
class TodoService(val todoRepository: TodoRepository) {

    fun createTodo(todoRequest: NewTodo): Todo  = todoRepository.save(todoRequest.createNewTodo())

    fun NewTodo.createNewTodo(): Todo =
        Todo(
            this.content,
            NEW,
            Instant.now(),
            Instant.now()
        )

    fun getAllTodos(): List<Todo> {
        return todoRepository.findAll()
    }

    fun getTodo(todoId: String): Todo {
        return todoRepository.findById(todoId)
    }

    fun updateTodo(todoId: String, updatedTodo: UpdatedTodo) {
        val todo = todoRepository.findById(todoId)
        todo.content = updatedTodo.content
        todo.status = updatedTodo.status
        todoRepository.save(todo)
    }

    fun deleteTodo(todoId: String) {
        todoRepository.deleteById(todoId)
    }

    companion object {
        val logger: Logger = LoggerFactory.getLogger(TodoEndpoint::class.java)
    }
}