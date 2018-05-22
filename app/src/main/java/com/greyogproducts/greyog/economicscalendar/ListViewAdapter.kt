package com.greyogproducts.greyog.economicscalendar

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

/**
 * Created by mac on 09/03/2018.
 */
class ListViewAdapter(context: Context?, resource: Int) : ArrayAdapter<ListItem>(context, resource) {
    private var inflater: LayoutInflater? = LayoutInflater.from(getContext())


    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = convertView ?: inflater?.inflate(R.layout.list_item, parent)
        val tvTime = view?.findViewById<TextView>(R.id.tvTime)
        tvTime?.text = getItem(position).eventTime
        val tvCurrency = view?.findViewById<TextView>(R.id.tvCurr)
        tvCurrency?.text = getItem(position).currency
        val tvDescription = view?.findViewById<TextView>(R.id.tvDesc)
        tvDescription?.text = getItem(position).description
        return view!!
    }
}

