package io.isiflix.isispring.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class IsiLogger {

    private IsiLogger() {
        throw new IllegalStateException("Utility class");
    }

    public static final String GREEN = "\u001B[32m";
    public static final String YELLOW = "\u001B[33m";
    public static final String WHITE = "\u001B[37m";
    public static final String RESET = "\u001B[0m";
    public static final DateTimeFormatter CUSTOM_DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static void log(String module, String message){
        String now = LocalDateTime.now().format(CUSTOM_DATE_TIME_FORMATTER);
        System.out.printf(GREEN + "%15s " + YELLOW + "%-30s" + WHITE + "%s\n" + RESET, now, module, message);
    }

    public static void showBanner(){
        String banner = """
                ____       ____     _ _____            _                ____ \s
                \\ \\ \\     /  _/____(_) ___/____  _____(_)___  ____ _    \\ \\ \\\s
                 \\ \\ \\    / // ___/ /\\__ \\/ __ \\/ ___/ / __ \\/ __ `/     \\ \\ \\
                 / / /  _/ /(__  ) /___/ / /_/ / /  / / / / / /_/ /      / / /
                /_/_/  /___/____/_//____/ .___/_/  /_/_/ /_/\\__, /      /_/_/\s
                                       /_/                 /____/            \s
                """;
        System.out.println(YELLOW);
        System.out.println(banner);
        System.out.println(RESET);
    }

}
