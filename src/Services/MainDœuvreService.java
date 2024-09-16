package Services;

import Entities.MainDoeuvre;
import repositories.MainDoeuvre.MainDoeuvreRepository;

import java.util.List;
import java.util.Optional;

public class MainDœuvreService {
    private final MainDoeuvreRepository mainDoeuvreRepository;

    public MainDœuvreService(MainDoeuvreRepository mainDoeuvreRepository) {
        this.mainDoeuvreRepository = mainDoeuvreRepository;
    }

    public MainDoeuvre createMainDœuvre(MainDoeuvre mainDoeuvre) {
        return mainDoeuvreRepository.save(mainDoeuvre);
    }

    public Optional<MainDoeuvre> getMainDœuvreById(Integer id) {
        return mainDoeuvreRepository.findById(id);
    }

    public List<MainDoeuvre> getAllMainDœuvre() {
        return mainDoeuvreRepository.findAll();
    }

    public MainDoeuvre updateMainDœuvre(MainDoeuvre mainDoeuvre) {
        return mainDoeuvreRepository.save(mainDoeuvre);
    }

    public void deleteMainDœuvre(Integer id) {
        mainDoeuvreRepository.deleteById(id);
    }
}
