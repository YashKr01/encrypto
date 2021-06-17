package com.example.encrypto.listeners

import com.example.encrypto.models.User

interface UserClickListener {
    fun onUserItemClicked(user: User)
}