package ru.hse.edu.consoleui.commands;

import ru.hse.edu.consoleui.Environment;
import ru.hse.edu.consoleui.Logger;

public abstract class ConsoleCommand {
    private static final String NOT_ALLOWED = "Нет такой команды. Введите help для списка доступных комманд.";
    public void execute(Environment env, String[] args) {
        if(!isAllowedInEnv(env)) {
            Logger.log(NOT_ALLOWED);
        }
        performCommand(env, args);
    }
    public abstract boolean isAllowedInEnv(Environment env);
    protected abstract void performCommand(Environment env, String[] args);
    public abstract String getName();
    public abstract String getLongDescription();
    public abstract String getShortDescription();

}
