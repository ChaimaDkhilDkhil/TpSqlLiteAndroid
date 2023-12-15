package com.example.sqllite

import EtudiantDBHelper
import android.database.Cursor
import android.os.Bundle
import android.widget.ListView
import android.widget.SimpleCursorAdapter
import androidx.appcompat.app.AppCompatActivity

class ListEtudiant : AppCompatActivity() {
    private lateinit var listEtudiant: ListView
    private lateinit var adapter: SimpleCursorAdapter
    private var dbHelper: EtudiantDBHelper? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_etudiant)

        listEtudiant = findViewById(R.id.idlistetu)
        adapter = createAdapter()
        listEtudiant.adapter = adapter
    }

    private fun createAdapter(): SimpleCursorAdapter {
        val dbHelper = EtudiantDBHelper(this)
        val db = dbHelper.readableDatabase

        val cursor: Cursor = db.rawQuery("SELECT * FROM ${EtudiantBC.EtudiantEntry.TABLE_NAME}", null)

        val fromColumns = arrayOf(
            EtudiantBC.EtudiantEntry.COLUMN_NAME_NOM,
            EtudiantBC.EtudiantEntry.COLUMN_NAME_PRENOM
        )

        val toViews = intArrayOf(R.id.nometud, R.id.preetud)

        return SimpleCursorAdapter(
            this,
            R.layout.ligne_etudiant,
            cursor,
            fromColumns,
            toViews,
            0
        )
    }

    private fun getDbHelper(): EtudiantDBHelper {
        if (dbHelper == null) {
            dbHelper = EtudiantDBHelper(this)
        }
        return dbHelper!!
    }
}
