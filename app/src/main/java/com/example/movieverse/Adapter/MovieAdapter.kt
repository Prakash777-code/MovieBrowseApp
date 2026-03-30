package com.example.movieverse.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.movieverse.R
import com.example.movieverse.Models.Search
import com.squareup.picasso.Picasso

class MovieAdapter(
    private var movieList: List<Search>,
    private val onItemClick: (Search) -> Unit
) : RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val poster: ImageView = itemView.findViewById(R.id.imgPoster)
        val title: TextView = itemView.findViewById(R.id.tvTitle)
        val type: TextView = itemView.findViewById(R.id.tvType)
        val year: TextView = itemView.findViewById(R.id.tvYear)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.movie_item, parent, false)
        return MovieViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = movieList[position]

        holder.title.text = movie.Title?:"N/A"
        holder.type.text = movie.Type?:"N/A"
        holder.year.text = movie.Year?:"N/A"

        if (movie.Poster != "N/A" && !movie.Poster.isNullOrEmpty()) {
            Picasso.get()
                .load(movie.Poster)
                .fit()
                .centerCrop()
                .into(holder.poster)
        } else {
            holder.poster.setImageResource(android.R.drawable.ic_menu_gallery)
        }

        holder.itemView.setOnClickListener {
            movie.imdbID?.let {
                onItemClick(movie)
            }
        }

    }

    override fun getItemCount(): Int = movieList.size

    fun updateList(newList: List<Search>?) {
        if (newList != null) {
            movieList = newList
        }
        notifyDataSetChanged()
    }
}