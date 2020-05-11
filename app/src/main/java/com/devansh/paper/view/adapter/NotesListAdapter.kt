package com.devansh.paper.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.devansh.core.data.Note
import com.devansh.paper.R
import kotlinx.android.synthetic.main.item_note.view.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class NotesListAdapter(var notes: ArrayList<Note>):
    RecyclerView.Adapter<NotesListAdapter.NoteViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = NoteViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_note, parent, false)
    )

    override fun getItemCount() = notes.size

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.bindData(notes[position])
    }

    inner class NoteViewHolder(view: View): RecyclerView.ViewHolder(view) {

        private val layout = view.noteCardView
        private val noteTitle = view.item_title
        private val noteContent = view.item_content
        private val updateTime = view.item_last_update

        fun bindData(note: Note) {
            noteTitle.text = note.title
            noteContent.text = note.content
            updateTime.text = "Last Updated: ${formatTime(note.updateTime)}"
        }

        private fun formatTime(timeInMillis: Long): String {
            val simpleDateFormat = SimpleDateFormat("dd MMM, HH:mm")
            val resultDate = Date(timeInMillis)
            return simpleDateFormat.format(resultDate)
        }
    }

    fun updateNotes(newNotesList: List<Note>) {
        notes.clear()
        notes.addAll(newNotesList)
        notifyDataSetChanged()
    }

}