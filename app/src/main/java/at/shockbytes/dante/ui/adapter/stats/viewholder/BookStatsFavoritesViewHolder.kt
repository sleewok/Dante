package at.shockbytes.dante.ui.adapter.stats.viewholder

import android.view.View
import at.shockbytes.dante.R
import at.shockbytes.dante.core.book.BareBoneBook
import at.shockbytes.dante.core.image.ImageLoader
import at.shockbytes.dante.stats.BookStatsViewItem
import at.shockbytes.dante.stats.FavoriteAuthor
import at.shockbytes.dante.util.setVisible
import at.shockbytes.util.adapter.BaseAdapter
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_stats_favorites.*

class BookStatsFavoritesViewHolder(
    override val containerView: View,
    private val imageLoader: ImageLoader
) : BaseAdapter.ViewHolder<BookStatsViewItem>(containerView), LayoutContainer {

    override fun bindToView(content: BookStatsViewItem, position: Int) {
        with(content as BookStatsViewItem.Favorites) {
            when (this) {
                BookStatsViewItem.Favorites.Empty -> {
                    showEmptyState()
                }
                is BookStatsViewItem.Favorites.Present -> {
                    showReadingDuration(this)
                }
            }
        }
    }

    private fun showEmptyState() {
        item_stats_favorites_empty.setVisible(true)
        item_stats_favorites_content.setVisible(false)
    }

    private fun showReadingDuration(content: BookStatsViewItem.Favorites.Present) {
        item_stats_favorites_empty.setVisible(false)
        item_stats_favorites_content.setVisible(true)

        with(content) {
            setFavoriteAuthor(favoriteAuthor)
            setFirstFiveStarBook(firstFiveStarBook)
        }
    }

    private fun setFavoriteAuthor(favoriteAuthor: FavoriteAuthor) {
        multi_bare_bone_book_favorite_author.apply {
            setTitle(favoriteAuthor.author)
            setMultipleBookImages(favoriteAuthor.bookUrls, imageLoader)
        }
    }

    private fun setFirstFiveStarBook(firstFiveStarBook: BareBoneBook?) {

        bare_bone_book_view_first_five_star.setVisible(firstFiveStarBook != null)
        tv_item_stats_favorites_first_five_star_header.setVisible(firstFiveStarBook != null)

        firstFiveStarBook?.let {
            bare_bone_book_view_first_five_star.apply {
                setTitle(firstFiveStarBook.title)

                val url = firstFiveStarBook.thumbnailAddress
                if (url != null) {
                    imageLoader.loadImageWithCornerRadius(
                        containerView.context,
                        url,
                        imageView,
                        cornerDimension = containerView.context.resources.getDimension(R.dimen.thumbnail_rounded_corner).toInt()
                    )
                } else {
                    imageView.setImageResource(R.drawable.ic_placeholder)
                }
            }
        }
    }
}