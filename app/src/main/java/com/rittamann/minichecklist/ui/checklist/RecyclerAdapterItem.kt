package com.rittamann.minichecklist.ui.checklist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.rittamann.minichecklist.R
import com.rittamann.minichecklist.data.base.Item

class RecyclerAdapterItem(private val list:List<Item>) :
    RecyclerView.Adapter<RecyclerAdapterItem.ViewHolderItem>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderItem {
        return ViewHolderItem(
            LayoutInflater.from(parent.context).inflate(
                R.layout.adapter_recycker_item,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ViewHolderItem, position: Int) {
        val item = list[holder.adapterPosition]
        item.content.also {
            if (it.isEmpty()) return
            val end = if (it.length >= 20) 20 else it.length
            holder.title.text = it.substring(0, end)
        }
    }

    class ViewHolderItem(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.title)
    }
}