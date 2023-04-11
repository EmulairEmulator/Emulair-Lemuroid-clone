package com.bigbratan.emulair.fragmentCloudStates

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.bigbratan.emulair.EmulairApplication
import com.google.firebase.storage.FirebaseStorage

class CloudStatesViewModel(application: EmulairApplication) : AndroidViewModel(application) {
    private val storageRef = FirebaseStorage.getInstance().reference.child("Storage")

    fun fetchPhotos(): LiveData<List<Bitmap>> {
        val photosLiveData = MutableLiveData<List<Bitmap>>()

        storageRef.listAll().addOnSuccessListener { listResult ->
            val photos = mutableListOf<Bitmap>()
            for (item in listResult.items) {
                // Assuming you have a Photo data class or domain model
                val photo = item.getBytes(Long.MAX_VALUE).addOnSuccessListener { bytes ->
                    // Data for "images/island.jpg" is returns, use this as needed
                    val bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
                    photos.add(bitmap)
                }.addOnFailureListener { exception ->
                    Toast.makeText(getApplication(), "Error on getting cloud data!", Toast.LENGTH_SHORT).show()
                }
            }
            photosLiveData.value = photos
        }.addOnFailureListener { exception ->
            // Handle error
        }

        return photosLiveData
    }

}
