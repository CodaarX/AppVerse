package com.decagon.n26_p3_usecase.commons.utils

//class Event<T>() {
//    private val listeners = mutableListOf<(T) -> Unit>()
//
//    fun subscribe(listener: (T) -> Unit) {
//        listeners.add(listener)
//    }
//
//    fun unsubscribe(listener: (T) -> Unit) {
//        listeners.remove(listener)
//    }
//
//    fun publish(value: T) {
//        listeners.forEach { it(value) }
//    }


open class Event<out T>(private val content: T) {

    var hasBeenHandled = false
        private set // Allow external read but not write

    /**
     * Returns the content and prevents its use again.
     */
    fun getContentIfNotHandled(): T? {
        return if (hasBeenHandled) {
            null
        } else {
            hasBeenHandled = true
            content
        }
    }

    /**
     * Returns the content, even if it's already been handled.
     */
    fun peekContent(): T = content
}
//}