package com.example.test.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.test.databinding.FragmentChatListBinding
import android.content.Context
import android.util.Log
import androidx.navigation.Navigation
import com.example.test.databinding.UserListDesignBinding
import com.example.test.R
import com.example.test.fragment.ClientTabLayoutFragmentDirections

class ChatListAdapter(var mContext: Context,var userList: HashMap<String,Any>)
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
        val userName = userList.get("getUserName")


        val textDesing = holder.userListDesignBinding
        textDesing.userNameText.text = userName.toString()
        val uuid = userList.get("getUid")
        textDesing.userNameText.setOnClickListener {
            Log.e("A3","${userName.toString()}")
            Log.e("A4","${uuid.toString()}")
            val lawyerImage = userList.get("lawyerImage")
            Log.e("A16","$lawyerImage")

           val pass = ClientTabLayoutFragmentDirections.toChatTwo(userName.toString(),uuid.toString(),lawyerImage.toString())
            Navigation.findNavController(it).navigate(pass)
        }
    }

    override fun getItemCount(): Int {
        return 1
    }


}