package com.example.test.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.test.R
import com.example.test.databinding.RvvRowBinding
import com.example.test.entity.Chat
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.delay

class ChatAdapter : RecyclerView.Adapter<ChatAdapter.ChatHolder>() {
private val VIEW_TYPE_MESSAGE_SENT  = 1
    private val VIEW_TYPE_MESSAGE_SENT_RECEIVED = 2

    inner class ChatHolder(rvvRowBinding: RvvRowBinding)
        :RecyclerView.ViewHolder(rvvRowBinding.root) {
            var rvvRowBinding :RvvRowBinding
            init {
                this.rvvRowBinding = rvvRowBinding
            }

        }




    private val diffUtil = object :DiffUtil.ItemCallback<Chat>() {
        override fun areItemsTheSame(oldItem: Chat, newItem: Chat): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Chat, newItem: Chat): Boolean {
            return  oldItem == newItem
        }

    }

    private val recyclerListDiffer = AsyncListDiffer(this,diffUtil)

    var chats : List<Chat>
    get() = recyclerListDiffer.currentList
    set(value) = recyclerListDiffer.submitList(value)

    override fun getItemViewType(position: Int): Int {

        val chat = chats.get(position)

        if(chat.user == FirebaseAuth.getInstance().currentUser?.email.toString()) {

            return VIEW_TYPE_MESSAGE_SENT
        }else{
            return  VIEW_TYPE_MESSAGE_SENT_RECEIVED

        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatHolder {

        if (viewType == VIEW_TYPE_MESSAGE_SENT_RECEIVED) {
            val view = LayoutInflater.from(parent.context)
            val design = RvvRowBinding.inflate(view,parent,false)

            return ChatHolder(design)
        }
        else{
            val view = LayoutInflater.from(parent.context)
            val design = RvvRowBinding.inflate(view,parent,false)
            design.chatTextView.textAlignment = View.TEXT_ALIGNMENT_TEXT_END
            return ChatHolder(design)

        }



    }

    override fun onBindViewHolder(holder: ChatHolder, position: Int) {
        val cardDesing = holder.rvvRowBinding
        cardDesing.chatTextView.text = "$${chats.get(position).user} : ${chats.get(position).text}"

    }

    override fun getItemCount(): Int {
        return chats.size

    }
}