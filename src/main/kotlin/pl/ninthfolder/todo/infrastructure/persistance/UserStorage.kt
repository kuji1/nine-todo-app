package pl.ninthfolder.todo.infrastructure.persistance

import org.springframework.stereotype.Repository
import pl.ninthfolder.todo.domain.user.User
import pl.ninthfolder.todo.domain.user.UserRepository
import pl.ninthfolder.todo.infrastructure.persistance.exception.UserNotFoundException

@Repository
class UserStorage(val userDocumentDao: UserDocumentDao) : UserRepository {
    override fun existsByUsername(username: String): Boolean = userDocumentDao.existsByUsername(username)

    override fun findByEmail(email: String): User = userDocumentDao.findByEmail(email)

    override fun findByUsername(username: String): User = userDocumentDao.findByUsername(username)

    override fun save(user: User): User = userDocumentDao.save(user)

    override fun findById(id: String): User = userDocumentDao.findById(id).orElseThrow{ UserNotFoundException("User with id: $id not found") }
}