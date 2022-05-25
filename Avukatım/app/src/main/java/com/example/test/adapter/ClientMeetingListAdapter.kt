package com.example.test.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.test.R
import com.example.test.databinding.ClientMeetigListCardDesignBinding
import com.example.test.databinding.LawyerMeetingListCardDesignBinding
import com.example.test.entity.MeetingDataClass
import com.example.test.viewModel.ClientMeetingListViewModel
import com.example.test.viewModel.LawyerMeetingListViewModel
import java.time.LocalDateTime

class ClientMeetingListAdapter(var mContext: Context, var meetingLawyerList:List<MeetingDataClass>, var viewModel: ClientMeetingListViewModel)
    : RecyclerView.Adapter<ClientMeetingListAdapter.CardDesignConservative>(){

    inner class CardDesignConservative(clientMeetigListCardDesignBinding: ClientMeetigListCardDesignBinding)
        :RecyclerView.ViewHolder(clientMeetigListCardDesignBinding.root){
        var clientMeetigListCardDesignBinding : ClientMeetigListCardDesignBinding
        init {
            this.clientMeetigListCardDesignBinding = clientMeetigListCardDesignBinding
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardDesignConservative {
        val layoutInflater = LayoutInflater.from(mContext)
        val design = ClientMeetigListCardDesignBinding.inflate(layoutInflater,parent,false)
        return  CardDesignConservative(design)
    }

    override fun onBindViewHolder(holder: CardDesignConservative, position: Int) {
        val meeting = meetingLawyerList.get(position)
        val cardDesign = holder.clientMeetigListCardDesignBinding
        cardDesign.meetingLawyerObject = meeting

        cardDesign.bttnJoinMeeting.setOnClickListener {

            Navigation.findNavController(it).navigate(R.id.toContactClient)





           /* val time = meetingLawyerList[position].time
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