package br.jaulipo.view;

import br.jaulipo.dao.ProdutoDAO;
import br.jaulipo.factory.ConexaoDB;
import br.jaulipo.model.Produto;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class ProdutoGUI extends Application {
    private ProdutoDAO produtoDAO;
    private ObservableList<Produto> produtos;
    private TableView<Produto> tableView;
    private TextField nomeInput, quantidadeInput, precoInput;
    private ComboBox<String> statusComboBox;
    private Connection conexaoDB;

    public static void main(String[] args){
        launch(args);
    }

    @Override
    public void start(Stage palco){

        conexaoDB = ConexaoDB.conectar();
        produtoDAO = new ProdutoDAO(conexaoDB); // inicializa o objetoDAO
        produtos = FXCollections.observableArrayList(produtoDAO.listarTodos());


        palco.setTitle("Gerenciamento de Estoque de Produtos");

        VBox layout = new VBox();
        layout.setPadding(new Insets(10, 10, 10, 10 )); // distancia até borda
        layout.setSpacing(10); // distancia entre componentes

        HBox nomeProdutoHbox = new HBox();
        nomeProdutoHbox.setSpacing(10);
        Label nomeLabel = new Label("Produto:");
        nomeInput = new TextField();
        nomeProdutoHbox.getChildren().addAll(nomeLabel,nomeInput);


        Label quantidadeLabel = new Label("Quantidade:");
        quantidadeInput = new TextField();
        HBox quantidadeProdutoHbox = new HBox(quantidadeLabel,quantidadeInput);
        quantidadeProdutoHbox.setSpacing(10);

        Label precoLabel = new Label("Preço:");
        precoInput = new TextField();
        HBox precoProdutoHbox = new HBox(precoLabel,precoInput);
        precoProdutoHbox.setSpacing(10);

        Label statusLabel = new Label("Status");
        statusComboBox = new ComboBox<>();
        statusComboBox.getItems().addAll("Estoque Normal", "Estoque Baixo");
        HBox statusProdutoHbox = new HBox(statusLabel,statusComboBox);
        statusProdutoHbox.setSpacing(10);

        Button addButton = new Button("Adicionar");
        addButton.getStyleClass().add("button-success");
        addButton.setOnAction(e -> {
            Produto produto = new Produto();
            produto.setNome(nomeInput.getText());
            produto.setQuantidade(Integer.parseInt(quantidadeInput.getText()));
            produto.setPreco(Double.parseDouble(precoInput.getText().replace(",",".")));
            produto.setStatus(statusComboBox.getValue());

            produtoDAO.inserir(produto);
            produtos.setAll((produtoDAO.listarTodos())); // atualiza os produtos na tela
           limparCampos();
        });

        Button updateButton = new Button("Atualizar");
        updateButton.setOnAction(e-> {

            Produto produto = tableView.getSelectionModel().getSelectedItem();
            if(produto != null){
                produto.setNome(nomeInput.getText());
                produto.setQuantidade(Integer.parseInt(quantidadeInput.getText()));
                produto.setPreco(Double.parseDouble(precoInput.getText().replace(",",".")));
                produto.setStatus(statusComboBox.getValue());

                produtoDAO.atualizar(produto);
                produtos.setAll((produtoDAO.listarTodos())); // atualiza os produtos na tela
                limparCampos();
            }
        });

        Button deleteButton = new Button("Excluir");
        deleteButton.getStyleClass().add("button-danger");
        deleteButton.setOnAction(e-> {
            Produto produto = tableView.getSelectionModel().getSelectedItem();
            if(produto != null){
                produtoDAO.excluir(produto.getId());
                produtos.setAll((produtoDAO.listarTodos())); //Atualiza tela
            }
        });

        Button clearButton = new Button("Limpar");
        clearButton.getStyleClass().add("button-neutral");
        clearButton.setOnAction(e-> limparCampos());


        tableView = new TableView<>();
        tableView.setItems(produtos); // define itens na tabela
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS); //AJUSTE DE TAMANHO DAS COLUNAS
        List<TableColumn<Produto, ?>> columns = List.of(
                criarColuna("ID", "id"),
                criarColuna("Produto", "nome"),
                criarColuna("Quantidade", "quantidade"),
                criarColuna("Preço","preco"),
                criarColuna("Status", "status")
        );
        tableView.getColumns().addAll(columns);

        tableView.getSelectionModel().selectedItemProperty().addListener((obs,oldSelection,newSelection)-> {
            if (newSelection!=null){
                nomeInput.setText(newSelection.getNome());
                quantidadeInput.setText(String.valueOf(newSelection.getQuantidade()));
                precoInput.setText(String.valueOf(newSelection.getPreco()));
                statusComboBox.setValue(newSelection.getStatus());
            }
        });

        HBox buttonHbox = new HBox(addButton,updateButton, deleteButton,clearButton);
        buttonHbox.setSpacing(10);

        layout.getChildren().addAll(nomeProdutoHbox,quantidadeProdutoHbox,precoProdutoHbox,statusProdutoHbox,buttonHbox, tableView);

        Scene cena = new Scene(layout, 850, 650);
        String css = getClass().getResource("/styles.css").toExternalForm();
        cena.getStylesheets().add(css);
        palco.setScene(cena);
        palco.show();
        }

    // Método stop é chamado automaticamente quando a aplicação JavaFX é encerrada. Override para garantir fechamento do DB

    @Override
    public void stop(){
        try{
            conexaoDB.close();
        } catch (SQLException e){
            System.err.println("Erro ao fechar conexão com DB: " + e.getMessage());
        }
    }

    // MÉTODO QUE LIMPA OS CAMPOS DO FORMULÁRIO
    private void limparCampos(){
        nomeInput.clear();
        quantidadeInput.clear();
        precoInput.clear();
        statusComboBox.setValue(null);
    }

    //MÉTODO QUE CRIA AS COLUNAS DA TABLEVIEW

    /**
     *
     * @param title O título da coluna que será exibido no cabeçalho
     * @param property A propriedade do objeto Produto que esta coluna deve exibir
     * @return A coluna configurada para a TableView
     */

    private TableColumn<Produto,String> criarColuna(String title, String property){
        TableColumn<Produto,String> col = new TableColumn<>(title);
        col.setCellValueFactory(new PropertyValueFactory<>(property)); // define a propriedade da coluna
        return col;
    }

    }


