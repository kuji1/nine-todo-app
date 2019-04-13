package pl.ninthfolder.todo.infrastructure.security

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Component
import pl.ninthfolder.todo.domain.user.PasswordHasher

@Component
class BCryptPasswordHasher: PasswordHasher {

    override fun hashPassword(password: String): String {
        val encoder = BCryptPasswordEncoder()
        return encoder.encode(password)
    }

}