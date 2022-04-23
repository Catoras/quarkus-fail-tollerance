package org.acme.controller;

import org.acme.model.User;
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

@Path("/user/sign_in")
@Produces(MediaType.APPLICATION_JSON)
public class UserController {
    List<User> userList = new ArrayList<>();
    Logger LGR = Logger.getLogger("MESSAGE SERVER");
    Boolean error = false;

    @GET
    @Fallback(fallbackMethod = "getUserFallbackList")
    public List<User> getUserList(){
        LGR.info("Solicitando Lista de usuarios");
        if(error) {
            userList.remove(userList.size()-1);
            falla();
        }

        return this.userList;
    }
    public List<User> getUserFallbackList(){
        var user = new User("errorEmail@error.com","Error", "Error", "Error");
        return List.of(user);
    }

    public void falla(){
        error = false;
        LGR.warning("Se ha producido una falla");
        throw new RuntimeException("Fallo el programa");
    }

    @POST
    public void setPersonaList(){
        LGR.info("Insertando Usuario");
        Scanner entrada = new Scanner(System.in);

        String email="";
        String userName="";
        String password="";
        String nickName="";
        LGR.info("Email:");
        email=entrada.nextLine();
        LGR.info("User name:");
        userName=entrada.nextLine();
        LGR.info("Password:");
        password=entrada.nextLine();
        LGR.info("Nickname:");
        nickName=entrada.nextLine();

        var u = new User(email,userName,password,nickName);

        userList.add(u);
        LGR.info("Usuario insertado");
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
