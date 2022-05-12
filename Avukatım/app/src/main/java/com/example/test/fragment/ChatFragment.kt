package com.example.test.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.navArgs
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

        val bundle : ChatFragmentArgs by navArgs()


        design.sendButton.setOnClickListener {

            if (design.messageText.text.toString() != ""){


                auth.currentUser?.let {


                    val clientuser = it.email
                    val clientUserName = it.displayName


                    val senduser = it.email
                    val sendUserName = it.displayName
                    val sendUuid = it.uid
                    val getUserName  = bundle.getUserName
                    val getuuid = bundle.getUuid
                    Log.e("uuda","${getuuid}")
                    val chatText = design.messageText.text.toString()
                    val date = FieldValue.serverTimestamp()
                    




                    Log.e("Müşteri adı","${sendUserName}")
                    Log.e("Avukat adı","${getUserName}")
                    design.nateTe.text = getUserName

                    val clientNamaeData  = HashMap<String,Any>()
                    clientNamaeData.put("clientName",sendUserName!!)
                    clientNamaeData.put("lawyerName",getUserName!!)
                    clientNamaeData.put("clientid",sendUuid)
                    clientNamaeData.put("lawyerid",getuuid)


                    fireStore.collection("Chats")
                        .document(auth.currentUser?.uid!!).collection("nameData").document("0").set(clientNamaeData)
                        .addOnSuccessListener {
                            design.messageText.setText("")
                        }
                        .addOnFailureListener {
                            // HATA İLE KARŞILAŞILDI
                        }


                    fireStore.collection("Chats")
                        .document(getuuid).collection("nameData").document("0").set(clientNamaeData)
                        .addOnSuccessListener {
                            design.messageText.setText("")
                        }
                        .addOnFailureListener {
                            // HATA İLE KARŞILAŞILDI
                        }









                        val dataMap = HashMap<String,Any>()
                         dataMap.put("senduser",senduser!!)

                         dataMap.put("sendUserName",sendUserName!!)

                         dataMap.put("sendUuid",sendUuid!!)

                         dataMap.put("getUserName",getUserName!!)

                         dataMap.put("getuuid",getuuid!!)

                         dataMap.put("chatText",chatText!!)
                         dataMap.put("date",date!!)




                         fireStore.collection("Chats")
                             .document(auth.currentUser?.uid!!).collection("message").add(dataMap)
                             .addOnSuccessListener {
                                 design.messageText.setText("")
                             }
                             .addOnFailureListener {
                                 // HATA İLE KARŞILAŞILDI
                             }


                         val dataMapA = HashMap<String,Any>()
                         dataMapA.put("senduser",senduser!!)

                         dataMapA.put("sendUserName",getUserName!!)

                         dataMapA.put("sendUuid",sendUuid!!)

                         dataMapA.put("getUserName",sendUserName!!)

                         dataMapA.put("getuuid",getuuid!!)
                         dataMapA.put("chatText",chatText!!)
                         dataMapA.put("date",date!!)


                         fireStore.collection("Chats")
                             .document(getuuid).collection("message").add(dataMapA)
                             .addOnSuccessListener {
                                 design.messageText.setText("")
                             }
                             .addOnFailureListener {
                                 // HATA İLE KARŞILAŞILDI
                             }





                }

            }


        }


        fireStore.collection("Chats")
            .document(auth.currentUser?.uid!!)
            .collection("message")
            .orderBy("date",Query.Direction.ASCENDING)
            .addSnapshotListener { value, error ->
                if (error != null) {
                  //  Toast.makeText(requireContext(),"Beklenmedik bir hata oluştu",Toast.LENGTH_SHORT).show()
                }else{
                    if (value != null) {
                        if (value.isEmpty) {
                            Toast.makeText(requireContext(),"Mesaj yok",Toast.LENGTH_SHORT).show()
                        }else {
                            val documents = value.documents
                            chats.clear()
                            for (document in documents) {
                                val text = document.get("chatText")
                                val user = document.get("senduser")
                                val chat = Chat(user.toString(),text.toString())
                                chats.add(chat)
                                adapter.chats = chats
                            }
                        }
                        adapter.notifyDataSetChanged()
                    }

                }

            }



      /* fireStore.collection("Chats").orderBy("date",Query.Direction.ASCENDING)
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


            }*/

        return design.root
    }

}