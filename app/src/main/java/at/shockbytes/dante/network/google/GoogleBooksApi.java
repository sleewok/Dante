package at.shockbytes.dante.network.google;

import at.shockbytes.dante.util.books.BookSuggestion;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * @author Martin Macheiner
 *         Date: 13.02.2017.
 */

public interface GoogleBooksApi {

    String SERVICE_ENDPOINT = "https://www.googleapis.com/books/v1/";

    @GET("volumes")
    Observable<BookSuggestion> downloadBookSuggestion(@Query("q") String query);

}
