/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.sil.jflavourapi;

/**
 * It's better to use our own class instead of a plain String to represent categories
 * Especially since these things can have their own nodes associated with them
 * category must override compareTo and equals so we can order them and check if
 * a collection contains them
 * @author toby
 */
public final class Category implements Comparable
{
    private final String name;

    public Category(String name)
    {
        this.name = name.trim();
    }

    @Override
    public String toString()
    {
        return name;
    }

    @Override
    public int compareTo(Object other) throws ClassCastException
    {
        if (!(other instanceof Category)) {
            throw new ClassCastException("A Category object expected.");
        }
        return name.compareTo(other.toString());
    }
    
    @Override
    public boolean equals(Object other) throws ClassCastException
    {
        if (!(other instanceof Category)) {
            throw new ClassCastException("A Category object expected.");
        }
        return name.equals(other.toString());
    }

    @Override
    public int hashCode()
    {
        int hash = 5;
        hash = 47 * hash + (this.name != null ? this.name.hashCode() : 0);
        return hash;
    }
    
}
