package repositories.Materiaux;

import Domain.Entities.Materiaux;

import java.util.List;
import java.util.Optional;

public interface MateriauxRepository {
    Materiaux save(Materiaux materiau);
    Optional<Materiaux> findById(Integer id);
    List<Materiaux> findAll();
    void deleteById(Integer id);
    List<Materiaux> findByProjectId(Integer projectId);
}
