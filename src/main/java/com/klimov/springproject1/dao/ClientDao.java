package com.klimov.springproject1.dao;

import com.klimov.springproject1.models.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class ClientDao
{

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ClientDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Client> getClients(){
        return jdbcTemplate.query("SELECT * FROM Clients", new BeanPropertyRowMapper<>(Client.class));
    }

    public Optional<Client> getClientById(int id){
        return jdbcTemplate.query("SELECT * FROM Clients WHERE client_id = ?",
                new Object[] {id}, new BeanPropertyRowMapper<>(Client.class)).stream().findAny();
    }

    public Optional<Client> getClientByName(String name){
        return jdbcTemplate.query("SELECT * FROM Clients WHERE name = ?", new Object[] {name},
                new BeanPropertyRowMapper<>(Client.class)).stream().findAny();
    }

    public void saveClient(Client client){
        jdbcTemplate.update("INSERT INTO Clients(name, birth_year) VALUES (?,?)",
                client.getName(), client.getBirth_year());
    }

    public void updateClient(int id, Client updatedClient){
        jdbcTemplate.update("UPDATE Clients SET name = ?, birth_year = ? WHERE client_id = ?",
                updatedClient.getName(), updatedClient.getBirth_year(), id);
    }

    public void delete(int id){
        jdbcTemplate.update("DELETE FROM Clients WHERE client_id = ?", id);
    }
}
