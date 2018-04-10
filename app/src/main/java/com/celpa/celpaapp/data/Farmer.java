package com.celpa.celpaapp.data;


import com.google.gson.JsonObject;

public class Farmer {

    public long id;
    public String firstName;
    public String lastName;
    public String email;
    public String userName;
    public String password;


    @Override
    public String toString() {
        return String.format("id: %s, firstName: %s, lastName: %s, userName: %s, password: %s",
                id, firstName, lastName, userName, password);
    }

    public JsonObject toJsonObject() {
        JsonObject json = new JsonObject();
        json.addProperty("firstName", firstName);
        json.addProperty("lastName", lastName);
        json.addProperty("email", email);
        json.addProperty("userName", userName);
        json.addProperty("password", password);

        return json;
    }
}
