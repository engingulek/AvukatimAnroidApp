package com.example.test.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import com.example.test.R
import com.example.test.databinding.FragmentClientAuthPageBinding


class ClientAuthPageFragment : Fragment() {
    private lateinit  var design : FragmentClientAuthPageBinding




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        design = DataBindingUtil.inflate(inflater,R.layout.fragment_client_auth_page, container, false)

        design.imageView.setImageResource(R.drawable.profile)
        design.buttonToFav.setOnClickListener {

            Navigation.findNavController(it).navigate(R.id.toFav)
        }

        return design.root
    }


}