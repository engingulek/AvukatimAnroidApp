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
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FieldValue
import com.squareup.picasso.Picasso
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*
import kotlin.collections.HashMap
import kotlin.time.Duration.Companion.days

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
        val contactImage = userList.get("lawyerImage")
        val chatText = userList.get("chatText")
        val timestamp = userList.get("date") as Timestamp


        // mesajın atıldığı tarih
        val milliseconds = timestamp.seconds * 1000 + timestamp.nanoseconds / 1000000
        val sdf = SimpleDateFormat("dd/MM/yyyy")
        val netDate = Date(milliseconds)
        val messageSendDate = sdf.format(netDate).toString()


        // mesajın atıldığı saat
        val sdfHours = SimpleDateFormat("HH:mm")
        val netHours = Date(milliseconds)
        val messageLastHours = sdfHours.format(netHours).toString()




        // mesajın atıldığı gün (sayı)
        val sdfDay = SimpleDateFormat("dd")
        val netDateDay = Date(milliseconds)
        val messageLastDay = sdfDay.format(netDateDay).toString()


        // şuanki tarih
        val sdfLocal = SimpleDateFormat("dd")
        val currentDateDay = sdfLocal.format(Date()).toString()



        Log.e("mm","${messageLastDay}")
        Log.e("nn","${currentDateDay}")









        val textDesing = holder.userListDesignBinding

        textDesing.userNameText.text = userName.toString()
        Picasso.get().load(contactImage.toString()).into(textDesing.contactImage)
        Log.e("Last Message",chatText.toString())
        textDesing.messageTextView.text = chatText.toString()

        val dateDiff =  messageLastDay.toInt() - currentDateDay.toInt()
        Log.e("Fark","${dateDiff}")



        if (currentDateDay.toInt() == messageLastDay.toInt()){
            textDesing.lastMessageDate.text = messageLastHours
        }else if (dateDiff == 30 || dateDiff == 29 || dateDiff == 28 || dateDiff == 27 || dateDiff == -1){
            textDesing.lastMessageDate.text = "Dün"
        }else{
            textDesing.lastMessageDate.text = messageSendDate
        }






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