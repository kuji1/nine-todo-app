package pl.ninthfolder.todo.infrastructure.persistance

import org.springframework.data.mongodb.repository.MongoRepository
import pl.ninthfolder.todo.domain.user.User

interface UserDocumentDao : MongoRepository<User, String> {
    fun findByUsername(username: String): User
    fun findByEmail(email: String): User
    fun existsByUsername(username: String): Boolean
}