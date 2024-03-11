package ru.hse.edu.consoleui.commands;

import ru.hse.edu.consoleui.CommandsHolder;
import ru.hse.edu.consoleui.Environment;
import ru.hse.edu.consoleui.Logger;

public class CommandHelp extends ConsoleCommand{
    private static final String NAME = "help";
    private static final String DESCRIPTION = "help - выводит информацию о возможных командах";
    private static final String LONG_DESCRIPTION = "help - выводит информацию о возможных командах" +
            "\nВведите help <название_команды> для подробной инструкции";
    private static final String HELP_ALL = "Вот доступные здесь команды:";
    private static final String HELP_ONE = "Подробное описание команды:";
    @Override
    public boolean isAllowedInEnv(Environment env) {
        return true;
    }

    @Override
    protected void performCommand(Environment env, String[] args) {
        if(args.length == 1) {
            Logger.log(HELP_ALL);
            for (var comm : CommandsHolder.getInstance().getCommands()) {
                if (comm.isAllowedInEnv(env)) {
                    Logger.log(comm.getShortDescription());
                }
            }
        }
        if(args.length == 2) {
            boolean doesCommandExist = false;
            for (var comm : CommandsHolder.getInstance().getCommands()) {
                if(comm.getName().equals(args[1])) {
                    doesCommandExist = true;
                    Logger.log(HELP_ONE);
                    Logger.log(comm.getLongDescription());
                }
            }
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
