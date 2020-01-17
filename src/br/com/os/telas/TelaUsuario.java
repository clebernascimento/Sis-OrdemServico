/**
* Sistema de Controle de Serviços OS com acesso restrito
*
* Usado para fazer controlar uma Ordem de Serviços
*
* @author Cleber-Soft <cleber-soft@hotmail.com>
*
* @version 1.0
* @package SiS - Controle de Serviços OS
* 
* TELA DE USUARIO
*/
package br.com.os.telas;

import Modelo.ApenasLetras;
import java.sql.*;
import br.com.os.dal.ModuloConexao;
import javax.swing.JOptionPane;

/**
 *
 * @author Cleber'Nascimento
 */
public class TelaUsuario extends javax.swing.JDialog {

    // Usando a variavel conexao da classe Conexao do DAL
    Connection conexao = null;
    //Criando Variaveis para Conexao com o Banco
    //PreparedStatement e ResultSet sao Frameworks do pacote java.sql
    // e servem para preparar e executar as instruções SQL
    PreparedStatement pst = null;
    ResultSet rs = null;

    /**
     * Creates new form TelaUsuario
     */
    public TelaUsuario(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();

        conexao = ModuloConexao.conector();
        //METODO PARA DIGITAR SOMENTES LETRAS NOS CAMPOS
        txtUserNome.setDocument(new ApenasLetras());
    }

    // Metodo consultar usuarios
    private void consultar() {
        //a linha abaixo cria uma caixa de entrada do tipo JOptionPane
        String num_id = JOptionPane.showInputDialog("Matricula do Funcionário");
        String sql = "select *from tbusuarios where iduser="+num_id;
        try {
            pst = conexao.prepareStatement(sql);
            //pst.setString(1, txtUserId.getText());
            rs = pst.executeQuery();
            if (rs.next()) {
                txtUserId.setText(rs.getString(1));
                txtUserNome.setText(rs.getString(2));
                // a linha abaixo se refere ao comboBox
                boxUserPerfil.setSelectedItem(rs.getString(3));
                txtUserEndereco.setText(rs.getString(4));
                txtUserBairro.setText(rs.getString(5));               
                txtUserFone.setText(rs.getString(6));
                txtUserLogin.setText(rs.getString(7));
                txtUserSenha.setText(rs.getString(8));
                //evitando problemas
                btnUserCadastro.setEnabled(false);
            } else {
                JOptionPane.showMessageDialog(null, "Funcionário(a) não Cadastrado !");
                //As linhas abaixo limpa os campos
                txtUserId.setText(null);
                txtUserNome.setText(null);
                txtUserEndereco.setText(null);
                txtUserBairro.setText(null);
                txtUserFone.setText(null);
                txtUserLogin.setText(null);
                txtUserSenha.setText(null);
                boxUserPerfil.setSelectedItem(null);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    //Metodo Cadastrar Usuarios
    private void cadastrar() {
        String sql = "insert into tbusuarios(usuario,endereco,bairro,fone,login,senha,perfil) values(?,?,?,?,?,?,?)";
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, txtUserNome.getText());
            pst.setString(2, txtUserEndereco.getText());
            pst.setString(3, txtUserBairro.getText());
            pst.setString(4, txtUserFone.getText());
            pst.setString(5, txtUserLogin.getText());
            pst.setString(6, txtUserSenha.getText());
            pst.setString(7, boxUserPerfil.getSelectedItem().toString());
            // validação dos campos obrigatorios
            if ((txtUserNome.getText().isEmpty()) || (txtUserEndereco.getText().isEmpty()) || (txtUserBairro.getText().isEmpty()) || (txtUserLogin.getText().isEmpty()) || (txtUserSenha.getText().isEmpty()) || (txtUserFone.getText().isEmpty())) {
                JOptionPane.showMessageDialog(null, "Preecha todos os campos obrigatorios!");
            } else {

                // a linha abaixo atualiza a tabela usuarios com os dados do formulario
                // A  estrtura abaixo é usada para confirmar a inserção dos dados na tabela
                int adicionado = pst.executeUpdate();
                System.out.println(adicionado);
                if (adicionado > 0) {
                    JOptionPane.showMessageDialog(null, "Usuario cadastrado com sucesso!");
                    //As linhas abaixo limpa os campos
                    txtUserId.setText(null);
                    txtUserNome.setText(null);
                    txtUserEndereco.setText(null);
                    txtUserBairro.setText(null);
                    txtUserFone.setText(null);
                    txtUserLogin.setText(null);
                    txtUserSenha.setText(null);
                    boxUserPerfil.setSelectedItem(null);
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    //Metodo Alterar()
    private void alterar() {
        String sql = "update tbusuarios set usuario=?,endereco=?,bairro=?,fone=?,login=?,senha=?,perfil=? where iduser=?";
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, txtUserNome.getText());
            pst.setString(2, txtUserEndereco.getText());
            pst.setString(3, txtUserBairro.getText());
            pst.setString(4, txtUserFone.getText());
            pst.setString(5, txtUserLogin.getText());
            pst.setString(6, txtUserSenha.getText());
            pst.setString(7, boxUserPerfil.getSelectedItem().toString());
            pst.setString(8, txtUserId.getText());
            // validação dos campos obrigatorios
            if ((txtUserNome.getText().isEmpty()) || (txtUserEndereco.getText().isEmpty()) || (txtUserBairro.getText().isEmpty()) || (txtUserLogin.getText().isEmpty()) || (txtUserSenha.getText().isEmpty()) || (txtUserFone.getText().isEmpty())) {
                JOptionPane.showMessageDialog(null, "Preecha todos os campos obrigatorios!");
            } else {

                // a linha abaixo atualiza a tabela usuarios com os dados do formulario
                // A  estrtura abaixo é usada para confirmar a alteração dos dados na tabela
                int adicionado = pst.executeUpdate();
                System.out.println(adicionado);
                if (adicionado > 0) {
                    JOptionPane.showMessageDialog(null, "Dados do Usuario Alterado com sucesso!");
                    //As linhas abaixo limpa os campos
                    txtUserId.setText(null);
                    txtUserNome.setText(null);
                    txtUserEndereco.setText(null);
                    txtUserBairro.setText(null);
                    txtUserFone.setText(null);
                    txtUserLogin.setText(null);
                    txtUserSenha.setText(null);
                    boxUserPerfil.setSelectedItem(null);
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    // Metodo Remover()
    private void remover() {
        //a estrutura abaixo confirma a remoção do usuario
        int confirma = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja Excluir este Usuario ?", "Atenção", JOptionPane.YES_NO_OPTION);
        if (confirma == JOptionPane.YES_NO_OPTION) {
            String sql = "delete from tbusuarios where iduser=?";
            try {
                pst = conexao.prepareStatement(sql);
                pst.setString(1, txtUserId.getText());
                // a linha abaixo atualiza a tabela usuarios com os dados do formulario
                // A  estrtura abaixo é usada para confirmar a remoção dos dados na tabela
                int apagado = pst.executeUpdate();
                if (apagado > 0) {
                    JOptionPane.showMessageDialog(null, "Usuario Removido co sucesso !");
                    //As linhas abaixo limpa os campos
                    txtUserId.setText(null);
                    txtUserNome.setText(null);
                    txtUserEndereco.setText(null);
                    txtUserBairro.setText(null);
                    txtUserFone.setText(null);
                    txtUserLogin.setText(null);
                    txtUserSenha.setText(null);
                    boxUserPerfil.setSelectedItem(null);
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
            }

        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtUserId = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtUserNome = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        txtUserEndereco = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        txtUserBairro = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        boxUserPerfil = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        txtUserLogin = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtUserSenha = new javax.swing.JTextField();
        btnUserCadastro = new javax.swing.JButton();
        btnUserConsultar = new javax.swing.JButton();
        btnUserAlterar = new javax.swing.JButton();
        btnUserExcluir = new javax.swing.JButton();
        txtUserFone = new javax.swing.JFormattedTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Cadastro / Lista - Funcionarios");
        setPreferredSize(new java.awt.Dimension(1461, 585));

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Cadastrar / Listar Funcionario", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", 1, 14))); // NOI18N

        jLabel1.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel1.setText("Matricula");

        txtUserId.setEnabled(false);

        jLabel7.setText("* Campos Obrogatórios");

        jLabel2.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel2.setText("Nome *");

        jLabel8.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel8.setText("Endereço *");

        jLabel9.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel9.setText("Bairro *");

        jLabel3.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel3.setText("Fone *");

        jLabel5.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel5.setText("Perfil *");

        boxUserPerfil.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Administrador", "Tecnico" }));

        jLabel4.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel4.setText("Login *");

        jLabel6.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel6.setText("Senha *");

        btnUserCadastro.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/os/icones/create.png"))); // NOI18N
        btnUserCadastro.setToolTipText("Adicionar");
        btnUserCadastro.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnUserCadastro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUserCadastroActionPerformed(evt);
            }
        });

        btnUserConsultar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/os/icones/read.png"))); // NOI18N
        btnUserConsultar.setToolTipText("Pesquisar");
        btnUserConsultar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnUserConsultar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUserConsultarActionPerformed(evt);
            }
        });

        btnUserAlterar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/os/icones/update.png"))); // NOI18N
        btnUserAlterar.setToolTipText("Alterar");
        btnUserAlterar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnUserAlterar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUserAlterarActionPerformed(evt);
            }
        });

        btnUserExcluir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/os/icones/delete.png"))); // NOI18N
        btnUserExcluir.setToolTipText("Excluir");
        btnUserExcluir.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnUserExcluir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUserExcluirActionPerformed(evt);
            }
        });

        try {
            txtUserFone.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("(##) #####-####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(150, 150, 150)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtUserFone, javax.swing.GroupLayout.PREFERRED_SIZE, 397, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(jLabel2))
                            .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtUserSenha)
                            .addComponent(txtUserLogin)
                            .addComponent(txtUserEndereco)
                            .addComponent(txtUserNome)
                            .addComponent(txtUserBairro, javax.swing.GroupLayout.PREFERRED_SIZE, 397, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtUserId, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(boxUserPerfil, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(118, 118, 118))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(221, 221, 221)
                .addComponent(btnUserCadastro)
                .addGap(52, 52, 52)
                .addComponent(btnUserConsultar)
                .addGap(50, 50, 50)
                .addComponent(btnUserAlterar)
                .addGap(53, 53, 53)
                .addComponent(btnUserExcluir)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel7)
                .addGap(22, 22, 22))
        );

        jPanel1Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jLabel1, jLabel2, jLabel3, jLabel5});

        jPanel1Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {btnUserAlterar, btnUserCadastro, btnUserConsultar, btnUserExcluir});

        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel7)
                .addGap(12, 12, 12)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtUserId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtUserNome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5)
                    .addComponent(boxUserPerfil, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(25, 25, 25)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(txtUserEndereco, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtUserBairro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9))
                .addGap(29, 29, 29)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtUserLogin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(32, 32, 32)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txtUserSenha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(29, 29, 29)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtUserFone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(51, 51, 51)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnUserConsultar)
                    .addComponent(btnUserCadastro)
                    .addComponent(btnUserAlterar)
                    .addComponent(btnUserExcluir))
                .addContainerGap(61, Short.MAX_VALUE))
        );

        jPanel1Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jLabel1, jLabel2, jLabel3, jLabel4, jLabel5});

        jPanel1Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {btnUserAlterar, btnUserCadastro, btnUserConsultar, btnUserExcluir});

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 1010, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        setSize(new java.awt.Dimension(1038, 622));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnUserConsultarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUserConsultarActionPerformed
        // Chamando o metodo consultar();
        consultar();
    }//GEN-LAST:event_btnUserConsultarActionPerformed

    private void btnUserCadastroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUserCadastroActionPerformed
        // Chamando o metodo adicionar();
        cadastrar();
    }//GEN-LAST:event_btnUserCadastroActionPerformed

    private void btnUserAlterarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUserAlterarActionPerformed
        // Chamando metodo alterar()
        alterar();
    }//GEN-LAST:event_btnUserAlterarActionPerformed

    private void btnUserExcluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUserExcluirActionPerformed
        // Chamando o metodo Remover()
        remover();
    }//GEN-LAST:event_btnUserExcluirActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(TelaUsuario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TelaUsuario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TelaUsuario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TelaUsuario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                TelaUsuario dialog = new TelaUsuario(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> boxUserPerfil;
    private javax.swing.JButton btnUserAlterar;
    private javax.swing.JButton btnUserCadastro;
    private javax.swing.JButton btnUserConsultar;
    private javax.swing.JButton btnUserExcluir;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTextField txtUserBairro;
    private javax.swing.JTextField txtUserEndereco;
    private javax.swing.JFormattedTextField txtUserFone;
    private javax.swing.JTextField txtUserId;
    private javax.swing.JTextField txtUserLogin;
    private javax.swing.JTextField txtUserNome;
    private javax.swing.JTextField txtUserSenha;
    // End of variables declaration//GEN-END:variables
}
