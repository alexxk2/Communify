package com.example.communify.presentation.profile.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.communify.R
import com.example.communify.databinding.FragmentProfileBinding
import com.example.communify.presentation.login.ui.LoginFragment
import com.example.communify.presentation.profile.models.ProfileScreenState
import com.example.communify.presentation.profile.view_model.ProfileViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ProfileFragment : Fragment() {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ProfileViewModel by viewModels()
    private lateinit var pickMedia: ActivityResultLauncher<PickVisualMediaRequest>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getProfile()
        viewModel.profileScreenState.observe(viewLifecycleOwner) { state ->
            manageScreenContent(state)
        }

        viewModel.isUpdateAvailable.observe(viewLifecycleOwner) {
            binding.btnUpdate.isEnabled = it
        }


        pickMedia = registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->

            uri?.let {

                Glide.with(requireContext())
                    .load(uri)
                    .circleCrop()
                    .into(binding.ivAvatar)

                viewModel.saveImageToPrivateStorage(uri)
            }
        }

        binding.ivAvatar.setOnClickListener {
            pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        }


        binding.etLogin.addTextChangedListener {
            validateInput()
        }

        binding.etPassword.addTextChangedListener {
            validateInput()
        }

        binding.btnUpdate.setOnClickListener {
            viewModel.updateProfile(
                binding.etLogin.text.toString(),
                binding.etPassword.text.toString()
            )
            showSnackBar(getString(R.string.profile_updated))
            validateInput()
        }

        binding.tvDeleteAccount.setOnClickListener {
            showDeleteProfileConfirmationDialog()
        }

    }

    private fun validateInput() {
        viewModel.validateChangesForUpdate(
            binding.etLogin.text.toString(),
            binding.etPassword.text.toString()
        )
    }

    private fun manageScreenContent(state: ProfileScreenState) {
        with(binding) {
            when (state) {
                is ProfileScreenState.Authorized -> {
                    ivAvatar.visibility = View.VISIBLE
                    tvName.visibility = View.VISIBLE
                    tiLogin.visibility = View.VISIBLE
                    tiPassword.visibility = View.VISIBLE
                    btnUpdate.visibility = View.VISIBLE
                    tvDeleteAccount.visibility = View.VISIBLE
                    clUnauthorized.visibility = View.GONE

                    tvName.text = state.credentials.name
                    etPassword.setText(state.credentials.password)
                    etLogin.setText(state.credentials.login)

                    Glide.with(requireContext())
                        .load(state.credentials.userImage)
                        .circleCrop()
                        .into(binding.ivAvatar)

                }

                ProfileScreenState.Unauthorized -> {
                    ivAvatar.visibility = View.GONE
                    tvName.visibility = View.GONE
                    tiLogin.visibility = View.GONE
                    tiPassword.visibility = View.GONE
                    btnUpdate.visibility = View.GONE
                    tvDeleteAccount.visibility = View.GONE
                    clUnauthorized.visibility = View.VISIBLE

                    btnLogin.setOnClickListener {
                        val action =
                            ProfileFragmentDirections.actionProfileFragmentToLoginFragment(true)
                        findNavController().navigate(action)
                    }

                    btnJoin.setOnClickListener {
                        val action =
                            ProfileFragmentDirections.actionProfileFragmentToLoginFragment(false)
                        findNavController().navigate(action)
                    }
                }
            }
        }

    }


    private fun showDeleteProfileConfirmationDialog() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(getString(R.string.delete_profile))
            .setBackground(AppCompatResources.getDrawable(requireContext(),R.drawable.rounder_background))
            .setMessage(R.string.are_you_sure_delete_profile)
            .setNegativeButton(R.string.cancel) { _, _ -> }
            .setPositiveButton(R.string.delete) { _, _ ->
                viewModel.deleteAccount()
                val action = ProfileFragmentDirections.actionProfileFragmentToStartFragment()
                findNavController().navigate(action)
            }
            .show()
    }

    private fun showSnackBar(message: String){
        Snackbar.make(binding.root, message, LoginFragment.FAILURE_DURATION)
            .setActionTextColor(resources.getColor(R.color.white, null))
            .setAction(getString(R.string.ok)){}
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}