package com.mode.service.impl;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mode.base.AppConfig;
import com.mode.dao.source.UserDao;
import com.mode.dao.target.CalendarDao;
import com.mode.dao.target.StatsDailyDao;
import com.mode.entity.Calendar;
import com.mode.entity.StatsDaily;
import com.mode.service.BiDailyProcessingService;

/**
 * Created by zhaoweiwei on 16/6/21.
 */
@Service
public class BiDailyProcessingServiceImpl implements BiDailyProcessingService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private StatsDailyDao statsDailyDao;

    @Autowired
    private CalendarDao calendarDao;

    private final String STATS_DAILY_NEW_REG_COL_NAME = "new_reg";

    private final String STATS_DAILY_NEW_REG_FB_COL_NAME = "new_reg_fb";

    private final String STATS_DAILY_NEW_REG_YT_COL_NAME = "new_reg_yt";

    private final String STATS_DAILY_TOTAL_REG_COL_NAME = "total_reg";

    private final String STATS_DAILY_ACTIVES_COL_NAME = "actives";

    private final String STATS_DAILY_ORDERS_COL_NAME = "orders";

    private final String STATS_DAILY_TOTAL_ORDERS_COL_NAME = "total_orders";

    private final String STATS_DAILY_GMV_COL_NAME = "gmv";

    private final String STATS_DAILY_TOTAL_GMV_COL_NAME = "total_gmv";

    @Override
    public void process() {
        long now = System.currentTimeMillis();
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
        df.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        // Process endDate is yesterday
        Integer endDate = Integer.parseInt(df.format(now - 24 * 3600 * 1000));

        Integer lastestDate = statsDailyDao.getLastestDate(null);
        if (lastestDate == null) {
            lastestDate = AppConfig.BEGIN_PROCESS_DATE;
        }
        // Get all dates from lastestDate to endDate
        List<Calendar> calendars = calendarDao.listCalendars(lastestDate, endDate);
        for (Calendar calendar : calendars) {
            StatsDaily statsDaily = new StatsDaily();
            statsDaily.setDate(calendar.getDate());
            statsDailyDao.createStatsDaily(statsDaily);

        }

    }

    private void processUserRegistration(Integer endDate) {
        List<Integer> dates = statsDailyDao.getToBeProcessedDate(STATS_DAILY_NEW_REG_COL_NAME,
                endDate);


    }

//    public static void main(String[] args) {
//        BiDailyProcessingServiceImpl biDailyProcessingService = new BiDailyProcessingServiceImpl();
//        biDailyProcessingService.process();
//    }
}