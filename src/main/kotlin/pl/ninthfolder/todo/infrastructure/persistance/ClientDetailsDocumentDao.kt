package pl.ninthfolder.todo.infrastructure.persistance

import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.security.oauth2.provider.client.BaseClientDetails

interface ClientDetailsDocumentDao : MongoRepository<BaseClientDetails, String> {
    fun findByClientId(clientId: String): BaseClientDetails
}