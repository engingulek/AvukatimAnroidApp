package com.example.test.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.test.databinding.LawyerListCardDesignBinding
import com.example.test.entity.LawyerInfo
import com.example.test.viewModel.HomePageViewModel

class LawyerListAdapter(var mContext: Context, var lawyerList: List<LawyerInfo>, var viewModel: HomePageViewModel)
    : RecyclerView.Adapter<LawyerListAdapter.CardDesignConservative>(){

    inner class CardDesignConservative(lawyerListCardDesignBinding: LawyerListCardDesignBinding)
        : RecyclerView.ViewHolder(lawyerListCardDesignBinding.root) {
        var lawyerListCardDesignBinding : LawyerListCardDesignBinding
        init {
            this.lawyerListCardDesignBinding = lawyerListCardDesignBinding
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardDesignConservative {
        val layoutInflater = LayoutInflater.from(mContext)
        val desing = LawyerListCardDesignBinding.inflate(layoutInflater,parent,false)
        return CardDesignConservative(desing)


    }

    override fun onBindViewHolder(holder: CardDesignConservative, position: Int) {
        val lawyer = lawyerList.get(position)
        val cardDesign = holder.lawyerListCardDesignBinding

        cardDesign.lawyerObject = lawyer


        cardDesign.lawyerCardView.setOnClickListener {
          /*val pass = ClientHomePageFragmentDirections.toDetails(lawyer)
            Navigation.findNavController(it).navigate(pass)*/

        }

    }

    override fun getItemCount(): Int {
        return lawyerList.size

    }

}