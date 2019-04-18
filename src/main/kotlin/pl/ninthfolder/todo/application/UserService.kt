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
        } catch (dke: DuplicateKeyException) {
            throw ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Username/email already registered.", dke)
        }
        return ResponseEntity.created(URI.create("/api/todos/${user.id}")).build()
    }
}