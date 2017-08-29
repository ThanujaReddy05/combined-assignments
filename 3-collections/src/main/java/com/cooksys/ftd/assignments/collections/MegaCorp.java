package com.cooksys.ftd.assignments.collections;

import com.cooksys.ftd.assignments.collections.hierarchy.Hierarchy;
import com.cooksys.ftd.assignments.collections.model.Capitalist;
import com.cooksys.ftd.assignments.collections.model.FatCat;
import com.cooksys.ftd.assignments.collections.model.WageSlave;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import static org.hamcrest.CoreMatchers.instanceOf;

import java.awt.datatransfer.FlavorListener;
import java.util.*;
import java.util.Map.Entry;
import java.util.function.Consumer;

public class MegaCorp implements Hierarchy<Capitalist, FatCat> {

	private TreeMap<Integer, Set<Capitalist>> mapTree = new TreeMap<>();
	private Integer treeCode;



	/**
	 * Adds a given element to the hierarchy. 
	 * <p>
	 * If the given element is already present in the hierarchy,
	 * do not add it and return false
	 * <p>
	 * If the given element has a parent and the parent is not part of the hierarchy,
	 * add the parent and then add the given element
	 * <p>
	 * If the given element has no parent but is a Parent itself,
	 * add it to the hierarchy
	 * <p>
	 * If the given element has no parent and is not a Parent itself,
	 * do not add it and return false
	 *
	 * @param capitalist the element to add to the hierarchy
	 * @return true if the element was added successfully, false otherwise
	 */
	@Override
	public boolean add(Capitalist capitalist) {

		if(capitalist != null ) //if element to add is not null then go ahead and add to hierarchy
		{	
			treeCode = capitalist.hashCode();

			if(!has(capitalist)) //if element is present in the hierarchy return false go ahead for adding 
			{
				if(capitalist.hasParent()) //if element has a parent get parents and the element to the hierarchy 
				{
					if(has(capitalist.getParent()))
					{
						return mapTree.get(treeCode).add(capitalist);
					}
					else //if parent does exists create new entry in the hierarchy by creating new element to the parent
					{
						mapTree.put(treeCode, new HashSet<Capitalist>()); 
						add(capitalist.getParent());
						return mapTree.get(treeCode).add(capitalist);
					}

				}
				else // if there is no parent check whether its a parent or child 
				{
					if(capitalist instanceof FatCat ) // if the element is a parent(instance of FatCat)
						if(mapTree.get(treeCode) != null) 
							return mapTree.get(treeCode).add(capitalist); // Add that element to hierarchy
						else
						{
							mapTree.put(treeCode, new HashSet<Capitalist>()); // If code is null from the Tree create one and add
							return mapTree.get(treeCode).add(capitalist);
						}
					else
						return false; // Don't add it if it is not a parent
				}
			}
			else
				return false;
		}
		else 
			return false;	
	}




	/**
	 * @param capitalist the element to search for
	 * @return true if the element has been added to the hierarchy, false otherwise
	 */
	@Override
	public boolean has(Capitalist capitalist) {
		treeCode = capitalist.code();

		if(mapTree.get(treeCode) != null) //if TreeCode is not equal to null check for availability of the element in the hierarchy
			return mapTree.get(treeCode).contains(capitalist);

		else
			return false;
	}

	/**
	 * @return all elements in the hierarchy,
	 * or an empty set if no elements have been added to the hierarchy
	 */
	@Override
	public Set<Capitalist> getElements() {

		Set<Capitalist> elements = new HashSet<Capitalist>();
		for (Entry<Integer, Set<Capitalist>> entry : mapTree.entrySet()) {
			elements.addAll(entry.getValue());
		}
		return elements;

	}
	/**
	 * @return all parent elements in the hierarchy,
	 * or an empty set if no parents have been added to the hierarchy
	 */
	@Override
	public Set<FatCat> getParents() {
		Set<FatCat> fatCatsSet = new HashSet<FatCat>();

		getElements().forEach(element -> {
			if((element instanceof FatCat)) { //It is a parent
				fatCatsSet.add((FatCat) element); 
			}
		});

		return fatCatsSet;
	}

	/**
	 * @param fatCat the parent whose children need to be returned
	 * @return all elements in the hierarchy that have the given parent as a direct parent,
	 * or an empty set if the parent is not present in the hierarchy or if there are no children
	 * for the given parent
	 */
	@Override
	public Set<Capitalist> getChildren(FatCat fatCat) {
		Set<Capitalist> childrenSet = new HashSet<Capitalist>();

		getElements().forEach(child -> {
			if(child.hasParent()){
				if(child.getParent() == fatCat){
					childrenSet.add(child);
				}
			}
		});

		return childrenSet;
	}

	/**
	 * @return a map in which the keys represent the parent elements in the hierarchy,
	 * and the each value is a set of the direct children of the associate parent, or an
	 * empty map if the hierarchy is empty.
	 */
	@Override
	public Map<FatCat, Set<Capitalist>> getHierarchy() {
		Map<FatCat, Set<Capitalist>> parentChildPair = new HashMap<FatCat, Set<Capitalist>>();

		getParents().forEach(parent ->
		{
			parentChildPair.put(parent, getChildren(parent));
		});

		return parentChildPair;
	}

	/**
	 * @param capitalist
	 * @return the parent chain of the given element, starting with its direct parent,
	 * then its parent's parent, etc, or an empty list if the given element has no parent
	 * or if its parent is not in the hierarchy
	 */
	@Override
	public List<FatCat> getParentChain(Capitalist capitalist) {
		List<FatCat> parentChain = new ArrayList<FatCat>();

		if(capitalist != null)
		{
			while (capitalist.hasParent() && has(capitalist.getParent()))
			{
				capitalist = capitalist.getParent();
				parentChain.add((FatCat) capitalist);
			}
		}
		return parentChain;
	}

}
