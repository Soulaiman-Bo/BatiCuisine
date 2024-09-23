package Services;

import Domain.Entities.Composants;
import Domain.Entities.MainDoeuvre;
import Domain.Entities.Materiaux;
import Domain.Entities.Projet;
import repositories.Projet.ProjetRepository;
import repositories.Projet.ProjetRepositoryImpl;

import java.util.List;
import java.util.Optional;

public class ProjetService {
    private final ProjetRepository projetRepository;
    MateriauxService materiauxService;
    MainDœuvreService mainDœuvreService;

    public ProjetService(ProjetRepository projetRepository, MateriauxService materiauxService, MainDœuvreService mainDœuvreService) {
        this.projetRepository = projetRepository;
        this.materiauxService = materiauxService;
        this.mainDœuvreService = mainDœuvreService;
    }

    public Projet createProjet(Projet projet) {
        return projetRepository.save(projet);
    }

    public Projet createProjetWithComponents(Projet projet) {
        Projet savedProjet = projetRepository.save(projet);
        List<Composants> composants =  projet.getComposants();

        composants.forEach(composant -> {
            if (composant instanceof Materiaux materiaux) {

                composant.setProjet(savedProjet);
                materiauxService.createMateriaux(materiaux);

            } else if (composant instanceof MainDoeuvre mainDoeuvre) {

                composant.setProjet(savedProjet);
                mainDœuvreService.createMainDœuvre(mainDoeuvre);

            }
        });

        return savedProjet;
    }

    public Projet getProjetWithComponents(Integer projectId) {
        Optional<Projet> project = projetRepository.findById(projectId);

        if(project.isPresent()) {
            List<Materiaux>  materiauxList = materiauxService.getMateriauxByProjectId(projectId);
            List<MainDoeuvre>  mainDoeuvreList = mainDœuvreService.getMainDœuvreByProjectId(projectId);
            project.get().getComposants().addAll(materiauxList);
            project.get().getComposants().addAll(mainDoeuvreList);
        }

        return project.orElse(null);
    }

    public Optional<Projet> getProjetById(Integer id) {
        return projetRepository.findById(id);
    }

    public List<Projet> getAllProjets() {
        return projetRepository.findAll();
    }

    public Projet updateProjet(Projet projet) {
        return projetRepository.update(projet);
    }

    public boolean deleteProjet(Integer id) {
        return projetRepository.deleteById(id);
    }


}
