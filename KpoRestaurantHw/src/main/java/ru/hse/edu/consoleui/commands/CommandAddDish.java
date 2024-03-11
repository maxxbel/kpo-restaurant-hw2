package ru.hse.edu.consoleui.commands;

import ru.hse.edu.consoleui.Environment;
import ru.hse.edu.consoleui.Logger;
import ru.hse.edu.menu.DishDTO;
import ru.hse.edu.users.UserRole;

import java.math.BigDecimal;
import java.sql.SQLException;

import static java.lang.Integer.parseInt;
import static ru.hse.edu.menu.MenuRepository.doesDishExist;
import static ru.hse.edu.menu.MenuRepository.insertDishIntoMenu;

public class CommandAddDish  extends ConsoleCommand {
    private static final String NAME = "add-dish-menu";
    private static final String DESCRIPTION = "add-dish-menu <dish_name> <cost> <seconds_to_cook> <quantity>" +
            " - добавить новое блюдо в меню.";
    private static final String LONG_DESCRIPTION = "add-dish-menu <dish_name> <cost> <seconds_to_cook> <quantity>" +
            " - добавляет блюдо в меню, доступно только администратору";
    @Override
    public boolean isAllowedInEnv(Environment env) {
        return env.role == UserRole.ADMIN;
    }

    @Override
    protected void performCommand(Environment env, String[] args) {

        // Проверка корректности команды.
        if(args.length != 5) {
            Logger.log("Неверная команда.");
            return;
        }
        if (args[1].length() > 100) {
            Logger.log("Название блюда должно быть короче 100 символов.");
            return;
        }
        try {
            if (doesDishExist(args[1])) {
                Logger.log("Название не может совпадать с существующим блюдом.");
                return;
            }
        }catch (SQLException e) {
            Logger.log(e.getMessage());
            Logger.log("Ошибка доступа к базе данных");
            return;
        }

        // Проверка введенных значений
        double cost;
        int timeSec, quantity;
        try {
            cost = Double.parseDouble(args[2]);
            timeSec = parseInt(args[3]);
            quantity = parseInt(args[4]);
        } catch(NumberFormatException e) {
            Logger.log(e.getMessage());
            Logger.log("Неверный формат чисел.");
            return;
        }

        if (cost < 0 || timeSec < 0 || quantity < 0) {
            Logger.log("Числовые значения должны быть больше 0.");
            return;
        }

        // Пытаемся вставить в бд.
        var dish = new DishDTO(args[1], BigDecimal.valueOf(cost), timeSec, quantity);
        try {
            insertDishIntoMenu(dish);
        } catch (SQLException e) {
            Logger.log(e.getMessage());
            Logger.log("Ошибка при вставке нового блюда.");
        }
        Logger.log("Блюдо внесено в меню.");
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
