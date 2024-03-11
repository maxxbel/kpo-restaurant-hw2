package ru.hse.edu.menu;

import ru.hse.edu.consoleui.Logger;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

import static ru.hse.edu.menu.MenuRepository.editQuantityByName;
import static ru.hse.edu.menu.MenuRepository.getDishByName;

public class Order implements Runnable {
    private String orderName;
    private String userName;
    private OrderStatus status;
    private ConcurrentLinkedQueue<DishDTO> dishes = new ConcurrentLinkedQueue<DishDTO>();
    public Order(String userName, String orderName) {
        status = OrderStatus.NOT_SUBMITTED;
        this.userName = userName;
        this.orderName = orderName;
    }
    public void addDish(DishDTO dish) {
        dishes.add(dish);
    }
    public void removeDishByName(String dishName) throws SQLException {
        if (status != OrderStatus.NOT_SUBMITTED) {
            return;
        }
        for (var dish : dishes) {
            if (dish.dishName().equals(dishName)) {
                var dbDish = getDishByName(dish.dishName());
                if (dbDish.isEmpty()) {
                    dishes.remove(dish);
                    return;
                }
                int newQuantity = dbDish.get().quantity() + dish.quantity();
                editQuantityByName(dish.dishName(), newQuantity);
                dishes.remove(dish);
                return;
            }
        }

    }
    public void cancel() throws SQLException {
        if (status == OrderStatus.CANCELLED ||
        status == OrderStatus.DONE ||
        status == OrderStatus.PAID) {
            return;
        }
        status = OrderStatus.CANCELLED;
        for (var dish : dishes) {
            var dbDish = getDishByName(dish.dishName());
            if (dbDish.isEmpty()) {
                return;
            }
            int newQuantity = dbDish.get().quantity() + dish.quantity();
            editQuantityByName(dish.dishName(), newQuantity);
        }
    }
    public void submit() {
        status = OrderStatus.SUBMITTED;
        Kitchen.service.submit(this);
    }
    public String getUsername() {
        return userName;
    }
    public String getOrderName() {
        return orderName;
    }
    public OrderStatus getStatus() {
        return status;
    }

    @Override
    public void run() {
        for (var dish : dishes) {
            int waitTimeMs = dish.secondsToMake() *
                    dish.quantity() * 1000;
            try {
                Thread.sleep(waitTimeMs);
            } catch (InterruptedException e) {
                Logger.log("Проблемы на кухне.");
                return;
            }
            if (status == OrderStatus.CANCELLED) {
                return;
            }
        }
        status = OrderStatus.DONE;
    }

    @Override
    public String toString() {
        var sb =new StringBuilder(userName);
        sb.append("\t").append(orderName)
                .append("\t")
                .append(status.toString())
                .append("\n");
        sb.append("Блюда в заказе:\n");
        for(var dish : dishes) {
            sb.append("\t").append(dish.toString()).append("\n");
        }
        return sb.toString();
    }

    public BigDecimal getSum() {
        BigDecimal sum = BigDecimal.ZERO;
        for (var dish : dishes) {
            sum = sum.add(dish.cost().multiply(BigDecimal.valueOf(dish.quantity())));
        }
        return sum;
    }
}
