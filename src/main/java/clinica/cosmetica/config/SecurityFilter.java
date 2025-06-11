package clinica.cosmetica.config;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import clinica.cosmetica.entities.Paciente;
import clinica.cosmetica.entities.Profissional;
import clinica.cosmetica.repository.PacienteRepository;
import clinica.cosmetica.repository.ProfissionalRepository;
import clinica.cosmetica.service.TokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
@Component
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private ProfissionalRepository profissionalRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String token = recoverToken(request);
        if (token != null) {
            String email = tokenService.getSubjectFromToken(token);  // Extrai email do token

            // Primeiro tenta encontrar o Paciente
            Optional<Paciente> optionalPaciente = pacienteRepository.findByEmail(email);

            if (optionalPaciente.isPresent()) {
                Paciente paciente = optionalPaciente.get();

                List<SimpleGrantedAuthority> authorities = List.of(
                    new SimpleGrantedAuthority("ROLE_" + paciente.getRole().name())
                );

                UsernamePasswordAuthenticationToken auth =
                        new UsernamePasswordAuthenticationToken(paciente, null, authorities);

                SecurityContextHolder.getContext().setAuthentication(auth);

            } else {
                // Se não for paciente, tenta profissional
                Optional<Profissional> optionalProfissional = profissionalRepository.findByEmail(email);

                if (optionalProfissional.isPresent()) {
                    Profissional profissional = optionalProfissional.get();

                    List<SimpleGrantedAuthority> authorities = List.of(
                        new SimpleGrantedAuthority("ROLE_" + profissional.getRole().name())
                    );

                    UsernamePasswordAuthenticationToken auth =
                            new UsernamePasswordAuthenticationToken(profissional, null, authorities);

                    SecurityContextHolder.getContext().setAuthentication(auth);
                }
            }
        }

        filterChain.doFilter(request, response);
    }

    private String recoverToken(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) return null;
        return authHeader.substring(7); // Remove "Bearer "
    }

  
}
