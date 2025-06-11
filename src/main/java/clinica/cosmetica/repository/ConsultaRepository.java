package clinica.cosmetica.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import clinica.cosmetica.entities.Consulta;
import clinica.cosmetica.entities.Profissional;

@Repository
public interface ConsultaRepository extends JpaRepository<Consulta, Long> {

    List<Consulta> findByProfissional_NomeContainingIgnoreCase(String nome);

    List<Consulta> findByProfissional_EspecialidadeContainingIgnoreCase(String especialidade);

    List<Consulta> findByDataHora(LocalDateTime dataHora);

    List<Consulta> findByProfissional_NomeContainingIgnoreCaseAndDataHora(String nome, LocalDateTime dataHora);

    List<Consulta> findByProfissional_EspecialidadeContainingIgnoreCaseAndDataHora(String especialidade, LocalDateTime dataHora);

    List<Consulta> findByPaciente_Id(Long pacienteId);

    List<Consulta> findByProfissional_Id(Long profissionalId);


    // Verifica se já existe consulta para o profissional em um determinado horário
    boolean existsByProfissionalAndDataHora(Profissional profissional, LocalDateTime dataHora);

    // Verifica se já existe outra consulta no mesmo horário (exceto a de determinado id)
    boolean existsByProfissionalAndDataHoraAndIdNot(Profissional profissional, LocalDateTime dataHora, Long id);
}
