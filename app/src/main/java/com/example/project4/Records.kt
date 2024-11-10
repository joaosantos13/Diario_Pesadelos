package com.example.project4

import MyDatabaseHelper
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class Records : AppCompatActivity() {
    private lateinit var dbHelper: MyDatabaseHelper
    private lateinit var recyclerView: RecyclerView
    private lateinit var recordsAdapter: RecordsAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_records)

        dbHelper = MyDatabaseHelper(this)
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerViewRecords)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val selectedTag = intent.getStringExtra("selected_tag") ?: ""

        // Filtra os dados com base na tag recebida
        val data = if (selectedTag.isNotEmpty()) {
            dbHelper.readDataByTag(selectedTag)
        } else {
            dbHelper.readData() // Recupera todos os dados se nenhuma tag for selecionada
        }

        recordsAdapter = RecordsAdapter(data)
        recyclerView.adapter = recordsAdapter
    }
}