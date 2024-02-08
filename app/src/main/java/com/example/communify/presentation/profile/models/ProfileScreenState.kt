package com.example.communify.presentation.profile.models

import com.example.communify.domain.models.Credentials

sealed interface ProfileScreenState {
    data class Authorized(val credentials: Credentials): ProfileScreenState
    data object Unauthorized: ProfileScreenState
}