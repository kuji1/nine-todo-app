package pl.ninthfolder.todo.domain.todo

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import pl.ninthfolder.todo.application.dto.NewTodo
import pl.ninthfolder.todo.domain.todo.TodoStatus.NEW
import java.time.Instant

@Document
class Todo(
    @Id var id: String,
    var title: String,
    var content: String,
    var status: TodoStatus,
    var createdOn: Instant,
    var modifiedOn: Instant
) {
    constructor(
        title: String, content: String, status: TodoStatus, createdOn: Instant, modifiedOn: Instant
    ) : this(ObjectId.get().toString(), "", "", NEW, Instant.now(), Instant.now()) {
        this.title = title
        this.content = content
        this.status = status
        this.createdOn = createdOn
        this.modifiedOn = modifiedOn
    }

    companion object {
        fun fromNewTodo(newTodo: NewTodo): Todo =
                Todo(
                        newTodo.title,
                        newTodo.content,
                        NEW,
                        Instant.now(),
                        Instant.now()
                )
    }

}