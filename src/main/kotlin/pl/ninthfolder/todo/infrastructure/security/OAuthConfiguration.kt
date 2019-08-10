package pl.ninthfolder.todo.infrastructure.security

import org.springframework.context.annotation.Configuration
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer

@Configuration
@EnableAuthorizationServer
class OAuthConfiguration : AuthorizationServerConfigurerAdapter() {

    override fun configure(auth: AuthorizationServerSecurityConfigurer) {
        auth
                .tokenKeyAccess("permitAll()")
                .checkTokenAccess("isAuthenticated()")
    }

    override fun configure(clients: ClientDetailsServiceConfigurer) {
        clients
                .inMemory()
                .withClient("todo")
                .secret("{noop}todosecret")
                .authorizedGrantTypes("client_credentials")
                .scopes("test")
                .autoApprove(true)

    }
}