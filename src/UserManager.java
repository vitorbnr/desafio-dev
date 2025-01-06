import java.io.*;
import java.util.ArrayList;
import java.util.List;

class UserManager {
    private static final String USERS_FOLDER = "usuarios";

    public UserManager() {
        File folder = new File(USERS_FOLDER);
        if (!folder.exists()) {
            folder.mkdir();
        }
    }

    public void salvarUsuario(User user) throws IOException {
        String fileName = USERS_FOLDER + "/" + user.getNome().replaceAll(" ", "").toUpperCase() + ".txt";
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName))) {
            bw.write(user.toString());
        }
    }

    public List<String> listarUsuarios() {
        File folder = new File(USERS_FOLDER);
        File[] files = folder.listFiles();
        List<String> nomes = new ArrayList<>();

        if (files != null) {
            for (File file : files) {
                String nome = file.getName().replaceAll(".txt", "");
                nomes.add(nome);
            }
        }

        return nomes;
    }

    public List<User> buscarUsuarios(String termo) throws IOException {
        File folder = new File(USERS_FOLDER);
        File[] files = folder.listFiles();
        List<User> resultados = new ArrayList<>();

        if (files != null) {
            for (File file : files) {
                try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                    String linha = br.readLine();
                    if (linha != null && linha.toLowerCase().contains(termo.toLowerCase())) {
                        String[] partes = linha.split(" ");
                        String nome = partes[0];
                        String email = partes[1];
                        int idade = Integer.parseInt(partes[2]);
                        double altura = Double.parseDouble(partes[3].replace(",", "."));
                        resultados.add(new User(nome, email, idade, altura));
                    }
                }
            }
        }

        return resultados;
    }
}
