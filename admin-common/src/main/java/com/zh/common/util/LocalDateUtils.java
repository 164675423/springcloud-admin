package com.zh.common.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class LocalDateUtils {
  public static final DateTimeFormatter YYYY_MM_DD = DateTimeFormatter.ISO_LOCAL_DATE;

  /**
   * 格式化时间
   *
   * @param localDateTime
   * @param format
   * @return
   */
  public static String format(LocalDateTime localDateTime, String format) {
    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(format);
    return localDateTime.format(dateTimeFormatter);
  }

  /**
   * 默认格式yyyy-MM-dd
   *
   * @param localDateTime
   * @return
   */
  public static String format(LocalDateTime localDateTime) {
    return localDateTime.format(YYYY_MM_DD);
  }

  /**
   * @param localDateTime
   * @param formatter
   * @return
   */
  public static String format(LocalDateTime localDateTime, DateTimeFormatter formatter) {
    return localDateTime.format(formatter);
  }

  /**
   * 字符串转换为LocalDate
   *
   * @param localDate
   * @param formatter
   * @return
   */
  public static LocalDate parse(String localDate, DateTimeFormatter formatter) {
    LocalDate parse = LocalDate.parse(localDate, formatter);
    return parse;
  }

  /**
   * 字符串转换为localDataTime
   *
   * @param localDateTime
   * @param formatter
   * @return
   */
  public static LocalDateTime toLocalDateTime(String localDateTime, DateTimeFormatter formatter) {
    LocalDateTime parse = LocalDateTime.parse(localDateTime, formatter);
    return parse;
  }

  /**
   * 获取时间差(天)
   *
   * @param startLocalDateTime
   * @param endLocalDateTime
   * @return
   */
  public static long between(LocalDateTime startLocalDateTime, LocalDateTime endLocalDateTime) {
    LocalDate startDate = startLocalDateTime.toLocalDate();
    LocalDate endDate = endLocalDateTime.toLocalDate();
    return between(startDate, endDate, ChronoUnit.DAYS);
  }

  /**
   * 获取时间差
   *
   * @param startLocalDateTime
   * @param endLocalDateTime
   * @param chronoUnit
   * @return
   */
  public static long between(LocalDateTime startLocalDateTime, LocalDateTime endLocalDateTime, ChronoUnit chronoUnit) {
    LocalDate startDate = startLocalDateTime.toLocalDate();
    LocalDate endDate = endLocalDateTime.toLocalDate();
    return between(startDate, endDate, chronoUnit);
  }

  /**
   * 获取时间差
   *
   * @param startLocalDate
   * @param endLocalDate
   * @return
   */
  public static long between(LocalDate startLocalDate, LocalDate endLocalDate, ChronoUnit chronoUnit) {
    return chronoUnit.between(startLocalDate, endLocalDate);
  }
}
