package pl.ninthfolder.todo.infrastructure.security

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
class OAuthConfiguration(val clientRepository: ClientRepository) : AuthorizationServerConfigurerAdapter() {

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
        converter.setSigningKey(
                "-----BEGIN RSA PRIVATE KEY-----\n" +
                "MIIJKQIBAAKCAgEAvFd0Hwy7w5Kq57uK/fJzD/sBbiiWjJDkzhNiYSxBZDqcQhBs\n" +
                "3Nw6QDZQ3gnZ4SP4qm8eEM7NY+wmHKphJejFUrwxYDUqxGhxhAPDsrYL0BQOa2nb\n" +
                "oMH/CmFRjtV3CvZbXjBRa4+OGD1VkiAGKVoM6UrVOLlzWWpfh5rt5COCpjW+osOv\n" +
                "hu9FUWSP3YOxqkxQE65jLvOnILT2wlofl5cdVtCmUT7lJjshkE44Wr9CnbYnKrK3\n" +
                "OCWcrFtqQp852COPMeDebI5J1BiSfPhNheCmgUcQpd06c1zVE9u1NIOJ2dJgty86\n" +
                "+4EkEjsdbwTxJqR2+aVZ6OHgif0lRd2j4XdY/ekr2I0J7LWRA2EwTKz6uyMO+5Ey\n" +
                "6nFgYLpVZOA7UGX0OsBl1M/mMBWJCyD3cyPRd5Z2AVfYKN27lfl62KXEl3aixDOz\n" +
                "mhnYK50NDqize4U5kUJEVuOjJxanHaYsrCUBjbnb+JiiST+jUAi44ToqxMOREzif\n" +
                "4PmVog52XZPKKfW80/Dp1il1jE2ndWg+2Qk8jrhU3cqNGtK+EOd+9MCgFGepkFp4\n" +
                "TH97PIF29VFDozp02qwqr2XWDPXSDYxjXKvK4FIWgiGATwG8pzcVvR8C7b0/gaf9\n" +
                "08rul/NaR96/uzrY1jIc/2UUK9biJ0bI+Y5dH24lK5d7XG+xGSa39CL+JwUCAwEA\n" +
                "AQKCAgBhEa2uhxCKxp/A2V7UbU2yMcrV7KNTBSZ3s3Pj4zw8nRyFzBNfbT20dIoP\n" +
                "NgKOT8zMgoJ7ZSpkUoR+B64ULLjuYEIOdLh7C91djdch10OVVZDs+V0RqIp63hd7\n" +
                "xzcYdko27C46gn1YrtvYeRrT2y6GWmQ0XRg/QFzF9iqfh1HBujdrifGAWPdf8LVZ\n" +
                "FJPP0o7+5cUGoNtFNx9qoapgpaigYt8lRoYxww4UYswWsTZzNDtRfVSC4peyGVdV\n" +
                "yNy2MfWOXngkeN1CK/vr3smNs2/9dxP0eGW0qWbEWls1GopEsnHYV8ZNc4yUvNT5\n" +
                "sBFduiv7lKYXKbthQxAfxxC8lpdNOc3ibBKAp9O/iohDp8dNgGW9aA2gm5Zd6zh2\n" +
                "7Rym9D53aLv1WmCmZdUMwU89dmF9iwOJLnxeWDggLS6mtt5cFpLBTdStvQLm/FWN\n" +
                "RP5cpwIuib2llnIG37ljTN0l7jKOqDmQBF8v4IyNE7rfE3BFGfvmwTzrjz1OZN5b\n" +
                "8tU6kNZBesOsK7Br8G8rIRZTpwZfEe/uh8o9X2mTwmEdpGgwwf//gB2QxCdO8+nl\n" +
                "eO1CYb8eHegFJRgOM140E5ZK3zr6ARWCbRupqkXGd2kUl0osK6L8g3iyiFDViJO4\n" +
                "PYFhU9lfcqHuH8DAkuent3TFsMT8MbIAHnz6WM1+BUASlTVPIQKCAQEA+fe3yrJh\n" +
                "xaVz2z3xiwdrFDrivVn5iB8QghVs+vW/o4tfqQENeXCHRf6U4AB6AMsWo9OadXnn\n" +
                "irRBg+tf53xvrOhuVD39+52xhqIee4jaFlu7uNW6GRgoa6RUkwk0jlSUw2/dkwYd\n" +
                "Csxipdiu//cSRucrgxMUQiVfSgyDFadrfkBfJShSNL9AxVPtfNwXzotlvTf+Xs9v\n" +
                "V1rflmtFO3PS4Z5Ly0H8QEmCEhnljjiNDAF/Jmx7PttlN8merpBxvRYfYXO+zhLe\n" +
                "KvQET6rHIl0Ph6RsjsHCU8jxIpvSR5PmtPXbk3ToJnmnQzfERNacoUriZSDKiJm/\n" +
                "oN+yz012Pt86TQKCAQEAwOMDtW1+cb1omEBzKHn0Je93eDcwpHHJHB7v+obb7g/o\n" +
                "7ekMgJOm1iOORO+tHQ99SEUxPyPaC/iGX3vXrtmm83WvR4NoN/ZXMvbWLQhrzteb\n" +
                "ht5yj0MRBsrEmeZGXfzbiNWHgJwtqeF1IE0YMNLuM3EYEBuZdS7PbzX7vameeYqc\n" +
                "lB3OkdKi3iqnB6MkM81sAiGLpqPWxszUQrU0QcnFhxlv3ZwvclCa1BGGbfZNZyN/\n" +
                "UYWUZ/+3QtqfGgO1Z5v/nH0NkPgj8wUsOoqebhoByYit3hIG3LRcLFAhpmh6xPCL\n" +
                "GbspZiEwVmTrXNy3Rc1veBhhwG/Ib1pB32hPa98LmQKCAQAshMne99Yh9U2lBms2\n" +
                "b6PKfpJTVuryK1YtVNOL/qia3/+xgdZ8gmYVOToaivsvmPv3QYCI26U5NtFURV9X\n" +
                "QvpAuUStDD1nyrYLXGRBbCYrRTsxuB7kyUCpqyLE7SbHEplMFWhI/xW9DpmjH+xK\n" +
                "+h3vMsy1E3jQLwfBkGjoOmESozRO0deSPV2zxzbMvgYfawQ7pvVOvXL/A9g+WeyC\n" +
                "oiY0uJSXJrFeYbkgsCwKIFg+gyW6Gif5Een8/ZOdU2xuWLpOCOc6nHGlBMCfz7KX\n" +
                "lCtn5QjQhAGwGmGX4FibFuRH5mGs6W1PLOt1NUFnYTGUKFoZ2LU5juhqJm0aLpmY\n" +
                "qPkhAoIBAQC0/xEG2QUSw9J3M9sbuzxPsKKhquA5HxDBE4LKymtru4AuZ0Y1DNg8\n" +
                "Dsc8hs8h+tvrygnJHuJH42BsDvC4AUL5J5Bfgb507PKsPxWA0msfgBBQAULsjdlX\n" +
                "T0Qmyf9zyIdUolHxbBwpUgevVMiDYBrnKJOU4eEhJdi+maBtO9IaaOg+0K17BnWQ\n" +
                "FeQAdaTN+1ADu+ioaQtsJt5y7khoB2cUO9lN0XZwRZTTtNI5Mys0wgmBvOAiqsET\n" +
                "ucQcDCjYXXkBJhNp2n8ZVtxG3raFyF+u9lN4k9/ekFwBtPuuhFHXpYRRk9/oFvKd\n" +
                "zztQO0i69mhIAFER25LwcnhnpCEKrxTZAoIBAQDGE6YkTBcOd0c/sKyHwXyW3O6r\n" +
                "d3LYWcpiUm0jj1em4BehGhj1WamDuMktcIjuOZDs1c8UHIFBkJ9TrxV+6g5tHtWs\n" +
                "6UuI7T5T4Vujxm2NyAw84HpcaIY25xrJUbH7eqlIsyO+gEGlkCeecJn+2HGVtA6z\n" +
                "bUcMvs6JrNvmLL5TJheIo/7xdnqu4twrWLPFesX0wBoS0FknUwFfnDtiCF595/97\n" +
                "78D7oHRPHvV7YdM4bJY2bt0dsFdHoV7QqjQxoq6ga45dTAzQW3JQ5R4tHRHECFMy\n" +
                "mmas7da5vGy8o1iobCxwsvE8RIGU25sS8t+oAK1LvQoXvPzy6ikGWk0H2N06\n" +
                "-----END RSA PRIVATE KEY-----"
        )
        converter.setVerifierKey(
                "-----BEGIN PUBLIC KEY-----\n" +
                "MIICIjANBgkqhkiG9w0BAQEFAAOCAg8AMIICCgKCAgEAvFd0Hwy7w5Kq57uK/fJz\n" +
                "D/sBbiiWjJDkzhNiYSxBZDqcQhBs3Nw6QDZQ3gnZ4SP4qm8eEM7NY+wmHKphJejF\n" +
                "UrwxYDUqxGhxhAPDsrYL0BQOa2nboMH/CmFRjtV3CvZbXjBRa4+OGD1VkiAGKVoM\n" +
                "6UrVOLlzWWpfh5rt5COCpjW+osOvhu9FUWSP3YOxqkxQE65jLvOnILT2wlofl5cd\n" +
                "VtCmUT7lJjshkE44Wr9CnbYnKrK3OCWcrFtqQp852COPMeDebI5J1BiSfPhNheCm\n" +
                "gUcQpd06c1zVE9u1NIOJ2dJgty86+4EkEjsdbwTxJqR2+aVZ6OHgif0lRd2j4XdY\n" +
                "/ekr2I0J7LWRA2EwTKz6uyMO+5Ey6nFgYLpVZOA7UGX0OsBl1M/mMBWJCyD3cyPR\n" +
                "d5Z2AVfYKN27lfl62KXEl3aixDOzmhnYK50NDqize4U5kUJEVuOjJxanHaYsrCUB\n" +
                "jbnb+JiiST+jUAi44ToqxMOREzif4PmVog52XZPKKfW80/Dp1il1jE2ndWg+2Qk8\n" +
                "jrhU3cqNGtK+EOd+9MCgFGepkFp4TH97PIF29VFDozp02qwqr2XWDPXSDYxjXKvK\n" +
                "4FIWgiGATwG8pzcVvR8C7b0/gaf908rul/NaR96/uzrY1jIc/2UUK9biJ0bI+Y5d\n" +
                "H24lK5d7XG+xGSa39CL+JwUCAwEAAQ==\n" +
                "-----END PUBLIC KEY-----"
        )
        return converter
    }

    @Bean
    fun tokenStore(): JwtTokenStore = JwtTokenStore(tokenEnhancer())
}