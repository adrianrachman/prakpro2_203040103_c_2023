// adrian rachman
// 203040103

import java.awt.Color;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import java.awt.Font;

import javax.swing.table.DefaultTableModel;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class BiodataApp extends JFrame {

    public BiodataApp() {
        // Exit
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                int exit = JOptionPane.showConfirmDialog(null,
                        "Apakah ingin keluar?", "EXIT",
                        JOptionPane.YES_NO_OPTION);

                if (exit == JOptionPane.YES_OPTION) {
                    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                } else {
                    setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
                }
            }
        });

        JLabel labelInput = new JLabel("Nama :");
        labelInput.setBounds(15, 20, 350, 30);

        JTextField textField = new JTextField();
        textField.setBounds(15, 50, 350, 30);

        JLabel labelInput2 = new JLabel("Nomor HP :");
        labelInput2.setBounds(15, 80, 350, 30);

        JTextField textField2 = new JTextField();
        textField2.setBounds(15, 110, 350, 30);

        JLabel labelInput3 = new JLabel("Alamat :");
        labelInput3.setBounds(15, 140, 350, 30);

        JTextField textField3 = new JTextField();
        textField3.setBounds(15, 170, 350, 30);

        // Radio button
        JLabel labelRadio = new JLabel("Jenis Kelamin :");
        labelRadio.setBounds(15, 200, 350, 30);
        JRadioButton radioButton1 = new JRadioButton("Laki-Laki");
        radioButton1.setBounds(15, 225, 350, 30);
        JRadioButton radioButton2 = new JRadioButton("Perempuan");
        radioButton2.setBounds(15, 250, 350, 30);

        ButtonGroup bg = new ButtonGroup();
        bg.add(radioButton1);
        bg.add(radioButton2);

        // Membuat JFrame and JTable
        JFrame frame = new JFrame();
        JTable table = new JTable();

        // Membuat table model dan set Column
        Object[] columns = { "Nama", "Nomor HP", "Jenis Kelamin", "Alamat" };
        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(columns);

        table.setModel(model);

        // tombol button
        JButton btnAdd = new JButton("Simpan");
        JButton btnDelete = new JButton("Hapus");
        JButton btnUpdate = new JButton("Edit");
        JButton btnFile = new JButton("Simpan Ke File");

        btnAdd.setBounds(15, 310, 100, 25);
        btnUpdate.setBounds(140, 310, 100, 25);
        btnDelete.setBounds(260, 310, 100, 25);
        btnFile.setBounds(380, 310, 130, 25);

        // Untuk membuat JScrollPane
        JScrollPane pane = new JScrollPane(table);
        pane.setBounds(15, 350, 500, 200);
        frame.setLayout(null);
        frame.add(pane);

        // memasukan JTextFields ke jframe
        frame.add(textField);
        frame.add(textField2);
        frame.add(textField3);

        // memasukan Jbutton ke jframe
        frame.add(btnAdd);
        frame.add(btnDelete);
        frame.add(btnUpdate);
        frame.add(btnFile);

        // membuat array dengan 4 row
        Object[] row = new Object[4];

        // button add
        btnAdd.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                String jenisKelamin = " ";
                if (radioButton1.isSelected()) {
                    jenisKelamin = radioButton1.getText();
                }
                if (radioButton2.isSelected()) {
                    jenisKelamin = radioButton2.getText();
                }
                String nama = textField.getText();
                String nomortlp = textField2.getText();
                String alamat = textField3.getText();
                if (nama.trim().isEmpty() || nomortlp.trim().isEmpty() || alamat.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(BiodataApp.this, "Form tidak boleh ada yang kosong!", "Warning",
                            JOptionPane.WARNING_MESSAGE);
                } else {
                    int confirmation = JOptionPane.showConfirmDialog(BiodataApp.this, "Masukan Data?", "Konfirmasi",
                            JOptionPane.YES_NO_OPTION);
                    if (confirmation == JOptionPane.YES_OPTION) {
                        row[0] = textField.getText();
                        row[1] = textField2.getText();
                        row[2] = textField3.getText();
                        row[3] = jenisKelamin;
                        model.addRow(row);
                        textField.setText(null);
                        textField2.setText(null);
                        textField3.setText(null);
                    } else {
                        JOptionPane.showMessageDialog(BiodataApp.this, "Anda tidak memasukan data");
                    }
                }
            }
        });

        // button delete
        btnDelete.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                // i = the index of the selected row
                int i = table.getSelectedRow();
                if (i >= 0) {
                    // remove a row from jtable
                    model.removeRow(i);
                } else {
                    System.out.println("Delete Error");
                }
                textField.setText(null);
                textField2.setText(null);
                textField3.setText(null);
            }
        });

        // Fungsi selected row data
        table.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {

                // i = the index of the selected row
                int i = table.getSelectedRow();

                textField.setText(model.getValueAt(i, 0).toString());
                textField2.setText(model.getValueAt(i, 1).toString());
                textField3.setText(model.getValueAt(i, 2).toString());
            }
        });

        // button Update
        btnUpdate.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                // i = the index of the selected row
                int i = table.getSelectedRow();

                if (i >= 0) {
                    model.setValueAt(textField.getText(), i, 0);
                    model.setValueAt(textField2.getText(), i, 1);
                    model.setValueAt(textField3.getText(), i, 2);
                } else {
                    System.out.println("Update Error");
                }
            }
        });

        
        // Membuat button untuk menyimpan data ke file
        btnFile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                int userSelection = fileChooser.showSaveDialog(BiodataApp.this);

                if (userSelection == JFileChooser.APPROVE_OPTION) {
                    try {
                        String filePath = fileChooser.getSelectedFile().getAbsolutePath();
                        FileWriter fileWriter = new FileWriter(filePath);
                        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

                        for (int i = 0; i < model.getRowCount(); i++) {
                            String nama = model.getValueAt(i, 0).toString();
                            String nomorTlp = model.getValueAt(i, 1).toString();
                            String alamat = model.getValueAt(i, 2).toString();
                            String jenisKelamin = model.getValueAt(i, 3).toString();

                            String data = nama + "," + nomorTlp + "," + alamat + "," + jenisKelamin;
                            bufferedWriter.write(data);
                            bufferedWriter.newLine();
                        }

                        bufferedWriter.close();
                        fileWriter.close();

                        JOptionPane.showMessageDialog(BiodataApp.this, "Data berhasil disimpan ke file.", "Info",
                                JOptionPane.INFORMATION_MESSAGE);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(BiodataApp.this, "Terjadi kesalahan saat menyimpan data ke file.",
                                "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        this.add(textField);
        this.add(textField2);
        this.add(textField3);
        this.add(labelRadio);
        this.add(radioButton1);
        this.add(radioButton2);
        this.add(labelInput);
        this.add(labelInput2);
        this.add(labelInput3);
        this.add(btnAdd);
        this.add(btnDelete);
        this.add(btnUpdate);
        this.add(btnFile);
        this.add(pane);

        this.setSize(600, 700);
        this.setLayout(null);
    }

    public static void main(String[] args) {

        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {

                BiodataApp b = new BiodataApp();
                b.setVisible(true);
            }
        });
    }
}
