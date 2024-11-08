package com.example.project4
import MyDatabaseHelper
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.util.Calendar

class SecondActivity : AppCompatActivity() {
    private lateinit var dbHelper: MyDatabaseHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        dbHelper = MyDatabaseHelper(this)

        val tituloText = findViewById<EditText>(R.id.tituloText)
        val pesadeloText = findViewById<EditText>(R.id.pesadeloText)
        val buttonInsert = findViewById<Button>(R.id.buttonInsert)
        val buttonView = findViewById<Button>(R.id.buttonView)
        val textViewData = findViewById<TextView>(R.id.textViewData)
        val delBtn = findViewById<Button>(R.id.buttonDelete)
        val delText = findViewById<TextView>(R.id.delText)
        val classText = findViewById<EditText>(R.id.classText)
        val dataText = findViewById<EditText>(R.id.dataText)
        val buttonUpdate = findViewById<Button>(R.id.buttonUpdate)

        buttonInsert.setOnClickListener {
            val titulo = tituloText.text.toString()
            val pesadelo = pesadeloText.text.toString()
            val classificação = classText.text.toString().toIntOrNull()
            val data = dataText.text.toString()

            if (titulo.isNotEmpty() && data.isNotEmpty() && pesadelo.isNotEmpty() && classificação != null) {
                dbHelper.insertData(titulo, data, pesadelo,classificação)

                tituloText.text.clear()
                dataText.text.clear()
                pesadeloText.text.clear()
                classText.text.clear()
            }
        }


        buttonView.setOnClickListener {
            val intent = Intent(this, Records::class.java)
            startActivity(intent)
        }

        delBtn.setOnClickListener{
            val id = delText.text.toString().toInt()
            dbHelper.deleteData(id)
        }

        dataText.setOnClickListener{
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            DatePickerDialog(this, { _, selectedYear, selectedMonth, selectedDay ->
                // Formata a data selecionada como "yyyy-MM-dd"
                val formattedDate = String.format("%04d-%02d-%02d", selectedYear, selectedMonth + 1, selectedDay)
                dataText.setText(formattedDate)
            }, year, month, day).show()
        }

        buttonUpdate.setOnClickListener {
            val id = delText.text.toString().toIntOrNull()
            val titulo = tituloText.text.toString()
            val data = dataText.text.toString()
            val pesadelo = pesadeloText.text.toString()
            val descricao = classText.text.toString().toIntOrNull()

            if (id != null && titulo.isNotEmpty() && data.isNotEmpty() && pesadelo.isNotEmpty() && descricao != null) {
                val rowsAffected = dbHelper.updateData(id, titulo, data, pesadelo, descricao)
                if (rowsAffected > 0) {
                    textViewData.text = "Registro atualizado com sucesso!"
                } else {
                    textViewData.text = "Falha ao atualizar o registro."
                }
            } else {
                textViewData.text = "Preencha todos os campos corretamente."
            }
        }

    }
}