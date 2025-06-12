import java.io.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class TrabalhoInterfaceDB extends JFrame {
    private JLabel labelNome, labelQuantidadeEstoque, labelDataValidade, labelCodigoProduto;
    private JTextField tfNome, tfQuantidadeEstoque, tfCodigoProduto;
    private JFormattedTextField tfDataValidade;
    private JButton btInserir, btListar, btLimpar; 
    private File pasta = new File("./meuProduto");
    private File arquivo = new File(pasta, "novoProduto.txt");

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new TrabalhoInterfaceDB();
        });
    }

    public TrabalhoInterfaceDB() {
        setTitle("Cadastro de Produtos");
        setSize(350, 300);
        setLayout(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        labelCodigoProduto = new JLabel("Código do produto:");
        tfCodigoProduto = new JTextField();

        labelNome = new JLabel("Nome do produto:");
        labelQuantidadeEstoque = new JLabel("Quantidade:");
        labelDataValidade = new JLabel("Data de validade (DD/MM/AAAA):");
        tfNome = new JTextField();
        tfQuantidadeEstoque = new JTextField();

        try {
            MaskFormatter mask = new MaskFormatter("##/##/####");
            mask.setPlaceholderCharacter('_');
            tfDataValidade = new JFormattedTextField(mask);
        } catch (java.text.ParseException e) {
            e.printStackTrace();
            tfDataValidade = new JFormattedTextField();
        }

        btInserir = new JButton("Inserir");
        btListar = new JButton("Listar produtos");
        btLimpar = new JButton("Limpar Produtos"); 

        labelCodigoProduto.setBounds(10, 10, 150, 20);
        tfCodigoProduto.setBounds(10, 30, 100, 20);
        labelNome.setBounds(10, 55, 150, 20);
        tfNome.setBounds(10, 75, 300, 20);
        labelQuantidadeEstoque.setBounds(10, 100, 150, 20);
        tfQuantidadeEstoque.setBounds(10, 120, 100, 20);
        labelDataValidade.setBounds(10, 145, 200, 20);
        tfDataValidade.setBounds(10, 165, 120, 20);
        btInserir.setBounds(10, 195, 100, 25);
        btListar.setBounds(120, 195, 150, 25);
        btLimpar.setBounds(10, 230, 150, 25);

        add(labelCodigoProduto);
        add(tfCodigoProduto);
        add(labelNome);
        add(tfNome);
        add(labelQuantidadeEstoque);
        add(tfQuantidadeEstoque);
        add(labelDataValidade);
        add(tfDataValidade);
        add(btInserir);
        add(btListar);
        add(btLimpar); 

        btInserir.addActionListener(e -> inserirProdutosEstoque());
        btListar.addActionListener(e -> exibirListaDeProdutos());
        btLimpar.addActionListener(e -> limparTodosProdutos()); 

        setVisible(true);
    }

    private List<String[]> carregarProdutosEstoque() {
        List<String[]> produtos = new ArrayList<>();

        if (arquivo.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(arquivo))) {
                String linha;
                while ((linha = reader.readLine()) != null) {
                    String[] partes = linha.split(";");
                    if (partes.length == 4) {
                        produtos.add(new String[]{partes[0].trim(), partes[1].trim(), partes[2].trim(), partes[3].trim()});
                    }
                }
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Erro ao ler o arquivo: " + ex.getMessage());
            }
        }
        return produtos;
    }

    private void inserirProdutosEstoque() {
        String codigo = tfCodigoProduto.getText().trim();
        String nome = tfNome.getText().trim();
        String produtoQuantidade = tfQuantidadeEstoque.getText().trim();
        String dataString = tfDataValidade.getText().trim();

        if (codigo.isEmpty() || nome.isEmpty() || produtoQuantidade.isEmpty() || dataString.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, preencha todos os campos.");
            return;
        }

        int quantidade;
        try {
            quantidade = Integer.parseInt(produtoQuantidade);
            if (quantidade <= 0) {
                JOptionPane.showMessageDialog(this, "Quantidade inválida. Digite um número inteiro maior que zero.");
                return;
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Quantidade inválida. Digite apenas números.");
            return;
        }

        try {
            Integer.parseInt(codigo);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Código do produto inválido. Digite apenas números.");
            return;
        }

        LocalDate dataValidade;
        try {
            dataValidade = LocalDate.parse(dataString, DATE_FORMATTER);

            if (!dataString.matches("\\d{2}/\\d{2}/\\d{4}")) {
                 JOptionPane.showMessageDialog(this, "Formato de data inválido. Use DD/MM/AAAA. Ex: 01/01/2025");
                 return;
            }

            if (dataValidade.isBefore(LocalDate.now())) {
                JOptionPane.showMessageDialog(this, "A data de validade não pode ser anterior à data atual (" + LocalDate.now().format(DATE_FORMATTER) + ").");
                return;
            }
        } catch (DateTimeParseException ex) {
            JOptionPane.showMessageDialog(this, "Data de validade inválida. Use o formato DD/MM/AAAA.");
            return;
        }

        if (!pasta.exists()) {
            pasta.mkdirs();
        }

        List<String[]> produtos = carregarProdutosEstoque();

        for (String[] produto : produtos) {
            if (produto[0].equalsIgnoreCase(codigo)) {
                JOptionPane.showMessageDialog(this, "Já existe um produto com este CÓDIGO cadastrado.");
                return;
            }
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(arquivo, true))) {
            writer.write(codigo + ";" + nome + ";" + produtoQuantidade + ";" + dataString);
            writer.newLine();
            JOptionPane.showMessageDialog(this, "Produto inserido com sucesso!");
            tfCodigoProduto.setText("");
            tfNome.setText("");
            tfQuantidadeEstoque.setText("");
            tfDataValidade.setText("");
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao escrever no arquivo: " + ex.getMessage());
        }

    }

    private void exibirListaDeProdutos() {
        List<String[]> produtos = carregarProdutosEstoque();

        if (produtos.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nenhum produto cadastrado.");
        } else {
            String mensagem = "Produtos cadastrados:\n";
            for (String[] produto : produtos) {
                mensagem += "- Código: " + produto[0] + " | Nome: " + produto[1] + " | Quantidade: " + produto[2] + " | Validade: " + produto[3] + "\n";
            }
            JOptionPane.showMessageDialog(this, mensagem);
        }
    }

    private void limparTodosProdutos() {
        int confirm = JOptionPane.showConfirmDialog(this, 
                      "Tem certeza que deseja apagar TODOS os produtos cadastrados?\nEsta ação não pode ser desfeita!", 
                      "Confirmar Limpeza", 
                      JOptionPane.YES_NO_OPTION, 
                      JOptionPane.WARNING_MESSAGE);

        if (confirm == JOptionPane.YES_OPTION) {
            if (arquivo.exists()) {
                if (arquivo.delete()) {
                    JOptionPane.showMessageDialog(this, "Todos os produtos foram removidos com sucesso!");
                } else {
                    JOptionPane.showMessageDialog(this, "Erro ao remover os produtos. O arquivo pode estar em uso ou não há permissão.");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Nenhum arquivo de produtos encontrado para limpar.");
            }
        }
    }
}