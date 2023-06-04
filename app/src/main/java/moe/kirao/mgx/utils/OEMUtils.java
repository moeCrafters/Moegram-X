package moe.kirao.mgx.utils;

import android.annotation.SuppressLint;
import android.os.Build;
import android.text.TextUtils;

import java.lang.reflect.Field;

public class OEMUtils {
  private static Boolean isMIUI;

  public static boolean isMIUI() {
    if (isMIUI != null) {
      return isMIUI;
    }
    isMIUI = !TextUtils.isEmpty(SystemUtils.getSystemProperty("ro.miui.ui.version.name"));
    return isMIUI;
  }

  public static final int ONEUI_40 = 40_000;
  private static int oneUIVersion;
  private static Boolean isOneUI;


  @SuppressWarnings("JavaReflectionMemberAccess")
  public static boolean isOneUI() {
    if (isOneUI != null) {
      return isOneUI;
    }
    try {
      @SuppressLint("PrivateApi")
      Field field = Build.VERSION.class.getDeclaredField("SEM_PLATFORM_INT");
      field.setAccessible(true);
      int semPlatformInt = (int) field.get(null);
      if (semPlatformInt < 100_000) {
        return false; // Samsung Experience
      }
      oneUIVersion = semPlatformInt - 90_000;
      isOneUI = true;
    } catch (Exception e) {
      isOneUI = false;
    }
    return isOneUI;
  }

  public static boolean hasBuiltInClipboardToasts() {
    return isOneUI() && getOneUIVersion() == ONEUI_40;
  }

  public static int getOneUIVersion() {
    if (!isOneUI()) {
      return 0;
    }
    return oneUIVersion;
  }
}