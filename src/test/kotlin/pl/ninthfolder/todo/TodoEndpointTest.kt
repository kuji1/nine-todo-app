package pl.ninthfolder.todo

import org.bson.types.ObjectId
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.HttpStatus.CREATED
import org.springframework.http.HttpStatus.OK
import org.springframework.http.ResponseEntity
import pl.ninthfolder.todo.application.dto.NewTodo
import pl.ninthfolder.todo.application.dto.UpdatedTodo
import pl.ninthfolder.todo.domain.todo.Todo
import pl.ninthfolder.todo.domain.todo.TodoStatus.IN_PROGRESS
import pl.ninthfolder.todo.domain.todo.TodoStatus.NEW
import pl.ninthfolder.todo.infrastructure.persistance.TodoDocumentDao
import java.time.Instant

@SpringBootTest(webEnvironment= WebEnvironment.RANDOM_PORT)
class TodoEndpointTest(
	@Autowired val testRestTemplate: TestRestTemplate,
	@Autowired val todoDocumentDao: TodoDocumentDao
) {

    private val TODO_PATH = "/api/todos"

	@BeforeEach
	fun beforeEach() {
		todoDocumentDao.deleteAll()
	}

	@Test
	fun shouldReturnOkForProperTodoRequest() {
		//given
		val todoRequest = NewTodo(
			"some todo"
		)

		//when
		val response = testRestTemplate.postForEntity(
            TODO_PATH,
			todoRequest,
			ResponseEntity::class.java
		)

		//then
		assert(response.statusCode == CREATED)
		assert(todoDocumentDao.count() == 1L)
	}

	@Test
	fun shouldGetAllTodos() {
		//given
		val objectId1 = ObjectId.get().toString()
		val objectId2 = ObjectId.get().toString()
		val todo1 = Todo(objectId1, "test 1", NEW, Instant.now(), Instant.now())
		val todo2 = Todo(objectId2, "test 2", NEW, Instant.now(), Instant.now())
		todoDocumentDao.save(todo1)
		todoDocumentDao.save(todo2)

		//when
		val response = testRestTemplate.getForObject(
            TODO_PATH,
			List::class.java
		)

		//then
		assert(response.size == 2)
	}

	@Test
	fun shouldGetTodoById() {
		//given
		val objectId1 = ObjectId.get().toString()
		val objectId2 = ObjectId.get().toString()
		val todo1 = Todo(objectId1, "test 1", NEW, Instant.now(), Instant.now())
		val todo2 = Todo(objectId2, "test 2", NEW, Instant.now(), Instant.now())
		todoDocumentDao.save(todo1)
		todoDocumentDao.save(todo2)

		//when
		val response = testRestTemplate.getForObject(
			"$TODO_PATH/$objectId1",
			Todo::class.java
		)

		//then
		assert(response.content == todo1.content)
		assert(response.status == todo1.status)
		assert(response.createdOn == todo1.createdOn)
		assert(response.modifiedOn == todo1.modifiedOn)
	}

	@Test
	fun shouldUpdateTodoStatus() {
		//given
		val objectId = ObjectId.get().toString()
		val todo = Todo(objectId, "test", NEW, Instant.now(), Instant.now())
		todoDocumentDao.save(todo)

		val update = UpdatedTodo("update", IN_PROGRESS)

		//when
		testRestTemplate.put(
			"$TODO_PATH/$objectId",
			update)

		//then
		val updatedTodo = todoDocumentDao.findById(objectId).get()
		assert(updatedTodo.content == "update")
		assert(updatedTodo.status == IN_PROGRESS)
	}

	@Test
	fun shouldDeleteTodo() {
		//given
		val objectId1 = ObjectId.get().toString()
		val objectId2 = ObjectId.get().toString()
		val todo1 = Todo(objectId1, "test 1", NEW, Instant.now(), Instant.now())
		val todo2 = Todo(objectId2, "test 2", NEW, Instant.now(), Instant.now())
		todoDocumentDao.save(todo1)
		todoDocumentDao.save(todo2)

		//when
		testRestTemplate.delete(
			"$TODO_PATH/$objectId1"
		)

		//then
		assert(todoDocumentDao.count() == 1L)
		assert(!todoDocumentDao.findById(objectId1).isPresent)
		assert(todoDocumentDao.findById(objectId2).isPresent)
	}

}

