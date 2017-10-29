package com.chemyoo.utils;

import java.util.Calendar;
import java.util.Date;

public class TimeUtil {

    public static Date getNow()
    {
        return Calendar.getInstance().getTime();
    }
}
