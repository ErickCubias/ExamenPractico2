
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author Erick
 */
public class Biblioteca extends JFrame implements ActionListener {

    private JTextField txtTitulo;
    private JTextField txtAutor;
    private JTextArea txtAreaResultado;
    private List<Libro> libros;

    public Biblioteca() {
        super("Biblioteca");

        libros = new ArrayList<>();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLayout(new BorderLayout());
        setResizable(false);
        setLocationRelativeTo(null);

        // panel de formulario para agregar libros
        JPanel panelFormulario = new JPanel(new GridLayout(2, 2, 10, 10));
        panelFormulario.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));

        JLabel lblTitulo = new JLabel("Titulo: ");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 16));

        txtTitulo = new JTextField();
        txtTitulo.setFont(new Font("Arial", Font.PLAIN, 16));

        JLabel lblAutor = new JLabel("Autor: ");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 16));

        txtAutor = new JTextField();
        txtAutor.setFont(new Font("Arial", Font.PLAIN, 16));

        panelFormulario.add(lblTitulo);
        panelFormulario.add(txtTitulo);
        panelFormulario.add(lblAutor);
        panelFormulario.add(txtAutor);

        JButton btnAgregar = new JButton("Agregar");
        btnAgregar.addActionListener(this);
        btnAgregar.setFont(new Font(" Arial", Font.BOLD, 16));
        btnAgregar.setBackground(new Color(0, 153, 51));
        btnAgregar.setForeground(Color.WHITE);
        btnAgregar.setPreferredSize(new Dimension(200, 40));

        JButton btnEliminar = new JButton("Eliminar");
        btnEliminar.addActionListener(this);
        btnEliminar.setFont(new Font(" Arial", Font.BOLD, 16));
        btnEliminar.setBackground(new Color(204, 0, 0));
        btnEliminar.setForeground(Color.WHITE);
        btnEliminar.setPreferredSize(new Dimension(200, 40));

        JButton btnBuscar = new JButton("Buscar");
        btnBuscar.addActionListener(this);
        btnBuscar.setFont(new Font(" Arial", Font.BOLD, 16));
        btnBuscar.setBackground(new Color(0, 102, 204));
        btnBuscar.setForeground(Color.WHITE);
        btnBuscar.setPreferredSize(new Dimension(200, 40));

        JButton btnInventario = new JButton("inventario");
        btnInventario.addActionListener(this);
        btnInventario.setFont(new Font(" Arial", Font.BOLD, 16));
        btnInventario.setBackground(new Color(255, 153, 0));
        btnInventario.setForeground(Color.WHITE);
        btnInventario.setPreferredSize(new Dimension(200, 40));

        panelFormulario.add(btnAgregar);
        panelFormulario.add(btnEliminar);
        panelFormulario.add(btnBuscar);
        panelFormulario.add(btnInventario);

        txtAreaResultado = new JTextArea();
        txtAreaResultado.setEditable(false);
        txtAreaResultado.setFont(new Font("Arial", Font.PLAIN, 16));
        txtAreaResultado.setLineWrap(true);
        txtAreaResultado.setWrapStyleWord(true);
        txtAreaResultado.setBackground(new Color(240, 240, 240));
        txtAreaResultado.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        JScrollPane scrollPane = new JScrollPane(txtAreaResultado);
        scrollPane.setPreferredSize(new Dimension(540, 200));

        JPanel panelPrincipal = new JPanel(new BorderLayout());
        panelPrincipal.add(panelFormulario, BorderLayout.NORTH);
        panelPrincipal.add(scrollPane, BorderLayout.SOUTH);
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(120, 20, 20, 20));

        add(panelPrincipal);

        setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Agregar")) {
            String titulo = txtTitulo.getText();
            String autor = txtAutor.getText();
            if (!titulo.isEmpty() && !autor.isEmpty()) {
                agregarLibro(titulo, autor);
                txtTitulo.setText("");
                txtAutor.setText("");
                txtAreaResultado.setText("El libro se ha añadido correctamente");
            } else {
                txtAreaResultado.setText("Por favor introduce el titulo y autor del libro");
            }
        } else if (e.getActionCommand().equals("Eliminar")) {
            String titulo = txtTitulo.getText();
            eliminarLibro(titulo);
            txtTitulo.setText("");
            txtAutor.setText("");
            txtAreaResultado.setText("El libro se ha elimindao correctamente");
        } else if (e.getActionCommand().equals("Buscar")) {
            String terminoBusqueda = txtTitulo.getText();
            String tipoBusqueda = "titulo";
            if (!terminoBusqueda.isEmpty()) {
                List<String> resultados = buscarLibro(terminoBusqueda, tipoBusqueda);
                txtAreaResultado.setText("");
                if (resultados.isEmpty()) {

                    txtAreaResultado.setText("No se encontro librocon ese titulo" + terminoBusqueda + " .");
                } else {
                    for (String resultado : resultados) {
                        txtAreaResultado.append(resultado + "\n");
                    }
                }
            } else {
                txtAreaResultado.setText("por favor introduce un termino de busqueda");
            }
            txtTitulo.setText("");
            txtAutor.setText("");
        } else if (e.getActionCommand().equals("inventario")) {
            txtAreaResultado.setText("");
            List<String> inventario = getInventario();
            if (inventario.isEmpty()) {
                txtAreaResultado.setText("La bibliotrca esta vacia");
            } else {
                for (String libro : inventario) {
                    txtAreaResultado.append(libro + "\n");
                }
            }
            txtTitulo.setText("");
            txtAutor.setText("");

        }
    }

    public void agregarLibro(String titulo, String autor) {
        Libro nuevoLibro = new Libro(titulo, autor);
        libros.add(nuevoLibro);
    }

    public void eliminarLibro(String titulo) {
        for (int i = 0; i < libros.size(); i++) {
            Libro libro = libros.get(i);
            if (libro.getTitulo().equalsIgnoreCase(titulo)) {
                libros.remove(i);
                break;
            }
        }
    }

    public List<String> buscarLibro(String terminoBusqueda, String tipoBusqueda) {
        List<String> resultados = new ArrayList<>();

        for (Libro libro : libros) {
            if (tipoBusqueda.equalsIgnoreCase("titulo")) {
                if (libro.getTitulo().equalsIgnoreCase(terminoBusqueda)) {
                    resultados.add(libro.toString());
                }
            } else if (tipoBusqueda.equalsIgnoreCase("autor")) {
                if (libro.getAutor().equalsIgnoreCase(terminoBusqueda)) {
                    resultados.add(libro.toString());
                }
            }
        }
        return resultados;
    }

    public List<String> getInventario() {
        List<String> inventario = new ArrayList<>();
        for (Libro libro : libros) {
            inventario.add(libro.toString());
        }
        return inventario;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
            } catch (IllegalAccessException ex) {
                ex.printStackTrace();
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(Biblioteca.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InstantiationException ex) {
                Logger.getLogger(Biblioteca.class.getName()).log(Level.SEVERE, null, ex);
            } catch (UnsupportedLookAndFeelException ex) {
                Logger.getLogger(Biblioteca.class.getName()).log(Level.SEVERE, null, ex);
            }
            new Biblioteca();
        });
    }

}
