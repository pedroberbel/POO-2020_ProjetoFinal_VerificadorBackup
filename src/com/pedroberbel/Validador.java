package com.pedroberbel;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.List;

public class Validador {

    String caminho, dataNova;
    Boolean encontraArquivo;
    String dia,mes,ano;
    String[] selecionaDataInicio, selecionaDataFim;
    String guarda = "";
    String nomeArquivo;

    public void verificaBackup(String data, List<String> bancos){

        selecionaDataFim = data.split("/");
        Calendar dataSelecionada = Calendar.getInstance();
        dataSelecionada.set(Calendar.DAY_OF_MONTH,Integer.parseInt(selecionaDataFim[0]));
        dataSelecionada.set(Calendar.MONTH, Integer.parseInt(selecionaDataFim[1]));
        dataSelecionada.set(Calendar.YEAR,Integer.parseInt(selecionaDataFim[2]));

        this.dia = Integer.toString(dataSelecionada.get(Calendar.DAY_OF_MONTH));
        this.mes = Integer.toString(dataSelecionada.get(Calendar.MONTH));
        this.ano = Integer.toString(dataSelecionada.get(Calendar.YEAR));
        this.dataNova = (dia+"."+mes+"."+ano);


        //Cria arquivo para imprimir o resultado
        for(int i = 0; i<bancos.size(); i++){
            nomeArquivo = ("DB2_" + bancos.get(i) + "_" + this.dataNova + ".rar");

            this.caminho = "C:\\Clientes\\backup\\" + bancos.get(i) + "\\"+ nomeArquivo;

            File file = new File(this.caminho);
            this.encontraArquivo = file.exists();

            if (this.encontraArquivo) {
                System.out.println("Backup encontrado: " + bancos.get(i));
                this.escreveRelatorio("Backup encontrado: " + bancos.get(i));
            } else {
                System.out.println("Backup não encontrado para: " + bancos.get(i));
                this.escreveRelatorio("Backup não encontrado para: " + bancos.get(i));
            }
        }
        System.out.println("------------------------");

    }



    public void verificaBackup(String DataInicio, String DataFim, List<String> bancos){

        for(int i = 0; i<bancos.size(); i++){

            selecionaDataFim = DataFim.split("/");
            selecionaDataInicio = DataInicio.split("/");

            //Trabalhando com calendário
            //Data Fim
            Calendar dataCalIni = Calendar.getInstance();
            Calendar dataCalFim = Calendar.getInstance();
            dataCalFim.set(Calendar.DAY_OF_MONTH,Integer.parseInt(selecionaDataFim[0]));
            dataCalFim.set(Calendar.MONTH, Integer.parseInt(selecionaDataFim[1]));
            dataCalFim.set(Calendar.YEAR,Integer.parseInt(selecionaDataFim[2]));
            //Data Inicio
            dataCalIni.set(Calendar.DAY_OF_MONTH, Integer.parseInt(selecionaDataInicio[0]));
            dataCalIni.set(Calendar.MONTH, Integer.parseInt(selecionaDataInicio[1]));
            dataCalIni.set(Calendar.YEAR,Integer.parseInt(selecionaDataInicio[2]));

            System.out.println(bancos.get(i)+":");

            while(dataCalIni.getTime().compareTo(dataCalFim.getTime()) == -1 || dataCalIni.getTime().compareTo(dataCalFim.getTime()) == 0){
                this.dia = Integer.toString(dataCalIni.get(Calendar.DAY_OF_MONTH));
                this.mes = Integer.toString(dataCalIni.get(Calendar.MONTH));
                this.ano = Integer.toString(dataCalIni.get(Calendar.YEAR));
                this.dataNova = (dia+"."+mes+"."+ano);

                nomeArquivo = ("DB2_" + bancos.get(i) + "_" + this.dataNova + ".rar");

                this.caminho = "C:\\Clientes\\backup\\" + bancos.get(i) + "\\"+ nomeArquivo;

                File file = new File(this.caminho);
                this.encontraArquivo = file.exists();


                if (this.encontraArquivo) {
                    System.out.println("Backup \""+nomeArquivo+"\" encontrado: " + bancos.get(i));
                    this.escreveRelatorio("Backup \""+nomeArquivo+"\" encontrado: " + bancos.get(i));

                } else {
                    System.out.println("Backup \""+nomeArquivo+"\" não encontrado para: " + bancos.get(i));
                    this.escreveRelatorio("Backup \""+nomeArquivo+"\" não encontrado para: " + bancos.get(i));
                }
                dataCalIni.set(Calendar.DAY_OF_MONTH, dataCalIni.get(Calendar.DAY_OF_MONTH) +1);
            }

        }
    }

    public void escreveRelatorio(String mensagem){

        Calendar datahoje = Calendar.getInstance();

        this.dia = Integer.toString(datahoje.get(Calendar.DAY_OF_MONTH));
        this.mes = Integer.toString(datahoje.get(Calendar.MONTH)+1);
        this.ano = Integer.toString(datahoje.get(Calendar.YEAR));
        int hora = datahoje.get(Calendar.HOUR_OF_DAY);
        int minuto = datahoje.get(Calendar.MINUTE);
        int segundo = datahoje.get(Calendar.SECOND);

        this.dataNova = ("["+dia+"/"+mes+"/"+ano+ " - "+ Integer.toString(hora)+":"+ Integer.toString(minuto)+":"+ Integer.toString(segundo)+"] ");

        this.guarda = this.guarda +this.dataNova+mensagem + "\n";
        try {
            datahoje.getTime();

            FileWriter report = new FileWriter("C:\\Clientes\\report.txt");

            report.write(this.guarda);

            report.close();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

    }
}
