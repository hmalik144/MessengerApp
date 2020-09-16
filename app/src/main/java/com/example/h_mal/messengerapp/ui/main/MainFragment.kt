package com.example.h_mal.messengerapp.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.h_mal.messengerapp.R
import com.example.h_mal.messengerapp.utils.showToast
import kotlinx.android.synthetic.main.main_fragment.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance

class MainFragment : Fragment(), KodeinAware {
    // Kodein DI to retrieve @MainViewModelFactory
    override val kodein by kodein()
    private val factory by instance<MainViewModelFactory>()

    companion object {
        fun newInstance() = MainFragment()
    }

    @ExperimentalCoroutinesApi
    private lateinit var viewModel: MainViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    @ExperimentalCoroutinesApi
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this, factory).get(MainViewModel::class.java)

        // Toast viewmodel error messages
        viewModel.operationFailure.observe(viewLifecycleOwner, Observer {
            it.getContentIfNotHandled()?.let { operationFailure ->
                context?.showToast(operationFailure)
            }
        })

        message_recycler.apply {
            layoutManager = LinearLayoutManager(requireContext()).apply {
                stackFromEnd = true
            }
            setHasFixedSize(true)

            // Apply adapter to recycler view
            // anchor to the bottom when chats are being populated
            adapter = MessageRecyclerAdapter(viewModel.messagesLiveData).apply {
                registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver(){
                    override fun onChanged() {
                        super.onChanged()
                        scrollToPosition(itemCount - 1)
                    }
                })
            }
        }

        message_box_et.apply {
            setOnEditorActionListener { _, actionId, _ ->
                // Press enter to send message
                when (actionId) {
                    EditorInfo.IME_ACTION_DONE ->{
                        viewModel.submitMessage(text.toString())
                        text.clear()
                    }
                }
                true
            }

            submit.setOnClickListener {
                // send message on submit
                viewModel.submitMessage(text.toString())
                text.clear()
            }
        }
    }

}