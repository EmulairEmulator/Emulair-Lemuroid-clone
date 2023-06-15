package com.bigbratan.emulair.activities.profile
import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import com.bigbratan.emulair.R

class ProfileActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: InfoAdapter
    private lateinit var ProfileList: ArrayList<ProfileItem>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info)

        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        // Initialize the RecyclerView
        recyclerView = findViewById(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)



        // Create and set the adapter for the RecyclerView
        adapter = InfoAdapter(ProfileListList)
        recyclerView.adapter = adapter
    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
    // Data class representing an info item
    data class ProfileItem(val title: String, val description: String)

    // RecyclerView adapter
    inner class InfoAdapter(private val items: List<InfoItem>) :
            RecyclerView.Adapter<InfoAdapter.InfoViewHolder>() {

        inner class InfoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val cardView: MaterialCardView = itemView.findViewById(R.id.card_view)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InfoViewHolder {
            val itemView = LayoutInflater.from(parent.context)
                    .inflate(R.layout.layout_card_info, parent, false)
            return InfoViewHolder(itemView)
        }

        override fun onBindViewHolder(holder: InfoViewHolder, position: Int) {
            val currentItem = items[position]

            // Set the title and description
            holder.cardView.findViewById<TextView>(R.id.title_text).text = currentItem.title
            holder.cardView.findViewById<TextView>(R.id.description_text).text =
                    currentItem.description

            // Set up the arrow icon and expand/collapse functionality
            val arrowIcon = holder.cardView.findViewById<ImageView>(R.id.arrow_icon)
            val expandableLayout = holder.cardView.findViewById<View>(R.id.expandable_layout)

            holder.cardView.setOnClickListener {
                if (expandableLayout.visibility == View.GONE) {
                    // Card is collapsed, expand it
                    expandableLayout.visibility = View.VISIBLE
                    arrowIcon.setImageResource(R.drawable.ic_arrow_up)
                } else {
                    // Card is expanded, collapse it
                    expandableLayout.visibility = View.GONE
                    arrowIcon.setImageResource(R.drawable.ic_arrow_down)
                }
            }
        }

        override fun getItemCount(): Int {
            return items.size
        }
    }
}
