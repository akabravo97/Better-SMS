package com.abhijeet.bettermessaging.view.splash

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.abhijeet.bettermessaging.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.splash_fragment.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@AndroidEntryPoint
class SplashFragment : Fragment() {

    val permissionRequestContract =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            permissions.entries.forEach {
                if (!it.value) {
                    activity?.finish()
                }
            }
            val action = SplashFragmentDirections.actionSplashFragmentToMessageListFragment()
            findNavController().navigate(action)
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.splash_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.READ_SMS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            permissionRequestContract.launch(
                arrayOf(
                    Manifest.permission.READ_SMS,
                    Manifest.permission.RECEIVE_SMS,
                    Manifest.permission.SEND_SMS,
                    Manifest.permission.READ_CONTACTS
                )
            )
        } else {
            //for animation
            lifecycleScope.launch {
                animateView(imageView)
                animateView(textView)
                delay(3000)
                val action = SplashFragmentDirections.actionSplashFragmentToMessageListFragment()
                findNavController().navigate(action)
            }
        }
    }

    fun animateView(view: View) {
        val anim = AlphaAnimation(1.0f, 0.3f)
        anim.repeatCount = Animation.INFINITE
        anim.repeatMode = Animation.REVERSE
        anim.duration = 300
        view.animation = anim
    }

    fun stopAnimation(view: View) {
        view.animation = null
    }
}