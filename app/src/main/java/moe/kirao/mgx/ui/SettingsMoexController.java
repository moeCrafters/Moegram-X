package moe.kirao.mgx.ui;

import android.content.Context;
import android.util.SparseIntArray;
import android.view.View;

import org.thunderdog.challegram.R;
import org.thunderdog.challegram.BuildConfig;
import org.thunderdog.challegram.U;
import org.thunderdog.challegram.core.Lang;
import org.thunderdog.challegram.navigation.ViewController;
import org.thunderdog.challegram.telegram.Tdlib;
import org.thunderdog.challegram.telegram.TdlibUi;
import org.thunderdog.challegram.tool.UI;
import org.thunderdog.challegram.ui.ListItem;
import org.thunderdog.challegram.ui.RecyclerViewController;
import org.thunderdog.challegram.ui.SettingsAdapter;
import org.thunderdog.challegram.v.CustomRecyclerView;

import java.util.ArrayList;

public class SettingsMoexController extends RecyclerViewController<Void> implements View.OnClickListener, View.OnLongClickListener, ViewController.SettingsIntDelegate {

  public SettingsMoexController (Context context, Tdlib tdlib) {
    super(context, tdlib);
  }

  @Override public CharSequence getName () {
    return Lang.getString(R.string.MoexSettings);
  }

  @Override public void onClick (View v) {
    int id = v.getId();
    switch (id) {
      case R.id.btn_GeneralMoexSettings: {
        navigateTo(new GeneralSettingsMoexController(context, tdlib));
        break;
      } case R.id.btn_InterfaceMoexSettings: {
        navigateTo(new InterfaceSettingsMoexController(context, tdlib));
        break;
      } case R.id.btn_ChatsMoexSettings: {
        navigateTo(new ChatsSettingsMoexController(context, tdlib));
        break;
      } case R.id.btn_moexChannelLink : {
        tdlib.ui().openUrl(this, Lang.getStringSecure(R.string.MoexChannelLink), new TdlibUi.UrlOpenParameters().forceInstantView());
        break;
      } case R.id.btn_moexChatLink : {
        tdlib.ui().openUrl(this, Lang.getStringSecure(R.string.MoexChatLink), new TdlibUi.UrlOpenParameters().forceInstantView());
        break;
      } case R.id.btn_moexUpdatesLink : {
        tdlib.ui().openUrl(this, Lang.getStringSecure(R.string.MoexUpdatesLink), new TdlibUi.UrlOpenParameters().forceInstantView());
        break;
      } case R.id.btn_moexSourceLink: {
        tdlib.ui().openUrl(this, Lang.getStringSecure(R.string.MoexSourceLink), new TdlibUi.UrlOpenParameters().forceInstantView());
        break;
      }
      case R.id.btn_build: {
        UI.copyText(Lang.getStringSecure(R.string.MoexVer) + " (" + BuildConfig.COMMIT + ")\n", R.string.CopiedText);
        break;
      }
    }
  }

  @Override
  public boolean onLongClick (View v) {
    if (v.getId() == R.id.btn_build) {
      UI.copyText(U.getUsefulMetadata(tdlib), R.string.CopiedText);
    }
    return false;
  }

  @Override public void onApplySettings (int id, SparseIntArray result) {

  }

  @Override public int getId () {
    return R.id.controller_moexSettings;
  }

  @Override protected void onCreateView (Context context, CustomRecyclerView recyclerView) {
    SettingsAdapter adapter = new SettingsAdapter(this);

    ArrayList<ListItem> items = new ArrayList<>();
    items.add(new ListItem(ListItem.TYPE_EMPTY_OFFSET_SMALL));
    items.add(new ListItem(ListItem.TYPE_HEADER, 0, 0, R.string.MoexAbout));
    items.add(new ListItem(ListItem.TYPE_SHADOW_TOP));
    items.add(new ListItem(ListItem.TYPE_DESCRIPTION, 0, 0, Lang.getMarkdownString(this, R.string.MoexAboutText), false));
    items.add(new ListItem(ListItem.TYPE_SHADOW_BOTTOM));

    items.add(new ListItem(ListItem.TYPE_HEADER, 0, 0, R.string.MoexCategories));
    items.add(new ListItem(ListItem.TYPE_SHADOW_TOP));
    items.add(new ListItem(ListItem.TYPE_SETTING, R.id.btn_GeneralMoexSettings, R.drawable.baseline_settings_24, R.string.GeneralMoexSettings));
    items.add(new ListItem(ListItem.TYPE_SEPARATOR));
    items.add(new ListItem(ListItem.TYPE_SETTING, R.id.btn_InterfaceMoexSettings, R.drawable.baseline_extension_24, R.string.InterfaceMoexSettings));
    items.add(new ListItem(ListItem.TYPE_SEPARATOR));
    items.add(new ListItem(ListItem.TYPE_SETTING, R.id.btn_ChatsMoexSettings, R.drawable.baseline_chat_bubble_24, R.string.ChatsMoexSettings));
    items.add(new ListItem(ListItem.TYPE_SHADOW_BOTTOM));

    items.add(new ListItem(ListItem.TYPE_HEADER, 0, 0, R.string.MoexLinks));
    items.add(new ListItem(ListItem.TYPE_SHADOW_TOP));
    items.add(new ListItem(ListItem.TYPE_SETTING, R.id.btn_moexChannelLink, R.drawable.baseline_link_24, R.string.MoexChannelText));
    items.add(new ListItem(ListItem.TYPE_SEPARATOR));
    items.add(new ListItem(ListItem.TYPE_SETTING, R.id.btn_moexChatLink, R.drawable.baseline_forum_24, R.string.MoexChatText));
    items.add(new ListItem(ListItem.TYPE_SEPARATOR));
    items.add(new ListItem(ListItem.TYPE_SETTING, R.id.btn_moexUpdatesLink, R.drawable.baseline_system_update_24, R.string.MoexUpdatesText));
    items.add(new ListItem(ListItem.TYPE_SEPARATOR));
    items.add(new ListItem(ListItem.TYPE_SETTING, R.id.btn_moexSourceLink, R.drawable.baseline_code_24, R.string.MoexSourceText));
    items.add(new ListItem(ListItem.TYPE_SHADOW_BOTTOM));

    items.add(new ListItem(ListItem.TYPE_BUILD_NO, R.id.btn_build, 0, R.string.MoexVer, false));
    adapter.setItems(items, true);
    recyclerView.setAdapter(adapter);
  }
}
