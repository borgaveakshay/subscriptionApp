package com.subscribe.snpa.SNPA.models

data class ResponseDTO<T>(var statusCode: Int = 200,
                          var statusMessage: String = "Success",
                          var result: T? = null)