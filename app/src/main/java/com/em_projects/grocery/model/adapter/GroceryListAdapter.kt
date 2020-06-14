package com.em_projects.grocery.model.adapter

import android.content.Context
import android.graphics.Color
import android.graphics.PorterDuff
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.em_projects.grocery.R
import com.em_projects.grocery.model.GroceryItemModel
import kotlinx.android.synthetic.main.grocery_list_item.view.*


class GroceryListAdapter(val context: Context, var arrayList: MutableList<GroceryItemModel>): BaseAdapter() {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = convertView ?: View.inflate(context, R.layout.grocery_list_item, null)
        val color = arrayList[position].bagColor

        view.point.background.setColorFilter(Color.parseColor(color), PorterDuff.Mode.SRC_ATOP)
        view.product.text = arrayList[position].name
        view.weight.text = arrayList[position].weight
        return view;
    }

    override fun getItem(position: Int): Any {
        return arrayList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return arrayList.size
    }
}