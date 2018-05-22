package com.greyogproducts.greyog.economicscalendar

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.greyogproducts.greyog.economicscalendar.LoadHelper.ResponseListener
import kotlin.collections.ArrayList


/**
 * A placeholder fragment containing a simple view.
 */
class MainActivityFragment : Fragment(), ResponseListener {
    private lateinit var recyclerView: RecyclerView

    override fun onResponse(list: ArrayList<ListItem>) {
        println("onResponse, count = " + (list.size ))
        val adapter = DataAdapter(list)
        println("adapter count" + adapter.itemCount)
        activity.runOnUiThread {
            recyclerView.adapter = adapter
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_main, container, false)
        LoadHelper.responseListener = this
        initViews(view)
        LoadHelper.makeRequest()
        return view
    }

    private fun initViews(view: View) {
        println("initViews")
        recyclerView = view.findViewById(R.id.card_rec_view) as RecyclerView
        recyclerView.setHasFixedSize(true)
        val dataList = ArrayList<ListItem>()
        val adapter = DataAdapter(dataList)
        recyclerView.adapter = adapter

        val layoutManager = LinearLayoutManager(context)
        recyclerView.layoutManager = layoutManager
    }
}
