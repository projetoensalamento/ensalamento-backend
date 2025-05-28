package br.univille.projetohotelpracachorro.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration // Indica que esta classe é uma fonte de definições de beans
@EnableWebSecurity
public class SecurityConfigurer {

    // Não precisamos mais injetar UserDetailsService diretamente aqui para o AuthenticationManagerBuilder
    // Ele será automaticamente configurado se um UserDetailsService estiver disponível no contexto Spring.

    @Bean
    public PasswordEncoder passwordEncoder() {
        // Usa o PasswordEncoderFactories para criar um delegating password encoder.
        // Isso é o padrão recomendado e mais seguro, pois permite diferentes tipos de codificação
        // e é compatível com senhas codificadas no passado.
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    // NOVO: Bean para usuários em memória para testes
    @Bean
    public UserDetailsService users(PasswordEncoder passwordEncoder) { // Injeta o PasswordEncoder
        UserDetails user = User.builder()
                .username("usuario") // Seu nome de usuário de teste
                .password(passwordEncoder.encode("senha")) // Sua senha de teste, codificada
                .roles("USUARIO") // Papel(is) do usuário
                .build();

        UserDetails admin = User.builder()
                .username("admin") // Seu nome de usuário de teste para admin
                .password(passwordEncoder.encode("admin")) // Sua senha de teste para admin, codificada
                .roles("ADMIN", "USUARIO") // Papel(is) do admin
                .build();

        return new InMemoryUserDetailsManager(user, admin);
    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorize -> authorize
                        .anyRequest().permitAll()
                        /*requestMatchers("/", "/resources/**", "/css/**", "/img/**", "/js/**", "/webjars/**", "/h2-console/**",
                                "/login").permitAll()
                        .anyRequest().authenticated()*/
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/professores", true)
                        .permitAll()
                )
                .csrf(csrf -> csrf.disable()) // Desabilita CSRF (cuidado: pode ser um risco de segurança se não for justificado)
                .headers(headers -> headers.frameOptions(frameOptions -> frameOptions.disable())); // Desabilita X-Frame-Options (para H2-console)

        return http.build();
    }
}