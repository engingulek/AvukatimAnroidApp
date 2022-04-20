package com.example.test.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import com.example.test.R
import com.example.test.adapter.LawyerFavListAdapter
import com.example.test.databinding.FragmentClientFavBinding
import com.example.test.viewModel.FavListPageViewModel
import com.example.test.viewModel.HomePageViewModel


class ClientFavFragment : Fragment() {
private lateinit var design : FragmentClientFavBinding
private lateinit var favViewModel : FavListPageViewModel
private lateinit var favListAdapter : LawyerFavListAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        design = DataBindingUtil.inflate(inflater,R.layout.fragment_client_fav, container, false)
        design.fragmnet = this

        favViewModel.favlawyerInfoList.observe(viewLifecycleOwner,{
            favListAdapter = LawyerFavListAdapter(requireContext(),it,favViewModel)
            for(a in it) {
                Log.e("da","${a.lawyerNameSurname}")
            }
            design.lawyerFavListAdapter = favListAdapter
        })





        return design.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val tempViewModel: FavListPageViewModel by viewModels()
        favViewModel = tempViewModel
    }

}