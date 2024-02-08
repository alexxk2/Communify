package com.example.communify.presentation.contacts.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.communify.databinding.ContactItemBinding
import com.example.communify.domain.models.Contact

class ContactsAdapter(
    private val onItemClickListener: (product: Contact) -> Unit,
    private val onSendEmailClickListener: (product: Contact) -> Unit,
    private val onDialClickListener: (product: Contact) -> Unit
) : ListAdapter<Contact, ContactsAdapter.ContactViewHolder>(DiffCallBack) {


    inner class ContactViewHolder(val binding: ContactItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(
            item: Contact,
            onItemClickListener: (product: Contact) -> Unit,
            onSendEmailClickListener: (product: Contact) -> Unit,
            onDialClickListener: (product: Contact) -> Unit
        ) {

            binding.root.setOnClickListener { onItemClickListener(item) }

            Glide.with(binding.root)
                .load(item.user.picture.large)
                .circleCrop()
                .into(binding.ivAvatar)


            val firstName = item.user.name.first.replaceFirstChar(Char::titlecase)
            val lastName = item.user.name.last.replaceFirstChar(Char::titlecase)
            val fullName = "$firstName $lastName"
            binding.tvName.text = fullName
            binding.tvCity.text = item.user.location.city.replaceFirstChar(Char::titlecase)

            binding.btnPhone.setOnClickListener { onDialClickListener(item) }
            binding.btnEmail.setOnClickListener { onSendEmailClickListener(item) }
        }
    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ContactsAdapter.ContactViewHolder {

        val inflater = LayoutInflater.from(parent.context)
        val binding = ContactItemBinding.inflate(inflater,parent,false)
        return ContactViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ContactsAdapter.ContactViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(
            item,
            onItemClickListener,
            onSendEmailClickListener,
            onDialClickListener
        )
    }

    companion object {
        val DiffCallBack = object : DiffUtil.ItemCallback<Contact>() {

            override fun areItemsTheSame(oldItem: Contact, newItem: Contact): Boolean {
                return oldItem.user.userId == oldItem.user.userId
            }

            override fun areContentsTheSame(oldItem: Contact, newItem: Contact): Boolean {
                return oldItem.user == newItem.user
            }
        }
    }
}