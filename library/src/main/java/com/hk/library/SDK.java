package com.hk.library;

import android.app.Instrumentation;
import android.util.Log;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by hk on 2019/7/4.
 */
public class SDK {
    public static final String TAG = "MyApplication";

    public static void init() {
        try {
            //获取当前的ActivityThread对象
            Class<?> activityThreadClass = Class.forName("android.app.ActivityThread");
            Method currentActivityThreadMethod = activityThreadClass.getDeclaredMethod("currentActivityThread");
            currentActivityThreadMethod.setAccessible(true);
            Object currentActivityThread = currentActivityThreadMethod.invoke(null);

            //拿到在ActivityThread类里面的原始mInstrumentation对象
            Field mInstrumentationField = activityThreadClass.getDeclaredField("mInstrumentation");
            mInstrumentationField.setAccessible(true);
            Instrumentation mInstrumentation = (Instrumentation) mInstrumentationField.get(currentActivityThread);

            //构建我们的代理对象
            Instrumentation evilInstrumentation = new InstrumentationProxy(mInstrumentation);

            //通过反射，换掉字段，注意，这里是反射的代码，不是Instrumentation里面的方法

            mInstrumentationField.set(currentActivityThread, evilInstrumentation);

        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        //做个标记，方便后面查看
        Log.d(TAG, "has init SDK ");
    }

}
