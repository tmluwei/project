package com.ahao.invoice.invoice.util;

import com.ahao.commons.config.Setter;
import com.ahao.commons.util.lang.StringHelper;
import com.ahao.commons.util.lang.math.NumberHelper;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Ahaochan on 2017/7/30.
 * <p>
 * 获取行政区划代码
 */
public abstract class ProvinceUtils {
    private static final Logger logger = LoggerFactory.getLogger(ProvinceUtils.class);
    private static final Map<String, String> codes;


    static {
        Map<String, String> jsoup = new HashMap<>(100);
        try {
            String url = Setter.getString("provinceUrl");
            Document doc = Jsoup.connect(url).get();
            Elements msoNormals = doc.getElementsByClass("MsoNormal");
            for (Element msoNormal : msoNormals) {
                Elements spans = msoNormal.getElementsByTag("span");
                JSONObject province = new JSONObject();

                spans.stream()
                        .filter(s -> StringUtils.isNotEmpty(s.text()))
                        .forEach(s -> {
                            String text = StringHelper.trim(s.text());
                            if (NumberHelper.isNumber(text)) {
                                province.put("code", text);
                            } else if (province.containsKey("code")) {
                                province.put("city", text);
                            }
                        });
                if (province.containsKey("code") && !StringHelper.equalsAny(province.getString("city"),
                        "市辖区", "省直辖县级行政区划")) {
                    jsoup.put(province.getString("code"), province.getString("city"));
                }
            }
        } catch (IOException e) {
            logger.error("获取行政区划代码错误! ", e);
        }
        codes = Collections.unmodifiableMap(jsoup);
    }

    public static String getCity(String code) {
        return codes.get(code);
    }

}
