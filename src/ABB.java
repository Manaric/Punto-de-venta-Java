import javax.swing.JTextArea;

/**
* <h1>ABB</h1>
* The ABB class implements an application that
* manage a search binary tree for the products in the Sell Point.
* It extends the class Comparable.
* <p>
* <b>Note:</b> This program doesn't have any validators,
* so exceptions could be thrown regullarly because of this.
* @see Comparable
* @author  Librado Jimenez
* @version 1.1
* @since   2013
*/

public class ABB<AnyType extends Comparable<AnyType>> // Just objects that implements comparable
{
	private NodoArbol<AnyType> raiz;  // Tree root
  
	/**
   * This method is the constructor of the application.
   * Because of this it won't have any return parameter.
   */
	public ABB()  // Tree Constructor
    { 
		raiz = null; 
	}

	/**
	* This method is to insert a new item into the binary search tree.
	* @param x It will accept only comparable objects.
	* @see java.lang.Comparable
	* @return boolean True if it could be added to the tree or false if it is duplicated.
	* @see AnyType
	*/
	public boolean inserta(AnyType x )
	{ 
		NodoArbol<AnyType> nuevo, hijo, padre;
		nuevo = new NodoArbol<AnyType>(x);
		hijo = raiz;
		padre = null;
		while ( hijo != null ) // Meanwhile we don't arrive to a null node
		{  
			if ( x.compareTo(hijo.dato)==0) 
				return false; // we don't want duplicated items
			
			padre = hijo;
			
			if (x.compareTo(hijo.dato) < 0)
				hijo = hijo.izq;
			else
			hijo=  hijo.der;
		}
		
		if( padre == null) // The tree is empty
			raiz=nuevo;
		else if ( padre.dato.compareTo(x) > 0  )
			padre.izq = nuevo;
		else
			padre.der = nuevo;
		return true;
	}
   
   /**
	* Recursive method for searching on the binary search tree. If an item wasn't found,
	* then it will enter again into method compareTo.
	* @param x It will accept only comparable objects.
	* @see java.lang.Comparable
	* @see Producto
	* @return NodoArbol It will return the item found in the binary search tree.
	* @see NodoArbol
	*/
	public NodoArbol<AnyType> busca(AnyType x) // Search item in the tree and return it's direction
    {  
		NodoArbol<AnyType> p = raiz;
		while (p != null)
		{ 
			if (p.dato.compareTo(x) == 0)
				return  p; // si lo encontró
			else if (x.compareTo(p.dato) < 0)
				p= p.izq;
			else 
				p = p.der;
        }
		
		return null;
    }

	/**
	* Method to print on the TextArea what is found in the binary search tree. 
	* @param area It need the TextArea field to print in the information
	* got from the tree.
	* @see javax.swing.JTextArea
	*/
	public void imprime(JTextArea area) // Prints on a text area
	{ 
		inorder(raiz, area);
	}

	/**
	* This method will order the tree to print it on the Text Area
	* @param res It need the TextArea field to print in the information got from the tree.
	* @param p Node from the tree.
	* @see javax.swing.JTextArea
	* @see java.lang.Comparable
	* @see NodoArbol
	*/
	public void inorder(NodoArbol <AnyType> p ,JTextArea res)
	{  
		if( p == null )
			return;

		if( p.izq != null )
			inorder(p.izq, res );
		res.append(p.dato.toString()); // Puts the text on the text Area
		if( p.der != null )
			inorder(p.der, res );
	}
    
	/**
	* This method will call another method to count the total of nodes.
	* @return int Sum of total nodes.
	*/
	public int cuenta_nodos()
    { 
		return cuenta(raiz);
	}
    
	/**
   * Called by cuenta_nodos to count the total of nodes held in the tree.
   * @param p recieve a node to count up the total of nodes in the tree.
   * @return int it will return the sum of the nodes.
   * @see NodoArbol
   */
	public int cuenta(NodoArbol<AnyType> p)
	{
		if( p == null )
			return 0;
		
		return cuenta(p.izq) + 1 + cuenta(p.der);
	}
 
	/**
   * This method will set the succesor in the binary search tree.
   * @param q Father of the node
   * @param sucesorptr Pointer to the successor
   * @param padre Father of the node
   * @see NodoArbol
   */
    public void  sucesor( NodoArbol<AnyType> q, NodoArbol<AnyType> sucesorptr, NodoArbol<AnyType> padre )
    {
		padre = q;
		sucesorptr  = q.der;

		while(sucesorptr.izq != null)// Go to the left as much as it is possible
		{	
			padre =sucesorptr;
			sucesorptr  =sucesorptr.izq;
		}
    }

	/** 
	* Function localiza return 1 if finds x, 0 other way
	* @param x AnyType object.
	* @param p Reference pointer to the place where it is
	* @param pp Reference pointer to p node father 
	* @return boolean If the item is located is true, if it wasn't found then it will return false.
	*/ 
	public boolean  localiza( AnyType x, NodoArbol<AnyType> p, NodoArbol<AnyType> pp )
	{    
		p  = raiz;
	    pp = null;

		while( p != null )
		{	
			if( x.compareTo(p.dato) == 0 )
			{
				System.out.println("Ya lo localize");
				return true; // It's located
			}
			else if( x.compareTo(p.dato) < 0 )
			{ 
				pp = p;
				p  = p.izq;
				System.out.println("es menor");
			} 
			else
			{ 
				pp = p;
				p  = p.der;
				System.out.println("es mayor");
			}
		}

		return false;  // It wasn't found
    }
    
	/**
	* Method borrar will ask if the binary search tree is empty.
	* @return boolean If is empty it will return true, false otherwise
	*/
	public boolean vacio()
	{ 
		return raiz == null; 
	}
	
	/**
	* Method borrar will ask if the binary search tree is empty.
	* @param w select the node to delete
	* @return boolean If it is located it will return true, false otherwise
	*/
	public boolean borrar( AnyType w)
	{ 
		NodoArbol<AnyType> y = null,py = null,x = null,px = null;
		NodoArbol<AnyType> suce = raiz,pad_suce = raiz,nodo_borrar=null, pad_nodo_borrar=null;
	 
		boolean localiza = false;
		pad_nodo_borrar = null;
		nodo_borrar = raiz;

		while( !localiza && nodo_borrar != null)// Locating the node to delete
		{	
			if( w.compareTo(nodo_borrar.dato) == 0 )
				localiza = true; // !Eureka
			else if( w.compareTo(nodo_borrar.dato) < 0 )
			{  
				pad_nodo_borrar = nodo_borrar;
				nodo_borrar  = nodo_borrar.izq;
			}
			else
			{ 
				pad_nodo_borrar = nodo_borrar;
				nodo_borrar  = nodo_borrar.der;
			}
		}
 
		if (localiza)
		{  
			if ((nodo_borrar.izq == null) || (nodo_borrar.der == null))
			{ 
			    y = nodo_borrar;   // Node to delete
				py = pad_nodo_borrar;
			}
			
			else // If the node have more than two succesors
			{ //searching for the succesors
				suce = nodo_borrar.der;
				pad_suce =nodo_borrar;
				while(suce.izq != null)// Go to the left as much as it is possible
				{	
					pad_suce = suce;
					suce = suce.izq;
				}
			  
				y = suce;  // The node for deleting is the succesor
				py = pad_suce;
			}

			if (y.izq != null) // If the node have a left son
				x = y.izq;
			else
				x = y.der;  // If don't have children it will be null

			if (x != null)  // If the node to delete have a son
				px= py;   //  Father of x = Father of y

			if (py == null) // If the node to read is the roots
				raiz = x;
			else if (y.equals(py.izq))  // Make the link py->izq o py->der to x
				py.izq = x;
			else
				py.der = x;

			if (!y.equals( nodo_borrar))
				nodo_borrar.dato = y.dato;

			y.izq=null; // Desconect the node before deleting it 
			y.der=null;
			y = null;  // Delete the node
		 
			return true;
		}
		
		return false;
    }   
}