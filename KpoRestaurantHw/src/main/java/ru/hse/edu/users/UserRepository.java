package ru.hse.edu.users;

import ru.hse.edu.db.DataBaseManager;

import java.sql.SQLException;
import java.util.Optional;

public class UserRepository {
    private static final DataBaseManager manager = new DataBaseManager();
    public static Optional<UserDTO> getUserByLogin(String login) throws SQLException {
        String userRequest = "select * from account where login = '" + login +"'";
        try(var result = manager.performExecuteQuery(userRequest);) {
            if (!result.next()) {
                return Optional.empty();
            }
            return Optional.of(new UserDTO(result.getString("login"), result.getString("hash"),
                    result.getString("role")));
        }
    }
    public static UserRole stringToRole(String string) {
        return switch (string.toLowerCase()) {
            case "admin" -> UserRole.ADMIN;
            case "customer" -> UserRole.CUSTOMER;
            default -> UserRole.NOT_LOGGED_IN;
        };
    }
    public static Boolean doesUserExist(String login) throws SQLException {
        String userExistRequest = "select 1 from account where login='" + login + "'";
        try(var result = manager.performExecuteQuery(userExistRequest)) {
            if(!result.next()) {
                return false;
            }
        }
        return true;
    }
    public static UserRole insertUserInDatabase(UserDTO user) throws SQLException {
        String userInsert = "insert into account (login, hash, role)" +
                "values ('" + user.login() + "', '" + user.hash() + "', '"+ user.role() + "');";
        manager.performExecuteUpdate(userInsert);
        if(doesUserExist(user.login())) {
            return stringToRole(user.role());
        }
        return UserRole.NOT_LOGGED_IN;
    }
}
