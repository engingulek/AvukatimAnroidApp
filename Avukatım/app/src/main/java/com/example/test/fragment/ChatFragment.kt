package com.example.test.fragment

import android.app.Activity
import android.app.DownloadManager
import android.content.Intent
import android.net.Uri
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
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.Timestamp
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
import kotlin.math.log
import android.content.ContentResolver
import android.content.Context
import android.os.Environment
import android.os.FileUtils
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.net.toUri
import java.lang.Exception


class ChatFragment : Fragment() {
private lateinit var design : FragmentChatBinding
private lateinit var auth : FirebaseAuth
private lateinit var fireStore : FirebaseFirestore
private lateinit var adapter: ChatAdapter
private  var chats = arrayListOf<Chat>()
    private var imageUri: Uri? = null
    private var imageChatrUrl:String? = ""




    companion object {
        val IMAGE_REQUEST_CODE = 100
    }



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
        val lawyerImagee = bundle.getIawyerImageUrl
        Log.e("A18","$lawyerImagee")



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
                    val lawyerImage = bundle.getIawyerImageUrl
                    val clientImage = it.photoUrl
                    val chatText = design.messageText.text.toString()
                    val date = FieldValue.serverTimestamp()



                    Log.e("Avukat resim url",bundle.getIawyerImageUrl)
                    Log.e("Kullanıcı image url","${it.photoUrl}")





                    Log.e("Müşteri adı","${sendUserName}")
                    Log.e("Avukat adı","${getUserName}")
                    design.nateTe.text = getUserName

                    val clientNamaeData  = HashMap<String,Any>()
                    clientNamaeData.put("clientName",sendUserName!!)
                    clientNamaeData.put("lawyerName",getUserName!!)
                    clientNamaeData.put("clientid",sendUuid)
                    clientNamaeData.put("lawyerid",getuuid)
                    clientNamaeData.put("lawyerImage",lawyerImage)

                    Log.e("A1","${getuuid}")
                    Log.e("A2","${getuuid}")



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
                    dataMap.put("lawyerImage",lawyerImage)
                    dataMap.put("clientImage",clientImage.toString())

                         dataMap.put("chatText",chatText!!)
                         dataMap.put("date",date!!)
                    dataMap.put("chatImage","")
                    dataMap.put("chatDoc","")







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
                    dataMapA.put("lawyerImage",lawyerImage)
                    dataMapA.put("clientImage",clientImage.toString())
                         dataMapA.put("chatText",chatText!!)
                         dataMapA.put("date",date!!)
                    dataMapA.put("chatImage","")
                    dataMapA.put("chatDoc","")


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
                                val lawyerImage = document.get("lawyerImage")
                                val clientImage = document.get("clientImage")
                                val chatImageUrlData = document.get("chatImage")

                                if (document.get("date") != null) {
                                    val timestamp = document.get("date") as Timestamp
                                    Log.e("date","${timestamp}")


                                    val milliseconds = timestamp.seconds * 1000 + timestamp.nanoseconds / 1000000
                                    val sdf = SimpleDateFormat("dd/MM/yyyy HH:mm")
                                    val netDate = Date(milliseconds)
                                    val date = sdf.format(netDate).toString()
                                    val chatDocUrlData = document.get("chatDoc")

                                    Log.e("da","${date}")


                                    val chat = Chat(user.toString(),text.toString(), lawyerImage.toString(),clientImage.toString(),date.toString(),chatImageUrlData.toString(),chatDocUrlData.toString())
                                    chats.add(chat)
                                    adapter.chats = chats
                                    adapter.constraintLayout = design.bigImageCL
                                    adapter.bigImageView = design.bigImageView
                                    adapter.saveButton = design.bttnImageSave
                                    adapter.closeBttn = design.closeButton
                                    adapter.context = requireContext()
                                    val resolver = activity!!.contentResolver
                                    adapter.resolver = resolver
                                    val downloadManager =
                                        activity!!.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
                                    adapter.manager = downloadManager



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
        design.sendDocsMessage.setOnClickListener {
            val intent = Intent()
                .setType("*/*")
                .setAction(Intent.ACTION_GET_CONTENT)

            startActivityForResult(Intent.createChooser(intent, "Select a file"), 111)


        }






        return design.root
    }



    fun addSendImage(chatImageUrls : String) {
        val bundle : ChatFragmentArgs by navArgs()
        val lawyerImagee = bundle.getIawyerImageUrl
        Log.e("Resim url chat","${chatImageUrls}")

            auth.currentUser?.let {


                val clientuser = it.email
                val clientUserName = it.displayName


                val senduser = it.email
                val sendUserName = it.displayName
                val sendUuid = it.uid
                val getUserName  = bundle.getUserName
                val getuuid = bundle.getUuid
                Log.e("uuda","${getuuid}")
                val lawyerImage = bundle.getIawyerImageUrl
                val clientImage = it.photoUrl
                val chatText = design.messageText.text.toString()
                val date = FieldValue.serverTimestamp()



                Log.e("Avukat resim url",bundle.getIawyerImageUrl)
                Log.e("Kullanıcı image url","${it.photoUrl}")





                Log.e("Müşteri adı","${sendUserName}")
                Log.e("Avukat adı","${getUserName}")
                design.nateTe.text = getUserName

                val clientNamaeData  = HashMap<String,Any>()
                clientNamaeData.put("clientName",sendUserName!!)
                clientNamaeData.put("lawyerName",getUserName!!)
                clientNamaeData.put("clientid",sendUuid)
                clientNamaeData.put("lawyerid",getuuid)
                clientNamaeData.put("lawyerImage",lawyerImage)

                Log.e("A1","${getuuid}")
                Log.e("A2","${getuuid}")



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
                dataMap.put("lawyerImage",lawyerImage)
                dataMap.put("clientImage",clientImage.toString())

                dataMap.put("chatText","")
                dataMap.put("date",date!!)
                dataMap.put("chatImage","${imageChatrUrl}")
                dataMap.put("chatDoc","")







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
                dataMapA.put("lawyerImage",lawyerImage)
                dataMapA.put("clientImage",clientImage.toString())
                dataMapA.put("chatText","")
                dataMapA.put("date",date!!)
                dataMapA.put("chatImage","${imageChatrUrl}")
                dataMapA.put("chatDoc","")


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





    fun addSendDobs(chatDocsUrls : String) {
        val bundle : ChatFragmentArgs by navArgs()
        val lawyerImagee = bundle.getIawyerImageUrl
        Log.e("Resim url chat","${chatDocsUrls}")

        auth.currentUser?.let {


            val clientuser = it.email
            val clientUserName = it.displayName


            val senduser = it.email
            val sendUserName = it.displayName
            val sendUuid = it.uid
            val getUserName  = bundle.getUserName
            val getuuid = bundle.getUuid
            Log.e("uuda","${getuuid}")
            val lawyerImage = bundle.getIawyerImageUrl
            val clientImage = it.photoUrl
            val chatText = design.messageText.text.toString()
            val date = FieldValue.serverTimestamp()



            Log.e("Avukat resim url",bundle.getIawyerImageUrl)
            Log.e("Kullanıcı image url","${it.photoUrl}")





            Log.e("Müşteri adı","${sendUserName}")
            Log.e("Avukat adı","${getUserName}")
            design.nateTe.text = getUserName

            val clientNamaeData  = HashMap<String,Any>()
            clientNamaeData.put("clientName",sendUserName!!)
            clientNamaeData.put("lawyerName",getUserName!!)
            clientNamaeData.put("clientid",sendUuid)
            clientNamaeData.put("lawyerid",getuuid)
            clientNamaeData.put("lawyerImage",lawyerImage)

            Log.e("A1","${getuuid}")
            Log.e("A2","${getuuid}")



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
            dataMap.put("lawyerImage",lawyerImage)
            dataMap.put("clientImage",clientImage.toString())

            dataMap.put("chatText","")
            dataMap.put("date",date!!)
            dataMap.put("chatImage","")
            dataMap.put("chatDoc","${chatDocsUrls}")







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
            dataMapA.put("lawyerImage",lawyerImage)
            dataMapA.put("clientImage",clientImage.toString())
            dataMapA.put("chatText","")
            dataMapA.put("date",date!!)
            dataMapA.put("chatImage","")
            dataMapA.put("chatDoc","${chatDocsUrls}")


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

    fun uploadStrongeDoc(fileUri : Uri) {
        val fileName = UUID.randomUUID().toString()+".pdf"
        var docUrl = ""
        val refStorage = FirebaseStorage.getInstance().reference.child("messageDocs/$fileName")
        refStorage.putFile(fileUri)
            .addOnSuccessListener(
                OnSuccessListener<UploadTask.TaskSnapshot> { taskSnapshot ->
                    taskSnapshot.storage.downloadUrl.addOnSuccessListener {
                        docUrl = it.toString()
                        imageUrlString(docUrl)
                        Log.e("Docs url","${docUrl}")
                        addSendDobs(docUrl)

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

        if (requestCode == 111 && resultCode == Activity.RESULT_OK) {

            val selectedFile = data?.data //The uri with the location of the file
            Log.e("Seçilen döküman","${selectedFile}")
            uploadStrongeDoc(selectedFile!!)
            design.imageAndDocConsL.visibility = View.GONE
        }


    }













}