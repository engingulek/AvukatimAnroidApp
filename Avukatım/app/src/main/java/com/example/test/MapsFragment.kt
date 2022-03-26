package com.example.test

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import androidx.fragment.app.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavDirections
import androidx.navigation.Navigation
import com.example.test.databinding.FragmentMapsBinding

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MapsFragment : Fragment(), LocationListener {
    private lateinit var desing:FragmentMapsBinding
    private lateinit var pass: NavDirections

    private var izinKontrol = 0
    private lateinit var konumYoneticisi: LocationManager
    private val konumSaglayici = "gps"


    private val callback = OnMapReadyCallback { googleMap ->
        val lawyerOffice = LatLng(41.0273353, 29.0192175)
        googleMap.addMarker(MarkerOptions().position(lawyerOffice).title("Evim"))
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(lawyerOffice,18f))
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        desing = DataBindingUtil.inflate(inflater,R.layout.fragment_maps, container, false)
        konumYoneticisi = requireContext().getSystemService(Context.LOCATION_SERVICE) as LocationManager
        pass = MapsFragmentDirections.toCreate( "","")




        desing.fragmentMaps = this


        desing.bttnMakeLoc.setOnClickListener {

            izinKontrol = ContextCompat.checkSelfPermission(requireContext(),Manifest.permission.ACCESS_FINE_LOCATION)

            if(izinKontrol != PackageManager.PERMISSION_GRANTED){

                ActivityCompat.requestPermissions(requireActivity()
                    , arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),100)

            }else{

                val konum = konumYoneticisi.getLastKnownLocation(konumSaglayici)

                if(konum != null){

                    onLocationChanged(konum)

                }else{
                   desing.teas.text = "Konum yok"
                }

            }

        }



        return desing.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {

        if(requestCode == 100){

            izinKontrol = ContextCompat.checkSelfPermission(requireContext(),Manifest.permission.ACCESS_FINE_LOCATION)

            if(grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Toast.makeText(requireContext(),"İzin onaylandı ve aktif edildi",Toast.LENGTH_SHORT).show()


                val konum = konumYoneticisi.getLastKnownLocation(konumSaglayici)

                if(konum != null){

                    onLocationChanged(konum)

                }else{
                   desing.teas.text = "yok konum"
                }



            }else{
                Toast.makeText(requireContext(),"İzin onaylanmandı ve aktif edilmedi!!!!",Toast.LENGTH_SHORT).show()
            }

        }
    }

    override fun onLocationChanged(location: Location) {
        val enlem = location?.latitude
        val boylam = location?.longitude
        desing.teas.text = enlem.toString()




    }

    override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {

        // konumla ilgili durum değiştiğinde çalışmaktadır.
    }

    override fun onProviderEnabled(provider: String) {

    }


    override fun onProviderDisabled(provider: String) {

    }

}