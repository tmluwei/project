package com.ahao.invoice.invoice.util;

import com.ahao.config.Setter;
import com.ahao.util.StringHelper;
import com.alibaba.fastjson.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashMap;

/**
 * Created by Ahaochan on 2017/7/30.
 * <p>
 * 获取行政区划代码
 */
public abstract class ProvinceUtils {
    private static final Logger logger = LoggerFactory.getLogger(ProvinceUtils.class);
    private static final HashMap<String, String> codes = new HashMap<>(100);


    static {
        try {
            String url = Setter.getString("provinceUrl");
            Document doc = Jsoup.connect(url).get();
            Elements msoNormals = doc.getElementsByClass("MsoNormal");
//            JSONArray array = new JSONArray(msoNormals.size());
            for (Element msoNormal : msoNormals) {
                Elements spans = msoNormal.getElementsByTag("span");
                JSONObject province = new JSONObject();

                spans.stream()
                        .filter(s -> StringHelper.isNotEmpty(s.text()))
                        .forEach(s -> {
                            String text = StringHelper.trim(s.text());
                            if (StringHelper.isNumeric(text)) {
                                province.put("code", text);
                            } else if (province.containsKey("code")) {
                                province.put("city", text);
                            }
                        });
                if (province.containsKey("code") && !StringHelper.equals(province.getString("city"),
                        "市辖区", "省直辖县级行政区划")) {
                    codes.put(province.getString("code"), province.getString("city"));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("获取行政区划代码错误! ");
        }
    }

    public static String getCity(String code) {
        return codes.get(code);
    }

}