package clinica.cosmetica.dto;


public class RelatorioConsultaDTO {
    private String dataConsulta;
    private String especialidade;
    private String nomePaciente;

    public RelatorioConsultaDTO(String dataConsulta, String especialidade, String nomePaciente) {
        this.dataConsulta = dataConsulta;
        this.especialidade = especialidade;
        this.nomePaciente = nomePaciente;
    }

    public String getDataConsulta() {
        return dataConsulta;
    }

    public void setDataConsulta(String dataConsulta) {
        this.dataConsulta = dataConsulta;
    }

    public String getEspecialidade() {
        return especialidade;
    }

    public void setEspecialidade(String especialidade) {
        this.especialidade = especialidade;
    }

    public String getNomePaciente() {
        return nomePaciente;
    }

    public void setNomePaciente(String nomePaciente) {
        this.nomePaciente = nomePaciente;
    }
}

