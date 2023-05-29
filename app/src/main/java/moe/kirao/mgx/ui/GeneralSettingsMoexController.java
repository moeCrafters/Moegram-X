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
    int viewId = v.getId();
    if (viewId == R.id.btn_hidePhone) {
      MoexConfig.instance().toggleHidePhoneNumber();
      adapter.updateValuedSettingById(R.id.btn_hidePhone);
    } else if (viewId == R.id.btn_enableFeaturesButton) {
      MoexConfig.instance().toggleEnableFeaturesButton();
      adapter.updateValuedSettingById(R.id.btn_enableFeaturesButton);
    } else if (viewId == R.id.btn_showIdProfile) {
      MoexConfig.instance().toggleShowIdProfile();
      adapter.updateValuedSettingById(R.id.btn_showIdProfile);
    } else if (viewId == R.id.btn_hideMessagesBadge) {
      MoexConfig.instance().toggleHideMessagesBadge();
      adapter.updateValuedSettingById(R.id.btn_hideMessagesBadge);
    } else if (viewId == R.id.btn_changeSizeLimit) {
      showChangeSizeLimit();
    }
  }

  private void showChangeSizeLimit () {
    int sizeLimitOption = MoexConfig.instance().getSizeLimit();
    showSettings(new SettingsWrapBuilder(R.id.btn_changeSizeLimit).setRawItems(new ListItem[] {
      new ListItem(ListItem.TYPE_RADIO_OPTION, R.id.btn_sizeLimit800, 0, R.string.px800, R.id.btn_changeSizeLimit, sizeLimitOption == MoexConfig.SIZE_LIMIT_800),
      new ListItem(ListItem.TYPE_RADIO_OPTION, R.id.btn_sizeLimit1280, 0, R.string.px1280, R.id.btn_changeSizeLimit, sizeLimitOption == MoexConfig.SIZE_LIMIT_1280),
      new ListItem(ListItem.TYPE_RADIO_OPTION, R.id.btn_sizeLimit2560, 0, R.string.px2560, R.id.btn_changeSizeLimit, sizeLimitOption == MoexConfig.SIZE_LIMIT_2560),
    }).setAllowResize(false).addHeaderItem(Lang.getString(R.string.SizeLimitDesc)).setIntDelegate((id, result) -> {
      int sizeOption;
      int sizeLimit = result.get(R.id.btn_changeSizeLimit);
      if (sizeLimit == R.id.btn_sizeLimit800) {
        sizeOption = MoexConfig.SIZE_LIMIT_800;
      } else if (sizeLimit == R.id.btn_sizeLimit1280) {
        sizeOption = MoexConfig.SIZE_LIMIT_1280;
      } else {
        sizeOption = MoexConfig.SIZE_LIMIT_2560;
      }
      MoexConfig.instance().setSizeLimit(sizeOption);
      adapter.updateValuedSettingById(R.id.btn_changeSizeLimit);
    }));
  }

  @Override public int getId () {
    return R.id.controller_moexSettings;
  }

  @Override protected void onCreateView (Context context, CustomRecyclerView recyclerView) {
    adapter = new SettingsAdapter(this) {
      @Override protected void setValuedSetting (ListItem item, SettingView view, boolean isUpdate) {
        view.setDrawModifier(item.getDrawModifier());
        int itemId = item.getId();
        if (itemId == R.id.btn_hidePhone) {
          view.getToggler().setRadioEnabled(MoexConfig.hidePhoneNumber, isUpdate);
        } else if (itemId == R.id.btn_enableFeaturesButton) {
          view.getToggler().setRadioEnabled(MoexConfig.enableTestFeatures, isUpdate);
        } else if (itemId == R.id.btn_showIdProfile) {
          view.getToggler().setRadioEnabled(MoexConfig.showId, isUpdate);
        } else if (itemId == R.id.btn_hideMessagesBadge) {
          view.getToggler().setRadioEnabled(MoexConfig.hideMessagesBadge, isUpdate);
        } else if (itemId == R.id.btn_changeSizeLimit) {
            int size = MoexConfig.instance().getSizeLimit();
            switch (size) {
              case MoexConfig.SIZE_LIMIT_800:
                view.setData(R.string.px800);
                break;
              case MoexConfig.SIZE_LIMIT_1280:
                view.setData(R.string.px1280);
                break;
              case MoexConfig.SIZE_LIMIT_2560:
                view.setData(R.string.px2560);
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
    items.add(new ListItem(ListItem.TYPE_SHADOW_BOTTOM));

    items.add(new ListItem(ListItem.TYPE_HEADER, 0, 0, R.string.ExperimentalOptions));
    items.add(new ListItem(ListItem.TYPE_RADIO_SETTING, R.id.btn_enableFeaturesButton, 0, R.string.EnableFeatures));
    items.add(new ListItem(ListItem.TYPE_SHADOW_BOTTOM));
    items.add(new ListItem(ListItem.TYPE_DESCRIPTION, 0, 0, Lang.getMarkdownString(this, R.string.FeaturesButtonInfo), false));
    items.add(new ListItem(ListItem.TYPE_SHADOW_TOP));
    items.add(new ListItem(ListItem.TYPE_VALUED_SETTING_COMPACT, R.id.btn_changeSizeLimit, 0, R.string.changeSizeLimit));
    items.add(new ListItem(ListItem.TYPE_DESCRIPTION, 0, 0, Lang.getMarkdownString(this, R.string.changeSizeLimitInfo), false));

    adapter.setItems(items, true);
    recyclerView.setAdapter(adapter);
  }
}