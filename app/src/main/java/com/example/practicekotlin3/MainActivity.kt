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
        buttonOpen.setOnClickListener { // 열기 버튼
            if (modeChange) { // 비밀 번호 변경 버튼이 활성화 되어 있는 경우
                Toast.makeText(this, "비밀번호 변경 중입니다.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            } else {
                val passwordPreferences = getSharedPreferences("password", Context.MODE_PRIVATE) // SharedPreference 생성
                val inputPassword = "${numberPickerOne.value}${numberPickerTwo.value}${numberPickerThree.value}" // 사용자 입력 값

                if (passwordPreferences.getString("password", "000").equals(inputPassword)) { // 비밀번호가 일치한 경우
                    val intent = Intent(this, DiaryActivity:: class.java) // DiaryActivity 로 이동
                    startActivity(intent)
                } else {
                    showErrorAlert() // 오류 다이얼로그 표시
                }
            }
        }
    }

    private fun initChangeButton() {
        buttonChange.setOnClickListener { // 비밀번호 변경 버튼

            val passwordPreferences = getSharedPreferences("password", Context.MODE_PRIVATE)
            val inputPassword = "${numberPickerOne.value}${numberPickerTwo.value}${numberPickerThree.value}"

            if (modeChange) { // 비밀번호 변경 모드일 경우
                passwordPreferences.edit(true) { // 새로운 입력 값으로 비밀번호 설정
                    this.putString("password", inputPassword)
                }

                modeChange = false // 비밀번호 변경 모드 비활성화
                buttonChange.setBackgroundColor(Color.BLACK) // 비밀번호 변경 버튼 default 색으로 변경
            } else if (passwordPreferences.getString("password", "000").equals(inputPassword)) {
                Toast.makeText(this, "변경할 비밀번호를 설정해주세요.", Toast.LENGTH_SHORT).show()

                modeChange = true // 비밀번호 변경 모드 활성화
                buttonChange.setBackgroundColor(Color.RED) // 비밀번호 변경 버튼 적색으로 변경
            } else {
                showErrorAlert()
            }
        }
    }

    private fun showErrorAlert() { // 오류 알림 다이얼로그
        AlertDialog.Builder(this)
            .setTitle("실패")
            .setMessage("비밀번호가 일치하지 않습니다.")
            .setPositiveButton("확인") { _, _ -> }
            .create()
            .show()
    }
}