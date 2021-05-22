package com.example.noteappsharedpref.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.noteappsharedpref.databinding.ListItemBinding

class ItemAdapter : RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {
    private var dataset : Set<String> = setOf()

    inner class ItemViewHolder(private val binding:ListItemBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(note:String){
            binding.root.setOnClickListener {
                listener?.invoke(adapterPosition)
            }

            binding.noteText.text = note
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(ListItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = dataset.elementAt(position)
        holder.bind(item)
    }

    override fun getItemCount() = dataset.size

    fun setData(set: Set<String>){
        dataset = set
        notifyDataSetChanged()
    }

    private var listener : ((position:Int) -> Unit)? = null

    fun setOnItemClickListener(listener : (position : Int) -> Unit){
        this.listener = listener
    }
}