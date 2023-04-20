package com.bigbratan.emulair.fragmentCloudStates

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.*
import com.bigbratan.emulair.EmulairApplication
import com.google.firebase.storage.FirebaseStorage

class CloudStatesViewModel(application: EmulairApplication) : AndroidViewModel(application) {

    class Factory(val application: EmulairApplication) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return CloudStatesViewModel(application) as T
        }
    }

    private val storageRef = FirebaseStorage.getInstance().reference.child("State Previews")
    val mutableImageList = MutableLiveData<List<Bitmap>>()
    val imageList: LiveData<List<Bitmap>>
        get() = mutableImageList

    fun fetchPhotos(): LiveData<List<Bitmap>> {
        storageRef.listAll().addOnSuccessListener { listResult ->
            val photos = mutableListOf<Bitmap>()
            for (item in listResult.items) {
                Log.d("myapp", "downlaoding image " + item.toString())
                item.getBytes(1024 * 1024).addOnSuccessListener { bytes ->
                    val bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
                    photos.add(bitmap)
                }.addOnFailureListener { exception ->
                    // Handle any errors
                }
            }
            Log.d("myapp", "assigning mutableimagelist " + photos.toString())
            mutableImageList.value = photos
        }.addOnFailureListener { exception -> }
         Toast.makeText(getApplication(), ""+mutableImageList.toString(), Toast.LENGTH_SHORT).show()
        return imageList
    }
}
