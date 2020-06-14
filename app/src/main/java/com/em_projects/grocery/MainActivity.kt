package com.em_projects.grocery

import android.content.ClipData
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.em_projects.grocery.model.GroceryItemModel
import com.em_projects.grocery.model.adapter.GroceryListAdapter
import com.em_projects.grocery.model.remote.DataWrapper
import com.em_projects.grocery.view.ItemActivity
import com.em_projects.grocery.viewmodel.GroceryViewModel
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var groceryViewModel: GroceryViewModel
    private lateinit var groceryListAdapter: GroceryListAdapter
    private var groceryItems: MutableList<GroceryItemModel> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        groceryViewModel = ViewModelProviders.of(this).get(GroceryViewModel::class.java)

        val groceryListAdapter = GroceryListAdapter(this, groceryItems)
        groceryList.adapter = groceryListAdapter
        groceryList.onItemClickListener = object: AdapterView.OnItemClickListener{
            override fun onItemClick(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                openNewScreen(groceryItems[position])
            }
        }

        groceryViewModel.connect().observe(this, object: Observer<DataWrapper<GroceryItemModel>>{
            override fun onChanged(t: DataWrapper<GroceryItemModel>?) {
                if (t?.data != null) {
                    groceryItems.add(t.data)
                    groceryListAdapter.notifyDataSetChanged()
                }
            }
        })
    }

    private fun openNewScreen(groceryItemModel: GroceryItemModel) {
        val intent = Intent(this, ItemActivity::class.java)
        intent.extras?.putString("color", groceryItemModel.bagColor)
        intent.extras?.putString("name", groceryItemModel.name)
        intent.extras?.putString("weight", groceryItemModel.weight)
        startActivity(intent)
    }


}
