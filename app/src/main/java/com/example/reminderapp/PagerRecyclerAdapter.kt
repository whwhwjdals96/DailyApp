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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class PagerRecyclerAdapter(private val context:Context, private val dataList:ArrayList<ArrayList<String>>):
    RecyclerView.Adapter<PagerRecyclerAdapter.ItemViewHolder>() {
    inner class ItemViewHolder(itemView:View, viewGroup: ViewGroup): RecyclerView.ViewHolder(itemView) {
        val subdata=ArrayList<String>()
        var pos=-1;
        fun sPos(position: Int)
        {
            pos=position
        }
        fun onBind(context: Context) {
            callData(pos)
            val mRecyclerView=itemView.findViewById<RecyclerView>(R.id.todoitemlist)
            val mAdapter=ListAdapter(context, subdata)
            mRecyclerView.layoutManager= LinearLayoutManager(context)
            mRecyclerView.adapter=mAdapter
            mRecyclerView.setItemViewCacheSize(20)
        }

        fun callData(pos :Int) //여기서 DB서 날짜정보 가져온다
        {
            subdata.clear()
            for(j in 1..10){
                var tmp:Int=j*pos
                subdata.add("$tmp 번째")
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view=LayoutInflater.from(context).inflate(R.layout.fragment_listlayout,parent,false)
        return ItemViewHolder(view, parent)
    }

    override fun getItemCount(): Int {
        return Integer.MAX_VALUE;
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.sPos(position)
        holder.onBind(context)
    }

    fun addItem(data: ArrayList<String>, addPos: Int)
    {
        dataList.add(addPos, data)
    }
}