package com.example.test.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.example.test.R
import com.example.test.databinding.FragmentClientPageBinding
import com.example.test.entity.Account
import com.example.test.viewModel.CreateAccountViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class ClientPageFragment : Fragment() {
    private lateinit var design : FragmentClientPageBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var viewModel : CreateAccountViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        design = DataBindingUtil.inflate(inflater,R.layout.fragment_client_page, container, false)
        design.fragmentClientPage = this
        auth = Firebase.auth
        cardViewStatus()

        design.buttonSingUp.setOnClickListener {
            if (design.singUpClientNameSurnameEditText.text.toString() == ""
                || design.singUpClientEmailEditText.text.toString() == "" || design.singUpClientPasswordEditText.text.toString() == ""){
                println()
                alertMessage("Boş Kalan yerleri doldurunuz")
            }

            else {
                auth.createUserWithEmailAndPassword(design.singUpClientEmailEditText.text.toString()
                    ,design.singUpClientPasswordEditText.text.toString()).addOnCompleteListener() { task ->
                    if (task.isSuccessful) {
                        var user = auth.currentUser
                        var profileUpdate = UserProfileChangeRequest.Builder().apply {
                            displayName = design.singUpClientNameSurnameEditText.text.toString()
                        }.build()
                        user?.updateProfile(profileUpdate)?.addOnCompleteListener{ task ->
                            if (task.isSuccessful){
                                val newAccount = Account(design.singUpClientEmailEditText.text.toString(),"client")
                                viewModel.createAccount(newAccount)
                               // Navigation.findNavController(it).navigate(R.id.toClientHomePage)
                            }
                        }
                    }else {
                        if ("${task.exception}" == "com.google.firebase.auth.FirebaseAuthUserCollisionException: The email address is already in use by another account.") {
                            alertMessage("Böyle bir e-posta adresi bulunmaktadır")

                        }

                        if("${task.exception}" == "com.google.firebase.auth.FirebaseAuthWeakPasswordException: The given password is invalid. [ Password should be at least 6 characters ]") {
                            alertMessage("Şifreniz 6 karakterden fazla olmalıdır")
                        }

                        if ("${task.exception}" == "com.google.firebase.auth.FirebaseAuthInvalidCredentialsException: The email address is badly formatted.") {
                            alertMessage("Desteklenmeye e-posta formatıdır.")
                        }

                        Log.e("Hata","${task.exception}")
                    }
                }
            }
        }


        design.singInClientButton.setOnClickListener {
            if(design.singInClientEmailEditText.text.toString() == ""
                || design.singInClientPasswordEditText.text.toString() == "") {
                alertMessage("Boş Kalan yerleri doldurunuz")
            }
            else {
                // kullanıcı girişi Yapılamaktadır.


                    viewModel.accountList.observe(viewLifecycleOwner,{
                        for (a in it)  {
                            if (a.email == design.singInClientEmailEditText.text.toString()) {
                                if (a.accountType == "client") {
                                    auth.signInWithEmailAndPassword(
                                        design.singInClientEmailEditText.text.toString(),
                                        design.singInClientPasswordEditText.text.toString()
                                    )
                                        .addOnCompleteListener() { task->
                                            // giriş işlemi başarılı bir şekilde gerçekleşti
                                            if (task.isSuccessful) {
                                                Navigation.findNavController(requireView()).navigate(R.id.toClientHomePage)
                                            }else{
                                                alertMessage("Hatalı Giriş. E-posta adresinizi ve şifrenizi kontrol ediniz")
                                            }
                                        }

                                }else {
                                    alertMessage("Müşteri değil")
                                }
                            }
                        }
                    })




            }
        }








        return design.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val tempViewModel :  CreateAccountViewModel by viewModels()
        viewModel = tempViewModel
    }



    fun cardViewStatus() {
        design.chipClientSingIn.setOnClickListener{
            design.cardViewClientSingUp.isVisible = false
            design.cardViewClientSingIn.isVisible  = true

            design.textViewCheckClientTitle.text = "Müvekkil Giriş Yap"
        }

        design.chipClientSingUp.setOnClickListener{
            design.cardViewClientSingIn.isVisible  = false
            design.cardViewClientSingUp.isVisible  = true
            design.textViewCheckClientTitle.text = "Müvekkil Kayıt Ol"
        }

    }


    fun alertMessage(message:String) {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Uyarı")
            .setMessage(message)

            .setPositiveButton("Kapat") { dialog, which ->
                // Respond to positive button press
            }
            .show()
    }

}