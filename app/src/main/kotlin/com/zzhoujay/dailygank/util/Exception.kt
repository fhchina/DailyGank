package com.zzhoujay.dailygank.util

/**
 * Created by zhou on 16-3-9.
 */

class NetworkRequestFailureException : Exception {

    constructor() : super()

    constructor(detailMessage: String?) : super(detailMessage)

    constructor(detailMessage: String?, throwable: Throwable?) : super(detailMessage, throwable)

    constructor(throwable: Throwable?) : super(throwable)
}