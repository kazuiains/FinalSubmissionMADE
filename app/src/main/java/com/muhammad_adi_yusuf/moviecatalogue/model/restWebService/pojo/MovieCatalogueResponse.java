package com.muhammad_adi_yusuf.moviecatalogue.model.restWebService.pojo;

import java.util.List;

public class MovieCatalogueResponse{
	private int page;
	private List<MovieCatalogueResultsItem> results;

	public void setPage(int page){
		this.page = page;
	}

	public int getPage(){
		return page;
	}

	public void setResults(List<MovieCatalogueResultsItem> results){
		this.results = results;
	}

	public List<MovieCatalogueResultsItem> getResults(){
		return results;
	}

	@Override
 	public String toString(){
		return 
			"MovieCatalogueResponse{" + 
			"page = '" + page + '\'' + 
			",results = '" + results + '\'' + 
			"}";
		}
}