package com.example.mongdbrealmchat

import android.net.Credentials
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import io.realm.Realm
import io.realm.SyncCredentials.anonymous

class MainActivity : AppCompatActivity() {
    private val appID : String = "<Your App ID>";

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Realm.init(this)

    }

}