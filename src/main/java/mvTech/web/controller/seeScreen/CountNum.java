package mvTech.web.controller.seeScreen;


import com.alibaba.fastjson2.JSONObject;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class CountNum {

    public static String method(String lock, JSONObject json, String compareRes, BigDecimal A, BigDecimal k1, BigDecimal Bk, BigDecimal k2, BigDecimal myK3_keep, BigDecimal myK3_dis) {
        StringBuilder sb = new StringBuilder();

        // 第一轮投入
        BigDecimal ADecimal = A.setScale(3, RoundingMode.HALF_UP);
        sb.append("第一轮 投入 = ").append(ADecimal).append("<br>");

        // 第一轮纯利
        BigDecimal value_step1 = k1.multiply(ADecimal).subtract(ADecimal).setScale(3, RoundingMode.HALF_UP);
        sb.append("第一轮 纯利 = ").append(value_step1).append("<br>");
        sb.append("左冲平 ---> 右边 = ").append(divideTwoByFractionalPart(k1)).append("<br>");
        sb.append("k1 = ").append(k1).append("<br>");
        sb.append("------------------------").append("<br>");

        if (compareRes != null) {
            sb.append("<br>").append("是否需要对冲: " + (compareRes.equals("true") ? "否" : "是")).append("<br>").append("<br>").append("------------------------<br>");
        } else {
            sb.append("<br>").append("首次调用接口...").append("<br>").append("<br>").append("------------------------<br>");
        }

        // 第二轮投入
        BigDecimal BDecimal = Bk.multiply(A).setScale(3, RoundingMode.HALF_UP);
        sb.append("第二轮 投入 = ").append(BDecimal).append("<br>");

        // 第二轮毛利
        BigDecimal value_step2 = BDecimal.multiply(k2).subtract(BDecimal).setScale(3, RoundingMode.HALF_UP);
        sb.append("第二轮 毛利 = ").append(value_step2).append("<br>");

        // 第二轮纯利
        BigDecimal value_step2_real = value_step2.subtract(ADecimal).setScale(3, RoundingMode.HALF_UP);
        sb.append("第二轮 纯利 = ").append(value_step2_real).append("<br>");
        sb.append("k2 = ").append(k2).append("<br>");
        sb.append("------------------------").append("<br>");

        // 第三轮保留初次利润的情况
        sb.append("第三轮_保1 ： ").append("<br>");
        sb.append("第二轮全部投注 ： ").append(BDecimal).append("<br>");
        BigDecimal C_in_keep = value_step2.subtract(ADecimal).setScale(3, RoundingMode.HALF_UP);
        sb.append("第三轮可用投注 ： ").append(C_in_keep).append("<br>");
        BigDecimal needed_keep = null;//防止除以0
        if (C_in_keep.compareTo(new BigDecimal("0.000")) == 0 || C_in_keep.compareTo(new BigDecimal("0.000")) < 0) {
            needed_keep = new BigDecimal("-1");
        } else {
            needed_keep = BDecimal.add(C_in_keep).divide(C_in_keep, 3, RoundingMode.HALF_UP);
        }
        sb.append("第三轮冲平需要 x").append(Bk).append(" ： ").append(needed_keep).append(" ---> ").append(BDecimal.add(C_in_keep)).append("<br>");
        BigDecimal final_profit_keep = C_in_keep.multiply(myK3_keep).subtract(C_in_keep).subtract(BDecimal).add(value_step1).setScale(3, RoundingMode.HALF_UP);
        sb.append("最终轮纯粹利润：").append(myK3_keep).append(" ---> ").append(final_profit_keep).append("<br>");
        sb.append("<br>");

        BigDecimal needed_dis = null;//防止除以0
        // 如果存在放弃初次利润的情况，计算第三轮毁1的情况
        if (myK3_dis != null) {
            sb.append("第三轮_毁1 : ").append("<br>");
            sb.append("第二轮全部投注 ： ").append(ADecimal.multiply(Bk)).append("<br>");
            BigDecimal C_in_dis = value_step2.subtract(ADecimal).add(value_step1).setScale(3, RoundingMode.HALF_UP);
            sb.append("第三轮可用投注 ： ").append(C_in_dis).append("<br>");
            if (C_in_keep.compareTo(new BigDecimal("0.000")) == 0 || C_in_dis.compareTo(new BigDecimal("0.000")) < 0) {
                needed_dis = new BigDecimal("-1");
            } else {
                needed_dis = BDecimal.add(C_in_dis).divide(C_in_dis, 3, RoundingMode.HALF_UP);
            }
            sb.append("第三轮冲平需要 x").append(Bk).append(" ： ").append(needed_dis).append(" ---> ").append(BDecimal.add(C_in_dis)).append("<br>");
            BigDecimal final_profit_dis = C_in_dis.multiply(myK3_dis).subtract(C_in_dis).subtract(BDecimal).setScale(3, RoundingMode.HALF_UP);
            sb.append("最终轮纯粹利润：").append(myK3_dis).append(" ---> ").append(final_profit_dis).append("<br>");
        }
        sb.append("------------------------").append("<br>");
        json.put("logMsg", sb.toString());
        if (compareRes != null && compareRes.equals("false")) {
            String bigK = json.get("bigK").toString();
            if (null != bigK) {
                BigDecimal decimal_bigK = new BigDecimal(bigK);
                if (lock.equals("unlock") && decimal_bigK.compareTo(needed_keep) == 0) {
                    json.put("bigMsg", "稳了!");
                }
                if (lock.equals("unlock") && decimal_bigK.compareTo(needed_keep) > 0) {
                    json.put("bigMsg", "赚了!");
                }
                if (lock.equals("unlock") && decimal_bigK.compareTo(needed_dis) == 0) {
                    json.put("bigMsg", "平了!");
                }
                json.put("fanzhuan", "反转!");
            }
        }
        return sb.toString();
    }
    public static String divideTwoByFractionalPart(BigDecimal decimalValue) {
        BigDecimal modResult = decimalValue.remainder(BigDecimal.ONE);
        BigDecimal result = new BigDecimal("2").divide(modResult, 3, RoundingMode.HALF_UP);
        return result.toString();
    }
//    public static void main(String[] args) {
//        // 初始化参数
//        BigDecimal A = new BigDecimal("10.000");
//        BigDecimal k1 = new BigDecimal("1.500");
//        BigDecimal Bk = new BigDecimal("3.000");
//        BigDecimal k2 = new BigDecimal("1.500");
//        BigDecimal myK3_keep = new BigDecimal("7.000");
//        BigDecimal myK3_dis = new BigDecimal("4.000");
//
//        // 调用计算方法
//        String logOutput = method("yes", A, k1, Bk, k2, myK3_keep, myK3_dis);
//
//        // 假设这里是后端接口，将logOutput返回给前端
//        System.out.println(logOutput);
//    }
}