package mvTech.web.controller.seeScreen;

import java.math.BigDecimal;

public class GetVal {
    static BigDecimal tmp = new BigDecimal("0.000");

    public static boolean makeMoney(BigDecimal valLeft) {
        tmp = valLeft;
        if (tmp.compareTo(valLeft) != 0) {
            return true;
        }
        return false;
    }

}
