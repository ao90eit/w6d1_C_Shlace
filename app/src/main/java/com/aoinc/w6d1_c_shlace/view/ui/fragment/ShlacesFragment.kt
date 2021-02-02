package com.aoinc.w6d1_c_shlace.view.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.aoinc.w6d1_c_shlace.R
import com.aoinc.w6d1_c_shlace.model.Shlace
import com.aoinc.w6d1_c_shlace.view.adapter.ShlaceAdapter

class ShlacesFragment : Fragment() {

    private lateinit var shlacesRecyclerView: RecyclerView
//    private var shlaceList: List<Shlace> = listOf()
    private val shlaceAdapter = ShlaceAdapter(listOf())

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.shlaces_fragment_layout, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        shlacesRecyclerView = view.findViewById(R.id.shlaces_recyclerView)
        shlacesRecyclerView.adapter = shlaceAdapter
    }
}