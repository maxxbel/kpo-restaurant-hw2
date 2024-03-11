package ru.hse.edu.consoleui;

import ru.hse.edu.consoleui.commands.*;

import java.util.List;

public class CommandsHolder {
    private static CommandsHolder instance = null;
    private static List<ConsoleCommand> commands;
    private CommandsHolder() {
        // Когда я это написал, я думал, что команд будет немного...
        commands = List.of(
                new CommandHelp(),
                new CommandExit(),
                new CommandLogIn(),
                new CommandLogOut(),
                new CommandRegister(),
                new CommandShowMenu(),
                new CommandAddDish(),
                new CommandDeleteDish(),
                new CommandChangeQuantity(),
                new CommandShowOrders(),
                new CommandCreateOrder(),
                new CommandAddDishToOrder(),
                new CommandCancelOrder(),
                new CommandRemoveDishFromOrder(),
                new CommandSubmitOrder(),
                new CommandPay(),
                new CommandViewRevenue()
        );
    }
    public static CommandsHolder getInstance() {
        if (instance == null) {
            instance = new CommandsHolder();
        }
        return instance;
    }

    public List<ConsoleCommand> getCommands() {
        return commands;
    }
}
