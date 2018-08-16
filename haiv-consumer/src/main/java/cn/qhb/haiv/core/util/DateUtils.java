package cn.qhb.haiv.core.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * 日期工具
 */
public class DateUtils {

    /**
     * 获取具体到秒的时间字符串
     *
     * @return
     */
    public static String currentDateSecondStr() {
        DateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
        Date date = new Date();
        return format.format(date);
    }

    /**
     * 获取具体到天的时间字符串
     *
     * @return
     */
    public static String currentDateDayStr() {
        DateFormat format = new SimpleDateFormat("yyyyMMdd");
        Date date = new Date();
        return format.format(date);
    }

    /**
     * 获取具体到月的时间字符串
     *
     * @return
     */
    public static String currentDateMonthStr() {
        DateFormat format = new SimpleDateFormat("yyyyMM");
        Date date = new Date();
        return format.format(date);
    }

    /**
     * 生成适应于linux的日期格式
     *
     * @param source
     * @return
     * @throws Exception
     */
    public static Date formatStringToDateLinux(String source) throws Exception {
        Date dt = new Date();
        String[] parts = source.split(" ");
        if (parts.length == 2) {
            String[] partsDate = parts[0].split("-");
            String[] partsTime = parts[1].split(":");
            if (partsDate.length == 3 && partsTime.length == 3) {
                int years = Integer.parseInt(partsDate[0]);
                int months = Integer.parseInt(partsDate[1]);
                int days = Integer.parseInt(partsDate[2]);
                int hours = Integer.parseInt(partsTime[0]);
                int minutes = Integer.parseInt(partsTime[1]);
                int seconds = Integer.parseInt(partsTime[2]);
                GregorianCalendar gc = new GregorianCalendar(years, months,
                        days, hours, minutes, seconds);
                dt = gc.getTime();
            }
        }
        return dt;
    }

    public static void main(String... args) {
        System.out.println(currentDateDayStr() + " " + currentDateMonthStr() + " " + currentDateSecondStr());
    }
}
