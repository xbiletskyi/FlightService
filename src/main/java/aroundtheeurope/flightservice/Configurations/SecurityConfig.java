package aroundtheeurope.flightservice.Configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    /**
     * Configures the security filter chain for the application.
     *
     * @param http an instance of HttpSecurity used to configure web-based security.
     * @return a SecurityFilterChain that defines the security settings for the application.
     * @throws Exception if there is a problem configuring the security.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // Disable Cross-Site Request Forgery (CSRF) protection since this configuration
                // is intended for local access only. CSRF protection is not necessary for non-public APIs.
                .csrf(AbstractHttpConfigurer::disable)

                .authorizeHttpRequests(authorizeRequests ->
                        authorizeRequests
                                // Allow all requests from localhost (both IPv4 and IPv6 loopback addresses).
                                .requestMatchers(request -> {
                                    String remoteAddr = request.getRemoteAddr();
                                    return "127.0.0.1".equals(remoteAddr) || "::1".equals(remoteAddr);
                                }).permitAll()

                                // Deny all other requests that do not originate from localhost.
                                .anyRequest().denyAll()
                );
        return http.build();
    }
}
