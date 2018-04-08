package com.celpa.celpaapp.data;


public class Farmer {

    public long id;
    public String firstName;
    public String lastName;
    public String userName;
    public String password;


    @Override
    public String toString() {
        return String.format("id: %s, firstName: %s, lastName: %s, userName: %s, password: %s",
                id, firstName, lastName, userName, password);
    }
}
