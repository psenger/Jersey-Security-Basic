package com.sample;

import com.sample.domain.Book;
import com.sample.domain.BookDao;
import com.sample.domain.UserDao;
import junit.framework.Assert;
import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.TestProperties;
import org.junit.Test;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.nio.charset.Charset;

import static org.junit.Assert.assertNotNull;

public class BookResourceTest extends JerseyTest {


    protected Application configure() {
        enable(TestProperties.LOG_TRAFFIC);
        enable(TestProperties.DUMP_ENTITY);
        final BookDao dao = new BookDao();
        final UserDao userDao = new UserDao();
        return new BookApplication(dao, userDao);
    }

    @Test
    public void testAddBook() {
        Book book = new Book();
        String title = "The Renegade of the Enemy";
        String author = "Michael Van Der Van";
        book.setTitle(title);
        book.setAuthor(author);
        Entity<Book> bookEntity = Entity.entity(book, MediaType.APPLICATION_JSON_TYPE);

        Response response = target("books").request().header("Authorization", "Basic " + java.util.Base64.getEncoder().encodeToString("ironman:password".getBytes(Charset.forName("ASCII")))).post(bookEntity);

        Assert.assertEquals(200, response.getStatus());

        Book responseBook = response.readEntity(Book.class);
        Assert.assertEquals(title, responseBook.getTitle());
        Assert.assertEquals(author, responseBook.getAuthor());
    }

    @Test
    public void testAddBookUnauthenticated() {
        Book book = new Book();
        String title = "The Renegade of the Enemy";
        String author = "Michael Van Der Van";
        book.setTitle(title);
        book.setAuthor(author);
        Entity<Book> bookEntity = Entity.entity(book, MediaType.APPLICATION_JSON_TYPE);

        Response response = target("books").request().header("Authorization", "Basic " + java.util.Base64.getEncoder().encodeToString("ironman:notpassword".getBytes( Charset.forName("ASCII")))).post(bookEntity);

        Assert.assertEquals(401, response.getStatus());

    }

    @Test
    public void testGetBook() {
        Book response = target("books").path("1").request().header("Authorization", "Basic " + java.util.Base64.getEncoder().encodeToString("ironman:password".getBytes(Charset.forName("ASCII")))).get(Book.class);
        assertNotNull(response);
    }

}
