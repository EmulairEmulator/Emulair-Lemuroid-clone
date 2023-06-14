package com.bigbratan.emulair.common.managers.coresLibrary

import android.content.Context
import com.bigbratan.emulair.common.metadata.retrograde.CoreID
import java.io.InputStream
import java.util.zip.ZipInputStream
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Streaming
import retrofit2.http.Url

interface CoreUpdater {

    suspend fun downloadCores(context: Context, coreIDs: List<CoreID>)

    interface CoreManagerApi {
        @GET
        @Streaming
        suspend fun downloadFile(@Url url: String): Response<InputStream>

        @GET
        @Streaming
        suspend fun downloadZip(@Url url: String): Response<ZipInputStream>
    }
}
