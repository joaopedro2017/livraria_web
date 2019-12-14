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
//        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
//        String diretorio = ec.getRealPath("\\Livraria");
        File file = new File("C:\\Users\\John Peter\\Documents\\GitHub\\livraria_web\\Livraria\\web\\upload\\arquivos\\" + livro.getId() + ".pdf"); //Caminho absoluto de arquivos
        InputStream inputStream = new FileInputStream(file);

        streamedContent = new DefaultStreamedContent(inputStream, Files.probeContentType(file.toPath()), livro.getTitulo() + ".pdf");
    }

    public void upload(String nomeFile, String pasta) {
        try {
            File arquivo = ArquivoUtil.escrever(nomeFile, uploadedFile.getContents(), pasta);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Upload completo", "O arquivo " + uploadedFile.getFileName() + " foi salvo!"));
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
//        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
//        String diretorio = ec.getRealPath("\\Livraria");
        File file = new File("C:\\Users\\John Peter\\Documents\\GitHub\\livraria_web\\Livraria\\web\\upload\\arquivos\\" + livro.getId() + ".pdf");
        return file.exists();
    }

}
