import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class MyDatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object {
        private const val DATABASE_NAME = "MyDatabase.db"
        private const val DATABASE_VERSION = 2
        private const val SQL_CREATE_TABLE = "CREATE TABLE Diary (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "titulo TEXT," +
                "data TEXT," +
                "pesadelo TEXT," +
                "descricao INTEGER)"

    }
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREATE_TABLE)
    }
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS Diary")
        onCreate(db)
    }
    fun insertData(titulo: String, data: String, pesadelo: String, descricao: Int): Long {
        val db = writableDatabase
        val values = ContentValues().apply {
            put("titulo", titulo)
            put("data", data)
            put("pesadelo", pesadelo)
            put("descricao", descricao)
        }
        return db.insert("Diary", null, values)
    }
    fun readData(): List<String> {
        val db = readableDatabase
        val projection = arrayOf("id", "titulo", "data", "pesadelo", "descricao")
        val cursor = db.query(
            "Diary", // Nome da tabela
            projection, // Colunas a serem recuperadas
            null, // Sem cláusula WHERE
            null, // Sem argumentos para o WHERE
            null, // Sem agrupamento
            null, // Sem filtro de grupo de linhas
            null  // Sem ordenação específica
        )

        val itemList = mutableListOf<String>()
        with(cursor) {
            while (moveToNext()) {
                try {
                    val id = getInt(getColumnIndexOrThrow("id"))
                    val titulo = getString(getColumnIndexOrThrow("titulo"))
                    val data = getString(getColumnIndexOrThrow("data"))
                    val pesadelo = getString(getColumnIndexOrThrow("pesadelo"))
                    val descricao = getInt(getColumnIndexOrThrow("descricao"))

                    itemList.add("ID: $id, Titulo: $titulo, Data: $data, Pesadelo: $pesadelo, Descricao: $descricao")
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
        cursor.close()
        return itemList
    }


    fun deleteData(id: Int): Int {
        val db = writableDatabase
        // Define a cláusula WHERE para especificar o ID a ser excluído
        val selection = "id = ?"
        val selectionArgs = arrayOf(id.toString())
        // Executa o método delete e retorna o número de linhas excluídas
        return db.delete("Diary", selection, selectionArgs)
    }

}