package com.example.test.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import com.example.test.R
import com.example.test.databinding.FragmentLawyerHomePageBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LawyerHomePageFragment : Fragment() {
    private lateinit var auth: FirebaseAuth
    private lateinit var design : FragmentLawyerHomePageBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        auth = Firebase.auth
        design = DataBindingUtil.inflate(inflater,R.layout.fragment_lawyer_home_page, container, false)
        design.fragmentLawyerHomePage = this
        design.authUser = auth
        design.bttntoCreateAdvert.setOnClickListener {

            Navigation.findNavController(it).navigate(R.id.toLawyerCreateAdvert)
        }

        return design.root
    }


}