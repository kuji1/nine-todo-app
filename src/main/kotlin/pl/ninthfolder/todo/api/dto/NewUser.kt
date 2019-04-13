package pl.ninthfolder.todo.api.dto

import com.fasterxml.jackson.annotation.JsonCreator

data class NewUser @JsonCreator constructor (
        val username: String,
        val email: String,
        val password: String
)