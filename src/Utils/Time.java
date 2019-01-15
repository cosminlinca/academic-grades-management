package Utils;

import net.bytebuddy.asm.Advice;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.time.temporal.WeekFields;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.time.temporal.ChronoUnit.*;

public class Time
{
    public static final int startWeek = 39;

    /**
     * @return the current week number
     */
    public static int getCurrentWeekNumber() {

        Calendar calendar = new GregorianCalendar();
        Date trialTime = new Date();
        calendar.setTime(trialTime);
        return calendar.get(Calendar.WEEK_OF_YEAR);
    }
    public static int getCurrentUniversityWeek() {
        LocalDate startDate = LocalDate.parse("2018-09-30");
        long days = ChronoUnit.DAYS.between(startDate, LocalDate.now());
        double diff = Math.ceil((double)days/7);
        return (int)diff ;
    }
    public static int getUniversityWeek(LocalDate localDate) {
        LocalDate startDate = LocalDate.parse("2018-09-30");
        long days = ChronoUnit.DAYS.between(startDate, localDate);
        double diff = Math.ceil((double)days/7);
        return (int)diff ;
    }

    /**
     * @param localDateTime
     * @return the week number for a specific date
     */
    public static int getWeekNumber(LocalDate localDateTime) {

        return localDateTime.get(WeekFields.ISO.weekOfYear());
    }
}
