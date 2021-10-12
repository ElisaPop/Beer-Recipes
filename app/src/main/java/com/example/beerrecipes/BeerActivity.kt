package com.example.beerrecipes

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.beerrecipes.entity.RecipeData
import com.example.beerrecipes.entity.Volume
import com.google.gson.Gson
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

class BeerActivity: AppCompatActivity() {

    private var position: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.details_edit_view)

        var recipe = Gson().fromJson(intent.getStringExtra("recipe"), RecipeData::class.java)

        findViewById<TextView>(R.id.name_edit).text = recipe.name

        findViewById<TextView>(R.id.volume_value_edit).text = recipe.volume.value.toString()
        findViewById<TextView>(R.id.volume_unit_edit).text = recipe.volume.unit

        findViewById<TextView>(R.id.ingredients_edit).text = Gson().toJson(recipe.ingredients).toString()

        var foodRefString = ""

        for(foodPairing in recipe.food_pairing)
            foodRefString += "" + foodPairing + ", "

        foodRefString = foodRefString.dropLast(2)

        findViewById<TextView>(R.id.food_edit).text = foodRefString

    }

    override fun onBackPressed(){
        var isValid = true

        val builder1 = AlertDialog.Builder(this)
        builder1.setTitle("The new values are valid.")
        builder1.setMessage("Are sure you want to save changes?")

        builder1.setPositiveButton("Yes") { _, _ ->
            sendBackData()
        }

        builder1.setNegativeButton("No") { _, _ ->
        }

        val builder2 = AlertDialog.Builder(this)
        builder2.setTitle("The new values are invalid.")
        builder2.setMessage("Some values are invalid. Do you want to continue to edit the recipe?")

        builder2.setPositiveButton("Yes") { _, _ ->
        }

        builder2.setNegativeButton("No") { _, _ ->
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        var intAmount = "" + findViewById<TextView>(R.id.volume_value_edit).text




        if (findViewById<TextView>(R.id.name_edit).text.isEmpty()) {
            isValid = false
        } else if (intAmount.toInt() < 0 || findViewById<TextView>(R.id.volume_unit_edit).text.isEmpty()){
            isValid = false
        } else if (!isJSONValid("" + findViewById<TextView>(R.id.ingredients_edit).text)){
            isValid = false
        } else if (findViewById<TextView>(R.id.food_edit).text.isEmpty()){
            isValid = false
        }

        if(isValid) builder1.show()
        else builder2.show()
    }

    fun sendBackData(){
        val intent = Intent()
        intent.putExtra("nameReturn", "" + findViewById<TextView>(R.id.name_edit).text)

        var intAmount = "" + findViewById<TextView>(R.id.volume_value_edit).text
        var volume = Volume(intAmount.toInt(), ""+ findViewById<TextView>(R.id.volume_unit_edit).text)
        intent.putExtra("volumeReturn", "" + Gson().toJson(volume).toString())

        intent.putExtra("ingredientsReturn", "" + findViewById<TextView>(R.id.ingredients_edit).text)

        intent.putExtra("foodReturn", "" + findViewById<TextView>(R.id.food_edit).text)

        setResult(Activity.RESULT_OK, intent)
        finish()
    }


    fun isJSONValid(test: String?): Boolean {
        try {
            JSONObject(test)
        } catch (ex: JSONException) {
            try {
                JSONArray(test)
            } catch (ex1: JSONException) {
                return false
            }
        }
        return true
    }

}