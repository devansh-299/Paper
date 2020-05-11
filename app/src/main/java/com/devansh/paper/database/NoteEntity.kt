package com.devansh.paper.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.devansh.core.data.Note

@Entity(tableName = "Note")
data class NoteEntity(
    val title: String,
    val content: String,
    @ColumnInfo(name = "creation_date")
    val creationTime: Long,
    @ColumnInfo(name = "creation_date")
    val updatedTime: Long,
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L
) {
    companion object {
        // creates a database entity from the note give
        fun createNoteEntity(note: Note) = NoteEntity(note.title, note.content,
            note.creationTime, note.updateTime)
    }

    // create NoteObject from the Note Entity
    fun createNoteObject() = Note(id, title, content, creationTime, updatedTime)
}