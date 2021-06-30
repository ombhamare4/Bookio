package com.rubiks.bookio.activity

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.rubiks.bookio.R
import com.rubiks.bookio.util.ConnectionManger
import com.squareup.picasso.Picasso
import org.json.JSONException
import org.json.JSONObject

class DescriptionActivity : AppCompatActivity() {

    lateinit var bookImage: ImageView
    lateinit var bookName: TextView
    lateinit var bookAuthor: TextView
    lateinit var bookPrice: TextView
    lateinit var bookRating: TextView
    lateinit var bookDescription: TextView
    lateinit var btnFavourites: Button
    lateinit var progressLayout: RelativeLayout
    lateinit var progressBar: ProgressBar
    lateinit var descriptionToolbar: Toolbar

    var bookId: String? = "100"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_description)


        bookImage = findViewById(R.id.imgbook)
        bookName = findViewById(R.id.txtRecyclerRowItemName)
        bookAuthor = findViewById(R.id.txtRecyclerRowItemAuthor)
        bookPrice = findViewById(R.id.txtRecyclerRowItemPrice)
        bookRating = findViewById(R.id.txtRecyclerRowItemRating)
        bookDescription = findViewById(R.id.aboutTheBook)
        btnFavourites = findViewById(R.id.btnFavorites)
        progressLayout = findViewById(R.id.progressLayout)
        progressLayout.visibility = View.VISIBLE
        progressBar = findViewById(R.id.progressBar)
        progressBar.visibility = View.VISIBLE
        descriptionToolbar = findViewById(R.id.descriptiontoolbar)
        setSupportActionBar(descriptionToolbar)
        supportActionBar?.title = "Book Details"


        if (intent != null) {
            bookId = intent.getStringExtra("book_id")

        } else {
            Toast.makeText(
                this@DescriptionActivity,
                "Some Unexceptted Error Ocuur",
                Toast.LENGTH_SHORT
            ).show()
        }

        if (bookId == "100") {
            Toast.makeText(
                this@DescriptionActivity,
                "Some Unexceptted Error Ocuur",
                Toast.LENGTH_SHORT
            ).show()
        }

        val queue = Volley.newRequestQueue(this@DescriptionActivity)
        val url = "https://api.npoint.io/e11c0efebfbfbc586c5e"

        //to send id
        val jsonParam = JSONObject()
        jsonParam.put("book_id", bookId)

        if (ConnectionManger().checkConnectivity(this@DescriptionActivity)) {
            val jsonObjectRequest =
                object : JsonObjectRequest(Request.Method.POST, url, jsonParam, Response.Listener {

                    try {
                        val success = it.getBoolean("success")
                        if (success) {
                            val bookJsonObject = it.getJSONObject("data")
                            progressLayout.visibility = View.GONE

                            Picasso.get().load(bookJsonObject.getString("image"))
                                .error(R.drawable.default_book_cover).into(bookImage)
                            bookName.text = bookJsonObject.getString("name")
                            bookAuthor.text = bookJsonObject.getString("author")
                            bookPrice.text = bookJsonObject.getString("price")
                            bookRating.text = bookJsonObject.getString("rating")

                        } else {
                            Toast.makeText(
                                this@DescriptionActivity,
                                "Some Thing went wrong",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    } catch (e: JSONException) {
                        Toast.makeText(
                            this@DescriptionActivity,
                            "Some Thing went wrong Exception ",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                }, Response.ErrorListener {
                    Toast.makeText(
                        this@DescriptionActivity,
                        "Volly Error Occur $it",
                        Toast.LENGTH_SHORT
                    ).show()
                }) {
                    override fun getHeaders(): MutableMap<String, String> {

                        val headers = HashMap<String, String>()
                        headers["Content-type"] = "application/json"
                        headers["token"] = "e5d8446ee8b0e3"
                        return headers
                    }
                }
        } else {
            val dialog = AlertDialog.Builder(this@DescriptionActivity)
            dialog.setTitle("Failed")
            dialog.setMessage("Internet Not Found")
            dialog.setPositiveButton("Setting") { text, listner ->
                val settingIntent = Intent(Settings.ACTION_WIRELESS_SETTINGS)
                startActivity(settingIntent)
                finish()
            }
            dialog.setNegativeButton("Exit") { text, listner ->

                //It close all running activity
                ActivityCompat.finishAffinity(this@DescriptionActivity)
            }
            dialog.create()
            dialog.show()
        }
    }

}