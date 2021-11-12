package com.example.mongdbrealmchat.viewsModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mongdbrealmchat.BuildConfig
import io.realm.Realm
import com.example.mongdbrealmchat.model.User
import io.realm.mongodb.App
import io.realm.mongodb.Credentials
import io.realm.mongodb.sync.SyncConfiguration

class UserViewModel:ViewModel() {

    private val _user = MutableLiveData<User>()
    val user:LiveData<User?> = _user
    private val realmApp = App(BuildConfig.RealmAppId)

    init{
        realmApp.loginAsync(Credentials.anonymous()){
            if(it.isSuccess){
                val config = SyncConfiguration.Builder(it.get(), it.get().id).build()
                Realm.getInstanceAsync(config, object : Realm.Callback() {
                    override fun onSuccess(realm: Realm) {
                        val realm = Realm.getDefaultInstance()
                        val user = realm.where(User::class.java).findFirst()
                        _user.postValue(user)
                    }
                })
            }

        }

    }

    fun onSubmit(name:String){
        realmApp.loginAsync(Credentials.anonymous()){
            if(it.isSuccess){
                val config = SyncConfiguration.Builder(it.get(), it.get().id).build()
                Realm.getInstanceAsync(config, object : Realm.Callback() {
                    override fun onSuccess(realm: Realm) {
                        realm.executeTransactionAsync{
                            var user = User().apply{
                                this.email = name
                            }
                            it.copyToRealmOrUpdate(user)
                        }
                    }
                })
            }

        }
    }

    fun updateUser(id:String, email:String){
        realmApp.loginAsync(Credentials.anonymous()){
            if(it.isSuccess){
                val config = SyncConfiguration.Builder(it.get(), it.get().id).build()
                Realm.getInstanceAsync(config, object : Realm.Callback() {
                    override fun onSuccess(realm: Realm) {
                        realm.executeTransactionAsync{
                            var user = it.where<User>(User::class.java)
                                    .equalTo("id", id).findFirst()

                            if(user != null) {
                                user.email = email
                            }else{
                                user = User().apply {
                                    this.email = email
                                }
                            }
                            it.copyToRealmOrUpdate(user)
                        }
                    }
                })
            }

        }
    }

    fun userDeleted(id: String) {
        realmApp.loginAsync(Credentials.anonymous()){
            if(it.isSuccess){
                val config = SyncConfiguration.Builder(it.get(), it.get().id).build()
                Realm.getInstanceAsync(config, object : Realm.Callback() {
                    override fun onSuccess(realm: Realm) {
                        realm.executeTransactionAsync {
                            it.where(User::class.java)
                                    .equalTo("id", id)
                                    .findFirst()
                                    ?.deleteFromRealm()
                        }
                    }
                })
            }

        }

    }

    fun getAllUser():ArrayList<User>{
        val usrList: ArrayList<User> = ArrayList()
        //val realm = Realm.getDefaultInstance()

        realmApp.loginAsync(Credentials.anonymous()){
            if(it.isSuccess){
                val config = SyncConfiguration.Builder(it.get(), it.get().id).build()
                Realm.getInstanceAsync(config, object : Realm.Callback() {
                    override fun onSuccess(realm: Realm) {
                        usrList.addAll(realm.where<User>(User::class.java).findAll())
                        Log.e("Users:", "${usrList}")

                    }
                })
            }

        }
        return usrList
    }

}