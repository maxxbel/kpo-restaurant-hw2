package ru.hse.edu.consoleui.commands;

import ru.hse.edu.consoleui.Environment;
import ru.hse.edu.consoleui.Logger;
import ru.hse.edu.users.UserRole;

import java.sql.SQLException;

import static ru.hse.edu.menu.MenuRepository.doesDishExist;
import static ru.hse.edu.menu.MenuRepository.editQuantityByName;

public class CommandChangeQuantity extends ConsoleCommand {

    private static final String NAME = "change-dish-quantity";
    private static final String DESCRIPTION = "change-dish-quantity <dish_name> <new_quantity>" +
            " - изменить оставшееся количество блюда в меню.";
    private static final String LONG_DESCRIPTION = "add-dish-menu <dish_name> <new_quantity>" +
            " - изменить количество блюда, введите имя, затем новое количество," +
            " доступно только администратору.";
    @Override
    public boolean isAllowedInEnv(Environment env) {
        return env.role == UserRole.ADMIN;
    }

    @Override
    protected void performCommand(Environment env, String[] args) {
        if(args.length != 3) {
            Logger.log("Неверная команда");
            return;
        }
        int quantity;
        try {
            quantity = Integer.parseInt(args[2]);
        } catch (NumberFormatException e) {
            Logger.log(e.getMessage());
            Logger.log("Количество блюд должно быть числом");
            return;
        }
        if (quantity < 0) {
            Logger.log("Колилество блюда должно быть положительным");
        }
        try {
            if(!doesDishExist(args[1])) {
                Logger.log("Нет такого блюда.");
                return;
            }
            editQuantityByName(args[1], quantity);
        } catch (SQLException e) {
            Logger.log(e.getMessage());
            Logger.log("Ошибка доступа к базе данных.");
        }
        Logger.log("Количество успешно изменено.");
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
