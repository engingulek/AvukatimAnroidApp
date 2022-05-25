package com.example.test.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.test.R
import com.example.test.databinding.LawyerMeetingListCardDesignBinding
import com.example.test.entity.MeetingDataClass
import com.example.test.viewModel.LawyerMeetingListViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import java.time.LocalDateTime

class LawyerMeetingListAdapter (var mContext:Context, var meetingLawyerList:List<MeetingDataClass>,var viewModel: LawyerMeetingListViewModel)
    : RecyclerView.Adapter<LawyerMeetingListAdapter.CardDesignConservative>(){

        inner class CardDesignConservative(lawyerMeetingListCardDesignBinding: LawyerMeetingListCardDesignBinding)
            :RecyclerView.ViewHolder(lawyerMeetingListCardDesignBinding.root){
            var lawyerMeetingListCardDesignBinding : LawyerMeetingListCardDesignBinding
            init {
                this.lawyerMeetingListCardDesignBinding = lawyerMeetingListCardDesignBinding
            }
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardDesignConservative {
        val layoutInflater = LayoutInflater.from(mContext)
        val design = LawyerMeetingListCardDesignBinding.inflate(layoutInflater,parent,false)
        return  CardDesignConservative(design)
    }

    override fun onBindViewHolder(holder: CardDesignConservative, position: Int) {


        val meeting = meetingLawyerList.get(position)
        val cardDesign = holder.lawyerMeetingListCardDesignBinding



        cardDesign.meetingLawyerObject = meeting

        cardDesign.bttnJoinMeeting.setOnClickListener {

            Navigation.findNavController(it).navigate(R.id.toContactLawyer)
            /*val time = meetingLawyerList[position].time
            val getTimeHA = "${time[0]}"
            val getTimeHB = "${time[1]}"


            var getTimeHAA = 0
            if (getTimeHA == "1" || getTimeHA == "0"){ // 10 katı ve 0 olduğunda
                getTimeHAA = (getTimeHA.toInt()*10)
            }else{
                getTimeHAA = (getTimeHA.toInt()*20)


            }


            var dataHours = 0
            dataHours = getTimeHAA + getTimeHB.toInt()

            Log.e("Toplantı Saati","${dataHours}")
            val current = LocalDateTime.now()
            val hours = current.hour
            Log.e("Local saat","$hours")
            if (dataHours!=hours){
                println("Saatiniz gelmediği için toplantıya katılamazsınız")
            }

            if (dataHours==hours){
                println("Toplantıya katıla bilirsiniz")
            }*/




        }

    }


    override fun getItemCount(): Int {
        return meetingLawyerList.size
    }







    }