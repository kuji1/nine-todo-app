package pl.ninthfolder.todo.domain.user

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document
data class User(
        @Id var id: String,
        var username: String,
        var email: String,
        var hashedPassword: String
) {
    constructor(
            username: String, email: String, hashedPassword: String
    ) : this(ObjectId.get().toString(), "", "", "") {
        this.username = username
        this.email = email
        this.hashedPassword = hashedPassword
    }
}