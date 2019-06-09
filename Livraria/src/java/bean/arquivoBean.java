package bean;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.nio.file.Files;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import model.Livro;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;
import util.ArquivoUtil;

@ManagedBean
@ViewScoped
public class arquivoBean implements Serializable {

    private static final long serialVersionUID = 1L;
    private UploadedFile uploadedFile;
    private StreamedContent streamedContent;

    public void download(Livro livro) throws FileNotFoundException, IOException {
        File file = new File("Caminho absoluto" + livro.getId() + ".pdf"); //Caminho absoluto de arquivos
        InputStream inputStream = new FileInputStream(file);

        streamedContent = new DefaultStreamedContent(inputStream, Files.probeContentType(file.toPath()), livro.getTitulo() + ".pdf");

    }

    public void upload(String nomeFile, String pasta) {
        try {
            File arquivo = ArquivoUtil.escrever(nomeFile, uploadedFile.getContents(), pasta);
            System.out.println("Caminho arquivo: " + arquivo.getAbsolutePath());
            System.out.println("Salvo com Sucesso!");
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Upload completo", "O arquivo " + nomeFile + " foi salvo!"));
        } catch (IOException e) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN, "Erro", e.getMessage()));
        }
    }

    public StreamedContent getStreamedContent() {
        return streamedContent;
    }

    public void setStreamedContent(StreamedContent streamedContent) {
        this.streamedContent = streamedContent;
    }

    public UploadedFile getUploadedFile() {
        return uploadedFile;
    }

    public void setUploadedFile(UploadedFile uploadedFile) {
        this.uploadedFile = uploadedFile;
    }

    public boolean verificar(Livro livro) {
        File file = new File("caminho absoluto" + livro.getId() + ".pdf"); //caminho absoluto arquivos
        return file.exists();
    }

}
