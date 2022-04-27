package com.example.test.fragment

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.example.test.R
import com.example.test.databinding.FragmentCreateLawyerAdvertBinding
import com.example.test.entity.Lawyer
import com.example.test.retrofit.APIUtils
import com.example.test.retrofit.MyLawyerDaoInterface
import com.example.test.viewModel.CreateLawyerAdvertViewModel
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.UploadTask
import retrofit2.Call
import java.time.LocalDateTime
import java.util.*
import kotlin.collections.ArrayList

class CreateLawyerAdvertFragment : Fragment() {
    private lateinit var desing: FragmentCreateLawyerAdvertBinding
    private lateinit var createLawyerAdvertViewModel: CreateLawyerAdvertViewModel
    private lateinit var  testInterface : MyLawyerDaoInterface
    private lateinit var auth: FirebaseAuth
    private lateinit var call : Call<Lawyer>


    private var imageUri: Uri? = null
    private var imageLawyerUrl:String? = ""


    companion object {
        val IMAGE_REQUEST_CODE = 100
    }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        auth = Firebase.auth
        desing = DataBindingUtil.inflate(inflater,R.layout.fragment_create_lawyer_advert, container, false)
        var selectionGender = "Kadın"

        testInterface = APIUtils.getMyLawyerDaoInterface()
        desing.bttnChangeImage.setOnClickListener {

            pickImageGallery()
        }
        desing.radioButtonWoman.isChecked = true

        desing.radioButtonWoman.setOnCheckedChangeListener { compoundButton, b ->
            if (b) {
                selectionGender = "Kadın"
                Log.e("Seçilen",selectionGender)
            }else{
                selectionGender = "Erkek"
                Log.e("Seçilen",selectionGender)
            }
        }
        val timeOne =
            MaterialTimePicker.Builder()
                .setTimeFormat(TimeFormat.CLOCK_24H)
                .setHour(14)
                .setMinute(0)
                .setInputMode(MaterialTimePicker.INPUT_MODE_KEYBOARD)
                .setTitleText("İlk Randevu Saatini Seçiniz")
                .build()

        val timeTwo =
            MaterialTimePicker.Builder()
                .setTimeFormat(TimeFormat.CLOCK_24H)
                .setHour(14)
                .setMinute(0)
                .setInputMode(MaterialTimePicker.INPUT_MODE_KEYBOARD)
                .setTitleText("İkinci Randevu Saatini Seçiniz")
                .build()

        val timeThree =
            MaterialTimePicker.Builder()
                .setTimeFormat(TimeFormat.CLOCK_24H)
                .setHour(14)
                .setMinute(0)
                .setInputMode(MaterialTimePicker.INPUT_MODE_KEYBOARD)
                .setTitleText("Üçüncü Randevu Saatini Seçiniz")
                .build()


        val timeFour =
            MaterialTimePicker.Builder()
                .setTimeFormat(TimeFormat.CLOCK_24H)
                .setHour(14)
                .setMinute(0)
                .setInputMode(MaterialTimePicker.INPUT_MODE_KEYBOARD)
                .setTitleText("Dördüncü Randevu Saatini Seçiniz")
                .build()




        desing.timeOneTextView.setOnClickListener {


            fragmentManager?.let { timeOne.show(it,"tag") }
        }


        desing.timeTwoTextView.setOnClickListener {

            fragmentManager?.let { timeTwo.show(it,"tag") }

        }
        desing.timeThreeTextView.setOnClickListener {
            fragmentManager?.let { timeThree.show(it,"tag") }

        }
        desing.timeFourTextView.setOnClickListener {
            fragmentManager?.let { timeFour.show(it,"tag") }

        }

        timeOne.addOnPositiveButtonClickListener {
            desing.timeOneTextView.text = "${timeOne.hour}:${timeOne.minute}0"

        }

        timeTwo.addOnPositiveButtonClickListener {

                desing.timeTwoTextView.text = "${timeTwo.hour}:${timeTwo.minute}0"

        }

        timeThree.addOnPositiveButtonClickListener {

            desing.timeThreeTextView.text = "${timeThree.hour}:${timeThree.minute}0"

        }

        timeFour.addOnPositiveButtonClickListener {

            desing.timeFourTextView.text = "${timeFour.hour}:${timeFour.minute}0"

        }

//Üniversite
          val items = listOf("Ankara Üniversitesi", "İstanbul Üniversitesi", "Hacettep Üniversitesi")
           val arrayAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_item, items)
           desing.autoCompleteTextViewUni.setAdapter(arrayAdapter)




//Şehir
        val districtItems = ArrayList<String>()
        createLawyerAdvertViewModel.cityList.observe(viewLifecycleOwner,{
            val cityItems = ArrayList<String>()
            for (a in it) {
                cityItems.add(a.cityName)
            }
            val cityArrayAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_item, cityItems)
            desing.autoCompleteTextViewCity.setAdapter(cityArrayAdapter)
        })

// ilçe
        desing.autoCompleteTextViewCity.setOnItemClickListener { adapterView, view, i, l ->
            districtItems.clear()
            desing.autoCompleteTextViewDistrict.text.clear()

            createLawyerAdvertViewModel.cityList.observe(viewLifecycleOwner,{

                for (a in it) {
                    if(a.cityName == desing.autoCompleteTextViewCity.text.toString()) {
                        for (b in a.district) {
                            districtItems.add(b)
                        }
                    }
                }
                val districtArrayAdapter = ArrayAdapter(requireContext(),
                    R.layout.dropdown_item, districtItems)
                desing.autoCompleteTextViewDistrict.setAdapter(districtArrayAdapter)
            })


        }


        // profession(Uzmanlık Alanı)
        var professionList = ArrayList<String>()
        createLawyerAdvertViewModel.professionListe.observe(viewLifecycleOwner,{
            for (a in it) {
                professionList.add(a.professionName)
            }
            val professionAdapter = ArrayAdapter(requireContext(),R.layout.dropdown_item,professionList)
            desing.autoCompleteTextViewOnePer.setAdapter(professionAdapter)

        })

        desing.autoCompleteTextViewOnePer.setOnItemClickListener { adapterView, view, i, l ->

            professionList = professionList
                .filter { s -> s != desing.autoCompleteTextViewOnePer.text.toString() }
                    as ArrayList<String>
            val professionAdapter = ArrayAdapter(requireContext(),R.layout.dropdown_item,professionList)
            desing.autoCompleteTextViewSeconPer.setAdapter(professionAdapter)

        }

        desing.autoCompleteTextViewSeconPer.setOnItemClickListener { adapterView, view, i, l ->

            professionList = professionList
                .filter { s -> s != desing.autoCompleteTextViewOnePer.text.toString() }
                    as ArrayList<String>

            professionList = professionList
                .filter { s -> s != desing.autoCompleteTextViewSeconPer.text.toString() }
                    as ArrayList<String>

            val professionAdapter = ArrayAdapter(requireContext(),R.layout.dropdown_item,professionList)
            desing.autoCompleteTextViewThirdPer.setAdapter(professionAdapter)
        }





      //  val bundle : CreateLawyerAdvertFragmentArgs by navArgs()


        val current = LocalDateTime.now()
        val day = current.dayOfMonth
        val mount = current.monthValue
        val year = current.year

        val cityItems = java.util.ArrayList<String>()
        cityItems.add("${day}/0${mount}/${year}")
        cityItems.add("${day+1}/0${mount}/${year}")
        cityItems.add("${day+2}/0${mount}/${year}")
        cityItems.add("${day+3}/0${mount}/${year}")

        val cityArrayAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_item, cityItems)
        desing.autoCompleteTextViewDate.setAdapter(cityArrayAdapter)

        desing.addLawyerAdvertbttn.setOnClickListener {

            val professionList = arrayOf("${desing.autoCompleteTextViewOnePer.text.toString()}",
                "${desing.autoCompleteTextViewSeconPer.text.toString()}",
                "${desing.autoCompleteTextViewThirdPer.text.toString()}")

           // val estiOnliHours = "${desing.timeOneTextView.text.toString()} / ${desing.timeTwoTextView.text.toString()}"

            val location ="${desing.autoCompleteTextViewCity.text.toString()} / ${desing.autoCompleteTextViewDistrict.text.toString()}"

            val age = desing.a.text.toString()


            val universyt = desing.autoCompleteTextViewUni.text.toString()
            val description = "Merhaba ben ${auth.currentUser?.displayName}. $age yaşındayım. $universyt okulundan mezun oldum. Şuanda çoğunlukla aşşağıda belirtilen alanlarada avukatlık yapmaktayım "


           // val getLongtude = bundle.longtude
         //   val getLatitude = bundle.latitude

           // desing.ada.text = getLatitude
           // desing.asa.text = getLongtude








            val timeOneText = desing.timeOneTextView.text.toString()
            val timeTwoText = desing.timeTwoTextView.text.toString()
            val timeThreeText = desing.timeThreeTextView.text.toString()
            val timeFourText = desing.timeFourTextView.text.toString()
            val locCordinate = arrayOf("","")
            val estiMeetHours = arrayOf(timeOneText,timeTwoText,timeThreeText,timeFourText)
            val selectDate =  desing.autoCompleteTextViewDate.text.toString()





            val newLawyer = Lawyer("${auth.currentUser?.uid}",imageLawyerUrl!!,
                "${auth.currentUser?.displayName}",
                "${selectionGender}" ,
                age,
                professionList,"${desing.autoCompleteTextViewCity.text.toString()}",
                desing.autoCompleteTextViewDistrict.text.toString(),
                estiMeetHours,
                "${selectDate}",
                "$description",
                universyt
            )


            createLawyerAdvertViewModel.createAdvert(newLawyer)
            Navigation.findNavController(it).navigate(R.id.toLHomePage)



        }




        return desing.root
    }




    fun uploadStrongeImage(fileUri: Uri) {
        val fileName = UUID.randomUUID().toString()+".jpg"
        val database = FirebaseDatabase.getInstance()

        var imageUrl = ""
        val refStorage = FirebaseStorage.getInstance().reference.child("images/$fileName")
        refStorage.putFile(fileUri)
            .addOnSuccessListener(
                OnSuccessListener<UploadTask.TaskSnapshot> { taskSnapshot ->
                    taskSnapshot.storage.downloadUrl.addOnSuccessListener {
                        imageUrl = it.toString()
                        imageUrlString(imageUrl)
                        Log.e("Resimin urlsi","${imageUrl}")
                    }
                })

            .addOnFailureListener(OnFailureListener { e ->
                print(e.message)
            })
        Log.e("Resimin urlsi 2","${imageLawyerUrl}")
    }

    fun imageUrlString(k:String) {
        imageLawyerUrl = k

    }




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val tempViewModel: CreateLawyerAdvertViewModel by viewModels()
        createLawyerAdvertViewModel = tempViewModel
    }

    private  fun pickImageGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_REQUEST_CODE)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == IMAGE_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            desing.lawyerImageView.setImageURI(data!!.data)
            imageUri = data?.data
            Log.e("daa","${imageUri}")
            uploadStrongeImage(imageUri!!)

        }


    }







}