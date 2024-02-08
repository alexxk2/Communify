package com.example.communify.presentation.contacts.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DefaultItemAnimator
import com.example.communify.R
import com.example.communify.databinding.FragmentContactsBinding
import com.example.communify.presentation.contacts.models.ContactsScreenState
import com.example.communify.presentation.contacts.models.VerticalItemDecorator
import com.example.communify.presentation.contacts.view_model.ContactsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ContactsFragment : Fragment() {
    private var _binding: FragmentContactsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ContactsViewModel by viewModels()
    private lateinit var adapter: ContactsAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentContactsBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setRecyclerView()
        setChipFilter()
        viewModel.getContactsFromDB()

        viewModel.contactsScreenState.observe(viewLifecycleOwner) { state ->
            manageScreenContent(state)
        }

        binding.swipeRefreshRv.setOnRefreshListener {
            viewModel.getContactsFromApi()
        }

    }

    private fun manageScreenContent(state: ContactsScreenState) {
        with(binding) {
            when (state) {
                is ContactsScreenState.Content -> {
                    adapter.submitList(state.contacts)
                    swipeRefreshRv.isRefreshing = false
                    swipeRefreshRv.visibility = View.VISIBLE
                    progressBar.visibility = View.GONE
                    tvError.visibility = View.GONE
                    btnUpdate.visibility = View.GONE
                    tvFilterError.visibility = View.GONE

                }

                ContactsScreenState.Error -> {
                    swipeRefreshRv.isRefreshing = false
                    swipeRefreshRv.visibility = View.GONE
                    progressBar.visibility = View.GONE
                    tvError.visibility = View.VISIBLE
                    btnUpdate.visibility = View.VISIBLE
                    tvFilterError.visibility = View.GONE
                    btnUpdate.setOnClickListener {
                        viewModel.getContactsFromApi()
                    }
                }

                ContactsScreenState.Loading -> {
                    swipeRefreshRv.isRefreshing = true
                    swipeRefreshRv.visibility = View.GONE
                    progressBar.visibility = View.VISIBLE
                    tvError.visibility = View.GONE
                    btnUpdate.visibility = View.GONE
                    tvFilterError.visibility = View.GONE
                }

                ContactsScreenState.Empty -> {
                    swipeRefreshRv.isRefreshing = false
                    swipeRefreshRv.visibility = View.GONE
                    progressBar.visibility = View.GONE
                    tvError.visibility = View.GONE
                    btnUpdate.visibility = View.GONE
                    tvFilterError.visibility = View.VISIBLE
                }
            }
        }

    }


    private fun setRecyclerView() {

        val spaceValue = resources.getDimension(R.dimen.half_default_margin).toInt()
        val gridItemDecorator = VerticalItemDecorator(
            startMargin = 0,
            endMargin = 0,
            interitemMargin = spaceValue
        )
        adapter = ContactsAdapter(
            onItemClickListener = {
                val action = ContactsFragmentDirections.actionContactsFragmentToDetailsFragment(it)
                findNavController().navigate(action)
            },
            onSendEmailClickListener = {
                viewModel.sendMessage(getString(R.string.hello_message), it.user.email)
            },
            onDialClickListener = {
                viewModel.makeADial(it.user.phone)
            }
        )

        binding.rvContacts.adapter = adapter
        binding.rvContacts.setHasFixedSize(true)
        binding.rvContacts.addItemDecoration(gridItemDecorator)

        val itemAnimator = binding.rvContacts.itemAnimator
        if (itemAnimator is DefaultItemAnimator) {
            itemAnimator.supportsChangeAnimations = false
        }
    }


    private fun manageChipCloseIcons(id: Int) {

        with(binding) {
            when (id) {
                chipSeeAll.id -> {
                    chipSeeAll.isCloseIconVisible = false
                    chipFemale.isCloseIconVisible = false
                    chipMale.isCloseIconVisible = false
                }

                chipFemale.id -> {
                    chipSeeAll.isCloseIconVisible = false
                    chipFemale.isCloseIconVisible = true
                    chipMale.isCloseIconVisible = false

                    chipFemale.setOnCloseIconClickListener {
                        clearChipGroup()
                    }
                }

                else -> {
                    chipSeeAll.isCloseIconVisible = false
                    chipFemale.isCloseIconVisible = false
                    chipMale.isCloseIconVisible = true

                    chipMale.setOnCloseIconClickListener {
                        clearChipGroup()
                    }
                }
            }
        }
    }


    private fun clearChipGroup() {
        binding.chipSeeAll.isChecked = true
    }

    private fun setChipFilter() {
        binding.chipGroupFilter.setOnCheckedStateChangeListener { group, _ ->
            when (group.checkedChipId) {
                group.getChildAt(0).id -> {
                    manageChipCloseIcons(binding.chipSeeAll.id)
                    viewModel.makeFilterDefault()

                    lifecycleScope.launch {
                        delay(SCROLL_DELAY)
                        binding.rvContacts.smoothScrollToPosition(0)
                    }


                }

                group.getChildAt(1).id -> {
                    manageChipCloseIcons(binding.chipFemale.id)
                    viewModel.filterContacts(FEMALE_TAG)
                }

                else -> {
                    manageChipCloseIcons(binding.chipMale.id)
                    viewModel.filterContacts(MALE_TAG)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val MALE_TAG = "male"
        const val FEMALE_TAG = "female"
        const val SCROLL_DELAY = 200L
    }
}