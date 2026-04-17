package com.example.nhom3.project.common.utils;

public class UserLevelUtil {

    public static String mapLevel(Integer point) {
        if (point == null) return "BRONZE";
        if (point < 1000) return "BRONZE";
        if (point < 5000) return "SILVER";
        if (point < 10000) return "GOLD";
        return "DIAMOND";
    }
}
