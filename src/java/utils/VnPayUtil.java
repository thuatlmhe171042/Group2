package utils;

import models.Order;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class VnPayUtil {
    public static String buildPayUrl(Order order, String tmnCode, String hashSecret, String returnUrl) {
        Map<String, String> params = new HashMap<>();
        params.put("vnp_Version", "2.1.0");
        params.put("vnp_Command", "pay");
        params.put("vnp_TmnCode", tmnCode);
        params.put("vnp_Amount", String.valueOf(Math.round(order.getOriginalAmount()) * 100000));
// Số tiền * 100
        params.put("vnp_CurrCode", "VND");
        params.put("vnp_TxnRef", order.getOrderCode());
        params.put("vnp_OrderInfo", "Thanh toan ve tau: " + order.getOrderCode());
        params.put("vnp_OrderType", "other");
        params.put("vnp_Locale", "vn");
        params.put("vnp_ReturnUrl", returnUrl);
        params.put("vnp_IpAddr", "127.0.0.1");
        params.put("vnp_CreateDate", new java.text.SimpleDateFormat("yyyyMMddHHmmss").format(new java.util.Date()));

        // Sắp xếp key theo alphabet
        List<String> fieldNames = new ArrayList<>(params.keySet());
        Collections.sort(fieldNames);

        StringBuilder hashData = new StringBuilder();
        StringBuilder query = new StringBuilder();

        try {
            for (int i = 0; i < fieldNames.size(); i++) {
                String name = fieldNames.get(i);
                String value = params.get(name);

                if (value != null && value.length() > 0) {
                    // hashData: encode value thôi
                    hashData.append(name).append('=').append(URLEncoder.encode(value, StandardCharsets.US_ASCII.toString()));
                    // query: encode cả key lẫn value
                    query.append(URLEncoder.encode(name, StandardCharsets.US_ASCII.toString()))
                         .append('=')
                         .append(URLEncoder.encode(value, StandardCharsets.US_ASCII.toString()));
                    if (i < fieldNames.size() - 1) {
                        hashData.append('&');
                        query.append('&');
                    }
                }
            }
        } catch (Exception ex) {
            throw new RuntimeException("Encoding error in buildPayUrl", ex);
        }

        String secureHash = hmacSHA512(hashSecret, hashData.toString());
        query.append("&vnp_SecureHash=").append(secureHash);

        return "https://sandbox.vnpayment.vn/paymentv2/vpcpay.html?" + query.toString();
    }

    // Hàm tạo HmacSHA512 signature
    public static String hmacSHA512(String key, String data) {
        try {
            Mac hmac = Mac.getInstance("HmacSHA512");
            SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "HmacSHA512");
            hmac.init(secretKey);
            byte[] bytes = hmac.doFinal(data.getBytes(StandardCharsets.UTF_8));
            StringBuilder hash = new StringBuilder();
            for (byte b : bytes) hash.append(String.format("%02x", b));
            return hash.toString();
        } catch (Exception e) {
            throw new RuntimeException("Hmac SHA512 error", e);
        }
    }

    // Xác thực callback trả về từ VNPay (dùng khi /payment_result)
    public static boolean verifyResponse(Map<String, String> params, String hashSecret, String vnp_SecureHash) {
        // Bỏ các tham số không dùng để ký
        Map<String, String> filtered = new HashMap<>(params);
        filtered.remove("vnp_SecureHash");
        filtered.remove("vnp_SecureHashType");

        List<String> fieldNames = new ArrayList<>(filtered.keySet());
        Collections.sort(fieldNames);

        StringBuilder hashData = new StringBuilder();
        try {
            for (int i = 0; i < fieldNames.size(); i++) {
                String name = fieldNames.get(i);
                String value = filtered.get(name);
                if (value != null && value.length() > 0) {
                    hashData.append(name).append("=").append(URLEncoder.encode(value, StandardCharsets.US_ASCII.toString()));
                    if (i < fieldNames.size() - 1) hashData.append("&");
                }
            }
        } catch (Exception ex) {
            throw new RuntimeException("Encoding error in verifyResponse", ex);
        }
        String hash = hmacSHA512(hashSecret, hashData.toString());
        return hash.equalsIgnoreCase(vnp_SecureHash);
    }
    
    public static String getRawData(Map<String, String> fields) {
    List<String> keys = new ArrayList<>(fields.keySet());
    Collections.sort(keys);
    StringBuilder sb = new StringBuilder();
    try {
        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            String value = fields.get(key);
            if (value != null && value.length() > 0) {
                sb.append(key).append("=").append(java.net.URLEncoder.encode(value, "US-ASCII"));
                if (i < keys.size() - 1) sb.append("&");
            }
        }
    } catch (Exception ex) {
        throw new RuntimeException("Encoding error in getRawData", ex);
    }
    return sb.toString();
}

}
