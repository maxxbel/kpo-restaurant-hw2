package ru.hse.edu.menu;

import ru.hse.edu.db.DataBaseManager;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class OrderRepository {
    private static DataBaseManager manager = new DataBaseManager();
    private static final List<Order> orders = new ArrayList<>();
    public static void addOrder(Order order) {
        orders.add(order);
    }
    public static Optional<Order> getOrderByName(String userName, String name) {
        for(var order : orders) {
            if(order.getUsername().equals(userName) && order.getOrderName().equals(name)) {
                return Optional.of(order);
            }
        }
        return Optional.empty();
    }

    public static void cancelAllOrders() throws SQLException {
        for (var order : orders) {
            order.cancel();
        }
    }
    public static String getOrdersInString(String userName) {
        var sb = new StringBuilder();
        for(var order : orders) {
            if (order.getUsername().equals(userName)) {
                sb.append(order.toString()).append("\n");
            }
        }
        return sb.toString();
    }

    public static void payForOrder(Order order) throws SQLException {
        var payedSql = "insert into orders (user_name, revenue) values" +
                "('" + order.getUsername() + "'," + order.getSum() + ");";
        manager.performExecuteUpdate(payedSql);
    }

    public static BigDecimal getTotalRevenue() throws SQLException {
        var selectAll = "select * from orders";
        var sum = BigDecimal.ZERO;
        try(var result = manager.performExecuteQuery(selectAll)) {
            while (result.next()) {
                sum = sum.add(result.getBigDecimal("revenue"));
            }
        }
        return sum;
    }
}
