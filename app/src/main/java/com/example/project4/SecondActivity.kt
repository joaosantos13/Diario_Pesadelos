package com.example.project4
import MyDatabaseHelper
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.project4.R

class SecondActivity : AppCompatActivity() {
    private lateinit var dbHelper: MyDatabaseHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)
        dbHelper = MyDatabaseHelper(this)
        val editTextName = findViewById<EditText>(R.id.editTextName)
        val editTextAge = findViewById<EditText>(R.id.editTextAge)
        val buttonInsert = findViewById<Button>(R.id.buttonInsert)
        val buttonView = findViewById<Button>(R.id.buttonView)
        val textViewData = findViewById<TextView>(R.id.textViewData)
        val delBtn = findViewById<Button>(R.id.buttonDelete)
        val delText = findViewById<TextView>(R.id.delText)

        buttonInsert.setOnClickListener {
            val name = editTextName.text.toString()
            val age = editTextAge.text.toString().toIntOrNull()
            if (name.isNotEmpty() && age != null) {
                dbHelper.insertData(name, age)
                editTextName.text.clear()
                editTextAge.text.clear()
            }
        }
        // Evento de clique para visualizar dados
        buttonView.setOnClickListener {
            val data = dbHelper.readData()
            textViewData.text = data.joinToString("\n")
        }

        delBtn.setOnClickListener{
            val id = delText.text.toString().toInt()
            dbHelper.deleteData(id)
        }

    }
}
