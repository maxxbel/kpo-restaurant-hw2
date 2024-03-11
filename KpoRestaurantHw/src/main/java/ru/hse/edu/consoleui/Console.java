package ru.hse.edu.consoleui;

import java.util.Scanner;

public class Console {
    private final String GREETING = "Добро пожаловать в программу! Для большинства функций требуется авторизация." +
            "\nЧтобы увидеть список доступных для вас комманд введите help.";
    private final String USER_ERROR = "Не распознана команда.";
    private final String PROG_ERROR = "Произошла ошибка при работе программы. Изменения не сохранены." +
            "\nВойдите заново.";
    private Scanner scanner;
    private Environment env;

    public Console(Scanner scanner) {
        this.scanner = scanner;
        env = new Environment();
    }
    public void listen() {
        log(GREETING);
        try {
            while (!env.isClosed) {
                String input = scanner.nextLine();
                parseCommand(input);
            }
        }catch (Exception e) {
            log(PROG_ERROR);
        }
    }
    private void parseCommand(String input) {
        String[] args = input.split(" ");
        if(args.length < 1) {
            log(USER_ERROR);
        }
        boolean isCommandFound = false;
        for (var comm : CommandsHolder.getInstance().getCommands()) {
            if(comm.getName().equals(args[0])) {
                comm.execute(env, args);
                isCommandFound = true;
            }
        }
        if(!isCommandFound) {
            Logger.log(USER_ERROR);
        }
    }

    private void reg() {
        log("1");
    }

    private void logIn() {
        log("2");
    }

    private void help() {

    }

    private void log(String string) {
        System.out.println(string);
    }

}
