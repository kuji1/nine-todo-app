package pl.ninthfolder.todo.integration.api

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.HttpStatus.CREATED
import org.springframework.http.ResponseEntity
import pl.ninthfolder.todo.api.dto.NewUser
import pl.ninthfolder.todo.infrastructure.persistance.UserDocumentDao

@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
class UserServiceTest(
	@Autowired val testRestTemplate: TestRestTemplate,
	@Autowired val userDocumentDao: UserDocumentDao
) {

	companion object {
		const val USER_PATH = "/api/users"
	}

	@BeforeEach
	fun beforeEach() {
		userDocumentDao.deleteAll()
	}

	@Test
	fun shouldCreateNewUser() {
		//given
		val userRequest = NewUser("testuser", "testuser@9folder.pl", "password")

		//when
		val response = testRestTemplate.postForEntity(
				USER_PATH,
			userRequest,
			ResponseEntity::class.java
		)

		//then
		assert(response.statusCode == CREATED)
		assert(userDocumentDao.count() == 1L)
	}

}

