package com.bigbratan.emulair.fragmentCloudStates

import android.graphics.Bitmap
import android.graphics.BitmapFactory
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

    private val storageRef = FirebaseStorage.getInstance().reference.child("Storage")
    val _imageList = MutableLiveData<List<Bitmap>>()
    val imageList: LiveData<List<Bitmap>>
        get() = _imageList

    fun fetchPhotos(): LiveData<List<Bitmap>> {
        storageRef.listAll().addOnSuccessListener { listResult ->
            val photos = mutableListOf<Bitmap>()
            for (item in listResult.items) {
                val photo = item.getBytes(Long.MAX_VALUE).addOnSuccessListener { bytes ->
                    val bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
                    photos.add(bitmap)
                }.addOnFailureListener { exception ->
                    Toast.makeText(getApplication(), "Error on getting cloud data!", Toast.LENGTH_SHORT).show()
                }
            }
            _imageList.value = photos // Move this line outside of the loop
        }.addOnFailureListener { exception ->
            // Handle error
        }
        //Toast.makeText(getApplication(), ""+_imageList.toString(), Toast.LENGTH_SHORT).show()

        return imageList
    }
}


