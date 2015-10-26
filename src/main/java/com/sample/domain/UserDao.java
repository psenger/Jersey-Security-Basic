/**
 * Created by philip a senger on October 22, 2015
 */
package com.sample.domain;

import java.util.*;

public class UserDao {

    private Map<String, User> users;

    public UserDao() {
        users = new HashMap<String, User>();
        User user = new User("ironman", new String[]{ "user" }, "password");
        users.put(user.getUsername(), user);
    }

    public Collection<User> getUsers() {
        return (users.values());
    }

    public User getUser(String username) {
        return (users.get(username));
    }
}
