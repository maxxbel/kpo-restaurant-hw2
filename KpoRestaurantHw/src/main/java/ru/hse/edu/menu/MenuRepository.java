package ru.hse.edu.menu;

import ru.hse.edu.db.DataBaseManager;

import java.sql.SQLException;
import java.util.Optional;

public class MenuRepository {
    private static final DataBaseManager manager = new DataBaseManager();
    public static String getMenu() throws SQLException {
        String getMenuSql = "select * from menu";
        var sb = new StringBuilder("Название\tЦена\tВремя на готовку (сек.)\tКоличество\n");
        try(var result = manager.performExecuteQuery(getMenuSql)) {
            while(result.next()) {
                sb.append(String.format("%s\t%f\t%d\t%d\n", result.getString("dish_name"),
                        result.getBigDecimal("cost"), result.getInt("seconds_to_make"),
                        result.getInt("quantity")));
            }
        }
        return sb.toString();
    }
    public static void insertDishIntoMenu(DishDTO dish) throws SQLException {
        var insertDishSql = "insert into menu (dish_name, cost, seconds_to_make, quantity)" +
                "values ('" + dish.dishName() + "'," + dish.cost() + "," + dish.secondsToMake() +"," +
                dish.quantity() + ");";
        manager.performExecuteUpdate(insertDishSql);
    }
    public static void deleteDishByName(String name) throws SQLException {
        var deleteDishSql = "delete from menu where dish_name ='" + name + "'";
        manager.performExecuteUpdate(deleteDishSql);
    }
    public static void editQuantityByName(String name, int new_quantity) throws SQLException {
        var editQuantitySql = "update menu set quantity = " + new_quantity +" where dish_name ='" +
                name + "'";
        manager.performExecuteUpdate(editQuantitySql);
    }
    public static Optional<DishDTO> getDishByName(String name) throws SQLException {
        String getDishSql = "select * from menu where dish_name ='" + name + "'";
        try(var result = manager.performExecuteQuery(getDishSql)) {
            if(result.next()) {
                return Optional.of(new DishDTO(result.getString("dish_name"),
                        result.getBigDecimal("cost"),
                        result.getInt("seconds_to_make"),
                        result.getInt("quantity")));
            }
            return Optional.empty();
        }
    }
    public static Boolean doesDishExist(String dishName) throws SQLException {
        String dishExistRequest = "select 1 from menu where dish_name='" + dishName + "'";
        try(var result = manager.performExecuteQuery(dishExistRequest)) {
            if(!result.next()) {
                return false;
            }
        }
        return true;
    }
}
