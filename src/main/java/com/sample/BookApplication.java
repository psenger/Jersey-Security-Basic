/**
 * Created by philip a senger on October 22, 2015
 */
package com.sample;

import com.sample.domain.BookDao;
import com.sample.domain.UserDao;
import com.sample.filters.AuthenticationExceptionMapper;
import com.sample.filters.SecurityFilter;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;

public class BookApplication extends ResourceConfig {

    public BookApplication() {
        this(new BookDao(), new UserDao());
    }

    public BookApplication(final BookDao dao,final UserDao userDao) {
        packages("com.sample");
        register(RolesAllowedDynamicFeature.class);
        register(new AbstractBinder() {
            @Override
            protected void configure() {
                bind(dao).to(BookDao.class);
                bind(userDao).to(UserDao.class);
            }
        })
        .register(SecurityFilter.class)
        .register(AuthenticationExceptionMapper.class);
    }
}
