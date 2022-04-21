package com.example.test.fragment

import android.opengl.Visibility
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
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

        design.bttnMeetingOne.setOnClickListener {
            design.randevu.visibility = View.VISIBLE

        }


        design.imageViewClose.setOnClickListener {
            design.randevu.visibility = View.GONE
        }

        stateMeetin()

        return design.root
    }


    fun stateMeetin(){
        var stateChipOne = false
        var stateChipTwo = false
        var stateChipThree = false
        var stateChipFour = false
        var stateChipOnee = true


            // başkası tarafından randevu alınmış
            design.chipOneC.setBackgroundResource(R.drawable.test_red)


        design.chipOne.setOnClickListener{
            if (stateChipOnee == true) {
                Toast.makeText(requireContext(),"Başkası Tarından alınmıştır",Toast.LENGTH_SHORT).show()

            }

            else {

                if(stateChipOne == false) {
                    design.chipOneC.setBackgroundResource(R.drawable.test_red)
                    stateChipOne = true

                }
                else {
                    design.chipOneC.setBackgroundResource(R.drawable.test)
                    stateChipOne = false

                }

            }



        }

        design.chipTwo.setOnClickListener{
            if(stateChipTwo == false) {
                design.chipTwoC.setBackgroundResource(R.drawable.test_red)
                stateChipTwo = true

            }
            else {
                design.chipTwoC.setBackgroundResource(R.drawable.test)
                stateChipTwo = false

            }


        }

        design.chipThree.setOnClickListener{
            if(stateChipThree == false) {
                design.chipThreeC.setBackgroundResource(R.drawable.test_red)
                stateChipThree = true

            }
            else {
                design.chipThreeC.setBackgroundResource(R.drawable.test)
                stateChipThree = false

            }


        }

        design.chipFour.setOnClickListener{
            if(stateChipFour == false) {
                design.chipFourC.setBackgroundResource(R.drawable.test_red)
                stateChipFour = true

            }
            else {
                design.chipFourC.setBackgroundResource(R.drawable.test)
                stateChipFour = false

            }


        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val tempViewModel: LawyerDetailsViewModel by viewModels()
        lawyerDetailViewModel = tempViewModel
    }


}