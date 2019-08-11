package pl.ninthfolder.todo.domain.client

interface ClientRepository {
    fun findByClientId(clientId: String): Client
    fun findAll(): List<Client>
    fun saveClient(client: Client): Client
}