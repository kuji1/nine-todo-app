package pl.ninthfolder.todo.api.dto

import pl.ninthfolder.todo.domain.todo.TodoStatus

data class UpdatedTodo constructor(
    val title: String,
    val content: String,
    val status: TodoStatus
)