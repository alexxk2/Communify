package com.example.communify.presentation.details.ui

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.communify.R
import com.example.communify.databinding.FragmentDetailsBinding
import com.example.communify.domain.models.Contact
import com.example.communify.presentation.details.view_model.DetailsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailsFragment : Fragment() {
    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: DetailsViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            val contact = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                it.getParcelable(CONTACT, Contact::class.java)!!
            } else {
                it.getParcelable(CONTACT)!!
            }
            viewModel.setContact(contact)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailsBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        viewModel.contact.observe(viewLifecycleOwner){contact->
            bind(contact)
        }

        binding.btnBack.setOnClickListener {
            findNavController().navigateUp()
        }

    }

    private fun bind(contact: Contact){
        with(binding){
            val topImages = listOf(
                AppCompatResources.getDrawable(requireContext(),R.drawable.green1),
                AppCompatResources.getDrawable(requireContext(),R.drawable.green2),
                AppCompatResources.getDrawable(requireContext(),R.drawable.green3),
                AppCompatResources.getDrawable(requireContext(),R.drawable.green4),
                AppCompatResources.getDrawable(requireContext(),R.drawable.green5),
                AppCompatResources.getDrawable(requireContext(),R.drawable.green6),
                AppCompatResources.getDrawable(requireContext(),R.drawable.green7),

            )
            ivTopImage.setImageDrawable(topImages.random())

            Glide.with(requireContext())
                .load(contact.user.picture.large)
                .circleCrop()
                .into(ivAvatar)

            tvName.text = contact.user.name.first.replaceFirstChar (Char::titlecase)
            tvLastName.text = contact.user.name.last.replaceFirstChar (Char::titlecase)
            tvPhone.text = contact.user.phone
            tvEmail.text = contact.user.email

            val street = contact.user.location.street.replaceFirstChar (Char::titlecase)
            val city = contact.user.location.city.replaceFirstChar (Char::titlecase)
            val state = contact.user.location.state.replaceFirstChar (Char::titlecase)
            val fullAddress = "$street, $city, $state"

            tvAddress.text = fullAddress

            btnEmail.setOnClickListener {
                viewModel.sendMessage(getString(R.string.hello_message),contact.user.email)
            }

            btnPhone.setOnClickListener {
                viewModel.makeADial(contact.user.phone)
            }

            btnAddress.setOnClickListener {
                viewModel.openMap(contact.user.location.city)
            }

            btnCopyEmail.setOnClickListener {
                requireContext().copyToClipboard(contact.user.email)
                showCopyToast(getString(R.string.email_copied))
            }

            btnCopyPhone.setOnClickListener {
                requireContext().copyToClipboard(contact.user.phone)
                showCopyToast(getString(R.string.phone_copied))
            }

            btnCopyAddress.setOnClickListener {
                requireContext().copyToClipboard(fullAddress)
                showCopyToast(getString(R.string.address_copied))
            }
        }
    }

    private fun showCopyToast(text: String){
        Toast.makeText(requireContext(),text,Toast.LENGTH_SHORT).show()
    }


    private fun Context.copyToClipboard(text: String){
        val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        clipboard.setPrimaryClip(ClipData.newPlainText("",text))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object{
        const val CONTACT = "contact"
    }


}