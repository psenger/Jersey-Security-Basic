package com.sample;

import com.sample.domain.*;
import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.TestProperties;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.nio.charset.Charset;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

public class UserResourceTest extends JerseyTest {


    protected javax.ws.rs.core.Application configure() {
        enable(TestProperties.LOG_TRAFFIC);
        enable(TestProperties.DUMP_ENTITY);
        final BookDao dao = new BookDao();
        final UserDao userDao = new UserDao();
        return new Application(dao, userDao);
    }

    @Test
    public void testAddUser() {

        String email = "batman@gmail.com";
        String username = "BatMan";
        String[] roles = new String[]{"user"};
        String hashedPassword = "password";

        User user = new User(null, username, email, roles, hashedPassword);

        Entity<User> userEntity = Entity.entity(user, MediaType.APPLICATION_JSON_TYPE);

        Response response = target("users").request().header("Authorization", "Basic " + java.util.Base64.getEncoder().encodeToString("ironman:password".getBytes(Charset.forName("ASCII")))).post(userEntity);

        assertEquals(200, response.getStatus());

        User responseUser = response.readEntity(User.class);

        assertEquals(email, responseUser.getEmail());
        assertEquals(username, responseUser.getUsername());
        assertArrayEquals(roles, responseUser.getRoles());
        assertEquals(hashedPassword, responseUser.getHashedPassword());
        assertNotNull(responseUser.getId());
    }

    @Test
     public void testAddNullUser() {
        Response response = target("users").request().header("Authorization", "Basic " + java.util.Base64.getEncoder().encodeToString("ironman:password".getBytes(Charset.forName("ASCII")))).post(null);
        assertEquals(400, response.getStatus());
    }
}
