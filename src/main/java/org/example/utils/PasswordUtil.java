package org.example.utils;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordUtil {

    public static String encode(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    public static boolean isValidPassword(String password, String hashPassword) {
        return BCrypt.checkpw(password, hashPassword);
    }

}
