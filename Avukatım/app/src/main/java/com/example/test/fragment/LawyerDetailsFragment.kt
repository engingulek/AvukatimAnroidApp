package com.example.test.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.test.R
import com.example.test.adapter.LawyerCommentAdapter
import com.example.test.databinding.FragmentLawyerDetailsBinding
import com.example.test.viewModel.LawyerDetailsViewModel
import com.squareup.picasso.Picasso

class LawyerDetailsFragment : Fragment() {
    private lateinit var  design : FragmentLawyerDetailsBinding
    private lateinit var lawyerDetailViewModel : LawyerDetailsViewModel
    private lateinit var lawyerCommentAdapter: LawyerCommentAdapter
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        design = DataBindingUtil.inflate(inflater,
            R.layout.fragment_lawyer_details, container, false)
        design.fragmentLawyerDetails = this
       val bundle : LawyerDetailsFragmentArgs by navArgs()
        val getLawyerDetails = bundle.lawyer
        val loc = getLawyerDetails.lawyerLocationCity+"/"+getLawyerDetails.lawyerLocationCounty
        design.loc = loc
        Log.e("Avukat details",getLawyerDetails.lawyerNameSurname)
        design.lawyerObject = getLawyerDetails
        Picasso.get().load(getLawyerDetails.lawyerImageUrl).into(design.imageView3)

        lawyerDetailViewModel.lawyerCommentList.observe(viewLifecycleOwner,{
            lawyerCommentAdapter = LawyerCommentAdapter(requireContext(),it,lawyerDetailViewModel)
            design.adapter = lawyerCommentAdapter
            Log.e("Tarih",it[0].commentDate)
        })



        return design.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val tempViewModel: LawyerDetailsViewModel by viewModels()
        lawyerDetailViewModel = tempViewModel
    }


}