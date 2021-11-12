package com.example.mongdbrealmchat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mongdbrealmchat.model.User
import com.example.mongdbrealmchat.viewsModel.UserViewModel


class MainActivity : AppCompatActivity() {

    private lateinit var btnAdd:Button
    private lateinit var btnView:Button
    private lateinit var btnUpdate:Button
    private lateinit var edEmail:EditText

    private lateinit var userViewModel: UserViewModel
    private lateinit var recyclerView: RecyclerView

    private var adapter: UserAdapter? = null
    private var usr:User? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initview()
        initRecyclerView()

        userViewModel = UserViewModel()

        btnAdd.setOnClickListener {
            addUser()
        }

        btnUpdate.setOnClickListener{
            updateUser()
        }

        btnView.setOnClickListener {
            getUsers()
        }

        adapter?.setOnClickItem {
            Toast.makeText(this, it.email, Toast.LENGTH_SHORT).show()
            edEmail.setText(it.email)

            usr = it

        }

        adapter?.setOnClickDeleteItem {
            deleteUser(it.id)
        }


    }

    private fun getUsers(){
        val stdUser = userViewModel.getAllUser()
        Log.e("Users:", "${stdUser}")

        adapter?.addItems(stdUser)
    }

    private fun addUser(){
        val email = edEmail.text.toString()
        if(email == null){
            Toast.makeText(this,"Entre com nome", Toast.LENGTH_SHORT).show()
            return
        }

        try{
            userViewModel.onSubmit(email)
            clearEditText()
            getUsers()
            Toast.makeText(this, "Adicionado com sucesso!", Toast.LENGTH_SHORT).show()
        } catch(e:Exception) {
            Toast.makeText(this, "Erro ao adicionar $e", Toast.LENGTH_SHORT).show()
        }

    }

    private fun updateUser(){
        val email = edEmail.text.toString()

        if(email == usr?.email){
            Toast.makeText(this, "Não atualizado!", Toast.LENGTH_SHORT).show()
            return
        }

        if(usr == null) return

        val newUser = User(usr!!.id, email)

        try{
            userViewModel.updateUser(newUser.id, email)
            clearEditText()
            getUsers()
        }catch(e:Exception){
            Toast.makeText(this, "Erro ao atualizar!", Toast.LENGTH_SHORT).show()
        }


    }

    private fun deleteUser(id:String){
        if(id == null) return

        val builder = AlertDialog.Builder(this)
        builder.setMessage("Tem certeza que quer excluir esse registro?")
        builder.setCancelable(true)
        builder.setPositiveButton("Sim"){ dialog, _->
            userViewModel.userDeleted(id)
            getUsers()
            dialog.dismiss()
        }
        builder.setNegativeButton("Não"){ dialog, _->
            dialog.dismiss()
        }

        val alert = builder.create()
        alert.show()
    }

    private fun initRecyclerView(){
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = UserAdapter()
        recyclerView.adapter = adapter
    }

    private fun initview(){
        edEmail = findViewById(R.id.edEmail)
        btnAdd = findViewById(R.id.btnAdd)
        btnView = findViewById(R.id.btnView)
        btnUpdate = findViewById(R.id.btnUpdate)
        recyclerView = findViewById(R.id.recyclerView)
    }

    private fun clearEditText(){
        edEmail.setText("")
        edEmail.requestFocus()
    }


}