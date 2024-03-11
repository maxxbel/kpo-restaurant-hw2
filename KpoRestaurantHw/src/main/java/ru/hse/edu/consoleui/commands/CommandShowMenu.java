package ru.hse.edu.consoleui.commands;

import ru.hse.edu.consoleui.Environment;
import ru.hse.edu.consoleui.Logger;

import static ru.hse.edu.menu.MenuRepository.getMenu;

public class CommandShowMenu extends ConsoleCommand {
    private static final String NAME = "show-menu";
    private static final String DESCRIPTION = "show-menu - выводит меню ресторана.";
    private static final String LONG_DESCRIPTION = "show-menu - позволяет просмотреть текущее меню ресторана." +
            "Для заказа войдите как пользователь. Для редактирования меню войдите как администратор.";
    @Override
    public boolean isAllowedInEnv(Environment env) {
        return true;
    }

    @Override
    protected void performCommand(Environment env, String[] args) {
        try {
            Logger.log(getMenu());
        } catch (Exception e) {
            Logger.log(e.getMessage());
            Logger.log("Ошибка получения меню из базы данных, проверьте настройки.");
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
