package ru.hse.edu.consoleui.commands;

import ru.hse.edu.consoleui.Environment;
import ru.hse.edu.consoleui.Logger;
import ru.hse.edu.users.UserRole;

import java.sql.SQLException;

import static ru.hse.edu.menu.MenuRepository.deleteDishByName;
import static ru.hse.edu.menu.MenuRepository.doesDishExist;

public class CommandDeleteDish extends ConsoleCommand {

    private static final String NAME = "delete-dish-menu";
    private static final String DESCRIPTION = "delete-dish-menu <dish_name>" +
            " - убрать блюдо с данным именем из меню.";
    private static final String LONG_DESCRIPTION = "add-dish-menu <dish_name>" +
            " - убрать блюдо с данным именем из меню, доступно только администратору.";
    @Override
    public boolean isAllowedInEnv(Environment env) {
        return env.role == UserRole.ADMIN;
    }

    @Override
    protected void performCommand(Environment env, String[] args) {

        if(args.length != 2) {
            Logger.log("Неверная команда.");
            return;
        }
        try {
            if (!doesDishExist(args[1])) {
                Logger.log("Такого блюда нет в меню.");
                return;
            }
            deleteDishByName(args[1]);
        }catch (SQLException e) {
            Logger.log(e.getMessage());
            Logger.log("Ошибка доступа к базе данных.");
        }
        Logger.log("Блюдо успешно удалено из меню.");
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
