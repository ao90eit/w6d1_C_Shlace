package com.aoinc.w6d1_c_shlace.view.ui.fragment

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.aoinc.w6d1_c_shlace.R
import com.aoinc.w6d1_c_shlace.model.data.Shlace
import com.aoinc.w6d1_c_shlace.viewmodel.ShlaceViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import java.io.ByteArrayOutputStream

class UploadFragment : Fragment() {

    // View Models
    private val shlaceViewModel: ShlaceViewModel by viewModels()

    // Layout Views
    private lateinit var uploadButton: Button
    private lateinit var addressEditText: EditText
    private lateinit var descriptionEditText: EditText
    private lateinit var placeImageView: ImageView

    private var shlaceBitmap: Bitmap? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.upload_fragment_layout, container, false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val backPressedCallback = requireActivity().onBackPressedDispatcher
            .addCallback(this) {
                parentFragment?.childFragmentManager?.popBackStack()
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.d("TAG_X", "Upload Fragment VM -> $shlaceViewModel")

        uploadButton = view.findViewById(R.id.upload_button)
        addressEditText = view.findViewById(R.id.address_editText)
        descriptionEditText = view.findViewById(R.id.description_editText)
        placeImageView  = view.findViewById(R.id.upload_imageView)

        placeImageView.setOnClickListener {
            val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(cameraIntent, 111)
        }

        uploadButton.setOnClickListener {

            shlaceBitmap?.let {
                val byteOutputStream = ByteArrayOutputStream()
                it.compress(Bitmap.CompressFormat.JPEG, 100, byteOutputStream)
                val imageBytes = byteOutputStream.toByteArray()

                val userId = FirebaseAuth.getInstance().currentUser?.uid ?: "default"
                val storageReference = FirebaseStorage.getInstance().reference.child(userId)

                val uploadTask = storageReference.putBytes(imageBytes)
                uploadTask.addOnCompleteListener {

                    if (it.isSuccessful) {
                        storageReference
                            .downloadUrl    // file URL of where the file was uploaded
                            .addOnCompleteListener { downloadTask ->
                                if (downloadTask.isSuccessful) {
                                    upload(downloadTask.result)
                                }
                            }
                    }
                }

            } ?: { Toast.makeText(context, "Must have a picture.", Toast.LENGTH_SHORT).show() }()
        }
    }

    private fun upload(result: Uri?) {
        val userEmail = FirebaseAuth.getInstance().currentUser?.email ?: "unknown"
        val location = addressEditText.text.toString().trim()
        val description = descriptionEditText.text.toString().trim()
        val shlace = Shlace().also {
            it.photoURL = result.toString()
            it.postedBy = userEmail
            it.description = description
            it.address = location
        }

        shlaceViewModel.uploadShlace(shlace)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // can do null checks here, being lazy for now.

        // "data" is android default name here
        shlaceBitmap = data?.extras?.get("data") as Bitmap?
        shlaceBitmap?.let {
            placeImageView.setImageBitmap(it)
        }
    }
}