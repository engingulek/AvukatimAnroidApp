package com.example.test.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.test.R
import com.example.test.adapter.ChatListAdapter
import com.example.test.adapter.LawyerChatAdapter
import com.example.test.adapter.LawyerChatListAdapter
import com.example.test.databinding.FragmentLawyerChatListBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class LawyerChatListFragment : Fragment() {
   private lateinit var design : FragmentLawyerChatListBinding
    private lateinit var auth : FirebaseAuth
    private lateinit var fireStore : FirebaseFirestore
    private  var chatUserList = HashMap<String,Any>()
    private lateinit var adapter: LawyerChatListAdapter



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        design = DataBindingUtil.inflate(inflater,R.layout.fragment_lawyer_chat_list, container, false)

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
                                val getUUid = document.get("getuuid") as String

                                if (user != auth.currentUser?.displayName) {
                                    if (chatUserList.isEmpty()) {
                                        chatUserList.put("getUserName",user)
                                        chatUserList.put("getUid",getUUid)
                                    }
                                    else{
                                        val check = chatUserList.contains(user)
                                        if (check) {

                                        }else{
                                            chatUserList.put("getUserName",user)
                                            chatUserList.put("getUid",getUUid)

                                        }
                                    }

                                }
                                adapter = LawyerChatListAdapter(requireContext(),chatUserList)
                                design.userListRvv.adapter = adapter
                                design.userListRvv.layoutManager = LinearLayoutManager(requireContext())


                            }
                        }
                        if(chatUserList.size != 0) {
                            adapter.notifyDataSetChanged()
                        }

                    }

                }


            }





        return design.root
    }


}