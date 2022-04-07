package fastjson;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * @author lqb
 * @date 2022/4/7 15:13
 */
public class fastJsonTest {
    public static void main(String[] args) {
        String s = "{\n" +
                "    \"errcode\": 0,\n" +
                "    \"errmsg\": \"ok\",\n" +
                "    \"department\": [\n" +
                "        {\n" +
                "            \"id\": 2033407,\n" +
                "            \"name\": \"粤西集什分公司\",\n" +
                "            \"parentid\": 11426,\n" +
                "            \"order\": 99989000,\n" +
                "            \"department_leader\": []\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": 2101608,\n" +
                "            \"name\": \"自营销售部\",\n" +
                "            \"parentid\": 2033407,\n" +
                "            \"order\": 99999000,\n" +
                "            \"department_leader\": []\n" +
                "        }\n" +
                "    ]\n" +
                "}";
        for (int i = 0; i < 100; i++) {
            JSONObject jsonObject = JSON.parseObject(s);
            System.out.println(jsonObject);
            JSONArray jsonArray = jsonObject.getJSONArray("department");
            System.out.println(jsonArray.get(0));
        }
    }
}
