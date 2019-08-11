package pl.ninthfolder.todo.infrastructure.persistance

import org.springframework.stereotype.Repository
import pl.ninthfolder.todo.domain.client.Client
import pl.ninthfolder.todo.domain.client.ClientRepository

@Repository
class ClientDetailsStorage(val clientDocumentDao: ClientDocumentDao) : ClientRepository {
    override fun saveClient(client: Client): Client = clientDocumentDao.insert(client)
    override fun findAll(): List<Client> = clientDocumentDao.findAll()
    override fun findByClientId(clientId: String): Client = clientDocumentDao.findByClientId(clientId)
}