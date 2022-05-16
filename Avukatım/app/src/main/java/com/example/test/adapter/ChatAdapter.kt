package com.example.test.adapter

import android.app.DownloadManager
import android.content.ContentResolver
import android.content.Context
import android.content.ContextWrapper
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.test.R
import com.example.test.databinding.RvvRowBinding
import com.example.test.entity.Chat
import com.google.firebase.auth.FirebaseAuth
import com.squareup.picasso.Picasso
import kotlinx.coroutines.delay
import android.provider.MediaStore

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import java.util.*
import android.graphics.drawable.Drawable
import android.os.Environment
import androidx.core.net.toUri
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.http.Url
import java.io.*
import java.net.URL
import java.nio.channels.Channel
import java.nio.channels.Channels
import androidx.core.content.ContextCompat.getSystemService
import java.lang.Exception


class ChatAdapter : RecyclerView.Adapter<ChatAdapter.ChatHolder>() {
    private val VIEW_TYPE_MESSAGE_SENT  = 1
    private val VIEW_TYPE_MESSAGE_SENT_RECEIVED = 2
    private var lawyerImage = ""
    private  var clientImage = ""
    var constraintLayout: ConstraintLayout? = null
    var saveButton : Button? = null
    var bigImageView : ImageView? = null
    var closeBttn : Button? = null
    var context : Context? = null
    var resolver : ContentResolver? = null
    var manager: DownloadManager? = null


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
        lawyerImage = chat.lawyerImage.toString()
        clientImage = chat.clientImage.toString()

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
            design.llrow.gravity = Gravity.LEFT
            design.chatTextView.setBackgroundResource(R.drawable.row_ballon)
            design.clRowL.setBackgroundResource(R.drawable.row_ballon)
            design.pdfCL.setBackgroundResource(R.drawable.row_ballon)
            design.rigtTime.visibility = View.GONE
            design.imageView9.visibility = View.GONE

            return ChatHolder(design)
        }
        else{
            val view = LayoutInflater.from(parent.context)
            val design = com.example.test.databinding.RvvRowBinding.inflate(view,parent,false)
            design.llrow.gravity = Gravity.RIGHT
            design.chatTextView.setBackgroundResource(R.drawable.row_ballon_right)
            design.clRowL.setBackgroundResource(R.drawable.row_ballon_right)
            design.pdfCL.setBackgroundResource(R.drawable.row_ballon_right)
            design.leftTime.visibility = View.GONE


            design.imageView8.visibility = View.GONE





            return ChatHolder(design)

        }



    }

    override fun onBindViewHolder(holder: ChatHolder, position: Int) {
        val cardDesing = holder.rvvRowBinding
        if (chats.get(position).text == "" && chats.get(position).chatDoc == "") {
            cardDesing.chatTextView.visibility = View.GONE
            Picasso.get().load(chats.get(position).chatImage).into(cardDesing.chatImageView)
            cardDesing.pdfCL.visibility = View.GONE


            cardDesing.chatImageView.setOnClickListener {
                constraintLayout?.visibility = View.VISIBLE
               Picasso.get().load(chats.get(position).chatImage).into(bigImageView)


            }


            saveButton?.setOnClickListener {
                Log.e("Kaydetmeişleöş","da")


                val newUUID = UUID.randomUUID()
                val dr = (bigImageView as ImageView).drawable
                val uri = saveImage(dr,"${newUUID}")

            }




            closeBttn?.setOnClickListener {
                constraintLayout?.visibility = View.GONE
            }

        }else if(chats.get(position).text == "" && chats.get(position).chatImage == ""){

            cardDesing.pdfCL.visibility = View.VISIBLE
            cardDesing.clRowL.visibility = View.GONE

            cardDesing.chatPdfView.setImageResource(R.drawable.pdf_image)


        }

        else{
            cardDesing.pdfCL.visibility = View.GONE
            cardDesing.chatPdfView.visibility = View.GONE
            cardDesing.chatTextView.text = "${chats.get(position).text}"

        }

        Picasso.get().load(chats.get(position).clientImage).into(cardDesing.imageView9)
        Picasso.get().load(chats.get(position).lawyerImage).into(cardDesing.imageView8)
        cardDesing.leftTime.text = "${chats.get(position).date}"
        cardDesing.rigtTime.text = "${chats.get(position).date}"



        cardDesing.pdfDowloandBttn.setOnClickListener {

            Log.e("Pdf da","${chats.get(position).chatDoc.toString()}")

            try {
                val url = "${chats.get(position).chatDoc.toString()}"
                val imageLink = Uri.parse(url)
                val request = DownloadManager.Request(imageLink)
                request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE or DownloadManager.Request.NETWORK_WIFI)
                    .setMimeType("files/pdf")
                    .setAllowedOverRoaming(false)
                    .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                    .setTitle("dosya")
                    .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOCUMENTS,"dosya.pdf")

                manager!!.enqueue(request)
            }catch (e:Exception){
                Log.e("da","dadadsa")
            }









        }

    }








    private fun saveImage(drawable:Drawable, title:String):Uri{
        // Get the image from drawable resource as drawable object


        // Get the bitmap from drawable object
        val bitmap = (drawable as BitmapDrawable).bitmap

        // Save image to gallery
        val savedImageURL = MediaStore.Images.Media.insertImage(
            resolver,
            bitmap,
            title,
            "Image of $title"
        )

   

        // Parse the gallery image url to uri
        constraintLayout?.visibility = View.GONE
        return Uri.parse(savedImageURL)
    }






    override fun getItemCount(): Int {
        return chats.size

    }
}