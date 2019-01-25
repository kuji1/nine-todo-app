package pl.ninthfolder.todo.domain.todo

interface TodoRepository {
    fun save(todo: Todo): Todo
    fun findAll(): List<Todo>
}