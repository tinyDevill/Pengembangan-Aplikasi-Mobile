package com.example.tugasketujuh

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.tugasketujuh.component.NoteViewModel
import com.example.tugasketujuh.data.NoteRepository
import com.example.tugasketujuh.db.Database
import com.example.tugasketujuh.db.DatabaseDriverFactory

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 🔥 INIT DATABASE
        val driverFactory = DatabaseDriverFactory(this)
        val database = Database(driverFactory)
        val repository = NoteRepository(database)

        // 🔥 INIT VIEWMODEL
        val viewModel = NoteViewModel(repository)

        setContent {
            App(viewModel)
        }
    }
}
