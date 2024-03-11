package ru.hse.edu.consoleui.commands;

import ru.hse.edu.consoleui.Environment;
import ru.hse.edu.consoleui.Logger;
import ru.hse.edu.users.UserRole;

import static ru.hse.edu.menu.OrderRepository.getOrdersInString;

public class CommandShowOrders  extends ConsoleCommand {
    private static final String NAME = "show-orders";
    private static final String DESCRIPTION = "show-orders - выводит список заказов пользователя.";
    private static final String LONG_DESCRIPTION = "show-orders - позволяет просмотреть заказы и их статус." +
            "Для заказа войдите как пользователь. Для редактирования меню войдите как администратор.";
    @Override
    public boolean isAllowedInEnv(Environment env) {
        return env.role == UserRole.CUSTOMER;
    }

    @Override
    protected void performCommand(Environment env, String[] args) {
        Logger.log("Вот ваши заказы:");
        Logger.log(getOrdersInString(env.username));
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
