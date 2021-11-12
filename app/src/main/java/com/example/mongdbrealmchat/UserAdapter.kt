package com.example.mongdbrealmchat

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mongdbrealmchat.model.User

class UserAdapter() : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {
    private var usrList: ArrayList<User> = ArrayList()
    private var onClickItem: ((User) -> Unit)? = null
    private var onClickDeleteItem: ((User) -> Unit)? = null

    fun addItems(items: ArrayList<User>){
        this.usrList = items
        notifyDataSetChanged()
    }

    fun setOnClickItem(callback: (User) -> Unit){
        this.onClickItem = callback
    }

    fun setOnClickDeleteItem(callback: (User) -> Unit){
        this.onClickDeleteItem = callback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = UserAdapter.UserViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.card_items_usr, parent, false)
    )

    override fun onBindViewHolder(holder: UserAdapter.UserViewHolder, position: Int) {
        val usr = usrList[position]
        holder.bindView(usr)
        holder.itemView.setOnClickListener { onClickItem?.invoke(usr) }
        holder.btnDelete.setOnClickListener { onClickDeleteItem?.invoke(usr) }
    }

    override fun getItemCount(): Int {
        return usrList.size
    }

    class UserViewHolder(var view: View):RecyclerView.ViewHolder(view){
        private var id = view.findViewById<TextView>(R.id.tvId)
        private var email = view.findViewById<TextView>(R.id.tvEmail)
        var btnDelete = view.findViewById<Button>(R.id.btnDelete)

        fun bindView(usr:User){
            id.text = usr.id.toString()
            email.text = usr.email.toString()
        }

    }
}