package lt.manufaktura.app;

import androidx.databinding.InverseMethod;

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
}