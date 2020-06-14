package com.em_projects.grocery.view

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.em_projects.grocery.R
import kotlinx.android.synthetic.main.activity_item.*

class ItemActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item)

        val color = Color.parseColor(intent.extras?.getString("bagColor")) ?: Color.BLUE
        mainLayout.setBackgroundColor(color)
        product.text = intent.extras?.getString("name")
        weight.text = intent.extras?.getString("weight")
    }
}