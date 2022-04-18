package com.abhijeet.bettermessaging.view.messages

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.abhijeet.bettermessaging.R
import com.abhijeet.bettermessaging.databinding.MessageListFragmentBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MessageListFragment : Fragment() {
    private val viewModel by viewModels<MessageListViewModel>()
    private var recyclerBinding: MessageListFragmentBinding? = null
    private val _recyclerBinding get() = recyclerBinding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.message_list_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerBinding = MessageListFragmentBinding.bind(view)
        val adapter = MessagePhotoAdapter()
        _recyclerBinding.apply {
            recyclerView.setHasFixedSize(true)
            recyclerView.adapter = adapter
        }
        viewModel.currentMessage.observe(viewLifecycleOwner) {
            //TODO: update adapter
            println("Message : $it")
            adapter.submitData(viewLifecycleOwner.lifecycle, it)

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        recyclerBinding = null
    }
}