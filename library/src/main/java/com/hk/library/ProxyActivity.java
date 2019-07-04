package com.hk.library;

import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import java.util.List;

/**
 * Created by hk on 2019/7/4.
 */
public class ProxyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proxy);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startToMain();
            }
        }, 3000);
    }

    private void startToMain() {
        Intent intent = new Intent(Intent.ACTION_MAIN, null);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);

        intent.addCategory(Intent.CATEGORY_LAUNCHER);

        List<ResolveInfo> packageInfos = getPackageManager().queryIntentActivities(intent, 0);
        String currentPkg = getPackageName();
        for (ResolveInfo info : packageInfos) {

            String launcherActivityName = info.activityInfo.name;
            String packageName = info.activityInfo.packageName;
            if (packageName.equals(currentPkg)) {
                intent.setComponent(new ComponentName(packageName, launcherActivityName));
                startActivity(intent);
            }
        }
        finish();
    }
}