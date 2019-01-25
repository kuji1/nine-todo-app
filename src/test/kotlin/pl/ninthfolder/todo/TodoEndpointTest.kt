package pl.ninthfolder.todo

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.http.HttpStatus.OK
import org.springframework.http.ResponseEntity
import pl.ninthfolder.todo.api.dto.TodoRequest
import pl.ninthfolder.todo.domain.todo.Todo
import pl.ninthfolder.todo.domain.todo.TodoRepository
import pl.ninthfolder.todo.domain.todo.TodoStatus.NEW
import pl.ninthfolder.todo.infrastructure.persistance.TodoDocumentDao
import java.time.Instant

@SpringBootTest(webEnvironment= WebEnvironment.RANDOM_PORT)
class TodoEndpointTest(
	@Autowired val testRestTemplate: TestRestTemplate,
	@Autowired val todoDocumentDao: TodoDocumentDao
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
		assert(response.statusCode == OK)
		assert(todoDocumentDao.count() == 1L)
	}

	@Test
	fun shouldGetAllTodos() {
		//given
		val todo1 = Todo("test 1", NEW, Instant.now(), Instant.now())
		val todo2 = Todo("test 1", NEW, Instant.now(), Instant.now())
		val todo3 = Todo("test 1", NEW, Instant.now(), Instant.now())
		todoDocumentDao.save(todo1)
		todoDocumentDao.save(todo2)
		todoDocumentDao.save(todo3)

		//when
		val response = testRestTemplate.getForObject(
			"/api/todo",
			List::class.java
		)

		//then
		assert(response.size == 3)
	}

}

