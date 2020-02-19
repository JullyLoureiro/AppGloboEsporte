package com.example.desafioglobo.controller

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.desafioglobo.model.Artigos

class ArtigoDBHelper(context: Context,
                     factory: SQLiteDatabase.CursorFactory?) :
    SQLiteOpenHelper(context, DATABASE_NAME,
        factory, DATABASE_VERSION) {
    override fun onCreate(db: SQLiteDatabase) {
        val CREATE_ARTIGOS_TABLE = ("CREATE TABLE IF NOT EXISTS  " +
                TABLE_NAME_ARTIGOS + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY," +
                COLUMN_AUTOR + " TEXT," +
                COLUMN_TITULO + " TEXT," +
                COLUMN_TEXTO + " TEXT," +
                COLUMN_DATA + " DATETIME" +
                ")")
        db.execSQL(CREATE_ARTIGOS_TABLE)

        val CREATE_FAVORITOS_TABLE = ("CREATE TABLE IF NOT EXISTS  " +
                TABLE_NAME_FAVORITOS + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY" +
                ")")
        db.execSQL(CREATE_FAVORITOS_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        onCreate(db)
    }

    fun adicionaArtigoFavorito(id: Int) {
        val values = ContentValues()
        values.put(COLUMN_ID, id)
        val db = this.writableDatabase
        db.insert(TABLE_NAME_FAVORITOS, null, values)
        db.close()
    }

    fun verificaFavorito(id: Int): Cursor? {
        val db = this.readableDatabase
        return db.rawQuery("SELECT * FROM $TABLE_NAME_FAVORITOS WHERE ID = $id", null)
    }

    fun carregaArtigos(): List<Artigos>? {
        val lista : ArrayList<Artigos> = ArrayList()
        val db = this.readableDatabase
        val cursor : Cursor = db.rawQuery("SELECT * FROM $TABLE_NAME_FAVORITOS", null)
        cursor.moveToFirst()
        if(cursor.count == 0) {
            do{
                val artigo : Artigos?=null
                artigo!!.id = cursor.getInt(cursor.getColumnIndex(cursor.getColumnName(0)))
                artigo.autor = cursor.getString(cursor.getColumnIndex(cursor.getColumnName(1)))
                artigo.titulo = cursor.getString(cursor.getColumnIndex(cursor.getColumnName(2)))
                artigo.texto = cursor.getString(cursor.getColumnIndex(cursor.getColumnName(3)))
                artigo.data = cursor.getString(cursor.getColumnIndex(cursor.getColumnName(4)))

                lista.add(artigo)
            }while (cursor.moveToNext())
        }
        cursor.close()
        return lista
    }

    fun removeFavorito(id: String): Int {
        val db = this.writableDatabase
        return db.delete(TABLE_NAME_FAVORITOS,"ID = ?", arrayOf(id))
    }

    companion object {
        private val DATABASE_VERSION = 1
        private val DATABASE_NAME = "GloboEsporte.db"
        val TABLE_NAME_ARTIGOS = "artigos"
        val TABLE_NAME_FAVORITOS = "favoritos"
        val COLUMN_ID = "ID"
        val COLUMN_AUTOR = "AUTOR"
        val COLUMN_TITULO = "TITULO"
        val COLUMN_TEXTO = "TEXTO"
        val COLUMN_DATA = "DATA"

    }
}
