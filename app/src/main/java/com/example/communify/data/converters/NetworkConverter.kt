package com.example.communify.data.converters

import com.example.communify.data.network.dto.ContactDto
import com.example.communify.domain.models.Contact
import com.example.communify.domain.models.Location
import com.example.communify.domain.models.Name
import com.example.communify.domain.models.Picture
import com.example.communify.domain.models.User
import javax.inject.Inject

class NetworkConverter @Inject constructor() {

    fun convert(contactDto: ContactDto, uniqueKey: String): Contact {
        with(contactDto.user) {
            return Contact(
                user = User(
                    userId = userId,
                    email = email,
                    gender = gender,
                    location = Location(
                        city = location.city,
                        state = location.state,
                        street = location.street,
                        zip = location.zip
                    ),
                    name = Name(
                        first = name.first,
                        last = name.last,
                        title = name.title
                    ),
                    phone = phone,
                    picture = Picture(large = picture.large),
                    username = username,
                    uniqueKey = uniqueKey
                )
            )
        }
    }
}