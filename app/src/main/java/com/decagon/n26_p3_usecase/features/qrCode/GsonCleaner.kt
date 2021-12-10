package com.decagon.n26_p3_usecase.features.qrCode

object GsonCleaner {
    fun removeQuotesAndUnescape(uncleanJson: String): String {
        return uncleanJson.replace("^\"|\"$".toRegex(), "")
    }

}