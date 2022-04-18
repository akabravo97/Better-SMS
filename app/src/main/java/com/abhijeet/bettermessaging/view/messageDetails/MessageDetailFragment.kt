package com.abhijeet.bettermessaging.view.messageDetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.abhijeet.bettermessaging.R
import kotlinx.android.synthetic.main.message_detail_fragment.*

class MessageDetailFragment : Fragment() {
    private val args: MessageDetailFragmentArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.message_detail_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        details_content.text = args.content
        details_date.text = args.date
        details_sender_name.text = args.sender

        requireActivity().onBackPressedDispatcher.addCallback(this) {
            val action =
                MessageDetailFragmentDirections.actionMessageDetailFragmentToMessageListFragment()
            findNavController().navigate(action)
        }
    }
}