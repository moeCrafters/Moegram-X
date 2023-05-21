package moe.kirao.mgx.ui;

import android.content.Context;
import android.view.View;

import org.thunderdog.challegram.R;
import org.thunderdog.challegram.component.base.SettingView;
import org.thunderdog.challegram.core.Lang;
import org.thunderdog.challegram.telegram.Tdlib;
import org.thunderdog.challegram.ui.ListItem;
import org.thunderdog.challegram.ui.RecyclerViewController;
import org.thunderdog.challegram.ui.SettingsAdapter;
import org.thunderdog.challegram.v.CustomRecyclerView;

import java.util.ArrayList;

import moe.kirao.mgx.MoexConfig;

public class ChatsSettingsMoexController extends RecyclerViewController<Void> implements View.OnClickListener {
  private SettingsAdapter adapter;

  public ChatsSettingsMoexController (Context context, Tdlib tdlib) {
    super(context, tdlib);
  }

  @Override public CharSequence getName () {
    return Lang.getString(R.string.ChatsMoexSettings);
  }

  @Override public void onClick (View v) {
    int id = v.getId();
    switch (id) {
      case R.id.btn_disableStickerTimestamp:
        MoexConfig.instance().toggleDisableStickerTimestamp();
        adapter.updateValuedSettingById(R.id.btn_disableStickerTimestamp);
        break;
      case R.id.btn_roundedStickers:
        MoexConfig.instance().toggleRoundedStickers();
        adapter.updateValuedSettingById(R.id.btn_roundedStickers);
        break;
      case R.id.btn_IncreaseRecents:
        MoexConfig.instance().toggleIncreaseRecents();
        adapter.updateValuedSettingById(R.id.btn_IncreaseRecents);
        break;
      case R.id.btn_ReorderStickers:
        MoexConfig.instance().toggleEnableReorderStickers();
        adapter.updateValuedSettingById(R.id.btn_ReorderStickers);
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
          case R.id.btn_disableStickerTimestamp:
            view.getToggler().setRadioEnabled(MoexConfig.hideStickerTimestamp, isUpdate);
            break;
          case R.id.btn_roundedStickers:
            view.getToggler().setRadioEnabled(MoexConfig.roundedStickers, isUpdate);
            break;
          case R.id.btn_IncreaseRecents:
            view.getToggler().setRadioEnabled(MoexConfig.increaseRecents, isUpdate);
            break;
          case R.id.btn_ReorderStickers:
            view.getToggler().setRadioEnabled(MoexConfig.reorderStickers, isUpdate);
            break;
        }
      }
    };

    ArrayList<ListItem> items = new ArrayList<>();
    items.add(new ListItem(ListItem.TYPE_EMPTY_OFFSET_SMALL));
    items.add(new ListItem(ListItem.TYPE_HEADER, 0, 0, R.string.MoexStickersCount));
    items.add(new ListItem(ListItem.TYPE_SHADOW_TOP));
    items.add(new ListItem(ListItem.TYPE_RADIO_SETTING, R.id.btn_disableStickerTimestamp, 0, R.string.DisableStickerTimestamp));
    items.add(new ListItem(ListItem.TYPE_SEPARATOR_FULL));
    items.add(new ListItem(ListItem.TYPE_RADIO_SETTING, R.id.btn_roundedStickers, 0, R.string.RoundedStickers));
    items.add(new ListItem(ListItem.TYPE_SEPARATOR_FULL));
    items.add(new ListItem(ListItem.TYPE_RADIO_SETTING, R.id.btn_IncreaseRecents, 0, R.string.IncreaseRecents));
    items.add(new ListItem(ListItem.TYPE_SEPARATOR_FULL));
    items.add(new ListItem(ListItem.TYPE_RADIO_SETTING, R.id.btn_ReorderStickers, 0, R.string.ReorderStickers));
    items.add(new ListItem(ListItem.TYPE_SHADOW_BOTTOM));
    items.add(new ListItem(ListItem.TYPE_DESCRIPTION, 0, 0, Lang.getMarkdownString(this, R.string.ReorderStickersInfo), false));

    adapter.setItems(items, true);
    recyclerView.setAdapter(adapter);
  }
}
