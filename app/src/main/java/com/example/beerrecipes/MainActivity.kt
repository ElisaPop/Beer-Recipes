package com.example.beerrecipes

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.AdapterView.OnItemClickListener
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.beerrecipes.entity.*
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import kotlin.collections.ArrayList


class MainActivity : AppCompatActivity(), BeerAdapter.OnNoteListener {
    private lateinit var recyclerView: RecyclerView

    private val beerList = LinkedList<RecipeData>()

    private lateinit var beerAdapter: BeerAdapter
    private lateinit var linearLayoutManager: LinearLayoutManager

    var clicked = 0
    var currentPosition = -1

    private val listener: OnItemClickListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        linearLayoutManager = LinearLayoutManager(this)

        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://api.punkapi.com/v2/")
            .build()

        val beerApi = retrofit.create(BeerApi::class.java)

        beerApi.getBeerRecipes().enqueue(
                object: Callback<ArrayList<RecipeData>> {
                    override fun onResponse(call: Call<ArrayList<RecipeData>>, response: Response<ArrayList<RecipeData>>) {
                        if (!response.isSuccessful) {
                            Log.e("xyz", "fail1 ")
                        } else {
                            val beerRecipeResponse = response.body() as ArrayList<RecipeData>
                            for (recipe in beerRecipeResponse) {

                                beerList.push(
                                    RecipeData(
                                        recipe.name, recipe.volume,
                                        recipe.food_pairing, recipe.ingredients
                                    )
                                )
                            }
                            beerAdapter.notifyDataSetChanged()
                        }
                    }

                    override fun onFailure(call: Call<ArrayList<RecipeData>>, t: Throwable) {
                        Log.e("xyz", "fail2")
                    }
                }
        )

        beerAdapter = BeerAdapter(this, beerList, this)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recycler_view)
        recyclerView.layoutManager = linearLayoutManager
        recyclerView.adapter = beerAdapter
    }

    override fun onNoteClick(position: Int) {
        val recipe = beerList[position]
        this.clicked = 1
        this.currentPosition = position
        val intent = Intent(this, BeerActivity::class.java)

        val offer: String = Gson().toJson(recipe)
        intent.putExtra("recipe", offer)

        //intent.putExtra("name", "" + recipe.name)
        this.startActivityForResult(intent, 0);
    }

//    override fun onResume() {
//        super.onResume()
//        if(clicked == 1 && currentPosition >= 0){
//            beerList[currentPosition].name = intent.getStringExtra("nameReturn").toString()
//            beerAdapter.notifyDataSetChanged()
//            clicked = 0
//            currentPosition = -1
//        }
//    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, intent: Intent?) {
        if (requestCode == 0) {
            if (resultCode == Activity.RESULT_OK) {
                beerList[currentPosition].name = intent?.getStringExtra("nameReturn").toString()
                beerList[currentPosition].volume = Gson().fromJson(intent?.getStringExtra("volumeReturn"), Volume::class.java)

                beerList[currentPosition].ingredients = Gson().fromJson(intent?.getStringExtra("ingredientsReturn"), Ingredients::class.java)

                var foodRefString = intent?.getStringExtra("foodReturn").toString().trim()
                beerList[currentPosition].food_pairing = ArrayList()
                for(foodElement in foodRefString.split(","))
                    beerList[currentPosition].food_pairing.add(foodElement)

                beerAdapter.notifyDataSetChanged()
            }
        }
        super.onActivityResult(requestCode, resultCode, intent)
    }
}