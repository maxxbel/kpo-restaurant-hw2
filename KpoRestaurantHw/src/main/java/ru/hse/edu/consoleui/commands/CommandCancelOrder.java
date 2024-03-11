package ru.hse.edu.consoleui.commands;

import ru.hse.edu.consoleui.Environment;
import ru.hse.edu.consoleui.Logger;
import ru.hse.edu.menu.OrderStatus;
import ru.hse.edu.users.UserRole;

import java.sql.SQLException;

import static ru.hse.edu.menu.OrderRepository.getOrderByName;

public class CommandCancelOrder extends ConsoleCommand {
    private static final String NAME = "cancel-order";
    private static final String DESCRIPTION = "cancel-order <order_name> - отменить заказ.";
    private static final String LONG_DESCRIPTION = "cancel-order <order_name> - отменить заказ." +
            "\n Отмена заказа вернет блюда в меню, заказ можно просмотреть командой show-orders.";
    @Override
    public boolean isAllowedInEnv(Environment env) {
        return env.role == UserRole.CUSTOMER;
    }

    @Override
    protected void performCommand(Environment env, String[] args) {
        if (args.length != 2) {
            Logger.log("Неверная команда");
            return;
        }
        var optOrder = getOrderByName(env.username, args[1]);
        if (optOrder.isEmpty()) {
            Logger.log("Нет такого заказа.");
            return;
        }
        var order = optOrder.get();
        try {
            order.cancel();
        }catch (SQLException e) {
            Logger.log(e.getMessage());
            Logger.log("Проблема доступа к базе данных.");
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
