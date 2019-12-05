package com.subscribe.snpa.SNPA.models

data class ResponseListDTO<T>(var statusCode: Int = 200,
                              var statusMessage: String = "Success",
                              var result: List<T>? = null)