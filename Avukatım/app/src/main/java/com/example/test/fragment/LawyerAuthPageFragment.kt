package com.example.test.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.example.test.R
import com.example.test.databinding.FragmentLawyerAuthPageBinding
import com.example.test.entity.Lawyer
import com.example.test.entity.LawyerInfo
import com.example.test.entity.LawyerInfoResult
import com.example.test.retrofit.APIUtils
import com.example.test.retrofit.MyLawyerDaoInterface
import com.example.test.viewModel.CreateLawyerAdvertViewModel
import com.example.test.viewModel.LawyerAuthViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class LawyerAuthPageFragment : Fragment() {
    private lateinit var lawyerAuthViewModel : LawyerAuthViewModel
    private lateinit var d : MyLawyerDaoInterface
    private lateinit var auth: FirebaseAuth
    private lateinit var design: FragmentLawyerAuthPageBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

      /*  lawyerAuthViewModel.getLawyerAuthInfo("1")

       lawyerAuthViewModel.authInfo.observe(viewLifecycleOwner,{
            Log.e("Kullanıcının ilan detayları","${it}")
        })*/

design = DataBindingUtil.inflate(inflater,R.layout.fragment_lawyer_auth_page, container, false)
        design.fragmehtAuthLawyer = this
       auth = Firebase.auth
        val uid = auth.currentUser?.uid.toString()
        getLawyerUid(uid)






        return design.root
    }


    fun getLawyerUid(uid:String) {
        Log.e("Tetstaad","1")

        val aa = arrayOf("")
       // val a  = Lawyer(uid,"","","" ,"",aa,"","","",aa)


        val lawyer = Lawyer(uid,"",
            "",
            "" ,
            "",
            aa,"",
            "",
            aa,"jj",
            "",
            "universyt"
        )
        d = APIUtils.getMyLawyerDaoInterface()

       d.getAuthInfoLawyer(lawyer).enqueue(object : Callback<LawyerInfoResult>{
           override fun onResponse(
               call: Call<LawyerInfoResult>,
               response: Response<LawyerInfoResult>
           ) {
               Log.e("Gelen avukat verileri","${response.body().lawyerInfoList}")

               for ( a in response.body().lawyerInfoList) {
                   getuserImage(a)


           }
           }

           override fun onFailure(call: Call<LawyerInfoResult>?, t: Throwable?) {

           }


       })

    }



    fun getuserImage(info:LawyerInfo) {


        Picasso.get().load(info.lawyerImageUrl).into(design.lawyerAuthImageView)
        design.age.setText(info.lawyerAge.toString())
      if (design.radioButtonManUid.text == info.lawyerGender.toString())  {
          design.radioButtonManUid.isChecked
      }else {
          design.radioButtonWomanUid.isChecked
      }

        design.textViewUni.text = "Universite bilgileri yazılacak"

        // şehir
        val districtItems = ArrayList<String>()
        lawyerAuthViewModel.cityList.observe(viewLifecycleOwner,{
            val cityItems = ArrayList<String>()
            for (a in it) {
                cityItems.add(a.cityName)
            }
            val cityArrayAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_item, cityItems)
            design.autoCompleteTextViewCityUid.setAdapter(cityArrayAdapter)
        })

        // ilçe
        design.autoCompleteTextViewCityUid.setOnItemClickListener { adapterView, view, i, l ->
            districtItems.clear()
            design.autoCompleteTextViewDistrictUid.text.clear()

            lawyerAuthViewModel.cityList.observe(viewLifecycleOwner,{

                for (a in it) {
                    if(a.cityName == design.autoCompleteTextViewCityUid.text.toString()) {
                        for (b in a.district) {
                            districtItems.add(b)
                        }
                    }
                }
                val districtArrayAdapter = ArrayAdapter(requireContext(),
                    R.layout.dropdown_item, districtItems)
                design.autoCompleteTextViewDistrictUid.setAdapter(districtArrayAdapter)
            })


        }

        // profession(Uzmanlık Alanı)
        var professionList = ArrayList<String>()
        lawyerAuthViewModel.professionListe.observe(viewLifecycleOwner,{
            for (a in it) {
                professionList.add(a.professionName)
            }
            val professionAdapter = ArrayAdapter(requireContext(),R.layout.dropdown_item,professionList)
            design.autoCompleteTextViewOnePerUid.setAdapter(professionAdapter)

        })

        design.autoCompleteTextViewOnePerUid.setOnItemClickListener { adapterView, view, i, l ->

            professionList = professionList
                .filter { s -> s != design.autoCompleteTextViewOnePerUid.text.toString() }
                    as ArrayList<String>
            val professionAdapter = ArrayAdapter(requireContext(),R.layout.dropdown_item,professionList)
            design.autoCompleteTextViewSeconPerUid.setAdapter(professionAdapter)

        }

        design.autoCompleteTextViewSeconPerUid.setOnItemClickListener { adapterView, view, i, l ->

            professionList = professionList
                .filter { s -> s != design.autoCompleteTextViewOnePerUid.text.toString() }
                    as ArrayList<String>

            professionList = professionList
                .filter { s -> s != design.autoCompleteTextViewSeconPerUid.text.toString() }
                    as ArrayList<String>

            val professionAdapter = ArrayAdapter(requireContext(),R.layout.dropdown_item,professionList)
            design.autoCompleteTextViewThirdPerUid.setAdapter(professionAdapter)
        }




        design.deleteLawyerAdvertbttn.setOnClickListener {
            val aa = arrayOf("")
           // val a  = Lawyer(info.id.toString(),"","","" ,"",aa,"","","",aa)
            val lawyer = Lawyer(info.id.toString(),"",
                "",
                "" ,
                "",
                aa,"",
                "",
                aa,"jj",
                "",
                "universyt"
            )

            d = APIUtils.getMyLawyerDaoInterface()
            d.delete(lawyer).enqueue(object : Callback<LawyerInfoResult>{
                override fun onResponse(call: Call<LawyerInfoResult>,
                    response: Response<LawyerInfoResult>) {
                    Navigation.findNavController(it).navigate(R.id.toLHomePage)
                }override fun onFailure(call: Call<LawyerInfoResult>?, t: Throwable?) {}


            })
        }



    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val tempViewModel: LawyerAuthViewModel by viewModels()
        lawyerAuthViewModel = tempViewModel
    }





}