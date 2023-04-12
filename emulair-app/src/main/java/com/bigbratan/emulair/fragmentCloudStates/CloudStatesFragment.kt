package com.bigbratan.emulair.fragmentCloudStates

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bigbratan.emulair.EmulairApplication
import com.bigbratan.emulair.R

class CloudStatesFragment : Fragment() {
    private lateinit var viewModel: CloudStatesViewModel
    private lateinit var recyclerView: RecyclerView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_cloudstates, container, false)

        recyclerView = view.findViewById(R.id.cloudstates_recyclerview)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        Log.d("myapp", "onCreateView: ")
        val factory = CloudStatesViewModel.Factory(requireActivity().application as EmulairApplication)
        viewModel = ViewModelProvider(this, factory).get(CloudStatesViewModel::class.java)
        viewModel.fetchPhotos() // Fetch the list of images

        Log.d("myapp", "onCreateView: ")
        viewModel._imageList.observe(viewLifecycleOwner, Observer { imageList ->
            // Update the RecyclerView adapter with the list of images
            val adapter = ImageAdapter(imageList) // Replace with your own adapter
            recyclerView.adapter = adapter
            Log.d("myapp", "adapter set: ")
        })
        Log.d("myapp", "before fetchphotos")

        Log.d("myapp", "after fetchphotos")
        return view
    }
    // Rest of your code...
}
