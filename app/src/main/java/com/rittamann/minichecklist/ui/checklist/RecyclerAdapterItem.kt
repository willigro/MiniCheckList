package com.rittamann.minichecklist.ui.checklist

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.rittamann.minichecklist.R
import com.rittamann.minichecklist.data.base.Item
import com.rittamann.minichecklist.ui.keepitem.KeepItemActivity
import com.rittamann.minichecklist.utils.Constants

class RecyclerAdapterItem(private val context: Context, private val list: List<Item>) :
    RecyclerView.Adapter<RecyclerAdapterItem.ViewHolderItem>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderItem {
        return ViewHolderItem(
            LayoutInflater.from(context).inflate(
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
            holder.title.apply {
                text = if (it.isEmpty()) {
                    setTextColor(ContextCompat.getColor(context, R.color.textColorLight))
                    "Sem valor"
                } else {
                    val end = if (it.length >= 20) 20 else it.length
                    it.substring(0, end)
                }
            }
        }
        holder.layout.setOnClickListener {
            Intent(context, KeepItemActivity::class.java).apply {
                putExtra(Constants.ITEM_ARGS, item)
                context.startActivity(this)
            }
        }
    }

    class ViewHolderItem(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.txtTitle)
        val layout: View = itemView.findViewById(R.id.adapterLayout)
    }
}