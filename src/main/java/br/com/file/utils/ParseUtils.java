package br.com.file.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ParseUtils {
    public static Long parseLong(Object obj) {
        Long result = null;

        if (obj != null)
            result = Long.parseLong(String.valueOf(obj));

        return result;
    }

    public static String parseString(Object obj) {
        String result = null;

        if (obj != null)
            result = String.valueOf(obj);

        return result;
    }

    public static Integer parseInteger(Object obj) {
        Integer result = null;

        if (obj != null)
            result = Integer.parseInt(String.valueOf(obj));

        return result;
    }

    public static Date parseDate(Object obj) {
        Date result = null;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        try {
            result = formatter.parse(String.valueOf(obj));
        } catch (Exception ex) {
        }

        return result;
    }
}
