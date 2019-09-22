package at.shockbytes.dante.core.injection

import at.shockbytes.dante.core.book.realm.RealmInstanceProvider
import at.shockbytes.dante.core.data.BookEntityDao
import at.shockbytes.dante.core.image.ImageLoader
import at.shockbytes.dante.core.image.ImagePicker
import at.shockbytes.dante.core.network.BookDownloader
import at.shockbytes.dante.core.network.amazon.AmazonItemLookupApi
import at.shockbytes.dante.core.network.google.GoogleBooksApi
import at.shockbytes.dante.util.scheduler.SchedulerFacade
import dagger.Component
import okhttp3.OkHttpClient
import javax.inject.Singleton

@Component(
    modules = [
        CoreModule::class,
        NetworkModule::class
    ]
)
@Singleton
interface CoreComponent {

    fun getBookDao(): BookEntityDao
    fun getBookDownloader(): BookDownloader
    fun getRealmInstanceProvider(): RealmInstanceProvider

    fun getImageLoader(): ImageLoader
    fun getImagePicker(): ImagePicker
    fun getSchedulerFacade(): SchedulerFacade

    fun getOkHttpClient(): OkHttpClient
    fun provideGoogleBooksApi(): GoogleBooksApi
    fun provideAmazonItemLookupApi(): AmazonItemLookupApi
}