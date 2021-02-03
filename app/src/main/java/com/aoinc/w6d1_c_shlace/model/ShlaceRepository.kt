package com.aoinc.w6d1_c_shlace.model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.aoinc.w6d1_c_shlace.model.data.Shlace
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

object ShlaceRepository {

    // make firebase database instance
    val firebaseDatabase: FirebaseDatabase =  FirebaseDatabase.getInstance()

    private val shlacesLiveData: MutableLiveData<List<Shlace>> = MutableLiveData()

    init {
        // set firebase to persist offline
        firebaseDatabase.setPersistenceEnabled(true)
    }

    fun getShlaces(): LiveData<List<Shlace>> {

        // child -> like a table name in the repo
        firebaseDatabase.reference.child("SHLACES")
            .addValueEventListener(object : ValueEventListener {

                // every time the online database changes
                override fun onDataChange(snapshot: DataSnapshot) {
                    val shlaceList = mutableListOf<Shlace>()

                    snapshot.children.forEach {
                        it.getValue(Shlace::class.java)?.let { shlace ->
                            shlaceList.add(shlace)
                        }
                    }

                    shlacesLiveData.value = shlaceList
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.e("TAG_X", "Error -> ${error.message}")
                }

            })

        return shlacesLiveData
    }

    fun postShlace(shlace: Shlace) {
        firebaseDatabase.reference.child("SHLACES").push().setValue(shlace)
        Log.d("TAG_X", "Shlace Posted!")
    }
}