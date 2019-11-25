package id.co.blogspot.fikriprayoga.www.footballmatchschedule.backendcontroller

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import org.jetbrains.anko.db.*

class MyDatabaseOpenHelper(ctx: Context) : ManagedSQLiteOpenHelper(ctx, "MyDatabase", null, 1) {
    companion object {
        private var instance: MyDatabaseOpenHelper? = null

        @Synchronized
        fun getInstance(ctx: Context): MyDatabaseOpenHelper {
            if (instance == null) {
                instance = MyDatabaseOpenHelper(ctx.applicationContext)
            }
            return instance as MyDatabaseOpenHelper
        }
    }

    override fun onCreate(db: SQLiteDatabase) {
        // Here you create tables
        db.createTable("FavoriteEvent", true, "ID" to INTEGER + PRIMARY_KEY + AUTOINCREMENT, "LeagueID" to TEXT, "EventID" to TEXT, "Data" to TEXT)
        db.createTable("FavoriteTeam", true, "ID" to INTEGER + PRIMARY_KEY + AUTOINCREMENT, "LeagueID" to TEXT, "TeamID" to TEXT, "Data" to TEXT)
        db.createTable("NextEventAlarm", true, "ID" to INTEGER + PRIMARY_KEY + AUTOINCREMENT, "LeagueID" to TEXT, "EventID" to TEXT, "Data" to TEXT)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // Here you can upgrade tables, as usual
        db.dropTable("FavoriteEvent", true)
        db.dropTable("FavoriteTeam", true)
        db.dropTable("NextEventAlarm", true)
    }
}

// Access property for Context
val Context.database: MyDatabaseOpenHelper
    get() = MyDatabaseOpenHelper.getInstance(applicationContext)