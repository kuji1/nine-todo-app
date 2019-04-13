package pl.ninthfolder.todo.infrastructure.persistance

import org.springframework.stereotype.Repository
import pl.ninthfolder.todo.domain.user.User
import pl.ninthfolder.todo.domain.user.UserRepository

@Repository
class UserStorage(val userDocumentDao: UserDocumentDao) : UserRepository {

    override fun save(user: User): User {
        return userDocumentDao.save(user)
    }
}