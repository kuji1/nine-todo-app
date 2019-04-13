package pl.ninthfolder.todo.domain.user

interface UserRepository {
    fun save(user: User): User
}