package moe.kirao.mgx.utils;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.widget.Toast;

import androidx.annotation.StringRes;

import org.drinkless.tdlib.TdApi;
import org.thunderdog.challegram.FileProvider;
import org.thunderdog.challegram.Log;
import org.thunderdog.challegram.config.Config;
import org.thunderdog.challegram.tool.UI;

import java.io.File;
public class SystemUtils {
  @SuppressLint("PrivateApi")
  public static String getSystemProperty(String key) {
    try {
      Class<?> props = Class.forName("android.os.SystemProperties");
      return (String) props.getMethod("get", String.class).invoke(null, key);
    } catch (Exception ignore) {
      //
    }
    return null;
  }

  public static boolean shouldShowClipboardToast() {
    return ((Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) || OEMUtils.isMIUI()) && ((Build.VERSION.SDK_INT < Build.VERSION_CODES.S) || !OEMUtils.hasBuiltInClipboardToasts());
  }

  public static void copyFileToClipboard (TdApi.File file, @StringRes int toast) {
    try {
      ClipboardManager clipboard = (ClipboardManager) UI.getAppContext().getSystemService(Context.CLIPBOARD_SERVICE);
      if (clipboard != null) {
        Uri uri = FileProvider.getUriForFile(UI.getAppContext(), Config.FILE_PROVIDER_AUTHORITY, new File(file.local.path));
        ClipData clip = ClipData.newUri(UI.getAppContext().getContentResolver(), "image", uri);
        clipboard.setPrimaryClip(clip);
        if (shouldShowClipboardToast()) {
          UI.showToast(toast, Toast.LENGTH_SHORT);
        }
      }
    } catch (Exception e) {
      Log.e(e);
    }
  }
}
