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
import com.example.test.ViewModel
import com.example.test.databinding.FragmentLawyerCheckInBinding
import com.example.test.entity.Account
import com.example.test.viewModel.CreateAccountViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LawyerCheckInFragment : Fragment() {
    private lateinit var design : FragmentLawyerCheckInBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var viewModel : CreateAccountViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        design = DataBindingUtil.inflate(inflater,R.layout.fragment_lawyer_check_in, container, false)
        design.fragmetLawyerCheckIn = this
        auth = Firebase.auth
        cardViewStatus()

        design.buttonSingUp.setOnClickListener {
            if (design.singUpNameSurnameEditText.text.toString() == ""
                || design.singUpEmailEditText.text.toString() == "" || design.singUpPasswordEditText.text.toString() == ""){
                println()
                alertMessage("Boş Kalan yerleri doldurunuz")
            }

            else {
                auth.createUserWithEmailAndPassword(design.singUpEmailEditText.text.toString()
                    ,design.singUpPasswordEditText.text.toString()).addOnCompleteListener() { task ->
                    if (task.isSuccessful) {
                        var user = auth.currentUser
                        var profileUpdate = UserProfileChangeRequest.Builder().apply {
                            displayName = design.singUpNameSurnameEditText.text.toString()
                        }.build()
                        user?.updateProfile(profileUpdate)?.addOnCompleteListener{ task ->
                            if (task.isSuccessful){


                                val nameSurname = auth.currentUser?.displayName.toString()
                                var uuid = auth.currentUser?.uid.toString()
                                val newAccount = Account(design.singUpEmailEditText.text.toString(),"lawyer",nameSurname,uuid)
                                viewModel.createAccount(newAccount)
                                Navigation.findNavController(it).navigate(R.id.toLawyerHomePage)




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


        design.singInButton.setOnClickListener {
            if(design.singInEmailEditText.text.toString() == ""
                || design.singInPasswordEditText.text.toString() == "") {
                alertMessage("Boş Kalan yerleri doldurunuz")
            }
            else {


                viewModel.accountList.observe(viewLifecycleOwner,{

                    for (a in it) {
                        if (a.email == design.singInEmailEditText.text.toString()){
                            if (a.accountType == "lawyer") {

                                auth.signInWithEmailAndPassword(
                                    design.singInEmailEditText.text.toString(),
                                    design.singInPasswordEditText.text.toString()
                                )
                                    .addOnCompleteListener() { task->
                                        // giriş işlemi başarılı bir şekilde gerçekleşti
                                        if (task.isSuccessful) {
                                            Navigation.findNavController(requireView()).navigate(R.id.toLawyerHomePage)
                                        }else{
                                            alertMessage("Hatalı Giriş. E-posta adresinizi ve şifrenizi kontrol ediniz")
                                        }
                                    }


                            }
                            else {
                                alertMessage("Avukat değil")

                            }

                        }

                        else {
                           // alertMessage("Burada hesabı yok")
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
        design.chipSingIn.setOnClickListener{
            design.cardViewSingUp.isVisible = false
            design.cardViewSingIn.isVisible  = true

            design.textViewCheckLawyerTitle.text = "Avukat Giriş Yap"
        }

        design.chipSingUp.setOnClickListener{
            design.cardViewSingIn.isVisible  = false
            design.cardViewSingUp.isVisible  = true
            design.textViewCheckLawyerTitle.text = "Avukat Kayıt Ol"
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