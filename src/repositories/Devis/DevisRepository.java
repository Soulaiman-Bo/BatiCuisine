package repositories.Devis;

import Entities.Devis;

import java.util.List;
import java.util.Optional;

public interface DevisRepository {
    Devis save(Devis devis);
    Optional<Devis> findById(Integer id);
    List<Devis> findAll();
    void deleteById(Integer id);
    List<Devis> findByProjectId(Integer projectId);
}
