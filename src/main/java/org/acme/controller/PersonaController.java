package org.acme.controller;

import org.acme.model.Persona;
import org.eclipse.microprofile.faulttolerance.Fallback;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Path("/persona")
@Produces(MediaType.APPLICATION_JSON)
public class PersonaController {
    List<Persona> personaList = new ArrayList<>();
    Logger LOGGER = Logger.getLogger("Demologger");
    Boolean error = false;

    @GET
    @Fallback(fallbackMethod = "getPersonaFallbackList")
    public List<Persona> getPersonaList(){
        LOGGER.info("Ejecutando person list");
        if(personaList.isEmpty() || error) {
            personaList.remove(personaList.size()-1);
            falla();
        }

        return this.personaList;
    }
    public List<Persona> getPersonaFallbackList(){
        var persona = new Persona(-1L,"Alex", "alex@live.com");
        return List.of(persona);
    }

    public void falla(){
        error = false;
        LOGGER.warning("Se ha producido una falla wachoooo");
        throw new RuntimeException("Ha fallao wachoooo");
    }

    @POST
    public void setPersonaList(){
        LOGGER.info("Insertando persona");
        var p = new Persona();
        Long id=0L;
        String name="";
        String email="";

        Scanner entrada = new Scanner(System.in);

        id=entrada.nextLong();
        name=entrada.next();
        email=entrada.next();

        p.setPersonaID(id);
        p.setName(name);
        p.setEmail(email);

        personaList.add(p);
        LOGGER.info("Persona insertada");
        if(!validate(email)){
            error=true;
        }
    }

    public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    public static boolean validate(String emailStr) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
        return matcher.find();
    }

}
