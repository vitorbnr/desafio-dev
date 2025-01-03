import java.io.*;
import java.util.*;

// Classe que representa um usuário
class User {
    private String nome;
    private String email;
    private int idade;
    private double altura;

    public User(String nome, String email, int idade, double altura) {
        this.nome = nome;
        this.email = email;
        this.idade = idade;
        this.altura = altura;
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public int getIdade() {
        return idade;
    }

    public double getAltura() {
        return altura;
    }

    @Override
    public String toString() {
        return String.format("%s %s %d %.2f", nome, email, idade, altura);
    }
}

// Classe para manipular o formulário
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

// Classe para gerenciar usuários
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

// Classe principal
public class Main {
    public static void main(String[] args) {
        FormHandler formHandler = new FormHandler();
        UserManager userManager = new UserManager();
        Scanner scanner = new Scanner(System.in);

        try {
            formHandler.carregarPerguntas();

            while (true) {
                System.out.println("\nMenu Principal");
                System.out.println("1 - Cadastrar Usuário");
                System.out.println("2 - Listar Usuários");
                System.out.println("3 - Cadastrar Nova Pergunta");
                System.out.println("4 - Deletar Pergunta");
                System.out.println("5 - Pesquisar Usuário");
                System.out.println("0 - Sair");
                System.out.print("Escolha uma opção: ");
                int opcao = scanner.nextInt();
                scanner.nextLine(); // Consumir a quebra de linha

                switch (opcao) {
                    case 1:
                        System.out.println("\nPreencha o formulário:");
                        List<String> respostas = new ArrayList<>();
                        for (String pergunta : formHandler.getPerguntas()) {
                            System.out.print(pergunta + " ");
                            respostas.add(scanner.nextLine());
                        }

                        String nome = respostas.get(0);
                        String email = respostas.get(1);
                        int idade = Integer.parseInt(respostas.get(2));
                        double altura = Double.parseDouble(respostas.get(3).replace(",", "."));

                        User user = new User(nome, email, idade, altura);
                        userManager.salvarUsuario(user);
                        System.out.println("Usuário salvo com sucesso!");
                        break;

                    case 2:
                        System.out.println("\nUsuários cadastrados:");
                        for (String nomeUsuario : userManager.listarUsuarios()) {
                            System.out.println(nomeUsuario);
                        }
                        break;

                    case 3:
                        System.out.print("Digite a nova pergunta: ");
                        String novaPergunta = scanner.nextLine();
                        formHandler.adicionarPergunta(novaPergunta);
                        System.out.println("Pergunta adicionada com sucesso!");
                        break;

                    case 4:
                        System.out.print("Digite o número da pergunta a ser deletada: ");
                        int numeroPergunta = scanner.nextInt() - 1;
                        scanner.nextLine(); // Consumir a quebra de linha
                        formHandler.deletarPergunta(numeroPergunta);
                        System.out.println("Pergunta deletada com sucesso!");
                        break;

                    case 5:
                        System.out.print("Digite o termo para busca: ");
                        String termo = scanner.nextLine();
                        List<User> resultados = userManager.buscarUsuarios(termo);
                        System.out.println("\nResultados da busca:");
                        for (User u : resultados) {
                            System.out.println(u);
                        }
                        break;

                    case 0:
                        System.out.println("Saindo...");
                        return;

                    default:
                        System.out.println("Opção inválida!");
                }
            }
        } catch (Exception e) {
            System.err.println("Erro: " + e.getMessage());
        }
    }
}

//TESTE GIT
