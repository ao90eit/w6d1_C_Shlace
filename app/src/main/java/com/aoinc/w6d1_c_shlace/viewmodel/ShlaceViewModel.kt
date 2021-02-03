package com.aoinc.w6d1_c_shlace.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.aoinc.w6d1_c_shlace.model.ShlaceRepository
import com.aoinc.w6d1_c_shlace.model.data.Shlace

class ShlaceViewModel : ViewModel() {

    fun getShlaces(): LiveData<List<Shlace>> = ShlaceRepository.getShlaces()
    fun uploadShlace(shlace: Shlace) = ShlaceRepository.postShlace(shlace)
}