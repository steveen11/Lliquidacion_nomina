import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// Definimos la clase Empleado, que contendrá toda la información y métodos para manejar los datos de cada empleado.
class Empleado {
    private String nombres;
    private String apellidos;
    private String documento;
    private double salario;
    private int diasTrabajados;
    private double subsidioTransporte;
    private double devengos;
    private double descuentos;
    private double netoAPagar;

    // Constantes para el salario mínimo y el subsidio de transporte en Colombia.
    private static final double SALARIO_MINIMO = 1300606;
    private static final double SUBSIDIO_TRANSPORTE = 160000;

    // Constructor de la clase Empleado que inicializa los atributos del empleado.
    public Empleado(String nombres, String apellidos, String documento, double salario, int diasTrabajados) {
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.documento = documento;
        this.salario = salario;
        this.diasTrabajados = diasTrabajados;
        // Calcula si el empleado tiene derecho a subsidio de transporte.
        this.subsidioTransporte = salario <= 2 * SALARIO_MINIMO ? SUBSIDIO_TRANSPORTE : 0;
        this.devengos = 0;
        this.descuentos = 0;
        this.netoAPagar = 0;
    }

    // Método para calcular los devengos del empleado.
    public void calcularDevengos() {
        double salarioDia = salario / 30;  // Calcula el salario diario.
        double salarioTotal = salarioDia * diasTrabajados;  // Calcula el salario total según los días trabajados.
        this.devengos = salarioTotal + this.subsidioTransporte;  // Suma el subsidio de transporte si corresponde.
    }

    // Método para calcular los descuentos del empleado.
    public void calcularDescuentos() {
        double descuentoSalud = salario * 0.04;  // Calcula el descuento por salud (4%)
        double descuentoPension = salario * 0.04;  // Calcula el descuento por pensión (4%)
        double descuentoSolidaridad = salario > 4 * SALARIO_MINIMO ? salario * 0.01 : 0;  // Calcula el fondo de solidaridad si aplica (1%)
        this.descuentos = descuentoSalud + descuentoPension + descuentoSolidaridad;  // Suma descuentos
    }

    // Método para calcular el valor neto a pagar al empleado
    public void calcularNetoAPagar() {
        calcularDevengos();  // Primero calcula los devengos
        calcularDescuentos();  // Luego calcula los descuentos
        this.netoAPagar = this.devengos - this.descuentos;
    }

    // Método para mostrar los resultados de la liquidación del empleado
    public void mostrarResultados() {
        System.out.println("Empleado: " + this.nombres + " " + this.apellidos);
        System.out.println("Documento: " + this.documento);
        System.out.println("Días trabajados: " + this.diasTrabajados);
        System.out.println("Salario Bruto: " + String.format("%.2f", this.devengos));
        System.out.println("Descuentos: " + String.format("%.2f", this.descuentos));
        System.out.println("Valor Neto a Pagar: " + String.format("%.2f", this.netoAPagar));
        System.out.println("------------------------------");
    }
}

// Clase principal del programa
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);  // Se crea un objeto Scanner para leer la entrada del usuario
        List<Empleado> empleados = new ArrayList<>();  // Se crea una lista para almacenar los empleados

        // Ciclo para ingresar la información de varios empleados
        while (true) {
            System.out.print("Ingrese los nombres del empleado: ");
            String nombres = scanner.nextLine();
            System.out.print("Ingrese los apellidos del empleado: ");
            String apellidos = scanner.nextLine();
            System.out.print("Ingrese el documento del empleado: ");
            String documento = scanner.nextLine();
            System.out.print("Ingrese el salario mensual del empleado: ");
            double salario = scanner.nextDouble();
            System.out.print("Ingrese los días trabajados por el empleado: ");
            int diasTrabajados = scanner.nextInt();
            scanner.nextLine();

            // Crear un nuevo objeto Empleado con la información ingresada
            Empleado empleado = new Empleado(nombres, apellidos, documento, salario, diasTrabajados);
            empleado.calcularNetoAPagar();  // Calcula el valor neto a pagar al empleado
            empleados.add(empleado);  // Añade el empleado a la lista

            System.out.print("¿Desea agregar otro empleado? (s/n): ");
            String continuar = scanner.nextLine().trim().toLowerCase();  // Lee la respuesta del usuario
            if (!continuar.equals("s")) {  // Si la respuesta no es 's', sale del ciclo
                break;
            }
        }

        System.out.println("\nResultados de la Liquidación de Nómina\n");
        for (Empleado empleado : empleados) {
            empleado.mostrarResultados();  // Muestra los resultados para cada emplead
        }

        scanner.close();  // Cierra Scanner.
    }
}
