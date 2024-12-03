package com.windsurf.app

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ServicesDetailFragment : Fragment() {
    private var _jobsAdapter: JobsAdapter? = null
    private var _availabilityAdapter: AvailabilityAdapter? = null
    private var _commentsAdapter: CommentsAdapter? = null
    
    private val jobsAdapter get() = _jobsAdapter!!
    private val availabilityAdapter get() = _availabilityAdapter!!
    private val commentsAdapter get() = _commentsAdapter!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_services_detail, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerViews(view)
    }

    private fun setupRecyclerViews(view: View) {
        setupJobsList(view)
        setupAvailabilityList(view)
        setupCommentsList(view)
    }

    private fun setupJobsList(view: View) {
        view.findViewById<RecyclerView>(R.id.jobsRecyclerView)?.apply {
            layoutManager = LinearLayoutManager(context)
            _jobsAdapter = JobsAdapter(getSampleJobs())
            adapter = _jobsAdapter
        }
    }

    private fun setupAvailabilityList(view: View) {
        view.findViewById<RecyclerView>(R.id.availabilityRecyclerView)?.apply {
            layoutManager = LinearLayoutManager(context)
            _availabilityAdapter = AvailabilityAdapter(getSampleAvailability())
            adapter = _availabilityAdapter
        }
    }

    private fun setupCommentsList(view: View) {
        view.findViewById<RecyclerView>(R.id.commentsRecyclerView)?.apply {
            layoutManager = LinearLayoutManager(context)
            _commentsAdapter = CommentsAdapter(getSampleComments())
            adapter = _commentsAdapter
        }
    }

    private fun getSampleJobs(): List<Job> = listOf(
        Job("Windsurf Instructor", "Full Time", "Experience required"),
        Job("Equipment Manager", "Part Time", "Morning shift"),
        Job("Safety Officer", "Full Time", "Certification needed")
    )

    private fun getSampleAvailability(): List<Availability> = listOf(
        Availability("Monday - Friday", "9:00 AM - 5:00 PM"),
        Availability("Saturday", "10:00 AM - 4:00 PM"),
        Availability("Sunday", "Closed")
    )

    private fun getSampleComments(): List<Comment> = listOf(
        Comment("John Doe", "Great service and friendly staff!", 5),
        Comment("Jane Smith", "Professional instructors, highly recommended", 5),
        Comment("Mike Johnson", "Amazing experience!", 4)
    )

    override fun onDestroyView() {
        super.onDestroyView()
        _jobsAdapter = null
        _availabilityAdapter = null
        _commentsAdapter = null
    }
}

data class Job(
    val title: String,
    val type: String,
    val description: String
)

data class Availability(
    val day: String,
    val hours: String
)

data class Comment(
    val author: String,
    val text: String,
    val rating: Int
)
