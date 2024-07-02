package mvTech.web.controller.seeScreen.util;


import com.alibaba.fastjson2.JSONObject;

import java.math.BigDecimal;

public class ComparisonHelper {
    // 静态变量用于记录第一次传入的两个数的比较关系
    private static BigDecimal firstComparisonResult;

    // 方法用于比较两个数的大小关系，并记录第一次的比较结果
    public static void compareAndRemember(BigDecimal num1, BigDecimal num2) {
        int comparison = num1.compareTo(num2);
        firstComparisonResult = new BigDecimal(comparison);
    }

    // 方法用于比较新传入的两个数与第一次的比较结果保持一致
    public static String compareConsistent(JSONObject json, String firstTimeSendData, BigDecimal num1, BigDecimal num2) {
        if ("yes".equals(firstTimeSendData)) {
            firstComparisonResult = null;
            compareAndRemember(num1, num2);
            json.put("bigK", null);
        } else {
            int comparison = num1.compareTo(num2);
            BigDecimal decimal_comparison = new BigDecimal(comparison);
            if (firstComparisonResult.compareTo(decimal_comparison) == 0) {
                //位置没变
                return "true";
            } else {
                if (comparison > 0) {
                    json.put("bigK", num1);
                } else if (comparison < 0) {
                    json.put("bigK", num2);
                } else if (comparison == 0) {
                    json.put("bigK", num1);
                }
                //位置变了
                return "false";
            }
        }
        return null;
    }

    public static void main(String[] args) {

        BigDecimal a = new BigDecimal(1);
        BigDecimal b = new BigDecimal(2);
        System.out.println(a.compareTo(b));
    }
}
