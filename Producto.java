import java.io.*;


/**
* <h1>Producto</h1>
* Producto class will have all the properties from the products
* for the sale Point.
* <p>
* <b>Note:</b> This program doesn't have any validators,
* so exceptions could be thrown regullarly because of this.
* @see Comparable
* @author  Librado Jimenez
* @version 1.1
* @since   2013
*/

public class Producto implements Comparable<Producto>
{
	// Product properties
	private String descripcion;
	private int clave;
	private double precio;
  
	/**
	* Constructor method for class Producto. It will establish values for parameters.
	* @param d Description of the product.
	* @param c Key for the product.
	* @param p Price of the product.
	*/
	public Producto(String d, int c, double p)
	{ 
		descripcion = d;
		clave = c;
		precio = p;
	}
  
    /**
	* Method to return the properties of the product in a format to be put on
	* a Text Area.
	* @return String Properties in a determined format.
	*/
	public String toString()
	{
		if (descripcion.length() <= 15) 
			return "\n" + clave + "\t\t" + descripcion + "\t\t\t " + precio;
		else
			return "\n" + clave + "\t\t" + descripcion + "\t\t" + precio;
	}
	
    /**
	* Method compare to that is used to compare two products by their key.
	* @param p It takes as a parameter the product that is going to be compared.
	* @return int If the key is the same as the other product it will return 0, if it's bigger return -1 if not return 1.
	*/
	public int compareTo(Producto p)
	{ 
		Producto prod = p; 
		
		if (clave == prod.clave)
			return 0;
		else if (clave < prod.clave)
			return -1;
		else
			return 1;
	}

	/*
	* Getter for Description
	* @return String Description of the product.
	*/
	public String getDescripcion()
	{ 
		return descripcion; 
	}
	
	/*
	* Setter for Description
	* @param descripcion Description of the product.
	*/
	public void setDescripcion(String descripcion)
	{ 
		this.descripcion = descripcion; 
	}

	/*
	* Getter for Price
	* @return String Description of the product.
	*/
	public double getPrecio()   
	{ 
		return precio; 
	}
	
	/*
	* Setter for Price
	* @param Price of the product.
	*/
	public void setPrecio(double precio)   
	{ 
		this.precio = precio; 
	}
	
	/*
	* Getter for Key
	* @return String Description of the product.
	*/
	public int getClave()
	{
		return clave;
	}
	
	/*
	* Setter for Key
	* @param clave Key of the product.
	*/
	public void setClave(int clave)
	{
		this.clave = clave;
	}
}