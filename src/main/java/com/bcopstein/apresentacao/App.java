package com.bcopstein.apresentacao;

import java.util.ArrayList;
import java.util.List;

import com.bcopstein.negocio.Cart;
import com.bcopstein.negocio.ItemCart;
import com.bcopstein.negocio.Produto;
import com.bcopstein.negocio.EntidadeDTOConverter;
import com.bcopstein.negocio.Estatistica;
import com.bcopstein.persistencia.ArqProdutos;
import com.bcopstein.persistencia.CadastroProduto;
import com.bcopstein.persistencia.ProdutoDTO;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class App extends Application {
    private CadastroProduto cadProd;
    private Cart cart;
    private Estatistica estatisticas;

    private ComboBox<Produto> cbProdutos;
    private TextField tfQtdade,tfTotal,tfSubTotal,tfDesconto;
    private TextArea taCart;
    
    @Override
    public void start(Stage primaryStage) {
        // Instancia estruturas de dados
        cadProd = new CadastroProduto(new ArqProdutos());
        cart = new Cart(cadProd);
        estatisticas = new Estatistica();
        cart.addObserver(estatisticas); // Registra estatisticas como observador de cart

        // Define o grid basico
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(20);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        // Define o título do form
        Text tfTitulo = new Text("ACME's Shopping Cart");
        tfTitulo.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(tfTitulo, 0, 0, 2, 1);    
        
        // Define o painel de seleção de produtos
        GridPane topGrid = new GridPane();
        topGrid.setAlignment(Pos.BASELINE_LEFT);
        topGrid.setHgap(4);
        topGrid.setVgap(6);

        // Monta lista de produtos para exibição
        List<Produto> produtos = new ArrayList<>();
        for(ProdutoDTO p:cadProd){
            produtos.add(EntidadeDTOConverter.getInstance().dto2prod(p));
        }
        // Monta o combo de produtos
        topGrid.add(new Label("Selecione um produto:"), 0, 1);
        cbProdutos = new ComboBox<Produto>(FXCollections.observableArrayList(produtos));
        cbProdutos.getSelectionModel().selectFirst();
        topGrid.add(cbProdutos, 0, 2);        
        topGrid.add(new Label("Quantidade:"), 1, 2);
        tfQtdade = new TextField();
        topGrid.add(tfQtdade,2,2);

        // Botoes
        Button btAdd = new Button("Add");
        btAdd.setOnAction(e->this.trataBotaoAdd(e));
        Button btRemove = new Button("Remove");
        btRemove.setOnAction(e->this.trataBotaoRemove(e));
        Button btCheckout = new Button("Checkout");
        btCheckout.setOnAction(e->this.trataBotaoCheckout(e));
        HBox hbBtn = new HBox();
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(btAdd);
        hbBtn.getChildren().add(btRemove);
        hbBtn.getChildren().add(btCheckout);
        topGrid.add(hbBtn, 0, 3);

        // Define a área de montagem do carrinho
        GridPane cartGrid = new GridPane();
        cartGrid.setAlignment(Pos.BASELINE_LEFT);
        cartGrid.setHgap(4);
        cartGrid.setVgap(6);
        
        taCart = new TextArea();
        taCart.setEditable(false);
        cartGrid.add(taCart,0,0);

        // Adiciona os campos com valores finais
        HBox hbSubTotal = new HBox();
        hbSubTotal.getChildren().add(new Label("SubTotal:"));
        tfSubTotal = new TextField();
        hbSubTotal.getChildren().add(tfSubTotal);
        cartGrid.add(hbSubTotal,0,2);
        HBox hbDesconto = new HBox();
        hbDesconto.getChildren().add(new Label("Desconto:"));
        tfDesconto = new TextField();
        hbDesconto.getChildren().add(tfDesconto);
        cartGrid.add(hbDesconto,0,3);
        HBox hbTotal = new HBox();
        hbTotal.getChildren().add(new Label("Total:"));
        tfTotal = new TextField();
        hbTotal.getChildren().add(tfTotal);
        cartGrid.add(hbTotal,0,4);

        // Adiciona os paineis da direita e da esquerda no grid básico
        grid.add(topGrid,0,2);
        grid.add(cartGrid,0,3);

        StackPane root = new StackPane();
        root.getChildren().add(grid);        
        
        Scene scene = new Scene(root, 800, 450);
        primaryStage.setTitle("ACME Shopping Cart");
        primaryStage.setScene(scene);
        primaryStage.show(); 
    }

    public void atualizaTotais(){
        tfSubTotal.setText("R$ "+cart.getSubTotal());
        tfDesconto.setText("R$ "+cart.getValorDesconto());
        tfTotal.setText("R$ "+cart.getTotal());
    }

    public void trataBotaoAdd(ActionEvent event){
        // Recupera produto selecionado
        Produto prod = cbProdutos.getSelectionModel().getSelectedItem();
        int qtdade = 0;
        try{
            qtdade = Integer.parseInt(tfQtdade.getText());
        }catch(NumberFormatException e){
            Alert numEx = new Alert(Alert.AlertType.WARNING,"Quantidade inválida");
            numEx.showAndWait();
            return;
        }
        // Cadastra o novo item e exibe
        ItemCart tc = cart.addItem(prod.getCodigo(), qtdade);
        taCart.appendText("Produto: "+tc.getProduto()+
                          ", quantidade:"+tc.getQuantidade()+
                          ", valor item:"+tc.getValorDoItem()+
                          "\n");
        atualizaTotais();
    }

    public void trataBotaoRemove(ActionEvent event){
        cart.removeLast();
        taCart.clear();
        for(ItemCart tc:cart){
            taCart.appendText("Produto: "+tc.getProduto()+
            ", quantidade:"+tc.getQuantidade()+
            ", valor item:"+tc.getValorDoItem()+
            "\n");
        }
        atualizaTotais();
    }

    public void trataBotaoCheckout(ActionEvent event){
        // Deve salvar o conteúdo do carrinho em algum mecanismo de persistencia antes
        // Deveria chamar PersisteVendas para salvar a venda
        cart.closeCart();
        cart = new Cart(cadProd);
        cart.addObserver(estatisticas); // Registra o coletor de estatisticas no novo carrinho
        taCart.clear();
        atualizaTotais();
        String msg = "Compra efetuada!\n";
        msg += "Total de operações de compra:"+estatisticas.getEstatisticas()[0]+"\n";
        msg += "Média de inserções:"+estatisticas.getEstatisticas()[1]+"\n";
        msg += "Média de remoções:"+estatisticas.getEstatisticas()[2]+"\n";
        Alert numEx = new Alert(Alert.AlertType.CONFIRMATION,msg);
        numEx.showAndWait();
    }

    public static void main(String args[]){
        launch(args);
    }
}

