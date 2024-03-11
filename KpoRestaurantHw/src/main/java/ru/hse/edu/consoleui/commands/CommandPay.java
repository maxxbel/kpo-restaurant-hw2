package ru.hse.edu.consoleui.commands;

import ru.hse.edu.consoleui.Environment;
import ru.hse.edu.consoleui.Logger;
import ru.hse.edu.menu.OrderRepository;
import ru.hse.edu.menu.OrderStatus;
import ru.hse.edu.users.UserRole;

import java.sql.SQLException;

import static ru.hse.edu.menu.OrderRepository.getOrderByName;

public class CommandPay extends ConsoleCommand {
    private static final String NAME = "pay";
    private static final String DESCRIPTION = "pay <order_name> - оплатить готовый заказ.";
    private static final String LONG_DESCRIPTION = "pay <order_name> - оплатить готовый заказ.";
    @Override
    public boolean isAllowedInEnv(Environment env) {
        return env.role == UserRole.CUSTOMER;
    }

    @Override
    protected void performCommand(Environment env, String[] args) {

        if (args.length != 2) {
            Logger.log("Неверная команда.");
            return;
        }
        var oprOrder = getOrderByName(env.username, args[1]);
        if (oprOrder.isEmpty()) {
            Logger.log("Нет такого заказа.");
            return;
        }
        if (oprOrder.get().getStatus() != OrderStatus.DONE) {
            Logger.log("Заказ не является готовым.");
            return;
        }
        try {
            OrderRepository.payForOrder(oprOrder.get());
        } catch (SQLException e) {
            Logger.log(e.getMessage());
            Logger.log("Ошибка доступа к бд.");
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
