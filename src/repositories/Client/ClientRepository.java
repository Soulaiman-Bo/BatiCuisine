package repositories.Client;

import Domain.Entities.Client;
import Domain.Entities.Projet;

import java.util.List;
import java.util.Optional;

public interface ClientRepository {
    Client save(Client client);
    Optional<Client> findById(Integer id);
    List<Client> findAll();
    boolean deleteById(Integer id);
    List<Client> findByProfessional(Boolean isProfessional);
    Client update(Client client);
}


