package com.subscribe.snpa.SNPA.models

import com.example.mp.recyclerviews.BaseSelectModel

data class NewsPaperDTO(
    var newsPaperId: Int,
    var newsPaperName: String,
    var newsPaperPrice: Double
) : BaseSelectModel()