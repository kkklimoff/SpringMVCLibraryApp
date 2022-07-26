package com.klimov.springproject1.util;

import com.klimov.springproject1.dao.ClientDao;
import com.klimov.springproject1.models.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class ClientValidator implements Validator {

    private final ClientDao clientDao;

    @Autowired
    public ClientValidator(ClientDao clientDao) {
        this.clientDao = clientDao;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Client.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Client client = (Client) target;
        if(clientDao.getClientByName(client.getName()).isPresent())
            errors.rejectValue("name", "", "This name is already registered!");
    }
}
