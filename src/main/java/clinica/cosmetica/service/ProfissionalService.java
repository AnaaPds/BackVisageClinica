package clinica.cosmetica.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import clinica.cosmetica.entities.Profissional;
import clinica.cosmetica.repository.ProfissionalRepository;

@Service
public class ProfissionalService {

    @Autowired
    private ProfissionalRepository profissionalRepository;

    public List<Profissional> listarTodos() {
        return profissionalRepository.findAll();
    }

    public Optional<Profissional> buscarPorId(Long id) {
        return profissionalRepository.findById(id);
    }

    public Profissional salvar(Profissional profissional) {
        return profissionalRepository.save(profissional);
    }

    public Optional<Profissional> atualizar(Long id, Profissional profissionalAtualizado) {
        return profissionalRepository.findById(id).map(profissional -> {
            profissional.setNome(profissionalAtualizado.getNome());
            profissional.setEspecialidade(profissionalAtualizado.getEspecialidade());
            return profissionalRepository.save(profissional);
        });
    }
    
    

    public boolean deletar(Long id) {
        if (profissionalRepository.existsById(id)) {
            profissionalRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
