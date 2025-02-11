package com.kayakstudio.geosnap.data.apiutils

import co.touchlab.kermit.Logger
import com.kayakstudio.geosnap.data.app.AppRepository
import com.kayakstudio.geosnap.data.auth.FirebaseAuthHelper
import com.kayakstudio.geosnap.indus.EnvHelper.isMockWithServer
import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.HttpResponseValidator
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.EMPTY
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.headers
import io.ktor.client.statement.bodyAsText
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.URLProtocol
import io.ktor.http.isSuccess
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json


fun httpClient(
    isDebug: Boolean = false,
    json: Json,
    engine: HttpClientEngine,
    appRepository: AppRepository,
    authHelper: FirebaseAuthHelper
) = HttpClient(engine) {
    expectSuccess = true

    install(ContentNegotiation) {
        json(json = json)
    }

    install(DefaultRequest) {
        apply {
            url {
                protocol = if (isMockWithServer) URLProtocol.HTTP else URLProtocol.HTTPS
                host = "" // TODO: add real backend enpoint
            }
            headers {
                append(HttpHeaders.ContentType, "application/json")
            }
        }
    }

    install(Auth) {
        bearer {
            loadTokens {
                // Load tokens from a local storage and return them as the 'BearerTokens' instance
                val token = appRepository.getToken()
                token?.let { BearerTokens(it, "") }
            }
            refreshTokens {
                val accessToken = appRepository.getToken()
                val token = authHelper.refreshToken()
                if (token != null) {
                    appRepository.setToken(token)
                }
                BearerTokens(accessToken!!, token)
            }
        }
    }

    HttpResponseValidator {
        validateResponse { response ->

            if (!response.status.isSuccess()) {
                val failureReason = when (response.status) {
                    HttpStatusCode.Unauthorized -> "Unauthorized request"
                    HttpStatusCode.Forbidden -> "${response.status.value} Missing API key."
                    HttpStatusCode.NotFound -> "Invalid Request"
                    HttpStatusCode.UpgradeRequired -> "Upgrade to VIP"
                    HttpStatusCode.RequestTimeout -> "Network Timeout"
                    in HttpStatusCode.InternalServerError..HttpStatusCode.GatewayTimeout ->
                        "${response.status.value} Server Error"

                    else -> "Network error!"
                }

                throw HttpExceptions(
                    response = response,
                    failureReason = failureReason,
                    cachedResponseText = response.bodyAsText(),
                )
            }
        }
    }

    install(HttpTimeout) {
        requestTimeoutMillis = TIMEOUT_DURATION
        connectTimeoutMillis = TIMEOUT_DURATION
        socketTimeoutMillis = TIMEOUT_DURATION
    }

    install(Logging) {
        level = LogLevel.ALL
        logger = if (isDebug) {
            object : io.ktor.client.plugins.logging.Logger {
                override fun log(message: String) {
                    Logger.d(message)
                }
            }
        } else {
            io.ktor.client.plugins.logging.Logger.EMPTY
        }
    }

}

const val TIMEOUT_DURATION: Long = 15_000
