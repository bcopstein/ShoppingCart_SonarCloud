package com.bcopstein.persistencia;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;

public class ArqProdutos {
    private static final String FNAME = "produtos.dat";

    public void carregaProdutos(List<ProdutoDTO> produtos) {
        String currDir = Paths.get("").toAbsolutePath().toString();
        String nameComplete = currDir + "\\resources\\" + FNAME;
        Path path = Paths.get(nameComplete);
        try (Scanner sc = new Scanner(Files.newBufferedReader(path, StandardCharsets.UTF_8))) {
            while (sc.hasNext()) {
                String linha = sc.nextLine();
                String dados[] = linha.split(",");
                int codigo = Integer.parseInt(dados[0]);
                String descricao = dados[1];
                float preco = (float)Double.parseDouble(dados[2]);
                String moeda = dados[3];
                ProdutoDTO p = new ProdutoDTO(codigo, descricao, preco,moeda);
                produtos.add(p);
            }
        } catch (IOException x) {
            System.err.format("Erro de E/S: %s%n", x);
        }
    }
}