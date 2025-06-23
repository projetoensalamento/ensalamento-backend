package br.univille.projetohotelpracachorro.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
        return new BCryptPasswordEncoder();
    }

    // NOVO: Bean para usuários em memória para testes
    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {
        // Usuário Professor
        UserDetails professor = User.withUsername("professor")
                .password(passwordEncoder.encode("123"))
                .roles("PROFESSOR") // Role PROFESSOR
                .build();

        // Usuário Aluno
        UserDetails aluno = User.withUsername("aluno")
                .password(passwordEncoder.encode("123"))
                .roles("ALUNO") // Role ALUNO
                .build();

        return new InMemoryUserDetailsManager(professor, aluno);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/css/**", "/js/**", "/images/**", "/my-login").permitAll() // Permite acesso público a estes recursos e à página de login
                        .requestMatchers("/ensalamento/**").authenticated() // Ensalamento acessível para todos autenticados
                        .requestMatchers("/ensalamento/editarAula/**", "/ensalamento/criarAula/**",
                                "/ensalamento/salvarEdicaoAula/**", "/ensalamento/excluirAula/**",
                                "/turmas").hasAnyRole("PROFESSOR", "ALUNO") // Apenas PROFESSOR pode editar/criar/excluir e ver a lista de turmas
                        .anyRequest().authenticated() // Todas as outras requisições exigem autenticação
                )
                .formLogin(form -> form
                        .loginPage("/login") // Define a página de login customizada
                        .defaultSuccessUrl("/turmas", true) // Redireciona após login bem-sucedido (ex: para a primeira turma)
                        .permitAll() // Permite acesso ao formulário de login para todos
                )
                .logout(logout -> logout
                        .logoutUrl("/logout") // Define a URL de logout
                        .logoutSuccessUrl("/login?logout") // Redireciona após logout bem-sucedido
                        .permitAll() // Permite acesso à URL de logout para todos
                )
                .csrf(csrf -> csrf.disable()); // Desabilita CSRF para simplificar, mas HABILITE em produção!

        return http.build();
    }
}