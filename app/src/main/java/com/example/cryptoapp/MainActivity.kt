package com.example.cryptoapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.cryptoapp.databinding.ActivityMainBinding
import java.lang.Exception
import java.util.Locale

class MainActivity : AppCompatActivity() {
   private lateinit var binding: ActivityMainBinding
    private lateinit var rvAdaptor: Rv_Adaptor
   private lateinit var data:ArrayList<Rv_Modal>

    override fun onCreate(savedInstanceState: Bundle?) {
        binding= ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        data = ArrayList<Rv_Modal>()
        rvAdaptor = Rv_Adaptor(this,data)
        apiData  // calling apidata function
        binding.Rv.layoutManager = LinearLayoutManager(this)
        binding.Rv.adapter = rvAdaptor

        binding.search.addTextChangedListener(object :TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
               val filterdata = ArrayList<Rv_Modal>()
                for (item in data){
                    if(item.name.lowercase(Locale.getDefault()).contains(s.toString().lowercase(Locale.getDefault())))
                        filterdata.add(item)
                }
                if(filterdata.isEmpty()){
                    Toast.makeText(this@MainActivity, "Data Not Available", Toast.LENGTH_SHORT).show()
                }
                else{
                    rvAdaptor.changeData(filterdata)
                }
            }

        })

    }
    // As such var ka use nahi but api se data ayenga use hold karne ke liye ->  val apiData:Unit
    val apiData:Unit
        get() {
            val url = "https://pro-api.coinmarketcap.com/v1/cryptocurrency/listings/latest"

            val queue = Volley.newRequestQueue(this)
           val jsonObjectRequest:JsonObjectRequest=
               object:JsonObjectRequest(Method.GET,url,null,Response.Listener {
               response ->
                   binding.progressBar.isVisible = false

                   binding.progressBar.isVisible = false
                   try {
                       val dataArray = response.getJSONArray("data")
                       for (i in 0 until dataArray.length())
                       {
                           val dataObject = dataArray.getJSONObject(i)
                           val symbol = dataObject.getString("symbol")
                           val name =dataObject.getString("name")
                           val qoute = dataObject.getJSONObject("quote")
                           val USD = qoute.getJSONObject("USD")
                           val price =String.format("$"+"%.2f", USD.getDouble("price"))  // To convert into some decimal
                           data.add(Rv_Modal(name,symbol, price.toString() ))
                       }
                       rvAdaptor.notifyDataSetChanged()
                   }catch (e:Exception){
                       Toast.makeText(this, "Error1", Toast.LENGTH_SHORT).show()
                   }

               },
               Response.ErrorListener {
                   Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()
               })
               {
                   override fun getHeaders(): Map<String, String> {

                       val headers=HashMap<String,String>();
                       headers["X-CMC_PRO_API_KEY"]="c51cda34-efd7-47ba-8e41-711994ad7e38"
                       return headers
                   }
               }
            queue.add(jsonObjectRequest)


        }
}


