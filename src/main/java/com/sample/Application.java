/**
 * Created by philip a senger on October 22, 2015
 */
package com.sample;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import com.sample.domain.BookDao;
import com.sample.domain.UserDao;
import com.sample.filters.AuthenticationExceptionMapper;
import com.sample.filters.SecurityFilter;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;

import javax.ws.rs.ext.ContextResolver;

public class Application extends ResourceConfig implements ContextResolver<ObjectMapper> {

    // to build a custom mapper..
    // final ObjectMapper defaultObjectMapper;

    public Application() {
        this(new BookDao(), new UserDao());
    }

    public Application(final BookDao dao, final UserDao userDao) {

        super(RolesAllowedDynamicFeature.class, SecurityFilter.class,AuthenticationExceptionMapper.class,JacksonFeature.class /** defaultObjectMapper **/ );
        packages("com.sample");
        register(new AbstractBinder() {
            @Override
            protected void configure() {
                bind(dao).to(BookDao.class);
                bind(userDao).to(UserDao.class);
            }
        });
    }

    @Override
    public ObjectMapper getContext(Class<?> type) {
        return null;
    }
}
