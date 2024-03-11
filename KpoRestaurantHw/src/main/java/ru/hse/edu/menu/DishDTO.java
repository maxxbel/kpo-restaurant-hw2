package ru.hse.edu.menu;

import java.math.BigDecimal;

public record DishDTO(String dishName, BigDecimal cost, int secondsToMake, int quantity) {

}
