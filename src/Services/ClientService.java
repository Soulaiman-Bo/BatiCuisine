package Services;

import Domain.Entities.Client;
import repositories.Client.ClientRepository;

import java.util.List;
import java.util.Optional;

public class ClientService {
    private final ClientRepository clientRepository;

    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public Client createClient(Client client) {
        return clientRepository.save(client);
    }

    public Optional<Client> getClientById(Integer id) {
        return clientRepository.findById(id);
    }

    public List<Client> getAllClients() {
        return clientRepository.findAll();
    }

    public Client updateClient(Client client) {
        return clientRepository.save(client);
    }

    public boolean deleteClient(Integer id) {
        return clientRepository.deleteById(id);
    }
    public Client upddateClient(Client client) {
        return clientRepository.update(client);
    }

    public List<Client> getProfessionalClients() {
        return clientRepository.findByProfessional(true);
    }

    public List<Client> getNonProfessionalClients() {
        return clientRepository.findByProfessional(false);
    }
}
