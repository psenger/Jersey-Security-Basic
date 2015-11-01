package com.sample;

import com.sample.domain.User;
import com.sample.domain.UserDao;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import java.util.Collection;

/**
 * Created by philipsenger on 30/10/2015.
 */
@PermitAll
@Path("/users")
public class UserResource {

    /**
     * HK2 Injection.
     */
    @Context
    UserDao dao;

    @Context
    Request request;

    /**
     * What can be injected via @Context
     *   ( * When Jersey is deployed in a servlet container )
     * -----------------------
     * Application
     * URLInfo
     * Request
     * HttpHeaders
     * SecurityContext
     * Providers
     * ServletConfig *
     * ServletContext *
     * HttpServletRequest *
     * HttpServletResponse *
     */

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Collection<User> getUsers() {
        return dao.getUsers();
    }

    @Path("/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public User getBook(@PathParam("id") String id) {
        return dao.getUser(id);
    }

    @RolesAllowed({"admin"})
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public User addBook( @Valid @NotNull User user ) {
        return dao.addUser( user );
    }
}
