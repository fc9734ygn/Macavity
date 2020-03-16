package com.example.macavity.ui.group

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.macavity.R
import com.example.macavity.data.models.local.Group
import com.example.macavity.data.models.local.User
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


    private val groupObserver = Observer<Group> {
        group_stat.text = it.journeysCompleted.toString()
    }

    private val membersObserver = Observer<List<User>> {
        if (it.isNotEmpty()) {
            membersAdapter.submitList(it)
        } else {
            //todo: Show no members view
        }
    }

    @AfterViews
    fun afterViews() {
        initToolbar()
        initRecyclerView()
        initViewModel()
    }

    private fun initViewModel() {
        vm = ViewModelProviders.of(this, viewModelFactory).get(GroupViewModel::class.java)
        vm.group.observe(this, groupObserver)
        vm.members.observe(this, membersObserver)
        vm.fetchMembers()
    }

    private fun initRecyclerView() {
        members_rv.layoutManager =
            LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        members_rv.adapter = membersAdapter
    }

    private fun initToolbar() {
        toolbar.setStartIcon(R.drawable.ic_menu)
            .setTitle(getString(R.string.toolbar_title_group))

        toolbar.startIconListener = { openDrawer() }
    }
}
