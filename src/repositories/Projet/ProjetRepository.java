package repositories.Projet;

import Domain.Entities.Devis;
import Domain.Entities.Projet;

import java.util.List;
import java.util.Optional;

public interface ProjetRepository {
    Projet save(Projet projet);
    Optional<Projet> findById(Integer id);
    List<Projet> findAll();
    boolean deleteById(Integer id);
    Projet update(Projet projet);
}
