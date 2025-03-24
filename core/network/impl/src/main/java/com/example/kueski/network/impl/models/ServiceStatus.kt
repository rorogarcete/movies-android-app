package com.example.kueski.network.impl.models

enum class ServiceStatus(val statusName: String) {
    HTTP_ERROR("httpError"),
    HOST_UNRESOLVED("unknownHost"),
    NOT_INTERNET_CONNECTION("notConnectedToInternet"),
    TIMEOUT("timedOut"),
    UNDEFINED("undefined")
}