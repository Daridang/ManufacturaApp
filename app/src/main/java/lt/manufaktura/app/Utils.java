package lt.manufaktura.app;

import android.content.SharedPreferences;
import android.util.Log;

import androidx.databinding.InverseMethod;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by
 * +-+-+-+-+-+-+-+-+
 * |D|a|r|i|d|a|n|g|
 * +-+-+-+-+-+-+-+-+
 * on 2020-08-20.
 */
public class Utils {
    @InverseMethod("toText")
    public static double toDouble(String price) {
        double p = 0.0;
        if (!"".equals(price)) {
            p = Double.parseDouble(price);
        }
        return p;
    }

    @InverseMethod("toDouble")
    public static String toText(double price) {
        return String.valueOf(price);
    }

    public static boolean isTokenExpired(String time) {
        SimpleDateFormat format =
                new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", new Locale("lt", "LT"));
        format.setTimeZone(TimeZone.getTimeZone("UTC"));
        try {
            Date parsedDate = format.parse(time);
            Date currentDate = Calendar.getInstance().getTime();
            return currentDate.after(parsedDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }
}