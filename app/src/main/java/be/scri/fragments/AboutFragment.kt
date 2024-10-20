package be.scri.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import be.scri.R
import be.scri.activities.MainActivity
import be.scri.databinding.FragmentAboutBinding
import be.scri.helpers.CustomAdapter
import be.scri.models.ItemsViewModel

class AboutFragment : Fragment() {
    private lateinit var binding: FragmentAboutBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentAboutBinding.inflate(inflater, container, false)
        val callback =
            requireActivity().onBackPressedDispatcher.addCallback(this) {
                getParentFragmentManager().popBackStack()
            }
        callback.isEnabled = true
        (requireActivity() as MainActivity).setActionBarTitle(R.string.app_about_title)
        (requireActivity() as MainActivity).unsetActionBarLayoutMargin()
        (requireActivity() as MainActivity).setActionBarButtonInvisible()
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerViews()
    }

    private fun setupRecyclerViews() {
        val recyclerView1 = binding.recycleView2
        recyclerView1.layoutManager = LinearLayoutManager(context)
        recyclerView1.adapter = CustomAdapter(getFirstRecyclerViewData(), requireContext())
        recyclerView1.suppressLayout(true)

        val recyclerView2 = binding.recycleView
        recyclerView2.layoutManager = LinearLayoutManager(context)
        recyclerView2.adapter = CustomAdapter(getSecondRecyclerViewData(), requireContext())
        recyclerView2.suppressLayout(true)

        val recyclerView3 = binding.recycleView3
        recyclerView3.layoutManager = LinearLayoutManager(context)
        recyclerView3.adapter = CustomAdapter(getThirdRecyclerViewData(), requireContext())
        recyclerView3.suppressLayout(true)
    }

    private fun getFirstRecyclerViewData(): List<Any> =
        listOf(
            ItemsViewModel(
                image = R.drawable.github_logo,
                textResId = R.string.app_about_community_github,
                image2 = R.drawable.external_link,
                url = "https://github.com/scribe-org/Scribe-Android",
                activity = null,
                action = null,
            ),
            ItemsViewModel(
                image = R.drawable.matrix_icon,
                textResId = R.string.app_about_community_matrix,
                image2 = R.drawable.external_link,
                url = "https://matrix.to/%23/%23scribe_community:matrix.org",
                activity = null,
                action = null,
            ),
            ItemsViewModel(
                image = R.drawable.mastodon_svg_icon,
                textResId = R.string.app_about_community_mastodon,
                image2 = R.drawable.external_link,
                url = "https://wikis.world/@scribe",
                activity = null,
                action = null,
            ),
            ItemsViewModel(
                image = R.drawable.share_icon,
                textResId = R.string.app_about_community_share_scribe,
                image2 = R.drawable.external_link,
                url = null,
                activity = null,
                action = ::shareScribe,
            ),
            ItemsViewModel(
                image = R.drawable.wikimedia_logo_black,
                textResId = R.string.app_about_community_wikimedia,
                image2 = R.drawable.right_arrow,
                url = null,
                activity = null,
                action = ::loadWikimediaScribeFragment,
            ),
        )

    private fun getSecondRecyclerViewData(): List<Any> =
        listOf(
            ItemsViewModel(
                image = R.drawable.star,
                textResId = R.string.app_about_feedback_rate_scribe,
                image2 = R.drawable.external_link,
                url = null,
                activity = null,
                action = null,
            ),
            ItemsViewModel(
                image = R.drawable.bug_report_icon,
                textResId = R.string.app_about_feedback_bug_report,
                image2 = R.drawable.external_link,
                url = "https://github.com/scribe-org/Scribe-Android/issues",
                activity = null,
                action = null,
            ),
            ItemsViewModel(
                image = R.drawable.mail_icon,
                textResId = R.string.app_about_feedback_email,
                image2 = R.drawable.external_link,
                url = null,
                activity = null,
                action = ::sendEmail,
            ),
            ItemsViewModel(
                image = R.drawable.bookmark_icon,
                textResId = R.string.app_about_feedback_version,
                image2 = R.drawable.right_arrow,
                url = null,
                activity = null,
                action = null,
            ),
            ItemsViewModel(
                image = R.drawable.light_bulb_icon,
                textResId = R.string.app_about_feedback_app_hints,
                image2 = R.drawable.counter_clockwise_icon,
                url = null,
                activity = null,
                action = null,
            ),
        )

    private fun getThirdRecyclerViewData(): List<ItemsViewModel> =
        listOf(
            ItemsViewModel(
                image = R.drawable.shield_lock,
                R.string.app_about_legal_privacy_policy,
                image2 = R.drawable.right_arrow,
                url = null,
                activity = null,
                action = ::loadPrivacyPolicyFragment,
            ),
            ItemsViewModel(
                image = R.drawable.license_icon,
                R.string.app_about_legal_third_party,
                image2 = R.drawable.right_arrow,
                url = null,
                activity = null,
                action = ::loadThirdPartyLicensesFragment,
            ),
        )

    private fun shareScribe() {
        val sharingIntent =
            Intent(Intent.ACTION_SEND).apply {
                type = "text/plain"
                putExtra(Intent.EXTRA_TEXT, "https://github.com/scribe-org/Scribe-Android")
            }
        startActivity(Intent.createChooser(sharingIntent, "Share via"))
    }

    private fun sendEmail() {
        val intent =
            Intent(Intent.ACTION_SEND).apply {
                putExtra(Intent.EXTRA_EMAIL, arrayOf("team@scri.be"))
                putExtra(Intent.EXTRA_SUBJECT, "Hey Scribe!")
                type = "message/rfc822"
            }
        startActivity(Intent.createChooser(intent, "Choose an Email client:"))
    }

    override fun onResume() {
        super.onResume()
    }

    private fun loadWikimediaScribeFragment() {
        val fragment = WikimediaScribeFragment()
        val fragmentTransaction = requireActivity().supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragment_container, fragment, "WikimediaScribePage")
        fragmentTransaction.addToBackStack("WikimediaScribePage")
        fragmentTransaction.commit()
    }

    private fun loadPrivacyPolicyFragment() {
        val fragment = PrivacyPolicyFragment()
        val fragmentTransaction = requireActivity().supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragment_container, fragment)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }

    private fun loadThirdPartyLicensesFragment() {
        val fragment = ThirdPartyFragment()
        val fragmentTransaction = requireActivity().supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragment_container, fragment)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }
}
