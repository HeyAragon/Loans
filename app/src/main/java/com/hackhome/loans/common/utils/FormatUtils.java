package com.hackhome.loans.common.utils;

/**
 * desc:
 * author: aragon
 * date: 2017/12/23 0023.
 */
public class FormatUtils {

    public static final String DAY_INTEREST = "日利率低至：%s%%";
    public static final String MAX_LIMIT = "最高额度%s元";
    public static final String LOAN_PEOPLE_NUM = "%s人已贷";
    public static final String LOAN_RANGE = "可贷额度：%1$s-%2$s元";
    public static final String TIME_RANGE = "可贷期限：%1$s-%2$s天";
    public static final String SUCCESS_RATE = "成功率：%s%%";
    public static final String LOAN_PEOPLE_COUNT = "已放贷：%s人";
    public static final String SECOND = "%sS";//秒


    public static String format(String msg, Object... objects) {
        return String.format(msg, objects);
    }

    public static String escape(String src) {
        int i;
        char j;
        StringBuffer tmp = new StringBuffer();
        tmp.ensureCapacity(src.length() * 6);
        for (i = 0; i < src.length(); i++) {
            j = src.charAt(i);
            if (Character.isDigit(j) || Character.isLowerCase(j)
                    || Character.isUpperCase(j))
                tmp.append(j);
            else if (j < 256) {
                tmp.append("%");
                if (j < 16)
                    tmp.append("0");
                tmp.append(Integer.toString(j, 16));
            } else {
                tmp.append("%u");
                tmp.append(Integer.toString(j, 16));
            }
        }
        return tmp.toString();
    }
}
