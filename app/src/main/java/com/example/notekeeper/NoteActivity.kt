package com.example.notekeeper

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.Spinner
import android.widget.ArrayAdapter as ArrayAdapter

class NoteActivity : AppCompatActivity() {
    private var notePosition = POSITION_NOT_SET
//    private val spinnerDisplay = findViewById<Spinner>(R.id.spinnerCourses)
//    private val noteTitleDisplay = findViewById<EditText>(R.id.textNoteTitle)
//    private val noteTextDisplay = findViewById<EditText>(R.id.textNoteText)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))

        val adapterCourses = ArrayAdapter<CourseInfo>(this,
            android.R.layout.simple_spinner_item,
            DataManager.courses.values.toList())
        adapterCourses.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        val spinnerDisplay = findViewById<Spinner>(R.id.spinnerCourses)
        spinnerDisplay.adapter = adapterCourses

        notePosition = savedInstanceState?.getInt(NOTE_POSITION, POSITION_NOT_SET) ?:
                intent.getIntExtra(NOTE_POSITION, POSITION_NOT_SET)

        if (notePosition != POSITION_NOT_SET)
            displayNote()
        else {
            DataManager.notes.add(NoteInfo())
            notePosition = DataManager.notes.lastIndex
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(NOTE_POSITION, notePosition)
    }

    private fun displayNote() {
        val note = DataManager.notes[notePosition]
        val noteTitleDisplay = findViewById<EditText>(R.id.textNoteTitle)
        noteTitleDisplay.setText(note.title)
        val noteTextDisplay = findViewById<EditText>(R.id.textNoteText)
        noteTextDisplay.setText(note.text)
        val coursePosition = DataManager.courses.values.indexOf(note.course)
        val spinnerDisplay = findViewById<Spinner>(R.id.spinnerCourses)
        spinnerDisplay.setSelection(coursePosition)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            R.id.action_next -> {
                moveNext()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun moveNext() {
        ++notePosition
        displayNote()
        invalidateOptionsMenu()
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        if (notePosition >= DataManager.notes.lastIndex) {
            val menuItem = menu?.findItem(R.id.action_next)
            menuItem?.icon = getDrawable(R.drawable.ic_baseline_block_24)
            menuItem?.isEnabled = false
        }
        return super.onPrepareOptionsMenu(menu)
    }

    override fun onPause() {
        super.onPause()
        saveNote()
    }

    private fun saveNote() {
        val note = DataManager.notes[notePosition]
        val noteTitleDisplay = findViewById<EditText>(R.id.textNoteTitle)
        note.title = noteTitleDisplay.text.toString()
        val noteTextDisplay = findViewById<EditText>(R.id.textNoteText)
        note.text = noteTextDisplay.text.toString()
        val spinnerDisplay = findViewById<Spinner>(R.id.spinnerCourses)
        note.course = spinnerDisplay.selectedItem as CourseInfo
    }

}