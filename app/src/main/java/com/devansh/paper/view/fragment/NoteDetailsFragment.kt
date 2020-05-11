package com.devansh.paper.view.fragment

import android.content.Context.INPUT_METHOD_SERVICE
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.devansh.core.data.Note
import com.devansh.paper.R
import com.devansh.paper.viewmodel.NoteViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.fragment_note_details.*
import kotlin.properties.Delegates


class NoteDetailsFragment : BottomSheetDialogFragment() {

    private lateinit var viewModel: NoteViewModel
    private var currentNote = Note(0L, "", "", 0L, 0L)

    var noteId by Delegates.notNull<Long>()

    companion object {
        private const val NOTE_ID = "note_id"

        fun newInstance(noteId: Long) = NoteDetailsFragment().apply {
            arguments = bundleOf(
                NOTE_ID to noteId)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_note_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let { noteId = it.getLong(NOTE_ID) }

        viewModel = ViewModelProviders.of(this).get(NoteViewModel::class.java)

        chip_save.setOnClickListener {
            if (bottomsheet_title.text.toString() != "" ||
                bottomsheet_content.text.toString() != "") {
                val currentTime = System.currentTimeMillis()
                currentNote.title = bottomsheet_title.text.toString()
                currentNote.content = bottomsheet_content.text.toString()
                currentNote.updateTime = currentTime
                // checking if the note already exists, 0 is the default value!
                if (currentNote.id == 0L) {
                    currentNote.creationTime = currentTime
                }
                // for saving the note
                viewModel.saveNote(currentNote)
            } else {
                Toast.makeText(context,getString(R.string.enter_all_details), Toast.LENGTH_SHORT )
                    .show()
            }
        }

        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.noteSaved.observe(this, Observer {
            // if noteSaved == true
            if (it) {
                hideKeyboard()
                Toast.makeText(context, getString(R.string.note_saved), Toast.LENGTH_SHORT ).show()
            } else {
                Toast.makeText(context, getString(R.string.error), Toast.LENGTH_SHORT ).show()
            }
        })
    }

    private fun hideKeyboard() {
        val inputMethodManager = context?.getSystemService(INPUT_METHOD_SERVICE)
                 as InputMethodManager
        // any view can be passed
        inputMethodManager.hideSoftInputFromWindow(bottomsheet_title.windowToken, 0)
    }
}
