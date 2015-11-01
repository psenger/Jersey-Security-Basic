package com.sample;

import com.sample.domain.Book;
import com.sample.domain.BookDao;
import com.sample.domain.Genre;
import com.sample.domain.UserDao;
import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.TestProperties;
import org.junit.Test;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.EntityTag;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.TimeZone;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class BookResourceTest extends JerseyTest {


    protected javax.ws.rs.core.Application configure() {
        enable(TestProperties.LOG_TRAFFIC);
        enable(TestProperties.DUMP_ENTITY);
        final BookDao dao = new BookDao();
        final UserDao userDao = new UserDao();
        return new Application(dao, userDao);
    }

    @Test
    public void testAddBook() {
        Book book = new Book();
        String title = "The Renegade of the Enemy";
        String author = "Michael Van Der Van";
        book.setTitle(title);
        book.setAuthor(author);
        Date now = new Date();
        book.setPublished(now);
        book.setGenre(Genre.HISTORY);
        Entity<Book> bookEntity = Entity.entity(book, MediaType.APPLICATION_JSON_TYPE);

        Response response = target("books").request().header("Authorization", "Basic " + java.util.Base64.getEncoder().encodeToString("ironman:password".getBytes(Charset.forName("ASCII")))).post(bookEntity);

        assertEquals(200, response.getStatus());

        Book responseBook = response.readEntity(Book.class);
        assertEquals(title, responseBook.getTitle());
        assertEquals(author, responseBook.getAuthor());
        assertEquals(now, responseBook.getPublished());
        assertEquals(Genre.HISTORY, responseBook.getGenre());
    }

    @Test
    public void testAddBookWithAdditionalDataInThePayload() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("title", "Seventh Door");
        map.put("author", "Aramazd Natanael");
        map.put("published", "2015-10-31T21:22:44.732 UTC");
        map.put("genre", "SCIFI");
        map.put("junk1", "this does not exist in the model");
        map.put("junk2", "this does not exist in the model");
        map.put("junk3", "this does not exist in the model");
        map.put("junk4", "this does not exist in the model");
        Entity<HashMap<String, Object>> bookEntity = Entity.entity(map, MediaType.APPLICATION_JSON_TYPE);

        Response response = target("books").request().header("Authorization", "Basic " + java.util.Base64.getEncoder().encodeToString("ironman:password".getBytes(Charset.forName("ASCII")))).post(bookEntity);

        assertEquals(200, response.getStatus());

        Book responseBook = response.readEntity(Book.class);
        assertEquals("Seventh Door", responseBook.getTitle());
        assertEquals("Aramazd Natanael", responseBook.getAuthor());

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS 'UTC'");
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        String utcTime = sdf.format(responseBook.getPublished());

        assertEquals("2015-10-31T21:22:44.732 UTC", utcTime);
        assertEquals(Genre.SCIFI, responseBook.getGenre());
    }

    @Test
    public void testAddBookUnauthenticated() {
        Book book = new Book();
        String title = "The Renegade of the Enemy";
        String author = "Michael Van Der Van";
        book.setTitle(title);
        book.setAuthor(author);
        Entity<Book> bookEntity = Entity.entity(book, MediaType.APPLICATION_JSON_TYPE);

        Response response = target("books").request().header("Authorization", "Basic " + java.util.Base64.getEncoder().encodeToString("ironman:notpassword".getBytes(Charset.forName("ASCII")))).post(bookEntity);

        assertEquals(401, response.getStatus());
    }

    @Test
    public void testGetBook() {
        Book response = target("books").path("1").request().header("Authorization", "Basic " + java.util.Base64.getEncoder().encodeToString("ironman:password".getBytes(Charset.forName("ASCII")))).get(Book.class);
        assertNotNull(response);
    }

    @Test
    public void testGetMissingBook() {
        Response response = target("books").path("0").request().header("Authorization", "Basic " + java.util.Base64.getEncoder().encodeToString("ironman:password".getBytes(Charset.forName("ASCII")))).get();
        assertNotNull(response);
        assertEquals(404, response.getStatus());
        String s = response.readEntity(String.class);
        assertEquals("Book Not Found", s);
    }

    @Test
    public void testGetBookEntityTag() {
        EntityTag entityTag = target("books").path("1").request().header("Authorization", "Basic " + java.util.Base64.getEncoder().encodeToString("ironman:password".getBytes(Charset.forName("ASCII"))))
                                                                .get().getEntityTag();
        assertNotNull(entityTag);

        Response response = target("books").path("1").request().header("Authorization", "Basic " + java.util.Base64.getEncoder().encodeToString("ironman:password".getBytes(Charset.forName("ASCII"))))
                                                                .header("If-None-Match", entityTag).get( );

        assertEquals(304,response.getStatus());
    }

}
