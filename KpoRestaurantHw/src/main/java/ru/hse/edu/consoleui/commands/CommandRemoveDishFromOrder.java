package ru.hse.edu.consoleui.commands;

import ru.hse.edu.consoleui.Environment;
import ru.hse.edu.consoleui.Logger;
import ru.hse.edu.users.UserRole;

import java.sql.SQLException;

import static ru.hse.edu.menu.OrderRepository.getOrderByName;

public class CommandRemoveDishFromOrder extends ConsoleCommand {
    private static final String NAME = "remove-dish";
    private static final String DESCRIPTION = "remove-dish <dish_name> <order_name> - убрать позицию из заказа.";
    private static final String LONG_DESCRIPTION = "remove-dish <dish_name> <order_name> - убирает блюда с таким именем." +
            "\n Команда вернет блюда в меню, заказ можно просмотреть командой show-orders.";
    @Override
    public boolean isAllowedInEnv(Environment env) {
        return env.role == UserRole.CUSTOMER;
    }

    @Override
    protected void performCommand(Environment env, String[] args) {

        if (args.length != 3) {
            Logger.log("Неверная команда");
            return;
        }

        var optOrder = getOrderByName(env.username, args[2]);
        if (optOrder.isEmpty()) {
            Logger.log("Нет такого заказа.");
            return;
        }

        try {
            optOrder.get().removeDishByName(args[1]);
        } catch (SQLException e) {
            Logger.log(e.getMessage());
            Logger.log("Ошибка подключения к базе данных.");
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
