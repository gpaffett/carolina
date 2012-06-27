package net.paffett.web.form;

import java.util.Date;

public class RecipeForm {

	public RecipeForm(int id, String name, String author) {
		this.name = name;
		this.author = author;
		this.dateCreated = new Date();
		this.id = id;
	}

	public RecipeForm() {
		this.dateCreated = new Date();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	private String name;
	private String author;
	private Date dateCreated;
	private int id = -1;

}
