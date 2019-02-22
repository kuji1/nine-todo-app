package pl.ninthfolder.todo.application.dto

import com.fasterxml.jackson.annotation.JsonCreator
import pl.ninthfolder.todo.domain.todo.TodoStatus

data class UpdatedTodo @JsonCreator constructor(
    val title: String,
    val content: String,
    val status: TodoStatus
)