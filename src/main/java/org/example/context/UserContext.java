package org.example.context;

import org.example.model.entity.User;

public class UserContext {

    public static ThreadLocal<User> contextUser = new ThreadLocal<>();

    public static void setContextUser(User user) {
        contextUser.set(user);
    }

    public static User getContextUser() {
        return contextUser.get();
    }

}
