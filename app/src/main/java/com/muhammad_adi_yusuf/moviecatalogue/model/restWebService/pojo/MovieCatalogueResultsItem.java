package com.muhammad_adi_yusuf.moviecatalogue.model.restWebService.pojo;

import com.google.gson.annotations.SerializedName;

public class MovieCatalogueResultsItem {
	@SerializedName("backdrop_path")
	private String backdropPath;

	@SerializedName("overview")
	private String overview;

	@SerializedName(value="release_date", alternate= "first_air_date")
	private String releaseDate;

	@SerializedName("vote_average")
	private double voteAverage;

	@SerializedName("id")
	private int id;

	@SerializedName(value="title", alternate= "name")
	private String title;

	@SerializedName("poster_path")
	private String posterPath;

	@SerializedName("status")
	private String status;


	public String getBackdropPath(){
		return backdropPath;
	}

	public String getOverview(){
		return overview;
	}

	public String getReleaseDate(){
		return releaseDate;
	}

	public double getVoteAverage(){
		return voteAverage;
	}

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}

	public void setTitle(String title){
		this.title = title;
	}

	public String getTitle(){
		return title;
	}

	public String getPosterPath(){
		return posterPath;
	}

	public String getStatus(){
		return status;
	}

	@Override
 	public String toString(){
		return 
			"MovieCatalogueResultsItem{" +
					"backdrop_path = '" + backdropPath + '\'' +
					",overview = '" + overview + '\'' +
					",release_date = '" + releaseDate + '\'' +
					",first_air_date = '" + releaseDate + '\'' +
					",vote_average = '" + voteAverage + '\'' +
					",id = '" + id + '\'' +
					",title = '" + title + '\'' +
					",name = '" + title + '\'' +
					",poster_path = '" + posterPath + '\'' +
					",status = '" + status + '\'' +
			"}";
		}
}
