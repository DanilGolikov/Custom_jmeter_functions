package com.xmeter.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.concurrent.ThreadLocalRandom;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class customFunctionUtils {
    public static String alignmentStr(String str, int len) {
        StringBuilder result = new StringBuilder(str);
        while (result.length() != len) result.insert(0, "0");
        return result.toString();
    }

    public static Long convertToTimestamp(String dateTimeString) {
        try {
            // Try parsing as LocalDateTime
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            LocalDateTime localDateTime = LocalDateTime.parse(dateTimeString, dateTimeFormatter);
            return localDateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
        } catch (DateTimeParseException e) {
            // If parsing as LocalDateTime fails, try parsing as LocalDate
            LocalDate localDate = LocalDate.parse(dateTimeString);
            LocalDateTime localDateTime = localDate.atStartOfDay();
            return localDateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
        }
    }

    public static Long convertRelativeTime(String relativeTime) {
        if (relativeTime.equals("now")) return new Date().getTime();

        Pattern pattern = Pattern.compile("now([+-])(\\d+)([smhd])");
        Matcher matcher = pattern.matcher(relativeTime);
        if (!matcher.matches())
            return null;

        String sign = matcher.group(1);
        int amount = Integer.parseInt(matcher.group(2));
        String unit = matcher.group(3);
        LocalDateTime now = LocalDateTime.now();
        switch (unit) {
            case "s":
                now = sign.equals("+") ? now.plusSeconds(amount) : now.minusSeconds(amount);
                break;
            case "m":
                now = sign.equals("+") ? now.plusMinutes(amount) : now.minusMinutes(amount);
                break;
            case "h":
                now = sign.equals("+") ? now.plusHours(amount) : now.minusHours(amount);
                break;
            case "d":
                now = sign.equals("+") ? now.plusDays(amount) : now.minusDays(amount);
                break;
            case "y":
                now = sign.equals("+") ? now.plusYears(amount) : now.minusYears(amount);
                break;
            default:
                throw new IllegalArgumentException("Invalid time unit");
        }
        return now.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }

    public static int randomFunc(int min, int max) {
        return ThreadLocalRandom.current().nextInt(min, max+1);
    }

    public static long randomFunc(long min, long max) {
        return ThreadLocalRandom.current().nextLong(min, max+1);
    }
}
