import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class MyDatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object {
        private const val DATABASE_NAME = "MyDatabase.db"
        private const val DATABASE_VERSION = 1
        private const val SQL_CREATE_TABLE = "CREATE TABLE MyTable (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name TEXT," +
                "age INTEGER)"

    }
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREATE_TABLE)
    }
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS MyTable")
        onCreate(db)
    }
    fun insertData(name: String, age: Int): Long {
        val db = writableDatabase
        val values = ContentValues().apply {
            put("name", name)
            put("age", age)
        }
        return db.insert("MyTable", null, values)
    }
    fun readData(): List<String> {
        val db = readableDatabase
        val projection = arrayOf("id", "name", "age")
        val cursor = db.query(
            "MyTable",
            projection,
            null,
            null,
            null,
            null,
            null
        )

        val itemList = mutableListOf<String>()
        with(cursor) {
            while (moveToNext()) {
                val id = getInt(getColumnIndexOrThrow("id"))
                val name = getString(getColumnIndexOrThrow("name"))
                val age = getInt(getColumnIndexOrThrow("age"))
                itemList.add("ID: $id, Name: $name, Age: $age")
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
        return db.delete("MyTable", selection, selectionArgs)
    }

}