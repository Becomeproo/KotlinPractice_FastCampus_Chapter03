package com.example.practicekotlin3

import android.content.Context
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.NumberPicker
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.edit

class MainActivity : AppCompatActivity() {

    private val numberPickerOne: NumberPicker by lazy {
        findViewById<NumberPicker>(R.id.numberPickerOne)
            .apply {
                minValue = 0
                maxValue = 9
            }
    }

    private val numberPickerTwo: NumberPicker by lazy {
        findViewById<NumberPicker>(R.id.numberPickerTwo)
            .apply {
                minValue = 0
                maxValue = 9
            }
    }

    private val numberPickerThree: NumberPicker by lazy {
        findViewById<NumberPicker>(R.id.numberPickerThree)
            .apply {
                minValue = 0
                maxValue = 9
            }
    }

    private val buttonOpen: AppCompatButton by lazy {
        findViewById(R.id.buttonOpen)
    }

    private val buttonChange: AppCompatButton by lazy {
        findViewById(R.id.buttonChange)
    }

    private var modeChange = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        numberPickerOne
        numberPickerTwo
        numberPickerThree

        initOpenButton()
        initChangeButton()
    }

    private fun initOpenButton() {
        buttonOpen.setOnClickListener {
            if (modeChange) {
                Toast.makeText(this, "비밀번호 변경 중입니다.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            } else {
                val passwordPreferences = getSharedPreferences("password", Context.MODE_PRIVATE)
                val inputPassword = "${numberPickerOne.value}${numberPickerTwo.value}${numberPickerThree.value}"

                if (passwordPreferences.getString("password", "000").equals(inputPassword)) {
                    val intent = Intent(this, DiaryActivity:: class.java)
                    startActivity(intent)
                } else {
                    showErrorAlert()
                }
            }
        }
    }

    private fun initChangeButton() {
        buttonChange.setOnClickListener {

            val passwordPreferences = getSharedPreferences("password", Context.MODE_PRIVATE)
            val inputPassword = "${numberPickerOne.value}${numberPickerTwo.value}${numberPickerThree.value}"

            if (modeChange) {
                passwordPreferences.edit(true) {
                    this.putString("password", inputPassword)
                }

                modeChange = false
                buttonChange.setBackgroundColor(Color.BLACK)
            } else if (passwordPreferences.getString("password", "000").equals(inputPassword)) {
                Toast.makeText(this, "변경할 비밀번호를 설정해주세요.", Toast.LENGTH_SHORT).show()

                modeChange = true
                buttonChange.setBackgroundColor(Color.RED)
            } else {
                showErrorAlert()
            }
        }
    }

    private fun showErrorAlert() {
        AlertDialog.Builder(this)
            .setTitle("실패")
            .setMessage("비밀번호가 일치하지 않습니다.")
            .setPositiveButton("확인") { _, _ -> }
            .create()
            .show()
    }
}