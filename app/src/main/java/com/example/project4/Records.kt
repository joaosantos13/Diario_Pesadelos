package com.example.project4

import MyDatabaseHelper
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class Records : AppCompatActivity() {
    private lateinit var dbHelper: MyDatabaseHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_records)

        dbHelper = MyDatabaseHelper(this)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerViewRecords)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val data = dbHelper.readData() // Obter todos os registros
        recyclerView.adapter = RecordsAdapter(data)
    }
}