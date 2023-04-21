package com.bigbratan.emulair.fragmentCloudStates

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.bigbratan.emulair.EmulairApplication
import com.bigbratan.emulair.R
import com.bigbratan.emulair.managerLayout.DynamicGridLayoutManager
import com.bigbratan.emulair.managerLayout.GridSpaceDecoration
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

/*class CloudStatesFragment : Fragment() {
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
        viewModel.mutableImageList.observe(viewLifecycleOwner, Observer { imageList ->
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
}*/

class CloudStatesFragment : Fragment(), SelectListener {
    @Inject
    lateinit var application: EmulairApplication

    private var cloudStatesAdapter: ImageAdapter? = null

    private lateinit var cloudStatesViewModel: CloudStatesViewModel

    private var recyclerView: RecyclerView? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreate(savedInstanceState)
        val root = inflater.inflate(R.layout.fragment_cloudstates, container, false)
        recyclerView = root.findViewById(R.id.recycler_view)
        return root
    }

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val factory = CloudStatesViewModel.Factory(requireActivity().application as EmulairApplication)
        cloudStatesViewModel = ViewModelProvider(this, factory)[CloudStatesViewModel::class.java]
        cloudStatesViewModel.fetchPhotos()
        cloudStatesViewModel.CloudStateList.observe(viewLifecycleOwner) { imageList ->
           Log.d("myapp", "adapter start set:")
            cloudStatesAdapter = ImageAdapter(/*requireContext().applicationContext,*/ imageList, this )

            recyclerView?.adapter = cloudStatesAdapter
            Log.d("myapp", "adapter end set: ")
        }


        recyclerView?.apply {
            this.layoutManager = DynamicGridLayoutManager(context, 3)

            val spacingInPixels = resources.getDimensionPixelSize(R.dimen.grid_spacing)
            GridSpaceDecoration.setSingleGridSpaceDecoration(this, spacingInPixels)
        }

    }

    @dagger.Module
    class Module

    override fun onItemClicked(position: Int) {
        val clickedItem = cloudStatesViewModel.CloudStateList.value?.get(position)
        if (clickedItem != null) {
            cloudStatesViewModel.fetchStateToDisk(clickedItem.title,clickedItem.image)
        }

    }
}
