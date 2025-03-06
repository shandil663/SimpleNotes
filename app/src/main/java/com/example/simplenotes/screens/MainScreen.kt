package com.example.simplenotes.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.simplenotes.viewmodel.NoteViewModel

@Composable
fun MainScreen(viewModel: NoteViewModel = viewModel()) {
    val notes by viewModel.notes
    var showDialog by remember { mutableStateOf(false) }
    var selectedDescription by remember { mutableStateOf<String?>(null) }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Spacer(modifier = Modifier.padding(top = 40.dp))
        Text(
            text = "Simple Notes App",
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Button(onClick = { showDialog = true }, modifier = Modifier.fillMaxWidth()) {
            Text("Add Note")
        }

        LazyColumn {
            items(notes) { note ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .clickable {
                            selectedDescription = note.description
                        },
                    elevation = CardDefaults.cardElevation(8.dp),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(text = note.title, fontWeight = FontWeight.Bold)
                        Text(text = note.description, color = Color.Gray, maxLines = 1)
                    }
                }
            }
        }
    }

    if (showDialog) {
        AddNoteDialog(onDismiss = { showDialog = false }) { title, description ->
            viewModel.addNote(title, description)
            showDialog = false
        }
    }

    if (selectedDescription != null) {
        AlertDialog(
            onDismissRequest = { selectedDescription = null },
            title = { Text(text = "Note Details") },
            text = { Text(text = selectedDescription!!) },
            confirmButton = {
                Button(onClick = { selectedDescription = null }) {
                    Text("Close")
                }
            }
        )
    }
}
