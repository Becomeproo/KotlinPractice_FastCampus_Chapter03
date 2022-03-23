package com.example.practicekotlin3

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.EditText
import androidx.core.content.edit
import androidx.core.widget.addTextChangedListener

class DiaryActivity : AppCompatActivity() {

    private val editTextDiary: EditText by lazy {
        findViewById(R.id.editTextDiary)
    }

    private val handler = Handler(Looper.getMainLooper())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_diary)

        val detailPreferences = getSharedPreferences("diary", Context.MODE_PRIVATE)
        editTextDiary.setText(detailPreferences.getString("detail", ""))


        val runnable = Runnable { // 스레드
            getSharedPreferences("diary", Context.MODE_PRIVATE).edit { // 백그라운드에서 실행하기 때문에 인자인 commit은 false로 설정
                putString("detail", editTextDiary.text.toString())
            }
        }

        editTextDiary.addTextChangedListener { // 텍스트가 실행될 때마다 해당 람다 실행
            handler.removeCallbacks(runnable) // 이전에 실행된 스레드가 있다면 삭제
            handler.postDelayed(runnable, 500) // 0.5초 이후 스레드 실행
        }
    }
}