package com.rittamann.minichecklist.ui.notelist

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.rittamann.minichecklist.R
import com.rittamann.minichecklist.data.base.Note
import com.rittamann.minichecklist.ui.keepnote.KeepNoteActivity
import com.rittamann.minichecklist.utils.Constants

class RecyclerAdapterNote(private val context: Context, private val list: List<Note>) :
    RecyclerView.Adapter<RecyclerAdapterNote.ViewHolderItem>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderItem {
        return ViewHolderItem(
            LayoutInflater.from(context).inflate(
                R.layout.adapter_recycler_note,
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
                    extractTitle(it)
                }
            }
        }
        holder.layout.setOnClickListener {
            Intent(context, KeepNoteActivity::class.java).apply {
                putExtra(Constants.ITEM_ARGS, item)
                context.startActivity(this)
            }
        }
    }

    private fun extractTitle(title: String): String {
        val end = if (title.length >= 20) 20 else title.length
        title.substring(0, end).also { result ->
            if (result.contains("\n")) {
                val index = result.indexOf("\n")
                if (index > 0) {
                    val resultSub = result.substring(0, index)
                    if (resultSub.isBlank())
                        return "Sem valor"
                    return resultSub
                }
            }
            return result
        }
    }

    fun forceUpdate(list: List<Note>) {
        (this.list as ArrayList).apply {
            clear()
            addAll(list)
            notifyDataSetChanged()
        }
    }

    class ViewHolderItem(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.txtTitle)
        val layout: View = itemView.findViewById(R.id.adapterLayout)
    }
}