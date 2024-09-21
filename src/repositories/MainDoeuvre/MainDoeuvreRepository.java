package repositories.MainDoeuvre;

import Domain.Entities.MainDoeuvre;
import Domain.Entities.Materiaux;

import java.util.List;
import java.util.Optional;

public interface MainDoeuvreRepository {
    MainDoeuvre save(MainDoeuvre mainDoeuvre);
    Optional<MainDoeuvre> findById(Integer id);
    List<MainDoeuvre> findAll();
    void deleteById(Integer id);
    List<MainDoeuvre> findByProjectId(Integer projectId);
}
