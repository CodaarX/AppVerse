package com.michael.appverse.features.qrCode.utils

object GsonCleaner {
    fun removeQuotesAndUnescape(uncleanJson: String): String {
        return uncleanJson.replace("^\"|\"$".toRegex(), "")
    }

}