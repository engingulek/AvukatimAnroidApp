package com.example.test.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.test.R
import com.example.test.adapter.ChatListAdapter
import com.example.test.adapter.LawyerChatListAdapter
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
    private  var chatUserList = HashMap<String,Any>()
    private lateinit var adapter: ChatListAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        design = DataBindingUtil.inflate(inflater,R.layout.fragment_chat_list, container, false)
        fireStore = Firebase.firestore
        auth = Firebase.auth
        design.fragment = this
        Log.e("Pzar","${auth.currentUser?.uid}")



        fireStore.collection("Chats")
            .document(auth.currentUser?.uid!!)
            .collection("nameData").document("0").addSnapshotListener { value, error ->

                if (error != null){

                }else{
                    if(value != null){

                        if(value.get("lawyerName") == null) {

                        }else{
                            Log.e("ContactNAME","${value.get("lawyerName")}")
                            Log.e("Contactid","${value.get("lawyerid")}")


                            if(value.get("lawyerName") != null && value.get("lawyerImage") != null) {
                                chatUserList.put("getUserName",value.get("lawyerName")!!)
                                chatUserList.put("getUid",value.get("lawyerid")!!)
                                chatUserList.put("lawyerImage",value.get("lawyerImage")!!)
                                chatUserList.put("chatText",value.get("chatText")!!)
                                chatUserList.put("date",value.get("date")!!)

                                Log.e("A6","${value.get("lawyerid")!!}")
                                Log.e("A7","${chatUserList.get("getUid")}")

                                if (value.get("lawyerName") != null && value.get("lawyerImage") != null && value.get("lawyerid") != null) {
                                    adapter = ChatListAdapter(requireContext(),chatUserList)
                                    design.userListRvv.adapter = adapter
                                    design.userListRvv.layoutManager = LinearLayoutManager(requireContext())
                                    Log.e("Test size","${chatUserList.size}")

                                }

                            }


                        }


                    }
                }
            }


        return design.root
    }


    fun getImageLawyer() {



    }


}