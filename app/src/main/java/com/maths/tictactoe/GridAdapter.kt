package com.maths.tictactoe

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button

class GridAdapter(private val context: Context , private val onItemClickListener: (position :Int)->Unit) : BaseAdapter() {

    val buttonStates = Array(9) { "" } // Track button states (empty, "X", or "O")

    override fun getCount(): Int = 9

    override fun getItem(position: Int): Any = position

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        // Inflate the LinearLayout containing the Button
        val view = convertView ?: LayoutInflater.from(context)
            .inflate(R.layout.grid_items, parent, false)

        // Extract the Button from the LinearLayout
        val imageview = view.findViewById<Button>(R.id.imageView)

        val layoutParams = imageview.layoutParams
        layoutParams.width = 220
        layoutParams.height = 220
        imageview.layoutParams = layoutParams

        when(buttonStates[position]){
            "X" -> imageview.setBackgroundResource(R.drawable.letter_x)
            "O" -> imageview.setBackgroundResource(R.drawable.letter_o)
            else -> imageview.setBackgroundResource(R.color.blunishPurple)
        }

        imageview.setOnClickListener {
            onItemClickListener(position)
        }

        return view
    }

}
