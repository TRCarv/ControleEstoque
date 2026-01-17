package br.jaulipo.test;

import br.jaulipo.dao.ProdutoDAO;
import br.jaulipo.factory.ConexaoDB;
import br.jaulipo.model.Produto;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class Main {
    public static void main(String[] args){
        try (Connection conexao = ConexaoDB.conectar()){
            ProdutoDAO produtoDAO = new ProdutoDAO(conexao);

            // LIMPAR BANCO ANTES DO INÍCIO DOS TESTES
            produtoDAO.excluirTodos();

            // Listar todos os produtos
            mostrarProdutos(produtoDAO);

            // Exemplo de inserção de produtos

            Produto novoProduto1 = new Produto("Notebook", 12, 4500.0, "Em estoque");
            Produto novoProduto2 = new Produto("Smartphone", 35, 2499.5, "Em estoque");
            Produto novoProduto3 = new Produto("Tablet", 3, 999.99, "Estoque baixo");

            produtoDAO.inserir(novoProduto1);
            produtoDAO.inserir(novoProduto2);
            produtoDAO.inserir(novoProduto3);

            // Listar todos os produtos após inserção
            mostrarProdutos(produtoDAO);

            // Exemplo de consulta por ID
            Produto produtoConsultado = produtoDAO.consultarPorId(1);
            if (produtoConsultado!=null){
                System.out.println("Produto Encontrado: " + produtoConsultado.getNome());
            } else {
                System.out.println("Produto não encontrado");
            }


        } catch (SQLException e) {
            System.err.println("Erro geral: " + e.getMessage());
        }
    }

    // METODO PARA LISTAR OS PRODUTOS
    private static void mostrarProdutos(ProdutoDAO produtoDAO){
        List<Produto> todosProdutos = produtoDAO.listarTodos();
        if(todosProdutos.isEmpty()){
            System.out.println("Nenhum produto encontrado");
        } else {
            System.out.println("Lista de produtos: ");
            for(Produto p : todosProdutos) {
                System.out.println(p.getId() + ": " + p.getNome() + " - " + p.getPreco());
            }
        }

    }
}
