package ru.hse.edu.consoleui.commands;

import ru.hse.edu.consoleui.Environment;
import ru.hse.edu.consoleui.Logger;
import ru.hse.edu.users.UserRepository;
import ru.hse.edu.users.UserRole;

import static ru.hse.edu.users.LogInValidator.comparePasswords;
import static ru.hse.edu.users.UserRepository.stringToRole;

public class CommandLogIn extends ConsoleCommand {
    private static final String NAME = "log-in";
    private static final String DESCRIPTION = "log-in <login> <password> - введите логин и пароль для входа в систему.";
    private static final String LONG_DESCRIPTION = "log-in <login> <password> - введите логин и пароль для входа в систему." +
            "\nДля регистрации введите команду reg <login> <password>";
    @Override
    public boolean isAllowedInEnv(Environment env) {
        return env.role == UserRole.NOT_LOGGED_IN;
    }

    @Override
    protected void performCommand(Environment env, String[] args) {
        if (args.length != 3) {
            Logger.log("Неправильная команда.");
            return;
        }
        try {
            var userOpt = UserRepository.getUserByLogin(args[1]);
            if (userOpt.isEmpty()) {
                Logger.log("Нет такого пользователя");
            } else {
                var user = userOpt.get();
                if(comparePasswords(user, args[2])) {
                    env.role = stringToRole(user.role());
                    if(env.role == UserRole.NOT_LOGGED_IN) {
                        Logger.log("Ошибка системы, неизвестная роль у пользователя.");
                        return;
                    }
                    env.username = user.login();
                    Logger.log("Здравствуйте, " + env.username + " !");
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
