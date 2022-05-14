package com.example.test.fragment

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.test.R
import com.example.test.adapter.ChatAdapter
import com.example.test.adapter.LawyerChatAdapter
import com.example.test.databinding.FragmentLawyerChatBinding
import com.example.test.entity.Chat
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.UploadTask
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.HashMap


class LawyerChatFragment : Fragment() {
    private lateinit var design : FragmentLawyerChatBinding
    private lateinit var fireStore : FirebaseFirestore
    private lateinit var adapter: LawyerChatAdapter
    private  var chats = arrayListOf<Chat>()
    private lateinit var auth : FirebaseAuth
    private var  lawyerImage = ""
    private  var  clientImage = ""

    private var imageUri: Uri? = null
    private var imageChatrUrl:String? = ""

    companion object {
        val IMAGE_REQUEST_CODE = 100
    }




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        design = DataBindingUtil.inflate(inflater,R.layout.fragment_lawyer_chat, container, false)
        design.fragment = this
        fireStore = Firebase.firestore
        auth = Firebase.auth

        adapter = LawyerChatAdapter()
        design.chatRvv.adapter = adapter
        design.chatRvv.layoutManager = LinearLayoutManager(requireContext())

        val bundle : LawyerChatFragmentArgs by navArgs()
        val getUserNamer  = bundle.getUserName
        design.lawyernateTe.text = getUserNamer

        design.sendButton.setOnClickListener {

            if (design.messageText.text.toString() != ""){


                auth.currentUser?.let {
                    val senduser = it.email
                    val sendUserName = it.displayName
                    val sendUuid = it.uid
                    val getUserName  = bundle.getUserName
                    val getuuid = bundle.getUuid
                    val chatText = design.messageText.text.toString()
                    val date = FieldValue.serverTimestamp()
                   // val testImageLawyer = "https://firebasestorage.googleapis.com/v0/b/avukatimauth.appspot.com/o/images%2F9d9e9e66-408b-4b8d-9fc2-3424cceb7156.jpg?alt=media&token=33fb9209-3fd1-4496-8155-7b3cfa962c23"
                    //val testImageClient = "https://firebasestorage.googleapis.com/v0/b/avukatimauth.appspot.com/o/images%2Fprofile.png?alt=media&token=de68420b-c006-459e-bacf-adc4a0ea72e6"

                    Log.e("müşteri id","${getuuid}")



                    val clientNamaeData  = HashMap<String,Any>()
                    clientNamaeData.put("clientName",getUserName!!)
                    clientNamaeData.put("lawyerName",sendUserName!!)
                    clientNamaeData.put("clientid",getuuid)
                    clientNamaeData.put("lawyerid",sendUuid)
                    clientNamaeData.put("lawyerImage",lawyerImage)




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
                    dataMap.put("lawyerImage",lawyerImage)
                    dataMap.put("clientImage",clientImage)
                    dataMap.put("chatImage","")

                    Log.e("sendUuid","${sendUuid}")
                    Log.e("authUuid","${auth.currentUser?.uid}")
                    Log.e("getUuid","${getuuid}")




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

                    dataMapA.put("lawyerImage",lawyerImage)
                    dataMapA.put("clientImage",clientImage)
                    dataMapA.put("chatImage","")




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
            .orderBy("date", Query.Direction.ASCENDING)
            .addSnapshotListener { value, error ->
                if (error != null) {
                    //  Toast.makeText(requireContext(),"Beklenmedik bir hata oluştu",Toast.LENGTH_SHORT).show()
                }else{
                    if (value != null) {
                        if (value.isEmpty) {
                            Toast.makeText(requireContext(),"Mesaj yok", Toast.LENGTH_SHORT).show()
                        }else {
                            val documents = value.documents
                            chats.clear()
                            for (document in documents) {
                                val text = document.get("chatText") as String
                                val user = document.get("senduser") as String
                                 lawyerImage = document.get("lawyerImage") as String
                                 clientImage = document.get("clientImage") as String
                                val chatImageUrlData = document.get("chatImage")




                                if (document.get("date") != null) {
                                    val timestamp = document.get("date") as com.google.firebase.Timestamp
                                    val milliseconds = timestamp.seconds * 1000 + timestamp.nanoseconds / 1000000
                                    val sdf = SimpleDateFormat("dd/MM/yyyy HH:mm")
                                    val netDate = Date(milliseconds)
                                    val date = sdf.format(netDate).toString()

                                    val chat = Chat(user,text,lawyerImage.toString(),clientImage.toString(),date.toString(),chatImageUrlData.toString())
                                    chats.add(chat)
                                    adapter.chats = chats
                                }



                            }
                        }
                        adapter.notifyDataSetChanged()
                    }

                }

            }
        design.sendImageButton.setOnClickListener {
            if (design.imageAndDocConsL.visibility == View.GONE) {
                design.imageAndDocConsL.visibility = View.VISIBLE
            }else{
                design.imageAndDocConsL.visibility = View.GONE

            }
        }

        design.sendImageMessage.setOnClickListener {
            println("Tıklandığında resim gönderileck")
            //1.Galeri açılmakta
            pickImageGallery()
            Log.e("SendImageButton","${imageChatrUrl}")

        }





        return design.root
    }

    fun addSendImage(chatImageUrls : String) {
        val bundle : LawyerChatFragmentArgs by navArgs()
        val getUserNamer  = bundle.getUserName



            auth.currentUser?.let {
                val senduser = it.email
                val sendUserName = it.displayName
                val sendUuid = it.uid
                val getUserName  = bundle.getUserName
                val getuuid = bundle.getUuid
                val chatText = design.messageText.text.toString()
                val date = FieldValue.serverTimestamp()
                // val testImageLawyer = "https://firebasestorage.googleapis.com/v0/b/avukatimauth.appspot.com/o/images%2F9d9e9e66-408b-4b8d-9fc2-3424cceb7156.jpg?alt=media&token=33fb9209-3fd1-4496-8155-7b3cfa962c23"
                //val testImageClient = "https://firebasestorage.googleapis.com/v0/b/avukatimauth.appspot.com/o/images%2Fprofile.png?alt=media&token=de68420b-c006-459e-bacf-adc4a0ea72e6"

                Log.e("müşteri id","${getuuid}")



                val clientNamaeData  = HashMap<String,Any>()
                clientNamaeData.put("clientName",getUserName!!)
                clientNamaeData.put("lawyerName",sendUserName!!)
                clientNamaeData.put("clientid",getuuid)
                clientNamaeData.put("lawyerid",sendUuid)
                clientNamaeData.put("lawyerImage",lawyerImage)




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
                dataMap.put("chatText","")
                dataMap.put("date",date!!)
                dataMap.put("lawyerImage",lawyerImage)
                dataMap.put("clientImage",clientImage)
                dataMap.put("chatImage","${imageChatrUrl}")

                Log.e("sendUuid","${sendUuid}")
                Log.e("authUuid","${auth.currentUser?.uid}")
                Log.e("getUuid","${getuuid}")




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
                dataMapA.put("chatText","")
                dataMapA.put("date",date!!)

                dataMapA.put("lawyerImage",lawyerImage)
                dataMapA.put("clientImage",clientImage)
                dataMapA.put("chatImage","${imageChatrUrl}")




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

    fun uploadStrongeImage(fileUri: Uri) {
        val fileName = UUID.randomUUID().toString()+".jpg"
        val database = FirebaseDatabase.getInstance()

        var imageUrl = ""
        val refStorage = FirebaseStorage.getInstance().reference.child("messageImages/$fileName")
        refStorage.putFile(fileUri)
            .addOnSuccessListener(
                OnSuccessListener<UploadTask.TaskSnapshot> { taskSnapshot ->
                    taskSnapshot.storage.downloadUrl.addOnSuccessListener {
                        imageUrl = it.toString()
                        imageUrlString(imageUrl)
                        Log.e("Resimin urlsi","${imageUrl}")
                        addSendImage(imageUrl)
                    }
                })

            .addOnFailureListener(OnFailureListener { e ->
                print(e.message)
            })

    }

    fun imageUrlString(k:String) {
        imageChatrUrl = k

    }

    private  fun pickImageGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, CreateLawyerAdvertFragment.IMAGE_REQUEST_CODE)

    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == CreateLawyerAdvertFragment.IMAGE_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            imageUri = data?.data
            uploadStrongeImage(imageUri!!)
            design.imageAndDocConsL.visibility = View.GONE
            Log.e("daa","${data?.data}")

        }


    }


}