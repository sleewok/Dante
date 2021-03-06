package at.shockbytes.dante.util.settings

import android.content.Context
import android.content.SharedPreferences
import at.shockbytes.dante.R
import at.shockbytes.dante.util.settings.delegate.SharedPreferencesBoolPropertyDelegate
import at.shockbytes.dante.util.settings.delegate.SharedPreferencesStringPropertyDelegate
import at.shockbytes.dante.util.sort.SortStrategy
import com.f2prateek.rx.preferences2.RxSharedPreferences
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Author:  Martin Macheiner
 * Date:    11.02.2018
 */
class DanteSettings(
    private val context: Context,
    private val prefs: SharedPreferences
) {

    private val rxPrefs: RxSharedPreferences = RxSharedPreferences.create(prefs)

    var isFirstAppOpen: Boolean by SharedPreferencesBoolPropertyDelegate(prefs, context.getString(R.string.prefs_first_app_open_key), defaultValue = true)

    private val darkModeString: String by SharedPreferencesStringPropertyDelegate(prefs, context.getString(R.string.prefs_dark_mode_key), defaultValue = "light")

    var selectedLauncherIconState: LauncherIconState
        get() {
            val idString = prefs.getString(context.getString(R.string.prefs_launcher_icon_state_key), "standard") ?: "standard"
            return LauncherIconState.ofStringOrDefault(idString)
        }
        set(value) {
            prefs.edit()
                .putString(context.getString(R.string.prefs_launcher_icon_state_key), value.manifestAliasId)
                .apply()
        }

    val themeState: ThemeState
        get() = ThemeState.ofString(darkModeString) ?: ThemeState.SYSTEM

    var showSummary: Boolean by SharedPreferencesBoolPropertyDelegate(prefs, context.getString(R.string.prefs_show_summary_key), defaultValue = true)

    var trackingEnabled: Boolean by SharedPreferencesBoolPropertyDelegate(prefs, context.getString(R.string.prefs_tracking_key), defaultValue = false)

    var sortStrategy: SortStrategy
        get() {
            val ordinal = prefs.getInt(context.getString(R.string.prefs_sort_strategy_key), 0)
            return SortStrategy.values()[ordinal]
        }
        set(value) {
            prefs.edit()
                    .putInt(context.getString(R.string.prefs_sort_strategy_key), value.ordinal)
                    .apply()
        }

    var lastLogin: Long
        get() = prefs.getLong(context.getString(R.string.prefs_last_login), 0L)
        set(value) {
            prefs.edit()
                    .putLong(context.getString(R.string.prefs_last_login), value)
                    .apply()
        }

    fun observeSortStrategy(): Observable<SortStrategy> {
        return rxPrefs.getInteger(context.getString(R.string.prefs_sort_strategy_key))
                .asObservable()
                .map { ordinal ->
                    SortStrategy.values()[ordinal]
                }
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
    }

    fun observeThemeChanged(): Observable<ThemeState> {
        return rxPrefs.getString(context.getString(R.string.prefs_dark_mode_key))
            .asObservable()
            .filter { it.isNotEmpty() }
            .distinctUntilChanged()
            .map(ThemeState.Companion::ofStringWithDefault)
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
    }
}