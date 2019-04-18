package pl.ninthfolder.todo.integration.api

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.HttpStatus.CREATED
import org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY
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

	@Test
	fun shouldNotSaveUserIfUserNameIsIncorrect() {
		//given
		val userRequest = NewUser("test_user", "testuser@9folder.pl", "password")

		//when
		val response = testRestTemplate.postForEntity(
				USER_PATH,
				userRequest,
				String::class.java
		)

		//then
		assert(response.statusCode == UNPROCESSABLE_ENTITY)
		assert(response.body!!.contains("Username should contain only letters and numbers"))
		assert(userDocumentDao.count() == 0L)
	}

	@Test
	fun shouldNotSaveUserIfUsernameAlreadyExist() {
		//given
		val firstUserRequest = NewUser("testuser", "testuser1@9folder.pl", "password")
		val secondUserRequest = NewUser("testuser", "testuser2@9folder.pl", "password")
		testRestTemplate.postForEntity(
				USER_PATH,
				firstUserRequest,
				String::class.java
		)

		//when
		val response = testRestTemplate.postForEntity(
				USER_PATH,
				secondUserRequest,
				String::class.java
		)

		//then
		assert(response.statusCode == UNPROCESSABLE_ENTITY)
		assert(response.body!!.contains("Username must be unique"))
		assert(userDocumentDao.count() == 1L)
	}

}

