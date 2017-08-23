package com.cooksys.ftd.assignments.objects;

import java.util.Arrays;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class SimplifiedRational implements IRational {
	private int numerator,denominator;

    /**
     * Determines the greatest common denominator for the given values
     *
     * @param a the first value to consider
     * @param b the second value to consider
     * @return the greatest common denominator, or shared factor, of `a` and `b`
     * @throws IllegalArgumentException if a <= 0 or b < 0
     */
    public static int gcd(int a, int b) throws IllegalArgumentException {
        if(a <= 0 || b < 0)
        	{
        	throw new IllegalArgumentException();
        	}
        if (b == 0)
          return a;
        else
        	return gcd(b, a%b);
        
    }

    /**
     * Simplifies the numerator and denominator of a rational value.
     * <p>
     * For example:
     * `simplify(10, 100) = [1, 10]`
     * or:
     * `simplify(0, 10) = [0, 1]`
     *
     * @param numerator   the numerator of the rational value to simplify
     * @param denominator the denominator of the rational value to simplify
     * @return a two element array representation of the simplified numerator and denominator
     * @throws IllegalArgumentException if the given denominator is 0
     */
    public static int[] simplify(int numerator, int denominator) throws IllegalArgumentException {
   
    	
    	if (denominator == 0 )
    	{
    		throw new IllegalArgumentException();
    	}
    	if(numerator != 0 )
    	{
    	int  multiple = gcd(Math.abs(numerator), Math.abs(denominator));
    	numerator = numerator/multiple;
    	denominator = denominator/multiple;
    	return new int[]{numerator,denominator};
    	}
    	return new int[]{numerator,1};
    	
    	

    }

    /**
     * Constructor for rational values of the type:
     * <p>
     * `numerator / denominator`
     * <p>
     * Simplification of numerator/denominator pair should occur in this method.
     * If the numerator is zero, no further simplification can be performed
     *
     * @param numerator   the numerator of the rational value
     * @param denominator the denominator of the rational value
     * @throws IllegalArgumentException if the given denominator is 0
     */
    public SimplifiedRational(int numerator, int denominator) throws IllegalArgumentException {
    	if(denominator == 0) 
    	{
    		throw new IllegalArgumentException();
    	}
    	
    		int n = simplify(numerator, denominator)[0];
    		int d = simplify(numerator, denominator)[1];
    		this.numerator = n;
    		this.denominator = d;
    	
    	
    }

    /**
     * @return the numerator of this rational number
     */
    @Override
    public int getNumerator() {
return this.numerator;
}

    /**
     * @return the denominator of this rational number
     */
    @Override
    public int getDenominator() {
    	return this.denominator;    }

    /**
     * Specializable constructor to take advantage of shared code between Rational and SimplifiedRational
     * <p>
     * Essentially, this method allows us to implement most of IRational methods directly in the interface while
     * preserving the underlying type
     *
     * @param numerator   the numerator of the rational value to construct
     * @param denominator the denominator of the rational value to construct
     * @return the constructed rational value (specifically, a SimplifiedRational value)
     * @throws IllegalArgumentException if the given denominator is 0
     */
    @Override
    public SimplifiedRational construct(int numerator, int denominator) throws IllegalArgumentException {
        if(this.getDenominator() == 0)
        	{
        	throw new IllegalArgumentException();
        	}
        return new SimplifiedRational(numerator, denominator);
    }

    /**
     * @param obj the object to check this against for equality
     * @return true if the given obj is a rational value and its
     * numerator and denominator are equal to this rational value's numerator and denominator,
     * false otherwise
     */
    @Override
    public boolean equals(Object obj) {
    	if (obj == null)
    		return false;
    	if(this.getClass() != obj.getClass())
    		return false;
    	SimplifiedRational r1 = (SimplifiedRational)obj; 
    	if (this.getNumerator() == r1.getNumerator() && this.getDenominator() == r1.getDenominator())
    		return true;
    	else
    		return false;
    }

    /**
     * If this is positive, the string should be of the form `numerator/denominator`
     * <p>
     * If this is negative, the string should be of the form `-numerator/denominator`
     *
     * @return a string representation of this rational value
     */
    @Override
    public String toString() {
    	if ((numerator < 0) != (denominator < 0))
    		return "-" +Math.abs(numerator) + "/" + Math.abs(denominator);
    	else
    		return  Math.abs(numerator) + "/" + Math.abs(denominator);
    }
}
