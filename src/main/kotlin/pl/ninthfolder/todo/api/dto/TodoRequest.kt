package pl.ninthfolder.todo.api.dto

import com.fasterxml.jackson.annotation.JsonCreator

data class TodoRequest @JsonCreator constructor(
    val content: String
)