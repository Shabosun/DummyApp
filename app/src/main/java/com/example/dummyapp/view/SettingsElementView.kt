package com.example.dummyapp.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.TextView
import com.example.dummyapp.R

class
SettingsElementView @JvmOverloads constructor(
    context : Context,
    attributeSet : AttributeSet? = null,
    defStyleAttr: Int = 0 ,
) : LinearLayout(context, attributeSet, defStyleAttr) {


    private val main_text : TextView
    private val title_text : TextView

    init{
        LayoutInflater.from(context).inflate(R.layout.settings_element_view, this, true)
        main_text = findViewById(R.id.settings_element_textview)
        title_text = findViewById(R.id.settings_element_textview_title)

        val typedArray = context.obtainStyledAttributes(attributeSet,R.styleable.SettingsElementView )
        var text = typedArray.getString(R.styleable.SettingsElementView_main_text)
        var title = typedArray.getString(R.styleable.SettingsElementView_title_text)
        typedArray.recycle()

        //Установка значения в TextView
        main_text.text = text
        title_text.text = title


    }

//    override fun onAttachedToWindow() {
//        super.onAttachedToWindow()
//
//        this.setOnClickListener {
//            Toast.makeText(context, "222", Toast.LENGTH_SHORT).show()
//        }
//    }

    fun setMainText(text : String){
        main_text.text = text
    }











}