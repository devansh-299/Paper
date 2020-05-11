package com.devansh.paper.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import com.devansh.paper.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.fragment_note_details.*
import kotlin.properties.Delegates


class NoteDetailsFragment : BottomSheetDialogFragment() {

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

        chip_save.setOnClickListener {

        }
    }

}
