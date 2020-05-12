package com.devansh.paper.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager

import com.devansh.paper.R
import com.devansh.paper.view.adapter.NotesListAdapter
import com.devansh.paper.viewmodel.NoteViewModel
import kotlinx.android.synthetic.main.fragment_notes.*


class NotesFragment : Fragment() {

    private lateinit var viewModel: NoteViewModel
    private val noteListAdapter = NotesListAdapter(arrayListOf())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_notes, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(NoteViewModel::class.java)

        // for handling item clicks
        noteListAdapter.onItemClick = { goToNoteDetails(it.id) }

        rv_notes.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = noteListAdapter
        }

        fab.setOnClickListener { goToNoteDetails() }
        observeViewModel()
    }

    private fun goToNoteDetails(id: Long = 0L) {
        val noteDetailsFragment = NoteDetailsFragment.newInstance(id)
        noteDetailsFragment.show(activity!!.supportFragmentManager, "note_details")
    }

    override fun onResume() {
        super.onResume()
        viewModel.fetchAllNotes()
    }

    private fun observeViewModel() {
        viewModel.notesList.observe(this, Observer { noteList ->
            progressbar.visibility = View.GONE
            rv_notes.visibility = View.VISIBLE
            // if values are fetched
            noteList?.let {
                if (noteList.isNotEmpty()) {
                    noteListAdapter.updateNotes(noteList.sortedByDescending { it.updateTime })
                } else {
                    Toast.makeText(context, "No Notes Found", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }
}
