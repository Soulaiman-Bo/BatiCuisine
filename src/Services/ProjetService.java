package Services;

import Entities.Projet;
import repositories.Projet.ProjetRepository;

import java.util.List;
import java.util.Optional;

public class ProjetService {
    private final ProjetRepository projetRepository;

    public ProjetService(ProjetRepository projetRepository) {
        this.projetRepository = projetRepository;
    }

    public Projet createProjet(Projet projet) {
        return projetRepository.save(projet);
    }

    public Optional<Projet> getProjetById(Integer id) {
        return projetRepository.findById(id);
    }

    public List<Projet> getAllProjets() {
        return projetRepository.findAll();
    }

    public Projet updateProjet(Projet projet) {
        return projetRepository.save(projet);
    }

    public void deleteProjet(Integer id) {
        projetRepository.deleteById(id);
    }
}
