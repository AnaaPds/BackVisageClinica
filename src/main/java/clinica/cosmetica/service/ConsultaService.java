package clinica.cosmetica.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import clinica.cosmetica.dto.ConsultaDTO;
import clinica.cosmetica.entities.Consulta;
import clinica.cosmetica.entities.Paciente;
import clinica.cosmetica.entities.Profissional;
import clinica.cosmetica.repository.ConsultaRepository;
import clinica.cosmetica.repository.PacienteRepository;
import clinica.cosmetica.repository.ProfissionalRepository;

@Service
public class ConsultaService {

    @Autowired
    private ConsultaRepository consultaRepository;

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private ProfissionalRepository profissionalRepository;

    public List<ConsultaDTO> listarTodas() {
        return consultaRepository.findAll().stream()
                .map(ConsultaDTO::new)
                .collect(Collectors.toList());
    }

    public Optional<ConsultaDTO> buscarPorId(Long id) {
        return consultaRepository.findById(id).map(ConsultaDTO::new);
    }

    public Consulta salvar(ConsultaDTO dto) {
        Paciente paciente = pacienteRepository.findById(dto.getPacienteId())
                .orElseThrow(() -> new RuntimeException("Paciente não encontrado"));

        Profissional profissional = profissionalRepository.findById(dto.getProfissionalId())
                .orElseThrow(() -> new RuntimeException("Profissional não encontrado"));

        // Verifica conflito de horário
        boolean existeConflito = consultaRepository
                .existsByProfissionalAndDataHora(profissional, dto.getDataHora());

        if (existeConflito) {
            throw new RuntimeException("Já existe uma consulta marcada para esse profissional neste horário.");
        }

        Consulta nova = new Consulta();
        nova.setPaciente(paciente);
        nova.setProfissional(profissional);
        nova.setDataHora(dto.getDataHora());
        nova.setObservacoes(dto.getObservacoes());

        // Setando telefonePaciente e procedimento
        nova.setTelefonePaciente(dto.getTelefonePaciente());
        nova.setProcedimento(dto.getProcedimento());

        return consultaRepository.save(nova);
    }

    public Consulta atualizar(Long id, ConsultaDTO dto) {
        Consulta existente = consultaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Consulta não encontrada"));

        Paciente paciente = pacienteRepository.findById(dto.getPacienteId())
                .orElseThrow(() -> new RuntimeException("Paciente não encontrado"));

        Profissional profissional = profissionalRepository.findById(dto.getProfissionalId())
                .orElseThrow(() -> new RuntimeException("Profissional não encontrado"));

        // Verifica conflito de horário, ignorando a própria consulta
        boolean conflito = consultaRepository
                .existsByProfissionalAndDataHoraAndIdNot(profissional, dto.getDataHora(), id);

        if (conflito) {
            throw new RuntimeException("Já existe uma consulta marcada para esse profissional neste horário.");
        }

        existente.setPaciente(paciente);
        existente.setProfissional(profissional);
        existente.setDataHora(dto.getDataHora());
        existente.setObservacoes(dto.getObservacoes());

        // Setando telefonePaciente e procedimento no update também
        existente.setTelefonePaciente(dto.getTelefonePaciente());
        existente.setProcedimento(dto.getProcedimento());

        return consultaRepository.save(existente);
    }

    public boolean deletar(Long id) {
        if (!consultaRepository.existsById(id)) {
            return false;
        }
        consultaRepository.deleteById(id);
        return true;
    }

    public List<ConsultaDTO> buscarPorProfissional(String nome) {
        return consultaRepository.findByProfissional_NomeContainingIgnoreCase(nome).stream()
                .map(ConsultaDTO::new)
                .collect(Collectors.toList());
    }

    public List<ConsultaDTO> buscarPorEspecialidade(String especialidade) {
        return consultaRepository.findByProfissional_EspecialidadeContainingIgnoreCase(especialidade).stream()
                .map(ConsultaDTO::new)
                .collect(Collectors.toList());
    }

    public List<ConsultaDTO> buscarPorDataHora(LocalDateTime dataHora) {
        return consultaRepository.findByDataHora(dataHora).stream()
                .map(ConsultaDTO::new)
                .collect(Collectors.toList());
    }

    public List<ConsultaDTO> buscarPorProfissionalEData(String nome, LocalDateTime dataHora) {
        return consultaRepository.findByProfissional_NomeContainingIgnoreCaseAndDataHora(nome, dataHora).stream()
                .map(ConsultaDTO::new)
                .collect(Collectors.toList());
    }

    public List<ConsultaDTO> buscarPorEspecialidadeEData(String especialidade, LocalDateTime dataHora) {
        return consultaRepository.findByProfissional_EspecialidadeContainingIgnoreCaseAndDataHora(especialidade, dataHora).stream()
                .map(ConsultaDTO::new)
                .collect(Collectors.toList());
    }

    public List<ConsultaDTO> listarPorPaciente(Long pacienteId) {
        return consultaRepository.findByPaciente_Id(pacienteId).stream()
                .map(ConsultaDTO::new)
                .collect(Collectors.toList());
    }
    
    public List<Consulta> listarPorProfissional(Long profissionalId) {
        return consultaRepository.findByProfissional_Id(profissionalId);
    }

    
}
