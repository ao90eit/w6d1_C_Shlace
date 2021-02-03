package com.aoinc.w6d1_c_shlace.view.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.aoinc.w6d1_c_shlace.R
import com.aoinc.w6d1_c_shlace.view.adapter.ShlaceAdapter
import com.aoinc.w6d1_c_shlace.viewmodel.ShlaceViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ShlacesFragment : Fragment() {

    // View Model
    private val shlaceViewModel: ShlaceViewModel by viewModels()

    private lateinit var shlacesRecyclerView: RecyclerView
    private val shlaceAdapter = ShlaceAdapter(listOf())
    private lateinit var addShlaceFab: FloatingActionButton

    private val uploadFragment = UploadFragment()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.shlaces_fragment_layout, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.d("TAG_X", "Shlaces Fragment VM -> $shlaceViewModel")

        shlacesRecyclerView = view.findViewById(R.id.shlaces_recyclerView)
        shlacesRecyclerView.adapter = shlaceAdapter

        addShlaceFab = view.findViewById(R.id.new_shlace_fab)
        addShlaceFab.setOnClickListener {
            childFragmentManager.beginTransaction()
                .add(R.id.upload_frame_layout, uploadFragment)
                .addToBackStack(uploadFragment.tag)
                .commit()
        }

        shlaceViewModel.getShlaces().observe(viewLifecycleOwner, {
            shlaceAdapter.updateShlaceList(it)
        })
    }
}