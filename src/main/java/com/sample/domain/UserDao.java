/**
 * Created by philip a senger on October 22, 2015
 */
package com.sample.domain;

import java.util.*;

public class UserDao {

    private Map<String, User> users;

    public UserDao() {
        users = new HashMap<String, User>();
        User user = new User("1","ironman","ironman@gmail.com", new String[]{ "user", "admin" }, "password");
        users.put(user.getUsername(), user);
    }

    public Collection<User> getUsers() {
        return (users.values());
    }

    public User getUser(String username) {
        return (users.get(username));
    }

    public User addUser( User user ) {
        user.setId( UUID.randomUUID().toString() );
        users.put( user.getId(), user );
        return user;
    }
}
