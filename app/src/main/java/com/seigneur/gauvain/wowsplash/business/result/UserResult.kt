package com.seigneur.gauvain.wowsplash.business.result

import com.seigneur.gauvain.wowsplash.data.model.user.User

sealed class UserResult {
    data class UserFetched(val user: User?) : UserResult()
    data class UserError(val inError: Throwable? = null) : UserResult()
}