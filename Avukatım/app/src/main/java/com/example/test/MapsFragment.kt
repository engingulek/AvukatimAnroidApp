package com.example.test

import androidx.fragment.app.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import com.example.test.databinding.FragmentMapsBinding
import com.example.test.fragment.CreateLawyerAdvertFragmentDirections

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MapsFragment : Fragment() {
    private lateinit var desing:FragmentMapsBinding

    private val callback = OnMapReadyCallback { googleMap ->
        val sydney = LatLng(41.0273353, 29.0192175)
        googleMap.addMarker(MarkerOptions().position(sydney).title("Evim"))
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        desing = DataBindingUtil.inflate(inflater,R.layout.fragment_maps, container, false)
        desing.fragmentMaps = this


        desing.bttnMakeLoc.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.toCreate)
            val longtude = ""
            val latitude = ""
            val pass = MapsFragmentDirections.toCreate(latitude,longtude)
            Navigation.findNavController(it).navigate(pass)
        }



        return desing.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
    }
}