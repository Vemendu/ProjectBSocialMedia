package com.example.ProjectB.controllers;

import com.example.ProjectB.Entities.Client;
import com.example.ProjectB.Entities.Role;
import com.example.ProjectB.helpers.ClientDetails;
import com.example.ProjectB.helpers.TokenHelper;
import com.example.ProjectB.models.ClientRequest;
import com.example.ProjectB.repositories.ClientRepository;
import com.example.ProjectB.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class ClientController {
    @Autowired
    private ClientService clientService;

    @Autowired
    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("client", new Client());

        return "signup_form";
    }

    @GetMapping("/register_admin")
    public String showAdminRegistrationForm(Model model) {
        model.addAttribute("client", new Client());

        return "admin_signup_form";
    }

    @PostMapping("/process_register")
    public String processRegistration(ClientRequest clientRequest)
    {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(clientRequest.getPassword());
        clientRequest.setPassword(encodedPassword);

        boolean result = clientService.saveClient(clientRequest);
        if (result)
            return "successRegistration";
        return "failedRegistration";
    }

    @PostMapping("/process_register_admin")
    public String processRegistrationAdmin(ClientRequest clientRequest)
    {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(clientRequest.getPassword());
        clientRequest.setPassword(encodedPassword);

        boolean result = clientService.saveAdmin(clientRequest);
        if (result)
            return "successRegistration";
        return "failedRegistration";
    }

    @GetMapping("/update")
    public String showUpdateUserInfoForm(Model model) {
        model.addAttribute("client", new Client());
        return "update_form";
    }

    @GetMapping("/delete_process")
    public String deleteProcess()
    {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username;
        if (principal instanceof UserDetails) {
            username = ((UserDetails)principal).getUsername();
        } else {
            username = principal.toString();
        }
        for(Role role : clientService.findByUsername(username).getRolesSet())
        {
            if(role.getName().equals("USER"))
                return "failedDelete";
        }
        boolean result = clientService.deleteClient(username);
        if(result)
            return "successDelete";
        return "failedDelete";
    }


    @PostMapping("/process_update")
    public String processUpdate(Client client)
    {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(client.getPassword());
        client.setPassword(encodedPassword);
            boolean result = clientService.updateClient(client);
            if(result)
                return "successUpdate";
            else
                return "failedUpdate";
    }

    @GetMapping("")
    public String viewHomePage()
    {
        return "index";
    }

    @GetMapping("/clients")
    public String listClients(Model model) {
        List<Client> listClients = (List<Client>) clientService.findAll();
        model.addAttribute("listClients", listClients);
        return "clients";
    }







}
