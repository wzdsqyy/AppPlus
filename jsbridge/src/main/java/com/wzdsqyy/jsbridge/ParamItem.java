package com.wzdsqyy.jsbridge;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.wzdsqyy.jsbridge.annotation.Param;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Iterator;

/**
 * 对应{@link Param}注解标注的参数
 */
class ParamItem extends BaseParamItem {


    public ParamItem(String paramKey, Class paramClass) {
        super(paramClass, paramKey);
    }

    protected void onReceiveKeyValue(RequestResponseBuilder requestResponseBuilder, String key, Object value) {
        requestResponseBuilder.putValue(key, value);
    }

    protected JSONObject getJson(RequestResponseBuilder requestResponseBuilder) {
        return requestResponseBuilder.getValues();
    }

    @Override
    public Object convertJson2ParamValue(RequestResponseBuilder requestResponseBuilder) {
        if (requestResponseBuilder == null || requestResponseBuilder.getValues() == null) {
            return null;
        }
        JSONObject jsonObject = getJson(requestResponseBuilder);
        if(jsonObject==null){
            return null;
        }
        if (!isObjectDirectPut2Json(paramType)) {





            try {
                JSONObject value = !TextUtils.isEmpty(paramKey) ? (JSONObject) jsonObject.opt(paramKey) : jsonObject;
                if (value == null) {
                    return null;
                }
                Object instance = paramType.newInstance();
                Field[] fields = paramType.getDeclaredFields();
                for (Field field : fields
                        ) {
                    Param p = field.getAnnotation(Param.class);
                    if (p != null) {
                            /*可以访问不可以访问的变量*/
                        field.setAccessible(true);
                        field.set(instance, value.opt(p.value()));
                    }
                }
                return instance;
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        } else {
            return jsonObject.opt(paramKey);
        }
        return null;
    }

    @Override
    public void convertParamValue2Json(RequestResponseBuilder requestResponseBuilder, Object obj) {

        if (requestResponseBuilder == null || obj == null) {
            return;
        }
        if (!isObjectDirectPut2Json(obj)) {
            JSONObject json = convertObjectFileds2Json(obj);
            if (json == null) {
                return;
            }
            if (!TextUtils.isEmpty(paramKey)) {
                onReceiveKeyValue(requestResponseBuilder, paramKey, json);
            } else {
                Iterator<String> iterator = json.keys();
                String key = null;
                while (iterator.hasNext()) {
                    key = iterator.next();
                    onReceiveKeyValue(requestResponseBuilder, key, json.opt(key));
                }
            }

        } else {
            onReceiveKeyValue(requestResponseBuilder, paramKey, obj);
        }
    }

    private JSONObject convertObjectFileds2Json(@NonNull Object obj) {
        Class cl = obj.getClass();
        Field[] fields = cl.getDeclaredFields();
        if (fields.length == 0) {
            return null; /*说明当前类的不包含任何属性*/
        }
        JSONObject objectParamJson = null;
        Object inst;
        String jsonName;
        /*属性用Param进行了标注*/
        Param filedAnnoByParam;
        for (Field field : fields) {
            if (Modifier.isStatic(field.getModifiers()) || Modifier.isFinal(field.getModifiers()) || field.isEnumConstant()) {
                continue;/*final或static类型的属性或枚举类型中的枚举常量不解析*/
            }
            filedAnnoByParam = field.getAnnotation(Param.class);
            if (filedAnnoByParam != null) {
                jsonName = filedAnnoByParam.value();                                                    /*可以访问不可以访问的变量*/
            } else {
                jsonName = field.getName();
            }
            field.setAccessible(true);
            try {
                inst = field.get(obj);
                if(inst==null){
                    continue;
                }
                if (objectParamJson == null) {
                    objectParamJson = new JSONObject();
                }
                if (isObjectDirectPut2Json(inst)) {
                    objectParamJson.put(jsonName, inst);
                } else {
                    JSONObject filedJson = convertObjectFileds2Json(inst); /*检查当前的属性是否还包含着属性*/
                    if(filedJson==null){
                        continue;
                    }
                    objectParamJson.put(jsonName, filedJson);
                }
            } catch (Exception e) {
               throw new JSBridgeException("参数转化json中发生错误!");
            }
        }
        return objectParamJson==null?null:objectParamJson.length() == 0 ? null : objectParamJson;

    }

    /**
     * 该对象是否可以直接往json中放
     *
     * @param type
     * @return
     */
    private boolean isObjectDirectPut2Json(Class type) {
        return (type == String.class || type.isPrimitive() || type == JSONArray.class || type == JSONObject.class);
    }

    /**
     * 该对象是否可以直接往json中放
     *
     * @param object
     * @return
     */
    private boolean isObjectDirectPut2Json(Object object) {
        if (object instanceof String || object instanceof Integer || object instanceof Double || object instanceof Long ||
                object instanceof Boolean || object instanceof JSONArray || object instanceof JSONObject) {
            return true;
        }
        return false;
    }
}
