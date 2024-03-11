package ru.hse.edu.consoleui.commands;

import jdk.jshell.spi.ExecutionControl;
import ru.hse.edu.consoleui.Environment;
import ru.hse.edu.consoleui.Logger;
import ru.hse.edu.menu.DishDTO;
import ru.hse.edu.menu.OrderStatus;
import ru.hse.edu.users.UserRole;

import java.sql.SQLException;

import static ru.hse.edu.menu.MenuRepository.editQuantityByName;
import static ru.hse.edu.menu.MenuRepository.getDishByName;
import static ru.hse.edu.menu.OrderRepository.getOrderByName;

public class CommandAddDishToOrder extends ConsoleCommand{
    private static final String NAME = "add-dish";
    private static final String DESCRIPTION = "add-dish <dish_name> <dish_count> <order_name>" +
            " - введите название и количество блюд для добавления в заказ.";
    private static final String LONG_DESCRIPTION = "add-dish <dish_name> <dish_count> <order_name>" +
            " - введите название и количество блюд для добавления в заказ. Для этого заказ нужно создать.";
    @Override
    public boolean isAllowedInEnv(Environment env) {
        return env.role == UserRole.CUSTOMER;
    }

    @Override
    protected void performCommand(Environment env, String[] args) {
        if(args.length != 4) {
            Logger.log("Неверная команда");
            return;
        }
        int quantity;
        try {
            quantity = Integer.parseInt(args[2]);
        } catch (NumberFormatException e) {
            Logger.log("Количество должно быть целым числом.");
            return;
        }
        if (quantity < 0) {
            Logger.log("Количество должно быть положительным числом.");
            return;
        }
        var optOrder = getOrderByName(env.username, args[3]);
        if (optOrder.isEmpty()) {
            Logger.log("Нет такого заказа, сначала создайте заказ.");
            return;
        }
        if (optOrder.get().getStatus() == OrderStatus.DONE ||
                optOrder.get().getStatus() == OrderStatus.PAID ||
                optOrder.get().getStatus() == OrderStatus.CANCELLED) {
            Logger.log("В этот заказ уже нельзя добавить блюдо.");
            return;
        }
        try {
            var optDish = getDishByName(args[1]);
            if (optDish.isEmpty()) {
                Logger.log("Нет такого блюда.");
                return;
            }
            var dish = optDish.get();
            if (dish.quantity() < quantity) {
                Logger.log("Нет столько блюд.");
                return;
            }
            int newQuantity = dish.quantity() - quantity;
            editQuantityByName(dish.dishName(), newQuantity);
            optOrder.get().addDish(new DishDTO(dish.dishName(), dish.cost(),
                    dish.secondsToMake(), quantity));
        } catch (SQLException e) {
            Logger.log(e.getMessage());
            Logger.log("Ошибка подключения к базе данных.");
            return;
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
