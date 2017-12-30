package at.shockbytes.dante.util.books

import android.support.v4.app.FragmentActivity
import at.shockbytes.dante.backup.BackupManager
import io.reactivex.Observable
import io.reactivex.Single

/**
 * @author Martin Macheiner
 * Date: 27.08.2016.
 */
interface BookManager {

    val statistics: Map<String, Int>

    val allBooks: Observable<List<Book>>

    val allBooksSync: List<Book>

    fun addBook(book: Book): Book

    fun getBook(id: Long): Book

    fun updateBookState(book: Book, newState: Book.State)

    fun updateCurrentBookPage(book: Book, page: Int)

    fun updateBookStateAndPage(book: Book, state: Book.State, page: Int)

    fun removeBook(id: Long)

    fun downloadBook(isbn: String): Observable<BookSuggestion>

    fun restoreBackup(backupBooks: List<Book>, strategy: BackupManager.RestoreStrategy)

    fun getBooksByState(state: Book.State): Observable<List<Book>>

    fun close()

}