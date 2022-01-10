package com.example.notesapp.model

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface NoteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
     fun insert(note: Note)

    @Delete
     fun delete(note: Note)

    @Query("select *from note_table")
    fun getAllNotes(): LiveData<List<Note>>

}