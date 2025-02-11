package com.kayakstudio.geosnap.data.stubs.databases

import com.kayakstudio.geosnap.data.user.UserEntity
import kotlinx.coroutines.flow.MutableStateFlow

object StubLocalDatabase {
    val settingsData = mutableMapOf<String, Any?>()

    val usersObs = MutableStateFlow(arrayListOf<UserEntity>())


    fun clear() {
        usersObs.value.clear()
    }

    fun doOnUserTable(action: (newList: ArrayList<UserEntity>) -> Unit) {
        val newList = ArrayList(usersObs.value)
        action(newList)
        usersObs.value = newList
    }

}
