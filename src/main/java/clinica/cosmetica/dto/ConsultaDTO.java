package clinica.cosmetica.dto;

import java.time.LocalDateTime;
import clinica.cosmetica.entities.Consulta;

public class ConsultaDTO {

    private Long id;
    private LocalDateTime dataHora;

    private Long pacienteId;
    private String nomePaciente;

    private Long profissionalId;
    private String nomeProfissional;

    private String especialidade;
    private String observacoes;
    private String telefonePaciente;
    private String procedimento;

    public ConsultaDTO() {}

    public ConsultaDTO(Long id, LocalDateTime dataHora, Long pacienteId, String nomePaciente,
                       Long profissionalId, String nomeProfissional, String especialidade,
                       String observacoes, String telefonePaciente, String procedimento) {
        this.id = id;
        this.dataHora = dataHora;
        this.pacienteId = pacienteId;
        this.nomePaciente = nomePaciente;
        this.profissionalId = profissionalId;
        this.nomeProfissional = nomeProfissional;
        this.especialidade = especialidade;
        this.observacoes = observacoes;
        this.telefonePaciente = telefonePaciente;
        this.procedimento = procedimento;
    }

    public ConsultaDTO(Consulta consulta) {
        this.id = consulta.getId();
        this.dataHora = consulta.getDataHora();
        this.pacienteId = consulta.getPaciente().getId();
        this.nomePaciente = consulta.getPaciente().getNome();
        this.profissionalId = consulta.getProfissional().getId();
        this.nomeProfissional = consulta.getProfissional().getNome();
        this.especialidade = consulta.getProfissional().getEspecialidade();
        this.observacoes = consulta.getObservacoes();
        this.telefonePaciente = consulta.getTelefonePaciente();
        this.procedimento = consulta.getProcedimento();
    }

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public LocalDateTime getDataHora() { return dataHora; }
    public void setDataHora(LocalDateTime dataHora) { this.dataHora = dataHora; }

    public Long getPacienteId() { return pacienteId; }
    public void setPacienteId(Long pacienteId) { this.pacienteId = pacienteId; }

    public String getNomePaciente() { return nomePaciente; }
    public void setNomePaciente(String nomePaciente) { this.nomePaciente = nomePaciente; }

    public Long getProfissionalId() { return profissionalId; }
    public void setProfissionalId(Long profissionalId) { this.profissionalId = profissionalId; }

    public String getNomeProfissional() { return nomeProfissional; }
    public void setNomeProfissional(String nomeProfissional) { this.nomeProfissional = nomeProfissional; }

    public String getEspecialidade() { return especialidade; }
    public void setEspecialidade(String especialidade) { this.especialidade = especialidade; }

    public String getObservacoes() { return observacoes; }
    public void setObservacoes(String observacoes) { this.observacoes = observacoes; }

    public String getTelefonePaciente() { return telefonePaciente; }
    public void setTelefonePaciente(String telefonePaciente) { this.telefonePaciente = telefonePaciente; }

    public String getProcedimento() { return procedimento; }
    public void setProcedimento(String procedimento) { this.procedimento = procedimento; }
}
