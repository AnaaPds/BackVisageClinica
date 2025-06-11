package clinica.cosmetica.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import clinica.cosmetica.entities.Profissional;

@Repository
public interface ProfissionalRepository extends JpaRepository<Profissional, Long> {

    Optional<Profissional> findByEmail(String email);

    List<Profissional> findByNomeContainingIgnoreCase(String nome);

	Optional<Profissional> findByEmailAndSenha(String email, String senha);

}
