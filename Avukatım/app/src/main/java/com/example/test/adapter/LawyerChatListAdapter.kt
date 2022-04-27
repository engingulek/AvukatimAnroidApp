package com.example.test.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.test.R
import com.example.test.databinding.UserListDesignBinding
import com.example.test.fragment.LawyerTabLayoutFragmentDirections
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LawyerChatListAdapter(var mContext: Context, var userList: HashMap<String,Any>)
    : RecyclerView.Adapter<LawyerChatListAdapter.ChatHolder>() {
    private lateinit var auth : FirebaseAuth

    inner class ChatHolder(userListDesignBinding: UserListDesignBinding)
        : RecyclerView.ViewHolder(userListDesignBinding.root){
        var userListDesignBinding = userListDesignBinding
        init {
            this.userListDesignBinding = userListDesignBinding
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatHolder {
        val layoutInflater = LayoutInflater.from(mContext)
        val design = UserListDesignBinding.inflate(layoutInflater,parent,false)
        return ChatHolder(design)

    }

    override fun onBindViewHolder(holder: ChatHolder, position: Int) {
        val userName = userList.get("getUserName")
        val textDesing = holder.userListDesignBinding
        textDesing.userNameText.text = userName.toString()
        val uuid = userList.get("getUid")
        textDesing.userNameText.setOnClickListener {
            auth = Firebase.auth
             val pass = LawyerTabLayoutFragmentDirections.toLawyerChat(userName.toString(),uuid.toString())
            Navigation.findNavController(it).navigate(pass)
        }
    }

    override fun getItemCount(): Int {
        return userList.size
    }


}