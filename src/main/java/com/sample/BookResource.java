/**
 * Created by philip a senger on October 22, 2015
 */
package com.sample;

import com.sample.domain.Book;
import com.sample.domain.BookDao;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import java.util.Collection;

@PermitAll
@Path("/books")
public class BookResource {

    /**
     * HK2 Injection.
     */
    @Context
    BookDao dao;

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
    public Collection<Book> getBooks() {
        return dao.getBooks();
    }

    @Path("/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Book getBook(@PathParam("id") String id) {
        return dao.getBook(id);
    }

    @RolesAllowed({"user"})
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Book addBook( Book book) {
        return dao.addBook( book );
    }
}
