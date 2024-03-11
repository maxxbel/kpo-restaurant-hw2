package ru.hse.edu.consoleui.commands;

import ru.hse.edu.consoleui.Environment;
import ru.hse.edu.consoleui.Logger;
import ru.hse.edu.menu.OrderRepository;
import ru.hse.edu.users.UserRole;

import java.sql.SQLException;

import static ru.hse.edu.menu.OrderRepository.getTotalRevenue;

public class CommandViewRevenue extends ConsoleCommand {
    private static final String NAME = "view-money";
    private static final String DESCRIPTION = "view-money - посмотреть сумму оплаченных заказов.";
    private static final String LONG_DESCRIPTION = "view-money - посмотреть сумму выручки ресторана.";
    @Override
    public boolean isAllowedInEnv(Environment env) {
        return env.role == UserRole.ADMIN;
    }

    @Override
    protected void performCommand(Environment env, String[] args) {
        try {
            Logger.log("Ваша выручка:\t" + getTotalRevenue());
        } catch (SQLException e) {
            Logger.log(e.getMessage());
            Logger.log("Ошибка доступа к бд.");
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
