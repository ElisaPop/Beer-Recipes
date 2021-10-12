package com.example.beerrecipes

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.beerrecipes.entity.RecipeData
import java.util.*

class BeerAdapter(context: Context, private val dataSource: LinkedList<RecipeData>, private val onNoteListener: OnNoteListener)
    : RecyclerView.Adapter<MyViewHolder>(){

    private val inflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = inflater.inflate(viewType,parent,false)
        return MyViewHolder(view,onNoteListener)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bindData(dataSource.elementAt(position))
    }

    override fun getItemCount(): Int {
        return dataSource.size
    }

    override fun getItemViewType(position:Int):Int{
        return R.layout.beer_view
    }

    interface OnNoteListener{
        fun onNoteClick(position: Int){
        }
    }
}