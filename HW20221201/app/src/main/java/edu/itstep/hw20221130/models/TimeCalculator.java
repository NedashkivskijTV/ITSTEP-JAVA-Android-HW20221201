package edu.itstep.hw20221130.models;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class TimeCalculator {

    // Метод вираховує дату, шляхом додавання інтервалу в мілісекундах до відомої дати
    public static String getDatePlusInterval(String dateStart, String timeStart, int interval, String patternDate) {

        String[] dateToArr = dateStart.split("\\.");

        String[] departureTimeToArr = timeStart.split(":");

        Date dateTimeSelected = new GregorianCalendar(
                Integer.parseInt(dateToArr[2]),
                Integer.parseInt(dateToArr[1]) - 1,
                Integer.parseInt(dateToArr[0]),
                Integer.parseInt(departureTimeToArr[0]),
                Integer.parseInt(departureTimeToArr[1])
        ).getTime();

        Calendar newDate = Calendar.getInstance();
        newDate.setTime(dateTimeSelected);
        newDate.add(Calendar.MILLISECOND, interval);

        Date createdAt = newDate.getTime();
        String newDateStr = new SimpleDateFormat(patternDate).format(createdAt);

        return newDateStr;
    }

    // Метод перетворює часовий інтервал в мілісекундах - long у години/хвилини
    public static int longToHoursOrMinutes(long interval, boolean isHourReturn) {
        int hour, min;

        long timeInMin = interval / (1000 * 60);

        if (isHourReturn) {
            hour = (int) timeInMin / 60;
            return hour;
        }
        min = (int) timeInMin % 60;
        return min;
    }

}
