package com.rubiks.bookio.fragment

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.rubiks.bookio.R
import com.rubiks.bookio.adapter.DashboardRecyclerAdapter
import com.rubiks.bookio.model.Book
import com.rubiks.bookio.util.ConnectionManger
import org.json.JSONException

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [DashboardFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DashboardFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    lateinit var btnNetworkCheck: Button
    lateinit var recylerDashboard: RecyclerView
    lateinit var layoutManager: RecyclerView.LayoutManager
    lateinit var progressBar: ProgressBar
    lateinit var progressLayout: RelativeLayout

//    val bookList = arrayListOf(
//        "P.S I Love You",
//        "The Great Gatsby",
//        "Ana karenina",
//        "Mademe Bovary",
//        "War and Peace",
//        "The Lord Of Ring"
//    )

    lateinit var recyleAdapter: DashboardRecyclerAdapter
    val bookInfoList = arrayListOf<Book>()
//    val bookInfoList = arrayListOf<Book>(
//        Book(
//            "Advance Data Science",
//            "Jesus Roger salazar",
//            "4.3",
//            "Rs.999",
//            R.drawable.advdatascienceanlyticpython
//        ),
//        Book(
//            "Arduino Programming",
//            "Agus Kurniawan",
//            "3.9",
//            "Rs.599",
//            R.drawable.ardunioprogramming
//        ),
//        Book(
//            "Building App Python",
//            "Ahemed Fawzy Mohamad Gad",
//            "3",
//            "Rs.400",
//            R.drawable.buildingappusingpython
//        ),
//        Book("Clean Code", "Robert C. Martin", "4.5", "Rs.500", R.drawable.cleancode),
//        Book("Core Php", "Leon Atiknson", "4", "Rs.499", R.drawable.corephp),
//        Book(
//            "Craking Coding Interview",
//            "Gayle Mcdowell",
//            "4.2",
//            "Rs.300",
//            R.drawable.crakingcodinginterview
//        ),
//        Book("Desgined Pattern", "Grady Booch", "4", "Rs.700", R.drawable.desginpatterned),
//        Book(
//            "Django For Professional",
//            "William S. Vincent",
//            "3.5",
//            "Rs.350",
//            R.drawable.djangoprofessionals
//        ),
//        Book("Flask Development", "Miguel Grinberg", "4.3", "Rs.400", R.drawable.flaskdevlopment),
//        Book(
//            "Itroduction to algorithm",
//            "Thomas H. Cormen",
//            "5",
//            "Rs.700",
//            R.drawable.indroductiontoalgorithm
//        ),
//        Book(
//            "Introduction to Machine Learning",
//            "Andreas C. Muller & Sarah Guido",
//            "4.7",
//            "Rs.600",
//            R.drawable.intoductiontomachinelearning
//        )
//
//    )


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_dashboard, container, false)


//        btnNetworkCheck = view.findViewById(R.id.btnNetworkCheck)
        recylerDashboard = view.findViewById(R.id.recylerdashboard)
        layoutManager = LinearLayoutManager(activity)
        progressBar = view.findViewById(R.id.progressBar)
        progressLayout= view.findViewById(R.id.progressLayout)

        progressLayout.visibility = View.VISIBLE


//        btnNetworkCheck.setOnClickListener {
//            if (ConnectionManger().checkConnectivity((activity as Context))) {
//                val dialog = AlertDialog.Builder(activity as Context)
//                dialog.setTitle("Success")
//                dialog.setMessage("Internet Connection Found")
//                dialog.setPositiveButton("Ok") { text, listner ->
//                    //
//                }
//                dialog.setNegativeButton("Cancel") { text, listner ->
//                    //
//                }
//                dialog.create()
//                dialog.show()
//                //Internet
//            } else {
//
//                val dialog = AlertDialog.Builder(activity as Context)
//                dialog.setTitle("Failed")
//                dialog.setMessage("Internet Not Found")
//                dialog.setPositiveButton("Ok") { text, listner ->
//                    //
//                }
//                dialog.setNegativeButton("Cancel") { text, listner ->
//                    //
//                }
//                dialog.create()
//                dialog.show()
//                //Not Internet
//            }
//        }


        val queue = Volley.newRequestQueue(activity as Context)

        val url = "https://api.npoint.io/e11c0efebfbfbc586c5e"

        if (ConnectionManger().checkConnectivity(activity as Context)) {
            val jsonObjectRequets =
                object : JsonObjectRequest(Method.GET, url, null, Response.Listener {
                    println("Response is $it")

                    try {
                        progressLayout.visibility = View.GONE
                        val success = it.getBoolean("success")
                        if (success) {
                            val data = it.getJSONArray("data")
                            for (i in 0 until data.length()) {
                                val bookJsonObject = data.getJSONObject(i)//To store each object
                                //pass each jsonObject into bookObject
                                val bookObject = Book(
                                    bookJsonObject.getString("book_id"),
                                    bookJsonObject.getString("name"),
                                    bookJsonObject.getString("author"),
                                    bookJsonObject.getString("rating"),
                                    bookJsonObject.getString("price"),
                                    bookJsonObject.getString("image")
                                )
                                bookInfoList.add(bookObject)
                                recyleAdapter =
                                    DashboardRecyclerAdapter(activity as Context, bookInfoList)

                                recylerDashboard.layoutManager = layoutManager
                                recylerDashboard.adapter = recyleAdapter

//                                recylerDashboard.addItemDecoration(
//                                    DividerItemDecoration(
//                                        recylerDashboard.context,
//                                        (layoutManager as LinearLayoutManager).orientation
//                                    )
//                                )
                            }
                        } else {
                            Toast.makeText(
                                activity as Context,
                                "Some Thing went wrong",
                                Toast.LENGTH_SHORT
                            )
                                .show()
                        }

                    } catch (e: JSONException) {
                        Toast.makeText(
                            activity as Context,
                            "Something Went Wrong!!",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    //Here we will handle the response
                }, Response.ErrorListener {
                    Toast.makeText(
                        activity as Context,
                        "Volly Error Occur $it",
                        Toast.LENGTH_SHORT
                    ).show()
                    //Here we will handle Error
                }) {
                    override fun getHeaders(): MutableMap<String, String> {

                        val headers = HashMap<String, String>()
                        headers["Content-type"] = "application/json"
                        headers["token"] = "e5d8446ee8b0e3"
                        return headers

                    }

                }
            queue.add(jsonObjectRequets)
        } else {
            val dialog = AlertDialog.Builder(activity as Context)
            dialog.setTitle("Failed")
            dialog.setMessage("Internet Not Found")
            dialog.setPositiveButton("Setting") { text, listner ->
                val settingIntent = Intent(Settings.ACTION_WIRELESS_SETTINGS)
                startActivity(settingIntent)
                activity?.finish()
            }
            dialog.setNegativeButton("Exit") { text, listner ->

                //It close all running activity
                ActivityCompat.finishAffinity(activity as Activity)
            }
            dialog.create()
            dialog.show()

        }


        // Inflate the layout for this fragment
        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment DashboardFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            DashboardFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}