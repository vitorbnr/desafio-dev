import java.io.*;
import java.util.ArrayList;
import java.util.List;

class FormHandler {
    private static final String FORM_FILE = "formulario.txt";
    private List<String> perguntas = new ArrayList<>();

    public void carregarPerguntas() throws IOException {
        perguntas.clear();
        try (BufferedReader br = new BufferedReader(new FileReader(FORM_FILE))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                perguntas.add(linha);
            }
        }
    }

    public void exibirPerguntas() {
        for (String pergunta : perguntas) {
            System.out.println(pergunta);
        }
    }

    public void adicionarPergunta(String pergunta) throws IOException {
        perguntas.add(pergunta);
        salvarPerguntas();
    }

    public void deletarPergunta(int index) throws IOException {
        if (index < 4) {
            throw new IllegalArgumentException("Não é possível apagar as 4 primeiras perguntas.");
        }
        perguntas.remove(index);
        salvarPerguntas();
    }

    private void salvarPerguntas() throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FORM_FILE))) {
            for (String pergunta : perguntas) {
                bw.write(pergunta);
                bw.newLine();
            }
        }
    }

    public List<String> getPerguntas() {
        return perguntas;
    }
}
