
package com.example.lemonade

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    private val LEMONADE_STATE = "LEMONADE_STATE"
    private val LEMON_SIZE = "LEMON_SIZE"
    private val SQUEEZE_COUNT = "SQUEEZE_COUNT"
    private val SELECT = "select"
    private val SQUEEZE = "squeeze"
    private val DRINK = "drink"
    private val RESTART = "restart"
    private var lemonadeState = "select"
    private var lemonSize = -1
    private var squeezeCount = -1

    private var lemonTree = LemonTree()
    private var lemonImage: ImageView? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // === DO NOT ALTER THE CODE IN THE FOLLOWING IF STATEMENT ===
        if (savedInstanceState != null) {
            lemonadeState = savedInstanceState.getString(LEMONADE_STATE, "select")
            lemonSize = savedInstanceState.getInt(LEMON_SIZE, -1)
            squeezeCount = savedInstanceState.getInt(SQUEEZE_COUNT, -1)
        }
        // === END IF STATEMENT ===

        lemonImage = findViewById(R.id.image_lemon_state)
        setViewElements()
        lemonImage!!.setOnClickListener {
            clickLemonImage()
        }
        lemonImage!!.setOnLongClickListener {
            showSnackbar()
        }
    }


    override fun onSaveInstanceState(outState: Bundle) {
        outState.putString(LEMONADE_STATE, lemonadeState)
        outState.putInt(LEMON_SIZE, lemonSize)
        outState.putInt(SQUEEZE_COUNT, squeezeCount)
        super.onSaveInstanceState(outState)
    }


    private fun clickLemonImage() {

        if(lemonadeState == SELECT){
            lemonadeState = SQUEEZE
            lemonSize = lemonTree.pick()
            squeezeCount = 0
        }else if (lemonadeState == SQUEEZE){
            squeezeCount+=1
            lemonSize-=1
            if(lemonSize == 0){
                lemonadeState = DRINK
                lemonSize = -1
            }
            showSnackbar()
        }else if (lemonadeState == DRINK){
            lemonadeState = RESTART
        }else if (lemonadeState == RESTART){
            lemonadeState = SELECT
        }
        setViewElements()
    }


    private fun setViewElements() {
        val textAction: TextView = findViewById(R.id.text_action)

        if (lemonadeState == SELECT){
            textAction.text = resources.getString(R.string.lemon_select)
            lemonImage?.setImageResource(R.drawable.lemon_tree)
        }else if (lemonadeState == SQUEEZE){
            textAction.text = resources.getString(R.string.lemon_squeeze)
            lemonImage?.setImageResource(R.drawable.lemon_squeeze)
        }else if (lemonadeState == DRINK){
            textAction.text = resources.getString(R.string.lemon_drink)
            lemonImage?.setImageResource(R.drawable.lemon_drink)
        }else if (lemonadeState == RESTART){
            textAction.text = resources.getString(R.string.lemon_empty_glass)
            lemonImage?.setImageResource(R.drawable.lemon_restart)
        }
    }

    private fun showSnackbar(): Boolean {
        if (lemonadeState != SQUEEZE) {
            return false
        }
        val squeezeText = getString(R.string.squeeze_count, squeezeCount)
        Snackbar.make(
            findViewById(R.id.constraint_Layout),
            squeezeText,
            Snackbar.LENGTH_SHORT
        ).show()
        return true
    }
}


class LemonTree {
    fun pick(): Int {
        return (2..4).random()
    }
}
