package clinica.cosmetica.dto;

import java.time.LocalDate;

import clinica.cosmetica.entities.Paciente;

public class PacienteDTO {

    private Long id;
    private String nome;
    private String email;
    private String telefone;  // Corrigido de "fone" para "telefone"
    private LocalDate dataNasc;  // Alterado para LocalDate
    private String senha;

    // Construtor com argumentos
    public PacienteDTO(Long id, String nome, String email, String telefone, LocalDate dataNasc, String senha) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.telefone = telefone;
        this.dataNasc = dataNasc;
        this.senha = senha;
    }

    // Construtor a partir da entidade
    public PacienteDTO(Paciente entity) {
        this.id = entity.getId();
        this.nome = entity.getNome();
        this.email = entity.getEmail();
        this.telefone = entity.getTelefone();
        this.dataNasc = entity.getDataNasc();
        this.senha = entity.getSenha();
    }

    // Construtor padrão
    public PacienteDTO() {
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }
    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public LocalDate getDataNasc() {
        return dataNasc;
    }
    public void setDataNasc(LocalDate dataNasc) {
        this.dataNasc = dataNasc;
    }

    public String getSenha() {
        return senha;
    }
    public void setSenha(String senha) {
        this.senha = senha;
    }

	public Object getRole() {
		// TODO Auto-generated method stub
		return null;
	}
    
    
}
