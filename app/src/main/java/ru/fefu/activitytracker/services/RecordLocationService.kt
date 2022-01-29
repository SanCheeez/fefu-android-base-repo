package ru.fefu.activitytracker.services

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.os.Looper
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import ru.fefu.activitytracker.DataBase.ActivityItemEntity
import ru.fefu.activitytracker.DataBase.App
import ru.fefu.activitytracker.DataBase.SerialiseClass
import ru.fefu.activitytracker.R
import ru.fefu.activitytracker.fragments.StartActivityOnMapFragment

class RecordLocationService : Service() {
    companion object {
        private const val TAG = "ForegroundService"
        private const val CHANNEL_ID = "foreground_service_id"
        private const val EXTRA_ID = "id"

        const val ACTION_START = "start"
        const val ACTION_CANCEL = "cancel"

        val locationRequest: LocationRequest
            get() = LocationRequest.create()
                .setInterval(10000L)
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setSmallestDisplacement(20f)

        fun startForeground(context: Context, id: Int) {
            val intent = Intent(context, RecordLocationService::class.java)
            intent.putExtra(EXTRA_ID, id)
            intent.action = ACTION_START
            ContextCompat.startForegroundService(context, intent)
        }
    }

    private val fusedLocationClient by lazy {
        LocationServices.getFusedLocationProviderClient(this)
    }

    private var locationCallback: LocationCallback? = null

    override fun onBind(p0: Intent?): IBinder? = null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId)
        if (intent?.action == ACTION_CANCEL) {
            FinishActivity()
            stopLocationUpdates()
            stopForeground(true)
            stopSelf()
            return START_NOT_STICKY
        } else if (intent?.action == ACTION_START) {
            startLocationUpdates(intent.getIntExtra(EXTRA_ID, -1))
            return START_REDELIVER_INTENT
        }
        return START_NOT_STICKY
    }

    override fun onDestroy() {
        locationCallback?.let { fusedLocationClient.removeLocationUpdates(it) }
        super.onDestroy()
    }

    @SuppressLint("MissingPermission")
    private fun startLocationUpdates(id: Int) {
        if (id == -1) {
            stopSelf()
        }
        locationCallback?.let { fusedLocationClient.removeLocationUpdates(it) }
        ActivityLocationCallback().apply {
            fusedLocationClient.requestLocationUpdates(
                locationRequest,
                this,
                Looper.getMainLooper()
            )
            locationCallback = this
        }
        showNotification()
    }

    private fun stopLocationUpdates() {
        locationCallback?.let { fusedLocationClient.removeLocationUpdates(it) }
    }

    private fun showNotification() {
        createChannelIfNeeded()
        val pendingIntent = PendingIntent.getActivity(
            this,
            0,
            Intent(this, StartActivityOnMapFragment::class.java),
            PendingIntent.FLAG_IMMUTABLE
        )
        val cancelIntent = Intent(this, RecordLocationService::class.java).apply {
            action = ACTION_CANCEL
        }
        val cancelPendingIntent = PendingIntent.getService(
            this,
            1,
            cancelIntent,
            PendingIntent.FLAG_IMMUTABLE
        )
        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("Hello")
            .setContentText("Tracking your activity")
            .setSmallIcon(R.drawable.ic_baseline_add_24)
            .setContentIntent(pendingIntent)
            .addAction(R.drawable.ic_baseline_stop_24, "Stop", cancelPendingIntent)
            .build()
        startForeground(1, notification)
    }

    private fun createChannelIfNeeded() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "Default channel",
                NotificationManager.IMPORTANCE_LOW
            )
            val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(channel)
        }
    }

    inner class ActivityLocationCallback() : LocationCallback() {
        override fun onLocationResult(p0: LocationResult) {
            val lastLocation = p0.lastLocation
            val activity = App.INSTANCE.db.activityDao().getLast()
            var list = activity.coordinates?.let { SerialiseClass().listDecode(it) }
            var activityUpdate: ActivityItemEntity? = null
            if (list != null) {
                list = list.toMutableList()
                list.add(Pair(lastLocation.latitude, lastLocation.longitude))
                activityUpdate = ActivityItemEntity(
                    id = activity.id,
                    type = activity.type,
                    date_start = activity.date_start,
                    date_finish = null,
                    coordinates = SerialiseClass().listEncode(list)
                )
            } else {
                var list_ = mutableListOf<Pair<Double, Double>>()
                list_.add(Pair(lastLocation.latitude, lastLocation.longitude))
                activityUpdate = ActivityItemEntity(
                    id = activity.id,
                    type = activity.type,
                    date_start = activity.date_start,
                    date_finish = null,
                    coordinates = SerialiseClass().listEncode(list_)
                )
            }
            App.INSTANCE.db.activityDao().updateLast(activityUpdate)
        }
    }

    private fun FinishActivity() {
        val activity = App.INSTANCE.db.activityDao().getLast()
        var activityUpdate = ActivityItemEntity(
            id = activity.id,
            type = activity.type,
            date_start = activity.date_start,
            date_finish = System.currentTimeMillis(),
            coordinates = activity.coordinates
        )
        App.INSTANCE.db.activityDao().updateLast(activityUpdate)
    }
}