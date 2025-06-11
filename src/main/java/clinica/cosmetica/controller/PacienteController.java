package clinica.cosmetica.controller;

import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import clinica.cosmetica.dto.LoginDTO;
import clinica.cosmetica.entities.Paciente;
import clinica.cosmetica.repository.PacienteRepository;
import clinica.cosmetica.service.AuthService;

@RestController
@RequestMapping("/pacientes")
public class PacienteController {

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private AuthService authService;

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @PostMapping("/cadastrar")
    public ResponseEntity<?> cadastrar(@RequestBody Paciente paciente) {
        if (pacienteRepository.findByEmail(paciente.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body("E-mail já cadastrado");
        }

        paciente.setSenha(encoder.encode(paciente.getSenha()));

        Paciente novoPaciente = pacienteRepository.save(paciente);
        novoPaciente.setSenha(null);

        return ResponseEntity.ok(novoPaciente);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Paciente loginRequest) {
        Optional<Paciente> pacienteOpt = pacienteRepository.findByEmail(loginRequest.getEmail());

        if (pacienteOpt.isPresent()) {
            Paciente paciente = pacienteOpt.get();

            if (encoder.matches(loginRequest.getSenha(), paciente.getSenha())) {
                String token = authService.autenticarEGerarToken(
                    new LoginDTO(paciente.getEmail(), loginRequest.getSenha())
                );

                paciente.setSenha(null);

                return ResponseEntity.ok(
                    Map.of(
                        "paciente", paciente,
                        "token", token
                    )
                );
            }
        }

        return ResponseEntity.status(401).body("Email ou senha inválidos");
    }
}
