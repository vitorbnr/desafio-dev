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



