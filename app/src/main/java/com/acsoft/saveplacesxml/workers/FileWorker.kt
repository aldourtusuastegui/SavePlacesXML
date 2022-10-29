package com.acsoft.saveplacesxml.workers

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.acsoft.saveplacesxml.util.Constants.TAG
import com.acsoft.saveplacesxml.util.FileUtils
import com.acsoft.saveplacesxml.util.Notifications.makeStatusNotification
import com.acsoft.saveplacesxml.util.Notifications.sleep

class FileWorker(context: Context, workerParams: WorkerParameters) : Worker(context, workerParams) {

    override fun doWork(): Result {
        val appContext = applicationContext
        makeStatusNotification("Save places","Creando archivo....", appContext)
        val data = inputData.getString("data")
        return try {
            FileUtils.writeFilePlaceList(appContext,"places",data.toString())
            sleep()
            Log.d(TAG, "Se creo archivo con lista de lugares")
            makeStatusNotification("Save places","Archivo creado con éxito....", appContext)
            Result.success()

        } catch (throwable: Throwable) {
            Log.e(TAG, "No fue posible crear el archivo")
            Result.failure()
        }
    }
}