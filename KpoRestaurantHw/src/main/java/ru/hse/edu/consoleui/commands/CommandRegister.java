package ru.hse.edu.consoleui.commands;

import ru.hse.edu.consoleui.Environment;
import ru.hse.edu.consoleui.Logger;
import ru.hse.edu.users.UserDTO;
import ru.hse.edu.users.UserRole;

import static ru.hse.edu.users.LogInValidator.createHashString;
import static ru.hse.edu.users.UserRepository.*;

public class CommandRegister extends ConsoleCommand {
    private static final String NAME = "reg";
    private static final String DESCRIPTION = "reg <login> <password> - зарегистрируйтесь как пользователь.";
    private static final String LONG_DESCRIPTION = "reg <login> <password> - введите логин и пароль без пробелов для" +
            "регистрации нового пользователя. Минимальная длина пароля - 4 символа. Максимальная длина - 100.";
    @Override
    public boolean isAllowedInEnv(Environment env) {
        return env.role == UserRole.NOT_LOGGED_IN;
    }

    @Override
    protected void performCommand(Environment env, String[] args) {
        if (args.length != 3) {
            Logger.log("Неправильная команда.");
        }
        try {
            if(args[1].length() > 100) {
                Logger.log("Слишком длинный логин.");
                return;
            }
            if (doesUserExist(args[1])) {
                Logger.log("Пользователь с таким логином уже существует.");
            } else {
                if(args[2].length() < 4) {
                    Logger.log("Слишком короткий пароль.");
                }
                if(args[2].length() > 100) {
                    Logger.log("Слишком длинный пароль.");
                }
                var user = new UserDTO(args[1], createHashString(args[2]), "customer");
                var role = insertUserInDatabase(user);
                if(role == UserRole.NOT_LOGGED_IN) {
                    Logger.log("Пользователь не создан, ошибка регистрации");
                } else {
                    Logger.log("Пользователь " + user.login() + " успешно создан!");
                }
            }

        }catch (Exception e) {
            Logger.log(e.getMessage());
        }
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public String getLongDescription() {
        return LONG_DESCRIPTION;
    }

    @Override
    public String getShortDescription() {
        return DESCRIPTION;
    }
}
