package pl.ninthfolder.todo.infrastructure.persistance

import org.springframework.stereotype.Repository
import pl.ninthfolder.todo.domain.todo.Todo
import pl.ninthfolder.todo.domain.todo.TodoRepository

@Repository
class TodoStorage(val todoDocumentDao: TodoDocumentDao) : TodoRepository {

    override fun save(todo: Todo) = todoDocumentDao.save(todo)

    override fun findAll(): List<Todo> = todoDocumentDao.findAll()
}