package com.bigbratan.emulair.activities.info
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

class InfoActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: InfoAdapter
    private lateinit var infoList: ArrayList<InfoItem>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info)

        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        // Initialize the RecyclerView
        recyclerView = findViewById(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)

        // Create a list of info items
        infoList = ArrayList()
        infoList.add(InfoItem("Why aren't my games recognized?", "There are a lot of reasons for why your games are not recognized:\n" +
                "\n" +
                "the CRC32 doesn't match a database entry;\n" +
                "the file name doesn't match a database entry;\n" +
                "the file extension is unsupported;\n" +
                "your romsets are merged;\n" +
                "your games are compressed - try decompressing them; note that Emulair currently doesn't support .7z archives; also, your .zip archives should contain only one file."))
        infoList.add(InfoItem("How to make FinalBurn Neo ROMs be detected?", "Emulair supports only non-merged romsets which have the same CRC32 code as the one that appears in the Libretro DB. If you still want to use the merged versions, copy them into the internal ROMs directory located at \"/sdcard/Android/data/com.bigbratan.emulair/files/roms/fbneo\"."))
        infoList.add(InfoItem("Where does Emulair store save files?", "Currently, Emulair stores save files under \"/sdcard/Android/data/com.bigbratan.emulair/files/saves\", while states are stored in \"/sdcard/Android/data/com.bigbratan.emulair/files/states\". We are working on providing the ability to store save files on other internal or external directories."))
        infoList.add(InfoItem("Can I import my save files from Retroarch?", "Yes, you can. To do this, simply temporarily disable \"Auto save state on correct quit\" and copy the content of your Retroarch saves folder into Emulair saves folder."))
        infoList.add(InfoItem("Are Sony PS1 multi-disk games supported?", "Yes. If you have a multidisk game, you should also provide an .m3u playlist file. It's a simple text file where each line contains the disk file name (stored in the same directory with the game). Here's an example of what a .m3u file should contain for a multi-disk game such as Metal Gear Solid (the file name should follow the name of the game, complete with the region, languages, and anything else that appears in other parantheses):\n" +
                "\n" +
                "Metal Gear Solid (USA) (Disc 1).cue\n" +
                "Metal Gear Solid (USA) (Disc 2).cue"))


        // Add more info items as needed

        // Create and set the adapter for the RecyclerView
        adapter = InfoAdapter(infoList)
        recyclerView.adapter = adapter
    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
    // Data class representing an info item
    data class InfoItem(val title: String, val description: String)

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
