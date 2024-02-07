package com.example.communify.data.converters

import androidx.core.net.toUri
import com.example.communify.data.db.dto.AuthResultDto
import com.example.communify.data.db.dto.CredentialsEntity
import com.example.communify.data.db.dto.UserEntity
import com.example.communify.data.network.dto.ContactDto
import com.example.communify.data.network.dto.LocationDto
import com.example.communify.data.network.dto.NameDto
import com.example.communify.data.network.dto.PictureDto
import com.example.communify.domain.models.AuthResult
import com.example.communify.domain.models.Contact
import com.example.communify.domain.models.Credentials
import com.example.communify.domain.models.Location
import com.example.communify.domain.models.Name
import com.example.communify.domain.models.Picture
import com.example.communify.domain.models.User
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import javax.inject.Inject

class DBConverter @Inject constructor() {

    fun convert(userEntity: UserEntity): Contact {
        with(userEntity) {
            return Contact(
                user = User(
                    userId = userId,
                    email = email,
                    gender = gender,
                    location = convertLocationFromJson(location),
                    name = convertNameFromJson(name),
                    phone = phone,
                    picture = convertPictureFromJson(picture),
                    username = username,
                    uniqueKey = uniqueKey
                )

            )
        }
    }

    fun convert(contact: Contact): UserEntity {
        with(contact.user) {
            return UserEntity(
                userId = userId,
                email = email,
                gender = gender,
                location = convertLocationToJson(location),
                name = convertNameToJson(name),
                phone = phone,
                picture = convertPictureToJson(picture),
                username = username,
                uniqueKey = uniqueKey
            )
        }
    }

    fun convert(contactDto: ContactDto): UserEntity {
        with(contactDto.user) {
            return UserEntity(
                userId = userId,
                email = email,
                gender = gender,
                location = convertLocationToJson(location),
                name = convertNameToJson(name),
                phone = phone,
                picture = convertPictureToJson(picture),
                username = username,
                uniqueKey = uniqueKey
            )
        }
    }




    fun convert(credentialsEntity: CredentialsEntity): Credentials {
        with(credentialsEntity) {
            return Credentials(
                uniqueKey = uniqueKey,
                name = name,
                login = login,
                password = password,
                userImage = userImage?.toUri()
            )
        }
    }

    fun convert(credentials: Credentials): CredentialsEntity {
        with(credentials) {
            return CredentialsEntity(
                uniqueKey = uniqueKey,
                name = name,
                login = login,
                password = password,
                userImage = userImage?.toString()
            )
        }
    }

    fun convert(result: AuthResultDto): AuthResult {

        return when (result) {
            is AuthResultDto.Failure -> {
                AuthResult.Failure(message = result.message)
            }

            AuthResultDto.Success -> {
                AuthResult.Success
            }
        }
    }


    private fun convertLocationFromJson(json: String): Location {
        val typeToken = object : TypeToken<Location>() {}.type
        return Gson().fromJson(json, typeToken)
    }

    private fun convertNameFromJson(json: String): Name {
        val typeToken = object : TypeToken<Name>() {}.type
        return Gson().fromJson(json, typeToken)
    }

    private fun convertPictureFromJson(json: String): Picture {
        val typeToken = object : TypeToken<Picture>() {}.type
        return Gson().fromJson(json, typeToken)
    }

    private fun convertLocationToJson(location: Location): String {
        return Gson().toJson(location)
    }
    private fun convertLocationToJson(locationDtp: LocationDto): String {
        return Gson().toJson(locationDtp)
    }

    private fun convertNameToJson(name: Name): String {
        return Gson().toJson(name)
    }

    private fun convertNameToJson(nameDto: NameDto): String {
        return Gson().toJson(nameDto)
    }

    private fun convertPictureToJson(picture: Picture): String {
        return Gson().toJson(picture)
    }

    private fun convertPictureToJson(pictureDto: PictureDto): String {
        return Gson().toJson(pictureDto)
    }

}