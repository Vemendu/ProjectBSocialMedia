package com.example.ProjectB.services;

import com.example.ProjectB.Entities.Client;
import com.example.ProjectB.Entities.Role;
import com.example.ProjectB.helpers.ClientDetails;
import com.example.ProjectB.models.ClientRequest;
import com.example.ProjectB.repositories.ClientRepository;
import com.example.ProjectB.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.LinkedList;
import java.util.List;

@Service
public class ClientService implements UserDetailsService {
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private RoleRepository roleRepository;

    @Override
    public ClientDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Client client = clientRepository.findByUsername(username);
        if(client == null)
        {
            throw new UsernameNotFoundException("User not found");
        }
        else{
            return new ClientDetails(client);
        }
    }

    public Client findByUsername(String username) throws UsernameNotFoundException {
        Client client = clientRepository.findByUsername(username);
        if(client == null)
        {
            throw new UsernameNotFoundException("User not found");
        }
        else{
            return client;
        }
    }

    public Iterable<Client> findAll()
    {
        return clientRepository.findAll();
    }


    public boolean saveClient(ClientRequest clientRequest) {
        if(findUsername(clientRequest.getUsername()))
            return false;
        Client client = new Client(clientRequest);
        Role roleclient;
        /*boolean checker = true;

        try{roleRepository.findByName("USER");}
        catch (NullPointerException e)
        {
            checker=false;
        };*/

        roleclient = find("USER");
        if(roleclient==null)
        {
            roleclient = new Role("USER");
            roleRepository.save(roleclient);
        }

        client.addRole(roleclient);
        clientRepository.save(client);
        return true;
    }

    public boolean saveAdmin(ClientRequest clientRequest) {
        if(findUsername(clientRequest.getUsername()))
            return false;
        Client client = new Client(clientRequest);
        boolean checker = true;
        Role roleclient;
        try{roleRepository.findByName("ADMIN");}
        catch (NullPointerException e)
        {
            checker=false;
        }
        roleclient = find("ADMIN");
        if(roleclient==null)
        {
            roleclient = new Role("ADMIN");
            roleRepository.save(roleclient);
        }

        client.addRole(roleclient);
        clientRepository.save(client);
        return true;
    }

    public Role find(String name)
    {

        try{ roleRepository.findByName(name);
            return roleRepository.findByName(name);}
        catch (NullPointerException e) {
            return null;
        }

    }

    public boolean findUsername(String name)
    {
        try{clientRepository.findByUsername(name);
            if (clientRepository.findByUsername(name)==null)
                return false;
            return true;}
        catch (NullPointerException e)
        {
            return false;
        }

    }

    public Client getByUsername(String name)
    {
        try{clientRepository.findByUsername(name);
            if (clientRepository.findByUsername(name)==null)
                return null;
            return clientRepository.findByUsername(name);}
        catch (NullPointerException e)
        {
            return null;
        }

    }
    //@Transactional
    public boolean updateClient(Client client)
    {
        Client client1 = clientRepository.findByUsername(client.getUsername());
        if(client1==null || client1.getPassword().equals(client.getPassword()))
            return false;
        client1.setAge(client.getAge());
        client1.setAddress(client.getAddress());
        client1.setEmail(client.getEmail());
        client1.setPhoneNumber(client.getPhoneNumber());
        clientRepository.save(client1);
        return true;
    }

    public boolean updateClientBalance(Client client)
    {
        Client client1 = clientRepository.findByUsername(client.getUsername());
        client1.setAccountBalance(client1.getAccountBalance());
        clientRepository.save(client1);
        return true;
    }

    public boolean updateClientByAdmin(Client client)
    {
        Client client1 = clientRepository.findByUsername(client.getUsername());
        if(client1==null)
            return false;
        client1.setAge(client.getAge());
        client1.setAddress(client.getAddress());
        client1.setEmail(client.getEmail());
        client1.setPhoneNumber(client.getPhoneNumber());
        clientRepository.save(client1);
        return true;
    }

    public boolean deleteClient(String username)
    {
        Client client1 = clientRepository.findByUsername(username);
        clientRepository.deleteById(client1.getId());
        return true;
    }


}
