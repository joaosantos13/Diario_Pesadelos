import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class MyDatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object {
        private const val DATABASE_NAME = "MyDatabase.db"
        private const val DATABASE_VERSION = 4
        private const val SQL_CREATE_TABLE = "CREATE TABLE Diary (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "titulo TEXT," +
                "data TEXT," +
                "pesadelo TEXT," +
                "descricao INTEGER," +
                "tag TEXT)"
    }


    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREATE_TABLE)
    }
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS Diary")
        onCreate(db)
    }
    fun insertData(titulo: String, data: String, pesadelo: String, descricao: Int, tag: String): Long {
        val db = writableDatabase
        val values = ContentValues().apply {
            put("titulo", titulo)
            put("data", data)
            put("pesadelo", pesadelo)
            put("descricao", descricao)
            put("tag", tag)
        }
        return db.insert("Diary", null, values)
    }
    fun readData(): List<String> {
        val db = readableDatabase
        val projection = arrayOf("id", "titulo", "data", "pesadelo", "descricao", "tag")
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
                    val tag = getString(getColumnIndexOrThrow("tag"))

                    itemList.add("Pesadelo: $id\nTitulo: $titulo\nSonhado em: $data\nDescrição: $pesadelo\nAssustador nível: $descricao\nTag: $tag")
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
        cursor.close()
        return itemList
    }

    fun readDataByTag(tag: String): List<String> {
        val db = readableDatabase
        val selection = "tag = ?"
        val selectionArgs = arrayOf(tag)
        val cursor = db.query(
            "Diary",
            arrayOf("id", "titulo", "data", "pesadelo", "descricao"),
            selection,
            selectionArgs,
            null,
            null,
            null
        )

        val itemList = mutableListOf<String>()
        with(cursor) {
            while (moveToNext()) {
                val id = getInt(getColumnIndexOrThrow("id"))
                val titulo = getString(getColumnIndexOrThrow("titulo"))
                val data = getString(getColumnIndexOrThrow("data"))
                val pesadelo = getString(getColumnIndexOrThrow("pesadelo"))
                val descricao = getInt(getColumnIndexOrThrow("descricao"))
                itemList.add("Pesadelo: $id\nTitulo: $titulo\nSonhado em: $data\nDescrição: $pesadelo\nAssustador nível: $descricao")
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

    fun updateData(id: Int, titulo: String, data: String, pesadelo: String, descricao: Int): Int {
        val db = writableDatabase
        val values = ContentValues().apply {
            put("titulo", titulo)
            put("data", data)
            put("pesadelo", pesadelo)
            put("descricao", descricao)
        }

        // Critério WHERE para identificar o registro a ser atualizado pelo ID
        val selection = "id = ?"
        val selectionArgs = arrayOf(id.toString())

        // Retorna o número de linhas afetadas pela atualização
        return db.update("Diary", values, selection, selectionArgs)
    }

}