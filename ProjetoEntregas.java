/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projetoentregas;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.ListCellRenderer;

/**
 *
 * @author Estácio
 */
public class ProjetoEntregas extends JFrame{

    private JTextField txNumeroPedido;
    private JButton btSalvar;
    private JButton btCarregar;
    private PedidoRepo pedidoRepo;
    private ProdutoRepo produtoRepo;
    private JComboBox cbProduto;
    private JTextField txCliente;
    private JTextField txEndereco;
            
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        ProjetoEntregas projetoEntregas = new ProjetoEntregas();
        projetoEntregas.setVisible(true);
    }
    
    ProjetoEntregas(){
        pedidoRepo = new PedidoRepo();
        produtoRepo = new ProdutoRepo();
        initComponents();
    }
    private void initComponents(){
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        txNumeroPedido = new JTextField(10);
        txNumeroPedido.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                System.out.println("keyTyped: "+e.getKeyChar()+" - "+e.getKeyCode());
            }

            @Override
            public void keyPressed(KeyEvent e) {
                System.out.println("keyPressed: "+e.getKeyChar()+" - "+e.getKeyCode());
            }

            @Override
            public void keyReleased(KeyEvent e) {
                System.out.println("keyReleased: "+e.getKeyChar()+" - "+e.getKeyCode());
            }
        });
        txCliente = new JTextField(10);
        txEndereco = new JTextField(10);
        btCarregar = new JButton("carregar");
        btSalvar = new JButton("salvar");
        cbProduto = new JComboBox();
        JPanel pHeader = new JPanel();
        JPanel pContent = new JPanel();
        JPanel pFooter = new JPanel();
        this.getContentPane().add(pHeader, BorderLayout.PAGE_START);
        this.getContentPane().add(pContent, BorderLayout.CENTER);
        this.getContentPane().add(pFooter, BorderLayout.PAGE_END);
        pHeader.add(new JLabel("Registro de Pedidos"));
        JPanel pNumero = new JPanel();
        pNumero.add(new JLabel("Número do pedido"));
        pNumero.add(txNumeroPedido);
        JPanel pCliente = new JPanel();
        pCliente.add(new JLabel("Cliente"));
        pCliente.add(txCliente);

        JPanel pEndereco = new JPanel();
        pEndereco.add(new JLabel("Endereco"));
        pEndereco.add(txEndereco);

        JPanel pProduto = new JPanel();
        pProduto.add(new JLabel("Produto"));
        pProduto.add(cbProduto);
        cbProduto.setRenderer(new ListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                Produto produto = (Produto)value;
                JLabel label=null;
                if (isSelected){
                    label = new JLabel("f - "+produto.getDescricao());
                }else{
                    label = new JLabel(produto.getDescricao());
                }
                    
                return label;
            }
        });
        
        
        
        List<Produto> produtos = produtoRepo.carregarProdutos();
        produtos.sort((Produto o1, Produto o2) -> {
            return o1.getDescricao().compareTo(o2.getDescricao());
        });

        produtos.forEach(produto->
                cbProduto.addItem(produto));
       
                
       
        pContent.add(pNumero);
        pContent.add(pCliente);
        pContent.add(pEndereco);
        pContent.add(pProduto);
        
        pFooter.add(btCarregar);
        pFooter.add(btSalvar);
        btCarregar.addActionListener((ActionEvent e) -> {
            btCarregarClick(e);
        });
        btSalvar.addActionListener((ActionEvent e)->{
            btSalvarClick(e);
        });
        this.pack();
    }

    private void btCarregarClick(ActionEvent e) {
        String numeroDoPedido = txNumeroPedido.getText();
        Pedido p = pedidoRepo.CarregarPedido(numeroDoPedido);
        txCliente.setText(p.getCliente());
        txEndereco.setText(p.getEndereco());
        cbProduto.setSelectedItem(p.getProduto());
        //cbProduto.setSelectedItem(p.getProduto());
    }
    private void btSalvarClick(ActionEvent e) {
        String numeroDoPedido = txNumeroPedido.getText();
        String cliente = txCliente.getText();
        String endereco = txEndereco.getText();
        Produto produto = (Produto)cbProduto.getSelectedItem();
        Pedido p = new Pedido(numeroDoPedido, cliente, endereco, produto);
        pedidoRepo.gravarPedido(p);
        JOptionPane.showMessageDialog(rootPane, "pedido gravado");
    }
    
}
