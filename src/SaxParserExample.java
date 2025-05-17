import org.xml.sax.*;
import org.xml.sax.helpers.DefaultHandler;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;

public class SaxParserExample {

    public static void main(String[] args) {
        try {
            File inputFile = new File("Ej01.xml");

            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();

            DefaultHandler handler = new DefaultHandler() {

                boolean bTitulo = false;
                boolean bTexto = false;
                boolean bPagina = false;

                StringBuilder textoBuffer = new StringBuilder();
                StringBuilder paginasBuffer = new StringBuilder();

                public void startElement(String uri, String localName, String qName, Attributes attributes) {
                    if (qName.equalsIgnoreCase("TITULO")) {
                        bTitulo = true;
                    } else if (qName.equalsIgnoreCase("TEXTO")) {
                        bTexto = true;
                        textoBuffer.setLength(0);
                    } else if (qName.equalsIgnoreCase("PAGINA")) {
                        bPagina = true;
                        paginasBuffer.append("");
                    }
                }

                public void characters(char ch[], int start, int length) {
                    if (bTitulo) {
                        System.out.println("Título: " + new String(ch, start, length));
                        bTitulo = false;
                    } else if (bTexto) {
                        textoBuffer.append(ch, start, length);
                    } else if (bPagina) {
                        paginasBuffer.append(new String(ch, start, length)).append(", ");
                    }
                }

                public void endElement(String uri, String localName, String qName) {
                    if (qName.equalsIgnoreCase("TEXTO")) {
                        System.out.println("Texto: " + textoBuffer.toString().trim());
                        bTexto = false;
                    } else if (qName.equalsIgnoreCase("PARRAFO")) {
                        // Imprime todas las páginas acumuladas para este párrafo
                        String paginas = paginasBuffer.toString();
                        if (!paginas.isEmpty()) {
                            paginas = paginas.substring(0, paginas.length() - 2); // quita la última coma y espacio
                            System.out.println("Páginas: " + paginas);
                        }
                        paginasBuffer.setLength(0);
                    } else if (qName.equalsIgnoreCase("PAGINA")) {
                        bPagina = false;
                    }
                }
            };

            saxParser.parse(inputFile, handler);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
