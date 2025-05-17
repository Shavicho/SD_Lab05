import org.w3c.dom.*;
import javax.xml.parsers.*;
import java.io.File;

public class DomParserExample {
    public static void main(String[] args) {
        try {
            File inputFile = new File("Ej01.xml");
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setValidating(false);
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(inputFile);
            doc.getDocumentElement().normalize();

            System.out.println("Elemento raíz: " + doc.getDocumentElement().getNodeName());

            // Imprime Título
            NodeList tituloList = doc.getElementsByTagName("TITULO");
            if (tituloList.getLength() > 0) {
                System.out.println("Título: " + tituloList.item(0).getTextContent());
            }

            // Imprime FECHA
            NodeList fechaList = doc.getElementsByTagName("FECHA");
            if (fechaList.getLength() > 0) {
                Element fecha = (Element) fechaList.item(0);
                System.out.println("Fecha: " +
                    fecha.getElementsByTagName("AAAA").item(0).getTextContent() + "-" +
                    fecha.getElementsByTagName("MM").item(0).getTextContent() + "-" +
                    fecha.getElementsByTagName("DD").item(0).getTextContent());
            }

            // Imprime todas las SECCIONES y APARTADOS
            NodeList secciones = doc.getElementsByTagName("SECCION");
            for (int i = 0; i < secciones.getLength(); i++) {
                Element seccion = (Element) secciones.item(i);
                System.out.println("\nSECCION: " + seccion.getElementsByTagName("TIT-SECCION").item(0).getTextContent());

                NodeList apartados = seccion.getElementsByTagName("APARTADO");
                for (int j = 0; j < apartados.getLength(); j++) {
                    Element apartado = (Element) apartados.item(j);
                    System.out.println("  APARTADO - Organismo: " + apartado.getElementsByTagName("ORGANISMO").item(0).getTextContent());

                    NodeList parrafos = apartado.getElementsByTagName("PARRAFO");
                    for (int k = 0; k < parrafos.getLength(); k++) {
                        Element parrafo = (Element) parrafos.item(k);
                        System.out.println("    PARRAFO:");

                        // TEXTO del párrafo
                        System.out.println("      Texto: " + parrafo.getElementsByTagName("TEXTO").item(0).getTextContent());

                        // Todas las PÁGINAS del párrafo
                        NodeList paginas = parrafo.getElementsByTagName("PAGINA");
                        System.out.print("      Páginas: ");
                        for (int p = 0; p < paginas.getLength(); p++) {
                            System.out.print(paginas.item(p).getTextContent());
                            if (p < paginas.getLength() - 1) System.out.print(", ");
                        }
                        System.out.println();
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
