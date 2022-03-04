package com.rkpandey.flixster

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

const val Movie_Extra = "movie_extra"
class MovieAdapter (private val context: Context, private val movies: List<Movie> )
    : RecyclerView.Adapter<MovieAdapter.ViewHolder>() {

    private val VIEW_TYPE_OVER = 0
    private val VIEW_TYPE_BELOW = 1
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): MovieAdapter.ViewHolder {
//        val view =LayoutInflater.from(context).inflate(R.layout.item_movie,parent,false)
        val viewHolder: RecyclerView.ViewHolder
        val inflater = LayoutInflater.from(viewGroup.context)
        var view = inflater.inflate(R.layout.item_movie, viewGroup, false)
        when (viewType) {
            VIEW_TYPE_OVER -> {
                view = inflater.inflate(R.layout.item_movie2, viewGroup, false)

            }
            VIEW_TYPE_BELOW -> {
                 view = inflater.inflate(R.layout.item_movie, viewGroup, false)

            }
            else -> {
                 view = inflater.inflate(R.layout.item_movie, viewGroup, false)

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

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val movie= movies[position]
        when (viewHolder.itemViewType) {
            VIEW_TYPE_OVER -> {
                val vh1 = viewHolder as ViewHolder1
                vh1.bind(movie)

            }
            VIEW_TYPE_BELOW -> {
                val vh2 = viewHolder as ViewHolder2
                vh2.bind(movie)
            }
            else -> {
                val vh = viewHolder as ViewHolder
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
        fun bind(movie:Movie){
            tvTitle.text = movie.title
            tvoverview.text = movie.overview
            Glide.with(context).load(movie.backdropImageUrl).into(tvPoster)
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
            Glide.with(context).load(movie.posterImageUrl).into(tvPoster)
        }
    }


}
