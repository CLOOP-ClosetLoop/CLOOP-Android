package com.example.cloop.presentation.mypage

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.example.cloop.TokenManager
import com.example.cloop.data.remote.RetrofitClient
import com.example.cloop.databinding.FragmentMyPageBinding
import com.example.cloop.presentation.onboarding.SplashActivity
import kotlinx.coroutines.launch

class MyPageFragment : Fragment() {

    private var _binding: FragmentMyPageBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMyPageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnLogout.setOnClickListener {
            performLogout()
        }
    }


    private fun performLogout() {
        val token = TokenManager.getAccessToken(requireContext())

        if (token.isNullOrEmpty()) {
            return
        }

        lifecycleScope.launch {
            try {
                val response = RetrofitClient.provideAuthServiceWithToken(token).logout()
                if (response.isSuccessful) {
                    Toast.makeText(requireContext(), "You have been logged out.", Toast.LENGTH_SHORT).show()

                    TokenManager.clear(requireContext())

                    val intent = Intent(requireActivity(), SplashActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                }
            } catch (e: Exception) {
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}