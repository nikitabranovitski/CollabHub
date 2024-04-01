package com.bsuir.collabhub.ui

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.bsuir.collabhub.R
import java.util.Random

class StubFragment : Fragment(R.layout.fragment_stub) {

    private val rnd = Random()
    private val color: Int = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256))

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.setBackgroundColor(color)
    }
}