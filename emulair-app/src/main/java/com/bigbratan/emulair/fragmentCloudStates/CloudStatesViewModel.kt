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
    var CloudStateList = MutableLiveData<List<CloudState>>()
        var statesList = mutableListOf<CloudState>()

    fun fetchPhotos() {
        storageRef.listAll().addOnSuccessListener { listResult ->
            val imageList = mutableListOf<Bitmap>()
            for (item in listResult.items) {
                item.getBytes(1024 * 1024).addOnSuccessListener { bytes ->
                    val bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
                    statesList.add(CloudState(item.name, bitmap))
                }.addOnFailureListener { exception ->
                    Log.d("myapp", "onFailure: " + exception.message)
                }
            }
            CloudStateList.value = statesList
        }.addOnFailureListener { exception -> }
         Toast.makeText(getApplication(), ""+CloudStateList.toString(), Toast.LENGTH_SHORT).show()
    }
}
