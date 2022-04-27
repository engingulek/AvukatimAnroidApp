package com.example.test.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.test.R
import com.example.test.adapter.ChatListAdapter
import com.example.test.databinding.FragmentChatListBinding
import com.example.test.entity.Chat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class ChatListFragment : Fragment() {
   private lateinit var design : FragmentChatListBinding
    private lateinit var auth : FirebaseAuth
    private lateinit var fireStore : FirebaseFirestore
    private  var chatUserList = arrayListOf<String>()
    private lateinit var adapter: ChatListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        design = DataBindingUtil.inflate(inflater,R.layout.fragment_chat_list, container, false)
        fireStore = Firebase.firestore
        auth = Firebase.auth
        design.fragment = this


        fireStore.collection("Chats")
            .document(auth.currentUser?.uid!!)
            .collection("message")
            .addSnapshotListener {value,error ->
                if (error != null) {
                    //  Toast.makeText(requireContext(),"Beklenmedik bir hata oluştu",Toast.LENGTH_SHORT).show()
                }else{
                    if (value != null) {
                        if (value.isEmpty) {
                           // Toast.makeText(requireContext(),"Mesaj yok",Toast.LENGTH_SHORT).show()
                        }else {
                            val documents = value.documents
                            chatUserList.clear()
                            for (document in documents) {
                                val text = document.get("chatText") as String
                                val user = document.get("getUserName") as String

                                if (user != auth.currentUser?.displayName) {
                                    if (chatUserList.isEmpty()) {
                                        chatUserList.add(user)
                                    }
                                    else{
                                        val check = chatUserList.contains(user)
                                        if (check) {

                                        }else{
                                            chatUserList.add(user)

                                        }
                                    }

                                }
                                adapter = ChatListAdapter(requireContext(),chatUserList)
                                design.userListRvv.adapter = adapter
                                design.userListRvv.layoutManager = LinearLayoutManager(requireContext())


                            }
                        }
                        adapter.notifyDataSetChanged()
                    }

                }


            }





        return design.root
    }


}