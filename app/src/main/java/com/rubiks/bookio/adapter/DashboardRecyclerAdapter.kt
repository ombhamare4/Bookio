package com.rubiks.bookio.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.rubiks.bookio.R
import com.rubiks.bookio.activity.DescriptionActivity
import com.rubiks.bookio.model.Book
import com.squareup.picasso.Picasso
import org.w3c.dom.Text


class DashboardRecyclerAdapter(val context:Context, val itemList:ArrayList<Book>): RecyclerView.Adapter<DashboardRecyclerAdapter.DashboardViewHolder>() {

    class DashboardViewHolder(view: View): RecyclerView.ViewHolder(view){
        val txtBookName: TextView = view.findViewById(R.id.txtRecyclerRowItemName)
        val txtBookAuthor: TextView = view.findViewById(R.id.txtRecyclerRowItemAuthor)
        val txtBookRating: TextView = view.findViewById(R.id.txtRecyclerRowItemRating)
        val txtBookPrice : TextView = view.findViewById(R.id.txtRecyclerRowItemPrice)
        val imgBookImage: ImageView = view.findViewById(R.id.imgRecyclerRowItem)
        val llContent : RelativeLayout = view.findViewById(R.id.llcontent)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DashboardViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recyler_dashboard_single_row,parent,false)

        return DashboardViewHolder(view)
    }

    override fun onBindViewHolder(holder: DashboardViewHolder, position: Int) {
        val book = itemList[position]
        holder.txtBookName.text = book.bookName
        holder.txtBookAuthor.text = book.bookAuthor
        holder.txtBookPrice.text = book.bookPrice
        holder.txtBookRating.text = book.bookRating
        //holder.imgBookImage.setImageResource(book.bookImage)
        //Libray for loading images online
        Picasso.get().load(book.bookImage).error(R.drawable.default_book_cover).into(holder.imgBookImage);

        holder.llContent.setOnClickListener{

            //Here context is fragment activity
            val intent = Intent(context,DescriptionActivity::class.java)
            intent.putExtra("book_id",book.bookId)
            context.startActivity(intent)
        }
            

    }

    override fun getItemCount(): Int {
       return  itemList.size
    }

}