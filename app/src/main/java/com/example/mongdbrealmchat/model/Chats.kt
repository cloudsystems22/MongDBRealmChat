package com.example.mongdbrealmchat.model

import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import java.util.*

open class Chats (
        @PrimaryKey
        var id: String = UUID.randomUUID().toString(),
        var dataCreated:Date = Date(),
        var mesages:RealmList<Mesages> = RealmList()

) : RealmObject()