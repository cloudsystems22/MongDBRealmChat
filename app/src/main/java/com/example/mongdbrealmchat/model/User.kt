package com.example.mongdbrealmchat.model

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmField
import java.util.*

open class User(
        @PrimaryKey
        @RealmField("_id")
        var id: String = UUID.randomUUID().toString(),
        var email: String = "",
        var password: String = ""
) : RealmObject()