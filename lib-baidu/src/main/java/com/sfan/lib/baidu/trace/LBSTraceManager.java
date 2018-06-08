package com.sfan.lib.baidu.trace;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.baidu.trace.LBSTraceClient;
import com.baidu.trace.Trace;
import com.baidu.trace.api.entity.AddEntityRequest;
import com.baidu.trace.api.entity.EntityListRequest;
import com.baidu.trace.api.entity.FilterCondition;
import com.baidu.trace.api.entity.OnEntityListener;
import com.baidu.trace.api.entity.UpdateEntityRequest;
import com.baidu.trace.api.track.HistoryTrackRequest;
import com.baidu.trace.api.track.OnTrackListener;
import com.baidu.trace.api.track.SupplementMode;
import com.baidu.trace.model.CoordType;
import com.baidu.trace.model.OnCustomAttributeListener;
import com.baidu.trace.model.OnTraceListener;
import com.baidu.trace.model.ProcessOption;
import com.baidu.trace.model.SortType;

import java.util.Map;

/**
 * Created by zhazhiyong on 2018/6/6.
 * 百度鹰眼轨迹管理
 */

public final class LBSTraceManager {

    private static LBSTraceManager mInstance = null;

    private LBSTraceManager() {
    }

    private LBSTraceClient mTraceClient = null;

    public static LBSTraceManager getInstance() {
        if (mInstance == null) {
            synchronized (LBSTraceManager.class) {
                mInstance = new LBSTraceManager();
            }
        }
        return mInstance;
    }

    /**
     * 初始化
     *
     * @param context
     * @param gatherInterval    设置定位周期(单位:秒)
     * @param packInterval      设置打包回传周期(单位:秒)
     * @param traceListener     轨迹服务监听器
     * @param attributeListener Trace自定义字段更新
     */
    public void init(Context context, int gatherInterval, int packInterval, OnTraceListener traceListener, OnCustomAttributeListener attributeListener) {
        Log.d("LBSTraceClient", "LBSTraceManager----init初始化");
        mTraceClient = new LBSTraceClient(context);
        // 设置定位周期以及打包回传周期
        mTraceClient.setInterval(gatherInterval, packInterval);
        // 初始化轨迹服务监听器
        mTraceClient.setOnTraceListener(traceListener);
        // Trace自定义字段更新
        mTraceClient.setOnCustomAttributeListener(attributeListener);
    }

    /**
     * 释放资源
     */
    public void destroy() {
        Log.d("LBSTraceClient", "LBSTraceManager----destroy释放资源");
        mTraceClient = null;
    }

    /**
     * 开启鹰眼轨迹服务
     *
     * @param trace serviceId 服务ID
     *              entityName 开启服务唯一账号
     *              isNeedObjectStorage 是否需要对象存储服务，默认为：false，关闭对象存储服务。注：鹰眼 Android SDK v3.0以上版本支持随轨迹上传图像等对象数据，若需使用此功能，该参数需设为 true，且需导入bos-android-sdk-1.0.2.jar。
     */
    public int startTrace(Trace trace) {
        if (trace == null) return -1;
        String entityName = trace.getEntityName();
        if (TextUtils.isEmpty(entityName) || entityName.length() < 6) {// 账号无效
            return -1;
        }
        // 开启鹰眼轨迹服务
        Log.d("LBSTraceClient", "LBSTraceManager----startTrace开启鹰眼轨迹服务...");
        mTraceClient.startTrace(trace, null);
        return 0;
    }

    /**
     * 停止鹰眼轨迹服务
     */
    public int stopTrace(Trace trace) {
        if (trace == null) return -1;
        Log.d("LBSTraceClient", "LBSTraceManager----stopTrace停止服务...");
        mTraceClient.stopTrace(trace, null);
        Log.d("LBSTraceClient", "LBSTraceManager----stopTrace停止服务成功");
        return 0;
    }

    /**
     * 开启采集
     */
    public void startGather() {
        Log.d("LBSTraceClient", "LBSTraceManager----startGather开启采集...");
        mTraceClient.startGather(null);
        Log.d("LBSTraceClient", "LBSTraceManager----startGather开启采集成功");
    }

    /**
     * 停止采集
     */
    public void stopGather() {
        Log.d("LBSTraceClient", "LBSTraceManager----stopGather停止采集...");
        mTraceClient.stopGather(null);
        Log.d("LBSTraceClient", "LBSTraceManager----stopGather停止采集成功");
    }

    /**
     * 新增Entity
     */
    public void addEntity(int tag, long serviceId, String entityName, Map<String, String> entityMap, OnEntityListener entityListener) {
        Log.d("LBSTraceClient", "LBSTraceManager----createEntity创建Entity");
        AddEntityRequest request = new AddEntityRequest(tag, serviceId);
        request.setEntityName(entityName);
        // Entity自定义字段
        request.setColumns(entityMap);
        mTraceClient.addEntity(request, entityListener);
    }

    /**
     * 更新Entity
     */
    public void updateEntity(int tag, long serviceId, String entityName, Map<String, String> entityMap, OnEntityListener entityListener) {
        Log.d("LBSTraceClient", "LBSTraceManager----updateEntity更新Entity");
        UpdateEntityRequest request = new UpdateEntityRequest(tag, serviceId);
        request.setEntityName(entityName);
        // Entity自定义字段
        request.setColumns(entityMap);
        mTraceClient.updateEntity(request, entityListener);
    }

    /**
     * 查询实时位置
     *
     * @param tag             - 请求标识
     * @param serviceId       - 轨迹服务ID
     * @param filterCondition - 过滤条件
     * @param coordTypeOutput - 返回结果坐标类型
     * @param pageIndex       - 分页索引
     * @param pageSize        - 分页大小
     */
    public void queryEntityList(int tag, long serviceId, FilterCondition filterCondition, CoordType coordTypeOutput, int pageIndex, int pageSize, OnEntityListener entityListener) {
        Log.d("LBSTraceClient", "LBSTraceManager----searchEntity查询Entity列表");
        // 查询EntityList请求参数
        EntityListRequest request = new EntityListRequest(tag, serviceId, filterCondition, coordTypeOutput, pageIndex, pageSize);
        // 查询Entity列表
        mTraceClient.queryEntityList(request, entityListener);
    }

    /**
     * 查询轨迹
     *
     * @param tag             - 请求标识
     * @param serviceId       - 轨迹服务ID
     * @param entityName      - entity标识
     * @param startTime       - 开始时间
     * @param endTime         - 结束时间
     * @param isProcessed     - 是否返回纠偏后轨迹
     * @param processOption   - 纠偏选项
     * @param supplementMode  - 里程补偿方式
     * @param sortType        - 返回结果的排序规则
     * @param coordTypeOutput - 返回结果的坐标类型
     * @param pageIndex       - 分页索引
     * @param pageSize        - 分页大小
     */
    public void queryHistoryTrack(int tag, long serviceId, String entityName, long startTime, long endTime, boolean isProcessed, ProcessOption processOption, SupplementMode supplementMode, SortType sortType, CoordType coordTypeOutput, int pageIndex, int pageSize, OnTrackListener trackListener) {
        Log.d("LBSTraceClient", "LBSTraceManager----queryHistoryTrack查询轨迹");
        // 查询历史轨迹请求参数
        HistoryTrackRequest request = new HistoryTrackRequest(tag, serviceId, entityName, startTime, endTime, isProcessed, processOption, supplementMode, sortType, coordTypeOutput, pageIndex, pageSize);
        // 查询轨迹
        mTraceClient.queryHistoryTrack(request, trackListener);
    }

}
