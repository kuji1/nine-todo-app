package pl.ninthfolder.todo.infrastructure.security

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer
import org.springframework.security.oauth2.provider.ClientDetailsService
import pl.ninthfolder.todo.domain.client.ClientRepository


@Configuration
@EnableAuthorizationServer
class OAuthConfiguration(val clientRepository: ClientRepository) : AuthorizationServerConfigurerAdapter() {

    override fun configure(auth: AuthorizationServerSecurityConfigurer) {
        auth
                .tokenKeyAccess("permitAll()")
                .checkTokenAccess("isAuthenticated()")
    }

    override fun configure(clients: ClientDetailsServiceConfigurer) {
        clients
                .withClientDetails(customClientDetailsService())
    }

    @Bean
    fun customClientDetailsService(): ClientDetailsService {
        return ClientDetailsService { clientId ->
            clientRepository.findByClientId(clientId)
        }
    }
}