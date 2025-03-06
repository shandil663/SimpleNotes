package com.example.simplenotes.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.simplenotes.model.Note

class NoteViewModel : ViewModel() {
    private var _notes = mutableStateOf(listOf<Note>())
    val notes: State<List<Note>> = _notes

    fun addNote(title: String, description: String) {
        if (title.isNotBlank() && description.isNotBlank()) {
            _notes.value += Note(title, description)
        }
    }
}
