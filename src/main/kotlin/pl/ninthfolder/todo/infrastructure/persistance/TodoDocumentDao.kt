package pl.ninthfolder.todo.infrastructure.persistance

import org.springframework.data.mongodb.repository.MongoRepository
import pl.ninthfolder.todo.domain.todo.Todo

interface TodoDocumentDao : MongoRepository<Todo, String> {
    fun findAllByStatus(status: String): List<Todo>
}