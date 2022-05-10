package com.example.test

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.os.PersistableBundle
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
import java.time.LocalDateTime
import java.util.logging.Logger
import kotlin.concurrent.timer

class MainActivity : AppCompatActivity() {
    private lateinit var myLawyerDao : MyLawyerDaoInterface
    var dataHours = 0
    var dataMinute = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)



       /* val current = LocalDateTime.now()
        val hours = current.hour
        var m = 0















        val timer = object : CountDownTimer(10000.toLong(),1000 ) {
            override fun onTick(p0: Long) {




            }

            override fun onFinish() {
              getData()
                m = (dataHours-hours)*3600*1000
                Log.e("Sayac","${m.toLong()}")
                val timerNoti = object : CountDownTimer(m.toLong(),1000 ) {
                    override fun onTick(p0: Long) {
                        Log.e("Sayac 2" ,"${p0/1000}")

                    }

                    override fun onFinish() {
                        notification()
                    }

                }
                timerNoti.start()

            }

        }

        timer.start()

        // gerçek timer








    }

    fun getData() {
        myLawyerDao = APIUtils.getMyLawyerDaoInterface()
        myLawyerDao.getAllMeetingList().enqueue(object : Callback<MeetingDataClassResult> {
            override fun onResponse(
                call: Call<MeetingDataClassResult>,
                response: Response<MeetingDataClassResult>
            ) {
                val list = response.body().meetingsList
                if(list.size == 0) {

                }else if (list.isEmpty()) {

                }else {
                    val time = list[0].time
                    val getTimeHA = "${time[0]}"
                    val getTimeHB = "${time[1]}"


                    var getTimeHAA = 0
                    if (getTimeHA == "1" || getTimeHA == "0"){ // 10 katı ve 0 olduğunda
                        getTimeHAA = (getTimeHA.toInt()*10)
                    }else{
                        getTimeHAA = (getTimeHA.toInt()*20)


                    }


                    dataHours = getTimeHAA + getTimeHB.toInt()


                }

            }

            override fun onFailure(call: Call<MeetingDataClassResult>, t: Throwable) {

            }

        })


    }




    fun notification() {
        val builder : NotificationCompat.Builder

        val bildirimYoneticisi = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val intent = Intent(this,MainActivity::class.java)

        val gidilecekIntent = PendingIntent.getActivity(this,1,intent,PendingIntent.FLAG_UPDATE_CURRENT)

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){

            val kanalId = "kanalId"
            val kanalAd = "kanalAd"
            val kanalTanıtım = "kanalTanıtım"
            val kanalOnceligi = NotificationManager.IMPORTANCE_HIGH

            var kanal : NotificationChannel? = bildirimYoneticisi.getNotificationChannel(kanalId)

            if(kanal == null){
                kanal = NotificationChannel(kanalId,kanalAd,kanalOnceligi)
                kanal.description = kanalTanıtım
                bildirimYoneticisi.createNotificationChannel(kanal)
            }

            builder = NotificationCompat.Builder(this,kanalId)

            builder.setContentTitle("Avukatım")
                .setContentText("1 saat sonra görüşme randevunuz bulunmaktadır")
                .setSmallIcon(R.drawable.delete)
                .setContentIntent(gidilecekIntent)
                .setAutoCancel(true)

        }else{
            builder = NotificationCompat.Builder(this)

            builder.setContentTitle("Avukatım")
                .setContentText("1 saat sonra görüşme randevunuz bulunmaktadır")
                .setSmallIcon(R.drawable.delete)
                .setContentIntent(gidilecekIntent)
                .setAutoCancel(true)
                .priority = Notification.PRIORITY_HIGH

        }


        bildirimYoneticisi.notify(1,builder.build())
    }*/

   

}
}



