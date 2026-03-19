package com.example.nhom3.project.modules.payment.service;

import com.example.nhom3.project.modules.order.entity.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Map;
import java.util.TimeZone;
import java.util.TreeMap;

@Service
@RequiredArgsConstructor
public class VNPAYService {
    @Value("${vnpay.tmn_code}") String tmnCode;
    @Value("${vnpay.hash_secret}") String hashSecret;
    @Value("${vnpay.pay_url}") String payUrl;
    @Value("${vnpay.return_url}") String returnUrl;

    public String createPaymentUrl(Order order, String ipAddress) {
        String vnp_TxnRef = order.getId().toString();
        long amount = order.getTotalAmount().multiply(new BigDecimal(100)).longValue();

        Map<String, String> vnp_Params = new TreeMap<>();
        vnp_Params.put("vnp_Version", "2.1.0");
        vnp_Params.put("vnp_Command", "pay");
        vnp_Params.put("vnp_TmnCode", tmnCode);
        vnp_Params.put("vnp_Amount", String.valueOf(amount));
        vnp_Params.put("vnp_CurrCode", "VND");
        vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
        vnp_Params.put("vnp_OrderInfo", "Thanh toan don hang: " + vnp_TxnRef);
        vnp_Params.put("vnp_OrderType", "other");
        vnp_Params.put("vnp_Locale", "vn");
        vnp_Params.put("vnp_ReturnUrl", returnUrl);
        vnp_Params.put("vnp_IpAddr", ipAddress);

        Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        vnp_Params.put("vnp_CreateDate", formatter.format(cld.getTime()));
        cld.add(Calendar.MINUTE, 15);
        vnp_Params.put("vnp_ExpireDate", formatter.format(cld.getTime()));

        StringBuilder hashData = new StringBuilder();
        StringBuilder query = new StringBuilder();
        vnp_Params.forEach((k, v) -> {
            try {
                hashData.append(k).append('=').append(URLEncoder.encode(v, StandardCharsets.US_ASCII.toString())).append('&');
                query.append(URLEncoder.encode(k, StandardCharsets.US_ASCII.toString())).append('=')
                        .append(URLEncoder.encode(v, StandardCharsets.US_ASCII.toString())).append('&');
            } catch (Exception e) { }
        });

        hashData.setLength(hashData.length() - 1);
        query.setLength(query.length() - 1);

        String vnp_SecureHash = VNPAYUtils.hmacSHA512(hashSecret, hashData.toString());
        return payUrl + "?" + query.toString() + "&vnp_SecureHash=" + vnp_SecureHash;
    }

    public boolean verifySignature(Map<String, String> fields) {
        String vnp_SecureHash = fields.get("vnp_SecureHash");
        fields.remove("vnp_SecureHash");
        fields.remove("vnp_SecureHashType");

        TreeMap<String, String> sorted = new TreeMap<>(fields);
        StringBuilder signData = new StringBuilder();
        sorted.forEach((k, v) -> {
            if (v != null && !v.isEmpty()) {
                signData.append(k).append('=').append(URLEncoder.encode(v, StandardCharsets.US_ASCII)).append('&');
            }
        });
        signData.setLength(signData.length() - 1);
        return VNPAYUtils.hmacSHA512(hashSecret, signData.toString()).equalsIgnoreCase(vnp_SecureHash);
    }
}
