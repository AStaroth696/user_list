package com.example.userlist.view

import android.animation.LayoutTransition
import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.core.view.setPadding
import com.example.userlist.R
import com.example.userlist.util.dpToPixels

class SectionedLayout @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyle: Int = 0
) : LinearLayout(context, attributeSet, defStyle) {

    private val sectionKeyColor = ContextCompat.getColor(context, R.color.colorSectionKey)
    private val titleTextSize = 18f
    private val sectionTextSize = 14f

    init {
        orientation = VERTICAL
        layoutTransition = LayoutTransition()
    }

    fun setSections(sections: List<Section>) {
        val sectionIterator = sections.iterator()
        while (sectionIterator.hasNext()) {
            val sectionData = sectionIterator.next()
            val section = LinearLayout(context)
            section.layoutParams =
                LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
            section.orientation = VERTICAL
            section.setPadding(8.dpToPixels())
            section.addView(TextView(context).apply {
                text = sectionData.title
                setTextColor(Color.BLACK)
                textSize = titleTextSize
            })
            val sectionBody = createSectionBody(sectionData.data)
            section.setOnClickListener {
                sectionBody.isVisible = !sectionBody.isVisible
            }
            section.addView(sectionBody)
            section.layoutTransition = LayoutTransition()
            addView(section)
            if (sectionIterator.hasNext()) {
                addView(createSectionDivider())
            }
        }
    }

    private fun createSectionBody(sectionBody: List<Pair<String, String?>>): View {
        val sectionBodyContainer = LinearLayout(context)
        sectionBodyContainer.layoutParams =
            LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
        sectionBodyContainer.orientation = VERTICAL
        sectionBodyContainer.setPadding(8.dpToPixels())
        sectionBody.forEach {
            val section = LinearLayout(context)
            section.layoutParams =
                LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
            section.orientation = HORIZONTAL
            val key = TextView(context).apply {
                text = it.first
                layoutParams = LayoutParams(0, LayoutParams.WRAP_CONTENT, 0.5f)
                setTextColor(sectionKeyColor)
                textSize = sectionTextSize
            }
            section.addView(key)
            val value = TextView(context).apply {
                text = it.second
                layoutParams = LayoutParams(0, LayoutParams.WRAP_CONTENT, 1f)
                setTextColor(Color.BLACK)
                textSize = sectionTextSize
            }
            section.addView(value)
            sectionBodyContainer.addView(section)
        }
        sectionBodyContainer.isVisible = false
        return sectionBodyContainer
    }

    private fun createSectionDivider() = View(context).apply {
        layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, 1.dpToPixels())
        setBackgroundColor(Color.BLACK)
    }

    data class Section(
        val title: String,
        val data: List<Pair<String, String?>>
    )

}