package com.example.test.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.recyclerview.widget.RecyclerView
import com.example.test.databinding.LawyerCommentCardDesignBinding
import com.example.test.entity.LawyerComment
import com.example.test.viewModel.LawyerDetailsViewModel

class LawyerCommentAdapter(var mContetx: Context, var lawyerCommnetList: List<LawyerComment>, var viewModel: LawyerDetailsViewModel)
    : RecyclerView.Adapter<LawyerCommentAdapter.CardDesignConservative>(){

    inner class CardDesignConservative(lawyerCommentCardDesignBinding: LawyerCommentCardDesignBinding)
        : RecyclerView.ViewHolder(lawyerCommentCardDesignBinding.root){
        var lawyerCommentCardDesignBinding: LawyerCommentCardDesignBinding
        init {
            this.lawyerCommentCardDesignBinding = lawyerCommentCardDesignBinding
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardDesignConservative {
        var layoutInflater = LayoutInflater.from(mContetx)
        val design = LawyerCommentCardDesignBinding.inflate(layoutInflater,parent,false)
        return CardDesignConservative(design)
    }

    override fun onBindViewHolder(holder: CardDesignConservative, position: Int) {
        val lawyerCommnet = lawyerCommnetList.get(position)
        val cardDesign = holder.lawyerCommentCardDesignBinding
        cardDesign.lawyerCommnetObjet = lawyerCommnet
        cardDesign.openThanksComment.setOnClickListener{
            if (cardDesign.thanksCommnetConstraintLayout.isGone == false) {
                cardDesign.thanksCommnetConstraintLayout.isGone = true
            }
            else {
                cardDesign.thanksCommnetConstraintLayout.isGone = false
            }

        }
        Log.e("Adapter test",lawyerCommnet.commentDate)
        if (lawyerCommnet.lawyerId == "6224ca699b136beca23f76b7") {

        }

    }

    override fun getItemCount(): Int {

        return lawyerCommnetList.size

    }


}