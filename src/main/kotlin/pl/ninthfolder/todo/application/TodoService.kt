package pl.ninthfolder.todo.application

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import pl.ninthfolder.todo.api.TodoEndpoint
import pl.ninthfolder.todo.api.dto.TodoRequest
import pl.ninthfolder.todo.domain.todo.Todo
import pl.ninthfolder.todo.domain.todo.TodoRepository
import pl.ninthfolder.todo.domain.todo.TodoStatus.NEW
import java.time.Instant

@Service
class TodoService(val todoRepository: TodoRepository) {

    fun createTodo(todoRequest: TodoRequest) {
        logger.info("Todo created")
        todoRepository.save(todoRequest.createTodo())
    }

    fun TodoRequest.createTodo(): Todo =
        Todo(
            this.content,
            NEW,
            Instant.now(),
            Instant.now()
        )

    fun getAllTodos(): List<Todo> {
        return todoRepository.findAll()
    }

    fun getTodoById(todoId: String): Todo {
        return todoRepository.findById(todoId)
    }

    companion object {
        val logger: Logger = LoggerFactory.getLogger(TodoEndpoint::class.java)
    }
}