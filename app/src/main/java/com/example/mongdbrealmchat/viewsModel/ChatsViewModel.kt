package com.example.mongdbrealmchat.viewsModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mongdbrealmchat.BuildConfig
import com.example.mongdbrealmchat.model.User
import com.example.mongdbrealmchat.model.Chats
import com.example.mongdbrealmchat.model.Mesages
import io.realm.Realm
import io.realm.kotlin.createObject
import io.realm.mongodb.App
import io.realm.mongodb.Credentials
import io.realm.mongodb.sync.SyncConfiguration
import java.util.*

class ChatsViewModel:ViewModel() {
    private val _chats = MutableLiveData<Chats>()
    val chats:LiveData<Chats?> = _chats
    private val realmApp = App(BuildConfig.RealmAppId)

    init{
        realmApp.loginAsync(Credentials.anonymous()){
            if(it.isSuccess){
                val config = SyncConfiguration.Builder(it.get(), it.get().id).build()
                Realm.getInstanceAsync(config, object : Realm.Callback() {
                    override fun onSuccess(realm: Realm) {
                        val realm = Realm.getDefaultInstance()
                        val chat = realm.where(Chats::class.java).findFirst()
                        _chats.postValue(chat)
                    }
                })
            }
        }
    }

    fun onAddMesage(mesage:String, user:User){
        realmApp.loginAsync(Credentials.anonymous()){
            if(it.isSuccess){
                val config = SyncConfiguration.Builder(it.get(), it.get().id).build()
                Realm.getInstanceAsync(config, object : Realm.Callback() {
                    override fun onSuccess(realm: Realm) {
                        realm.executeTransactionAsync{
                            var chat = realm.createObject<Chats>()

                            var mesage = realm.createObject<Mesages>()
                            mesage = mesage

                            mesage.user?.id = user.id.toString()
                            mesage.user?.email = user.email.toString()
                            chat.mesages.add(mesage)
                        }
                    }
                })
            }
        }

    }



}