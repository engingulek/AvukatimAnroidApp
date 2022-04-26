package com.example.test.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.test.databinding.FragmentChatListBinding
import android.content.Context
import androidx.navigation.Navigation
import com.example.test.databinding.UserListDesignBinding
import com.example.test.R

class ChatListAdapter(var mContext: Context,var userList: List<String>)
    :RecyclerView.Adapter<ChatListAdapter.ChatHolder>() {

        inner class ChatHolder(userListDesignBinding: UserListDesignBinding)
            :RecyclerView.ViewHolder(userListDesignBinding.root){
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
        val userName = userList.get(position)
        val textDesing = holder.userListDesignBinding
        textDesing.userNameText.text = userName
        textDesing.userNameText.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.toChatTwo)
        }
    }

    override fun getItemCount(): Int {
        return userList.size
    }


}