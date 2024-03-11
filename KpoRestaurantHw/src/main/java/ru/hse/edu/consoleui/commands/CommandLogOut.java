package ru.hse.edu.consoleui.commands;

import ru.hse.edu.consoleui.Environment;
import ru.hse.edu.consoleui.Logger;
import ru.hse.edu.users.UserRole;

public class CommandLogOut extends ConsoleCommand {
    private static final String NAME = "log-out";
    private static final String DESCRIPTION = "log-out - введите для выхода из системы.";
    private static final String LONG_DESCRIPTION = "log-out - введите, чтобы выйти из системы" +
            " или сменить пользователя.";
    @Override
    public boolean isAllowedInEnv(Environment env) {
        return env.role != UserRole.NOT_LOGGED_IN;
    }

    @Override
    protected void performCommand(Environment env, String[] args) {
        env.role = UserRole.NOT_LOGGED_IN;
        env.username = null;
        Logger.log("Вы вышли из системы.");
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
