package mvTech.web.controller.seeScreen;

import com.alibaba.fastjson2.JSONObject;
import mvTech.web.controller.seeScreen.util.ComparisonHelper;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
@RequestMapping(value = "getNum")
public class Controller {

    @GetMapping("/getKey")
    @ResponseBody
    @CrossOrigin(origins = "https://dtptxpchn.qbe7b.com/")
    public String getKey(
            @RequestParam(value = "lock") String lock,
            @RequestParam(value = "firstTimeSendData") String firstTimeSendData,
            @RequestParam(value = "leftCleanValue") BigDecimal leftCleanValue,
            @RequestParam(value = "rightCleanValue") BigDecimal rightCleanValue,
            @RequestParam(value = "A") BigDecimal A,
            @RequestParam(value = "k1") BigDecimal k1,
            @RequestParam(value = "Bk") BigDecimal Bk,
            @RequestParam(value = "k2") BigDecimal k2,
            @RequestParam(value = "myK3_keep") BigDecimal myK3_keep,
            @RequestParam(value = "myK3_dis", required = false) BigDecimal myK3_dis // 可选参数，放弃首次利润
    ) {
        JSONObject json = new JSONObject();

        System.out.println("leftCleanValue: " + leftCleanValue);
        System.out.println("rightCleanValue: " + rightCleanValue);
        System.out.println("A: " + A);
        System.out.println("k1: " + k1);
        System.out.println("Bk: " + Bk);
        System.out.println("k2: " + k2);
        System.out.println("myK3_keep: " + myK3_keep);
        System.out.println("myK3_dis: " + myK3_dis);

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formattedDate = dateFormat.format(new Date());
        System.out.println("当前时间（精确到秒）: " + formattedDate);
        String compareRes = ComparisonHelper.compareConsistent(json, firstTimeSendData, leftCleanValue, rightCleanValue);
        json.put("compareRes", compareRes);
        String log_res = CountNum.method(lock, json, compareRes, A, k1, Bk, k2, myK3_keep, myK3_dis);
        json.put("logMsg", log_res + formattedDate);
        return json.toString();
    }
}