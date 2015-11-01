/**
 * Created by philip a senger on October 22, 2015
 */
package com.sample;

import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.sample.domain.Book;
import com.sample.domain.BookDao;
import org.apache.commons.codec.digest.DigestUtils;
import org.glassfish.jersey.server.ManagedAsync;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.*;
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
     * ( * When Jersey is deployed in a servlet container )
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

// this was the old way
//    @Path("/{id}")
//    @GET
//    @Produces(MediaType.APPLICATION_JSON)
//    public Book getBook(@PathParam("id") String id) {
//        return dao.getBook(id);
//    }

    @Path("/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @ManagedAsync
    public void getBook(@PathParam("id") String id, @Suspended final AsyncResponse asyncResponse) {
        // asyncResponse.resume( dao.getBookAsync(id) );
        ListenableFuture<Book> bookFutures = dao.getBookAsync(id);
        Futures.addCallback(bookFutures, new FutureCallback<Book>() {
            @Override
            public void onSuccess(Book result) {
                EntityTag entityTag = generateEntityTag(result);
                Response.ResponseBuilder rb = request.evaluatePreconditions(entityTag);
                if (rb != null) {
                    asyncResponse.resume(rb.build());
                } else {
                    asyncResponse.resume(Response.ok().tag(entityTag).entity(result).build());
                }
            }

            @Override
            public void onFailure(Throwable t) {
                // we cound throw Jersey's WebApplicationException.
                asyncResponse.resume(t);
            }
        });
    }

    @RolesAllowed({"user"})
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Book addBook(@Valid @NotNull Book book) {
        return dao.addBook(book);
    }

    EntityTag generateEntityTag(Book book) {
        return new EntityTag(DigestUtils.md5Hex((book != null) ? book.toString() : ""));
    }
}
