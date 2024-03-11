package ru.hse.edu.consoleui.commands;

import ru.hse.edu.consoleui.Environment;
import ru.hse.edu.consoleui.Logger;
import ru.hse.edu.users.UserRole;

import static ru.hse.edu.menu.OrderRepository.getOrderByName;

public class CommandSubmitOrder extends ConsoleCommand {
    private static final String NAME = "submit-order";
    private static final String DESCRIPTION = "submit-order <order_name>" +
            " - отдать заказ на кухню готовиться.";
    private static final String LONG_DESCRIPTION = "submit-order <order_name>" +
            " - вы можете добавлять блюда, пока заказ не готов.";
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

        optOrder.get().submit();
        Logger.log("Ваш заказ готовится.");
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
