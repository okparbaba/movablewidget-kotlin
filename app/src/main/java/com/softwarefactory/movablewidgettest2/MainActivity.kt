package com.softwarefactory.movablewidgettest2

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener
import android.view.ViewGroup.LayoutParams
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.PopupWindow
import android.widget.Spinner

class MainActivity : Activity() {

    internal var DayOfWeek = arrayOf("Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnOpenPopup = findViewById<View>(R.id.openpopup) as Button
        btnOpenPopup.setOnClickListener {
             val layoutInflater = baseContext
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
                val popupView = layoutInflater.inflate(R.layout.popup, null)
                val popupWindow = PopupWindow(
                        popupView, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)

                val btnDismiss = popupView.findViewById<View>(R.id.dismiss) as Button

                val popupSpinner = popupView.findViewById<View>(R.id.popupspinner) as Spinner

                val adapter = ArrayAdapter(this@MainActivity,
                        android.R.layout.simple_spinner_item, DayOfWeek)
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                popupSpinner.adapter = adapter

                btnDismiss.setOnClickListener {
                        popupWindow.dismiss()
                }

                popupWindow.showAsDropDown(btnOpenPopup, 50, -30)

                popupView.setOnTouchListener(object : OnTouchListener {
                    var orgX: Int = 0
                    var orgY: Int = 0
                    var offsetX: Int = 0
                    var offsetY: Int = 0

                    override fun onTouch(v: View, event: MotionEvent): Boolean {
                        when (event.action) {
                            MotionEvent.ACTION_DOWN -> {
                                orgX = event.x.toInt()
                                orgY = event.y.toInt()
                            }
                            MotionEvent.ACTION_MOVE -> {
                                offsetX = event.rawX.toInt() - orgX
                                offsetY = event.rawY.toInt() - orgY
                                popupWindow.update(offsetX, offsetY, -1, -1, true)
                            }
                        }
                        return true
                    }
                })
            }


    }

}
