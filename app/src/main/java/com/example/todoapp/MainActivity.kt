package com.example.todoapp

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {
    private lateinit var items: ArrayList<ToDoAppItem>
    private lateinit var recyItems: RecyclerView
    private lateinit var recyAdapter: ToDoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        items = arrayListOf()

        recyItems = findViewById<RecyclerView>(R.id.recyItems)
        recyAdapter = ToDoAdapter(items)
        recyItems.adapter = recyAdapter
        recyItems.layoutManager = LinearLayoutManager(this)

        val ico = findViewById<FloatingActionButton>(R.id.ico)
        ico.setOnClickListener {
            customDialog()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        var itemsDeleted = 0
        for(i in items){
            if(i.checked){itemsDeleted++}
        }

        if(itemsDeleted > 0){
            Toast.makeText(this, "$itemsDeleted items deleted", Toast.LENGTH_LONG).show()
        }else{
            Toast.makeText(this, "No items selected", Toast.LENGTH_LONG).show()
        }
        recyAdapter.deleteItems()
        return super.onOptionsItemSelected(item)
    }

    private fun customDialog(){
        val dialogBuilder = AlertDialog.Builder(this)

        val layout = LinearLayout(this)
        layout.orientation = LinearLayout.VERTICAL

        val toDo = EditText(this)
        toDo.hint = "Enter to-do item"
        layout.addView(toDo)

        dialogBuilder.setPositiveButton("ADD", DialogInterface.OnClickListener {
                dialog, id -> items.add(ToDoAppItem(toDo.text.toString()))
        })
            .setNegativeButton("CANCEL", DialogInterface.OnClickListener {
                    dialog, id -> dialog.cancel()
            })

        val alert = dialogBuilder.create()
        alert.setTitle("New Item")
        alert.setView(layout)
        alert.show()
    }
}