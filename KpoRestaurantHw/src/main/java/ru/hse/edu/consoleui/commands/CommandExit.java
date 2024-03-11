package ru.hse.edu.consoleui.commands;

import ru.hse.edu.consoleui.Environment;
import ru.hse.edu.consoleui.Logger;

import java.sql.SQLException;

import static ru.hse.edu.menu.OrderRepository.cancelAllOrders;

public class CommandExit extends ConsoleCommand {
    private static final String NAME = "exit";
    private static final String DESCRIPTION = "exit - выход из программмы";
    private static final String LONG_DESCRIPTION = "Введите exit для выхода из программы.";
    public static final String EXIT_MESSAGE = "Выход из программы, все несохраненные данные удалены.";
    @Override
    public boolean isAllowedInEnv(Environment env) {
        return true;
    }

    @Override
    protected void performCommand(Environment env, String[] args) {

        try {
            cancelAllOrders();
        } catch (SQLException e) {
            Logger.log("Блюда из заказов утеряны из-за ошибки бд.");
        }
        Logger.log(EXIT_MESSAGE);
        env.isClosed = true;
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
