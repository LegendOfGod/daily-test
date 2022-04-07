package concurrent;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

/**
 * @author lqb
 * @date 2022/3/31 16:12
 */
public class DateUtil {

    public static void main(String[] args) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date sDate = dateFormat.parse("2022-03-26");
        Date eDate = dateFormat.parse("2022-03-31");
        System.out.println(DateUtil.getWorkdayTimeInDate(sDate, eDate));
    }


    /**
     * 获取两个时间之内的工作日时间（只去掉两个日期之间的周末时间，法定节假日未去掉） 时间精确到日
     *
     * @param startDate
     *            -起始时间
     * @param endDate
     *            -结束时间
     * @return Long型时间差对象
     */
    public static int getWorkdayTimeInDate(Date startDate, Date endDate) {
        long start = startDate.getTime();
        long end = endDate.getTime();
        // 如果起始时间大于结束时间，将二者交换
        if (start > end) {
            long temp = start;
            start = end;
            end = temp;
        }
        // 根据参数获取起始时间与结束时间的日历类型对象
        Calendar sdate = Calendar.getInstance();
        Calendar edate = Calendar.getInstance();
        sdate.setTimeInMillis(start);
        edate.setTimeInMillis(end);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");


        try {
            sdate.setTime(sdf.parse(sdf.format(sdate.getTime())));
            edate.setTime(sdf.parse(sdf.format(edate.getTime())));
        } catch (ParseException e) {
            e.printStackTrace();
        }


        // 如果两个时间在同一周并且都不是周末日期，则直接返回时间差，增加执行效率
        if (sdate.get(Calendar.YEAR) == edate.get(Calendar.YEAR)
                && sdate.get(Calendar.WEEK_OF_YEAR) == edate.get(Calendar.WEEK_OF_YEAR)
                && sdate.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY
                && sdate.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY
                && edate.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY
                && edate.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY) {
            long between_days = (end - start) / (1000 * 3600 * 24);
            return Integer.parseInt(String.valueOf(between_days));
        }


        // 防止开始日期在周五，结束日期在周日情况下，计算间隔天数不准确问题，开始或结束日期为周六日时，
        // 开始日期周六日，设置为下周一。
        if (sdate.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
            sdate.add(Calendar.DAY_OF_MONTH, 1);
        }
        if (sdate.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
            // 开始日期在周六日时，设置日期为周一
            sdate.add(Calendar.DAY_OF_MONTH, 2);
        }
        start = sdate.getTimeInMillis();
        // 结束日期为周六日时，设置为周五
        if (edate.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
            edate.add(Calendar.DAY_OF_MONTH, -2);
        }
        if (edate.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
            edate.add(Calendar.DAY_OF_MONTH, -1);
        }
        end = edate.getTimeInMillis();


        // 首先取得起始日期与结束日期的下个周一的日期
        Calendar snextM = getNextMonday(sdate);
        Calendar enextM = getNextMonday(edate);
        // 获取这两个周一之间的实际天数
        int days = 0;
        try {
            days = daysBetween(snextM.getTime(), enextM.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        // 获取这两个周一之间的工作日数(两个周一之间的天数肯定能被7整除，并且工作日数量占其中的5/7)
        int workdays = days / 7 * 5;
        // 获取开始时间的偏移量
        long scharge = 0;
        sdate.setTimeInMillis(start);
        edate.setTimeInMillis(end);
        if (sdate.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY
                && sdate.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY) {
            // 只有在开始时间为非周末的时候才计算偏移量
            scharge += (7 - sdate.get(Calendar.DAY_OF_WEEK)) * 24 * 3600000;
            scharge -= sdate.get(Calendar.HOUR_OF_DAY) * 3600000;
            scharge -= sdate.get(Calendar.MINUTE) * 60000;
            scharge -= sdate.get(Calendar.SECOND) * 1000;
            scharge -= sdate.get(Calendar.MILLISECOND);
        }
        // 获取结束时间的偏移量
        long echarge = 0;
        if (edate.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY
                && edate.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY) {
            // 只有在结束时间为非周末的时候才计算偏移量
            echarge += (7 - edate.get(Calendar.DAY_OF_WEEK)) * 24 * 3600000;
            echarge -= edate.get(Calendar.HOUR_OF_DAY) * 3600000;
            echarge -= edate.get(Calendar.MINUTE) * 60000;
            echarge -= edate.get(Calendar.SECOND) * 1000;
            echarge -= edate.get(Calendar.MILLISECOND);
        }
        // 计算最终结果，具体为：workdays加上开始时间的时间偏移量，减去结束时间的时间偏移量
        long between_days = workdays + (scharge - echarge) / (1000 * 3600 * 24);
        return Integer.parseInt(String.valueOf(between_days));
    }


    /**
     * 获取下周一
     *
     * @param calendar
     *            指定日期
     * @return
     */
    private static Calendar getNextMonday(Calendar calendar) {
        int addnum = 9 - calendar.get(Calendar.DAY_OF_WEEK);
        if (addnum == 8) {
            addnum = 1;// 周日的情况
        }
        calendar.add(Calendar.DATE, addnum);
        return calendar;
    }


    /**
     *
     * 计算两个日期之间相差的天数
     *
     * @param smdate
     *            较小的时间
     *
     * @param bdate
     *            较大的时间
     *
     * @return 相差天数
     *
     * @throws ParseException
     *
     */
    public static int daysBetween(Date smdate, Date bdate) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        smdate = sdf.parse(sdf.format(smdate));
        bdate = sdf.parse(sdf.format(bdate));
        Calendar cal = Calendar.getInstance();
        cal.setTime(smdate);


        long time1 = cal.getTimeInMillis();
        cal.setTime(bdate);
        long time2 = cal.getTimeInMillis();


        long between_days = (time2 - time1) / (1000 * 3600 * 24);
        return Integer.parseInt(String.valueOf(between_days));
    }
}
