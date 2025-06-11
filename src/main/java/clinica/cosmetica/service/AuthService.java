package clinica.cosmetica.service;

import java.sql.Date;
import java.util.Optional;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import clinica.cosmetica.dto.LoginDTO;
import clinica.cosmetica.entities.Paciente;
import clinica.cosmetica.entities.Profissional;
import clinica.cosmetica.repository.PacienteRepository;
import clinica.cosmetica.repository.ProfissionalRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Service
public class AuthService {

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private ProfissionalRepository profissionalRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private final SecretKey jwtSecretKey = Keys.hmacShaKeyFor("minhaChaveSecretaSuperSecreta1234567890".getBytes());

    private final long jwtExpirationMs = 3600000; // 1 hora

    public String autenticarEGerarToken(LoginDTO login) {
        Optional<Paciente> usuarioOpt = pacienteRepository.findByEmail(login.getEmail());

        if (usuarioOpt.isPresent()) {
            Paciente usuario = usuarioOpt.get();
            if (passwordEncoder.matches(login.getPassword(), usuario.getSenha())) {
                return gerarToken(usuario.getEmail());
            }
        }
        return null;
    }

    public String autenticarProfissionalEGerarToken(LoginDTO login) {
        Optional<Profissional> profissionalOpt = profissionalRepository.findByEmail(login.getEmail());

        if (profissionalOpt.isPresent()) {
            Profissional profissional = profissionalOpt.get();
            if (passwordEncoder.matches(login.getPassword(), profissional.getSenha())) {
                return gerarToken(profissional.getEmail());
            }
        }
        return null;
    }

    private String gerarToken(String email) {
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationMs))
                .signWith(jwtSecretKey, SignatureAlgorithm.HS256)
                .compact();
    }
}
