package com.example.beerrecipes

import android.view.ContextMenu
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.beerrecipes.entity.RecipeData


class MyViewHolder(itemView: View, onNoteListener: BeerAdapter.OnNoteListener): RecyclerView.ViewHolder(itemView), View.OnClickListener, View.OnCreateContextMenuListener{

    private var nameRef: TextView =itemView.findViewById(R.id.recipe_name)
    private var volumeRef: TextView =itemView.findViewById(R.id.recipe_volume)
    private var foodRef: TextView =itemView.findViewById(R.id.recipe_food)
    private var ingredientsRef: TextView = itemView.findViewById(R.id.recipe_ingredients)
    var style: Int = 1

    var onNoteListener: BeerAdapter.OnNoteListener

    lateinit var recipe: RecipeData

    fun bindData(data: RecipeData){
        nameRef.text = data.name
        volumeRef.text = "Volume: " + data.volume.value + " " + data.volume.unit
        var foodRefString =  "----------- Food pairing -----------\n"

        for(foodPairing in data.food_pairing)
            foodRefString += "o " + foodPairing + "\n"

        foodRef.text = foodRefString

        var ingredientsString = "----------- Ingredients -----------\n"
        ingredientsString += "MALT \n"
        for(malt in data.ingredients.malt){
            ingredientsString += "o " + malt.name + ", "
            ingredientsString += "" +  malt.amount.value + " " + malt.amount.unit + "\n"
        }
        ingredientsString += "HOPS \n"
        for(hop in data.ingredients.hops){
            ingredientsString += "o " + hop.name + ", "
            ingredientsString += "" + hop.amount.value + " " + hop.amount.unit + "\n"
            ingredientsString += "   - Add: " + hop.add + "\n"
            ingredientsString += "   - Attribute: " + hop.attribute + "\n"
        }

        ingredientsString += "YEAST \n o " + data.ingredients.yeast

        ingredientsRef.text = ingredientsString
        recipe = data
    }

    init {
        this.onNoteListener = onNoteListener
        itemView.setOnClickListener(this)
        itemView.setOnCreateContextMenuListener(this)
    }

    fun showHide(view:View) {
        view.visibility = if (view.visibility == View.VISIBLE){
            View.GONE
        } else{
            View.VISIBLE
        }
    }

    private val onChange =
        MenuItem.OnMenuItemClickListener { item ->
            when (item.itemId) {
                1 -> {
                    showHide(ingredientsRef)
                    return@OnMenuItemClickListener true
                }
            }
            false
        }

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {

        val edit = menu!!.add(Menu.NONE, 1, 1, "Expand/Collapse")
        edit.setOnMenuItemClickListener(onChange)
    }

    override fun onClick(v: View?) {
        onNoteListener.onNoteClick(adapterPosition)
        if (v != null) {
            Toast.makeText(
                v.context,
                position.toString() + "",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

}