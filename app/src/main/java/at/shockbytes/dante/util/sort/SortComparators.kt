package at.shockbytes.dante.util.sort

import at.shockbytes.dante.core.book.BookEntity
import at.shockbytes.dante.util.removeBrackets

/**
 * Author:  Martin Macheiner
 * Date:    14.06.2018
 */
class SortComparators {

    private class PositionComparator : Comparator<BookEntity> {
        override fun compare(o1: BookEntity?, o2: BookEntity?): Int {
            return o1?.position?.compareTo(o2?.position ?: 0) ?: 0
        }
    }

    private class AuthorComparator : Comparator<BookEntity> {
        override fun compare(o1: BookEntity?, o2: BookEntity?): Int {

            val author1 = o1?.author ?: return 0
            val author2 = o2?.author ?: return 0
            val surnameAuthor1 = getSurname(author1)
            val surnameAuthor2 = getSurname(author2)

            return surnameAuthor1.compareTo(surnameAuthor2)
        }

        private fun getSurname(author: String): String {

            // Multiple authors
            val primaryAuthor = if (author.contains(",")) {
                author.split(",")[0].removeBrackets()
            } else author

            // Author has several names
            return if (primaryAuthor.contains(" ")) {
                primaryAuthor.split(" ").last()
            } else primaryAuthor
        }
    }

    private class TitleComparator : Comparator<BookEntity> {
        override fun compare(o1: BookEntity?, o2: BookEntity?): Int {
            return o1?.title?.compareTo(o2?.title ?: "") ?: 0
        }
    }

    /**
     * Here compare o2 with o1, because we want descending sorting
     */
    private class ProgressComparator : Comparator<BookEntity> {
        override fun compare(o1: BookEntity?, o2: BookEntity?): Int {
            return o2?.currentPage?.compareTo(o1?.currentPage ?: 0) ?: 0
        }
    }

    private class PagesComparator : Comparator<BookEntity> {
        override fun compare(o1: BookEntity?, o2: BookEntity?): Int {
            return o1?.pageCount?.compareTo(o2?.pageCount ?: 0) ?: 0
        }
    }

    private class LabelsComparator : Comparator<BookEntity> {
        override fun compare(o1: BookEntity?, o2: BookEntity?): Int {

            val firstLabel = o1?.labels?.firstOrNull()?.title ?: ""
            val secondLabel = o2?.labels?.firstOrNull()?.title ?: ""

            return firstLabel.compareTo(secondLabel)
        }
    }

    companion object {

        fun of(strategy: SortStrategy): Comparator<BookEntity> {
            return when (strategy) {
                SortStrategy.POSITION -> PositionComparator()
                SortStrategy.AUTHOR -> AuthorComparator()
                SortStrategy.TITLE -> TitleComparator()
                SortStrategy.PROGRESS -> ProgressComparator()
                SortStrategy.PAGES -> PagesComparator()
                SortStrategy.LABELS -> LabelsComparator()
            }
        }
    }
}