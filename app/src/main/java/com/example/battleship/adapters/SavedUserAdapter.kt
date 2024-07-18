package com.example.battleship.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Filter
import android.widget.TextView
import com.example.battleship.localDatabase.savedUser.SavedUser

class SavedUserAdapter(context: Context, private val users: List<SavedUser>) : ArrayAdapter<SavedUser>(context, android.R.layout.simple_dropdown_item_1line, users) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(android.R.layout.simple_dropdown_item_1line, parent, false)
        val user = getItem(position)
        (view as TextView).text = user?.account
        return view
    }

    override fun getItem(position: Int): SavedUser? {
        return users[position]
    }

    override fun getCount(): Int {
        return users.size
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val results = FilterResults()
                val filteredUsers = if (constraint.isNullOrEmpty()) {
                    users
                } else {
                    users.filter { it.account.contains(constraint, ignoreCase = true) }
                }
                results.values = filteredUsers
                results.count = filteredUsers.size
                return results
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                clear()
                if (results?.values is List<*>) {
                    addAll(results.values as List<SavedUser>)
                    notifyDataSetChanged()
                }
            }
        }
    }
}



