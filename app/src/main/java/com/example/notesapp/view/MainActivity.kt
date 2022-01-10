package com.example.notesapp.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.notesapp.viewModel.NoteViewModel
import com.example.notesapp.R
import com.example.notesapp.databinding.ActivityMainBinding
import com.example.notesapp.model.Note

class MainActivity : AppCompatActivity(), INoteRVAdapter {

    private lateinit var binding: ActivityMainBinding

    lateinit var viewModel: NoteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rv.layoutManager=LinearLayoutManager(this)
        val adapter= NotesRVAdapter(this,this
        )
        binding.rv.adapter=adapter

        viewModel=ViewModelProvider(this,ViewModelProvider.AndroidViewModelFactory.getInstance(application)).get(
            NoteViewModel::class.java)
        viewModel.allNotes.observe(this, Observer {
            it?.let{
                adapter.updateList(it)
            }
        })

        binding.submitBtn.setOnClickListener {
            val noteText=findViewById<EditText>(R.id.input).text.toString()
            Log.d("tag",noteText)
            if(noteText.isNotEmpty()){
                viewModel.insertNote(Note(noteText))
                Toast.makeText(this,"$noteText added successfully",Toast.LENGTH_LONG).show()
            }

        }

    }

    override fun onItemClicked(note: Note) {
        viewModel.deleteNote(note)
        Toast.makeText(this,"${note.note} deleted successfully",Toast.LENGTH_LONG).show()
    }
}