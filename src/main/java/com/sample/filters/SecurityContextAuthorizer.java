/**
 * Created by philip a senger on October 22, 2015
 */

package com.sample.filters;

import com.sample.domain.User;

import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriInfo;
import java.security.Principal;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class SecurityContextAuthorizer implements SecurityContext {

    private User user;
    private Principal principal;
    private javax.inject.Provider<UriInfo> uriInfo;

    public SecurityContextAuthorizer(final javax.inject.Provider<UriInfo> uriInfo, final User user) {
        this.user = user;
        this.principal = new Principal() {
            public String getName() {
                return user.getUsername();
            }
        };
        this.uriInfo = uriInfo;
    }

    public Principal getUserPrincipal() {
        return this.principal;
    }

    public boolean isUserInRole(String role) {
        Set roles = new HashSet<String>();
        if (user.getRoles() != null) {
            Collection<String> strings = Arrays.asList(user.getRoles());
            roles.addAll(strings);
        }
        return roles.contains(((role == null) ? "" : role));
    }

    public boolean isSecure() {
        return "https".equals(uriInfo.get().getRequestUri().getScheme());
    }

    public String getAuthenticationScheme() {
        return SecurityContext.BASIC_AUTH;
    }
}
