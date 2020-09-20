package com.devansh.paper.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.devansh.paper.R
import com.devansh.paper.view.adapter.NotesListAdapter
import com.devansh.paper.viewmodel.NoteViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_notes.*

@AndroidEntryPoint
class NotesFragment : Fragment(), SwipeRefreshLayout.OnRefreshListener {

    private lateinit var viewModel: NoteViewModel
    private val noteListAdapter = NotesListAdapter(arrayListOf())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProviders.of(this,defaultViewModelProviderFactory).get(NoteViewModel::class.java)

        return inflater.inflate(R.layout.fragment_notes, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        // for handling item clicks
        noteListAdapter.onItemClick = { goToNoteDetails(it.id) }

        rv_notes.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = noteListAdapter
        }

        swipeRefresh.setOnRefreshListener(this)
        fab.setOnClickListener { goToNoteDetails() }
        observeViewModel()
    }

    private fun goToNoteDetails(id: Long = 0L) {
        val noteDetailsFragment = NoteDetailsFragment.newInstance(id)
        noteDetailsFragment.show(activity!!.supportFragmentManager, "note_details")
    }

    override fun onResume() {
        super.onResume()
        onRefresh()
    }

    private fun observeViewModel() {
        viewModel.notesList.observe(viewLifecycleOwner, { noteList ->
            progressbar.visibility = View.GONE
            swipeRefresh.isRefreshing = false
            // if values are fetched
            noteList?.let {
                if (noteList.isNotEmpty()) {
                    rv_notes.visibility = View.VISIBLE
                    noteListAdapter.updateNotes(noteList.sortedByDescending { it.updateTime })
                } else {
                    layout_empty.visibility = View.VISIBLE
                }
            }
        })
    }

    override fun onRefresh() {
        viewModel.fetchAllNotes()
    }
}
