package com.demo.common.service.tools;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.junit.Test;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * *** Java Number & Math 类 ***
 * <p>
 * 所有的包装类（Integer、Long、Byte、Double、Float、Short）都是抽象类 Number 的子类。
 */
public class PositionDemo {

    // 数据来源: https://lbs.amap.com/api/webservice/guide/api/district

    final static String str = "{\"status\":\"1\",\"info\":\"OK\",\"infocode\":\"10000\",\"count\":\"1\",\"suggestion\":{\"keywords\":[],\"cities\":[]},\"districts\":[{\"citycode\":\"0760\",\"adcode\":\"442000\",\"name\":\"中山市\",\"center\":\"113" +
            ".382391,22.521113\",\"level\":\"city\",\"districts\":[{\"citycode\":\"0760\",\"adcode\":\"442000\",\"name\":\"三角镇\",\"center\":\"113.416,22.7079\",\"level\":\"street\",\"districts\":[]},{\"citycode\":\"0760\",\"adcode\":\"442000\"," +
            "\"name\":\"横栏镇\",\"center\":\"113.224,22.6048\",\"level\":\"street\",\"districts\":[]},{\"citycode\":\"0760\",\"adcode\":\"442000\",\"name\":\"五桂山街道\",\"center\":\"113.399,22.4847\",\"level\":\"street\",\"districts\":[]}," +
            "{\"citycode\":\"0760\",\"adcode\":\"442000\",\"name\":\"东升镇\",\"center\":\"113.321,22.5607\",\"level\":\"street\",\"districts\":[]},{\"citycode\":\"0760\",\"adcode\":\"442000\",\"name\":\"神湾镇\",\"center\":\"113.376,22.2768\"," +
            "\"level\":\"street\",\"districts\":[]},{\"citycode\":\"0760\",\"adcode\":\"442000\",\"name\":\"火炬开发区街道\",\"center\":\"113.42,22.5577\",\"level\":\"street\",\"districts\":[]},{\"citycode\":\"0760\",\"adcode\":\"442000\"," +
            "\"name\":\"小榄镇\",\"center\":\"113.23,22.7026\",\"level\":\"street\",\"districts\":[]},{\"citycode\":\"0760\",\"adcode\":\"442000\",\"name\":\"南朗镇\",\"center\":\"113.482,22.519\",\"level\":\"street\",\"districts\":[]}," +
            "{\"citycode\":\"0760\",\"adcode\":\"442000\",\"name\":\"古镇镇\",\"center\":\"113.167,22.6751\",\"level\":\"street\",\"districts\":[]},{\"citycode\":\"0760\",\"adcode\":\"442000\",\"name\":\"民众镇\",\"center\":\"113.499,22.6855\"," +
            "\"level\":\"street\",\"districts\":[]},{\"citycode\":\"0760\",\"adcode\":\"442000\",\"name\":\"港口镇\",\"center\":\"113.354,22.6027\",\"level\":\"street\",\"districts\":[]},{\"citycode\":\"0760\",\"adcode\":\"442000\",\"name\":\"三乡镇\"," +
            "\"center\":\"113.432,22.3879\",\"level\":\"street\",\"districts\":[]},{\"citycode\":\"0760\",\"adcode\":\"442000\",\"name\":\"石岐区街道\",\"center\":\"113.405,22.5247\",\"level\":\"street\",\"districts\":[]},{\"citycode\":\"0760\"," +
            "\"adcode\":\"442000\",\"name\":\"大涌镇\",\"center\":\"113.297,22.4834\",\"level\":\"street\",\"districts\":[]},{\"citycode\":\"0760\",\"adcode\":\"442000\",\"name\":\"南头镇\",\"center\":\"113.328,22.6882\",\"level\":\"street\"," +
            "\"districts\":[]},{\"citycode\":\"0760\",\"adcode\":\"442000\",\"name\":\"黄圃镇\",\"center\":\"113.329,22.7457\",\"level\":\"street\",\"districts\":[]},{\"citycode\":\"0760\",\"adcode\":\"442000\",\"name\":\"东区街道\",\"center\":\"113.4,22" +
            ".5274\",\"level\":\"street\",\"districts\":[]},{\"citycode\":\"0760\",\"adcode\":\"442000\",\"name\":\"阜沙镇\",\"center\":\"113.34,22.6406\",\"level\":\"street\",\"districts\":[]},{\"citycode\":\"0760\",\"adcode\":\"442000\"," +
            "\"name\":\"坦洲镇\",\"center\":\"113.38,22.2868\",\"level\":\"street\",\"districts\":[]},{\"citycode\":\"0760\",\"adcode\":\"442000\",\"name\":\"西区街道\",\"center\":\"113.314,22.5649\",\"level\":\"street\",\"districts\":[]}," +
            "{\"citycode\":\"0760\",\"adcode\":\"442000\",\"name\":\"板芙镇\",\"center\":\"113.358,22.4068\",\"level\":\"street\",\"districts\":[]},{\"citycode\":\"0760\",\"adcode\":\"442000\",\"name\":\"沙溪镇\",\"center\":\"113.344,22.5248\"," +
            "\"level\":\"street\",\"districts\":[]},{\"citycode\":\"0760\",\"adcode\":\"442000\",\"name\":\"南区街道\",\"center\":\"113.367,22.4892\",\"level\":\"street\",\"districts\":[]},{\"citycode\":\"0760\",\"adcode\":\"442000\",\"name\":\"东凤镇\"," +
            "\"center\":\"113.308,22.6803\",\"level\":\"street\",\"districts\":[]}]}]}";

    @Test
    public void test() {
        System.out.println("\n\n\n\n");
        JSONObject parse = JSON.parseObject(str);
        JSONObject districtAll = (JSONObject) parse.getJSONArray("districts").get(0);
        String areaBigCode = districtAll.getString("adcode");
        JSONArray districts = districtAll.getJSONArray("districts");
        AtomicInteger index = new AtomicInteger(1);
        districts.forEach(district -> {
            final String format = String.format("%02d", index.getAndIncrement());
            JSONObject districtTo = (JSONObject) district;
            System.out.println("INSERT INTO `nyh_platform`.`t_position`" +
                    "(`area_name`, `area_code`, `city_code`, `level`, `area_index`, `create_time`, `create_user_id`, `create_user`, `update_time`, `update_user_id`, `update_user`, `create_empno`, `delete_status`, `sync_data`) " +
                    "VALUES " +
                    "('" + districtTo.get("name") + "', " + (areaBigCode.substring(0, 4) + format) + ", '" + districtTo.get("citycode") + "', 2, '" + areaBigCode + "', '2020-04-20 02:09:35', '-1', 'admin', '2020-04-20 02:09:35', '-1', 'admin', " +
                    "'-1', 1, '2021-03-10');");
        });
    }
}
