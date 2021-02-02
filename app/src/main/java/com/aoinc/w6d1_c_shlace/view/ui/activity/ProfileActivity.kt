package com.aoinc.w6d1_c_shlace.view.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.aoinc.w6d1_c_shlace.R
import com.aoinc.w6d1_c_shlace.view.adapter.ShlaceFragmentAdapter
import com.google.android.material.bottomnavigation.BottomNavigationView

class ProfileActivity : AppCompatActivity(), ViewPager.OnPageChangeListener {

    private lateinit var navigationView: BottomNavigationView
    // TODO: learn about ViewPager2
    private lateinit var profileViewPager: ViewPager
    private lateinit var shlaceFragmentAdapter: ShlaceFragmentAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        navigationView = findViewById(R.id.profile_bottom_navigation)
        profileViewPager = findViewById(R.id.profile_viewPager)

        profileViewPager.addOnPageChangeListener(this)

        shlaceFragmentAdapter = ShlaceFragmentAdapter(supportFragmentManager)
        profileViewPager.adapter = shlaceFragmentAdapter

        navigationView.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.places_menu_item -> loadFragment(0)
                else -> loadFragment(1)
            }
            true
        }
    }

    private fun loadFragment(fragmentId: Int) {
        profileViewPager.currentItem = fragmentId
    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
        // Do nothing
    }

    override fun onPageSelected(position: Int) {
        navigationView.selectedItemId = when (position) {
            0 -> R.id.places_menu_item
            else -> R.id.person_menu_item
        }
    }

    override fun onPageScrollStateChanged(state: Int) {
        // Do nothing
    }
}