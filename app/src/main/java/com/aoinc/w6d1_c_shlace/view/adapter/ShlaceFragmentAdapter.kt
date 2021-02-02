package com.aoinc.w6d1_c_shlace.view.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.aoinc.w6d1_c_shlace.view.ui.fragment.MyProfileFragment
import com.aoinc.w6d1_c_shlace.view.ui.fragment.ShlacesFragment

class ShlaceFragmentAdapter(fragmentManager: FragmentManager)
    : FragmentPagerAdapter(fragmentManager, FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    private val shlacesFragment = ShlacesFragment()
    private val myProfileFragment = MyProfileFragment()

    // num of fragments
    override fun getCount(): Int = 2

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> shlacesFragment
            else -> myProfileFragment
        }
    }
}