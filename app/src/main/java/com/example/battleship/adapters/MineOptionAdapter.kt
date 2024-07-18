package com.example.battleship.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.battleship.R
import com.example.battleship.bean.MineOption

class MineOptionAdapter(
    private val options: List<MineOption>,
) : RecyclerView.Adapter<MineOptionAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val iconView: ImageView = view.findViewById(R.id.option_icon)
        private val textView: TextView = view.findViewById(R.id.option_text)

        fun bind(option: MineOption) {
            iconView.setImageResource(option.iconResId)
            textView.text = option.text
            itemView.setOnClickListener {
                Log.d("TAG", "bind: dasdasdasdadada")
                option.onclickAction()
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        Log.d("dasdas","create")
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_mine_option, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(options[position])
    }

    override fun getItemCount() = options.size
}
