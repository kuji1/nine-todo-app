package pl.ninthfolder.todo.api

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import pl.ninthfolder.todo.api.dto.NewUser
import pl.ninthfolder.todo.domain.user.User
import javax.validation.Valid

@RestController
@RequestMapping("/api/users")
interface UserEndpoint {

    @PostMapping(consumes = ["application/json"])
    @ResponseStatus(HttpStatus.CREATED)
    fun saveUser(@RequestBody @Valid newUser: NewUser): ResponseEntity<User>

    @GetMapping(produces = ["application/json"], path = ["/{userId}"])
    @ResponseStatus(HttpStatus.OK)
    fun getUser(@PathVariable userId: String): ResponseEntity<User>

}