package com.iktakademija.RestExamples.entities;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class BankClientEntities {

	protected Integer id;
	protected String name;
	protected String surname;
	protected String email;
	protected Character bonitet;
	protected LocalDate datumRodenja;
	protected String grad;

	public BankClientEntities() {
		super();
	}

	public BankClientEntities(Integer id, String name, String surname, String email, Character bonitet,
			LocalDate datumRodenja, String grad) {
		super();
		this.id = id;
		this.name = name;
		this.surname = surname;
		this.email = email;
		this.bonitet = bonitet;
		this.datumRodenja = datumRodenja;
		this.grad = grad;
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Character getBonitet() {
		return bonitet;
	}

	public void setBonitet(Character bonitet) {
		this.bonitet = bonitet;
	}

	public LocalDate getDatumRodenja() {
		return datumRodenja;
	}	
	
	public String getGrad() {
		return grad;
	}

	public void setGrad(String grad) {
		this.grad = grad;
	}

	public void setDatumRodenja(LocalDate datumRodenja) {
		this.datumRodenja = datumRodenja;
//		if (getAges() > 65) {
//			setBonitet(false);
//		} else {
//			setBonitet(true);
//		}
	}	
	
	//@JsonIgnore
	public int getAges()
	{
		if (getDatumRodenja() == null) {
			return 0; // This should rise error
		} else {
			return (int) java.time.temporal.ChronoUnit.YEARS.between(getDatumRodenja(), LocalDate.now());
		}
	}	
	
	@JsonIgnore
	public boolean isValid()
	{		
		Boolean invalid = false;
		if (getId() == null)
			invalid = true;
		if (getName() == null)
			invalid = true;
		if (getSurname() == null)
			invalid = true;
		if (getEmail() == null)
			invalid = true;
		if (getBonitet() == null)
			invalid = true;
		if (getDatumRodenja() == null)
			invalid = true;
		if (getGrad() == null)
			invalid = true;		
		return !invalid;
	}

}
