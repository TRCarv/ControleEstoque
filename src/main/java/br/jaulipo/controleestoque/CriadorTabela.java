package br.jaulipo.controleestoque;

import br.jaulipo.factory.ConexaoDB;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;


public class CriadorTabela {
    public static void main(String [] args){

        try (Connection conexao = ConexaoDB.conectar();
            Statement stmt = conexao.createStatement()) {

            // Definindo o comando para criar a tabela
            String comandoSql = "CREATE TABLE produtos (" +
                    "id_produto INTEGER PRIMARY KEY, " +
                    "nome_produto TEXT NOT NULL, " +
                    "quantidade INTEGER, " +
                    "preco REAL, " +
                    "status TEXT" +
                    ");";
            System.out.println(comandoSql); // só pra ver no terminal
            // executando o comando sql
            stmt.execute(comandoSql);
            System.out.println("Tabela 'Produtos' Criada com sucesso");
        } catch(SQLException e) {
            System.err.println("Erro na criação da tabela: " + e.getMessage());
            e.printStackTrace();
        }

    }
}
