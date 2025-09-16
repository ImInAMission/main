package com.example.myassssmentapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class EntityAdapter(
    private var entities: List<Map<String, Any>>,
    private val onItemClick: (Map<String, Any>) -> Unit
) : RecyclerView.Adapter<EntityAdapter.EntityViewHolder>() {

    inner class EntityViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvName: TextView = itemView.findViewById(R.id.tvName)
        val tvType: TextView = itemView.findViewById(R.id.tvType)
        val tvDescription: TextView = itemView.findViewById(R.id.tvDescription)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EntityViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_entity, parent, false)
        return EntityViewHolder(view)
    }

    override fun onBindViewHolder(holder: EntityViewHolder, position: Int) {
        val props = entities[position]
        val keys = props.keys.toList()

        val title = keys.getOrNull(0)?.let { props[it].toString() } ?: "No Name"
        val subtitle = keys.getOrNull(1)?.let { props[it].toString() } ?: "Unknown"
        val description = props["description"]?.toString() ?: "No Description"

        holder.tvName.text = title
        holder.tvType.text = subtitle
        holder.tvDescription.text = description

        holder.itemView.setOnClickListener { onItemClick(props) }
    }

    override fun getItemCount(): Int = entities.size

    fun updateData(newEntities: List<Map<String, Any>>) {
        entities = newEntities
        notifyDataSetChanged()
    }
}
