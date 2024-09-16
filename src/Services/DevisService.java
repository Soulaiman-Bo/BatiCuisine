package Services;

import Entities.Devis;
import repositories.Devis.DevisRepository;

import java.util.List;
import java.util.Optional;

public class DevisService {
    private final DevisRepository devisRepository;

    public DevisService(DevisRepository devisRepository) {
        this.devisRepository = devisRepository;
    }

    public Devis createDevis(Devis devis) {
        return devisRepository.save(devis);
    }

    public Optional<Devis> getDevisById(Integer id) {
        return devisRepository.findById(id);
    }

    public List<Devis> getAllDevis() {
        return devisRepository.findAll();
    }

    public Devis updateDevis(Devis devis) {
        return devisRepository.save(devis);
    }

    public void deleteDevis(Integer id) {
        devisRepository.deleteById(id);
    }

    public List<Devis> getDevisByProjectId(Integer projectId) {
        return devisRepository.findByProjectId(projectId);
    }
}
