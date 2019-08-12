package pl.ninthfolder.todo.domain.client

import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.oauth2.provider.ClientDetails

@Document
class Client constructor(
        private val clientId: String,
        private val clientSecret: String,
        private val accessTokenValiditySeconds: Int,
        private val refreshTokenValiditySeconds: Int,
        private val authorizedGrantTypes: MutableSet<String>,
        private val authorities: MutableCollection<GrantedAuthority>
) : ClientDetails {

    override fun isSecretRequired(): Boolean = true

    override fun getClientId(): String = clientId

    override fun getClientSecret(): String = clientSecret

    override fun getAccessTokenValiditySeconds(): Int = accessTokenValiditySeconds

    override fun getRefreshTokenValiditySeconds(): Int = refreshTokenValiditySeconds

    override fun getAuthorizedGrantTypes(): MutableSet<String> = authorizedGrantTypes

    override fun getAuthorities(): MutableCollection<GrantedAuthority> = authorities

    // TODO: Turn off autoapprove when it will be needed
    override fun isAutoApprove(scope: String?): Boolean = true

    // TODO: Turn on scoping when it will be needed
    override fun isScoped(): Boolean = false

    // TODO: Implement methods below proper way
    override fun getScope(): MutableSet<String> = hashSetOf()

    override fun getAdditionalInformation(): MutableMap<String, Any> = hashMapOf()

    override fun getResourceIds(): MutableSet<String> = hashSetOf()

    override fun getRegisteredRedirectUri(): MutableSet<String> = hashSetOf()
}