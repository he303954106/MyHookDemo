package com.hk.library;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import java.lang.reflect.Field;

/**
 * Created by hk on 2019/7/4.
 */
public class InstrumentationProxy extends Instrumentation {

    public static final String TAG = "InstrumentationProxy";

    private boolean hasHook = false;

    public Instrumentation target;

    //通过构造函数来传递对象
    public InstrumentationProxy(Instrumentation mInstrumentation) {
        target = mInstrumentation;
    }


    @Override
    public void callActivityOnCreate(final Activity activity, Bundle icicle) {
        // TODO Auto-generated method stub
        Log.d(TAG, " callActivityOnCreate: activity" + activity.getIntent().getAction());
        if (isMainActivity(activity) && !hasHook) {
            changeActivityHasCalled(activity);
            activity.startActivity(new Intent(activity, ProxyActivity.class));
            if (!activity.isFinishing()) {
                activity.finish();// TODO: 2019/7/4 AppCompatActivity crash
            }
//            if(activity instanceof FragmentActivity){
//                FragmentActivity fragmentActivity = (FragmentActivity) activity;
//                FragmentManager fragmentManager = fragmentActivity.getSupportFragmentManager();
//
//            }
            hasHook = true;
        } else {
            target.callActivityOnCreate(activity, icicle);
        }
    }

    private boolean isMainActivity(Activity activity) {
        return Intent.ACTION_MAIN.equals(activity.getIntent().getAction()) &&
                activity.getIntent().getCategories().contains(Intent.CATEGORY_LAUNCHER);

    }

    private void changeActivityHasCalled(Activity activity) {
        Field nameField;
        try {
            Class<? extends Activity> clazz = Activity.class;// 获取到对象对应的class对象
            nameField = clazz.getDeclaredField("mCalled");
            // 获取私有成员变量:name
            nameField.setAccessible(true);// 设置操作权限为true
            nameField.set(activity, true);
        } catch (NoSuchFieldException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


}