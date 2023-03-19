package pialeda.app.Invoice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import pialeda.app.Invoice.dto.ClientInfo;
import pialeda.app.Invoice.model.Client;
import pialeda.app.Invoice.repository.ClientRepository;

import java.util.List;

@Service
public class ClientService {
    @Autowired
    private ClientRepository clientRepository;

    public void createClient(ClientInfo clientInfo){
        Client client = new Client();

        client.setName(clientInfo.getName());
        client.setAddress(clientInfo.getAddress());
        client.setCityAddress(clientInfo.getCityAddress());
        client.setTin(clientInfo.getTin());
        client.setAgent(clientInfo.getAgent());
        clientRepository.save(client);
    }

    public long getClientCount(){
        return clientRepository.count();
    }
    public List<Client> getAllClient(){
        return clientRepository.findAll(Sort.by(Sort.Direction.ASC, "name"));
    }

    public Client findByName(String name){
        return clientRepository.findByName(name);
    }
}
