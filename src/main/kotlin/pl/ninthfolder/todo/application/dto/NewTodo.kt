package pl.ninthfolder.todo.application.dto

import com.fasterxml.jackson.annotation.JsonCreator

data class NewTodo @JsonCreator constructor(
    val title: String,
    val content: String
)