package moe.kirao.mgx.ui;

import android.content.Context;
import android.util.SparseIntArray;
import android.view.View;

import org.thunderdog.challegram.R;
import org.thunderdog.challegram.component.base.SettingView;
import org.thunderdog.challegram.core.Lang;
import org.thunderdog.challegram.navigation.ViewController;
import org.thunderdog.challegram.telegram.Tdlib;
import org.thunderdog.challegram.ui.ListItem;
import org.thunderdog.challegram.ui.RecyclerViewController;
import org.thunderdog.challegram.ui.SettingsAdapter;
import org.thunderdog.challegram.v.CustomRecyclerView;

import java.util.ArrayList;

import moe.kirao.mgx.MoexSettings;

public class ChatsSettingsMoexController extends RecyclerViewController<Void> implements View.OnClickListener, ViewController.SettingsIntDelegate {
  private SettingsAdapter adapter;

  public ChatsSettingsMoexController (Context context, Tdlib tdlib) {
    super(context, tdlib);
  }

  @Override public CharSequence getName () {
    return Lang.getString(R.string.MoexChatsSettings);
  }

  @Override public void onClick (View v) {
    int id = v.getId();
    switch (id) {
      case R.id.btn_recentStickersCount:
        int count = MoexSettings.instance().getRecentStickersCount();
        showSettings(id, new ListItem[] {new ListItem(ListItem.TYPE_RADIO_OPTION, R.id.btn_recentStickers20, 0, "20", R.id.btn_recentStickersCount, count == 20), new ListItem(ListItem.TYPE_RADIO_OPTION, R.id.btn_recentStickers40, 0, "40", R.id.btn_recentStickersCount, count == 40), new ListItem(ListItem.TYPE_RADIO_OPTION, R.id.btn_recentStickers60, 0, "60", R.id.btn_recentStickersCount, count == 60), new ListItem(ListItem.TYPE_RADIO_OPTION, R.id.btn_recentStickers80, 0, "80", R.id.btn_recentStickersCount, count == 80), new ListItem(ListItem.TYPE_RADIO_OPTION, R.id.btn_recentStickers100, 0, "100", R.id.btn_recentStickersCount, count == 100)}, this);
        break;
      case R.id.btn_disableStickerTimestamp:
        MoexSettings.instance().toggleDisableStickerTimestamp();
        adapter.updateValuedSettingById(R.id.btn_disableStickerTimestamp);
        break;
    }
  }

  @Override public void onApplySettings (int id, SparseIntArray result) {
    switch (id) {
      case R.id.btn_recentStickersCount:
        int count;
        switch (result.valueAt(0)) {
          case R.id.btn_recentStickers40:
            count = 40;
            break;
          case R.id.btn_recentStickers60:
            count = 60;
            break;
          case R.id.btn_recentStickers80:
            count = 80;
            break;
          case R.id.btn_recentStickers100:
            count = 100;
            break;
          default:
            count = 20;
        }
        MoexSettings.instance().setRecentStickersCount(count);
        adapter.updateValuedSettingById(R.id.btn_recentStickersCount);
        break;
    }
  }

  @Override public int getId () {
    return R.id.controller_moexSettings;
  }

  @Override protected void onCreateView (Context context, CustomRecyclerView recyclerView) {
    adapter = new SettingsAdapter(this) {
      @Override protected void setValuedSetting (ListItem item, SettingView view, boolean isUpdate) {
        view.setDrawModifier(item.getDrawModifier());
        switch (item.getId()) {
          case R.id.btn_recentStickersCount:
            view.setData("" + MoexSettings.instance().getRecentStickersCount());
            break;
          case R.id.btn_disableStickerTimestamp:
            view.getToggler().setRadioEnabled(MoexSettings.instance().isDisableStickerTimestamp(), isUpdate);
            break;
        }
      }
    };

    ArrayList<ListItem> items = new ArrayList<>();
    items.add(new ListItem(ListItem.TYPE_EMPTY_OFFSET_SMALL));
    items.add(new ListItem(ListItem.TYPE_HEADER, 0, 0, R.string.MoexStickersCount));
    items.add(new ListItem(ListItem.TYPE_VALUED_SETTING_COMPACT, R.id.btn_recentStickersCount, 0, R.string.RecentStickersCount));
    items.add(new ListItem(ListItem.TYPE_SEPARATOR_FULL));
    items.add(new ListItem(ListItem.TYPE_RADIO_SETTING, R.id.btn_disableStickerTimestamp, 0, R.string.DisableStickerTimestamp));
    items.add(new ListItem(ListItem.TYPE_SHADOW_BOTTOM));

    adapter.setItems(items, true);
    recyclerView.setAdapter(adapter);
  }
}
