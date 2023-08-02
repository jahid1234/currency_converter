package com.example.assaignment_currency_converter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.assaignment_currency_converter.database.CurrencyRate
import com.example.assaignment_currency_converter.databinding.GridViewItemBinding

class CurrencyListAdapter(private val listener : OnItemClickListenerCustom) :
    ListAdapter<CurrencyRate, CurrencyListAdapter.CurrencyViewHolder>(DiffCallback) {

    class CurrencyViewHolder(val binding: GridViewItemBinding) : RecyclerView.ViewHolder(binding.root) {

        val textItem: TextView = binding.convertToCurrencyTxt

        fun bind(currencyProperty: CurrencyRate,listener: OnItemClickListenerCustom) {

            textItem.text = "Convert to " + currencyProperty.currencyName

            binding.root.setOnClickListener{
                listener.onItemClickCustom(currencyProperty)
            }
        }

    }

    interface OnItemClickListenerCustom {
        fun onItemClickCustom(currencyDetailsProperty: CurrencyRate)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencyViewHolder {
          return CurrencyViewHolder(GridViewItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: CurrencyViewHolder, position: Int) {
        val currProperty = getItem(position)
        holder.bind(currProperty,listener)
    }

    companion object DiffCallback : DiffUtil.ItemCallback<CurrencyRate>() {
        override fun areItemsTheSame(oldItem: CurrencyRate, newItem: CurrencyRate): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: CurrencyRate, newItem: CurrencyRate): Boolean {
            return oldItem.id == newItem.id
        }

    }
}