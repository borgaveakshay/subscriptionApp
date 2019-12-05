package com.subscribe.snpa.SNPA.models

data class VendorDTO(
    var vendorId: Int = 0,
    var name: String = "",
    var email: String = "",
    var areaDTO: AreaDTO = AreaDTO(1, "Kalyani Nagar"),
    var newsPapers: Set<NewsPaperDTO> = emptySet()
)