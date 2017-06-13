package com.ai.core.util.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static java.lang.StrictMath.*;

/**
 * 经纬度相关工具方法
 *
 * @author peiyu
 */
public final class GISUtils {

    /**
     * 赤道半径(单位m)
     */
    private static final double EARTH_RADIUS = 6370693.4856530580439461631130889;

    /**
     * 计算两点之间距离
     *
     * @param lon1 第一点的精度
     * @param lat1 第一点的纬度
     * @param lon2 第二点的精度
     * @param lat2 第二点的纬度
     * @return 返回的距离，单位m
     */
    public static double distance(double lon1, double lat1, double lon2, double lat2) {
        double theta = lon1 - lon2;
        double dist = sin(toRadians(lat1)) * sin(toRadians(lat2)) + cos(toRadians(lat1))
                * cos(toRadians(lat2)) * cos(toRadians(theta));
        dist = acos(dist);
        dist = dist * 6370693.4856530580439461631130889;
        return dist;
    }

    /**
     * 距离四舍五入格式转换
     *
     * @param distance 单位m
     * @return 返回格式化过的距离 大于1000m显示xx公里，
     */
    public static String distanceConvert(double distance) {
        String result = "";
        if (distance >= 1000) {
            //四舍五入
            BigDecimal bf = new BigDecimal(String.valueOf(distance / 1000)).setScale(1, RoundingMode.HALF_UP);
            result = bf.toString() + "公里";
        } else {
            //四舍五入
            BigDecimal bf = new BigDecimal(String.valueOf(distance)).setScale(0, RoundingMode.HALF_UP);
            result = bf.toString() + "米";
        }
        return result;
    }

//    public static void main(String[] args) {
//        double lon1 = 118.79747772;
//        double lat1 = 32.02710724;
//
//        double lon2 = 118.83613586;
//        double lat2 = 32.01306915;
//
//        System.out.println(distance(lat1,lon1, lat2,lon2));
//    }
}
