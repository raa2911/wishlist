package com.raapp.data.models

data class Account(
    val id: Long = 0,
    val firstName: String,
    val lastName: String?,
    val dateOfBirth: Long?,
    val avatar: String?
)