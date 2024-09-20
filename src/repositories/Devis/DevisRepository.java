package repositories.Devis;

import Entities.Client;
import Entities.Devis;

import java.util.List;
import java.util.Optional;

public interface DevisRepository {
    Devis save(Devis devis);
    Optional<Devis> findById(Integer id);
    List<Devis> findAll();
    void deleteById(Integer id);
    List<Devis> findDevisJoinProjectsById(Client client);
    Devis update(Devis devis);
}
