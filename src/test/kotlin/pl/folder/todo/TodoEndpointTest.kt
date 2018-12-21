package pl.folder.todo

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.HttpStatus.OK
import org.springframework.http.ResponseEntity
import org.springframework.test.context.junit.jupiter.SpringExtension
import pl.folder.todo.api.dto.TodoRequest
import pl.folder.todo.domain.todo.Todo
import pl.folder.todo.domain.todo.TodoStatus.NEW
import java.time.Instant

@ExtendWith(SpringExtension::class)
@SpringBootTest(webEnvironment= WebEnvironment.RANDOM_PORT)
class TodoEndpointTest(
	@Autowired val testRestTemplate: TestRestTemplate
) {

	@Test
	fun shouldReturnOkForProperTodoRequest() {
		//given
		val todoRequest = TodoRequest(
			"some todo"
		)

		//when
		val response = testRestTemplate.postForEntity(
			"/api/todo",
			todoRequest,
			ResponseEntity::class.java
		)

		//then
		response!!
		assert(response.statusCode == OK)
	}

	@Test
	fun shouldGetTodos() {
		//given
		val todo1 = Todo("test 1", NEW, Instant.now(), Instant.now())
		val todo2 = Todo("test 1", NEW, Instant.now(), Instant.now())
		val todo3 = Todo("test 1", NEW, Instant.now(), Instant.now())

		//when
		val response = testRestTemplate.getForObject(
			"/api/todo",
			List::class.java
		)

		//then
		assert(response.size == 3)
	}

}

