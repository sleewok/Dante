package at.shockbytes.dante.ui.fragment

import android.os.Bundle
import android.widget.Toast
import at.shockbytes.dante.R
import at.shockbytes.dante.dagger.AppComponent
import kotlinx.android.synthetic.main.fragment_pages.*

class PagesFragment  : BaseFragment() {

    override val layoutId = R.layout.fragment_pages

    var onPageEditedListener: ((current: Int, pages: Int) -> Unit)? = null

    override fun setupViews() {

        arguments?.getInt(ARG_CURRENT)?.let { current ->
            et_pages_current_page.setText(current.toString())
        }
        arguments?.getInt(ARG_PAGES)?.let { pages ->
            et_pages_pages.setText(pages.toString())
        }

        btn_pages_save.setOnClickListener {

            val current = et_pages_current_page.text?.toString()?.toIntOrNull()
            val pages = et_pages_pages.text?.toString()?.toIntOrNull()

            if (validateInput()) {
                if (current != null && pages != null) {
                    onPageEditedListener?.invoke(current, pages)
                    fragmentManager?.popBackStack()
                }
            } else {
                Toast.makeText(context, getString(R.string.dialogfragment_paging_error),
                        Toast.LENGTH_SHORT).show()
            }
        }

        layout_pages.setOnClickListener {
            fragmentManager?.popBackStack()
        }

        btn_pages_close.setOnClickListener {
            fragmentManager?.popBackStack()
        }
    }

    override fun injectToGraph(appComponent: AppComponent) {
    }

    override fun bindViewModel() = Unit

    override fun unbindViewModel() = Unit

    private fun validateInput(): Boolean {

        val current = et_pages_current_page.text.toString().toIntOrNull()
        val pages = et_pages_pages.text.toString().toIntOrNull()

        return if (current == null || pages == null) {
            false
        } else {
            pages >= current
        }
    }

    companion object {

        private const val ARG_CURRENT = "arg_current"
        private const val ARG_PAGES = "arg_pages"

        fun newInstance(current: Int, pages: Int): PagesFragment {
            return PagesFragment().apply {
                this.arguments = Bundle().apply {
                    putInt(ARG_CURRENT, current)
                    putInt(ARG_PAGES, pages)
                }
            }
        }
    }
}