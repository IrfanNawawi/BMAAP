package id.heycoding.mylivedata

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import id.heycoding.mylivedata.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var liveDataTimerViewModel: MainViewModel
    private lateinit var activityMainBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)

        liveDataTimerViewModel = ViewModelProvider(this)[MainViewModel::class.java]

        onSubscribe()
    }

    private fun onSubscribe() {
        val elapsedTimeObserver = Observer<Long?> { aLong ->
            val newText = this.resources.getString(R.string.seconds, aLong)
            activityMainBinding.timerTextview.text = newText
        }

        liveDataTimerViewModel.getElapsedTime().observe(this, elapsedTimeObserver)
    }
}