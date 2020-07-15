package com.example.myapplication.youtube

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.lifecycle.lifecycleScope
import com.example.myapplication.R
import kotlinx.android.synthetic.main.fragment_second.*
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment : Fragment(), MotionLayout.TransitionListener {
    private var userInteract = false
    private var hideJob: Job? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_second, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        player.addTransitionListener(this)

        setListenerWiths(button, button2, button3, button4) {
            hide()
        }
        time.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {

            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
                Log.d("Teste", "haha")
                userInteract = true
            }

            override fun onStopTrackingTouch(p0: SeekBar?) {
                userInteract = false
                hide()
            }
        })
    }

    override fun onTransitionTrigger(p0: MotionLayout?, p1: Int, p2: Boolean, p3: Float) {}
    override fun onTransitionStarted(p0: MotionLayout?, p1: Int, p2: Int) {}
    override fun onTransitionChange(p0: MotionLayout?, p1: Int, p2: Int, p3: Float) {}
    override fun onTransitionCompleted(p0: MotionLayout?, p1: Int) {
        if (p1 == R.id.end) {
            hide()
        }
    }

    fun hide() {
        hideJob?.cancel()
        hideJob = lifecycleScope.launch {
            delay(1300)
            if (!userInteract)
            player.transitionToStart()
        }
    }


    fun setListenerWiths(vararg views: View, clickListener: (View) -> Unit) {
        views.forEach {
            it.setOnClickListener { v ->
                clickListener.invoke(v)
            }
        }
    }
}