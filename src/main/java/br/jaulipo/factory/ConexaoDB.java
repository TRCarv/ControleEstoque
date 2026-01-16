package br.jaulipo.factory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoDB {
    private static final String URL_JDBC_PADRAO = "jdbc:sqlite:banco_de_dados_estoque.db";

    // Método conectar padrão

    public static Connection conectar() {
        try{
            return DriverManager.getConnection(URL_JDBC_PADRAO);
        } catch (SQLException e) {
            System.err.println("Erro ao conectar ao banco de dados "+ e.getMessage());
            return null;
        }
    }

    // método para conectar com url, usuário e senha

    public static Connection conectarGenerico(String url, String usuario, String senha){
        try {
            return DriverManager.getConnection(url,usuario,senha);
        } catch (SQLException e){
            System.err.println("Erro ao conectar ao db: " + e.getMessage());
            return null;
        }
    }


}