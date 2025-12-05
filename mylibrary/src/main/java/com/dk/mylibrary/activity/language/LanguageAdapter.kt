package com.dk.mylibrary.activity.language

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dk.mylibrary.R
import com.dk.mylibrary.databinding.RcvLanguageBinding

class LanguageAdapter(
    val onClickListener: OnClickListener,
    val context: Context,
    private val selectedLanguageDrawable: Int? = null,
    private val unselectedLanguageDrawable: Int? = null,
    private val languageNameFontFamily: Int? = null
) : RecyclerView.Adapter<LanguageAdapter.ViewHolder>() {

    companion object {
        var selected = -1
    }

    var languageList: ArrayList<Language> = ArrayList()

    inner class ViewHolder(val binding: RcvLanguageBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            RcvLanguageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    interface OnClickListener {
        fun onClickListener(position: Int, name: String, img: Int)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(data: ArrayList<Language>) {
        languageList.clear()
        languageList.addAll(data)
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updatePosition(position: Int) {
        selected = position
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = languageList[position]
        val binding = holder.binding

        if (selected == position) {
            if (selectedLanguageDrawable != null) {
                binding.language.setBackgroundResource(selectedLanguageDrawable)
            } else {
                binding.language.setBackgroundResource(R.drawable.btn_select_language)
            }
            binding.checkbox.isChecked = true
            binding.languageName.setTextColor(context.resources.getColor(R.color.language_text_item_color_selected))
        } else {
            if (unselectedLanguageDrawable != null) {
                binding.language.setBackgroundResource(unselectedLanguageDrawable)
            } else {
                binding.language.setBackgroundResource(R.drawable.btn_unselect_language)
            }
            binding.checkbox.isChecked = false
            binding.languageName.setTextColor(context.resources.getColor(R.color.language_text_item_color))
        }

        binding.languageIcon.setImageResource(item.img)
        binding.languageName.text = item.name
        binding.languageName.setHorizontallyScrolling(true)
        binding.languageName.isSelected = true
        
        if (languageNameFontFamily != null) {
            binding.languageName.typeface = context.resources.getFont(languageNameFontFamily)
        }

        holder.itemView.setOnClickListener {
            onClickListener.onClickListener(position, item.key, item.img)
        }
    }

    override fun getItemCount(): Int = languageList.size
}
