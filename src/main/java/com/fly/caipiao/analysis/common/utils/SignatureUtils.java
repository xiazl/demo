package com.fly.caipiao.analysis.common.utils;

import com.fly.caipiao.analysis.framework.excepiton.AppException;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 阿里云日志下载--签名认证
 */

public class SignatureUtils {
    private final static Logger LOGGER = LoggerFactory.getLogger(SignatureUtils.class);

    private final static String CHARSET_UTF8 = "utf8";
    private final static String ALGORITHM = "UTF-8";
    private final static String SEPARATOR = "&";
    private final static String BASE_URL = "https://cdn.aliyuncs.com/?";

    private final static String AccessKeyId = "LTAIbzOJzjxBqAuj";
    private final static String AccessKeySecret = "Aw5faogIc2G400wrGxyylECn1gddmh";


    public static Map<String, String> splitQueryString(String url)
            throws URISyntaxException, UnsupportedEncodingException {
        URI uri = new URI(url);
        String query = uri.getQuery();
        final String[] pairs = query.split("&");
        TreeMap<String, String> queryMap = new TreeMap<String, String>();
        for (String pair : pairs) {
            final int idx = pair.indexOf("=");
            final String key = idx > 0 ? pair.substring(0, idx) : pair;
            if (!queryMap.containsKey(key)) {
                queryMap.put(key, URLDecoder.decode(pair.substring(idx + 1), CHARSET_UTF8));
            }
        }
        return queryMap;
    }

    public static String generate(String method, Map<String, String> parameter,
            String accessKeySecret) throws Exception {
        String signString = generateSignString(method, parameter);
        byte[] signBytes = hmacSHA1Signature(accessKeySecret + "&", signString);
        String signature = newStringByBase64(signBytes);
        if ("POST".equals(method))
            return signature;
        return URLEncoder.encode(signature, "UTF-8");

    }

    public static String generateSignString(String httpMethod, Map<String, String> parameter)
            throws IOException {

        String canonicalizedQueryString = generateQueryString(parameter, true);
        if (null == httpMethod) {
            throw new RuntimeException("httpMethod can not be empty");
        }

        StringBuilder stringToSign = new StringBuilder();
        stringToSign.append(httpMethod).append(SEPARATOR);
        stringToSign.append(percentEncode("/")).append(SEPARATOR);
        stringToSign.append(percentEncode(canonicalizedQueryString));

        return stringToSign.toString();
    }
    
    public static String generateQueryString(Map<String, String> params, boolean isEncodeKV) {
        TreeMap<String, String> sortParameter = new TreeMap<String, String>();
        sortParameter.putAll(params);

        StringBuilder canonicalizedQueryString = new StringBuilder();
        for (Map.Entry<String, String> entry : sortParameter.entrySet()) {
            if (isEncodeKV)
                canonicalizedQueryString.append(percentEncode(entry.getKey())).append("=")
                        .append(percentEncode(entry.getValue())).append("&");
            else
                canonicalizedQueryString.append(entry.getKey()).append("=")
                        .append(entry.getValue()).append("&");
        }
        if (canonicalizedQueryString.length() > 1) {
            canonicalizedQueryString.setLength(canonicalizedQueryString.length() - 1);
        }
        return canonicalizedQueryString.toString();
    }

    public static String percentEncode(String value) {
        try {
            return value == null ? null : URLEncoder.encode(value, CHARSET_UTF8)
                    .replace("+", "%20").replace("*", "%2A").replace("%7E", "~");
        } catch (Exception e) {
        }
        return "";
    }

    public static byte[] hmacSHA1Signature(String secret, String baseString)
            throws Exception {
        if (StringUtils.isEmpty(secret)) {
            throw new IOException("secret can not be empty");
        }
        if (StringUtils.isEmpty(baseString)) {
            return null;
        }
        Mac mac = Mac.getInstance("HmacSHA1");
        SecretKeySpec keySpec = new SecretKeySpec(secret.getBytes(CHARSET_UTF8), ALGORITHM);
        mac.init(keySpec);
        return mac.doFinal(baseString.getBytes(CHARSET_UTF8));
    }

    public static String newStringByBase64(byte[] bytes)
            throws UnsupportedEncodingException {
        if (bytes == null || bytes.length == 0) {
            return null;
        }

        return new String(Base64.encodeBase64(bytes, false), CHARSET_UTF8);
    }

    /**
     * 获取UTC时间
     * @return
     */
    public static String getCurrentUtcTime(Date date) {
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        TimeZone timeZone = TimeZone.getTimeZone("GMT-0");
        formatter.setTimeZone(timeZone);
        String utcDate = formatter.format(date);
        return utcDate;
    }


    public static String getUrl(Map<String,String> mapParam) {
        String Action = "DescribeCdnDomainLogs";
        String LogDay = "2018-07-29";

		String Format="JSON";
		String Version="2014-11-11";
		String SignatureMethod="HMAC-SHA1";
		String SignatureVersion="1.0";

		Map<String,String> param = new HashMap<>();
        param.put("Action", Action);
//        param.put("LogDay", LogDay);

        param.put("AccessKeyId", AccessKeyId);
		param.put("Format", Format);
		param.put("Version", Version);
		param.put("SignatureMethod", SignatureMethod);
		param.put("SignatureVersion", SignatureVersion);
		param.put("SignatureNonce", UUID.randomUUID().toString());
		param.put("Timestamp", getCurrentUtcTime(new Date()));

		param.putAll(mapParam);

		try {
			String url = generate("GET",param,AccessKeySecret);
			String paramString = generateQueryString(param,true);
			return BASE_URL+paramString+"&Signature="+url;
		} catch (Exception e) {
            LOGGER.error(e.getMessage(),e);
			// TODO Auto-generated catch block
			throw new AppException("生成签名认证失败");
		}

    }
   
}