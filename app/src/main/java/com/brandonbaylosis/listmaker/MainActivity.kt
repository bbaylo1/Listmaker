package com.brandonbaylosis.listmaker

import android.content.Intent
import android.os.Bundle
import android.text.InputType
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity(), ListSelectionRecyclerViewAdapter.ListSelectionRecyclerViewClickListener {

    lateinit var listsRecyclerView: RecyclerView
    val listDataManager: ListDataManager = ListDataManager(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))

        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener {
            showCreateListDialog()
        }
        // 1 Get list of TaskLists from listDataManager
        val lists = listDataManager.readLists()
        listsRecyclerView =
                findViewById<RecyclerView>(R.id.lists_recyclerview)
        listsRecyclerView.layoutManager = LinearLayoutManager(this)
        // 2
        listsRecyclerView.adapter =
                ListSelectionRecyclerViewAdapter(lists, this)

    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun showCreateListDialog() {
        // 1 Retrieve strings
        val dialogTitle = getString(R.string.name_of_list)
        val positiveButtonTitle = getString(R.string.create_list)
        // 2
        val builder = AlertDialog.Builder(this) // Helps construct Dialog
        val listTitleEditText = EditText(this) // Serves as input field
        listTitleEditText.inputType = InputType.TYPE_CLASS_TEXT
        builder.setTitle(dialogTitle) // Sets title
        builder.setView(listTitleEditText) // Sets content View
        // 3 Positive button tells the Dialog a positive action has occurred
        builder.setPositiveButton(positiveButtonTitle) { dialog, _ ->
            // Creates empty taskList to save to SharedPreferences
            val list = TaskList(listTitleEditText.text.toString())
            listDataManager.saveList(list)
            // Cast RecyclerView as a custom adapter
            val recyclerAdapter = listsRecyclerView.adapter as
                    ListSelectionRecyclerViewAdapter
            recyclerAdapter.addList(list)
            // Pass TaskList into the adapter via addList
            recyclerAdapter.addList(list)
            dialog.dismiss() // Dismisses the Dialog
            showListDetail(list) // Call to showListDetail
        }
        // 4 Create and display the Dialog
        builder.create().show()
    }
    private fun showListDetail(list: TaskList) {
        // 1 Create Intent and pass in the current Activity and class of Activity shown on screen
        val listDetailIntent = Intent(this,ListDetailActivity::class.java)
        // 2 Add an Extra to display a list
        listDetailIntent.putExtra(INTENT_LIST_KEY, list)
        // 3 Starts another activity
        startActivityForResult(listDetailIntent, LIST_DETAIL_REQUEST_CODE)
    }

    // Conforms to interface
    override fun listItemClicked(list: TaskList) {
        showListDetail(list)
    }

    companion object {
        const val INTENT_LIST_KEY = "list"
        const val LIST_DETAIL_REQUEST_CODE = 123
    }
}