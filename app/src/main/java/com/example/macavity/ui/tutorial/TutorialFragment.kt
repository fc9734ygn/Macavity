package com.example.macavity.ui.tutorial

import android.view.View
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.example.macavity.R
import com.example.macavity.ui.auth.AuthFragment
import kotlinx.android.synthetic.main.fragment_tutorial.*
import org.androidannotations.annotations.AfterViews
import org.androidannotations.annotations.Click
import org.androidannotations.annotations.EFragment

@EFragment(resName = "fragment_tutorial")
open class TutorialFragment : AuthFragment() {

    lateinit var vm: TutorialViewModel
    private val tutorialPagesAdapter = TutorialPagesAdapter()

    data class TutorialPage(val textResource: Int, val imageResource: Int)

    private val tutorialPages = listOf(
        TutorialPage(R.string.tutorial_text_page_1, R.drawable.ic_tutorial1),
        TutorialPage(R.string.tutorial_text_page_2, R.drawable.ic_tutorial2),
        TutorialPage(R.string.tutorial_text_page_3, R.drawable.ic_tutorial3),
        TutorialPage(R.string.tutorial_text_page_4, R.drawable.ic_tutorial4),
        TutorialPage(R.string.tutorial_text_page_5, R.drawable.ic_tutorial5)
    )

    @AfterViews
    fun afterViews() {
        vm = ViewModelProviders.of(this, viewModelFactory).get(TutorialViewModel::class.java)
        initToolbar()
        initViewPager()
    }

    private fun initViewPager() {
        pager.adapter = tutorialPagesAdapter
        tutorialPagesAdapter.setItem(tutorialPages)
        indicator.setViewPager(pager)
        pager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels)
                button.visibility =
                    if (position == tutorialPages.size - 1) View.VISIBLE else View.INVISIBLE
            }
        })
    }

    private fun initToolbar() {
        toolbar.setTitle(getString(R.string.tutorial_toolbar_title))
    }

    @Click(resName = ["button"])
    fun goToCreateProfile(){
        findNavController().navigate(R.id.action_tutorialFragment__to_createGroupFragment_)
    }
}

