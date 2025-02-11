package com.kayakstudio.geosnap.ui.design.components.ccp

import co.touchlab.kermit.Logger
import io.michaelrocks.libphonenumber.kotlin.NumberParseException
import io.michaelrocks.libphonenumber.kotlin.PhoneNumberUtil
import io.michaelrocks.libphonenumber.kotlin.Phonenumber
import io.michaelrocks.libphonenumber.kotlin.metadata.defaultMetadataLoader

data class AccountPhoneNumber(
    val countryCode: Country,
    val phoneNumber: String,
) {

    fun formatNationalPhoneNumber(defaultRegion: String = "US"): String {
        val phoneUtil = PhoneNumberUtil.createInstance(defaultMetadataLoader())
        val fullPhoneNumber = assembleFullPhoneNumber()

        return try {
            val number = phoneUtil.parse(fullPhoneNumber, defaultRegion)
            phoneUtil.format(
                number,
                PhoneNumberUtil.PhoneNumberFormat.NATIONAL
            )  // Format national sans le code pays
        } catch (e: NumberParseException) {
            Logger.d("An error occurred when analyse the phone number: ${e.message}")
            phoneNumber
        }
    }

    fun formatPhoneNumberForDisplay(
        defaultRegion: String = "US",
    ): String {
        val phoneUtil = PhoneNumberUtil.createInstance(defaultMetadataLoader())
        val fullPhoneNumber = assembleFullPhoneNumber()

        return try {
            val number = phoneUtil.parse(fullPhoneNumber, defaultRegion)
            phoneUtil.format(
                number,
                PhoneNumberUtil.PhoneNumberFormat.INTERNATIONAL
            )  // Format international pour affichage
        } catch (e: NumberParseException) {
            Logger.d("An error occurred when analyse the phone number: ${e.message}")
            fullPhoneNumber
        }
    }

    fun assembleFullPhoneNumber(): String {
        val phoneUtil = PhoneNumberUtil.createInstance(defaultMetadataLoader())
        return try {
            val number = Phonenumber.PhoneNumber()
            number.setCountryCode(countryCode.code.toInt())
            number.setNationalNumber(phoneNumber.toLong())

            // Format du numéro en E.164 (format international standard)
            phoneUtil.format(number, PhoneNumberUtil.PhoneNumberFormat.E164)
        } catch (e: Exception) {
            Logger.d("An error occurred when assembly the phone number: ${e.message}")
            "+" + countryCode.code + phoneNumber
        }
    }

    companion object {
        fun init() = AccountPhoneNumber(
            countryCode = Country("fr", "33", "France"),
            phoneNumber = ""
        )

        fun init(
            fullPhoneNumber: String,
            defaultRegion: String = "US",
        ): AccountPhoneNumber {
            val phoneUtil = PhoneNumberUtil.createInstance(defaultMetadataLoader())
            return try {
                val number = phoneUtil.parse(fullPhoneNumber, defaultRegion)
                val countryCode = number.countryCode.toString()
                val nationalNumber = number.nationalNumber.toString()
                countryCode to nationalNumber
                AccountPhoneNumber(
                    countryCode = getCountriesList().first { it.code == countryCode },
                    phoneNumber = nationalNumber
                )
            } catch (e: NumberParseException) {
                Logger.d("An error occurred when analyse the phone number: ${e.message}")
                init()
            }
        }
    }

}