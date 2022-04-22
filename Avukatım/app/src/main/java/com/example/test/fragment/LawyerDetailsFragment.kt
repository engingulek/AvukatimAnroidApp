package com.example.test.fragment

import android.opengl.Visibility
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.test.R
import com.example.test.adapter.LawyerCommentAdapter
import com.example.test.databinding.FragmentLawyerDetailsBinding
import com.example.test.entity.Meeting
import com.example.test.viewModel.LawyerDetailsViewModel
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.datepicker.MaterialDatePicker.thisMonthInUtcMilliseconds
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.squareup.picasso.Picasso
import java.time.LocalDateTime
import java.util.*

class LawyerDetailsFragment : Fragment() {
    private lateinit var  design : FragmentLawyerDetailsBinding
    private lateinit var lawyerDetailViewModel : LawyerDetailsViewModel
    private lateinit var lawyerCommentAdapter: LawyerCommentAdapter
    private lateinit var auth: FirebaseAuth
    var time =  ""
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        design = DataBindingUtil.inflate(inflater,
            R.layout.fragment_lawyer_details, container, false)
        design.fragmentLawyerDetails = this
        auth = Firebase.auth
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
        val cityItems = ArrayList<String>()
        design.bttnMeetingOne.setOnClickListener {
            design.randevu.visibility = View.VISIBLE

            val current = LocalDateTime.now()
            val day = current.dayOfMonth
            val mount = current.monthValue
            val year = current.year

            cityItems.add("${day}/0${mount}/${year}")
            cityItems.add("${day+1}/0${mount}/${year}")
            cityItems.add("${day+2}/0${mount}/${year}")
            cityItems.add("${day+3}/0${mount}/${year}")
            val cityArrayAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_item, cityItems)
            design.autoCompleteTextViewCity.setAdapter(cityArrayAdapter)


            design.chipOneC.setBackgroundResource(R.drawable.test)
            design.chipTwoC.setBackgroundResource(R.drawable.test)
            design.chipThreeC.setBackgroundResource(R.drawable.test)
            design.chipFourC.setBackgroundResource(R.drawable.test)

        }


        design.imageViewClose.setOnClickListener {
            design.randevu.visibility = View.GONE
        }



        stateMeetin()



        design.bttnSendMeeting.setOnClickListener {
          val selectDate =  design.autoCompleteTextViewCity.text.toString()
            val selectedTime = time

            if (selectDate == "" || selectedTime == "") {
                Toast.makeText(requireContext(),"Tarih veya saat seçmediniz",Toast.LENGTH_SHORT).show()
            }
            else {






                Log.e("Seçilen ilan id","${getLawyerDetails.id}")
                Log.e("Seçen kişinin kullanıcı id ","${auth.currentUser?.uid}")
                Log.e("Seçilen gün ve tarih ","${selectDate} / ${selectedTime}")
                Log.e("Seçilen ilan avukat id","${getLawyerDetails.authUserId}")


                val meetInfo = Meeting(
                    "${auth.currentUser?.uid}",
                    getLawyerDetails.id,
                    selectDate,
                    selectedTime,
                    "test lawyerAuthUserId",
                    getLawyerDetails.lawyerNameSurname,
                    getLawyerDetails.lawyerImageUrl,
                    "${auth.currentUser?.displayName}",
                    "müvekkil profil resmi")


                lawyerDetailViewModel.addMeetingInfo(meetInfo)





                // veri tabanına yazma işlemi gerçekleştirilecek
                // kullanıcı id -> benim id -> firebaseden çekilecek
                // ilan id  ->  getLawyerDetails.id
                //tarih
                // saat

                val timer = object: CountDownTimer(1000, 1000) {
                    override fun onTick(millisUntilFinished: Long) {
                        
                    }

                    override fun onFinish() {
                        design.randevu.visibility = View.GONE
                        Toast.makeText(requireContext(),"Randevunuz Alınmıştır",Toast.LENGTH_SHORT).show()
                        design.autoCompleteTextViewCity.setText("")

                    }
                }
                timer.start()






            }

        }


        return design.root
    }


    fun stateMeetin(){
        var stateChipOne = false
        var stateChipTwo = false
        var stateChipThree = false
        var stateChipFour = false
        var stateChipOnee = false


            // başkası tarafından randevu alınmış
           // design.chipOneC.setBackgroundResource(R.drawable.test_red)


        design.chipOne.setOnClickListener{

            if (stateChipFour == true || stateChipTwo == true || stateChipThree == true) {

            }
            else {
                if(stateChipOne == false) {
                    design.chipOneC.setBackgroundResource(R.drawable.test_red)
                    time = design.chipOneT.text.toString()
                    stateChipOne = true

                }
                else {
                    design.chipOneC.setBackgroundResource(R.drawable.test)
                    stateChipOne = false
                }
            }
        }

        design.chipTwo.setOnClickListener{

            if (stateChipOne == true || stateChipThree == true || stateChipFour == true) {

            }
            else {
                if(stateChipTwo == false) {
                    design.chipTwoC.setBackgroundResource(R.drawable.test_red)
                    time = design.chipTwoT.text.toString()
                    stateChipTwo = true

                }
                else {
                    design.chipTwoC.setBackgroundResource(R.drawable.test)
                    stateChipTwo = false

                }

            }





        }

        design.chipThree.setOnClickListener{

            if (stateChipOne == true || stateChipTwo == true || stateChipFour == true) {

            }
            else {
                if(stateChipThree == false) {
                    design.chipThreeC.setBackgroundResource(R.drawable.test_red)
                    time = design.chipThreeT.text.toString()
                    stateChipThree = true

                }
                else {
                    design.chipThreeC.setBackgroundResource(R.drawable.test)
                    stateChipThree = false

                }

            }



        }

        design.chipFour.setOnClickListener{

            if (stateChipOne == true || stateChipTwo == true || stateChipThree == true) {

            }else {

                if(stateChipFour == false) {
                    design.chipFourC.setBackgroundResource(R.drawable.test_red)
                    time = design.chipFourT.text.toString()
                    stateChipFour = true

                }
                else {
                    design.chipFourC.setBackgroundResource(R.drawable.test)
                    stateChipFour = false

                }
            }


        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val tempViewModel: LawyerDetailsViewModel by viewModels()
        lawyerDetailViewModel = tempViewModel
    }


}