package com.ifs21023.lostandfound.adapter

import android.graphics.Color
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

import com.ifs21023.lostandfound.data.remote.response.LostFoundsItemResponse
import com.ifs21023.lostandfound.databinding.ItemRowLostfoundBinding


class LostfoundAdapter :
    ListAdapter<LostFoundsItemResponse,
            LostfoundAdapter.MyViewHolder>(DIFF_CALLBACK) { // untuk efisiensi pembaruan daftar.

    private lateinit var onItemClickCallback: OnItemClickCallback
    private var originalData = mutableListOf<LostFoundsItemResponse>() //menyimpan semua data.
    private var filteredData = mutableListOf<LostFoundsItemResponse>() //menyimpan data yang difilter berdasarkan query.

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback //untuk menangani klik pada item.
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder { // untuk menampilkan item.
        val binding = ItemRowLostfoundBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) { // Mengikat data ke ViewHolder dan menangani logika klik dan perubahan status.
        val data = originalData[originalData.indexOf(getItem(position))]

        holder.binding.cbItemLostFoundIsFinished.setOnCheckedChangeListener(null)
        holder.binding.cbItemLostFoundIsFinished.setOnLongClickListener(null)

        holder.bind(data)

        holder.binding.cbItemLostFoundIsFinished.setOnCheckedChangeListener { _, isChecked ->
            data.isCompleted = if (isChecked) 1 else 0
            holder.bind(data)
            onItemClickCallback.onCheckedChangeListener(data, isChecked)
        }

        holder.binding.ivItemLostFoundDetail.setOnClickListener {
            onItemClickCallback.onClickDetailListener(data.id)
        }
    }

    class MyViewHolder(val binding: ItemRowLostfoundBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(data: LostFoundsItemResponse) {
            binding.apply {
                tvItemLostFoundTitle.text = data.title
                cbItemLostFoundIsFinished.isChecked = data.isCompleted == 1
                val statusText = if (data.status.equals("found", ignoreCase = true)) {
                    highlightText("Found", Color.BLUE)
                } else {
                    highlightText("Lost", Color.RED)
                }
                // Menetapkan teks status yang sudah disorot ke TextView
                tvStatus.text = statusText
                data.cover?.let { coverUrl ->
                    Glide.with(itemView)
                        .load(coverUrl)
                        .into(ivLostFoundItem)
                }
            }
        }

        private fun highlightText(text: String, color: Int): SpannableString { //Menyoroti teks dengan warna tertentu.
            val spannableString = SpannableString(text)
            val foregroundColorSpan = ForegroundColorSpan(color)
            spannableString.setSpan(foregroundColorSpan, 0, text.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
            return spannableString
        }
    }

    fun submitOriginalList(list: List<LostFoundsItemResponse>) { // Mengatur dan menampilkan daftar asli.
        originalData = list.toMutableList()
        filteredData = list.toMutableList()

        submitList(originalData)
    }

    fun filter(query: String) {
        filteredData = if (query.isEmpty()) {
            originalData
        } else {
            originalData.filter {
                (it.title.contains(query, ignoreCase = true))
            }.toMutableList()
        }

        submitList(filteredData)
    }

    interface OnItemClickCallback {
        fun onCheckedChangeListener(todo: LostFoundsItemResponse, isChecked: Boolean)
        fun onClickDetailListener(todoId: Int)
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<LostFoundsItemResponse>() {
            override fun areItemsTheSame(
                oldItem: LostFoundsItemResponse,
                newItem: LostFoundsItemResponse
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: LostFoundsItemResponse,
                newItem: LostFoundsItemResponse
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}
