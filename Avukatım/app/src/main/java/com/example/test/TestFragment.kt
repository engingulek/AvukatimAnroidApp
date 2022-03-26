package com.example.test

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import com.example.test.databinding.FragmentTestBinding
import retrofit2.Call
import retrofit2.Callback
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation

import retrofit2.Response


class TestFragment : Fragment(),LocationListener {

    private lateinit var viewModel : ViewModel
    private lateinit var tetstinter : TestInterface
    private lateinit var call : Call<Test>
private lateinit var desing : FragmentTestBinding



private var izinKontrol = 0
    private lateinit var konumYoneticisi:LocationManager
    private val konumSaglayici = "gps"
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
desing = DataBindingUtil.inflate(inflater,R.layout.fragment_test, container, false)

konumYoneticisi = requireContext().getSystemService(Context.LOCATION_SERVICE) as LocationManager


        val num = arrayOf("1", "2", "3", "4")
      /*  viewModel.createAdvert("A","A","A","A"
            ,"A",num,"A","A","A","A")*/
val a  = Test("url","A","A","A"
    ,"A",num,"A","A","A","A")



     //viewModel.aka(a)

        desing.buttonA.setOnClickListener {

            //Navigation.findNavController(it).navigate(R.id.toMaps)
           izinKontrol = ContextCompat.checkSelfPermission(requireContext(),Manifest.permission.ACCESS_FINE_LOCATION)

            if(izinKontrol != PackageManager.PERMISSION_GRANTED){

                ActivityCompat.requestPermissions(requireActivity(), arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),100)

            }else{


                val konum = konumYoneticisi.getLastKnownLocation(konumSaglayici)
                if (konum != null) {
                 onLocationChanged(konum)
                }else{
                    desing.textViewBoy.text = "Konum yok"
                    desing.textViewEn.text = "Konum yok"
                }
            }

        }




        return desing.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val tempViewModel: ViewModel by viewModels()
       viewModel = tempViewModel
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == 100) {
            izinKontrol = ContextCompat.checkSelfPermission(requireContext(),Manifest.permission.ACCESS_FINE_LOCATION)
            if(grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                Toast.makeText(requireContext(),"İzin onaylandı ve aktif edildi",Toast.LENGTH_LONG).show()

                val konum = konumYoneticisi.getLastKnownLocation(konumSaglayici)
                if (konum != null) {
                    onLocationChanged(konum)
                }else{
                    desing.textViewBoy.text = "Konum yok"
                    desing.textViewEn.text = "Konum yok"
                }
            }else{

                Toast.makeText(requireContext(),"İzin onaylanmadı",Toast.LENGTH_LONG).show()

            }
        }
    }

    override fun onLocationChanged(location: Location) {
        val enlem = location?.latitude
        val boylam = location?.longitude

        desing.textViewEn.text ="$enlem"
        desing.textViewBoy.text = "$boylam"


    }

    override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {

        // konumla ilgili durum değiştiğinde çalışmaktadır.
    }

    override fun onProviderEnabled(provider: String) {

    }


    override fun onProviderDisabled(provider: String) {

    }

}