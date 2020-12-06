package com.pedroberbel;

import com.sun.org.apache.xerces.internal.impl.dv.xs.DayDV;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.*;
import java.util.ArrayList;
import java.util.List;


public class TelaValidador extends JFrame implements WindowListener, ActionListener {

    protected Dimension dFrame, dLabel, dTextField, dButton, dDateField;
    protected Button btnVerifica, btnRelatorio, btnVerificaIntervalo;
    protected TextField txtData, txtDataInicio, txtDataFim;
    protected TextArea txtClientes;
    protected Label lbDataInicio, lbDataFim, lbDataFixa,lbListaBancos;

    private String  data, dataInicio, dataFim;
    private List<String> bancos = new ArrayList<>();

    private Cliente cliente = new Cliente();
    private Validador validador = new Validador();

    public TratamentoExcecao tratamentoExcecao = new TratamentoExcecao();

    public TelaValidador(){
        dFrame = new Dimension(350,400);
        dLabel = new Dimension(100,20);
        dTextField = new Dimension(100,300);
        dButton = new Dimension(50,20);
        dDateField = new Dimension(100,20);
        //Define as propriedades ao iniciar a tela
        setTitle("Verificador de Backup");
        setResizable(false);
        setSize(dFrame);
        setLocation(200,200);
        setLayout(null);


        //Lista de Bancos

        lbListaBancos = new Label();
        lbListaBancos.setSize(dLabel);
        lbListaBancos.setLocation(5,5);
        lbListaBancos.setText("Backup:");
        add(lbListaBancos);

        txtClientes = new TextArea(null);
        txtClientes.setSize(dTextField);
        txtClientes.setLocation(5,25);
        txtClientes.setEditable(false);
        txtClientes.setText("");
        add(txtClientes);

        //Campo com a data a verificar

        lbDataFixa = new Label();
        lbDataFixa.setSize(dLabel);
        lbDataFixa.setLocation(115,5);
        lbDataFixa.setText("Data:");
        add(lbDataFixa);

        txtData = new TextField(null);
        txtData.setSize(100,20);
        txtData.setLocation(115,25);
        txtData.setEditable(true);
        txtData.setText("");
        add(txtData);

        btnVerifica = new Button("verifica");
        btnVerifica.setSize(60,20);
        btnVerifica.setLocation(220,25);
        btnVerifica.addActionListener(this);
        add(btnVerifica);
//********************************************
        lbDataInicio = new Label();
        lbDataInicio.setSize(dLabel);
        lbDataInicio.setLocation(115,130);
        lbDataInicio.setText("Data Inicial:");
        add(lbDataInicio);

        txtDataInicio = new TextField(null);
        txtDataInicio.setSize(100,20);
        txtDataInicio.setLocation(115,150);
        txtDataInicio.setEditable(true);
        txtDataInicio.setText("");
        add(txtDataInicio);

        lbDataFim = new Label();
        lbDataFim.setSize(dLabel);
        lbDataFim.setLocation(115,170);
        lbDataFim.setText("Data Final:");
        add(lbDataFim);

        txtDataFim = new TextField(null);
        txtDataFim.setSize(100,20);
        txtDataFim.setLocation(115,190);
        txtDataFim.setEditable(true);
        txtDataFim.setText("");
        add(txtDataFim);

        btnVerificaIntervalo = new Button("Verifica");
        btnVerificaIntervalo.setSize(60,20);
        btnVerificaIntervalo.setLocation(220,190);
        btnVerificaIntervalo.addActionListener(this);
        add(btnVerificaIntervalo);
//********************************************

        btnRelatorio = new Button("Relatório");
        btnRelatorio.setSize(100,20);
        btnRelatorio.setLocation(115,250);
        btnRelatorio.addActionListener(this);
        add(btnRelatorio);

        addWindowListener(this);
    }
    //Métodos window Listener
    @Override
    public void windowOpened(WindowEvent e) {
        int i = 0;
        try {
            BufferedReader br = new BufferedReader(new FileReader("C:\\Clientes\\Clientes.txt"));
            while (br.ready()) {
                String linha = br.readLine();
                this.cliente.setBANCO(linha);
                this.bancos.add(linha);
                i++;
            }
            br.close();
        } catch (IOException ioe){
            ioe.printStackTrace();
        }
        int bancoSize = bancos.size();
        for (int y = 0;y < bancoSize; y++){
            this.txtClientes.append(bancos.get(y)+"\n");
        }
    }

    @Override
    public void windowClosing(WindowEvent e) {}

    @Override
    public void windowClosed(WindowEvent e) {}

    @Override
    public void windowIconified(WindowEvent e) {}

    @Override
    public void windowDeiconified(WindowEvent e) {}

    @Override
    public void windowActivated(WindowEvent e) {

    }

    @Override
    public void windowDeactivated(WindowEvent e) {}

    //Action Listener
    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == btnVerificaIntervalo){
            try {
                if (this.txtDataInicio.getText().equals("") || this.txtDataFim.getText().equals("")) {
                    throw new TratamentoExcecao();
                }

                this.dataInicio = this.txtDataInicio.getText();
                this.dataFim = this.txtDataFim.getText();

                this.validador.verificaBackup(this.dataInicio, this.dataFim, this.bancos);

            } catch (TratamentoExcecao excecao) {
                JOptionPane.showMessageDialog(null, "Digite a data inicial e a data final \n no formato dd\\mm\\aaaa");
            }
        }

        if (e.getSource() == btnVerifica) {
            try {
                if (this.txtData.getText().equals("")) {
                    throw new TratamentoExcecao();
                }
                this.data = this.txtData.getText();
                this.validador.verificaBackup(this.data, this.bancos);
            } catch (TratamentoExcecao excecao){
                JOptionPane.showMessageDialog(null, "Digite uma data no formato dd\\mm\\aaaa");
            }
        }

        if(e.getSource() == btnRelatorio){
            File file = new File("C:\\Clientes\\report.txt");
            Desktop desktop = Desktop.getDesktop();
            if(file.exists()){
                try {
                    desktop.open(file);
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        }

    }



}
