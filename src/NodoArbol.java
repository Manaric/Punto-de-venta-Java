import java.io.*;


/**
* <h1>Nodo Arbol</h1>
* NodoArbol is the class for the object of an item in the tree.
* <p>
* <b>Note:</b> This program doesn't have any validators,
* so exceptions could be thrown regullarly because of this.
* @author  Librado Jimenez
* @version 1.1
* @since   2013
* 
*/

public class  NodoArbol<AnyType>
{  
	AnyType dato; // Node data
	NodoArbol<AnyType>  izq, der; // right son and left son
	
	/**
	* Constructor method for class NodoArbol. It will establish values for parameters.
	* @param x It will take the properties of another object to itself.
	*/
	public NodoArbol(AnyType x ) 
	{ 
		izq = der = null;  
		dato = x; 
	}
	
	/**
	* NodoArbol constructor will set all properties to null in the object.
	*/	
	public NodoArbol() 
	{ 
		izq = der = null;  
		dato = null; 
	} 
}


