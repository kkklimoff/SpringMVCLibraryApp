package com.klimov.springproject1.models;


import javax.validation.constraints.*;

public class Client {

    private int client_id;

    @NotEmpty(message = "Client's name can't be empty!")
    @Size(max = 120, message = "Client's name can't exceed 120 characters!")
    @Pattern(regexp = "[A-Z]\\w+ [A-Z]\\w+", message = "Client's name should be in format \"Name Surname\"")
    private String name;

    @NotNull(message = "Client's birth year can't be empty!")
    @Max(value = 2022, message = "No clients from the future!")
    @Min(value = 1900, message = "No Biden voters allowed!")
    private int birth_year;

    public Client(){

    }

    public Client(int client_id, String name, int birth_year) {
        this.client_id = client_id;
        this.name = name;
        this.birth_year = birth_year;
    }

    public int getClient_id() {
        return client_id;
    }

    public void setClient_id(int client_id) {
        this.client_id = client_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBirth_year() {
        return birth_year;
    }

    public void setBirth_year(int birth_year) {
        this.birth_year = birth_year;
    }
}
