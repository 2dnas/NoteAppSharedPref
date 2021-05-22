package com.example.noteappsharedpref

import android.content.ClipData
import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.noteappsharedpref.adapter.ItemAdapter

import com.example.noteappsharedpref.databinding.ActivityMainBinding
import com.example.noteappsharedpref.databinding.AddNoteBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialog

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    var noteList: MutableSet<String>? = null
    lateinit var sharedPref: SharedPreferences
    lateinit var adapter: ItemAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPref = getSharedPreferences("myPref", Context.MODE_PRIVATE)

        noteList = sharedPref.getStringSet("note", mutableSetOf())

        adapter = ItemAdapter()

        initRecycler()



        binding.addNoteFab.setOnClickListener {
            showBottomSheet()
        }
    }


    private fun showBottomSheet() {
        val bottomSheetDialog = BottomSheetDialog(this)
        bottomSheetDialog.setContentView(R.layout.add_note_bottom_sheet)
        val button = bottomSheetDialog.findViewById<Button>(R.id.add_note_button)
        val textEditText = bottomSheetDialog.findViewById<EditText>(R.id.add_note_edit_text)
        button?.setOnClickListener {
            val text = textEditText?.text.toString()
            if (text.isNotEmpty()) {
                noteList?.add(text)
                if (sharedPref.contains("note")) {
                    sharedPref.edit().remove("note").apply()
                }
                sharedPref.edit().putStringSet("note", noteList).apply()
                Toast.makeText(this, "Note Added Successfully", Toast.LENGTH_SHORT).show()
                adapter.setData(noteList!!)
            }
            bottomSheetDialog.dismiss()
        }

        bottomSheetDialog.show()
    }

    private fun initRecycler() {
        binding.noteRecyclerview.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = this@MainActivity.adapter.apply {
                setData(noteList!!)
                initItemClick()
            }
        }
    }

    private fun initItemClick(){
        adapter.setOnItemClickListener {
            val elementText = noteList?.elementAt(it)
            noteList?.remove(elementText)
            sharedPref.edit().remove("note").apply()
            sharedPref.edit().putStringSet("note",noteList).apply()
            adapter.setData(noteList!!)
        }
    }

}