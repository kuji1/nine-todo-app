package pl.ninthfolder.todo.infrastructure.persistance

import org.springframework.stereotype.Repository
import pl.ninthfolder.todo.domain.todo.Todo
import pl.ninthfolder.todo.domain.todo.TodoRepository
import pl.ninthfolder.todo.infrastructure.persistance.exception.TodoNotFoundException

@Repository
class TodoStorage(val todoDocumentDao: TodoDocumentDao) : TodoRepository {

    override fun deleteById(todoId: String): Unit = todoDocumentDao.deleteById(todoId)

    override fun save(todo: Todo): Todo = todoDocumentDao.save(todo)

    override fun findAll(): List<Todo> = todoDocumentDao.findAll()

    override fun findAllByStatus(status: String): List<Todo> = todoDocumentDao.findAllByStatus(status)

    override fun findById(todoId: String): Todo =
            todoDocumentDao.findById(todoId).orElseThrow { TodoNotFoundException("No todo with id: $todoId") }
}