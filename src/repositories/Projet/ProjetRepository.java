package repositories.Projet;

import Entities.Projet;

import java.util.List;
import java.util.Optional;

public interface ProjetRepository {
    Projet save(Projet projet);
    Optional<Projet> findById(Integer id);
    List<Projet> findAll();
    void deleteById(Integer id);
}
