package org.buffer.android.boilerplate.ui.browse

import androidx.lifecycle.Observer
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.View
import kotlinx.android.synthetic.main.activity_browse.progress
import kotlinx.android.synthetic.main.activity_browse.recycler_browse
import kotlinx.android.synthetic.main.activity_browse.view_empty
import kotlinx.android.synthetic.main.activity_browse.view_error
import org.buffer.android.boilerplate.data.browse.Bufferoo
import org.buffer.android.boilerplate.ui.R
import org.buffer.android.boilerplate.ui.widget.empty.EmptyListener
import org.buffer.android.boilerplate.ui.widget.error.ErrorListener
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class BrowseActivity: AppCompatActivity() {

    val browseAdapter: BrowseAdapter by inject()

    val browseBufferoosViewModel: BrowseBufferoosViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_browse)

        setupBrowseRecycler()
        setupViewListeners()

        browseBufferoosViewModel.getBufferoos().observe(this,
                Observer<BrowseState> {
                    if (it != null) this.handleDataState(it) })
        browseBufferoosViewModel.fetchBufferoos()
    }

    private fun setupBrowseRecycler() {
        recycler_browse.layoutManager = LinearLayoutManager(this)
        recycler_browse.adapter = browseAdapter
    }

    private fun handleDataState(browseState: BrowseState) {
        when (browseState) {
            is BrowseState.Loading -> setupScreenForLoadingState()
            is BrowseState.Success -> setupScreenForSuccess(browseState.data)
            is BrowseState.Error -> setupScreenForError(browseState.errorMessage)
        }
    }

    private fun setupScreenForLoadingState() {
        progress.visibility = View.VISIBLE
        recycler_browse.visibility = View.GONE
        view_empty.visibility = View.GONE
        view_error.visibility = View.GONE
    }

    private fun setupScreenForSuccess(data: List<Bufferoo>?) {
        view_error.visibility = View.GONE
        progress.visibility = View.GONE
        if (data!= null && data.isNotEmpty()) {
            updateListView(data)
            recycler_browse.visibility = View.VISIBLE
        } else {
            view_empty.visibility = View.VISIBLE
        }
    }

    private fun updateListView(bufferoos: List<Bufferoo>) {
        browseAdapter.bufferoos = bufferoos
        browseAdapter.notifyDataSetChanged()
    }

    private fun setupScreenForError(message: String?) {
        progress.visibility = View.GONE
        recycler_browse.visibility = View.GONE
        view_empty.visibility = View.GONE
        view_error.visibility = View.VISIBLE
    }

    private fun setupViewListeners() {
        view_empty.emptyListener = emptyListener
        view_error.errorListener = errorListener
    }

    private val emptyListener = object : EmptyListener {
        override fun onCheckAgainClicked() {
            browseBufferoosViewModel.fetchBufferoos()
        }
    }

    private val errorListener = object : ErrorListener {
        override fun onTryAgainClicked() {
            browseBufferoosViewModel.fetchBufferoos()
        }
    }

}