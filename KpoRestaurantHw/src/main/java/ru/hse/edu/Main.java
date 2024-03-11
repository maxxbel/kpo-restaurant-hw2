package ru.hse.edu;

import ru.hse.edu.consoleui.Console;
import ru.hse.edu.consoleui.Logger;
import ru.hse.edu.db.DataBaseManager;

import java.sql.SQLException;
import java.util.Scanner;

import static ru.hse.edu.users.LogInValidator.createHashString;


public class Main {
    public static final String CREATE_ACC_TABLE = """
                create table if not exists account (
                            id serial primary key,
                            login varchar(256),
                            hash varchar(256),
                            role varchar(256));
                """;
    public static final String CREATE_MENU_TABLE = """
                create table if not exists menu (
                            id serial primary key,
                            dish_name varchar(256),
                            cost numeric,
                            seconds_to_make integer,
                            quantity integer);
                            """;
    public static final String CREATE_ORDERS_TABLE = """
                create table if not exists orders (
                            id serial primary key,
                            user_name varchar(256),
                            revenue numeric);
                """;
    public static final String ADD_TEST_DISH = """
                insert into menu (dish_name, cost, seconds_to_make, quantity)
                values ('test_dish', 100, 12, 50),
                ('tea', 60, 6, 40);
                """;
    public static final String ADD_ADMIN = "insert into account (login, hash, role)" +
            "values ('admin', '" + createHashString("1111") + "', 'admin')";
    public static void main(String[] args) {

        Console console = new Console(new Scanner(System.in));
        var manager = new DataBaseManager();
        try {
            //initialize(manager);
        }catch (Exception e) {
            Logger.log(e.getMessage());
        }
        console.listen();
    }

    private static void initialize(DataBaseManager manager) throws SQLException {
        manager.performExecute(CREATE_ACC_TABLE);
        manager.performExecute(CREATE_MENU_TABLE);
        manager.performExecute(CREATE_ORDERS_TABLE);
        manager.performExecuteUpdate(ADD_TEST_DISH);
        manager.performExecuteUpdate(ADD_ADMIN);
    }
}