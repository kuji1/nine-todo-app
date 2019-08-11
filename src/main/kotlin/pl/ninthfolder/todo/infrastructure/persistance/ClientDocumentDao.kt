package pl.ninthfolder.todo.infrastructure.persistance

import org.springframework.data.mongodb.repository.MongoRepository
import pl.ninthfolder.todo.domain.client.Client

interface ClientDocumentDao : MongoRepository<Client, String> {
    fun findByClientId(clientId: String): Client
}