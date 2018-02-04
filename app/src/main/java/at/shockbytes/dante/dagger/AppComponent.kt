package at.shockbytes.dante.dagger

import at.shockbytes.dante.ui.activity.BookRetrievalActivity
import at.shockbytes.dante.ui.activity.DetailActivity
import at.shockbytes.dante.ui.activity.MainActivity
import at.shockbytes.dante.ui.activity.SearchActivity
import at.shockbytes.dante.ui.fragment.*
import at.shockbytes.dante.ui.fragment.dialog.GoogleSignInDialogFragment
import at.shockbytes.dante.ui.fragment.dialog.StatsDialogFragment
import dagger.Component
import javax.inject.Singleton

/**
 * @author Martin Macheiner
 * Date: 19.01.2017.
 */

@Singleton
@Component(modules = [(NetworkModule::class), (AppModule::class), (BookModule::class)])
interface AppComponent {

    fun inject(activity: MainActivity)

    fun inject(activity: DetailActivity)

    fun inject(activity: BookRetrievalActivity)

    fun inject(activity: SearchActivity)

    fun inject(fragment: MainBookFragment)

    fun inject(fragment: StatsDialogFragment)

    fun inject(fragment: DownloadBookFragment)

    fun inject(fragment: BackupFragment)

    fun inject(fragment: QueryCaptureFragment)

    fun inject(fragment: SearchFragment)

    fun inject(dialogFragment: GoogleSignInDialogFragment)

}
