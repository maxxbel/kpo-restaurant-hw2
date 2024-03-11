package ru.hse.edu.users;


public class LogInValidator {
    public static Boolean comparePasswords(UserDTO user, String password) {
        return createHashString(password).equals(user.hash());
    }
    // Тело в этом методе можно заменить чем-то более секретным, просто сейчас нет времени
    public static String createHashString(String password) {
        return String.valueOf(password.hashCode());
    }
}
