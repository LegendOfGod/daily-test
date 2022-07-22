package jdk;

import cn.hutool.crypto.digest.MD5;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;

import java.nio.charset.StandardCharsets;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author lqb
 * @date 2022/6/8 16:06
 */
public class Test {
    private static final Pattern skuPattern = Pattern.compile("[0-9]{8,9}?[0-9A-Za-z]");

    public static void main(String[] args) {
        //System.out.println(MD5.create().digestHex("xWKOjvgD18040034", StandardCharsets.UTF_8));
        //System.out.println(MD5.create().digestHex("7dded2c3e7204169ad2485e590124f1da20e56633c173b36c664fd7536172db0", StandardCharsets.UTF_8));
//        String content = "以下5个产品，是否可以给海油客户供货？收货地址天津，需求数量暂不确定，麻烦确认下，谢谢。<br/>1.83835439&nbsp;空气呼吸器气瓶\\HONEYWELL&nbsp;BC1868527\\GB&nbsp;28053&nbsp;/霍尼韦尔\\6.8L碳瓶\\型号BC1868527\\匹配T8000&nbsp;SCBA805M空气呼吸器的气瓶&nbsp;<br/>2.83835438&nbsp;空气呼吸器气瓶\\HONEYWELL&nbsp;BC1868527L\\GB&nbsp;28053/霍尼韦尔\\6.8L自锁带表气瓶\\型号BC1868527L\\匹配T8000&nbsp;SCBA805ML/X空气呼吸器的气瓶&nbsp;<br/>3.83835437&nbsp;空气呼吸器气瓶\\HONEYWELL&nbsp;BC1868427L\\GB&nbsp;28053&nbsp;/霍尼韦尔\\6.8L&nbsp;Luxfer自锁带表气瓶\\型号BC1868427L\\匹配T8500&nbsp;SCBA2005M/X空气呼吸器的气瓶&nbsp;<br/>4.83522049&nbsp;空气呼吸器气瓶\\MSA&nbsp;10121837\\EN&nbsp;1146&nbsp;/MSA\\6.8L带表BTIC碳纤气瓶\\型号10121837\\匹配MSA&nbsp;10165419/10176286空气呼吸器的气瓶&nbsp;<br/>5.83835440&nbsp;空气呼吸器气瓶\\DELTA&nbsp;106502\\GB&nbsp;28053&nbsp;/代尔塔\\6.8L碳纤维缠绕复合气瓶\\型号106502\\匹配代尔塔106005正压式空气呼吸器的气瓶&nbsp;";
//        System.out.println(extractSkuNos(content));
        //System.out.println(JSONObject.parseObject(null, Map.class));
        List<String> strs = new ArrayList<>();
        strs.add("aaa");
        strs.add("bbb");
        System.out.println(strs.contains("bbb"));
    }

    public static List<String> extractSkuNos(String content) {
        List<String> result = new ArrayList();
        if (StringUtils.isBlank(content)) {
            return result;
        } else {
            Matcher matcher = skuPattern.matcher(content);

            while(matcher.find()) {
                result.add(matcher.group());
            }

            return result;
        }
    }
}
