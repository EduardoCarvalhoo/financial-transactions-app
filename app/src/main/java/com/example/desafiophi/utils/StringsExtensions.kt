package com.example.desafiophi.utils

import java.text.SimpleDateFormat

private const val ORIGINAL_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss'Z'"

fun String.convertDateToFormat(pattern: String): String? {
    val originalFormat = SimpleDateFormat(ORIGINAL_DATE_FORMAT, localePtBr)
    val dateFormat = originalFormat.parse(this) ?: return null
    val convertedFormat = SimpleDateFormat(pattern, localePtBr)
    return convertedFormat.format(dateFormat)
}