import java.io.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
* <h1>GUI</h1>
* The GUI program implements an application that
* serves as a Sell Point that manage a .dat file for management of products,
* the stock and to print bills of the bought products.
* <p>
* <b>Note:</b> This program doesn't have any validators,
* so exceptions could be thrown regullarly because of this.
* 
* @author Librado Jimenez
* @author  Yamil Elías
* @version 1.1
* @since   2016-02-03
*/

public class GUI extends JFrame implements ActionListener
{ 
	int lineas = 0;
	double subtotal = 0;
	Producto producto;
	ABB<Producto> miArbol;
	JButton bAlta,bAgregar,bFacturar, bListado,bBorrar, bBorrarNodo;
	JTextArea area;
	JPanel p1,p2,p3,p4, p5, p6, p7, p8;
	Container contenedor = getContentPane(); // Generate a container
	JLabel eClave,eDescripcion, ePrecio,eCantidad, imagen;
	JTextField tClave, tDescripcion, tPrecio, tCantidad;
	File archivo1;
	DataInputStream archEntrada; 
	DataOutputStream archSalida;
	BufferedImage myPicture = ImageIO.read(new File("logo.png"));
	
	/**
   * This method is the constructor of the application.
   * Because of this it won't have any return parameter.
   * @see javax.swing
   * @see java.awt.event
   * @throws IOException If File wasn't read or there was a malfunction.
   */
	public GUI() throws IOException
	{  
		super("Punto de venta - labguru"); // Title of the JFramce
		contenedor.setLayout(new GridLayout(3,1));// Layout of the container
		miArbol = new ABB<Producto>();

		// Create buttons
		bAlta = new JButton("Al archivo");
		bAlta.addActionListener(this);
		bAgregar = new JButton("Agregar ");
		bAgregar.addActionListener(this);
		bFacturar = new JButton("Facturar");
		bFacturar.addActionListener(this);
		bFacturar.setEnabled(false);
		bListado = new JButton("Listado ");
		bListado.addActionListener(this);
		bBorrar = new JButton("Borrar");
		bBorrar.addActionListener(this);
		bBorrarNodo = new JButton("Eliminar producto");
		bBorrarNodo.addActionListener(this);
		 
		area = new JTextArea();

		// Create labels
		eClave = new JLabel("Clave");
		eClave.setHorizontalAlignment(SwingConstants.TRAILING);
		eDescripcion = new JLabel("Descripcion");
		ePrecio = new JLabel("Precio");
		eCantidad = new JLabel("Cantidad");

		// Create textfields
		tClave = new JTextField("",2);
		tDescripcion = new JTextField("",10);
		tPrecio = new JTextField("",4);
		tCantidad = new JTextField("",4);

		// Create JPanels
		p1 = new JPanel(new FlowLayout());
		p1.setPreferredSize(new Dimension(600, 600));
		p2 = new JPanel(new FlowLayout());
		p3 = new JPanel(new FlowLayout());
		p4 = new JPanel(new FlowLayout());
		p8 = new JPanel(new GridLayout(3,1));

		// Add items to panel
		imagen = new JLabel(new ImageIcon(myPicture));
		p1.add(imagen);
		
		p2.add(eClave);
		p2.add(tClave);
		p2.add(eDescripcion);
		p2.add(tDescripcion);
		p2.add(ePrecio);
		p2.add(tPrecio);
		
		p2.add(eCantidad);
		p2.add(tCantidad);

		p3.add(bAlta);
		p3.add(bAgregar);
		p3.add(bFacturar);
		p3.add(bListado);
		p3.add(bBorrar);
		p3.add(bBorrarNodo);
		 
		p8.add(p2);
		p8.add(p3);
		
		contenedor.add(p1);
		contenedor.add(p8);
		contenedor.add(new JScrollPane(area));

		setSize(750,750);  // Define frame size
		setVisible(true); // Make Frame visible
	}
 
	/**
   * This is the main method which initialize the program. This method also
   * call another method to read the file when application starts.
   * @param arg Unused.
   */
	public static void main(String arg[])
    { 
		try 
		{
			GUI aplicacion = new GUI();// Execute the constructor
			aplicacion.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // When application close
			aplicacion.leeArchivo();
		} 
		catch (IOException ex)
	    { 
			System.out.println("ERROR AL manejar los archivos");
		}
    } 

	/**
	* This method return a boolean value that it can only be true,
	* because that's the only case it can read the file. If a file isn't found by method
	* then it creates it.
	* @return boolean True only if file has been read.
	* @see java.io.DataInputStream
	* @see java.io.File
	* @throws IOException If File wasn't read or there was a malfunction.
	*/
	public boolean leeArchivo() throws IOException
	{  
		File archivo1 = new File("Productos.dat");
		DataInputStream archEntrada = new DataInputStream(new FileInputStream(archivo1));// Archivo de Lectura
		boolean EOF = false; // (EOF = end of file), it will be true when it reach the end of the file
		int clave;
		String des;
		double precio;
		area.setText("En el archivo los productos estan en este orden:");	  
		while (!EOF) // Read the data that has been read
		{ 
			try 
			{
				clave = archEntrada.readInt();
				des = archEntrada.readUTF();
				precio = archEntrada.readDouble();
				producto = new Producto(des,clave,precio);
				area.append(producto.toString());
				if (!miArbol.inserta(producto)) // If it can't be inserted on ABB
					area.append("\nEl producto no se pudo insertar en el ABB");

			} 
			catch (IOException ex)
			{
				area.append("\n Fin del archivo");
				EOF = true;
			}

		}

		area.append("\nLos productos ya estan en el arbol binario de busqueda");
		archEntrada.close();
		return (true);	 
   } 
    
	/**
	* This method don't have a return, is void.
	* It can write a product into the file for reading it later.
	* If file doesn't exist, then it creates it.
	* @see java.io.DataOutputStream
	* @see java.io.File
	* @throws IOException If File wasn't read or there was a malfunction.
	*/
	public void alta() throws IOException // New product to file and tree
	{ 
		File archivo1 = new File("Productos.dat");
		DataOutputStream archSalida = new DataOutputStream(new FileOutputStream(archivo1,true));// Writing file

		int clave = Integer.parseInt(tClave.getText()); // Take data from textfields
		String des = tDescripcion.getText();
		double precio = Double.parseDouble(tPrecio.getText());
		producto = new Producto(des,clave,precio); // New Product object constructed
		if(miArbol.busca(producto)==null) // If it isn't in the tree
		{
			try 
			{
				archSalida.writeInt(clave);  // Put data
				archSalida.writeUTF(des);
				archSalida.writeDouble(precio);
			} 
			catch (IOException ex)
			{
				area.append("\n Problemas al meter el producto al archivo"); 
			}
			
			area.append("\nel producto a sido metido al archivo");
		}

		if(!miArbol.inserta(producto)) //Tryin to insert product on tree
			area.append("\nEsa clave ya esta en el archivo y no se inserto");
		else	
			area.append("\nel producto a sido metido al ABB");

		archSalida.close(); 
	}

	/**
	* Prints out all products that are on the tree (that previously were inserted into it).
	* @see ABB
	* @throws IOException If File wasn't read or there was a malfunction.
	*/
	public void listado() throws IOException
	{ 
		area.setText("clave		Descripcion			Precio");
		miArbol.imprime(area); //Imprimir el arbol 	
	}

	/**
	* This method adds a product to the bill to calculate it later.
	* Is a void method, so it won't return anything, it only writes on the TextArea.
	* @see NodoArbol
	*/
    public void agregar() // Add product to the bill
	{  
		int clave = Integer.parseInt(tClave.getText());
		int cantidad = Integer.parseInt(tCantidad.getText());
		producto = new Producto("",clave,0);
		NodoArbol<Producto> nodo = miArbol.busca(producto); // Search on tree
		
		if (nodo!=null) // If Node was in the tree
			producto = (Producto)nodo.dato; // Product with Node data
		else
			producto = null;	   
		
		if (producto!=null) //If it is null
		{ 
			area.append(producto.toString());
			subtotal += producto.getPrecio()*cantidad;
			area.append("\t\t" + (producto.getPrecio()*cantidad));
			lineas++;
		}  
	}

	/**
	* This method calculates the bill with the total of the products. It have a global variable
	* that have the sum of all the products that were bought. This is a void method it don't return anything.
	*/
    public void facturar()
	{  
		area.append("\n\t\t\t\t\t\t\t" + "==========" + "\n" + "\t\t\t\t\t\t\t" + subtotal);
		area.append("\n\t\t\t\t\t\t IVA =\t" +  (subtotal*0.15));
		area.append("\n\t\t\t\t\t\t total =\t" + (subtotal*1.15));
		bFacturar.setEnabled(false);
		bAgregar.setEnabled(false);
		lineas = 0;
		subtotal = 0;
    }	   
	
	/**
	* This will erase everything that is present in the current TextArea of the application,
	* to make another one.
	*/
	public void borrar()
	{ 
		area.setText("");
		bAgregar.setEnabled(true);
		bFacturar.setEnabled(false);
		bAlta.setEnabled(true);
		bListado.setEnabled(true);
	}
	
	/**
	* Method to delete a Product from the Tree and from the File.
	* @see NodoArbol
	*/
	public void borrarNodo()
	{
		int clave = Integer.parseInt(tClave.getText());
		producto = new Producto("",clave,0);
		
		NodoArbol<Producto> nodo = miArbol.busca(producto); // Search for the product on the tree
		boolean delete = false;
		
		if (nodo!=null) // If Node was in the tree
			delete = miArbol.borrar(nodo.dato);
		
		if(delete)
		{
			try
			{
				JOptionPane.showMessageDialog(null,"El producto fue eliminado exitosamente");
				listado();
			}
			catch(IOException e)
			{
				System.out.println("" + e);
			}
		}		
		else
		{
			JOptionPane.showMessageDialog(null,"El producto no pudo ser eliminado");
		}
	}
	
	/**
	* Action Listener to the buttons that were clicked on the application.
	* For this it won't have a return value, it will do what is commanded to only.
	* @see java.awt.event.ActionListener
	*/
	public void actionPerformed(ActionEvent e)
    {   
		if (e.getSource() == bAlta)
			try 
			{
				alta();
			} 
			catch (IOException ex)
			{ 
				area.append("\nproblemas con el archivo para agregar");
			}		
		else if (e.getSource() == bListado)
			try 
			{
			listado();	  
			} 
			catch (IOException ex)
			{ 
				area.append("\nproblemas con el archivo para listado");
			}
		else if (e.getSource() == bAgregar)
		{ 
			if (lineas == 0)
			{ 
				area.setText("clave		Descripcion			Precio		total");
				bAlta.setEnabled(false);
				bListado.setEnabled(false);
				bFacturar.setEnabled(true);
			}
			
			agregar();
		}
		else if (e.getSource() == bFacturar)
		{ 
			lineas = 0;
			facturar();
		}
		else if (e.getSource() == bBorrarNodo)
		{
			borrarNodo();
		}
		else
			borrar();		  
	} // End of ActionPerformed
} // End of class