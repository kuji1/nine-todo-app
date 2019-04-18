package pl.ninthfolder.todo.domain.user

interface UserRepository {
    fun save(user: User): User
    fun existsByUsername(username: String): Boolean
    fun findByUsername(username: String): User
    fun findByEmail(email: String): User
}