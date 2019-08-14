package pl.ninthfolder.todo.infrastructure.security

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer
import org.springframework.security.oauth2.provider.ClientDetailsService
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore
import pl.ninthfolder.todo.domain.client.ClientRepository

@Configuration
@EnableAuthorizationServer
class OAuthConfiguration(
        @Value("\${security.publicKey}") val publicKey: String,
        @Value("\${security.privateKey}") val privateKey: String,
        val clientRepository: ClientRepository
) : AuthorizationServerConfigurerAdapter() {

    override fun configure(auth: AuthorizationServerSecurityConfigurer) {
        auth
                .tokenKeyAccess("permitAll()")
                .checkTokenAccess("isAuthenticated()")
    }

    override fun configure(endpoints: AuthorizationServerEndpointsConfigurer) {
        endpoints
                .tokenStore(tokenStore())
                .accessTokenConverter(tokenEnhancer())
    }

    override fun configure(clients: ClientDetailsServiceConfigurer) {
        clients
                .withClientDetails(customClientDetailsService())
    }

    fun customClientDetailsService(): ClientDetailsService {
        return ClientDetailsService { clientId ->
            clientRepository.findByClientId(clientId)
        }
    }

    @Bean
    fun tokenEnhancer(): JwtAccessTokenConverter {
        val converter = JwtAccessTokenConverter()
        converter.setSigningKey(privateKey)
        converter.setVerifierKey(publicKey)
        return converter
    }

    @Bean
    fun tokenStore(): JwtTokenStore = JwtTokenStore(tokenEnhancer())
}