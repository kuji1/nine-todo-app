package pl.ninthfolder.todo.infrastructure.security

import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter


@Configuration
@EnableResourceServer
class SecurityConfiguration: ResourceServerConfigurerAdapter() {

    override fun configure(http: HttpSecurity) {
        http
                /* Saving commented stuff for later */
//                .httpBasic().disable()
//                .csrf().disable()
                .requestMatchers()
                .antMatchers("/api/**").and().authorizeRequests()
                .anyRequest().access("#oauth2.hasScope('ultimate')")
//                .anyRequest()
//                .permitAll()
    }
}