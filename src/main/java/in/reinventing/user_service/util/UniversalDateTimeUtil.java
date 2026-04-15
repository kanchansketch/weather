package in.reinventing.user_service.util;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;

public final class UniversalDateTimeUtil {
    public static final String YYYY_MM_DD_HH_MM = "yyyy-MM-dd HH:mm";
    public static final String YYYY_MM_DD = "yyyy-MM-dd";

    private UniversalDateTimeUtil(){}

    public static LocalDateTime startOfDate(String timezone){
        ZoneId zoneId = ZoneId.of(timezone);
        LocalDate localDateTime = LocalDate.now(zoneId);
        return localDateTime.atStartOfDay();
    }

    public static LocalDateTime startOfDate(String timezone,int day){
        ZoneId zoneId = ZoneId.of(timezone);
        LocalDate localDateTime = LocalDate.now(zoneId);
        localDateTime = localDateTime.plus(day+1, ChronoUnit.DAYS);
        return localDateTime.atStartOfDay();
    }

    public static LocalDateTime endOfDate(String timezone){
        ZoneId zoneId = ZoneId.of(timezone);
        LocalDate localDateTime = LocalDate.now(zoneId);
        return localDateTime.atTime(LocalTime.MAX);
    }

    public static LocalDateTime convertDateTime(String dateTime, String format){
        return LocalDateTime.parse(dateTime,
                DateTimeFormatter.ofPattern(format));
    }

    public static LocalDateTime convertDate(String dateTime,String format){

        LocalDate localDate =
                LocalDate.parse(
                        dateTime,
                        DateTimeFormatter.ofPattern(format)
                );

        return localDate.atStartOfDay();
    }
}