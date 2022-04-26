package com.example.test.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.test.R
import com.example.test.adapter.ChatAdapter
import com.example.test.databinding.FragmentChatBinding
import com.example.test.entity.Chat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class ChatFragment : Fragment() {
private lateinit var design : FragmentChatBinding
private lateinit var auth : FirebaseAuth
private lateinit var fireStore : FirebaseFirestore
private lateinit var adapter: ChatAdapter
private  var chats = arrayListOf<Chat>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        design = DataBindingUtil.inflate(inflater,R.layout.fragment_chat, container, false)

        fireStore = Firebase.firestore
        auth = Firebase.auth

        adapter = ChatAdapter()
        design.chatRvv.adapter = adapter
        design.chatRvv.layoutManager = LinearLayoutManager(requireContext())
        design.sendButton.setOnClickListener {
            auth.currentUser?.let {
                val user = it.email
                val chatText = design.messageText.text.toString()
                val date = FieldValue.serverTimestamp()
                val userName = it.displayName

                val dataMap = HashMap<String,Any>()
                dataMap.put("text",chatText)
                dataMap.put("user",user!!)
                dataMap.put("date",date)


                fireStore.collection("Chats").add(dataMap)
                    .addOnSuccessListener {
                        design.messageText.setText("")
                    }
                    .addOnFailureListener {

                    } }
        }

        fireStore.collection("Chats").orderBy("date",Query.Direction.ASCENDING)
            .addSnapshotListener{value,error->

                if (error != null) {
                    Toast.makeText(requireContext(),"Beklenmedik bir hata oluştu",Toast.LENGTH_SHORT).show()
                }else{
                    if (value != null) {
                        if (value.isEmpty) {
                            Toast.makeText(requireContext(),"Mesaj yok",Toast.LENGTH_SHORT).show()
                        }else {
                            val documents = value.documents
                            chats.clear()
                            for (document in documents) {
                                val text = document.get("text") as String
                                val user = document.get("user") as String
                                val chat = Chat(user,text)
                                chats.add(chat)
                                adapter.chats = chats
                            }
                        }
                        adapter.notifyDataSetChanged()
                    }

                }


            }

        return design.root
    }

}