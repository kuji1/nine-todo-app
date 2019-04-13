package pl.ninthfolder.todo.api.dto

import com.fasterxml.jackson.annotation.JsonCreator

data class NewTodo @JsonCreator constructor(
    val title: String,
    val content: String
)