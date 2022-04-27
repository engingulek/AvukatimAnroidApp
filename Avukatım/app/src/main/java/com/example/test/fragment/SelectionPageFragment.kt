package com.example.test.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.example.test.R
import com.example.test.databinding.FragmentSelectionPageBinding
import com.example.test.viewModel.CreateAccountViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class SelectionPageFragment : Fragment() {
    private lateinit var desing : FragmentSelectionPageBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var viewModel : CreateAccountViewModel

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

            viewModel.accountList.observe(viewLifecycleOwner,{
                for (a in it) {
                    if (a.email == auth.currentUser?.email) {
                        if(a.accountType == "lawyer") {
                            Navigation.findNavController(desing.root).navigate(R.id.toLawyerHomePageTwo)
                        }else{
                            Navigation.findNavController(desing.root).navigate(R.id.toClientHomePageTwo)

                        }
                    }
                }
            })





        }




    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val tempViewModel :  CreateAccountViewModel by viewModels()
        viewModel = tempViewModel
    }


}