package com.example.communify.presentation.login.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.communify.R
import com.example.communify.databinding.FragmentLoginBinding
import com.example.communify.domain.models.AuthResult
import com.example.communify.presentation.login.view_model.LoginViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private val viewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            val isLogin = it.getBoolean(IS_LOGIN)
            viewModel.setScreenState(isLogin)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.isLoginScreen.observe(viewLifecycleOwner) {
            manageScreenContent(it)
        }

        viewModel.isAllFieldsValid.observe(viewLifecycleOwner){
            binding.btnLogin.isEnabled = it
        }

        viewModel.authResult.observe(viewLifecycleOwner){result->
            manageAuthResult(result)
        }

        binding.btnBack.setOnClickListener {
            findNavController().navigateUp()
        }

    }

    private fun validateSignInInput() {
        with(binding) {
            viewModel.isAllInputsNotEmpty(
                etFirstName.text,
                etLogin.text,
                etPassword.text
            )
        }
    }

    private fun validateLoginInput() {
        with(binding) {
            viewModel.isAllInputsNotEmpty(
                etLogin.text,
                etPassword.text
            )
        }
    }

    private fun manageAuthResult(result: AuthResult){
        when(result){
            is AuthResult.Failure -> {
                showSnackBar(result.message)
            }
            AuthResult.Success -> {
                val action = LoginFragmentDirections.actionLoginFragmentToContactsFragment()
                findNavController().navigate(action)
            }
        }
    }

    private fun showSnackBar(message: String){
        Snackbar.make(binding.container,message, FAILURE_DURATION)
            .setActionTextColor(resources.getColor(R.color.white, null))
            .setAction(getString(R.string.ok)){}
            .show()
    }

    private fun manageScreenContent(isLoginState: Boolean) {
        with(binding) {
            if (isLoginState) {
                tvTitle.text = getString(R.string.entrance)
                tiFirstName.visibility = View.GONE
                btnLogin.text = getString(R.string.enter)

                etFirstName.addTextChangedListener {
                    validateLoginInput()
                }

                etLogin.addTextChangedListener {
                    validateLoginInput()
                }

                etPassword.addTextChangedListener {
                    validateLoginInput()
                }

                btnLogin.setOnClickListener {
                    viewModel.login(etLogin.text.toString(), etPassword.text.toString())
                }
            } else {
                tvTitle.text = getString(R.string.registration)
                tiFirstName.visibility = View.VISIBLE
                btnLogin.text = getString(R.string.register)

                etFirstName.addTextChangedListener {
                    validateSignInInput()
                }

                etLogin.addTextChangedListener {
                    validateSignInInput()
                }

                etPassword.addTextChangedListener {
                    validateSignInInput()
                }

                btnLogin.setOnClickListener {
                    viewModel.signIn(
                        etFirstName.text.toString(),
                        etLogin.text.toString(),
                        etPassword.text.toString()
                    )
                }
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val IS_LOGIN = "isLogin"
        const val FAILURE_DURATION = 3000
    }
}