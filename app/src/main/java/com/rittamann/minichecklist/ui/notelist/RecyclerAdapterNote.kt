package com.rittamann.minichecklist.ui.notelist

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.rittamann.minichecklist.R
import com.rittamann.minichecklist.data.base.Note
import com.rittamann.minichecklist.ui.base.BaseActivity
import com.rittamann.minichecklist.ui.keepnote.KeepNoteActivity
import com.rittamann.minichecklist.utils.Constants
import com.rittamann.minichecklist.utils.DateUtil
import com.rittamann.minichecklist.utils.RequestCode

class RecyclerAdapterNote(private val baseActivity: BaseActivity, private val list: List<Note>) :
    RecyclerView.Adapter<RecyclerAdapterNote.ViewHolderItem>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderItem {
        return ViewHolderItem(
            LayoutInflater.from(baseActivity).inflate(
                R.layout.adapter_recycler_note,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ViewHolderItem, position: Int) {
        val note = list[holder.adapterPosition]
        bindNote(holder, note)
        holder.layout.setOnClickListener {
            Intent(baseActivity, KeepNoteActivity::class.java).apply {
                putExtra(Constants.ITEM_ARGS, note)
                (baseActivity).startActivityForResult(this, RequestCode.KEEP_NOTE)
            }
        }
    }

    private fun bindNote(holder: ViewHolderItem, note: Note) {
        note.content.also {
            holder.title.apply {
                text = if (it.isEmpty()) {
                    ""
                } else {
                    extractTitle(it)
                }
            }
        }


        val date = DateUtil.parseDateRepresentative(note.createCate)
        holder.createdDate.text = if (note.idApi > 0) {
            "$date - registrado na nuvem"
        } else
            date
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

    fun noteDeleted(note: Note) {
        (list as ArrayList).apply {
            for (index in 0 until size) {
                if (note.id == this[index].id) {
                    remove(this[index])
                    notifyItemRemoved(index)
                    break
                }
            }
        }
    }

    fun noteUpdated(note: Note) {
        (list as ArrayList).apply {
            for (index in 0 until size) {
                if (note.id == this[index].id) {
                    this[index] = note
                    notifyItemChanged(index)
                    break
                }
            }
        }
    }

    fun newNote(item: Note) {
        var found = false
        for (noteInList in list)
            if (noteInList.id == item.id) {
                found = true
                break
            }

        if (found.not()) {
            (list as ArrayList).add(0, item)
        }
        notifyItemInserted(0)
    }

    class ViewHolderItem(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.txtTitle)
        val layout: View = itemView.findViewById(R.id.adapterLayout)
        val createdDate: TextView = itemView.findViewById(R.id.txtCreatedDate)
    }
}