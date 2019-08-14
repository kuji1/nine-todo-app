package pl.ninthfolder.todo.application

import org.springframework.dao.DuplicateKeyException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException
import pl.ninthfolder.todo.api.UserEndpoint
import pl.ninthfolder.todo.api.dto.NewUser
import pl.ninthfolder.todo.domain.user.PasswordHasher
import pl.ninthfolder.todo.domain.user.User
import pl.ninthfolder.todo.domain.user.UserRepository
import pl.ninthfolder.todo.infrastructure.persistance.exception.UserNotFoundException
import java.net.URI

@Service
class UserService(
        val passwordHasher: PasswordHasher,
        val userRepository: UserRepository
) : UserEndpoint {

    override fun saveUser(newUser: NewUser): ResponseEntity<User> {
        val user: User
        try {
            user = userRepository.save(User(
                           newUser.username,
                           newUser.email,
                           passwordHasher.hashPassword(newUser.password)
                   ))
        } catch (dke: DuplicateKeyException) { // TODO - throw exception in userRepository?
            throw ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Username/email already registered.", dke)
        }
        return ResponseEntity.created(URI.create("/api/users/${user.id}")).build()
    }

    override fun getUser(userId: String): ResponseEntity<User> {
        try {
            return ResponseEntity.ok(userRepository.findById(userId))
        } catch (unfe: UserNotFoundException) {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, "User not found.", unfe)
        }
    }
}