package com.example.test.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import com.example.test.R
import com.example.test.databinding.FragmentContactLawyerBinding
import io.agora.rtc.Constants
import io.agora.rtc.IRtcEngineEventHandler
import io.agora.rtc.RtcEngine
import io.agora.rtc.video.VideoCanvas


class ContactLawyerFragment : Fragment() {
    private lateinit var design : FragmentContactLawyerBinding

    
    private  var mRtcEngine: RtcEngine? = null
    private var channelName : String? = null
    private  var userRole = 1



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        design = DataBindingUtil.inflate(inflater,R.layout.fragment_contact_lawyer, container, false)
        design.fragment = this
        initAgoraEngineAndJoinChannel()
        return design.root
    }

    fun initAgoraEngineAndJoinChannel(){
        initializeAgoraEngine()
        mRtcEngine!!.setChannelProfile(Constants.CHANNEL_PROFILE_LIVE_BROADCASTING)
        mRtcEngine!!.setClientRole(userRole)
        mRtcEngine!!.enableVideo()
        if (userRole == 1) {
            setupLocalVideo()
        }else{
            val designLocalVideo = design.localVideo
            val localVideoCanvas = designLocalVideo
            localVideoCanvas.isVisible = false

        }


        joinChannel()


    }

    private  val mRtcEventHandler:IRtcEngineEventHandler = object : IRtcEngineEventHandler(){
        override fun onUserJoined(uid: Int, elapsed: Int) {
            activity?.runOnUiThread{setupRemoteVideo(uid)}

        }

        override fun onUserOffline(uid: Int, reason: Int) {
            activity?.runOnUiThread{onRemoteUserLeft()}

        }

        override fun onJoinChannelSuccess(channel: String?, uid: Int, elapsed: Int) {

            activity?.runOnUiThread { Log.e("Join Success","${uid}") }

        }

    }

    private fun initializeAgoraEngine(){

        try {
            mRtcEngine = RtcEngine.create(activity?.baseContext,APP_ID,mRtcEventHandler)
        }catch (e:Exception){
            println("Hata cam ${e.localizedMessage}")
        }
    }


    private  fun setupLocalVideo(){
        val container = design.localVideo
        val surfaceView = RtcEngine.CreateRendererView(activity?.baseContext)
        surfaceView.setZOrderMediaOverlay(true)
        container.addView(surfaceView)
        mRtcEngine!!.setupLocalVideo(VideoCanvas(surfaceView,VideoCanvas.RENDER_MODE_FIT,0))
    }
    private  fun joinChannel(){
        mRtcEngine!!.joinChannel(token,"engin",null,0)
    }


    private fun setupRemoteVideo(uid:Int){
        val container = design.remoteVideoViewContainer as FrameLayout

        if (container.childCount >= 1){
            return
        }

        val surfaceView = RtcEngine.CreateRendererView(activity?.baseContext)

        container.addView(surfaceView)
        mRtcEngine!!.setupRemoteVideo(VideoCanvas(surfaceView, VideoCanvas.RENDER_MODE_FIT,uid))
        surfaceView.tag = uid

    }

    private fun onRemoteUserLeft(){
        val container = design.remoteVideoViewContainer as FrameLayout
        container.removeAllViews()


    }


}