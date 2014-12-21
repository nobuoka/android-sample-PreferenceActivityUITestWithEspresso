package info.vividcode.android.app.preftest;

import android.app.Instrumentation;
import android.content.Intent;
import android.content.IntentFilter;
import android.test.ActivityInstrumentationTestCase2;
import android.test.TouchUtils;
import android.widget.ListView;

import android.support.test.espresso.matcher.PreferenceMatchers;

import org.hamcrest.Matchers;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.action.ViewActions.click;

public class SettingsActivityTest extends ActivityInstrumentationTestCase2<SettingsActivity> {

    public SettingsActivityTest() {
        super(SettingsActivity.class);
    }

    /**
     * 設定画面の中の 「その他」 の 「ひだまりソケットは壊れない」 をクリックして、
     * http スキーマの URL をデータとして持つ Intent が投げられることを確認する。
     */
    public void test_clickSettingHidamari() throws Throwable {
        // ActivityMonitor の追加
        IntentFilter intentFilter = new IntentFilter(Intent.ACTION_VIEW);
        intentFilter.addDataScheme("http");
        Instrumentation.ActivityMonitor monitor =
                new Instrumentation.ActivityMonitor(intentFilter, null, true);
        getInstrumentation().addMonitor(monitor);

        SettingsActivity s = getActivity();

        // `onData` api を使って、もともと画面上に表示されていない項目を選択した場合は、
        // Espresso が自動的に項目を画面上に表示してくれる。
        // See: https://code.google.com/p/android-test-kit/source/browse/testapp_test/src/main/java/com/google/android/apps/common/testing/ui/testapp/AdapterViewTest.java#48
        // しかし、PreferenceActivity のリストではうまく動かない場合がある。
        // - API level 8 でうまく動かないことを確認。
        // - API level 19 では、targetSdkVersion を 19 にしていれば動くが、targetSdkVersion を指定
        //   しなかった場合は動かないことを確認。
        // 回避策として、一度リストのドラッグを行えば良いということがわかっているのでそのようにする。
        // (ドラッグにより項目が見える必要はなくて、単にドラッグすればよいようである。)

        // ドラッグ対象の ListView
        ListView listView = s.getListView();

        // 「その他」 をタップ
        TouchUtils.dragViewToTop(this, listView); // 問題回避用のドラッグ
        String otherSettingsKey = s.getResources().getString(R.string.setting_other_settings_key);
        onData(Matchers.<Object>allOf(PreferenceMatchers.withKey(otherSettingsKey))).perform(click());

        // 「ひだまりソケットは壊れない」 をタップ
        TouchUtils.dragViewToTop(this, listView); // 問題回避用のドラッグ
        String hidamariKey = s.getResources().getString(R.string.setting_hidamari_key);
        onData(Matchers.<Object>allOf(PreferenceMatchers.withKey(hidamariKey))).perform(click());

        // 確認
        assertEquals("http スキーマのデータを持つ VIEW アクションの Intent が 1 回投げられている",
                1, monitor.getHits());

        // ActivityMonitor の削除
        getInstrumentation().removeMonitor(monitor);
    }

}
