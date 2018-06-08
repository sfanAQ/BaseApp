package com.sfan.lib.amap;

import android.content.Context;
import android.util.Pair;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.model.BitmapDescriptor;
import com.amap.api.maps.model.Circle;
import com.amap.api.maps.model.CircleOptions;
import com.amap.api.maps.model.HeatmapTileProvider;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.LatLngBounds;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.Polygon;
import com.amap.api.maps.model.PolygonOptions;
import com.amap.api.maps.model.Polyline;
import com.amap.api.maps.model.PolylineOptions;
import com.amap.api.maps.model.TileOverlay;
import com.amap.api.maps.model.TileOverlayOptions;
import com.amap.api.maps.utils.SpatialRelationUtil;
import com.amap.api.maps.utils.overlay.SmoothMoveMarker;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * Created by zzy on 2017/1/23.
 * 高德地图绘制管理
 */
public class MarkerOptionsManager {

    private Context mContext;
    private AMap mAMap;

    public MarkerOptionsManager(Context context, AMap aMap) {
        this.mContext = context;
        this.mAMap = aMap;
    }

    /**
     * 绘制点
     *
     * @param point     坐标点（经纬度值）
     * @param title     标题
     * @param snippet   内容
     * @param draggable 是否可拖拽
     * @param flat      是否平贴地面
     * @param bitmap    图标
     * @param u         v anchor默认0.5f,1.0f
     * @return
     */
    public Marker addMarker(LatLng point, String title, String snippet, boolean draggable, boolean flat, BitmapDescriptor bitmap, float u, float v) {
        if (mAMap == null) return null;
        MarkerOptions markerOption = new MarkerOptions().position(point).title(title).snippet(snippet).draggable(draggable).setFlat(flat).anchor(u, v).icon(bitmap);
        return mAMap.addMarker(markerOption);
    }

    /**
     * 绘制线
     *
     * @param points     线段拐点集合
     * @param color      线段颜色
     * @param width      线段宽度，单位像素
     * @param dottedLine 是否画虚线
     */
    public Polyline addPolyline(Iterable<LatLng> points, int color, float width, boolean dottedLine) {
        if (mAMap == null) return null;
        PolylineOptions polylineOptions = new PolylineOptions().addAll(points).color(color).width(width).setDottedLine(dottedLine);
        return mAMap.addPolyline(polylineOptions);
    }

    /**
     * 绘制圆
     *
     * @param center      圆心
     * @param radius      半径，单位米
     * @param fillColor   填充色
     * @param strokeColor 边线颜色
     * @param strokeWidth 边线宽度，单位像素
     * @return
     */
    public Circle addCircle(LatLng center, double radius, int fillColor, int strokeColor, float strokeWidth) {
        if (mAMap == null) return null;
        CircleOptions circleOptions = new CircleOptions().center(center).radius(radius).fillColor(fillColor).strokeColor(strokeColor).strokeWidth(strokeWidth);
        return mAMap.addCircle(circleOptions);
    }

    /**
     * 绘制多边形
     *
     * @param points      多边形各个角
     * @param fillColor   填充色
     * @param strokeColor 边线颜色
     * @param strokeWidth 边线宽度，单位像素
     * @return
     */
    public Polygon addPolygon(Iterable<LatLng> points, int fillColor, int strokeColor, float strokeWidth) {
        if (mAMap == null) return null;
        PolygonOptions polygonOptions = new PolygonOptions().addAll(points).fillColor(fillColor).strokeColor(strokeColor).strokeWidth(strokeWidth);
        return mAMap.addPolygon(polygonOptions);
    }

    /**
     * 绘制热力图图层
     *
     * @param points 热力点坐标列表
     * @return
     */
    public TileOverlay addTileOverlay(LatLng[] points) {
        if (mAMap == null) return null;
        // 构建热力图 HeatmapTileProvider
        HeatmapTileProvider.Builder builder = new HeatmapTileProvider.Builder();
        builder.data(Arrays.asList(points))// 设置热力图绘制的数据，data 或 weightedData接口必须设置其中之一
                .gradient(HeatmapTileProvider.DEFAULT_GRADIENT); // 设置热力图渐变，有默认值 DEFAULT_GRADIENT，可不设置该接口
        // Gradient 的设置可见参考手册
        // 构造热力图对象
        HeatmapTileProvider heatmapTileProvider = builder.build();
        // 初始化 TileOverlayOptions
        TileOverlayOptions tileOverlayOptions = new TileOverlayOptions();
        tileOverlayOptions.tileProvider(heatmapTileProvider); // 设置瓦片图层的提供者
        // 向地图上添加 TileOverlayOptions 类对象
        return mAMap.addTileOverlay(tileOverlayOptions);
    }

    /**
     * 点平滑移动
     *
     * @param smoothMarker 移动的点
     * @param points       轨迹
     * @param duration     耗时 秒
     */
    public boolean smoothMove(SmoothMoveMarker smoothMarker, List<LatLng> points, int duration) {
        if (smoothMarker == null || points == null || points.isEmpty()) return false;
        LatLng drivePoint = points.get(0);
        if (drivePoint.equals(smoothMarker.getPosition())) {
            Pair<Integer, LatLng> pair = SpatialRelationUtil.calShortestDistancePoint(points, drivePoint);
            points.set(pair.first, drivePoint);
            List<LatLng> subList = points.subList(pair.first, points.size());
            // 设置滑动的轨迹左边点
            smoothMarker.setPoints(subList);
        } else {
            smoothMarker.setPoints(points);
        }
        // 设置滑动的总时间 秒s
        smoothMarker.setTotalDuration(duration);
        // 开始滑动
        smoothMarker.startSmoothMove();
        return true;
    }

    /**
     * 缩放地图，使所有地图点都在合适的视野内
     *
     * @param points  坐标集合
     * @param padding 边距，单位像素
     */
    public boolean zoomToSpan(Iterable<LatLng> points, int padding) {
        if (mAMap == null) return false;
        Iterator<LatLng> iterator = points.iterator();
        double minLat = 90, minLng = 180;// 左下角，初始值最大
        double maxLat = 0, maxLng = 0;// 右上角，初始值最小
        while (iterator.hasNext()) {
            LatLng latLng = iterator.next();
            minLat = minLat < latLng.latitude ? minLat : latLng.latitude;
            minLng = minLng < latLng.longitude ? minLng : latLng.longitude;
            maxLat = maxLat > latLng.latitude ? maxLat : latLng.latitude;
            maxLng = maxLng > latLng.longitude ? maxLng : latLng.longitude;
        }
        if (maxLat > minLat && maxLng > minLng) {
            LatLngBounds bounds = new LatLngBounds(new LatLng(minLat, minLng), new LatLng(maxLat, maxLng));
            mAMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, padding));
            return true;
        }
        return false;
    }

}