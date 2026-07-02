package com.api.maquininha_projeto.controller;

import com.api.maquininha_projeto.dto.AuthResponse;
import com.api.maquininha_projeto.dto.LoginRequest;
import com.api.maquininha_projeto.dto.RegisterRequest;
import com.api.maquininha_projeto.entity.Usuario;
import com.api.maquininha_projeto.security.JwtUtil;
import com.api.maquininha_projeto.service.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UsuarioService usuarioService;
    private final JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getSenha())
        );

        Usuario usuario = (Usuario) usuarioService.loadUserByUsername(request.getEmail());
        String token = jwtUtil.gerarToken(usuario);

        return ResponseEntity.ok(new AuthResponse(token, "Bearer", usuario.getNome(), usuario.getEmail()));
    }

    @PostMapping("/registrar")
    public ResponseEntity<AuthResponse> registrar(@Valid @RequestBody RegisterRequest request) {
        Usuario usuario = usuarioService.registrar(request);
        String token = jwtUtil.gerarToken(usuario);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new AuthResponse(token, "Bearer", usuario.getNome(), usuario.getEmail()));
    }
}
