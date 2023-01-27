package fr.isen.jaxel.androidcontactds

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import fr.isen.jaxel.androidcontactds.model.Results

internal class ContactAdapter(private var myArrayList: ArrayList<Results>, val onContactClickListener: () -> Unit) : RecyclerView.Adapter<ContactAdapter.MyViewHolder>() {

    internal inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView = view.findViewById(R.id.name)
        val image: ImageView = view.findViewById(R.id.image)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.activity_contact_adapter, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val contact = myArrayList[position]
        holder.name.text = contact.name.toString()
        /*
        if (contact.picture[0].isNotEmpty()) {
            Picasso.get().load(myArrayList[position].picture[0])
                .placeholder(R.drawable.contact)
                .into(holder.contentImage)
        }
        /*
        holder.contactView.setOnClickListener {
            onContactClickListener()
        }

         */*/
    }

    override fun getItemCount(): Int = myArrayList.size

    fun refreshList(contactFromAPI: ArrayList<Results>) {
        myArrayList = contactFromAPI
        notifyDataSetChanged()
    }

}