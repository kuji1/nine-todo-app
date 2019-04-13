package pl.ninthfolder.todo.application

import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
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

    override fun saveUser(user: NewUser): ResponseEntity<User> {
        val user = userRepository.save(User(
                user.username,
                user.email,
                passwordHasher.hashPassword(user.password)
        ))
        return ResponseEntity.created(URI.create("/api/todos/${user.id}")).build()
    }
}