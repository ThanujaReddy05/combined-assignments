package com.cooksys.ftd.assignments.collections.model;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class FatCat implements Capitalist {

	private String name;
	private int salary;
	private FatCat owner;

	public FatCat(String name, int salary) {
		this.name = name;
		this.salary = salary;

	}

	public FatCat(String name, int salary, FatCat owner) {
		this.name = name;
		this.salary = salary;
		this.owner = owner;
	}

	/**
	 * @return the name of the capitalist
	 */
	@Override
	public String getName() {
		return this.name;
	}

	/**
	 * @return the salary of the capitalist, in dollars
	 */
	@Override
	public int getSalary() {
		return this.salary;
	}

	/**
	 * @return true if this element has a parent, or false otherwise
	 */
	@Override
	public boolean hasParent() {
		if(owner == null)
			return false;
		else 
			return true;
	}

	/**
	 * @return the parent of this element, or null if this represents the top of a hierarchy
	 */
	@Override
	public FatCat getParent() {
		if(hasParent())
			return owner;
		else 
			return null;
	}


	@Override
	public boolean equals(Object obj) {
		if (obj == null) // if obj is null you no need to check for equality
			return false;

		if(this.getClass() != obj.getClass()) // cannot check two different objects for equality of Rational number 
			return false;

		FatCat fc = (FatCat)obj; // Type cast obj to Rational before comparing

		if(((this.name != null) && (fc.name == null)) || (this.owner != null) && (fc.owner == null))
			return false;
		else
			if(this.name.equals(fc.name))
				return true;

		if(this.owner.equals(fc.owner))
			return true;

		if((this.salary == fc.salary))
			return true;

		return false;
	}


	@Override
	public int hashCode() {
		int num = 31;
		num +=  ((this.name == null)? 0 : name.hashCode());
		num += ((this.owner == null)? 0 : owner.hashCode());
		num +=  this.salary;
		return num;



	}





	public int code()
	{
		if(!(hasParent()))
			return hashCode();
		else
			return getParent().code();
	}
}
