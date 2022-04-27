package com.example.test.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.test.databinding.LawyerMeetingListCardDesignBinding
import com.example.test.entity.MeetingDataClass
import com.example.test.viewModel.LawyerMeetingListViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

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

    }


    override fun getItemCount(): Int {
        return meetingLawyerList.size
    }







    }