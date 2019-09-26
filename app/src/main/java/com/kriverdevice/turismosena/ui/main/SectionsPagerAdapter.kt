package com.kriverdevice.turismosena.ui.main

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.kriverdevice.turismosena.R

private val TAB_TITLES = arrayOf(
    R.string.tab_text_sitios,
    R.string.tab_text_hoteles,
    R.string.tab_text_operadores
)

class SectionsPagerAdapter(
    private val context: Context,
    fm: FragmentManager,
    private val fragments: ArrayList<Fragment>
) : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        return fragments.get(position)
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return context.resources.getString(TAB_TITLES[position])
    }

    override fun getCount(): Int {
        // Show 2 total pages.
        return fragments.count()
    }
}