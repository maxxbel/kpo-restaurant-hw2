package ru.hse.edu.menu;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Kitchen {
    public static ExecutorService service = Executors.newFixedThreadPool(2);

}
