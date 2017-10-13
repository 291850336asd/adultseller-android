package adult.mas.com.adultgoodssell.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by sunmeng on 17/8/2.
 */

public class StringUtils {

    public static String concatString(String... mParams) {
        StringBuilder builder = new StringBuilder();
        if (mParams.length > 0) {
            for (String mParam : mParams) {
                if (!isEmpty(mParam))
                    builder.append(mParam);
                else
                    builder.append("");
            }
        }
        return builder.toString();
    }

    public static boolean isEmpty(String s) {
        return s == null || s.isEmpty();
    }

    public static String formatString(String formatStr, String value){
        return  String.format(formatStr, value);
    }


    /**
     * 手机号验证
     *
     * @param  str
     * @return 验证通过返回true
     */
    public static boolean isMobile(String str) {
        Pattern p = null;
        Matcher m = null;
        boolean b = false;
        p = Pattern.compile("^[1][1,2,3,4,5,6,7,8,9][0-9]{9}$"); // 验证手机号
        m = p.matcher(str);
        b = m.matches();
        return b;
    }
}
