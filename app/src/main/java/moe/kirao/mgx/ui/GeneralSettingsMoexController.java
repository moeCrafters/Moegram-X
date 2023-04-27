package moe.kirao.mgx.ui;

import android.content.Context;
import android.view.View;

import org.thunderdog.challegram.R;
import org.thunderdog.challegram.component.base.SettingView;
import org.thunderdog.challegram.core.Lang;
import org.thunderdog.challegram.navigation.SettingsWrapBuilder;
import org.thunderdog.challegram.telegram.Tdlib;
import org.thunderdog.challegram.ui.ListItem;
import org.thunderdog.challegram.ui.RecyclerViewController;
import org.thunderdog.challegram.ui.SettingsAdapter;
import org.thunderdog.challegram.v.CustomRecyclerView;

import java.util.ArrayList;

import moe.kirao.mgx.MoexConfig;

public class GeneralSettingsMoexController extends RecyclerViewController<Void> implements View.OnClickListener {
  private SettingsAdapter adapter;

  public GeneralSettingsMoexController (Context context, Tdlib tdlib) {
    super(context, tdlib);
  }

  @Override public CharSequence getName () {
    return Lang.getString(R.string.GeneralMoexSettings);
  }

  @Override public void onClick (View v) {
    int id = v.getId();
    switch (id) {
      case R.id.btn_hidePhone:
        MoexConfig.instance().toggleHidePhoneNumber();
        adapter.updateValuedSettingById(R.id.btn_hidePhone);
        break;
      case R.id.btn_enableFeaturesButton:
        MoexConfig.instance().toggleEnableFeaturesButton();
        adapter.updateValuedSettingById(R.id.btn_enableFeaturesButton);
        break;
      case R.id.btn_showIdProfile:
        MoexConfig.instance().toggleShowIdProfile();
        adapter.updateValuedSettingById(R.id.btn_showIdProfile);
        break;
      case R.id.btn_hideMessagesBadge:
        MoexConfig.instance().toggleHideMessagesBadge();
        adapter.updateValuedSettingById(R.id.btn_hideMessagesBadge);
        break;
      case R.id.btn_headerText:
        showChangeHeader();
        break;
    }
  }

  private void showChangeHeader () {
    int headerTextOption = MoexConfig.instance().getHeaderText();
    showSettings(new SettingsWrapBuilder(R.id.btn_headerText).setRawItems(new ListItem[] {
      new ListItem(ListItem.TYPE_RADIO_OPTION, R.id.btn_headerTextChats, 0, R.string.Chats, R.id.btn_headerText, headerTextOption == MoexConfig.HEADER_TEXT_CHATS),
      new ListItem(ListItem.TYPE_RADIO_OPTION, R.id.btn_headerTextMoex, 0, R.string.moexHeaderClient, R.id.btn_headerText, headerTextOption == MoexConfig.HEADER_TEXT_MOEX),
      new ListItem(ListItem.TYPE_RADIO_OPTION, R.id.btn_headerTextUsername, 0, R.string.Username, R.id.btn_headerText, headerTextOption == MoexConfig.HEADER_TEXT_USERNAME),
      new ListItem(ListItem.TYPE_RADIO_OPTION, R.id.btn_headerTextName, 0, R.string.login_FirstName, R.id.btn_headerText, headerTextOption == MoexConfig.HEADER_TEXT_NAME),
    }).setIntDelegate((id, result) -> {
      int defaultOption = MoexConfig.instance().getHeaderText();
      int headerText = result.get(R.id.btn_headerText);
      switch (headerText) {
        case R.id.btn_headerTextChats:
          defaultOption = MoexConfig.HEADER_TEXT_CHATS;
          break;
        case R.id.btn_headerTextMoex:
          defaultOption = MoexConfig.HEADER_TEXT_MOEX;
          break;
        case R.id.btn_headerTextUsername:
          defaultOption = MoexConfig.HEADER_TEXT_USERNAME;
          break;
        case R.id.btn_headerTextName:
          defaultOption = MoexConfig.HEADER_TEXT_NAME;
          break;
      }
      MoexConfig.instance().setHeaderText(defaultOption);
      adapter.updateValuedSettingById(R.id.btn_headerText);
    }));
  }

  @Override public int getId () {
    return R.id.controller_moexSettings;
  }

  @Override protected void onCreateView (Context context, CustomRecyclerView recyclerView) {
    adapter = new SettingsAdapter(this) {
      @Override protected void setValuedSetting (ListItem item, SettingView view, boolean isUpdate) {
        view.setDrawModifier(item.getDrawModifier());
        switch (item.getId()) {
          case R.id.btn_hidePhone:
            view.getToggler().setRadioEnabled(MoexConfig.hidePhoneNumber, isUpdate);
            break;
          case R.id.btn_enableFeaturesButton:
            view.getToggler().setRadioEnabled(MoexConfig.enableTestFeatures, isUpdate);
            break;
          case R.id.btn_showIdProfile:
            view.getToggler().setRadioEnabled(MoexConfig.showId, isUpdate);
            break;
          case R.id.btn_hideMessagesBadge:
            view.getToggler().setRadioEnabled(MoexConfig.hideMessagesBadge, isUpdate);
            break;
          case R.id.btn_headerText: {
            int mode = MoexConfig.instance().getHeaderText();
            switch (mode) {
              case MoexConfig.HEADER_TEXT_CHATS:
                view.setData(R.string.Chats);
                break;
              case MoexConfig.HEADER_TEXT_MOEX:
                view.setData(R.string.moexHeaderClient);
                break;
              case MoexConfig.HEADER_TEXT_USERNAME:
                view.setData(R.string.Username);
                break;
              case MoexConfig.HEADER_TEXT_NAME:
                view.setData(R.string.login_FirstName);
                break;
            }
            break;
          }
        }
      }
    };

    ArrayList<ListItem> items = new ArrayList<>();
    items.add(new ListItem(ListItem.TYPE_EMPTY_OFFSET_SMALL));
    items.add(new ListItem(ListItem.TYPE_HEADER, 0, 0, R.string.DrawerOptions));
    items.add(new ListItem(ListItem.TYPE_RADIO_SETTING, R.id.btn_hidePhone, 0, R.string.hidePhoneNumber));
    items.add(new ListItem(ListItem.TYPE_SEPARATOR_FULL));
    items.add(new ListItem(ListItem.TYPE_RADIO_SETTING, R.id.btn_showIdProfile, 0, R.string.showIdProfile));
    items.add(new ListItem(ListItem.TYPE_SEPARATOR_FULL));
    items.add(new ListItem(ListItem.TYPE_RADIO_SETTING, R.id.btn_hideMessagesBadge, 0, R.string.hideMessagesBadge));
    items.add(new ListItem(ListItem.TYPE_SEPARATOR_FULL));
    items.add(new ListItem(ListItem.TYPE_VALUED_SETTING_COMPACT, R.id.btn_headerText, 0, R.string.changeHeaderText));
    items.add(new ListItem(ListItem.TYPE_SHADOW_BOTTOM));

    items.add(new ListItem(ListItem.TYPE_HEADER, 0, 0, R.string.ExperimentalOptions));
    items.add(new ListItem(ListItem.TYPE_RADIO_SETTING, R.id.btn_enableFeaturesButton, 0, R.string.EnableFeatures));
    items.add(new ListItem(ListItem.TYPE_SHADOW_BOTTOM));
    items.add(new ListItem(ListItem.TYPE_DESCRIPTION, 0, 0, Lang.getMarkdownString(this, R.string.FeaturesButtonInfo), false));
    items.add(new ListItem(ListItem.TYPE_SHADOW_TOP));

    adapter.setItems(items, true);
    recyclerView.setAdapter(adapter);
  }
}