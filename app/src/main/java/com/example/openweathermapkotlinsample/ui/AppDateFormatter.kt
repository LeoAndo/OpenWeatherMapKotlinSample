package com.example.openweathermapkotlinsample.ui

import java.util.*

enum class DateFormatPattern(val pattern: String) {
    ISO8601("yyyy-MM-dd'T'HH:mm:ss"),
    YYYYMMDD("yyyy-MM-dd"),
    YYYYMMDD_SLASH("yyyy/MM/dd"),
    YYYYMMDD_JP("yyyy年MM月dd日"),
    MMDDEHHMM_JP("MM/dd E曜日 HH:mm"),
    YYYYMMDDHHMM_JP("yyyy年mm月dd日 hh時mm分"),
    HHMM("HH:mm"),
    E_JP("E曜日"),
    MMDD("MM/dd"),
    E("E")
}

enum class AppTimeZone(val timeZone: TimeZone) {
    UTC(TimeZone.getTimeZone("UTC")),
    TOKYO(TimeZone.getTimeZone("Asia/Tokyo"))
}
