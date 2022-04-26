package com.example.test.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import com.example.test.R
import com.example.test.databinding.FragmentSelectionPageBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class SelectionPageFragment : Fragment() {
    private lateinit var desing : FragmentSelectionPageBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        desing = DataBindingUtil.inflate(inflater,R.layout.fragment_selection_page, container, false)
        desing.fragmentSelectionPage = this
        auth = Firebase.auth
        desing.buttonLawyer.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.toCheckInLawyer)
        }
        desing.buttonClient.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.toCheckInClient)

        }
        return desing.root
    }

    // Daha önceden giriş yapılıp yapılmadığı kontrol edilmektedir.
    override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        if (currentUser != null ) {
            Navigation.findNavController(desing.root).navigate(R.id.toClientHomePageTwo)

        }

    }



}