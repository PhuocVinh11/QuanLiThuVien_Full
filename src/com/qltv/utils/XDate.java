/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qltv.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class XDate {
    
     public static SimpleDateFormat sdf = new SimpleDateFormat();

    public static Date toDate(String date, String... pattern) {
        try {
            if (pattern.length > 0) {
                sdf.applyPattern(pattern[0]);
            }
            if (date == null) {
                return XDate.now();
            }
            return sdf.parse(date);
        } catch (ParseException ex) {
            throw new RuntimeException(ex);
        }
    }

    public static String toString(Date date, String pattern) {
        sdf.applyPattern(pattern);
        return sdf.format(date);
    }

    public static Date addDays(Date date, long days) {
        date.setTime(date.getTime() + days * 24 * 60 * 60 * 1000);
        return date;
    }
    
    public static Date now() {
        return new Date(); //new Date lấy giờ hiện tại
    }
}
