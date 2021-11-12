package com.example.mongdbrealmchat.model

import io.realm.RealmObject
import java.util.*

open class Mesages(
        var user:User? = null,
        var mesage:String = "",
        var date: Date = Date()
) : RealmObject()