package com.bigbratan.emulair.fragmentCloudStates

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.*
import com.bigbratan.emulair.EmulairApplication
import com.bigbratan.emulair.common.managerSaves.StatesManager
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.storage.FirebaseStorage
import java.io.File
import javax.inject.Inject

class CloudStatesViewModel(application: EmulairApplication) : AndroidViewModel(application) {

    class Factory(val application: EmulairApplication) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return CloudStatesViewModel(application) as T
        }
    }

    @Inject
    lateinit var statesManager: StatesManager
    private val storagePreviewsRef = FirebaseStorage.getInstance().reference.child("State Previews")
    private val storageStatesRef = FirebaseStorage.getInstance().reference.child("States")
    var CloudStateList = MutableLiveData<List<CloudState>>()
        var statesList = mutableListOf<CloudState>()


    fun fetchStateToDisk(nameOfPreview:String, image: Bitmap?){
        //remove what comes after last dot
        val nameOfState = nameOfPreview.substringBeforeLast(".")
        storageStatesRef.child(nameOfState).getBytes(1024 * 1024).addOnSuccessListener { bytes ->
            val folderNameStates = "states/mgba"
            val folderNamePreviews = "state-previews"
            val fileNameState = nameOfState
            val fileNamePreview = nameOfPreview
//            val File = File(getApplication<EmulairApplication>().getExternalFilesDir(folderNamePreviews), fileNamePreview)
            val file = File(getApplication<EmulairApplication>().getExternalFilesDir(folderNameStates), fileNameState.replace("_vali2wd", ""))
            try{
                file.writeBytes(bytes)
                //TODO: show snackbar to confirm download

                //TODO: also write preview file to disk
                //TODO: deal with folder to which the save is written (gba is /mgba)
//                val outputStream = File.outputStream()
//                image.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
//                outputStream.flush()
//                outputStream.close()
            }
            catch (e:Exception){
                Log.d("myapp", "fetchStateToDisk: "+e.message)
            }

        }.addOnFailureListener { exception ->
            Log.d("myapp", "onFailure: " + exception.message)
        }
    }

    // You'll need to import com.google.firebase.storage.ktx.component1 and
    // com.google.firebase.storage.ktx.component2
//    fun listAllPaginated(pageToken: String?){
//        val listPageTask = if (pageToken != null) {
//            storagePreviewsRef.list(100, pageToken)
//        } else {
//            storagePreviewsRef.list(100)
//        }
//
//        listPageTask
//            .addOnSuccessListener { (items, prefixes, pageToken) ->
//
//            }
//
//    }
    fun fetchPhotos() {
        storagePreviewsRef.listAll().addOnSuccessListener { listResult ->

            val imageList = mutableListOf<Bitmap>()
            for (item in listResult.items) {
                item.getBytes(1024 * 1024).addOnSuccessListener { bytes ->
                    val bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)

                    statesList.add(CloudState(item.name, bitmap))


                    CloudStateList.value = statesList

                }.addOnFailureListener { exception ->

                }
            }

        }.addOnFailureListener { exception -> }
         Toast.makeText(getApplication(), ""+CloudStateList.toString(), Toast.LENGTH_SHORT).show()
    }


}
