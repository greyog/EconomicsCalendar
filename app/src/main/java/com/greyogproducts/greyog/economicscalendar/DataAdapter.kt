package com.greyogproducts.greyog.economicscalendar

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

/**
 * Created by mac on 09/03/2018.
 */
class DataAdapter(private val calen: ArrayList<ListItem>) : RecyclerView.Adapter<DataAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val view =  LayoutInflater.from(parent?.context).inflate(R.layout.card_row, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return calen.size
    }

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        holder?.tvTime?.text = calen[position].eventTime
        holder?.tvCurr?.text = calen[position].currency
        holder?.tvDesc?.text = calen[position].description
        holder?.tvImp?.text = calen[position].importance.toString()
        holder?.tvPrev?.text = calen[position].previous
        holder?.tvFore?.text = calen[position].forecast
        holder?.tvActu?.text = calen[position].actual

    }

    class ViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {
        internal var tvTime : TextView? = null
        internal var tvCurr: TextView? = null
        internal var tvDesc: TextView? = null
        internal var tvPrev: TextView? = null
        internal var tvActu: TextView? = null
        internal var tvFore: TextView? = null
        internal var tvImp: TextView? = null

        init {
            tvTime = itemView?.findViewById(R.id.tv_time)
            tvCurr = itemView?.findViewById(R.id.tv_currency)
            tvDesc = itemView?.findViewById(R.id.tv_description)
            tvImp = itemView?.findViewById(R.id.tv_importance)
            tvPrev= itemView?.findViewById(R.id.tv_previous)
            tvFore= itemView?.findViewById(R.id.tv_forecast)
            tvActu = itemView?.findViewById(R.id.tv_actual)
        }
    }
}