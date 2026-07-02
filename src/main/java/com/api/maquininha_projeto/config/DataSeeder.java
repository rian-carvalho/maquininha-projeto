package com.api.maquininha_projeto.config;

import com.api.maquininha_projeto.entity.Usuario;
import com.api.maquininha_projeto.enuns.Role;
import com.api.maquininha_projeto.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.NonNull;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataSeeder implements ApplicationRunner {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(@NonNull ApplicationArguments args) {
        if (usuarioRepository.existsByEmail("admin@maquininha.com")) {
            return;
        }

        Usuario admin = Usuario.builder()
                .nome("Administrador")
                .email("admin@maquininha.com")
                .senha(passwordEncoder.encode("admin123"))
                .role(Role.ADMIN)
                .build();

        usuarioRepository.save(admin);

        log.info("=================================================");
        log.info("Admin criado automaticamente:");
        log.info("  Email: admin@maquininha.com");
        log.info("  Senha: admin123");
        log.info("=================================================");
    }
}
