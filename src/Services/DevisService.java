package Services;

import Domain.Entities.Client;
import Domain.Entities.Devis;
import repositories.Devis.DevisRepository;

import java.time.LocalDate;
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

    public boolean deleteDevis(Integer id) {
        return devisRepository.deleteById(id);
    }

    public List<Devis> getDevisWithProject(Client client) {
        return devisRepository.findDevisJoinProjectsById(client);
    }

    public Devis acceptDevis(Devis devis) throws Exception {
        Devis newdevis  = new Devis();
        boolean isPast = devis.getValidityDate().isAfter(LocalDate.now());

         if(isPast) {
             newdevis.setAccepted(true);
             newdevis.setId(devis.getId());
             return devisRepository.update(newdevis);
         } else {
             throw new Exception("Date of Validity is Expired");
         }
    }
}
