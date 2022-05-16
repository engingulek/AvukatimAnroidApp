package com.example.test

import android.app.*
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.*
import androidx.appcompat.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.fragment.app.viewModels
import androidx.core.app.NotificationCompat
import com.example.test.adapter.LawyerMeetingListAdapter
import com.example.test.entity.LawyerInfoResult
import com.example.test.entity.MeetingDataClassResult
import com.example.test.retrofit.APIUtils
import com.example.test.retrofit.MyLawyerDaoInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception
import java.time.LocalDateTime
import java.util.logging.Logger
import kotlin.concurrent.timer

class MainActivity : AppCompatActivity() {
    private lateinit var myLawyerDao : MyLawyerDaoInterface
    var dataHours = 0
    var dataMinute = 0
    private  var permission = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)




}




}



