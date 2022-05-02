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
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.example.test.R
import com.example.test.adapter.LawyerCommentAdapter
import com.example.test.databinding.FragmentLawyerDetailsBinding
import com.example.test.entity.Meeting
import com.example.test.entity.MeetingDataClass
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




        design.sendMessageChat.setOnClickListener {
            val pass = LawyerDetailsFragmentDirections.toChat(getLawyerDetails.lawyerNameSurname,getLawyerDetails.authUserId)
            Navigation.findNavController(it).navigate(pass)
        }

        lawyerDetailViewModel.lawyerCommentList.observe(viewLifecycleOwner,{
            lawyerCommentAdapter = LawyerCommentAdapter(requireContext(),it,lawyerDetailViewModel)
            design.adapter = lawyerCommentAdapter
            if (it.size != 0) {
                lawyerCommentAdapter.notifyDataSetChanged()
            }
            Log.e("Tarih",it[0].commentDate)


        })
        val cityItems = ArrayList<String>()
        design.bttnMeetingOne.setOnClickListener {
            design.randevu.visibility = View.VISIBLE







            design.chipOneC.setBackgroundResource(R.drawable.test)
            design.chipTwoC.setBackgroundResource(R.drawable.test)
            design.chipThreeC.setBackgroundResource(R.drawable.test)
            design.chipFourC.setBackgroundResource(R.drawable.test)
            lawyerMeetingFreeTime(getLawyerDetails.authUserId)

        }


        design.imageViewClose.setOnClickListener {
            design.randevu.visibility = View.GONE
        }







        design.bttnSendMeeting.setOnClickListener {
          val selectDate =  design.textViewMeetDate.text.toString()
            val selectedTime = time

            if ( selectedTime == "") {
                Toast.makeText(requireContext(),"Tarih veya saat seçmediniz",Toast.LENGTH_SHORT).show()
            }
            else {
                val meetInfo = Meeting(
                    "${auth.currentUser?.uid}",
                    getLawyerDetails.id,
                    selectDate,
                    selectedTime,
                    "${getLawyerDetails.authUserId}",
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


                    }
                }
                timer.start()






            }

        }


        return design.root
    }

    fun lawyerMeetingFreeTime(lawyerId:String) {
        lawyerDetailViewModel.meetingList.observe(viewLifecycleOwner,{
            auth =  Firebase.auth
            val ita = it.filter { it.lawyerAuthUserId == lawyerId }
            stateMeetin(ita)

        })

    }


    fun stateMeetin(list:List<MeetingDataClass>){
        var stateChipOne = false
        var stateChipTwo = false
        var stateChipThree = false
        var stateChipFour = false
        var stateChipOneNotAvaible = true
        var stateChipTwoNotAvaible = false
        var stateChipThreeNotAvaible = false
        var stateChipFourNotAvaible = false

        for (a in list) {
            if (a.time == design.chipTwoT.text.toString()) {
                design.chipTwoC.setBackgroundResource(R.drawable.test_gray)
                stateChipTwoNotAvaible = true }

            /*if ("16:00" == design.chipOneT.text.toString()) {
               // design.chipOneC.setBackgroundResource(R.drawable.test_gray)
                stateChipOneNotAvaible = true }*/

            if (a.time == design.chipThreeT.text.toString()) {
                design.chipThreeC.setBackgroundResource(R.drawable.test_gray)
                stateChipThreeNotAvaible = true }

            if (a.time == design.chipFourT.text.toString()) {
                design.chipFourC.setBackgroundResource(R.drawable.test_gray)
                stateChipFourNotAvaible = true }
            else {
                Log.e("aynı değil","da")
            }
        }







        design.chipOne.setOnClickListener{

            if (stateChipFour == true || stateChipTwo == true || stateChipThree == true) {

            }

            if (stateChipOneNotAvaible == true ) {
                 Toast.makeText(requireContext(),"Bu saat uygun değil",Toast.LENGTH_SHORT).show()
            }
            else {

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

                } }
        }



        design.chipTwo.setOnClickListener{

            if (stateChipOne == true || stateChipThree == true || stateChipFour == true) {

            }

            if (stateChipTwoNotAvaible == true ) {
                Toast.makeText(requireContext(),"Bu saat uygun değil",Toast.LENGTH_SHORT).show()
            }


            else {

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





        }

        design.chipThree.setOnClickListener{

            if (stateChipOne == true || stateChipTwo == true || stateChipFour == true) {

            }

            if (stateChipThreeNotAvaible == true ){
                Toast.makeText(requireContext(),"Bu saat uygun değil",Toast.LENGTH_SHORT).show()
            }
            else {

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



        }

        design.chipFour.setOnClickListener{

            if (stateChipOne == true || stateChipTwo == true || stateChipThree == true) {

            }
            if (stateChipFourNotAvaible == true) {
                Toast.makeText(requireContext(),"Bu saat uygun değil",Toast.LENGTH_SHORT).show()
            }
            else {

                if (stateChipOne == true || stateChipTwo == true || stateChipThree == true) {

                }
                else {

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



        design.chipOneC.setBackgroundResource(R.drawable.test_gray)

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val tempViewModel: LawyerDetailsViewModel by viewModels()
        lawyerDetailViewModel = tempViewModel
    }


}