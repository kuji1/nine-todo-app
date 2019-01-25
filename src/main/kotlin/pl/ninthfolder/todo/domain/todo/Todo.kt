package pl.ninthfolder.todo.domain.todo

import java.time.Instant

data class Todo(
    val content: String,
    val status: TodoStatus,
    val createdOn: Instant,
    val modifiedOn: Instant
)