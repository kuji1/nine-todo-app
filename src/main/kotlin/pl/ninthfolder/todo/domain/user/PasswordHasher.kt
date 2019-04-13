package pl.ninthfolder.todo.domain.user

interface PasswordHasher {
    fun hashPassword(password: String): String
}