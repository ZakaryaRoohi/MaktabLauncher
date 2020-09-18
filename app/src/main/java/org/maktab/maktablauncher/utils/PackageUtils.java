package org.maktab.maktablauncher.utils;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;

import java.util.List;

public class PackageUtils {

    public static List<ResolveInfo> getLauncherActivities(Context context) {
        PackageManager packageManager = context.getPackageManager();
        Intent launcherActivityIntent = getLauncherIntent();

        List<ResolveInfo> activities =
                packageManager.queryIntentActivities(launcherActivityIntent, 0);

        return activities;
    }

    public static Intent getLauncherIntent() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);

        return intent;
    }

    public static ComponentName getComponentName(ResolveInfo resolveInfo) {
        String packageName = resolveInfo.activityInfo.applicationInfo.packageName;
        String activityName = resolveInfo.activityInfo.name;

        return new ComponentName(packageName, activityName);
    }
}
