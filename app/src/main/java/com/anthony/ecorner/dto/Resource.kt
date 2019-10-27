package com.anthony.ecorner.dto

enum class Status(val value : Boolean) {
    SUCCESS(true),
    Failed(false)
}


data class Resource<out T>(val status: Status, val data: T?, val message: String?) {
    companion object {
        fun <T> success(data: T): Resource<T> {
            return Resource(Status.SUCCESS, data, null)
        }

        fun <T> error(msg: String?, data: T?): Resource<T> {
            return Resource(Status.Failed, data, msg)
        }
    }
}