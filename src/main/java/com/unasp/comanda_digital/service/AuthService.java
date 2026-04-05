package com.unasp.comanda_digital.service;

import com.unasp.comanda_digital.dto.LoginRequest;
import com.unasp.comanda_digital.dto.LoginResponse;
import com.unasp.comanda_digital.dto.RegisterRequest;
import com.unasp.comanda_digital.model.Usuario;
import com.unasp.comanda_digital.repository.UsuarioRepository;
import com.unasp.comanda_digital.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    public LoginResponse login(LoginRequest request) {
        var usuario = usuarioRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Email ou senha inválidos!"));

        if (!passwordEncoder.matches(request.getSenha(), usuario.getSenhaHash())) {
            throw new RuntimeException("Email ou senha inválidos!");
        }

        String token = jwtService.gerarToken(usuario.getEmail(), usuario.getPerfil().name());

        return new LoginResponse(token, usuario.getNome(), usuario.getEmail(), usuario.getPerfil().name());
    }

    public LoginResponse register(RegisterRequest request) {
        if (usuarioRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email já cadastrado!");
        }

        Usuario usuario = new Usuario();
        usuario.setNome(request.getNome());
        usuario.setEmail(request.getEmail());
        usuario.setSenhaHash(passwordEncoder.encode(request.getSenha()));
        usuario.setTelefone(request.getTelefone());
        usuario.setPerfil(Usuario.Perfil.CLIENTE);
        usuario.setStatus(Usuario.Status.ATIVO);

        usuarioRepository.save(usuario);

        String token = jwtService.gerarToken(usuario.getEmail(), usuario.getPerfil().name());

        return new LoginResponse(token, usuario.getNome(), usuario.getEmail(), usuario.getPerfil().name());
    }
}