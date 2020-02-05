package com.example.macavity.ui.group

import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.macavity.R
import com.example.macavity.data.models.Journey
import com.example.macavity.data.models.User

import com.example.macavity.ui.base.HomeFragment
import kotlinx.android.synthetic.main.fragment_group.*
import org.androidannotations.annotations.AfterViews
import org.androidannotations.annotations.EFragment

@EFragment(resName = "fragment_group")
open class GroupFragment : HomeFragment() {

    private lateinit var vm: GroupViewModel
    //todo: pass user id
    private val membersAdapter =
        MembersAdapter { findNavController().navigate(R.id.action_groupFragment__to_profileFragment_) }

    @AfterViews
    fun afterViews() {
        vm = ViewModelProviders.of(this).get(GroupViewModel::class.java)
        initToolbar()
        initRecyclerView()
        //todo: use real data
        group_stat.text = "56"
    }

    private fun initRecyclerView() {

        members_rv.layoutManager =
            LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        members_rv.adapter = membersAdapter

        //TODO: user real data
        membersAdapter.submitList(dummyData)

    }

    private fun initToolbar() {
        toolbar.setStartIcon(R.drawable.ic_menu)
            .setTitle(getString(R.string.toolbar_title_group))

        toolbar.startIconListener = { openDrawer() }
    }

    //TODO: use real data
    private val dummyData = listOf(
        User(
            "123",
            "John",
            "Main Street 25",
            "Knight Ave 87",
            Pair(12.2, 51.2),
            Pair(21.2, 13.2),
            "https://images.pexels.com/photos/220453/pexels-photo-220453.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500",
            "john.smith@gmail.com",
            "+44123123123",
            true,
            "AH3K24",
            4,
            41,
            23
        ), User(
            "123",
            "Veronica",
            "Main Street 25",
            "Knight Ave 87",
            Pair(12.2, 51.2),
            Pair(21.2, 13.2),
            "https://s3.envato.com/files/236559784/preview.jpg",
            "john.smith@gmail.com",
            "+44123123123",
            true,
            "AH3K24",
            4,
            7,
            3
        ), User(
            "123",
            "Erick",
            "Main Street 25",
            "Knight Ave 87",
            Pair(12.2, 51.2),
            Pair(21.2, 13.2),
            "https://images.pexels.com/photos/736716/pexels-photo-736716.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500",
            "john.smith@gmail.com",
            "+44123123123",
            true,
            "AH3K24",
            4,
            88,
            23
        ), User(
            "123",
            "Rachel",
            "Main Street 25",
            "Knight Ave 87",
            Pair(12.2, 51.2),
            Pair(21.2, 13.2),
            "https://media.istockphoto.com/photos/young-longhaired-smiling-woman-in-white-shirt-picture-id965523228?k=6&m=965523228&s=612x612&w=0&h=qeVmQfjRq1QWxaLdxLdF_IaXahI-dqt9UYcunaHUqA4=",
            "john.smith@gmail.com",
            "+44123123123",
            true,
            "AH3K24",
            4,
            2,
            73
        ),
        User(
            "123",
            "Olivia",
            "Main Street 25",
            "Knight Ave 87",
            Pair(12.2, 51.2),
            Pair(21.2, 13.2),
            "https://www.maybelline.com/~/media/mny/us/face-makeup/modules/pathing-switcher/face-category-pathing-switcher.jpg",
            "john.smith@gmail.com",
            "+44123123123",
            true,
            "AH3K24",
            4,
            20,
            0
        )
    )
}
