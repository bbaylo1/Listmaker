package com.brandonbaylosis.listmaker

import android.content.Context
import androidx.preference.PreferenceManager

class ListDataManager(private val context: Context) {
    fun saveList(list: TaskList) {
        // 1 Get reference to app's default SharedPreference
        // Append .edit() to get a .Editor instance to write key-value
        // pairs to SharedPreferences
        val sharedPreferences =
                PreferenceManager.getDefaultSharedPreferences(context).edit()
        // 2 Write TaskList to SharedPreferences as a set of strings
        // Convert tasks to HashSet to ensure unique values in the list
        sharedPreferences.putStringSet(list.name, list.tasks.toHashSet())
        // 3 Applies the changes
        sharedPreferences.apply()
    }
    fun readLists(): ArrayList<TaskList> {
        // 1 Grabs reference to default SharedPreferences file
        val sharedPreferences =
                PreferenceManager.getDefaultSharedPreferences(context)
        // 2 Calls sharedPreferences.all to get contents as a Map
        val sharedPreferenceContents = sharedPreferences.all
        // 3 Creates an empty ArrayList
        val taskLists = ArrayList<TaskList>()
        // 4 Iterates over items in the Map in a for loop
        for (taskList in sharedPreferenceContents) {
            // Takes the value of the object and casts it to a HashSet<String>
            val itemsHashSet = ArrayList(taskList.value as HashSet<String>)
            val list = TaskList(taskList.key, itemsHashSet)
            // 5 Adds reconstructed TaskList to empty ArrayList
            taskLists.add(list)
        }
        // 6 Returns contents to caller
        return taskLists
    }
}