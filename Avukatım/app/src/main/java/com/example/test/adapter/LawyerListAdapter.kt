package com.example.test.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.test.R
import com.example.test.databinding.LawyerListCardDesignBinding
import com.example.test.entity.FavoriteLawyer
import com.example.test.entity.Lawyer
import com.example.test.entity.LawyerInfo
import com.example.test.fragment.ClientTabLayoutFragmentDirections
import com.example.test.viewModel.HomePageViewModel
import com.squareup.picasso.Picasso

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
        if (lawyer.lawyerImageUrl == "") {

        }
        else {
            Picasso.get().load(lawyer.lawyerImageUrl).into(cardDesign.imageViewLawyer)
        }



        cardDesign.lawyerCardView.setOnClickListener {
            val pass = ClientTabLayoutFragmentDirections.toDetails(lawyer)
            Navigation.findNavController(it).navigate(pass)
            /*val pass = ClientHomePageFragmentDirections.toDetails(lawyer)
              Navigation.findNavController(it).navigate(pass)*/

        }
        val locCordinate = arrayOf("","")
        cardDesign.imageViewLawyer.setOnClickListener {
            println("T??kland??")
            val tets = Lawyer(lawyer.authUserId,lawyer.lawyerImageUrl,lawyer.lawyerNameSurname,lawyer.lawyerGender,lawyer.lawyerAge,lawyer.lawyerProfession,
                lawyer.lawyerLocationCity,lawyer.lawyerLocationCounty, locCordinate,"jj",lawyer.lawyerDescription,lawyer.lawyerDescription)
            viewModel.addLayertoFav(tets)

        }






    }

    override fun getItemCount(): Int {
        return lawyerList.size

    }

}