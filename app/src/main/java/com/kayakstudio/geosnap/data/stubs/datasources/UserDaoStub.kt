package com.kayakstudio.geosnap.data.stubs.datasources

import com.kayakstudio.geosnap.data.stubs.databases.StubLocalDatabase.doOnUserTable
import com.kayakstudio.geosnap.data.stubs.databases.StubLocalDatabase.usersObs
import com.kayakstudio.geosnap.data.user.UserDao
import com.kayakstudio.geosnap.data.user.UserEntity
import com.kayakstudio.geosnap.tools.extensions.upsert
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserDaoStub : UserDao {

    override suspend fun upsert(newObject: UserEntity) {
        doOnUserTable {
            it.upsert(listOf(newObject)) { old, new -> old.id == new.id }
        }
    }

    override fun observeUserOrThrow(): Flow<UserEntity> {
        return usersObs.map { it.firstOrNull() ?: throw IllegalArgumentException("No user found") }
    }

    override fun observeUser(): Flow<UserEntity?> {
        return usersObs.map { it.firstOrNull() }
    }

    override suspend fun getUserOrThrow(): UserEntity {
        return usersObs.value.firstOrNull()
            ?: throw IllegalArgumentException("No user found") // Return user or throw an error if not set
    }

    override suspend fun deleteAll() {
        usersObs.value.clear()
    }

}