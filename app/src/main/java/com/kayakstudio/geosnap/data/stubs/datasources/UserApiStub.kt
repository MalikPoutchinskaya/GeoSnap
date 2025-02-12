package com.kayakstudio.geosnap.data.stubs.datasources

import com.kayakstudio.geosnap.data.apiutils.ApiResponse
import com.kayakstudio.geosnap.data.stubs.databases.StubRemoteDatabase.users
import com.kayakstudio.geosnap.data.user.UserApi
import com.kayakstudio.geosnap.data.user.UserDto

class UserApiStub : UserApi {

    override suspend fun getProfile(): ApiResponse<UserDto> {
        return ApiResponse.Success(users.first())
    }
}
