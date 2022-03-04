package com.rkpandey.flixster

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.Target.SIZE_ORIGINAL

class MovieAdapterComplex(private val context: Context, private val movies: List<Movie> )
    : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val VIEW_TYPE_OVER = 0
    private val VIEW_TYPE_BELOW = 1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val viewHolder: RecyclerView.ViewHolder
        val inflater = LayoutInflater.from(parent.context)
        var view = inflater.inflate(R.layout.item_movie, parent, false)
        when (viewType) {
            VIEW_TYPE_OVER -> {
                view = inflater.inflate(R.layout.item_movie2, parent, false)
                return ViewHolder1(view)
            }
            VIEW_TYPE_BELOW -> {
                view = inflater.inflate(R.layout.item_movie, parent, false)
                return ViewHolder2(view)
            }
            else -> {
                view = inflater.inflate(R.layout.item_movie, parent, false)

            }
        }

        return ViewHolder(view)
    }

    override fun getItemViewType(position: Int): Int {
        val movie= movies[position]
        if ( movie.voted > 5) {
            return VIEW_TYPE_OVER
        } else {
            return VIEW_TYPE_BELOW
        }
        return -1
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val movie= movies[position]
        when (holder.itemViewType) {
            VIEW_TYPE_OVER -> {
                val vh1 = holder as ViewHolder1
                vh1.bind(movie)

            }
            VIEW_TYPE_BELOW -> {
                val vh2 = holder as ViewHolder2
                vh2.bind(movie)
            }
            else -> {
                val vh = holder as ViewHolder
                with(vh) {
                    bind(movie)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return movies.size
    }
    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        private val tvPoster = itemView.findViewById<ImageView>(R.id.tvPoster)
        private val tvTitle = itemView.findViewById<TextView>(R.id.tvTitle)
        private val tvoverview = itemView.findViewById<TextView>(R.id.tvOverview)
        fun bind(movie:Movie){
            tvTitle.text = movie.title
            tvoverview.text = movie.overview
            val orientation = context.resources.configuration.orientation
            if (orientation == Configuration.ORIENTATION_PORTRAIT) {
                Glide.with(context).load(movie.posterImageUrl).into(tvPoster)
            } else if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
                Glide.with(context).load(movie.backdropImageUrl).into(tvPoster)
            }

        }
    }

    inner class ViewHolder1(itemView: View): RecyclerView.ViewHolder(itemView), View.OnClickListener{
        private val tvPoster = itemView.findViewById<ImageView>(R.id.tvPoster)
        private val tvTitle = itemView.findViewById<TextView>(R.id.tvTitle)
        private val tvoverview = itemView.findViewById<TextView>(R.id.tvOverview)
        init {
            itemView.setOnClickListener(this)
        }
        fun bind(movie:Movie){
            if(tvTitle !== null){
            tvTitle.text = movie.title}
            if(tvoverview !== null){
            tvoverview.text = movie.overview}
            Glide.with(context).load(movie.backdropImageUrl).placeholder(R.drawable.ic_launcher_foreground).override(600, 400).into(tvPoster)
        }

        override fun onClick(p0: View?) {
            // 1. get notified with specific movie which was clicked
            val movie = movies[adapterPosition]
            Toast.makeText(context, movie.title, Toast.LENGTH_SHORT).show()
            // 2. Use the intent to navigate to the new activity
            val  intent = Intent(context, DetailActivity::class.java)

            intent.putExtra(Movie_Extra, movie)
            context.startActivity(intent)
        }
    }

    inner class ViewHolder2(itemView: View): RecyclerView.ViewHolder(itemView){
        private val tvPoster = itemView.findViewById<ImageView>(R.id.tvPoster)
        private val tvTitle = itemView.findViewById<TextView>(R.id.tvTitle)
        private val tvoverview = itemView.findViewById<TextView>(R.id.tvOverview)
        fun bind(movie:Movie){
            tvTitle.text = movie.title
            tvoverview.text = movie.overview
            Glide.with(context).load(movie.posterImageUrl).placeholder(R.drawable.ic_launcher_foreground).override(200, 400).into(tvPoster)
        }
    }
}
