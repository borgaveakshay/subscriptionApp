package com.example.mp.recyclerviews

import com.example.mp.recyclerviews.databinding.AreaListItemBinding
import com.subscribe.snpa.SNPA.models.AreaDTO

class AreaAdapter : BaseRecyclerAdapter<AreaDTO, AreaListItemBinding>(R.layout.area_list_item) {

    override fun onBindData(binding: AreaListItemBinding, position: Int) {
        binding.area = mDataList[position]
    }

}