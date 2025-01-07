import java.io.*;
import java.util.*;

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



