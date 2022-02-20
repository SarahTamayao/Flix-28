package com.rkpandey.flixster

import org.json.JSONArray

data class Movie (
    val movieId: Int,
    private val posterPath: String,
    val title:String,
    val overview: String,
    private val backdropPath: String
    ){
    val posterImageUrl= "https://image.tmdb.org/t/p/w342/$posterPath"
    val backdropImageUrl = "https://image.tmdb.org/t/p/w342/$backdropPath"
    companion object {
        fun fromJsonArray(movieJSONArray: JSONArray): List<Movie>{
            val movies= mutableListOf<Movie>()
            for (i in 0 until movieJSONArray.length()){
                val movieJson = movieJSONArray.getJSONObject(i)
                movies.add(
                    Movie(
                        movieJson.getInt("id"),
                        movieJson.getString("poster_path"),
                        movieJson.getString("title"),
                        movieJson.getString("overview"),
                        movieJson.getString("backdrop_path")
                    )
                )
            }
            return movies
        }
    }
}