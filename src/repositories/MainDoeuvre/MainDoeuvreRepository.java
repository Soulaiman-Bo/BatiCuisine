package repositories.MainDoeuvre;

import Entities.MainDoeuvre;

import java.util.List;
import java.util.Optional;

public interface MainDoeuvreRepository {
    MainDoeuvre save(MainDoeuvre mainDoeuvre);
    Optional<MainDoeuvre> findById(Integer id);
    List<MainDoeuvre> findAll();
    void deleteById(Integer id);
}
