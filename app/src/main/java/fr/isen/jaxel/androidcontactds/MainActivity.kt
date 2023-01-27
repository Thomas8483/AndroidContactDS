package fr.isen.jaxel.androidcontactds

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import fr.isen.jaxel.androidcontactds.model.Results
import fr.isen.jaxel.androidcontactds.databinding.ActivityMainBinding
import fr.isen.jaxel.androidcontactds.model.DataResult
import org.json.JSONObject


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var contactsList = ArrayList<Results>()
    private lateinit var contact: String
    private lateinit var myCategoryAdapter : ContactAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        myCategoryAdapter = ContactAdapter(contactsList){
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        val layoutManager = LinearLayoutManager(applicationContext)
        binding.contactList.layoutManager = layoutManager
        binding.contactList.adapter = myCategoryAdapter

        binding.contactList.layoutManager = LinearLayoutManager(this)

        loadContactsfromApi()

    }

    private fun loadContactsfromApi(){
        val url = "https://randomuser.me/api/?results=10&nat=fr"
        val jsonObject = JSONObject()
        jsonObject.put("id_shop", "1")
        val jsonRequest = JsonObjectRequest(
            Request.Method.POST, url, jsonObject,
            {
                Log.w("MainActivity", "response : $it")
                handleAPIData(it.toString())
            },
            {
                Log.w("MainActivity", "error : $it")
            })
        Volley.newRequestQueue(this).add(jsonRequest)
    }

    private fun handleAPIData(data: String){
        var contactsResult = Gson().fromJson(data, DataResult::class.java)
        val dishCategory = contactsResult.data.firstOrNull { it.results.toString() == contact }
        val adapter = binding.contactList.adapter as ContactAdapter
        adapter.refreshList(dishCategory?.results as ArrayList<Results>)
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("MainActivity", "L'activité Main a été détruite")
    }
}