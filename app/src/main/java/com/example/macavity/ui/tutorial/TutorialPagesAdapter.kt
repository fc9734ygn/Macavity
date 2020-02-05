package com.example.macavity.ui.tutorial

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.macavity.R
import com.example.macavity.data.models.TutorialPage
import kotlinx.android.synthetic.main.view_tutorial_page.view.*


class TutorialPagesAdapter : RecyclerView.Adapter<TutorialPagesAdapter.TutorialPageViewHolder>() {
    private var list: List<TutorialPage> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TutorialPageViewHolder {
        return TutorialPageViewHolder(parent)
    }

    override fun onBindViewHolder(holder: TutorialPageViewHolder, position: Int) {
        holder.bind(list[position])
    }

    fun setItem(list: List<TutorialPage>) {
        this.list = list
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = list.size

    inner class TutorialPageViewHolder constructor(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        constructor(parent: ViewGroup) :
                this(LayoutInflater.from(parent.context).inflate(R.layout.view_tutorial_page, parent, false))
        fun bind(model: TutorialPage) {
            itemView.text.text = itemView.context.getString(model.textResource)
            itemView.image.setImageResource(model.imageResource)
        }
    }
}
