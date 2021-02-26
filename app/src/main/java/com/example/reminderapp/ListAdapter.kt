package com.example.reminderapp

import android.content.Context
import android.graphics.Paint
import android.transition.AutoTransition
import android.transition.TransitionManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView

class ListAdapter(private val context:Context, private val dataList:ArrayList<String>):
    RecyclerView.Adapter<ListAdapter.ItemViewHolder>() {

    inner class ItemViewHolder(itemView:View, viewGroup: ViewGroup): RecyclerView.ViewHolder(itemView) {
        private val title=itemView.findViewById<TextView>(R.id.textTitle)

        private val cardview=viewGroup
        private val subTitleLayout=itemView.findViewById<ConstraintLayout>(R.id.subtitlelayout)
        private val alarmLayout=itemView.findViewById<ConstraintLayout>(R.id.alarmlayout)

        private val doneBox=itemView.findViewById<CheckBox>(R.id.donecheckbox)

        var pos=-1;
        fun sPos(position: Int)
        {
            pos=position
        }
        fun onBind(data:String, context: Context) {
            title.setText(data)
            doneBox.setOnCheckedChangeListener { buttonView, isChecked ->
                if(isChecked) {
                    val autoTransition = AutoTransition()
                    autoTransition.duration = 100
                    TransitionManager.beginDelayedTransition(cardview,autoTransition)
                    title.setPaintFlags(title.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG)
                    title.setAlpha(0.3f)

                    subTitleLayout.setVisibility(View.GONE)
                    alarmLayout.setVisibility(View.GONE)


                }
                else {
                    val autoTransition = AutoTransition()
                    autoTransition.duration = 100
                    TransitionManager.beginDelayedTransition(cardview,autoTransition)
                    title.setPaintFlags(0)
                    title.setAlpha(1f)

                    subTitleLayout.setVisibility(View.VISIBLE)
                    alarmLayout.setVisibility(View.VISIBLE)
                }
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view=LayoutInflater.from(context).inflate(R.layout.todolist,parent,false)
        return ItemViewHolder(view, parent)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.sPos(position)
        holder.onBind(dataList.get(position), context)
    }
}