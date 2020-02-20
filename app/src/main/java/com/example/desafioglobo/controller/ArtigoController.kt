package com.example.desafioglobo.controller

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.desafioglobo.model.Artigos

class ArtigoController(context: Context,
                       factory: SQLiteDatabase.CursorFactory?) :
    SQLiteOpenHelper(context, DATABASE_NAME,
        factory, DATABASE_VERSION) {
    override fun onCreate(db: SQLiteDatabase) {
        val CREATE_FAVORITOS_TABLE = ("CREATE TABLE IF NOT EXISTS  " +
                TABLE_NAME_FAVORITOS + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY," +
                COLUMN_AUTOR + " TEXT," +
                COLUMN_TITULO + " TEXT," +
                COLUMN_TEXTO + " TEXT," +
                COLUMN_DATA + " DATETIME" +
                ")")
        db.execSQL(CREATE_FAVORITOS_TABLE)


        val CREATE_IMAGENS_TABLE = ("CREATE TABLE IF NOT EXISTS  " +
                TABLE_NAME_IMAGENS + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                COLUMN_ID_ARTIGO + " INTEGER," +
                COLUMN_IMAGEM + " TEXT" +
                ")")
        db.execSQL(CREATE_IMAGENS_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        onCreate(db)
    }

    fun adicionaArtigoFavorito(artigo : Artigos) {
        val db = this.writableDatabase

        val values = ContentValues()
        values.put(COLUMN_ID, artigo.id)
        values.put(COLUMN_AUTOR, artigo.autor)
        values.put(COLUMN_TITULO, artigo.titulo)
        values.put(COLUMN_TEXTO, artigo.texto)
        values.put(COLUMN_DATA, artigo.data)

        db.insert(TABLE_NAME_FAVORITOS, null, values)

        for(img in artigo.imagens) {
            val valuesImg = ContentValues()
            valuesImg.put(COLUMN_ID_ARTIGO, artigo.id)
            valuesImg.put(COLUMN_IMAGEM, img)

            db.insert(TABLE_NAME_IMAGENS, null, valuesImg)
        }

        db.close()
    }

    fun verificaFavorito(id: Int): Cursor? {
        val db = this.readableDatabase
        return db.rawQuery("SELECT * FROM $TABLE_NAME_FAVORITOS WHERE ID = $id", null)
    }

    fun carregaArtigos(): ArrayList<Artigos>? {
        val lista : ArrayList<Artigos> = ArrayList()
        val db = this.readableDatabase
        val cursor : Cursor = db.rawQuery("SELECT * FROM $TABLE_NAME_FAVORITOS", null)
        cursor.moveToFirst()
        if(cursor.count > 0) {
            do{
                val artigo = Artigos()
                artigo.id = cursor.getInt(cursor.getColumnIndex(COLUMN_ID))
                artigo.autor = cursor.getString(cursor.getColumnIndex(COLUMN_AUTOR))
                artigo.titulo = cursor.getString(cursor.getColumnIndex(COLUMN_TITULO))
                artigo.texto =cursor.getString(cursor.getColumnIndex(COLUMN_TEXTO))
                artigo.data = cursor.getString(cursor.getColumnIndex(COLUMN_DATA))

                val cursorImagens : Cursor = db.rawQuery("SELECT * FROM $TABLE_NAME_IMAGENS WHERE ID_ARTIGO = ${artigo.id}", null)
                cursorImagens.moveToFirst()
                do{
                    artigo.imagens.add(cursorImagens.getString(cursorImagens.getColumnIndex(COLUMN_IMAGEM)))
                }while (cursorImagens.moveToNext())
                cursorImagens.close()

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

        //FAVORITOS
        val TABLE_NAME_FAVORITOS = "favoritos"
        val COLUMN_ID = "ID"
        val COLUMN_AUTOR = "AUTOR"
        val COLUMN_TITULO = "TITULO"
        val COLUMN_TEXTO = "TEXTO"
        val COLUMN_DATA = "DATA"

        //IMAGENS
        val COLUMN_ID_ARTIGO = "ID_ARTIGO"
        val COLUMN_IMAGEM = "IMAGEM"
        val TABLE_NAME_IMAGENS = "imagens"
    }
}
