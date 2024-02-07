package com.example.communify.domain.models

sealed interface AuthResult{
    data object Success: AuthResult
    data class Failure(val message: String): AuthResult

}