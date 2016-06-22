package com.mode.dao.target;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import com.mode.entity.Calendar;

/**
 * Created by zhaoweiwei on 16/6/21.
 */
public interface CalendarDao {

    /**
     * List calendars.
     *
     * @param startDate
     * @param endDate
     * @return
     */
    @Select({
            "<script>",
            "SELECT * FROM md_calendar ",
            "<where>",
            "<if test='startDate != null'> <![CDATA[ AND date > #{startDate} ]]> </if>",
            "<if test='endDate != null'> <![CDATA[ AND date < #{endDate} ]]> </if>",
            "</where>",
            "</script>"
    })
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "date", column = "date"),
            @Result(property = "weekend", column = "weekend"),
            @Result(property = "month", column = "month"),
            @Result(property = "quarter", column = "quarter"),
            @Result(property = "year", column = "year"),
            @Result(property = "startTs", column = "start_ts"),
            @Result(property = "endTs", column = "end_ts")})
    public List<Calendar> listCalendars(@Param("startDate") Integer startDate,
                                        @Param("endDate") Integer endDate);

    @Select({
            "<script>",
            "SELECT * FROM md_calendar ",
            "where date = #{date}",
            "</script>"
    })
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "date", column = "date"),
            @Result(property = "weekend", column = "weekend"),
            @Result(property = "month", column = "month"),
            @Result(property = "quarter", column = "quarter"),
            @Result(property = "year", column = "year"),
            @Result(property = "startTs", column = "start_ts"),
            @Result(property = "endTs", column = "end_ts")})
    public Calendar getCalendar(Integer date);

    @Select({
            "<script>",
            "select date  from md_calendar where weekend = (select weekend from md_calendar where" +
                    " date = #{startDate}) limit 1",
            "</script>"
    })
    public Integer getWeekFirstDay(@Param("startDate") Integer startDate);

    @Select({
            "<script>",
            "select date  from md_calendar where weekend = (select weekend from md_calendar where" +
                    " date = #{endDate}) order by id desc limit 1",
            "</script>"
    })
    public Integer getWeekLastDay(@Param("endDate") Integer endDate);

    @Select({
            "<script>",
            "select date  from md_calendar where month = (select month from md_calendar where" +
                    " date = #{startDate}) limit 1",
            "</script>"
    })
    public Integer getMonthFirstDay(@Param("startDate") Integer startDate);

    @Select({
            "<script>",
            "select date  from md_calendar where weekend = (select month from md_calendar where" +
                    " date = #{endDate}) order by id desc limit 1",
            "</script>"
    })
    public Integer getMonthLastDay(@Param("endDate") Integer endDate);

}
