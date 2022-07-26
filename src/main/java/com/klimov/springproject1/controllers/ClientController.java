package com.klimov.springproject1.controllers;

import com.klimov.springproject1.dao.BookDao;
import com.klimov.springproject1.dao.ClientDao;
import com.klimov.springproject1.models.Client;
import com.klimov.springproject1.util.ClientValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping("/clients")
public class ClientController {

    private final ClientDao clientDao;
    private final BookDao bookDao;
    private final ClientValidator clientValidator;

    @Autowired
    public ClientController(ClientDao clientDao, BookDao bookDao, ClientValidator clientValidator) {
        this.clientDao = clientDao;
        this.bookDao = bookDao;
        this.clientValidator = clientValidator;
    }

    @GetMapping()
    public String index(Model model){
        model.addAttribute("clients", clientDao.getClients());
        return "clients/index";
    }

    @GetMapping("/{id}")
    public String details(@PathVariable("id") int id,
                          Model model)
    {
        Optional<Client> client = clientDao.getClientById(id);
        if(!client.isPresent()) return "redirect:/clients";
        model.addAttribute("client", client.get());
        model.addAttribute("clientsBooks", bookDao.getBooksPerClient(client.get().getClient_id()));
        return "clients/details";
    }

    @GetMapping("/new")
    public String newClient(@ModelAttribute("client") Client client){
        return "clients/add";
    }

    @PostMapping()
    public String add(@ModelAttribute("client") @Valid Client client,
                      BindingResult bindingResult)
    {
        clientValidator.validate(client,bindingResult);
        if (bindingResult.hasErrors()) return "clients/add";
        clientDao.saveClient(client);
        return ("redirect:/clients");
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model,
                       @PathVariable("id") int id)
    {
        Optional<Client> client = clientDao.getClientById(id);
        if(!client.isPresent()) return "redirect:/clients";
        model.addAttribute("client", client.get());
        return "clients/edit";
    }

    @PatchMapping("/{id}")
    public String update(@PathVariable("id") int id,
                         @ModelAttribute("client") @Valid Client updatedClient,
                         BindingResult bindingResult)
    {
        Client oldClient = clientDao.getClientById(id).get();
        if (!oldClient.getName().equals(updatedClient.getName()))
            clientValidator.validate(updatedClient,bindingResult);

        if(bindingResult.hasErrors()) return "clients/edit";
        clientDao.updateClient(id, updatedClient);
        return "redirect:/clients/"+id;
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id){
        clientDao.delete(id);
        return "redirect:/clients";
    }
}
