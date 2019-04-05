package pl.ninthfolder.todo.infrastructure.persistance

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import pl.ninthfolder.todo.infrastructure.persistance.exception.TodoNotFoundException

@SpringBootTest
class TodoStorageTest(
        @Autowired val todoDocumentDao: TodoDocumentDao,
        @Autowired val todoStorage: TodoStorage
) {

    private val NON_EXISTING_TODO_ID = "1q2w3e4r5t6y7u8i9o0p"

    @BeforeEach
    fun beforeEach() {
        todoDocumentDao.deleteAll()
    }

    @Test
    fun shouldThrowTodoNotFoundException() {
        assertThrows<TodoNotFoundException> {
            todoStorage.findById(NON_EXISTING_TODO_ID)
        }
    }
}

