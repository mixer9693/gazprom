package ru.gazprom.test;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.gazprom.test.service.Command;

public class Application {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext ctx =
                new AnnotationConfigApplicationContext("ru.gazprom.test");

        try (ctx) {
            Command executor = ctx.getBean(Command.class);
            executor.execute(args);
        }
    }

}
