package com.subscribe.snpa.SNPA.models

import java.sql.Date


data class SubscribeDTO(var subscriptionId: Int = 0,
                        var SubscriptionStartDate: Date,
                        var SubscriptionEndDate: Date,
                        var vendorDTO: VendorDTO,
                        var newsPaperDTO: NewsPaperDTO,
                        var customerDTO: CustomerDTO)