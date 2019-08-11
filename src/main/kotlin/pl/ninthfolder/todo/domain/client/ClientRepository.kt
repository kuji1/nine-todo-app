package pl.ninthfolder.todo.domain.client

import org.springframework.security.oauth2.provider.client.BaseClientDetails

interface ClientRepository {
    fun findByClientId(clientId: String): BaseClientDetails
    fun findAll(): List<BaseClientDetails>
    fun saveClient(baseClientDetails: BaseClientDetails): BaseClientDetails
}