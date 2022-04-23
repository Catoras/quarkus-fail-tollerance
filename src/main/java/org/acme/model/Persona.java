package org.acme.model;

public class Persona {
    private Long personaID;
    private String name;
    private String email;

    public Persona() {
    }

    public Persona(Long personaID, String name, String email) {
        this.personaID = personaID;
        this.name = name;
        this.email = email;
    }

    public Long getPersonaID() {
        return personaID;
    }

    public void setPersonaID(Long personaID) {
        this.personaID = personaID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
