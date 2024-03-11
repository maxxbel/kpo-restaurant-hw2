package ru.hse.edu.consoleui.commands;

import ru.hse.edu.consoleui.Environment;
import ru.hse.edu.consoleui.Logger;
import ru.hse.edu.menu.Order;
import ru.hse.edu.users.UserRole;

import static ru.hse.edu.menu.OrderRepository.addOrder;

public class CommandCreateOrder extends ConsoleCommand{
    private static final String NAME = "create-order";
    private static final String DESCRIPTION = "create-order <order_name>" +
            " - создайте заказ с уникальным названием.";
    private static final String LONG_DESCRIPTION = "create-order <order_name>" +
            " - создать пустой заказ для заполнения блюдами из меню.";
    @Override
    public boolean isAllowedInEnv(Environment env) {
        return env.role == UserRole.CUSTOMER;
    }

    @Override
    protected void performCommand(Environment env, String[] args) {

        if(args.length != 2) {
            Logger.log("Неверная команда.");
        }

        var order = new Order(env.username, args[1]);
        addOrder(order);
        Logger.log("Добавлен заказ, заполните его блюдами из меню.");
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
