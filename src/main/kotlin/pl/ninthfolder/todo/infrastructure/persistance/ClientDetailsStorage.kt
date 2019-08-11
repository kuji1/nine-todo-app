package pl.ninthfolder.todo.infrastructure.persistance

import org.springframework.security.oauth2.provider.client.BaseClientDetails
import org.springframework.stereotype.Repository
import pl.ninthfolder.todo.domain.client.ClientRepository

@Repository
class ClientDetailsStorage(val clientDetailsDocumentDao: ClientDetailsDocumentDao) : ClientRepository {
    override fun saveClient(baseClientDetails: BaseClientDetails): BaseClientDetails = clientDetailsDocumentDao.insert(baseClientDetails)
    override fun findAll(): List<BaseClientDetails> = clientDetailsDocumentDao.findAll()
    override fun findByClientId(clientId: String): BaseClientDetails = clientDetailsDocumentDao.findByClientId(clientId)
}