package com.sfan.lib.amap;

import android.content.Context;

import com.amap.api.maps.CoordinateConverter;
import com.amap.api.maps.model.LatLng;

import java.text.DecimalFormat;

/**
 * Created by zhazhiyong on 2018/6/7.
 * 坐标系
 */

public class CoordinateUtils {

    /**
     * 其他坐标系转换高德坐标系
     *
     * @param latLng 待转换坐标点 LatLng类型
     * @param type   待转换坐标类型
     * @return 高德坐标点
     */
    public static LatLng getAMapLatLng(Context context, LatLng latLng, CoordinateConverter.CoordType type) {
        // 执行转换操作
        return new CoordinateConverter(context).from(type).coord(latLng).convert();
    }

    /**
     * 两点经纬度计算角度
     *
     * @param lat1 A点纬度Y
     * @param lon1 A点经度X
     * @param lat2 B点纬度Y
     * @param lon2 B点经度X
     * @return 角度
     */
    public static double getAngle(double lat1, double lon1, double lat2, double lon2) {
        try {
            // 保留5位小数
            DecimalFormat df5 = new DecimalFormat("#.00000");
            lat1 = Double.parseDouble(df5.format(lat1));
            lon1 = Double.parseDouble(df5.format(lon1));
            lat2 = Double.parseDouble(df5.format(lat2));
            lon2 = Double.parseDouble(df5.format(lon2));
        } catch (Exception e) {
        }
        double y = Math.sin(lon2 - lon1) * Math.cos(lat2);
        double x = Math.cos(lat1) * Math.sin(lat2) - Math.sin(lat1) * Math.cos(lat2) * Math.cos(lon2 - lon1);
        double angle = Math.atan2(y, x);
        angle = Math.toDegrees(angle) - 90;
        if (angle < 0)
            angle += 360;
        return 360 - angle;
    }

}
