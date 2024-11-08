package com.example.project4

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RecordsAdapter(private val records: List<String>) : RecyclerView.Adapter<RecordsAdapter.RecordViewHolder>() {
    class RecordViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val id: TextView = view.findViewById(R.id.textViewRecordId)
        /*val title: TextView = view.findViewById(R.id.textViewRecordTitle)
        val data: TextView = view.findViewById(R.id.textViewRecordData)
        val pesadelo: TextView = view.findViewById(R.id.textViewRecordPesadelo)
        val description: TextView = view.findViewById(R.id.textViewRecordDescription)*/
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecordViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_record, parent, false)
        return RecordViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecordViewHolder, position: Int) {
        val record = records[position].split(", ")
        holder.id.text = record[0]  // id
        /*holder.title.text = record[2]  // Titulo
        holder.data.text = record[3]   // Data
        holder.pesadelo.text = record[4]   // pesadelo
        holder.description.text = record[5] // Descrição*/
    }

    override fun getItemCount() = records.size
}