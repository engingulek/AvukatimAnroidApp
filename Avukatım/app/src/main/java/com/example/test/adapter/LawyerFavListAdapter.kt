package com.example.test.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.test.databinding.ClientFavListCardDesignBinding
import com.example.test.entity.FavLawyerResult
import com.example.test.entity.Lawyer
import com.example.test.entity.LawyerInfo
import com.example.test.retrofit.APIUtils
import com.example.test.retrofit.MyLawyerDaoInterface
import com.example.test.viewModel.FavListPageViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LawyerFavListAdapter(var mContext:Context,var lawyerList: List<LawyerInfo>,var viewModel:FavListPageViewModel)
    :RecyclerView.Adapter<LawyerFavListAdapter.CardDesignConservative>(){
    private lateinit var d : MyLawyerDaoInterface
        inner class CardDesignConservative(clientFavListCardDesignBinding: ClientFavListCardDesignBinding)
            :RecyclerView.ViewHolder(clientFavListCardDesignBinding.root) {
                var clientFavListCardDesignBinding : ClientFavListCardDesignBinding
                init {
                    this.clientFavListCardDesignBinding = clientFavListCardDesignBinding
                }


        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardDesignConservative {
        val layoutInflater = LayoutInflater.from(mContext)
        val design = ClientFavListCardDesignBinding.inflate(layoutInflater,parent,false)
        return CardDesignConservative(design)
    }

    override fun onBindViewHolder(holder: CardDesignConservative, position: Int) {
        val favlawyer = lawyerList.get(position)
        Log.e("adapterdada","${favlawyer.lawyerNameSurname}")
        val cardDesign = holder.clientFavListCardDesignBinding
        cardDesign.lawyerObject = favlawyer
        cardDesign.deleteView.setOnClickListener {

            val aa = arrayOf("")
            // val a  = Lawyer(info.id.toString(),"","","" ,"",aa,"","","",aa)
            val lawyer = Lawyer(favlawyer.id.toString(),"",
                "",
                "" ,
                "",
                aa,"",
                "",
                aa,
                "hh",
                "",
                ""
            )
            d = APIUtils.getMyLawyerDaoInterface()

           d.favdelete(lawyer).enqueue(object  : Callback<FavLawyerResult>{
               override fun onResponse(
                   call: Call<FavLawyerResult>?,
                   response: Response<FavLawyerResult>?
               ) {

               }

               override fun onFailure(call: retrofit2.Call<FavLawyerResult>?, t: Throwable?) {

               }

           })


        }
    }

    override fun getItemCount(): Int {
        return lawyerList.size
    }
}