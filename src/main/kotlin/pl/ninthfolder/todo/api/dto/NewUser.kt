package pl.ninthfolder.todo.api.dto

import pl.ninthfolder.todo.api.validation.Username

data class NewUser constructor (
        @field:Username val username: String,
        val email: String,
        val password: String
)