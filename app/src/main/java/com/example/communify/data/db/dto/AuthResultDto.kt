package com.example.communify.data.db.dto

sealed interface AuthResultDto{
    data object Success: AuthResultDto
    data class Failure(val message: String): AuthResultDto

}