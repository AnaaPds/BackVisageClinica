package clinica.cosmetica.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import clinica.cosmetica.dto.LoginDTO;
import clinica.cosmetica.entities.Profissional;
import clinica.cosmetica.repository.ProfissionalRepository;
import clinica.cosmetica.service.AuthService;

@RestController
@RequestMapping("/profissionais")
public class ProfissionalController {

    @Autowired
    private ProfissionalRepository profissionalRepository;

    @Autowired
    private AuthService authService;

    @Autowired
    private PasswordEncoder encoder;

    @PostMapping("/cadastrar")
    public ResponseEntity<?> cadastrar(@RequestBody Profissional profissional) {
        if (profissionalRepository.findByEmail(profissional.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body("E-mail já cadastrado");
        }

        profissional.setSenha(encoder.encode(profissional.getSenha()));
        Profissional novoProfissional = profissionalRepository.save(profissional);
        novoProfissional.setSenha(null);
        return ResponseEntity.ok(novoProfissional);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Profissional loginRequest) {
        Optional<Profissional> profissionalOpt = profissionalRepository.findByEmail(loginRequest.getEmail());

        if (profissionalOpt.isPresent()) {
            Profissional profissional = profissionalOpt.get();

            if (encoder.matches(loginRequest.getSenha(), profissional.getSenha())) {
                String token = authService.autenticarProfissionalEGerarToken(
                    new LoginDTO(profissional.getEmail(), loginRequest.getSenha())
                );

                profissional.setSenha(null);

                return ResponseEntity.ok(
                    Map.of(
                        "profissional", profissional,
                        "token", token
                    )
                );
            }
        }

        return ResponseEntity.status(401).body("Email ou senha inválidos");
    }

    @SuppressWarnings("unchecked")
	@GetMapping("/meu-perfil")
    public ResponseEntity<?> getMeuPerfil() {
        try {
            Long profissionalId = getProfissionalIdLogado();
            return profissionalRepository.findById(profissionalId)
                .map(profissional -> {
                    profissional.setSenha(null);
                    return ResponseEntity.ok(profissional);
                })
                .orElseGet((Supplier<? extends ResponseEntity<Profissional>>) ResponseEntity.status(404).body("Profissional não encontrado"));
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Erro ao buscar perfil: " + e.getMessage());
        }
    }

    @GetMapping("/todos")
    public ResponseEntity<List<Profissional>> listarTodos() {
        List<Profissional> lista = profissionalRepository.findAll();
        lista.forEach(p -> p.setSenha(null));
        return ResponseEntity.ok(lista);
    }

    private Long getProfissionalIdLogado() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return profissionalRepository.findByEmail(email)
                .map(Profissional::getId)
                .orElseThrow(() -> new IllegalStateException("Profissional logado não encontrado"));
    }
}
