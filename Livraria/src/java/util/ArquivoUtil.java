package util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class ArquivoUtil {

    public static File escrever(String name, byte[] contents, String pasta) throws IOException {
        File file = new File(diretorioRaizParaArquivos(pasta), name);

        OutputStream out = new FileOutputStream(file);
        out.write(contents);
        out.close();

        return file;
    }

    public static java.io.File diretorioRaizParaArquivos(String pasta) {
        File dir;
        if ("arquivos".equals(pasta)) {
            dir = new File(diretorioRaiz(), "arquivos");
        } else {
            dir = new File(diretorioRaiz(), "capas");
        }

        if (!dir.exists()) {
            dir.mkdirs();
        }

        return dir;
    }

    public static File diretorioRaiz() {

        File dir = new File("caminho absoluto"); //Caminho absoluto de arquivos
        if (!dir.exists()) {
            dir.mkdirs();
        }

        return dir;
    }
}
