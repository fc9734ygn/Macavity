package com.example.macavity.ui.group

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.macavity.R
import com.example.macavity.data.models.local.Group
import com.example.macavity.data.models.local.User
import com.example.macavity.ui.home.HomeFragment
import kotlinx.android.synthetic.main.fragment_group.*
import org.androidannotations.annotations.AfterViews
import org.androidannotations.annotations.EFragment

@EFragment(resName = "fragment_group")
open class GroupFragment : HomeFragment() {

    private lateinit var vm: GroupViewModel

    private val membersAdapter =
        MembersAdapter {
            val action =
                GroupFragment_Directions.actionGroupFragmentToProfileFragment(it.id)
            findNavController().navigate(action)
        }

    private val groupObserver = Observer<Group> {
        group_stat.text = it.journeysCompleted.toString()
    }

    private val membersObserver = Observer<List<User>> {
        if (!it.isNullOrEmpty()) {
            membersAdapter.submitList(it)
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
        toolbar.setStartIcon(R.drawable.ic_arrow_back)
            .setTitle(getString(R.string.toolbar_title_group))

        toolbar.startIconListener = { requireActivity().onBackPressed() }
    }
}
