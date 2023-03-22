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

public class InterfaceSettingsMoexController extends RecyclerViewController<Void> implements View.OnClickListener, ViewController.SettingsIntDelegate {
  private SettingsAdapter adapter;

  public InterfaceSettingsMoexController (Context context, Tdlib tdlib) {
    super(context, tdlib);
  }

  @Override public CharSequence getName () {
    return Lang.getString(R.string.MoexInterfaceSettings);
  }

  @Override public void onClick (View v) {
    int id = v.getId();
    switch (id) {
      case R.id.btn_disableCameraButton:
        MoexSettings.instance().toggleDisableCameraButton();
        adapter.updateValuedSettingById(R.id.btn_disableCameraButton);
        break;
      case R.id.btn_disableRecordButton:
        MoexSettings.instance().toggleDisableRecordButton();
        adapter.updateValuedSettingById(R.id.btn_disableRecordButton);
        break;
      case R.id.btn_disableCommandsButton:
        MoexSettings.instance().toggleDisableCommandsButton();
        adapter.updateValuedSettingById(R.id.btn_disableCommandsButton);
        break;
      case R.id.btn_disableSendAsButton:
        MoexSettings.instance().toggleDisableSendAsButton();
        adapter.updateValuedSettingById(R.id.btn_disableSendAsButton);
        break;
    }
  }

  @Override public void onApplySettings (int id, SparseIntArray result) {

  }

  @Override public int getId () {
    return R.id.controller_moexSettings;
  }

  @Override protected void onCreateView (Context context, CustomRecyclerView recyclerView) {
    adapter = new SettingsAdapter(this) {
      @Override protected void setValuedSetting (ListItem item, SettingView view, boolean isUpdate) {
        view.setDrawModifier(item.getDrawModifier());
        switch (item.getId()) {
          case R.id.btn_disableCameraButton:
            view.getToggler().setRadioEnabled(MoexSettings.instance().isDisableCameraButton(), isUpdate);
            break;
          case R.id.btn_disableRecordButton:
            view.getToggler().setRadioEnabled(MoexSettings.instance().isDisableRecordButton(), isUpdate);
            break;
          case R.id.btn_disableCommandsButton:
            view.getToggler().setRadioEnabled(MoexSettings.instance().isDisableCommandsButton(), isUpdate);
            break;
          case R.id.btn_disableSendAsButton:
            view.getToggler().setRadioEnabled(MoexSettings.instance().isDisableSendAsButton(), isUpdate);
            break;
        }
      }
    };

    ArrayList<ListItem> items = new ArrayList<>();
    items.add(new ListItem(ListItem.TYPE_EMPTY_OFFSET_SMALL));
    items.add(new ListItem(ListItem.TYPE_HEADER, 0, 0, R.string.MoexHideButtons));
    items.add(new ListItem(ListItem.TYPE_RADIO_SETTING, R.id.btn_disableCommandsButton, 0, R.string.DisableCommandsButton));
    items.add(new ListItem(ListItem.TYPE_SEPARATOR_FULL));
    items.add(new ListItem(ListItem.TYPE_RADIO_SETTING, R.id.btn_disableCameraButton, 0, R.string.DisableCameraButton));
    items.add(new ListItem(ListItem.TYPE_SHADOW_BOTTOM));
    items.add(new ListItem(ListItem.TYPE_DESCRIPTION, 0, 0, Lang.getMarkdownString(this, R.string.HideCameraButtonInfo), false));
    items.add(new ListItem(ListItem.TYPE_SHADOW_TOP));
    items.add(new ListItem(ListItem.TYPE_RADIO_SETTING, R.id.btn_disableRecordButton, 0, R.string.DisableRecordButton));
    items.add(new ListItem(ListItem.TYPE_SEPARATOR_FULL));
    items.add(new ListItem(ListItem.TYPE_RADIO_SETTING, R.id.btn_disableSendAsButton, 0, R.string.DisableSendAsButton));
    items.add(new ListItem(ListItem.TYPE_SHADOW_BOTTOM));

    adapter.setItems(items, true);
    recyclerView.setAdapter(adapter);
  }
}