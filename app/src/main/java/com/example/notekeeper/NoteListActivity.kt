package com.example.notekeeper

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import androidx.appcompat.app.AppCompatActivity
import android.content.Intent as Intent

class NoteListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note_list)
        setSupportActionBar(findViewById(R.id.toolbar))

        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener { view ->
            val activityIntent = Intent(this, NoteActivity::class.java)
            startActivity(activityIntent)
        }

        val listNotesDisplay = findViewById<ListView>(R.id.listNotes)
        listNotesDisplay.adapter = ArrayAdapter<NoteInfo>(this,
            android.R.layout.simple_list_item_1,
            DataManager.notes)

        listNotesDisplay.setOnItemClickListener { parent, view, position, id ->
            val activityIntent = Intent(this, NoteActivity::class.java)
            activityIntent.putExtra(NOTE_POSITION, position)
            print(position)
            startActivity(activityIntent)
        }
    }
    override fun onResume() {
        super.onResume()
        val listNotesDisplay = findViewById<ListView>(R.id.listNotes)
        (listNotesDisplay.adapter as ArrayAdapter<NoteInfo>).notifyDataSetChanged()
    }
}
