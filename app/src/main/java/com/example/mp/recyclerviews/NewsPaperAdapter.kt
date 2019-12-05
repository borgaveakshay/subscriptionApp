package com.example.mp.recyclerviews

import com.example.mp.recyclerviews.databinding.NewsPaperListItemBinding
import com.subscribe.snpa.SNPA.models.NewsPaperDTO

class NewsPaperAdapter : BaseRecyclerAdapter<NewsPaperDTO, NewsPaperListItemBinding>(R.layout.news_paper_list_item) {

    override fun onBindData(binding: NewsPaperListItemBinding, position: Int) {
        binding.newsPaper = mDataList[position]
    }
}