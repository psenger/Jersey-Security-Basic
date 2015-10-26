/**
 * Created by philip a senger on October 22, 2015
 */
package com.sample.filters;

import com.sample.domain.User;
import com.sample.domain.UserDao;

import javax.inject.Inject;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.PreMatching;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.Provider;
import javax.xml.bind.DatatypeConverter;
import java.io.IOException;
import java.nio.charset.Charset;

@Provider
@PreMatching
public class SecurityFilter implements ContainerRequestFilter {

    /**
     * HK2 Injection.
     */
    @Context
    UserDao dao;

    @Inject
    javax.inject.Provider<UriInfo> uriInfo;
    private static final String REALM = "Realm of the impossible";

    public SecurityFilter() {
    }

    @Override
    public void filter(ContainerRequestContext filterContext) throws IOException {
        User user = authenticate(filterContext);
        filterContext.setSecurityContext(new SecurityContextAuthorizer(uriInfo, user));
    }

    private User authenticate(ContainerRequestContext filterContext) {

        // Extract authentication credentials out of the standard headers.
        String authentication = filterContext.getHeaderString(HttpHeaders.AUTHORIZATION);
        if (authentication == null) {
            throw new AuthenticationException("Authentication credentials are required", REALM);
        }
        if (!authentication.startsWith("Basic ")) {
            return null;
            // additional checks should be done here
            // "Only HTTP Basic authentication is supported"
        }
        authentication = authentication.substring("Basic ".length());
        String[] values = new String(DatatypeConverter.parseBase64Binary(authentication), Charset.forName("ASCII")).split(":");
        if (values.length < 2) {
            throw new WebApplicationException(400);
            // "Invalid syntax for username and password"
        }
        String username = values[0];
        String password = values[1];
        if ((username == null) || (password == null)) {
            throw new WebApplicationException(400);
            // "Missing username or password"
        }

        // Validate the extracted credentials
        User user = dao.getUser(username);
        if ( user == null ) {
            System.out.println("USER NOT AUTHENTICATED");
            throw new AuthenticationException("Invalid username\r\n", REALM);
        }
        if ( user.getHashedPassword().equals(password) ) {
            System.out.println("USER AUTHENTICATED");
        } else {
            System.out.println("USER NOT AUTHENTICATED");
            throw new AuthenticationException("Invalid username or password\r\n", REALM);
        }
        return user;
    }


}