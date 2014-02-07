package ratatouille;

import java.awt.Color;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jdesktop.application.Action;
import org.jdesktop.application.ResourceMap;
import org.jdesktop.application.SingleFrameApplication;
import org.jdesktop.application.FrameView;
import org.jdesktop.application.TaskMonitor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;
import javax.swing.Icon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.ImageIcon;
import ratatouille.Controlador.PonteControladora;

public class RatatouilleView extends FrameView {
    ImageIcon imageIconButton = new ImageIcon("Images/button2.png");
    PonteControladora masterBridge;
    private int caminhoDesenhado[] ;

    public RatatouilleView(SingleFrameApplication app) {
        
        super(app);

        initComponents();        

        ResourceMap resourceMap = getResourceMap();
        int messageTimeout = resourceMap.getInteger("StatusBar.messageTimeout");
        messageTimer = new Timer(messageTimeout, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                statusMessageLabel.setText("");
            }
        });
        messageTimer.setRepeats(false);
        int busyAnimationRate = resourceMap.getInteger("StatusBar.busyAnimationRate");
        for (int i = 0; i < busyIcons.length; i++) {
            busyIcons[i] = resourceMap.getIcon("StatusBar.busyIcons[" + i + "]");
        }
        busyIconTimer = new Timer(busyAnimationRate, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                busyIconIndex = (busyIconIndex + 1) % busyIcons.length;
                statusAnimationLabel.setIcon(busyIcons[busyIconIndex]);
            }
        });
        idleIcon = resourceMap.getIcon("StatusBar.idleIcon");
        statusAnimationLabel.setIcon(idleIcon);
        progressBar.setVisible(false);
        label2.setVisible(false);
        jPanel2.setVisible(false);
        jPanel2.setBackground(Color.black);

        // connecting action tasks to status bar via TaskMonitor
        TaskMonitor taskMonitor = new TaskMonitor(getApplication().getContext());
        taskMonitor.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                String propertyName = evt.getPropertyName();
                if ("started".equals(propertyName)) {
                    if (!busyIconTimer.isRunning()) {
                        statusAnimationLabel.setIcon(busyIcons[0]);
                        busyIconIndex = 0;
                        busyIconTimer.start();
                    }
                    progressBar.setVisible(true);
                    progressBar.setIndeterminate(true);
                } else if ("done".equals(propertyName)) {
                    busyIconTimer.stop();
                    statusAnimationLabel.setIcon(idleIcon);
                    progressBar.setVisible(false);
                    progressBar.setValue(0);
                } else if ("message".equals(propertyName)) {
                    String text = (String)(evt.getNewValue());
                    statusMessageLabel.setText((text == null) ? "" : text);
                    messageTimer.restart();
                } else if ("progress".equals(propertyName)) {
                    int value = (Integer)(evt.getNewValue());
                    progressBar.setVisible(true);
                    progressBar.setIndeterminate(false);
                    progressBar.setValue(value);
                }
            }
        });
    }

    @Action
    public void showAboutBox() {
        if (aboutBox == null) {
            JFrame mainFrame = RatatouilleApp.getApplication().getMainFrame();
            aboutBox = new RatatouilleAboutBox(mainFrame);
            aboutBox.setLocationRelativeTo(mainFrame);
        }
        RatatouilleApp.getApplication().show(aboutBox);
    }
    
    public void falseFirstVisible () {
        jLabel2.setVisible(false);
        jLabel3.setVisible(false);
        jLabel4.setVisible(false);
        jLabel5.setVisible(false);
        jLabel6.setVisible(false);
        jLabel7.setVisible(false);
        jLabel8.setVisible(false);
        jComboBox1.setVisible(false);
        jComboBox2.setVisible(false);
        jComboBox3.setVisible(false);
        jComboBox4.setVisible(false);
        jComboBox5.setVisible(false);
        jComboBox6.setVisible(false);
        jComboBox7.setVisible(false);
        label1.setVisible(false);
        label2.setVisible(true);
    }
    
    public void trueFirstVisible () {
        jLabel2.setVisible(true);
        jLabel3.setVisible(true);
        jLabel4.setVisible(true);
        jLabel5.setVisible(true);
        jLabel6.setVisible(true);
        jLabel7.setVisible(true);
        jLabel8.setVisible(true);
        jComboBox1.setVisible(true);
        jComboBox2.setVisible(true);
        jComboBox3.setVisible(true);
        jComboBox4.setVisible(true);
        jComboBox5.setVisible(true);
        jComboBox6.setVisible(true);
        jComboBox7.setVisible(true);
        label1.setVisible(true);
        label2.setVisible(false);
    }
    
    public void setValuesInMasterBridge () throws IOException {
       
        masterBridge = new PonteControladora(    jComboBox1.getSelectedIndex(), 
                                            jComboBox2.getSelectedIndex(),
                                            getComboBox3(),
                                            getComboBox4(),
                                            getComboBox5(),
                                            getComboBox6(),
                                            jComboBox7.getSelectedIndex());
        
        DesenheOCaminhoDoRatatouille();
    }
    
    public void DesenheOCaminhoDoRatatouille () 
    {
        caminhoDesenhado = new int[20];
        caminhoDesenhado = masterBridge.getMelhorCaminhoEncontrado();
        
        jPanel2.setVisible(true);
        
        for (int j = 0; j < caminhoDesenhado.length - 1; j++ ) {
            
            int flag = 0;
            
            if ( j == 0 ) 
            {
                DesenheA(caminhoDesenhado[j]);
                j++;
            }
                
            if ( caminhoDesenhado[j] > caminhoDesenhado[j - 1] )
                flag = 1;
            
            // comment
            if (flag == 1) {
            for ( int i = 0; i <= 20; i++ ) {
                    if ( i <= caminhoDesenhado[j] && i >= caminhoDesenhado[j-1] ) {
                        if (j == 1) DesenheB(i);
                        else if (j == 2) DesenheC(i);
                        else if (j == 3) DesenheD(i);
                        else if (j == 4) DesenheE(i);
                        else if (j == 5) DesenheF(i);
                        else if (j == 6) DesenheG(i);
                        else if (j == 7) DesenheH(i);
                        else if (j == 8) DesenheI(i);
                        else if (j == 9) DesenheJ(i);
                        else if (j == 10) DesenheK(i);
                        else if (j == 11) DesenheL(i);
                        else if (j == 12) DesenheM(i);
                        else if (j == 13) DesenheN(i);
                        else if (j == 14) DesenheO(i);
                        
                        if ( caminhoDesenhado[j] == caminhoDesenhado[j-1] )
                            break;
                }
            }
            }
            else 
            for ( int i = 20; i != -1; i-- )
                    if ( i >= caminhoDesenhado[j] && i <= caminhoDesenhado[j-1] ) {
                        if (j == 1) DesenheB(i);
                        else if (j == 2) DesenheC(i);
                        else if (j == 3) DesenheD(i);
                        else if (j == 4) DesenheE(i);
                        else if (j == 5) DesenheF(i);
                        else if (j == 6) DesenheG(i);
                        else if (j == 7) DesenheH(i);
                        else if (j == 8) DesenheI(i);
                        else if (j == 9) DesenheJ(i);
                        else if (j == 10) DesenheK(i);
                        else if (j == 11) DesenheL(i);
                        else if (j == 12) DesenheM(i);
                        else if (j == 13) DesenheN(i);
                        else if (j == 14) DesenheO(i);
                        
                        if ( caminhoDesenhado[j] == caminhoDesenhado[j-1] )
                            break;
                }
        }
    }
    
    private void CancelaCores () {
        A1.setBackground(Color.black);
        A3.setBackground(Color.black);
        A2.setBackground(Color.black);
        A4.setBackground(Color.black);
        A5.setBackground(Color.black);
        A6.setBackground(Color.black);
        A7.setBackground(Color.black);
        A8.setBackground(Color.black);
        A9.setBackground(Color.black);
        A10.setBackground(Color.black);
        A11.setBackground(Color.black);
        A12.setBackground(Color.black);
        A13.setBackground(Color.black);
        A14.setBackground(Color.black);
        A15.setBackground(Color.black);
        A16.setBackground(Color.black);
        A17.setBackground(Color.black);
        A18.setBackground(Color.black);
        A19.setBackground(Color.black);
        A20.setBackground(Color.black);
        B1.setBackground(Color.black);
        B2.setBackground(Color.black);
        B3.setBackground(Color.black);
        B4.setBackground(Color.black);
        B5.setBackground(Color.black);
        B6.setBackground(Color.black);
        B7.setBackground(Color.black);
        B8.setBackground(Color.black);
        B9.setBackground(Color.black);
        B10.setBackground(Color.black);
        B11.setBackground(Color.black);
        B12.setBackground(Color.black);
        B13.setBackground(Color.black);
        B14.setBackground(Color.black);
        B15.setBackground(Color.black);
        B16.setBackground(Color.black);
        B17.setBackground(Color.black);
        B18.setBackground(Color.black);
        B19.setBackground(Color.black);
        B20.setBackground(Color.black);
        C1.setBackground(Color.black);
        C2.setBackground(Color.black);
        C3.setBackground(Color.black);
        C4.setBackground(Color.black);
        C5.setBackground(Color.black);
        C6.setBackground(Color.black);
        C7.setBackground(Color.black);
        C8.setBackground(Color.black);
        C9.setBackground(Color.black);
        C10.setBackground(Color.black);
        C11.setBackground(Color.black);
        C12.setBackground(Color.black);
        C13.setBackground(Color.black);
        C14.setBackground(Color.black);
        C15.setBackground(Color.black);
        C16.setBackground(Color.black);
        C17.setBackground(Color.black);
        C18.setBackground(Color.black);
        C19.setBackground(Color.black);
        C20.setBackground(Color.black);
        D1.setBackground(Color.black);
        D3.setBackground(Color.black);
        D2.setBackground(Color.black);
        D4.setBackground(Color.black);
        D5.setBackground(Color.black);
        D6.setBackground(Color.black);
        D7.setBackground(Color.black);
        D8.setBackground(Color.black);
        D9.setBackground(Color.black);
        D10.setBackground(Color.black);
        D11.setBackground(Color.black);
        D12.setBackground(Color.black);
        D13.setBackground(Color.black);
        D14.setBackground(Color.black);
        D15.setBackground(Color.black);
        D16.setBackground(Color.black);
        D17.setBackground(Color.black);
        D18.setBackground(Color.black);
        D19.setBackground(Color.black);
        D20.setBackground(Color.black);
        E1.setBackground(Color.black);
        E3.setBackground(Color.black);
        E2.setBackground(Color.black);
        E4.setBackground(Color.black);
        E5.setBackground(Color.black);
        E6.setBackground(Color.black);
        E7.setBackground(Color.black);
        E8.setBackground(Color.black);
        E9.setBackground(Color.black);
        E10.setBackground(Color.black);
        E11.setBackground(Color.black);
        E12.setBackground(Color.black);
        E13.setBackground(Color.black);
        E14.setBackground(Color.black);
        E15.setBackground(Color.black);
        E16.setBackground(Color.black);
        E17.setBackground(Color.black);
        E18.setBackground(Color.black);
        E19.setBackground(Color.black);
        E20.setBackground(Color.black);
        F1.setBackground(Color.black);
        F3.setBackground(Color.black);
        F2.setBackground(Color.black);
        F4.setBackground(Color.black);
        F5.setBackground(Color.black);
        F6.setBackground(Color.black);
        F7.setBackground(Color.black);
        F8.setBackground(Color.black);
        F9.setBackground(Color.black);
        F10.setBackground(Color.black);
        F11.setBackground(Color.black);
        F12.setBackground(Color.black);
        F13.setBackground(Color.black);
        F14.setBackground(Color.black);
        F15.setBackground(Color.black);
        F16.setBackground(Color.black);
        F17.setBackground(Color.black);
        F18.setBackground(Color.black);
        F19.setBackground(Color.black);
        F20.setBackground(Color.black);
        G1.setBackground(Color.black);
        G3.setBackground(Color.black);
        G2.setBackground(Color.black);
        G4.setBackground(Color.black);
        G5.setBackground(Color.black);
        G6.setBackground(Color.black);
        G7.setBackground(Color.black);
        G8.setBackground(Color.black);
        G9.setBackground(Color.black);
        G10.setBackground(Color.black);
        G11.setBackground(Color.black);
        G12.setBackground(Color.black);
        G13.setBackground(Color.black);
        G14.setBackground(Color.black);
        G15.setBackground(Color.black);
        G16.setBackground(Color.black);
        G17.setBackground(Color.black);
        G18.setBackground(Color.black);
        G19.setBackground(Color.black);
        G20.setBackground(Color.black);
        H1.setBackground(Color.black);
        H3.setBackground(Color.black);
        H2.setBackground(Color.black);
        H4.setBackground(Color.black);
        H5.setBackground(Color.black);
        H6.setBackground(Color.black);
        H7.setBackground(Color.black);
        H8.setBackground(Color.black);
        H9.setBackground(Color.black);
        H10.setBackground(Color.black);
        H11.setBackground(Color.black);
        H12.setBackground(Color.black);
        H13.setBackground(Color.black);
        H14.setBackground(Color.black);
        H15.setBackground(Color.black);
        H16.setBackground(Color.black);
        H17.setBackground(Color.black);
        H18.setBackground(Color.black);
        H19.setBackground(Color.black);
        H20.setBackground(Color.black);
        I1.setBackground(Color.black);
        I3.setBackground(Color.black);
        I2.setBackground(Color.black);
        I4.setBackground(Color.black);
        I5.setBackground(Color.black);
        I6.setBackground(Color.black);
        I7.setBackground(Color.black);
        I8.setBackground(Color.black);
        I9.setBackground(Color.black);
        I10.setBackground(Color.black);
        I11.setBackground(Color.black);
        I12.setBackground(Color.black);
        I13.setBackground(Color.black);
        I14.setBackground(Color.black);
        I15.setBackground(Color.black);
        I16.setBackground(Color.black);
        I17.setBackground(Color.black);
        I18.setBackground(Color.black);
        I19.setBackground(Color.black);
        I20.setBackground(Color.black);
        J1.setBackground(Color.black);
        J3.setBackground(Color.black);
        J2.setBackground(Color.black);
        J4.setBackground(Color.black);
        J5.setBackground(Color.black);
        J6.setBackground(Color.black);
        J7.setBackground(Color.black);
        J8.setBackground(Color.black);
        J9.setBackground(Color.black);
        J10.setBackground(Color.black);
        J11.setBackground(Color.black);
        J12.setBackground(Color.black);
        J13.setBackground(Color.black);
        J14.setBackground(Color.black);
        J15.setBackground(Color.black);
        J16.setBackground(Color.black);
        J17.setBackground(Color.black);
        J18.setBackground(Color.black);
        J19.setBackground(Color.black);
        J20.setBackground(Color.black);
        K1.setBackground(Color.black);
        K3.setBackground(Color.black);
        K2.setBackground(Color.black);
        K4.setBackground(Color.black);
        K5.setBackground(Color.black);
        K6.setBackground(Color.black);
        K7.setBackground(Color.black);
        K8.setBackground(Color.black);
        K9.setBackground(Color.black);
        K10.setBackground(Color.black);
        K11.setBackground(Color.black);
        K12.setBackground(Color.black);
        K13.setBackground(Color.black);
        K14.setBackground(Color.black);
        K15.setBackground(Color.black);
        K16.setBackground(Color.black);
        K17.setBackground(Color.black);
        K18.setBackground(Color.black);
        K19.setBackground(Color.black);
        K20.setBackground(Color.black);
        L1.setBackground(Color.black);
        L3.setBackground(Color.black);
        L2.setBackground(Color.black);
        L4.setBackground(Color.black);
        L5.setBackground(Color.black);
        L6.setBackground(Color.black);
        L7.setBackground(Color.black);
        L8.setBackground(Color.black);
        L9.setBackground(Color.black);
        L10.setBackground(Color.black);
        L11.setBackground(Color.black);
        L12.setBackground(Color.black);
        L13.setBackground(Color.black);
        L14.setBackground(Color.black);
        L15.setBackground(Color.black);
        L16.setBackground(Color.black);
        L17.setBackground(Color.black);
        L18.setBackground(Color.black);
        L19.setBackground(Color.black);
        L20.setBackground(Color.black);
        M1.setBackground(Color.black);
        M3.setBackground(Color.black);
        M2.setBackground(Color.black);
        M4.setBackground(Color.black);
        M5.setBackground(Color.black);
        M6.setBackground(Color.black);
        M7.setBackground(Color.black);
        M8.setBackground(Color.black);
        M9.setBackground(Color.black);
        M10.setBackground(Color.black);
        M11.setBackground(Color.black);
        M12.setBackground(Color.black);
        M13.setBackground(Color.black);
        M14.setBackground(Color.black);
        M15.setBackground(Color.black);
        M16.setBackground(Color.black);
        M17.setBackground(Color.black);
        M18.setBackground(Color.black);
        M19.setBackground(Color.black);
        M20.setBackground(Color.black);
        N1.setBackground(Color.black);
        N3.setBackground(Color.black);
        N2.setBackground(Color.black);
        N4.setBackground(Color.black);
        N5.setBackground(Color.black);
        N6.setBackground(Color.black);
        N7.setBackground(Color.black);
        N8.setBackground(Color.black);
        N9.setBackground(Color.black);
        N10.setBackground(Color.black);
        N11.setBackground(Color.black);
        N12.setBackground(Color.black);
        N13.setBackground(Color.black);
        N14.setBackground(Color.black);
        N15.setBackground(Color.black);
        N16.setBackground(Color.black);
        N17.setBackground(Color.black);
        N18.setBackground(Color.black);
        N19.setBackground(Color.black);
        N20.setBackground(Color.black);
        O1.setBackground(Color.black);
        O3.setBackground(Color.black);
        O2.setBackground(Color.black);
        O4.setBackground(Color.black);
        O5.setBackground(Color.black);
        O6.setBackground(Color.black);
        O7.setBackground(Color.black);
        O8.setBackground(Color.black);
        O9.setBackground(Color.black);
        O10.setBackground(Color.black);
        O11.setBackground(Color.black);
        O12.setBackground(Color.black);
        O13.setBackground(Color.black);
        O14.setBackground(Color.black);
        O15.setBackground(Color.black);
        O16.setBackground(Color.black);
        O17.setBackground(Color.black);
        O18.setBackground(Color.black);
        O19.setBackground(Color.black);
        O20.setBackground(Color.black);
    }
    
    private void DesenheA (int i) {
        if ( i == 0 ) A1.setBackground(Color.white);
        if ( i == 1 ) A3.setBackground(Color.white);
        if ( i == 2 ) A2.setBackground(Color.white);
        if ( i == 3 ) A4.setBackground(Color.white);
        if ( i == 4 ) A5.setBackground(Color.white);
        if ( i == 5 ) A6.setBackground(Color.white);
        if ( i == 6 ) A7.setBackground(Color.white);
        if ( i == 7 ) A8.setBackground(Color.white);
        if ( i == 8 ) A9.setBackground(Color.white);
        if ( i == 9 ) A10.setBackground(Color.white);
        if ( i == 10 ) A11.setBackground(Color.white);
        if ( i == 11 ) A12.setBackground(Color.white);
        if ( i == 12 ) A13.setBackground(Color.white);
        if ( i == 13 ) A14.setBackground(Color.white);
        if ( i == 14 ) A15.setBackground(Color.white);
        if ( i == 15 ) A16.setBackground(Color.white);
        if ( i == 16 ) A17.setBackground(Color.white);
        if ( i == 17 ) A18.setBackground(Color.white);
        if ( i == 18 ) A19.setBackground(Color.white);
        if ( i == 19 ) A20.setBackground(Color.white);
    }
    
    private void DesenheB ( int i ) {
        if ( i == 0 ) B1.setBackground(Color.white);
        if ( i == 1 ) B2.setBackground(Color.white);
        if ( i == 2 ) B3.setBackground(Color.white);
        if ( i == 3 ) B4.setBackground(Color.white);
        if ( i == 4 ) B5.setBackground(Color.white);
        if ( i == 5 ) B6.setBackground(Color.white);
        if ( i == 6 ) B7.setBackground(Color.white);
        if ( i == 7 ) B8.setBackground(Color.white);
        if ( i == 8 ) B9.setBackground(Color.white);
        if ( i == 9 ) B10.setBackground(Color.white);
        if ( i == 10 ) B11.setBackground(Color.white);
        if ( i == 11 ) B12.setBackground(Color.white);
        if ( i == 12 ) B13.setBackground(Color.white);
        if ( i == 13 ) B14.setBackground(Color.white);
        if ( i == 14 ) B15.setBackground(Color.white);
        if ( i == 15 ) B16.setBackground(Color.white);
        if ( i == 16 ) B17.setBackground(Color.white);
        if ( i == 17 ) B18.setBackground(Color.white);
        if ( i == 18 ) B19.setBackground(Color.white);
        if ( i == 19 ) B20.setBackground(Color.white);
    }
    
    private void DesenheC ( int i ) {
        if ( i == 0 ) C1.setBackground(Color.white);
        if ( i == 1 ) C2.setBackground(Color.white);
        if ( i == 2 ) C3.setBackground(Color.white);
        if ( i == 3 ) C4.setBackground(Color.white);
        if ( i == 4 ) C5.setBackground(Color.white);
        if ( i == 5 ) C6.setBackground(Color.white);
        if ( i == 6 ) C7.setBackground(Color.white);
        if ( i == 7 ) C8.setBackground(Color.white);
        if ( i == 8 ) C9.setBackground(Color.white);
        if ( i == 9 ) C10.setBackground(Color.white);
        if ( i == 10 ) C11.setBackground(Color.white);
        if ( i == 11 ) C12.setBackground(Color.white);
        if ( i == 12 ) C13.setBackground(Color.white);
        if ( i == 13 ) C14.setBackground(Color.white);
        if ( i == 14 ) C15.setBackground(Color.white);
        if ( i == 15 ) C16.setBackground(Color.white);
        if ( i == 16 ) C17.setBackground(Color.white);
        if ( i == 17 ) C18.setBackground(Color.white);
        if ( i == 18 ) C19.setBackground(Color.white);
        if ( i == 19 ) C20.setBackground(Color.white);
    }
    
    private void DesenheD ( int i ) {
        if ( i == 0 ) D1.setBackground(Color.white);
        if ( i == 1 ) D3.setBackground(Color.white);
        if ( i == 2 ) D2.setBackground(Color.white);
        if ( i == 3 ) D4.setBackground(Color.white);
        if ( i == 4 ) D5.setBackground(Color.white);
        if ( i == 5 ) D6.setBackground(Color.white);
        if ( i == 6 ) D7.setBackground(Color.white);
        if ( i == 7 ) D8.setBackground(Color.white);
        if ( i == 8 ) D9.setBackground(Color.white);
        if ( i == 9 ) D10.setBackground(Color.white);
        if ( i == 10 ) D11.setBackground(Color.white);
        if ( i == 11 ) D12.setBackground(Color.white);
        if ( i == 12 ) D13.setBackground(Color.white);
        if ( i == 13 ) D14.setBackground(Color.white);
        if ( i == 14 ) D15.setBackground(Color.white);
        if ( i == 15 ) D16.setBackground(Color.white);
        if ( i == 16 ) D17.setBackground(Color.white);
        if ( i == 17 ) D18.setBackground(Color.white);
        if ( i == 18 ) D19.setBackground(Color.white);
        if ( i == 19 ) D20.setBackground(Color.white);
    }
    
    private void DesenheE ( int i ) {
        if ( i == 0 ) E1.setBackground(Color.white);
        if ( i == 1 ) E3.setBackground(Color.white);
        if ( i == 2 ) E2.setBackground(Color.white);
        if ( i == 3 ) E4.setBackground(Color.white);
        if ( i == 4 ) E5.setBackground(Color.white);
        if ( i == 5 ) E6.setBackground(Color.white);
        if ( i == 6 ) E7.setBackground(Color.white);
        if ( i == 7 ) E8.setBackground(Color.white);
        if ( i == 8 ) E9.setBackground(Color.white);
        if ( i == 9 ) E10.setBackground(Color.white);
        if ( i == 10 ) E11.setBackground(Color.white);
        if ( i == 11 ) E12.setBackground(Color.white);
        if ( i == 12 ) E13.setBackground(Color.white);
        if ( i == 13 ) E14.setBackground(Color.white);
        if ( i == 14 ) E15.setBackground(Color.white);
        if ( i == 15 ) E16.setBackground(Color.white);
        if ( i == 16 ) E17.setBackground(Color.white);
        if ( i == 17 ) E18.setBackground(Color.white);
        if ( i == 18 ) E19.setBackground(Color.white);
        if ( i == 19 ) E20.setBackground(Color.white);
    }
    
    private void DesenheF ( int i ) {
        if ( i == 0 ) F1.setBackground(Color.white);
        if ( i == 1 ) F3.setBackground(Color.white);
        if ( i == 2 ) F2.setBackground(Color.white);
        if ( i == 3 ) F4.setBackground(Color.white);
        if ( i == 4 ) F5.setBackground(Color.white);
        if ( i == 5 ) F6.setBackground(Color.white);
        if ( i == 6 ) F7.setBackground(Color.white);
        if ( i == 7 ) F8.setBackground(Color.white);
        if ( i == 8 ) F9.setBackground(Color.white);
        if ( i == 9 ) F10.setBackground(Color.white);
        if ( i == 10 ) F11.setBackground(Color.white);
        if ( i == 11 ) F12.setBackground(Color.white);
        if ( i == 12 ) F13.setBackground(Color.white);
        if ( i == 13 ) F14.setBackground(Color.white);
        if ( i == 14 ) F15.setBackground(Color.white);
        if ( i == 15 ) F16.setBackground(Color.white);
        if ( i == 16 ) F17.setBackground(Color.white);
        if ( i == 17 ) F18.setBackground(Color.white);
        if ( i == 18 ) F19.setBackground(Color.white);
        if ( i == 19 ) F20.setBackground(Color.white);
    }
    
    private void DesenheG ( int i ) {
        if ( i == 0 ) G1.setBackground(Color.white);
        if ( i == 1 ) G3.setBackground(Color.white);
        if ( i == 2 ) G2.setBackground(Color.white);
        if ( i == 3 ) G4.setBackground(Color.white);
        if ( i == 4 ) G5.setBackground(Color.white);
        if ( i == 5 ) G6.setBackground(Color.white);
        if ( i == 6 ) G7.setBackground(Color.white);
        if ( i == 7 ) G8.setBackground(Color.white);
        if ( i == 8 ) G9.setBackground(Color.white);
        if ( i == 9 ) G10.setBackground(Color.white);
        if ( i == 10 ) G11.setBackground(Color.white);
        if ( i == 11 ) G12.setBackground(Color.white);
        if ( i == 12 ) G13.setBackground(Color.white);
        if ( i == 13 ) G14.setBackground(Color.white);
        if ( i == 14 ) G15.setBackground(Color.white);
        if ( i == 15 ) G16.setBackground(Color.white);
        if ( i == 16 ) G17.setBackground(Color.white);
        if ( i == 17 ) G18.setBackground(Color.white);
        if ( i == 18 ) G19.setBackground(Color.white);
        if ( i == 19 ) G20.setBackground(Color.white);
    }
    
    private void DesenheH ( int i ) {
        if ( i == 0 ) H1.setBackground(Color.white);
        if ( i == 1 ) H3.setBackground(Color.white);
        if ( i == 2 ) H2.setBackground(Color.white);
        if ( i == 3 ) H4.setBackground(Color.white);
        if ( i == 4 ) H5.setBackground(Color.white);
        if ( i == 5 ) H6.setBackground(Color.white);
        if ( i == 6 ) H7.setBackground(Color.white);
        if ( i == 7 ) H8.setBackground(Color.white);
        if ( i == 8 ) H9.setBackground(Color.white);
        if ( i == 9 ) H10.setBackground(Color.white);
        if ( i == 10 ) H11.setBackground(Color.white);
        if ( i == 11 ) H12.setBackground(Color.white);
        if ( i == 12 ) H13.setBackground(Color.white);
        if ( i == 13 ) H14.setBackground(Color.white);
        if ( i == 14 ) H15.setBackground(Color.white);
        if ( i == 15 ) H16.setBackground(Color.white);
        if ( i == 16 ) H17.setBackground(Color.white);
        if ( i == 17 ) H18.setBackground(Color.white);
        if ( i == 18 ) H19.setBackground(Color.white);
        if ( i == 19 ) H20.setBackground(Color.white);
    }
    
    private void DesenheI ( int i ) {
        if ( i == 0 ) I1.setBackground(Color.white);
        if ( i == 1 ) I3.setBackground(Color.white);
        if ( i == 2 ) I2.setBackground(Color.white);
        if ( i == 3 ) I4.setBackground(Color.white);
        if ( i == 4 ) I5.setBackground(Color.white);
        if ( i == 5 ) I6.setBackground(Color.white);
        if ( i == 6 ) I7.setBackground(Color.white);
        if ( i == 7 ) I8.setBackground(Color.white);
        if ( i == 8 ) I9.setBackground(Color.white);
        if ( i == 9 ) I10.setBackground(Color.white);
        if ( i == 10 ) I11.setBackground(Color.white);
        if ( i == 11 ) I12.setBackground(Color.white);
        if ( i == 12 ) I13.setBackground(Color.white);
        if ( i == 13 ) I14.setBackground(Color.white);
        if ( i == 14 ) I15.setBackground(Color.white);
        if ( i == 15 ) I16.setBackground(Color.white);
        if ( i == 16 ) I17.setBackground(Color.white);
        if ( i == 17 ) I18.setBackground(Color.white);
        if ( i == 18 ) I19.setBackground(Color.white);
        if ( i == 19 ) I20.setBackground(Color.white);
    }
    
    private void DesenheJ ( int i ) {
        if ( i == 0 ) J1.setBackground(Color.white);
        if ( i == 1 ) J3.setBackground(Color.white);
        if ( i == 2 ) J2.setBackground(Color.white);
        if ( i == 3 ) J4.setBackground(Color.white);
        if ( i == 4 ) J5.setBackground(Color.white);
        if ( i == 5 ) J6.setBackground(Color.white);
        if ( i == 6 ) J7.setBackground(Color.white);
        if ( i == 7 ) J8.setBackground(Color.white);
        if ( i == 8 ) J9.setBackground(Color.white);
        if ( i == 9 ) J10.setBackground(Color.white);
        if ( i == 10 ) J11.setBackground(Color.white);
        if ( i == 11 ) J12.setBackground(Color.white);
        if ( i == 12 ) J13.setBackground(Color.white);
        if ( i == 13 ) J14.setBackground(Color.white);
        if ( i == 14 ) J15.setBackground(Color.white);
        if ( i == 15 ) J16.setBackground(Color.white);
        if ( i == 16 ) J17.setBackground(Color.white);
        if ( i == 17 ) J18.setBackground(Color.white);
        if ( i == 18 ) J19.setBackground(Color.white);
        if ( i == 19 ) J20.setBackground(Color.white);
    }
    
    private void DesenheK ( int i ) {
        if ( i == 0 ) K1.setBackground(Color.white);
        if ( i == 1 ) K3.setBackground(Color.white);
        if ( i == 2 ) K2.setBackground(Color.white);
        if ( i == 3 ) K4.setBackground(Color.white);
        if ( i == 4 ) K5.setBackground(Color.white);
        if ( i == 5 ) K6.setBackground(Color.white);
        if ( i == 6 ) K7.setBackground(Color.white);
        if ( i == 7 ) K8.setBackground(Color.white);
        if ( i == 8 ) K9.setBackground(Color.white);
        if ( i == 9 ) K10.setBackground(Color.white);
        if ( i == 10 ) K11.setBackground(Color.white);
        if ( i == 11 ) K12.setBackground(Color.white);
        if ( i == 12 ) K13.setBackground(Color.white);
        if ( i == 13 ) K14.setBackground(Color.white);
        if ( i == 14 ) K15.setBackground(Color.white);
        if ( i == 15 ) K16.setBackground(Color.white);
        if ( i == 16 ) K17.setBackground(Color.white);
        if ( i == 17 ) K18.setBackground(Color.white);
        if ( i == 18 ) K19.setBackground(Color.white);
        if ( i == 19 ) K20.setBackground(Color.white);
    }
    
    private void DesenheL ( int i ) {
        if ( i == 0 ) L1.setBackground(Color.white);
        if ( i == 1 ) L3.setBackground(Color.white);
        if ( i == 2 ) L2.setBackground(Color.white);
        if ( i == 3 ) L4.setBackground(Color.white);
        if ( i == 4 ) L5.setBackground(Color.white);
        if ( i == 5 ) L6.setBackground(Color.white);
        if ( i == 6 ) L7.setBackground(Color.white);
        if ( i == 7 ) L8.setBackground(Color.white);
        if ( i == 8 ) L9.setBackground(Color.white);
        if ( i == 9 ) L10.setBackground(Color.white);
        if ( i == 10 ) L11.setBackground(Color.white);
        if ( i == 11 ) L12.setBackground(Color.white);
        if ( i == 12 ) L13.setBackground(Color.white);
        if ( i == 13 ) L14.setBackground(Color.white);
        if ( i == 14 ) L15.setBackground(Color.white);
        if ( i == 15 ) L16.setBackground(Color.white);
        if ( i == 16 ) L17.setBackground(Color.white);
        if ( i == 17 ) L18.setBackground(Color.white);
        if ( i == 18 ) L19.setBackground(Color.white);
        if ( i == 19 ) L20.setBackground(Color.white);
    }
    
    private void DesenheM ( int i ) {
        if ( i == 0 ) M1.setBackground(Color.white);
        if ( i == 1 ) M3.setBackground(Color.white);
        if ( i == 2 ) M2.setBackground(Color.white);
        if ( i == 3 ) M4.setBackground(Color.white);
        if ( i == 4 ) M5.setBackground(Color.white);
        if ( i == 5 ) M6.setBackground(Color.white);
        if ( i == 6 ) M7.setBackground(Color.white);
        if ( i == 7 ) M8.setBackground(Color.white);
        if ( i == 8 ) M9.setBackground(Color.white);
        if ( i == 9 ) M10.setBackground(Color.white);
        if ( i == 10 ) M11.setBackground(Color.white);
        if ( i == 11 ) M12.setBackground(Color.white);
        if ( i == 12 ) M13.setBackground(Color.white);
        if ( i == 13 ) M14.setBackground(Color.white);
        if ( i == 14 ) M15.setBackground(Color.white);
        if ( i == 15 ) M16.setBackground(Color.white);
        if ( i == 16 ) M17.setBackground(Color.white);
        if ( i == 17 ) M18.setBackground(Color.white);
        if ( i == 18 ) M19.setBackground(Color.white);
        if ( i == 19 ) M20.setBackground(Color.white);
    }
    
    private void DesenheN ( int i ) {
        if ( i == 0 ) N1.setBackground(Color.white);
        if ( i == 1 ) N3.setBackground(Color.white);
        if ( i == 2 ) N2.setBackground(Color.white);
        if ( i == 3 ) N4.setBackground(Color.white);
        if ( i == 4 ) N5.setBackground(Color.white);
        if ( i == 5 ) N6.setBackground(Color.white);
        if ( i == 6 ) N7.setBackground(Color.white);
        if ( i == 7 ) N8.setBackground(Color.white);
        if ( i == 8 ) N9.setBackground(Color.white);
        if ( i == 9 ) N10.setBackground(Color.white);
        if ( i == 10 ) N11.setBackground(Color.white);
        if ( i == 11 ) N12.setBackground(Color.white);
        if ( i == 12 ) N13.setBackground(Color.white);
        if ( i == 13 ) N14.setBackground(Color.white);
        if ( i == 14 ) N15.setBackground(Color.white);
        if ( i == 15 ) N16.setBackground(Color.white);
        if ( i == 16 ) N17.setBackground(Color.white);
        if ( i == 17 ) N18.setBackground(Color.white);
        if ( i == 18 ) N19.setBackground(Color.white);
        if ( i == 19 ) N20.setBackground(Color.white);
    }
    
    private void DesenheO ( int i ) {
        if ( i == 0 ) O1.setBackground(Color.white);
        if ( i == 1 ) O3.setBackground(Color.white);
        if ( i == 2 ) O2.setBackground(Color.white);
        if ( i == 3 ) O4.setBackground(Color.white);
        if ( i == 4 ) O5.setBackground(Color.white);
        if ( i == 5 ) O6.setBackground(Color.white);
        if ( i == 6 ) O7.setBackground(Color.white);
        if ( i == 7 ) O8.setBackground(Color.white);
        if ( i == 8 ) O9.setBackground(Color.white);
        if ( i == 9 ) O10.setBackground(Color.white);
        if ( i == 10 ) O11.setBackground(Color.white);
        if ( i == 11 ) O12.setBackground(Color.white);
        if ( i == 12 ) O13.setBackground(Color.white);
        if ( i == 13 ) O14.setBackground(Color.white);
        if ( i == 14 ) O15.setBackground(Color.white);
        if ( i == 15 ) O16.setBackground(Color.white);
        if ( i == 16 ) O17.setBackground(Color.white);
        if ( i == 17 ) O18.setBackground(Color.white);
        if ( i == 18 ) O19.setBackground(Color.white);
        if ( i == 19 ) O20.setBackground(Color.white);
    }
    
    public int getComboBox3 () {
        if (jComboBox3.getSelectedIndex() == 0) return 10;
        else if (jComboBox3.getSelectedIndex() == 1) return 50;
        else if (jComboBox3.getSelectedIndex() == 2) return 100; 
        else if (jComboBox3.getSelectedIndex() == 3) return 250; 
        else if (jComboBox3.getSelectedIndex() == 4) return 500; 
        else if (jComboBox3.getSelectedIndex() == 5) return 1000; 
        
        return 5000;
    }
    
    public int getComboBox4 () {
        if (jComboBox4.getSelectedIndex() == 0) return 70;
        else if (jComboBox4.getSelectedIndex() == 1) return 75;
        else if (jComboBox4.getSelectedIndex() == 2) return 80; 
        else if (jComboBox4.getSelectedIndex() == 3) return 85; 
        
        return 90;
    }
    
    public int getComboBox5 () {
        if (jComboBox5.getSelectedIndex() == 0) return 1;
        else if (jComboBox5.getSelectedIndex() == 1) return 3; 
        
        return 5;
    }
    
    public int getComboBox6 () {
        if (jComboBox6.getSelectedIndex() == 0) return 10;
        else if (jComboBox6.getSelectedIndex() == 1) return 50;
        else if (jComboBox6.getSelectedIndex() == 2) return 100; 
        else if (jComboBox6.getSelectedIndex() == 3) return 250; 
        else if (jComboBox6.getSelectedIndex() == 4) return 500; 
        else if (jComboBox6.getSelectedIndex() == 5) return 1000; 
        
        return 5000;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mainPanel = new javax.swing.JPanel();
        jLayeredPane1 = new javax.swing.JLayeredPane();
        jLabel1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox();
        jComboBox2 = new javax.swing.JComboBox();
        jComboBox3 = new javax.swing.JComboBox();
        jComboBox4 = new javax.swing.JComboBox();
        jComboBox5 = new javax.swing.JComboBox();
        jComboBox6 = new javax.swing.JComboBox();
        jComboBox7 = new javax.swing.JComboBox();
        label1 = new java.awt.Label();
        label2 = new java.awt.Label();
        jPanel2 = new javax.swing.JPanel();
        A1 = new javax.swing.JPanel();
        A3 = new javax.swing.JPanel();
        A2 = new javax.swing.JPanel();
        A4 = new javax.swing.JPanel();
        A5 = new javax.swing.JPanel();
        A6 = new javax.swing.JPanel();
        A7 = new javax.swing.JPanel();
        A8 = new javax.swing.JPanel();
        A9 = new javax.swing.JPanel();
        A10 = new javax.swing.JPanel();
        A11 = new javax.swing.JPanel();
        A12 = new javax.swing.JPanel();
        A13 = new javax.swing.JPanel();
        A14 = new javax.swing.JPanel();
        A15 = new javax.swing.JPanel();
        A16 = new javax.swing.JPanel();
        A17 = new javax.swing.JPanel();
        A18 = new javax.swing.JPanel();
        A19 = new javax.swing.JPanel();
        A20 = new javax.swing.JPanel();
        B1 = new javax.swing.JPanel();
        B2 = new javax.swing.JPanel();
        B3 = new javax.swing.JPanel();
        B4 = new javax.swing.JPanel();
        B5 = new javax.swing.JPanel();
        B6 = new javax.swing.JPanel();
        B7 = new javax.swing.JPanel();
        B8 = new javax.swing.JPanel();
        B9 = new javax.swing.JPanel();
        B10 = new javax.swing.JPanel();
        B11 = new javax.swing.JPanel();
        B12 = new javax.swing.JPanel();
        B13 = new javax.swing.JPanel();
        B14 = new javax.swing.JPanel();
        B15 = new javax.swing.JPanel();
        B16 = new javax.swing.JPanel();
        B17 = new javax.swing.JPanel();
        B18 = new javax.swing.JPanel();
        B19 = new javax.swing.JPanel();
        B20 = new javax.swing.JPanel();
        C1 = new javax.swing.JPanel();
        C2 = new javax.swing.JPanel();
        C3 = new javax.swing.JPanel();
        C4 = new javax.swing.JPanel();
        C5 = new javax.swing.JPanel();
        C6 = new javax.swing.JPanel();
        C7 = new javax.swing.JPanel();
        C8 = new javax.swing.JPanel();
        C9 = new javax.swing.JPanel();
        C10 = new javax.swing.JPanel();
        C11 = new javax.swing.JPanel();
        C12 = new javax.swing.JPanel();
        C13 = new javax.swing.JPanel();
        C14 = new javax.swing.JPanel();
        C15 = new javax.swing.JPanel();
        C16 = new javax.swing.JPanel();
        C17 = new javax.swing.JPanel();
        C18 = new javax.swing.JPanel();
        C19 = new javax.swing.JPanel();
        C20 = new javax.swing.JPanel();
        D1 = new javax.swing.JPanel();
        D3 = new javax.swing.JPanel();
        D2 = new javax.swing.JPanel();
        D4 = new javax.swing.JPanel();
        D5 = new javax.swing.JPanel();
        D6 = new javax.swing.JPanel();
        D7 = new javax.swing.JPanel();
        D8 = new javax.swing.JPanel();
        D9 = new javax.swing.JPanel();
        D10 = new javax.swing.JPanel();
        D11 = new javax.swing.JPanel();
        D12 = new javax.swing.JPanel();
        D13 = new javax.swing.JPanel();
        D14 = new javax.swing.JPanel();
        D15 = new javax.swing.JPanel();
        D16 = new javax.swing.JPanel();
        D17 = new javax.swing.JPanel();
        D18 = new javax.swing.JPanel();
        D19 = new javax.swing.JPanel();
        D20 = new javax.swing.JPanel();
        E1 = new javax.swing.JPanel();
        E3 = new javax.swing.JPanel();
        E2 = new javax.swing.JPanel();
        E4 = new javax.swing.JPanel();
        E5 = new javax.swing.JPanel();
        E6 = new javax.swing.JPanel();
        E7 = new javax.swing.JPanel();
        E8 = new javax.swing.JPanel();
        E9 = new javax.swing.JPanel();
        E10 = new javax.swing.JPanel();
        E11 = new javax.swing.JPanel();
        E12 = new javax.swing.JPanel();
        E13 = new javax.swing.JPanel();
        E14 = new javax.swing.JPanel();
        E15 = new javax.swing.JPanel();
        E16 = new javax.swing.JPanel();
        E17 = new javax.swing.JPanel();
        E18 = new javax.swing.JPanel();
        E19 = new javax.swing.JPanel();
        E20 = new javax.swing.JPanel();
        F1 = new javax.swing.JPanel();
        F3 = new javax.swing.JPanel();
        F2 = new javax.swing.JPanel();
        F4 = new javax.swing.JPanel();
        F5 = new javax.swing.JPanel();
        F6 = new javax.swing.JPanel();
        F7 = new javax.swing.JPanel();
        F8 = new javax.swing.JPanel();
        F9 = new javax.swing.JPanel();
        F10 = new javax.swing.JPanel();
        F11 = new javax.swing.JPanel();
        F12 = new javax.swing.JPanel();
        F13 = new javax.swing.JPanel();
        F14 = new javax.swing.JPanel();
        F15 = new javax.swing.JPanel();
        F16 = new javax.swing.JPanel();
        F17 = new javax.swing.JPanel();
        F18 = new javax.swing.JPanel();
        F19 = new javax.swing.JPanel();
        F20 = new javax.swing.JPanel();
        G1 = new javax.swing.JPanel();
        G3 = new javax.swing.JPanel();
        G2 = new javax.swing.JPanel();
        G4 = new javax.swing.JPanel();
        G5 = new javax.swing.JPanel();
        G6 = new javax.swing.JPanel();
        G7 = new javax.swing.JPanel();
        G8 = new javax.swing.JPanel();
        G9 = new javax.swing.JPanel();
        G10 = new javax.swing.JPanel();
        G11 = new javax.swing.JPanel();
        G12 = new javax.swing.JPanel();
        G13 = new javax.swing.JPanel();
        G14 = new javax.swing.JPanel();
        G15 = new javax.swing.JPanel();
        G16 = new javax.swing.JPanel();
        G17 = new javax.swing.JPanel();
        G18 = new javax.swing.JPanel();
        G19 = new javax.swing.JPanel();
        G20 = new javax.swing.JPanel();
        H1 = new javax.swing.JPanel();
        H3 = new javax.swing.JPanel();
        H2 = new javax.swing.JPanel();
        H4 = new javax.swing.JPanel();
        H5 = new javax.swing.JPanel();
        H6 = new javax.swing.JPanel();
        H7 = new javax.swing.JPanel();
        H8 = new javax.swing.JPanel();
        H9 = new javax.swing.JPanel();
        H10 = new javax.swing.JPanel();
        H11 = new javax.swing.JPanel();
        H12 = new javax.swing.JPanel();
        H13 = new javax.swing.JPanel();
        H14 = new javax.swing.JPanel();
        H15 = new javax.swing.JPanel();
        H16 = new javax.swing.JPanel();
        H17 = new javax.swing.JPanel();
        H18 = new javax.swing.JPanel();
        H19 = new javax.swing.JPanel();
        H20 = new javax.swing.JPanel();
        I1 = new javax.swing.JPanel();
        I3 = new javax.swing.JPanel();
        I2 = new javax.swing.JPanel();
        I4 = new javax.swing.JPanel();
        I5 = new javax.swing.JPanel();
        I6 = new javax.swing.JPanel();
        I7 = new javax.swing.JPanel();
        I8 = new javax.swing.JPanel();
        I9 = new javax.swing.JPanel();
        I10 = new javax.swing.JPanel();
        I11 = new javax.swing.JPanel();
        I12 = new javax.swing.JPanel();
        I13 = new javax.swing.JPanel();
        I14 = new javax.swing.JPanel();
        I15 = new javax.swing.JPanel();
        I16 = new javax.swing.JPanel();
        I17 = new javax.swing.JPanel();
        I18 = new javax.swing.JPanel();
        I19 = new javax.swing.JPanel();
        I20 = new javax.swing.JPanel();
        J1 = new javax.swing.JPanel();
        J3 = new javax.swing.JPanel();
        J2 = new javax.swing.JPanel();
        J4 = new javax.swing.JPanel();
        J5 = new javax.swing.JPanel();
        J6 = new javax.swing.JPanel();
        J7 = new javax.swing.JPanel();
        J8 = new javax.swing.JPanel();
        J9 = new javax.swing.JPanel();
        J10 = new javax.swing.JPanel();
        J11 = new javax.swing.JPanel();
        J12 = new javax.swing.JPanel();
        J13 = new javax.swing.JPanel();
        J14 = new javax.swing.JPanel();
        J15 = new javax.swing.JPanel();
        J16 = new javax.swing.JPanel();
        J17 = new javax.swing.JPanel();
        J18 = new javax.swing.JPanel();
        J19 = new javax.swing.JPanel();
        J20 = new javax.swing.JPanel();
        K1 = new javax.swing.JPanel();
        K3 = new javax.swing.JPanel();
        K2 = new javax.swing.JPanel();
        K4 = new javax.swing.JPanel();
        K5 = new javax.swing.JPanel();
        K6 = new javax.swing.JPanel();
        K7 = new javax.swing.JPanel();
        K8 = new javax.swing.JPanel();
        K9 = new javax.swing.JPanel();
        K10 = new javax.swing.JPanel();
        K11 = new javax.swing.JPanel();
        K12 = new javax.swing.JPanel();
        K13 = new javax.swing.JPanel();
        K14 = new javax.swing.JPanel();
        K15 = new javax.swing.JPanel();
        K16 = new javax.swing.JPanel();
        K17 = new javax.swing.JPanel();
        K18 = new javax.swing.JPanel();
        K19 = new javax.swing.JPanel();
        K20 = new javax.swing.JPanel();
        L1 = new javax.swing.JPanel();
        L3 = new javax.swing.JPanel();
        L2 = new javax.swing.JPanel();
        L4 = new javax.swing.JPanel();
        L5 = new javax.swing.JPanel();
        L6 = new javax.swing.JPanel();
        L7 = new javax.swing.JPanel();
        L8 = new javax.swing.JPanel();
        L9 = new javax.swing.JPanel();
        L10 = new javax.swing.JPanel();
        L11 = new javax.swing.JPanel();
        L12 = new javax.swing.JPanel();
        L13 = new javax.swing.JPanel();
        L14 = new javax.swing.JPanel();
        L15 = new javax.swing.JPanel();
        L16 = new javax.swing.JPanel();
        L17 = new javax.swing.JPanel();
        L18 = new javax.swing.JPanel();
        L19 = new javax.swing.JPanel();
        L20 = new javax.swing.JPanel();
        M1 = new javax.swing.JPanel();
        M3 = new javax.swing.JPanel();
        M2 = new javax.swing.JPanel();
        M4 = new javax.swing.JPanel();
        M5 = new javax.swing.JPanel();
        M6 = new javax.swing.JPanel();
        M7 = new javax.swing.JPanel();
        M8 = new javax.swing.JPanel();
        M9 = new javax.swing.JPanel();
        M10 = new javax.swing.JPanel();
        M11 = new javax.swing.JPanel();
        M12 = new javax.swing.JPanel();
        M13 = new javax.swing.JPanel();
        M14 = new javax.swing.JPanel();
        M15 = new javax.swing.JPanel();
        M16 = new javax.swing.JPanel();
        M17 = new javax.swing.JPanel();
        M18 = new javax.swing.JPanel();
        M19 = new javax.swing.JPanel();
        M20 = new javax.swing.JPanel();
        N1 = new javax.swing.JPanel();
        N2 = new javax.swing.JPanel();
        N3 = new javax.swing.JPanel();
        N4 = new javax.swing.JPanel();
        N5 = new javax.swing.JPanel();
        N6 = new javax.swing.JPanel();
        N7 = new javax.swing.JPanel();
        N8 = new javax.swing.JPanel();
        N9 = new javax.swing.JPanel();
        N10 = new javax.swing.JPanel();
        N11 = new javax.swing.JPanel();
        N12 = new javax.swing.JPanel();
        N13 = new javax.swing.JPanel();
        N14 = new javax.swing.JPanel();
        N15 = new javax.swing.JPanel();
        N16 = new javax.swing.JPanel();
        N17 = new javax.swing.JPanel();
        N18 = new javax.swing.JPanel();
        N19 = new javax.swing.JPanel();
        N20 = new javax.swing.JPanel();
        O1 = new javax.swing.JPanel();
        O3 = new javax.swing.JPanel();
        O2 = new javax.swing.JPanel();
        O4 = new javax.swing.JPanel();
        O5 = new javax.swing.JPanel();
        O6 = new javax.swing.JPanel();
        O7 = new javax.swing.JPanel();
        O8 = new javax.swing.JPanel();
        O9 = new javax.swing.JPanel();
        O10 = new javax.swing.JPanel();
        O11 = new javax.swing.JPanel();
        O12 = new javax.swing.JPanel();
        O13 = new javax.swing.JPanel();
        O14 = new javax.swing.JPanel();
        O15 = new javax.swing.JPanel();
        O16 = new javax.swing.JPanel();
        O17 = new javax.swing.JPanel();
        O18 = new javax.swing.JPanel();
        O19 = new javax.swing.JPanel();
        O20 = new javax.swing.JPanel();
        P1 = new javax.swing.JPanel();
        P3 = new javax.swing.JPanel();
        P2 = new javax.swing.JPanel();
        P4 = new javax.swing.JPanel();
        P5 = new javax.swing.JPanel();
        P6 = new javax.swing.JPanel();
        P7 = new javax.swing.JPanel();
        P8 = new javax.swing.JPanel();
        P9 = new javax.swing.JPanel();
        P10 = new javax.swing.JPanel();
        P11 = new javax.swing.JPanel();
        P12 = new javax.swing.JPanel();
        P13 = new javax.swing.JPanel();
        P14 = new javax.swing.JPanel();
        P15 = new javax.swing.JPanel();
        P16 = new javax.swing.JPanel();
        P17 = new javax.swing.JPanel();
        P18 = new javax.swing.JPanel();
        P19 = new javax.swing.JPanel();
        P20 = new javax.swing.JPanel();
        menuBar = new javax.swing.JMenuBar();
        javax.swing.JMenu fileMenu = new javax.swing.JMenu();
        javax.swing.JMenuItem exitMenuItem = new javax.swing.JMenuItem();
        javax.swing.JMenu helpMenu = new javax.swing.JMenu();
        javax.swing.JMenuItem aboutMenuItem = new javax.swing.JMenuItem();
        statusPanel = new javax.swing.JPanel();
        javax.swing.JSeparator statusPanelSeparator = new javax.swing.JSeparator();
        statusMessageLabel = new javax.swing.JLabel();
        statusAnimationLabel = new javax.swing.JLabel();
        progressBar = new javax.swing.JProgressBar();

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(ratatouille.RatatouilleApp.class).getContext().getResourceMap(RatatouilleView.class);
        mainPanel.setBackground(resourceMap.getColor("mainPanel.background")); // NOI18N
        mainPanel.setName("mainPanel"); // NOI18N

        jLayeredPane1.setName("jLayeredPane1"); // NOI18N

        jLabel1.setIcon(resourceMap.getIcon("jLabel1.icon")); // NOI18N
        jLabel1.setText(resourceMap.getString("jLabel1.text")); // NOI18N
        jLabel1.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jLabel1.setName("jLabel1"); // NOI18N
        jLabel1.setBounds(0, 0, 440, 470);
        jLayeredPane1.add(jLabel1, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jPanel1.setBackground(resourceMap.getColor("jPanel1.background")); // NOI18N
        jPanel1.setName("jPanel1"); // NOI18N

        jLabel2.setFont(resourceMap.getFont("jLabel2.font")); // NOI18N
        jLabel2.setForeground(resourceMap.getColor("jLabel2.foreground")); // NOI18N
        jLabel2.setText(resourceMap.getString("jLabel2.text")); // NOI18N
        jLabel2.setName("jLabel2"); // NOI18N

        jLabel3.setFont(resourceMap.getFont("jLabel3.font")); // NOI18N
        jLabel3.setForeground(resourceMap.getColor("jLabel3.foreground")); // NOI18N
        jLabel3.setText(resourceMap.getString("jLabel3.text")); // NOI18N
        jLabel3.setName("jLabel3"); // NOI18N

        jLabel4.setFont(resourceMap.getFont("jLabel4.font")); // NOI18N
        jLabel4.setForeground(resourceMap.getColor("jLabel4.foreground")); // NOI18N
        jLabel4.setText(resourceMap.getString("jLabel4.text")); // NOI18N
        jLabel4.setName("jLabel4"); // NOI18N

        jLabel5.setFont(resourceMap.getFont("jLabel8.font")); // NOI18N
        jLabel5.setForeground(resourceMap.getColor("jLabel8.foreground")); // NOI18N
        jLabel5.setText(resourceMap.getString("jLabel5.text")); // NOI18N
        jLabel5.setName("jLabel5"); // NOI18N

        jLabel6.setFont(resourceMap.getFont("jLabel8.font")); // NOI18N
        jLabel6.setForeground(resourceMap.getColor("jLabel8.foreground")); // NOI18N
        jLabel6.setText(resourceMap.getString("jLabel6.text")); // NOI18N
        jLabel6.setName("jLabel6"); // NOI18N

        jLabel7.setFont(resourceMap.getFont("jLabel8.font")); // NOI18N
        jLabel7.setForeground(resourceMap.getColor("jLabel8.foreground")); // NOI18N
        jLabel7.setText(resourceMap.getString("jLabel7.text")); // NOI18N
        jLabel7.setName("jLabel7"); // NOI18N

        jLabel8.setFont(resourceMap.getFont("jLabel8.font")); // NOI18N
        jLabel8.setForeground(resourceMap.getColor("jLabel8.foreground")); // NOI18N
        jLabel8.setText(resourceMap.getString("jLabel8.text")); // NOI18N
        jLabel8.setName("jLabel8"); // NOI18N

        jComboBox1.setBackground(resourceMap.getColor("jComboBox1.background")); // NOI18N
        jComboBox1.setFont(resourceMap.getFont("jComboBox1.font")); // NOI18N
        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19" }));
        jComboBox1.setName("jComboBox1"); // NOI18N

        jComboBox2.setBackground(resourceMap.getColor("jComboBox1.background")); // NOI18N
        jComboBox2.setFont(resourceMap.getFont("jComboBox1.font")); // NOI18N
        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19" }));
        jComboBox2.setName("jComboBox2"); // NOI18N

        jComboBox3.setBackground(resourceMap.getColor("jComboBox1.background")); // NOI18N
        jComboBox3.setFont(resourceMap.getFont("jComboBox1.font")); // NOI18N
        jComboBox3.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "10", "50", "100", "250", "500", "1000", "5000" }));
        jComboBox3.setName("jComboBox3"); // NOI18N

        jComboBox4.setBackground(resourceMap.getColor("jComboBox1.background")); // NOI18N
        jComboBox4.setFont(resourceMap.getFont("jComboBox1.font")); // NOI18N
        jComboBox4.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "70", "75", "80", "85", "90" }));
        jComboBox4.setName("jComboBox4"); // NOI18N

        jComboBox5.setBackground(resourceMap.getColor("jComboBox1.background")); // NOI18N
        jComboBox5.setFont(resourceMap.getFont("jComboBox1.font")); // NOI18N
        jComboBox5.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "1", "3", "5" }));
        jComboBox5.setName("jComboBox5"); // NOI18N

        jComboBox6.setBackground(resourceMap.getColor("jComboBox1.background")); // NOI18N
        jComboBox6.setFont(resourceMap.getFont("jComboBox1.font")); // NOI18N
        jComboBox6.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "10", "50", "100", "250", "500", "1000", "5000" }));
        jComboBox6.setName("jComboBox6"); // NOI18N

        jComboBox7.setBackground(resourceMap.getColor("jComboBox1.background")); // NOI18N
        jComboBox7.setFont(resourceMap.getFont("jComboBox1.font")); // NOI18N
        jComboBox7.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Rank", "Torneio", "Rank + Torneio" }));
        jComboBox7.setName("jComboBox7"); // NOI18N

        label1.setAlignment(java.awt.Label.CENTER);
        label1.setBackground(resourceMap.getColor("label1.background")); // NOI18N
        label1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        label1.setFont(resourceMap.getFont("label1.font")); // NOI18N
        label1.setName("label1"); // NOI18N
        label1.setText(resourceMap.getString("label1.text")); // NOI18N
        label1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                label1MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                label1MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                label1MouseExited(evt);
            }
        });

        label2.setAlignment(java.awt.Label.CENTER);
        label2.setBackground(resourceMap.getColor("label2.background")); // NOI18N
        label2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        label2.setFont(resourceMap.getFont("label2.font")); // NOI18N
        label2.setForeground(resourceMap.getColor("label2.foreground")); // NOI18N
        label2.setName("label2"); // NOI18N
        label2.setText(resourceMap.getString("label2.text")); // NOI18N
        label2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                label2MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                label2MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                label2MouseExited(evt);
            }
        });

        jPanel2.setBackground(resourceMap.getColor("jPanel2.background")); // NOI18N
        jPanel2.setName("jPanel2"); // NOI18N

        A1.setBackground(resourceMap.getColor("A1.background")); // NOI18N
        A1.setBorder(javax.swing.BorderFactory.createLineBorder(resourceMap.getColor("A1.border.lineColor"))); // NOI18N
        A1.setMaximumSize(new java.awt.Dimension(15, 15));
        A1.setMinimumSize(new java.awt.Dimension(15, 15));
        A1.setName("A1"); // NOI18N
        A1.setPreferredSize(new java.awt.Dimension(15, 15));

        javax.swing.GroupLayout A1Layout = new javax.swing.GroupLayout(A1);
        A1.setLayout(A1Layout);
        A1Layout.setHorizontalGroup(
            A1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );
        A1Layout.setVerticalGroup(
            A1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        A3.setBackground(resourceMap.getColor("A3.background")); // NOI18N
        A3.setBorder(javax.swing.BorderFactory.createLineBorder(resourceMap.getColor("A1.border.lineColor"))); // NOI18N
        A3.setMaximumSize(new java.awt.Dimension(15, 15));
        A3.setMinimumSize(new java.awt.Dimension(15, 15));
        A3.setName("A3"); // NOI18N
        A3.setPreferredSize(new java.awt.Dimension(15, 15));

        javax.swing.GroupLayout A3Layout = new javax.swing.GroupLayout(A3);
        A3.setLayout(A3Layout);
        A3Layout.setHorizontalGroup(
            A3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );
        A3Layout.setVerticalGroup(
            A3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        A2.setBackground(resourceMap.getColor("A2.background")); // NOI18N
        A2.setBorder(javax.swing.BorderFactory.createLineBorder(resourceMap.getColor("A1.border.lineColor"))); // NOI18N
        A2.setMaximumSize(new java.awt.Dimension(15, 15));
        A2.setMinimumSize(new java.awt.Dimension(15, 15));
        A2.setName("A2"); // NOI18N
        A2.setPreferredSize(new java.awt.Dimension(15, 15));

        javax.swing.GroupLayout A2Layout = new javax.swing.GroupLayout(A2);
        A2.setLayout(A2Layout);
        A2Layout.setHorizontalGroup(
            A2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );
        A2Layout.setVerticalGroup(
            A2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        A4.setBackground(resourceMap.getColor("A4.background")); // NOI18N
        A4.setBorder(javax.swing.BorderFactory.createLineBorder(resourceMap.getColor("A1.border.lineColor"))); // NOI18N
        A4.setMaximumSize(new java.awt.Dimension(15, 15));
        A4.setMinimumSize(new java.awt.Dimension(15, 15));
        A4.setName("A4"); // NOI18N
        A4.setPreferredSize(new java.awt.Dimension(15, 15));

        javax.swing.GroupLayout A4Layout = new javax.swing.GroupLayout(A4);
        A4.setLayout(A4Layout);
        A4Layout.setHorizontalGroup(
            A4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );
        A4Layout.setVerticalGroup(
            A4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        A5.setBackground(resourceMap.getColor("A5.background")); // NOI18N
        A5.setBorder(javax.swing.BorderFactory.createLineBorder(resourceMap.getColor("A1.border.lineColor"))); // NOI18N
        A5.setMaximumSize(new java.awt.Dimension(15, 15));
        A5.setMinimumSize(new java.awt.Dimension(15, 15));
        A5.setName("A5"); // NOI18N
        A5.setPreferredSize(new java.awt.Dimension(15, 15));

        javax.swing.GroupLayout A5Layout = new javax.swing.GroupLayout(A5);
        A5.setLayout(A5Layout);
        A5Layout.setHorizontalGroup(
            A5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );
        A5Layout.setVerticalGroup(
            A5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        A6.setBackground(resourceMap.getColor("A6.background")); // NOI18N
        A6.setBorder(javax.swing.BorderFactory.createLineBorder(resourceMap.getColor("A1.border.lineColor"))); // NOI18N
        A6.setMaximumSize(new java.awt.Dimension(15, 15));
        A6.setMinimumSize(new java.awt.Dimension(15, 15));
        A6.setName("A6"); // NOI18N
        A6.setPreferredSize(new java.awt.Dimension(15, 15));

        javax.swing.GroupLayout A6Layout = new javax.swing.GroupLayout(A6);
        A6.setLayout(A6Layout);
        A6Layout.setHorizontalGroup(
            A6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );
        A6Layout.setVerticalGroup(
            A6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        A7.setBackground(resourceMap.getColor("A7.background")); // NOI18N
        A7.setBorder(javax.swing.BorderFactory.createLineBorder(resourceMap.getColor("A1.border.lineColor"))); // NOI18N
        A7.setMaximumSize(new java.awt.Dimension(15, 15));
        A7.setMinimumSize(new java.awt.Dimension(15, 15));
        A7.setName("A7"); // NOI18N
        A7.setPreferredSize(new java.awt.Dimension(15, 15));

        javax.swing.GroupLayout A7Layout = new javax.swing.GroupLayout(A7);
        A7.setLayout(A7Layout);
        A7Layout.setHorizontalGroup(
            A7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );
        A7Layout.setVerticalGroup(
            A7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        A8.setBackground(resourceMap.getColor("A8.background")); // NOI18N
        A8.setBorder(javax.swing.BorderFactory.createLineBorder(resourceMap.getColor("A1.border.lineColor"))); // NOI18N
        A8.setMaximumSize(new java.awt.Dimension(15, 15));
        A8.setMinimumSize(new java.awt.Dimension(15, 15));
        A8.setName("A8"); // NOI18N
        A8.setPreferredSize(new java.awt.Dimension(15, 15));

        javax.swing.GroupLayout A8Layout = new javax.swing.GroupLayout(A8);
        A8.setLayout(A8Layout);
        A8Layout.setHorizontalGroup(
            A8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );
        A8Layout.setVerticalGroup(
            A8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        A9.setBackground(resourceMap.getColor("A9.background")); // NOI18N
        A9.setBorder(javax.swing.BorderFactory.createLineBorder(resourceMap.getColor("A1.border.lineColor"))); // NOI18N
        A9.setMaximumSize(new java.awt.Dimension(15, 15));
        A9.setMinimumSize(new java.awt.Dimension(15, 15));
        A9.setName("A9"); // NOI18N
        A9.setPreferredSize(new java.awt.Dimension(15, 15));

        javax.swing.GroupLayout A9Layout = new javax.swing.GroupLayout(A9);
        A9.setLayout(A9Layout);
        A9Layout.setHorizontalGroup(
            A9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );
        A9Layout.setVerticalGroup(
            A9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        A10.setBackground(resourceMap.getColor("A10.background")); // NOI18N
        A10.setBorder(javax.swing.BorderFactory.createLineBorder(resourceMap.getColor("A1.border.lineColor"))); // NOI18N
        A10.setMaximumSize(new java.awt.Dimension(15, 15));
        A10.setMinimumSize(new java.awt.Dimension(15, 15));
        A10.setName("A10"); // NOI18N
        A10.setPreferredSize(new java.awt.Dimension(15, 15));

        javax.swing.GroupLayout A10Layout = new javax.swing.GroupLayout(A10);
        A10.setLayout(A10Layout);
        A10Layout.setHorizontalGroup(
            A10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );
        A10Layout.setVerticalGroup(
            A10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        A11.setBackground(resourceMap.getColor("A11.background")); // NOI18N
        A11.setBorder(javax.swing.BorderFactory.createLineBorder(resourceMap.getColor("A1.border.lineColor"))); // NOI18N
        A11.setMaximumSize(new java.awt.Dimension(15, 15));
        A11.setMinimumSize(new java.awt.Dimension(15, 15));
        A11.setName("A11"); // NOI18N
        A11.setPreferredSize(new java.awt.Dimension(15, 15));

        javax.swing.GroupLayout A11Layout = new javax.swing.GroupLayout(A11);
        A11.setLayout(A11Layout);
        A11Layout.setHorizontalGroup(
            A11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );
        A11Layout.setVerticalGroup(
            A11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        A12.setBackground(resourceMap.getColor("A12.background")); // NOI18N
        A12.setBorder(javax.swing.BorderFactory.createLineBorder(resourceMap.getColor("A1.border.lineColor"))); // NOI18N
        A12.setMaximumSize(new java.awt.Dimension(15, 15));
        A12.setMinimumSize(new java.awt.Dimension(15, 15));
        A12.setName("A12"); // NOI18N
        A12.setPreferredSize(new java.awt.Dimension(15, 15));

        javax.swing.GroupLayout A12Layout = new javax.swing.GroupLayout(A12);
        A12.setLayout(A12Layout);
        A12Layout.setHorizontalGroup(
            A12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );
        A12Layout.setVerticalGroup(
            A12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        A13.setBackground(resourceMap.getColor("A13.background")); // NOI18N
        A13.setBorder(javax.swing.BorderFactory.createLineBorder(resourceMap.getColor("A1.border.lineColor"))); // NOI18N
        A13.setMaximumSize(new java.awt.Dimension(15, 15));
        A13.setMinimumSize(new java.awt.Dimension(15, 15));
        A13.setName("A13"); // NOI18N
        A13.setPreferredSize(new java.awt.Dimension(15, 15));

        javax.swing.GroupLayout A13Layout = new javax.swing.GroupLayout(A13);
        A13.setLayout(A13Layout);
        A13Layout.setHorizontalGroup(
            A13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );
        A13Layout.setVerticalGroup(
            A13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        A14.setBackground(resourceMap.getColor("A14.background")); // NOI18N
        A14.setBorder(javax.swing.BorderFactory.createLineBorder(resourceMap.getColor("A1.border.lineColor"))); // NOI18N
        A14.setMaximumSize(new java.awt.Dimension(15, 15));
        A14.setMinimumSize(new java.awt.Dimension(15, 15));
        A14.setName("A14"); // NOI18N
        A14.setPreferredSize(new java.awt.Dimension(15, 15));

        javax.swing.GroupLayout A14Layout = new javax.swing.GroupLayout(A14);
        A14.setLayout(A14Layout);
        A14Layout.setHorizontalGroup(
            A14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );
        A14Layout.setVerticalGroup(
            A14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        A15.setBackground(resourceMap.getColor("A15.background")); // NOI18N
        A15.setBorder(javax.swing.BorderFactory.createLineBorder(resourceMap.getColor("A1.border.lineColor"))); // NOI18N
        A15.setMaximumSize(new java.awt.Dimension(15, 15));
        A15.setMinimumSize(new java.awt.Dimension(15, 15));
        A15.setName("A15"); // NOI18N
        A15.setPreferredSize(new java.awt.Dimension(15, 15));

        javax.swing.GroupLayout A15Layout = new javax.swing.GroupLayout(A15);
        A15.setLayout(A15Layout);
        A15Layout.setHorizontalGroup(
            A15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );
        A15Layout.setVerticalGroup(
            A15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        A16.setBackground(resourceMap.getColor("A16.background")); // NOI18N
        A16.setBorder(javax.swing.BorderFactory.createLineBorder(resourceMap.getColor("A1.border.lineColor"))); // NOI18N
        A16.setMaximumSize(new java.awt.Dimension(15, 15));
        A16.setMinimumSize(new java.awt.Dimension(15, 15));
        A16.setName("A16"); // NOI18N
        A16.setPreferredSize(new java.awt.Dimension(15, 15));

        javax.swing.GroupLayout A16Layout = new javax.swing.GroupLayout(A16);
        A16.setLayout(A16Layout);
        A16Layout.setHorizontalGroup(
            A16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );
        A16Layout.setVerticalGroup(
            A16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        A17.setBackground(resourceMap.getColor("A17.background")); // NOI18N
        A17.setBorder(javax.swing.BorderFactory.createLineBorder(resourceMap.getColor("A1.border.lineColor"))); // NOI18N
        A17.setMaximumSize(new java.awt.Dimension(15, 15));
        A17.setMinimumSize(new java.awt.Dimension(15, 15));
        A17.setName("A17"); // NOI18N
        A17.setPreferredSize(new java.awt.Dimension(15, 15));

        javax.swing.GroupLayout A17Layout = new javax.swing.GroupLayout(A17);
        A17.setLayout(A17Layout);
        A17Layout.setHorizontalGroup(
            A17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );
        A17Layout.setVerticalGroup(
            A17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        A18.setBackground(resourceMap.getColor("A18.background")); // NOI18N
        A18.setBorder(javax.swing.BorderFactory.createLineBorder(resourceMap.getColor("A1.border.lineColor"))); // NOI18N
        A18.setMaximumSize(new java.awt.Dimension(15, 15));
        A18.setMinimumSize(new java.awt.Dimension(15, 15));
        A18.setName("A18"); // NOI18N
        A18.setPreferredSize(new java.awt.Dimension(15, 15));

        javax.swing.GroupLayout A18Layout = new javax.swing.GroupLayout(A18);
        A18.setLayout(A18Layout);
        A18Layout.setHorizontalGroup(
            A18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );
        A18Layout.setVerticalGroup(
            A18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        A19.setBackground(resourceMap.getColor("A19.background")); // NOI18N
        A19.setBorder(javax.swing.BorderFactory.createLineBorder(resourceMap.getColor("A1.border.lineColor"))); // NOI18N
        A19.setMaximumSize(new java.awt.Dimension(15, 15));
        A19.setMinimumSize(new java.awt.Dimension(15, 15));
        A19.setName("A19"); // NOI18N
        A19.setPreferredSize(new java.awt.Dimension(15, 15));

        javax.swing.GroupLayout A19Layout = new javax.swing.GroupLayout(A19);
        A19.setLayout(A19Layout);
        A19Layout.setHorizontalGroup(
            A19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );
        A19Layout.setVerticalGroup(
            A19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        A20.setBackground(resourceMap.getColor("A20.background")); // NOI18N
        A20.setBorder(javax.swing.BorderFactory.createLineBorder(resourceMap.getColor("A1.border.lineColor"))); // NOI18N
        A20.setMaximumSize(new java.awt.Dimension(15, 15));
        A20.setMinimumSize(new java.awt.Dimension(15, 15));
        A20.setName("A20"); // NOI18N
        A20.setPreferredSize(new java.awt.Dimension(15, 15));

        javax.swing.GroupLayout A20Layout = new javax.swing.GroupLayout(A20);
        A20.setLayout(A20Layout);
        A20Layout.setHorizontalGroup(
            A20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );
        A20Layout.setVerticalGroup(
            A20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        B1.setBackground(resourceMap.getColor("B1.background")); // NOI18N
        B1.setBorder(javax.swing.BorderFactory.createLineBorder(resourceMap.getColor("B1.border.lineColor"))); // NOI18N
        B1.setMaximumSize(new java.awt.Dimension(15, 15));
        B1.setMinimumSize(new java.awt.Dimension(15, 15));
        B1.setName("B1"); // NOI18N

        javax.swing.GroupLayout B1Layout = new javax.swing.GroupLayout(B1);
        B1.setLayout(B1Layout);
        B1Layout.setHorizontalGroup(
            B1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );
        B1Layout.setVerticalGroup(
            B1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        B2.setBackground(resourceMap.getColor("B2.background")); // NOI18N
        B2.setBorder(javax.swing.BorderFactory.createLineBorder(resourceMap.getColor("B2.border.lineColor"))); // NOI18N
        B2.setMaximumSize(new java.awt.Dimension(15, 15));
        B2.setMinimumSize(new java.awt.Dimension(15, 15));
        B2.setName("B2"); // NOI18N

        javax.swing.GroupLayout B2Layout = new javax.swing.GroupLayout(B2);
        B2.setLayout(B2Layout);
        B2Layout.setHorizontalGroup(
            B2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );
        B2Layout.setVerticalGroup(
            B2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        B3.setBackground(resourceMap.getColor("B3.background")); // NOI18N
        B3.setBorder(javax.swing.BorderFactory.createLineBorder(resourceMap.getColor("B3.border.lineColor"))); // NOI18N
        B3.setMaximumSize(new java.awt.Dimension(15, 15));
        B3.setMinimumSize(new java.awt.Dimension(15, 15));
        B3.setName("B3"); // NOI18N

        javax.swing.GroupLayout B3Layout = new javax.swing.GroupLayout(B3);
        B3.setLayout(B3Layout);
        B3Layout.setHorizontalGroup(
            B3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );
        B3Layout.setVerticalGroup(
            B3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        B4.setBackground(resourceMap.getColor("B4.background")); // NOI18N
        B4.setBorder(javax.swing.BorderFactory.createLineBorder(resourceMap.getColor("B4.border.lineColor"))); // NOI18N
        B4.setMaximumSize(new java.awt.Dimension(15, 15));
        B4.setMinimumSize(new java.awt.Dimension(15, 15));
        B4.setName("B4"); // NOI18N

        javax.swing.GroupLayout B4Layout = new javax.swing.GroupLayout(B4);
        B4.setLayout(B4Layout);
        B4Layout.setHorizontalGroup(
            B4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );
        B4Layout.setVerticalGroup(
            B4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        B5.setBackground(resourceMap.getColor("B5.background")); // NOI18N
        B5.setBorder(javax.swing.BorderFactory.createLineBorder(resourceMap.getColor("B5.border.lineColor"))); // NOI18N
        B5.setMaximumSize(new java.awt.Dimension(15, 15));
        B5.setMinimumSize(new java.awt.Dimension(15, 15));
        B5.setName("B5"); // NOI18N

        javax.swing.GroupLayout B5Layout = new javax.swing.GroupLayout(B5);
        B5.setLayout(B5Layout);
        B5Layout.setHorizontalGroup(
            B5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );
        B5Layout.setVerticalGroup(
            B5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        B6.setBackground(resourceMap.getColor("B6.background")); // NOI18N
        B6.setBorder(javax.swing.BorderFactory.createLineBorder(resourceMap.getColor("B6.border.lineColor"))); // NOI18N
        B6.setMaximumSize(new java.awt.Dimension(15, 15));
        B6.setMinimumSize(new java.awt.Dimension(15, 15));
        B6.setName("B6"); // NOI18N

        javax.swing.GroupLayout B6Layout = new javax.swing.GroupLayout(B6);
        B6.setLayout(B6Layout);
        B6Layout.setHorizontalGroup(
            B6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );
        B6Layout.setVerticalGroup(
            B6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        B7.setBackground(resourceMap.getColor("B7.background")); // NOI18N
        B7.setBorder(javax.swing.BorderFactory.createLineBorder(resourceMap.getColor("B7.border.lineColor"))); // NOI18N
        B7.setMaximumSize(new java.awt.Dimension(15, 15));
        B7.setMinimumSize(new java.awt.Dimension(15, 15));
        B7.setName("B7"); // NOI18N

        javax.swing.GroupLayout B7Layout = new javax.swing.GroupLayout(B7);
        B7.setLayout(B7Layout);
        B7Layout.setHorizontalGroup(
            B7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );
        B7Layout.setVerticalGroup(
            B7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        B8.setBackground(resourceMap.getColor("B8.background")); // NOI18N
        B8.setBorder(javax.swing.BorderFactory.createLineBorder(resourceMap.getColor("B8.border.lineColor"))); // NOI18N
        B8.setMaximumSize(new java.awt.Dimension(15, 15));
        B8.setMinimumSize(new java.awt.Dimension(15, 15));
        B8.setName("B8"); // NOI18N

        javax.swing.GroupLayout B8Layout = new javax.swing.GroupLayout(B8);
        B8.setLayout(B8Layout);
        B8Layout.setHorizontalGroup(
            B8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );
        B8Layout.setVerticalGroup(
            B8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        B9.setBackground(resourceMap.getColor("B9.background")); // NOI18N
        B9.setBorder(javax.swing.BorderFactory.createLineBorder(resourceMap.getColor("B9.border.lineColor"))); // NOI18N
        B9.setMaximumSize(new java.awt.Dimension(15, 15));
        B9.setMinimumSize(new java.awt.Dimension(15, 15));
        B9.setName("B9"); // NOI18N

        javax.swing.GroupLayout B9Layout = new javax.swing.GroupLayout(B9);
        B9.setLayout(B9Layout);
        B9Layout.setHorizontalGroup(
            B9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );
        B9Layout.setVerticalGroup(
            B9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        B10.setBackground(resourceMap.getColor("B10.background")); // NOI18N
        B10.setBorder(javax.swing.BorderFactory.createLineBorder(resourceMap.getColor("B10.border.lineColor"))); // NOI18N
        B10.setMaximumSize(new java.awt.Dimension(15, 15));
        B10.setMinimumSize(new java.awt.Dimension(15, 15));
        B10.setName("B10"); // NOI18N

        javax.swing.GroupLayout B10Layout = new javax.swing.GroupLayout(B10);
        B10.setLayout(B10Layout);
        B10Layout.setHorizontalGroup(
            B10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );
        B10Layout.setVerticalGroup(
            B10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        B11.setBackground(resourceMap.getColor("B11.background")); // NOI18N
        B11.setBorder(javax.swing.BorderFactory.createLineBorder(resourceMap.getColor("B11.border.lineColor"))); // NOI18N
        B11.setMaximumSize(new java.awt.Dimension(15, 15));
        B11.setMinimumSize(new java.awt.Dimension(15, 15));
        B11.setName("B11"); // NOI18N

        javax.swing.GroupLayout B11Layout = new javax.swing.GroupLayout(B11);
        B11.setLayout(B11Layout);
        B11Layout.setHorizontalGroup(
            B11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );
        B11Layout.setVerticalGroup(
            B11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        B12.setBackground(resourceMap.getColor("B12.background")); // NOI18N
        B12.setBorder(javax.swing.BorderFactory.createLineBorder(resourceMap.getColor("B12.border.lineColor"))); // NOI18N
        B12.setMaximumSize(new java.awt.Dimension(15, 15));
        B12.setMinimumSize(new java.awt.Dimension(15, 15));
        B12.setName("B12"); // NOI18N

        javax.swing.GroupLayout B12Layout = new javax.swing.GroupLayout(B12);
        B12.setLayout(B12Layout);
        B12Layout.setHorizontalGroup(
            B12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );
        B12Layout.setVerticalGroup(
            B12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        B13.setBackground(resourceMap.getColor("B13.background")); // NOI18N
        B13.setBorder(javax.swing.BorderFactory.createLineBorder(resourceMap.getColor("B13.border.lineColor"))); // NOI18N
        B13.setMaximumSize(new java.awt.Dimension(15, 15));
        B13.setMinimumSize(new java.awt.Dimension(15, 15));
        B13.setName("B13"); // NOI18N

        javax.swing.GroupLayout B13Layout = new javax.swing.GroupLayout(B13);
        B13.setLayout(B13Layout);
        B13Layout.setHorizontalGroup(
            B13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );
        B13Layout.setVerticalGroup(
            B13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        B14.setBackground(resourceMap.getColor("B14.background")); // NOI18N
        B14.setBorder(javax.swing.BorderFactory.createLineBorder(resourceMap.getColor("B14.border.lineColor"))); // NOI18N
        B14.setMaximumSize(new java.awt.Dimension(15, 15));
        B14.setMinimumSize(new java.awt.Dimension(15, 15));
        B14.setName("B14"); // NOI18N

        javax.swing.GroupLayout B14Layout = new javax.swing.GroupLayout(B14);
        B14.setLayout(B14Layout);
        B14Layout.setHorizontalGroup(
            B14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );
        B14Layout.setVerticalGroup(
            B14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        B15.setBackground(resourceMap.getColor("B15.background")); // NOI18N
        B15.setBorder(javax.swing.BorderFactory.createLineBorder(resourceMap.getColor("B15.border.lineColor"))); // NOI18N
        B15.setMaximumSize(new java.awt.Dimension(15, 15));
        B15.setMinimumSize(new java.awt.Dimension(15, 15));
        B15.setName("B15"); // NOI18N

        javax.swing.GroupLayout B15Layout = new javax.swing.GroupLayout(B15);
        B15.setLayout(B15Layout);
        B15Layout.setHorizontalGroup(
            B15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );
        B15Layout.setVerticalGroup(
            B15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        B16.setBackground(resourceMap.getColor("B16.background")); // NOI18N
        B16.setBorder(javax.swing.BorderFactory.createLineBorder(resourceMap.getColor("B16.border.lineColor"))); // NOI18N
        B16.setMaximumSize(new java.awt.Dimension(15, 15));
        B16.setMinimumSize(new java.awt.Dimension(15, 15));
        B16.setName("B16"); // NOI18N

        javax.swing.GroupLayout B16Layout = new javax.swing.GroupLayout(B16);
        B16.setLayout(B16Layout);
        B16Layout.setHorizontalGroup(
            B16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );
        B16Layout.setVerticalGroup(
            B16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        B17.setBackground(resourceMap.getColor("B17.background")); // NOI18N
        B17.setBorder(javax.swing.BorderFactory.createLineBorder(resourceMap.getColor("B17.border.lineColor"))); // NOI18N
        B17.setMaximumSize(new java.awt.Dimension(15, 15));
        B17.setMinimumSize(new java.awt.Dimension(15, 15));
        B17.setName("B17"); // NOI18N

        javax.swing.GroupLayout B17Layout = new javax.swing.GroupLayout(B17);
        B17.setLayout(B17Layout);
        B17Layout.setHorizontalGroup(
            B17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );
        B17Layout.setVerticalGroup(
            B17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        B18.setBackground(resourceMap.getColor("B18.background")); // NOI18N
        B18.setBorder(javax.swing.BorderFactory.createLineBorder(resourceMap.getColor("B18.border.lineColor"))); // NOI18N
        B18.setMaximumSize(new java.awt.Dimension(15, 15));
        B18.setMinimumSize(new java.awt.Dimension(15, 15));
        B18.setName("B18"); // NOI18N

        javax.swing.GroupLayout B18Layout = new javax.swing.GroupLayout(B18);
        B18.setLayout(B18Layout);
        B18Layout.setHorizontalGroup(
            B18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );
        B18Layout.setVerticalGroup(
            B18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        B19.setBackground(resourceMap.getColor("B19.background")); // NOI18N
        B19.setBorder(javax.swing.BorderFactory.createLineBorder(resourceMap.getColor("B19.border.lineColor"))); // NOI18N
        B19.setMaximumSize(new java.awt.Dimension(15, 15));
        B19.setMinimumSize(new java.awt.Dimension(15, 15));
        B19.setName("B19"); // NOI18N

        javax.swing.GroupLayout B19Layout = new javax.swing.GroupLayout(B19);
        B19.setLayout(B19Layout);
        B19Layout.setHorizontalGroup(
            B19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );
        B19Layout.setVerticalGroup(
            B19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        B20.setBackground(resourceMap.getColor("B20.background")); // NOI18N
        B20.setBorder(javax.swing.BorderFactory.createLineBorder(resourceMap.getColor("B20.border.lineColor"))); // NOI18N
        B20.setMaximumSize(new java.awt.Dimension(15, 15));
        B20.setMinimumSize(new java.awt.Dimension(15, 15));
        B20.setName("B20"); // NOI18N

        javax.swing.GroupLayout B20Layout = new javax.swing.GroupLayout(B20);
        B20.setLayout(B20Layout);
        B20Layout.setHorizontalGroup(
            B20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );
        B20Layout.setVerticalGroup(
            B20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        C1.setBackground(resourceMap.getColor("C1.background")); // NOI18N
        C1.setBorder(javax.swing.BorderFactory.createLineBorder(resourceMap.getColor("C1.border.lineColor"))); // NOI18N
        C1.setMaximumSize(new java.awt.Dimension(15, 15));
        C1.setMinimumSize(new java.awt.Dimension(15, 15));
        C1.setName("C1"); // NOI18N

        javax.swing.GroupLayout C1Layout = new javax.swing.GroupLayout(C1);
        C1.setLayout(C1Layout);
        C1Layout.setHorizontalGroup(
            C1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );
        C1Layout.setVerticalGroup(
            C1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        C2.setBackground(resourceMap.getColor("C2.background")); // NOI18N
        C2.setBorder(javax.swing.BorderFactory.createLineBorder(resourceMap.getColor("C2.border.lineColor"))); // NOI18N
        C2.setMaximumSize(new java.awt.Dimension(15, 15));
        C2.setMinimumSize(new java.awt.Dimension(15, 15));
        C2.setName("C2"); // NOI18N

        javax.swing.GroupLayout C2Layout = new javax.swing.GroupLayout(C2);
        C2.setLayout(C2Layout);
        C2Layout.setHorizontalGroup(
            C2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );
        C2Layout.setVerticalGroup(
            C2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        C3.setBackground(resourceMap.getColor("C3.background")); // NOI18N
        C3.setBorder(javax.swing.BorderFactory.createLineBorder(resourceMap.getColor("C3.border.lineColor"))); // NOI18N
        C3.setMaximumSize(new java.awt.Dimension(15, 15));
        C3.setMinimumSize(new java.awt.Dimension(15, 15));
        C3.setName("C3"); // NOI18N

        javax.swing.GroupLayout C3Layout = new javax.swing.GroupLayout(C3);
        C3.setLayout(C3Layout);
        C3Layout.setHorizontalGroup(
            C3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );
        C3Layout.setVerticalGroup(
            C3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        C4.setBackground(resourceMap.getColor("C4.background")); // NOI18N
        C4.setBorder(javax.swing.BorderFactory.createLineBorder(resourceMap.getColor("C4.border.lineColor"))); // NOI18N
        C4.setMaximumSize(new java.awt.Dimension(15, 15));
        C4.setMinimumSize(new java.awt.Dimension(15, 15));
        C4.setName("C4"); // NOI18N

        javax.swing.GroupLayout C4Layout = new javax.swing.GroupLayout(C4);
        C4.setLayout(C4Layout);
        C4Layout.setHorizontalGroup(
            C4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );
        C4Layout.setVerticalGroup(
            C4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        C5.setBackground(resourceMap.getColor("C5.background")); // NOI18N
        C5.setBorder(javax.swing.BorderFactory.createLineBorder(resourceMap.getColor("C5.border.lineColor"))); // NOI18N
        C5.setMaximumSize(new java.awt.Dimension(15, 15));
        C5.setMinimumSize(new java.awt.Dimension(15, 15));
        C5.setName("C5"); // NOI18N

        javax.swing.GroupLayout C5Layout = new javax.swing.GroupLayout(C5);
        C5.setLayout(C5Layout);
        C5Layout.setHorizontalGroup(
            C5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );
        C5Layout.setVerticalGroup(
            C5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        C6.setBackground(resourceMap.getColor("C6.background")); // NOI18N
        C6.setBorder(javax.swing.BorderFactory.createLineBorder(resourceMap.getColor("C6.border.lineColor"))); // NOI18N
        C6.setMaximumSize(new java.awt.Dimension(15, 15));
        C6.setMinimumSize(new java.awt.Dimension(15, 15));
        C6.setName("C6"); // NOI18N

        javax.swing.GroupLayout C6Layout = new javax.swing.GroupLayout(C6);
        C6.setLayout(C6Layout);
        C6Layout.setHorizontalGroup(
            C6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );
        C6Layout.setVerticalGroup(
            C6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        C7.setBackground(resourceMap.getColor("C7.background")); // NOI18N
        C7.setBorder(javax.swing.BorderFactory.createLineBorder(resourceMap.getColor("C7.border.lineColor"))); // NOI18N
        C7.setMaximumSize(new java.awt.Dimension(15, 15));
        C7.setMinimumSize(new java.awt.Dimension(15, 15));
        C7.setName("C7"); // NOI18N

        javax.swing.GroupLayout C7Layout = new javax.swing.GroupLayout(C7);
        C7.setLayout(C7Layout);
        C7Layout.setHorizontalGroup(
            C7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );
        C7Layout.setVerticalGroup(
            C7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        C8.setBackground(resourceMap.getColor("C8.background")); // NOI18N
        C8.setBorder(javax.swing.BorderFactory.createLineBorder(resourceMap.getColor("C8.border.lineColor"))); // NOI18N
        C8.setMaximumSize(new java.awt.Dimension(15, 15));
        C8.setMinimumSize(new java.awt.Dimension(15, 15));
        C8.setName("C8"); // NOI18N

        javax.swing.GroupLayout C8Layout = new javax.swing.GroupLayout(C8);
        C8.setLayout(C8Layout);
        C8Layout.setHorizontalGroup(
            C8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );
        C8Layout.setVerticalGroup(
            C8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        C9.setBackground(resourceMap.getColor("C9.background")); // NOI18N
        C9.setBorder(javax.swing.BorderFactory.createLineBorder(resourceMap.getColor("C9.border.lineColor"))); // NOI18N
        C9.setMaximumSize(new java.awt.Dimension(15, 15));
        C9.setMinimumSize(new java.awt.Dimension(15, 15));
        C9.setName("C9"); // NOI18N

        javax.swing.GroupLayout C9Layout = new javax.swing.GroupLayout(C9);
        C9.setLayout(C9Layout);
        C9Layout.setHorizontalGroup(
            C9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );
        C9Layout.setVerticalGroup(
            C9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        C10.setBackground(resourceMap.getColor("C10.background")); // NOI18N
        C10.setBorder(javax.swing.BorderFactory.createLineBorder(resourceMap.getColor("C10.border.lineColor"))); // NOI18N
        C10.setMaximumSize(new java.awt.Dimension(15, 15));
        C10.setMinimumSize(new java.awt.Dimension(15, 15));
        C10.setName("C10"); // NOI18N

        javax.swing.GroupLayout C10Layout = new javax.swing.GroupLayout(C10);
        C10.setLayout(C10Layout);
        C10Layout.setHorizontalGroup(
            C10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );
        C10Layout.setVerticalGroup(
            C10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        C11.setBackground(resourceMap.getColor("C11.background")); // NOI18N
        C11.setBorder(javax.swing.BorderFactory.createLineBorder(resourceMap.getColor("C11.border.lineColor"))); // NOI18N
        C11.setMaximumSize(new java.awt.Dimension(15, 15));
        C11.setMinimumSize(new java.awt.Dimension(15, 15));
        C11.setName("C11"); // NOI18N

        javax.swing.GroupLayout C11Layout = new javax.swing.GroupLayout(C11);
        C11.setLayout(C11Layout);
        C11Layout.setHorizontalGroup(
            C11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );
        C11Layout.setVerticalGroup(
            C11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        C12.setBackground(resourceMap.getColor("C12.background")); // NOI18N
        C12.setBorder(javax.swing.BorderFactory.createLineBorder(resourceMap.getColor("C12.border.lineColor"))); // NOI18N
        C12.setMaximumSize(new java.awt.Dimension(15, 15));
        C12.setMinimumSize(new java.awt.Dimension(15, 15));
        C12.setName("C12"); // NOI18N

        javax.swing.GroupLayout C12Layout = new javax.swing.GroupLayout(C12);
        C12.setLayout(C12Layout);
        C12Layout.setHorizontalGroup(
            C12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );
        C12Layout.setVerticalGroup(
            C12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        C13.setBackground(resourceMap.getColor("C13.background")); // NOI18N
        C13.setBorder(javax.swing.BorderFactory.createLineBorder(resourceMap.getColor("C13.border.lineColor"))); // NOI18N
        C13.setMaximumSize(new java.awt.Dimension(15, 15));
        C13.setMinimumSize(new java.awt.Dimension(15, 15));
        C13.setName("C13"); // NOI18N

        javax.swing.GroupLayout C13Layout = new javax.swing.GroupLayout(C13);
        C13.setLayout(C13Layout);
        C13Layout.setHorizontalGroup(
            C13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );
        C13Layout.setVerticalGroup(
            C13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        C14.setBackground(resourceMap.getColor("C14.background")); // NOI18N
        C14.setBorder(javax.swing.BorderFactory.createLineBorder(resourceMap.getColor("C14.border.lineColor"))); // NOI18N
        C14.setMaximumSize(new java.awt.Dimension(15, 15));
        C14.setMinimumSize(new java.awt.Dimension(15, 15));
        C14.setName("C14"); // NOI18N

        javax.swing.GroupLayout C14Layout = new javax.swing.GroupLayout(C14);
        C14.setLayout(C14Layout);
        C14Layout.setHorizontalGroup(
            C14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );
        C14Layout.setVerticalGroup(
            C14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        C15.setBackground(resourceMap.getColor("C15.background")); // NOI18N
        C15.setBorder(javax.swing.BorderFactory.createLineBorder(resourceMap.getColor("C15.border.lineColor"))); // NOI18N
        C15.setMaximumSize(new java.awt.Dimension(15, 15));
        C15.setMinimumSize(new java.awt.Dimension(15, 15));
        C15.setName("C15"); // NOI18N

        javax.swing.GroupLayout C15Layout = new javax.swing.GroupLayout(C15);
        C15.setLayout(C15Layout);
        C15Layout.setHorizontalGroup(
            C15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );
        C15Layout.setVerticalGroup(
            C15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        C16.setBackground(resourceMap.getColor("C16.background")); // NOI18N
        C16.setBorder(javax.swing.BorderFactory.createLineBorder(resourceMap.getColor("C16.border.lineColor"))); // NOI18N
        C16.setMaximumSize(new java.awt.Dimension(15, 15));
        C16.setMinimumSize(new java.awt.Dimension(15, 15));
        C16.setName("C16"); // NOI18N

        javax.swing.GroupLayout C16Layout = new javax.swing.GroupLayout(C16);
        C16.setLayout(C16Layout);
        C16Layout.setHorizontalGroup(
            C16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );
        C16Layout.setVerticalGroup(
            C16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        C17.setBackground(resourceMap.getColor("C17.background")); // NOI18N
        C17.setBorder(javax.swing.BorderFactory.createLineBorder(resourceMap.getColor("C17.border.lineColor"))); // NOI18N
        C17.setMaximumSize(new java.awt.Dimension(15, 15));
        C17.setMinimumSize(new java.awt.Dimension(15, 15));
        C17.setName("C17"); // NOI18N

        javax.swing.GroupLayout C17Layout = new javax.swing.GroupLayout(C17);
        C17.setLayout(C17Layout);
        C17Layout.setHorizontalGroup(
            C17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );
        C17Layout.setVerticalGroup(
            C17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        C18.setBackground(resourceMap.getColor("C18.background")); // NOI18N
        C18.setBorder(javax.swing.BorderFactory.createLineBorder(resourceMap.getColor("C18.border.lineColor"))); // NOI18N
        C18.setMaximumSize(new java.awt.Dimension(15, 15));
        C18.setMinimumSize(new java.awt.Dimension(15, 15));
        C18.setName("C18"); // NOI18N

        javax.swing.GroupLayout C18Layout = new javax.swing.GroupLayout(C18);
        C18.setLayout(C18Layout);
        C18Layout.setHorizontalGroup(
            C18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );
        C18Layout.setVerticalGroup(
            C18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        C19.setBackground(resourceMap.getColor("C19.background")); // NOI18N
        C19.setBorder(javax.swing.BorderFactory.createLineBorder(resourceMap.getColor("C19.border.lineColor"))); // NOI18N
        C19.setMaximumSize(new java.awt.Dimension(15, 15));
        C19.setMinimumSize(new java.awt.Dimension(15, 15));
        C19.setName("C19"); // NOI18N

        javax.swing.GroupLayout C19Layout = new javax.swing.GroupLayout(C19);
        C19.setLayout(C19Layout);
        C19Layout.setHorizontalGroup(
            C19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );
        C19Layout.setVerticalGroup(
            C19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        C20.setBackground(resourceMap.getColor("C20.background")); // NOI18N
        C20.setBorder(javax.swing.BorderFactory.createLineBorder(resourceMap.getColor("C20.border.lineColor"))); // NOI18N
        C20.setMaximumSize(new java.awt.Dimension(15, 15));
        C20.setMinimumSize(new java.awt.Dimension(15, 15));
        C20.setName("C20"); // NOI18N

        javax.swing.GroupLayout C20Layout = new javax.swing.GroupLayout(C20);
        C20.setLayout(C20Layout);
        C20Layout.setHorizontalGroup(
            C20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );
        C20Layout.setVerticalGroup(
            C20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        D1.setBackground(resourceMap.getColor("D1.background")); // NOI18N
        D1.setBorder(javax.swing.BorderFactory.createLineBorder(resourceMap.getColor("D1.border.lineColor"))); // NOI18N
        D1.setMaximumSize(new java.awt.Dimension(15, 15));
        D1.setMinimumSize(new java.awt.Dimension(15, 15));
        D1.setName("D1"); // NOI18N

        javax.swing.GroupLayout D1Layout = new javax.swing.GroupLayout(D1);
        D1.setLayout(D1Layout);
        D1Layout.setHorizontalGroup(
            D1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );
        D1Layout.setVerticalGroup(
            D1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        D3.setBackground(resourceMap.getColor("D3.background")); // NOI18N
        D3.setBorder(javax.swing.BorderFactory.createLineBorder(resourceMap.getColor("D3.border.lineColor"))); // NOI18N
        D3.setMaximumSize(new java.awt.Dimension(15, 15));
        D3.setMinimumSize(new java.awt.Dimension(15, 15));
        D3.setName("D3"); // NOI18N

        javax.swing.GroupLayout D3Layout = new javax.swing.GroupLayout(D3);
        D3.setLayout(D3Layout);
        D3Layout.setHorizontalGroup(
            D3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );
        D3Layout.setVerticalGroup(
            D3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        D2.setBackground(resourceMap.getColor("D2.background")); // NOI18N
        D2.setBorder(javax.swing.BorderFactory.createLineBorder(resourceMap.getColor("D2.border.lineColor"))); // NOI18N
        D2.setMaximumSize(new java.awt.Dimension(15, 15));
        D2.setMinimumSize(new java.awt.Dimension(15, 15));
        D2.setName("D2"); // NOI18N

        javax.swing.GroupLayout D2Layout = new javax.swing.GroupLayout(D2);
        D2.setLayout(D2Layout);
        D2Layout.setHorizontalGroup(
            D2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );
        D2Layout.setVerticalGroup(
            D2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        D4.setBackground(resourceMap.getColor("D4.background")); // NOI18N
        D4.setBorder(javax.swing.BorderFactory.createLineBorder(resourceMap.getColor("D4.border.lineColor"))); // NOI18N
        D4.setMaximumSize(new java.awt.Dimension(15, 15));
        D4.setMinimumSize(new java.awt.Dimension(15, 15));
        D4.setName("D4"); // NOI18N

        javax.swing.GroupLayout D4Layout = new javax.swing.GroupLayout(D4);
        D4.setLayout(D4Layout);
        D4Layout.setHorizontalGroup(
            D4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );
        D4Layout.setVerticalGroup(
            D4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        D5.setBackground(resourceMap.getColor("D5.background")); // NOI18N
        D5.setBorder(javax.swing.BorderFactory.createLineBorder(resourceMap.getColor("D5.border.lineColor"))); // NOI18N
        D5.setMaximumSize(new java.awt.Dimension(15, 15));
        D5.setMinimumSize(new java.awt.Dimension(15, 15));
        D5.setName("D5"); // NOI18N

        javax.swing.GroupLayout D5Layout = new javax.swing.GroupLayout(D5);
        D5.setLayout(D5Layout);
        D5Layout.setHorizontalGroup(
            D5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );
        D5Layout.setVerticalGroup(
            D5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        D6.setBackground(resourceMap.getColor("D6.background")); // NOI18N
        D6.setBorder(javax.swing.BorderFactory.createLineBorder(resourceMap.getColor("D6.border.lineColor"))); // NOI18N
        D6.setMaximumSize(new java.awt.Dimension(15, 15));
        D6.setMinimumSize(new java.awt.Dimension(15, 15));
        D6.setName("D6"); // NOI18N

        javax.swing.GroupLayout D6Layout = new javax.swing.GroupLayout(D6);
        D6.setLayout(D6Layout);
        D6Layout.setHorizontalGroup(
            D6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );
        D6Layout.setVerticalGroup(
            D6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        D7.setBackground(resourceMap.getColor("D7.background")); // NOI18N
        D7.setBorder(javax.swing.BorderFactory.createLineBorder(resourceMap.getColor("D7.border.lineColor"))); // NOI18N
        D7.setMaximumSize(new java.awt.Dimension(15, 15));
        D7.setMinimumSize(new java.awt.Dimension(15, 15));
        D7.setName("D7"); // NOI18N

        javax.swing.GroupLayout D7Layout = new javax.swing.GroupLayout(D7);
        D7.setLayout(D7Layout);
        D7Layout.setHorizontalGroup(
            D7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );
        D7Layout.setVerticalGroup(
            D7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        D8.setBackground(resourceMap.getColor("D8.background")); // NOI18N
        D8.setBorder(javax.swing.BorderFactory.createLineBorder(resourceMap.getColor("D8.border.lineColor"))); // NOI18N
        D8.setMaximumSize(new java.awt.Dimension(15, 15));
        D8.setMinimumSize(new java.awt.Dimension(15, 15));
        D8.setName("D8"); // NOI18N

        javax.swing.GroupLayout D8Layout = new javax.swing.GroupLayout(D8);
        D8.setLayout(D8Layout);
        D8Layout.setHorizontalGroup(
            D8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );
        D8Layout.setVerticalGroup(
            D8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        D9.setBackground(resourceMap.getColor("D9.background")); // NOI18N
        D9.setBorder(javax.swing.BorderFactory.createLineBorder(resourceMap.getColor("D9.border.lineColor"))); // NOI18N
        D9.setMaximumSize(new java.awt.Dimension(15, 15));
        D9.setMinimumSize(new java.awt.Dimension(15, 15));
        D9.setName("D9"); // NOI18N

        javax.swing.GroupLayout D9Layout = new javax.swing.GroupLayout(D9);
        D9.setLayout(D9Layout);
        D9Layout.setHorizontalGroup(
            D9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );
        D9Layout.setVerticalGroup(
            D9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        D10.setBackground(resourceMap.getColor("D10.background")); // NOI18N
        D10.setBorder(javax.swing.BorderFactory.createLineBorder(resourceMap.getColor("D10.border.lineColor"))); // NOI18N
        D10.setMaximumSize(new java.awt.Dimension(15, 15));
        D10.setMinimumSize(new java.awt.Dimension(15, 15));
        D10.setName("D10"); // NOI18N

        javax.swing.GroupLayout D10Layout = new javax.swing.GroupLayout(D10);
        D10.setLayout(D10Layout);
        D10Layout.setHorizontalGroup(
            D10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );
        D10Layout.setVerticalGroup(
            D10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        D11.setBackground(resourceMap.getColor("D11.background")); // NOI18N
        D11.setBorder(javax.swing.BorderFactory.createLineBorder(resourceMap.getColor("D11.border.lineColor"))); // NOI18N
        D11.setMaximumSize(new java.awt.Dimension(15, 15));
        D11.setMinimumSize(new java.awt.Dimension(15, 15));
        D11.setName("D11"); // NOI18N

        javax.swing.GroupLayout D11Layout = new javax.swing.GroupLayout(D11);
        D11.setLayout(D11Layout);
        D11Layout.setHorizontalGroup(
            D11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );
        D11Layout.setVerticalGroup(
            D11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        D12.setBackground(resourceMap.getColor("D12.background")); // NOI18N
        D12.setBorder(javax.swing.BorderFactory.createLineBorder(resourceMap.getColor("D12.border.lineColor"))); // NOI18N
        D12.setMaximumSize(new java.awt.Dimension(15, 15));
        D12.setMinimumSize(new java.awt.Dimension(15, 15));
        D12.setName("D12"); // NOI18N

        javax.swing.GroupLayout D12Layout = new javax.swing.GroupLayout(D12);
        D12.setLayout(D12Layout);
        D12Layout.setHorizontalGroup(
            D12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );
        D12Layout.setVerticalGroup(
            D12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        D13.setBackground(resourceMap.getColor("D13.background")); // NOI18N
        D13.setBorder(javax.swing.BorderFactory.createLineBorder(resourceMap.getColor("D13.border.lineColor"))); // NOI18N
        D13.setMaximumSize(new java.awt.Dimension(15, 15));
        D13.setMinimumSize(new java.awt.Dimension(15, 15));
        D13.setName("D13"); // NOI18N

        javax.swing.GroupLayout D13Layout = new javax.swing.GroupLayout(D13);
        D13.setLayout(D13Layout);
        D13Layout.setHorizontalGroup(
            D13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );
        D13Layout.setVerticalGroup(
            D13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        D14.setBackground(resourceMap.getColor("D14.background")); // NOI18N
        D14.setBorder(javax.swing.BorderFactory.createLineBorder(resourceMap.getColor("D14.border.lineColor"))); // NOI18N
        D14.setMaximumSize(new java.awt.Dimension(15, 15));
        D14.setMinimumSize(new java.awt.Dimension(15, 15));
        D14.setName("D14"); // NOI18N

        javax.swing.GroupLayout D14Layout = new javax.swing.GroupLayout(D14);
        D14.setLayout(D14Layout);
        D14Layout.setHorizontalGroup(
            D14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );
        D14Layout.setVerticalGroup(
            D14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        D15.setBackground(resourceMap.getColor("D15.background")); // NOI18N
        D15.setBorder(javax.swing.BorderFactory.createLineBorder(resourceMap.getColor("D15.border.lineColor"))); // NOI18N
        D15.setMaximumSize(new java.awt.Dimension(15, 15));
        D15.setMinimumSize(new java.awt.Dimension(15, 15));
        D15.setName("D15"); // NOI18N

        javax.swing.GroupLayout D15Layout = new javax.swing.GroupLayout(D15);
        D15.setLayout(D15Layout);
        D15Layout.setHorizontalGroup(
            D15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );
        D15Layout.setVerticalGroup(
            D15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        D16.setBackground(resourceMap.getColor("D16.background")); // NOI18N
        D16.setBorder(javax.swing.BorderFactory.createLineBorder(resourceMap.getColor("D16.border.lineColor"))); // NOI18N
        D16.setMaximumSize(new java.awt.Dimension(15, 15));
        D16.setMinimumSize(new java.awt.Dimension(15, 15));
        D16.setName("D16"); // NOI18N

        javax.swing.GroupLayout D16Layout = new javax.swing.GroupLayout(D16);
        D16.setLayout(D16Layout);
        D16Layout.setHorizontalGroup(
            D16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );
        D16Layout.setVerticalGroup(
            D16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        D17.setBackground(resourceMap.getColor("D17.background")); // NOI18N
        D17.setBorder(javax.swing.BorderFactory.createLineBorder(resourceMap.getColor("D17.border.lineColor"))); // NOI18N
        D17.setMaximumSize(new java.awt.Dimension(15, 15));
        D17.setMinimumSize(new java.awt.Dimension(15, 15));
        D17.setName("D17"); // NOI18N

        javax.swing.GroupLayout D17Layout = new javax.swing.GroupLayout(D17);
        D17.setLayout(D17Layout);
        D17Layout.setHorizontalGroup(
            D17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );
        D17Layout.setVerticalGroup(
            D17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        D18.setBackground(resourceMap.getColor("D18.background")); // NOI18N
        D18.setBorder(javax.swing.BorderFactory.createLineBorder(resourceMap.getColor("D18.border.lineColor"))); // NOI18N
        D18.setMaximumSize(new java.awt.Dimension(15, 15));
        D18.setMinimumSize(new java.awt.Dimension(15, 15));
        D18.setName("D18"); // NOI18N

        javax.swing.GroupLayout D18Layout = new javax.swing.GroupLayout(D18);
        D18.setLayout(D18Layout);
        D18Layout.setHorizontalGroup(
            D18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );
        D18Layout.setVerticalGroup(
            D18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        D19.setBackground(resourceMap.getColor("D19.background")); // NOI18N
        D19.setBorder(javax.swing.BorderFactory.createLineBorder(resourceMap.getColor("D19.border.lineColor"))); // NOI18N
        D19.setMaximumSize(new java.awt.Dimension(15, 15));
        D19.setMinimumSize(new java.awt.Dimension(15, 15));
        D19.setName("D19"); // NOI18N

        javax.swing.GroupLayout D19Layout = new javax.swing.GroupLayout(D19);
        D19.setLayout(D19Layout);
        D19Layout.setHorizontalGroup(
            D19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );
        D19Layout.setVerticalGroup(
            D19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        D20.setBackground(resourceMap.getColor("D20.background")); // NOI18N
        D20.setBorder(javax.swing.BorderFactory.createLineBorder(resourceMap.getColor("D20.border.lineColor"))); // NOI18N
        D20.setMaximumSize(new java.awt.Dimension(15, 15));
        D20.setMinimumSize(new java.awt.Dimension(15, 15));
        D20.setName("D20"); // NOI18N

        javax.swing.GroupLayout D20Layout = new javax.swing.GroupLayout(D20);
        D20.setLayout(D20Layout);
        D20Layout.setHorizontalGroup(
            D20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );
        D20Layout.setVerticalGroup(
            D20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        E1.setBackground(resourceMap.getColor("E1.background")); // NOI18N
        E1.setBorder(javax.swing.BorderFactory.createLineBorder(resourceMap.getColor("E1.border.lineColor"))); // NOI18N
        E1.setMaximumSize(new java.awt.Dimension(15, 15));
        E1.setMinimumSize(new java.awt.Dimension(15, 15));
        E1.setName("E1"); // NOI18N

        javax.swing.GroupLayout E1Layout = new javax.swing.GroupLayout(E1);
        E1.setLayout(E1Layout);
        E1Layout.setHorizontalGroup(
            E1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );
        E1Layout.setVerticalGroup(
            E1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        E3.setBackground(resourceMap.getColor("E3.background")); // NOI18N
        E3.setBorder(javax.swing.BorderFactory.createLineBorder(resourceMap.getColor("E3.border.lineColor"))); // NOI18N
        E3.setMaximumSize(new java.awt.Dimension(15, 15));
        E3.setMinimumSize(new java.awt.Dimension(15, 15));
        E3.setName("E3"); // NOI18N

        javax.swing.GroupLayout E3Layout = new javax.swing.GroupLayout(E3);
        E3.setLayout(E3Layout);
        E3Layout.setHorizontalGroup(
            E3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );
        E3Layout.setVerticalGroup(
            E3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        E2.setBackground(resourceMap.getColor("E2.background")); // NOI18N
        E2.setBorder(javax.swing.BorderFactory.createLineBorder(resourceMap.getColor("E2.border.lineColor"))); // NOI18N
        E2.setMaximumSize(new java.awt.Dimension(15, 15));
        E2.setMinimumSize(new java.awt.Dimension(15, 15));
        E2.setName("E2"); // NOI18N

        javax.swing.GroupLayout E2Layout = new javax.swing.GroupLayout(E2);
        E2.setLayout(E2Layout);
        E2Layout.setHorizontalGroup(
            E2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );
        E2Layout.setVerticalGroup(
            E2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        E4.setBackground(resourceMap.getColor("E4.background")); // NOI18N
        E4.setBorder(javax.swing.BorderFactory.createLineBorder(resourceMap.getColor("E4.border.lineColor"))); // NOI18N
        E4.setMaximumSize(new java.awt.Dimension(15, 15));
        E4.setMinimumSize(new java.awt.Dimension(15, 15));
        E4.setName("E4"); // NOI18N

        javax.swing.GroupLayout E4Layout = new javax.swing.GroupLayout(E4);
        E4.setLayout(E4Layout);
        E4Layout.setHorizontalGroup(
            E4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );
        E4Layout.setVerticalGroup(
            E4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        E5.setBackground(resourceMap.getColor("E5.background")); // NOI18N
        E5.setBorder(javax.swing.BorderFactory.createLineBorder(resourceMap.getColor("E5.border.lineColor"))); // NOI18N
        E5.setMaximumSize(new java.awt.Dimension(15, 15));
        E5.setMinimumSize(new java.awt.Dimension(15, 15));
        E5.setName("E5"); // NOI18N

        javax.swing.GroupLayout E5Layout = new javax.swing.GroupLayout(E5);
        E5.setLayout(E5Layout);
        E5Layout.setHorizontalGroup(
            E5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );
        E5Layout.setVerticalGroup(
            E5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        E6.setBackground(resourceMap.getColor("E6.background")); // NOI18N
        E6.setBorder(javax.swing.BorderFactory.createLineBorder(resourceMap.getColor("E6.border.lineColor"))); // NOI18N
        E6.setMaximumSize(new java.awt.Dimension(15, 15));
        E6.setMinimumSize(new java.awt.Dimension(15, 15));
        E6.setName("E6"); // NOI18N

        javax.swing.GroupLayout E6Layout = new javax.swing.GroupLayout(E6);
        E6.setLayout(E6Layout);
        E6Layout.setHorizontalGroup(
            E6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );
        E6Layout.setVerticalGroup(
            E6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        E7.setBackground(resourceMap.getColor("E7.background")); // NOI18N
        E7.setBorder(javax.swing.BorderFactory.createLineBorder(resourceMap.getColor("E7.border.lineColor"))); // NOI18N
        E7.setMaximumSize(new java.awt.Dimension(15, 15));
        E7.setMinimumSize(new java.awt.Dimension(15, 15));
        E7.setName("E7"); // NOI18N

        javax.swing.GroupLayout E7Layout = new javax.swing.GroupLayout(E7);
        E7.setLayout(E7Layout);
        E7Layout.setHorizontalGroup(
            E7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );
        E7Layout.setVerticalGroup(
            E7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        E8.setBackground(resourceMap.getColor("E8.background")); // NOI18N
        E8.setBorder(javax.swing.BorderFactory.createLineBorder(resourceMap.getColor("E8.border.lineColor"))); // NOI18N
        E8.setMaximumSize(new java.awt.Dimension(15, 15));
        E8.setMinimumSize(new java.awt.Dimension(15, 15));
        E8.setName("E8"); // NOI18N

        javax.swing.GroupLayout E8Layout = new javax.swing.GroupLayout(E8);
        E8.setLayout(E8Layout);
        E8Layout.setHorizontalGroup(
            E8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );
        E8Layout.setVerticalGroup(
            E8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        E9.setBackground(resourceMap.getColor("E9.background")); // NOI18N
        E9.setBorder(javax.swing.BorderFactory.createLineBorder(resourceMap.getColor("E9.border.lineColor"))); // NOI18N
        E9.setMaximumSize(new java.awt.Dimension(15, 15));
        E9.setMinimumSize(new java.awt.Dimension(15, 15));
        E9.setName("E9"); // NOI18N

        javax.swing.GroupLayout E9Layout = new javax.swing.GroupLayout(E9);
        E9.setLayout(E9Layout);
        E9Layout.setHorizontalGroup(
            E9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );
        E9Layout.setVerticalGroup(
            E9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        E10.setBackground(resourceMap.getColor("E10.background")); // NOI18N
        E10.setBorder(javax.swing.BorderFactory.createLineBorder(resourceMap.getColor("E10.border.lineColor"))); // NOI18N
        E10.setMaximumSize(new java.awt.Dimension(15, 15));
        E10.setMinimumSize(new java.awt.Dimension(15, 15));
        E10.setName("E10"); // NOI18N

        javax.swing.GroupLayout E10Layout = new javax.swing.GroupLayout(E10);
        E10.setLayout(E10Layout);
        E10Layout.setHorizontalGroup(
            E10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );
        E10Layout.setVerticalGroup(
            E10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        E11.setBackground(resourceMap.getColor("E11.background")); // NOI18N
        E11.setBorder(javax.swing.BorderFactory.createLineBorder(resourceMap.getColor("E11.border.lineColor"))); // NOI18N
        E11.setMaximumSize(new java.awt.Dimension(15, 15));
        E11.setMinimumSize(new java.awt.Dimension(15, 15));
        E11.setName("E11"); // NOI18N

        javax.swing.GroupLayout E11Layout = new javax.swing.GroupLayout(E11);
        E11.setLayout(E11Layout);
        E11Layout.setHorizontalGroup(
            E11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );
        E11Layout.setVerticalGroup(
            E11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        E12.setBackground(resourceMap.getColor("E12.background")); // NOI18N
        E12.setBorder(javax.swing.BorderFactory.createLineBorder(resourceMap.getColor("E12.border.lineColor"))); // NOI18N
        E12.setMaximumSize(new java.awt.Dimension(15, 15));
        E12.setMinimumSize(new java.awt.Dimension(15, 15));
        E12.setName("E12"); // NOI18N

        javax.swing.GroupLayout E12Layout = new javax.swing.GroupLayout(E12);
        E12.setLayout(E12Layout);
        E12Layout.setHorizontalGroup(
            E12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );
        E12Layout.setVerticalGroup(
            E12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        E13.setBackground(resourceMap.getColor("E13.background")); // NOI18N
        E13.setBorder(javax.swing.BorderFactory.createLineBorder(resourceMap.getColor("E13.border.lineColor"))); // NOI18N
        E13.setMaximumSize(new java.awt.Dimension(15, 15));
        E13.setMinimumSize(new java.awt.Dimension(15, 15));
        E13.setName("E13"); // NOI18N

        javax.swing.GroupLayout E13Layout = new javax.swing.GroupLayout(E13);
        E13.setLayout(E13Layout);
        E13Layout.setHorizontalGroup(
            E13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );
        E13Layout.setVerticalGroup(
            E13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        E14.setBackground(resourceMap.getColor("E14.background")); // NOI18N
        E14.setBorder(javax.swing.BorderFactory.createLineBorder(resourceMap.getColor("E14.border.lineColor"))); // NOI18N
        E14.setMaximumSize(new java.awt.Dimension(15, 15));
        E14.setMinimumSize(new java.awt.Dimension(15, 15));
        E14.setName("E14"); // NOI18N

        javax.swing.GroupLayout E14Layout = new javax.swing.GroupLayout(E14);
        E14.setLayout(E14Layout);
        E14Layout.setHorizontalGroup(
            E14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );
        E14Layout.setVerticalGroup(
            E14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        E15.setBackground(resourceMap.getColor("E15.background")); // NOI18N
        E15.setBorder(javax.swing.BorderFactory.createLineBorder(resourceMap.getColor("E15.border.lineColor"))); // NOI18N
        E15.setMaximumSize(new java.awt.Dimension(15, 15));
        E15.setMinimumSize(new java.awt.Dimension(15, 15));
        E15.setName("E15"); // NOI18N

        javax.swing.GroupLayout E15Layout = new javax.swing.GroupLayout(E15);
        E15.setLayout(E15Layout);
        E15Layout.setHorizontalGroup(
            E15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );
        E15Layout.setVerticalGroup(
            E15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        E16.setBackground(resourceMap.getColor("E16.background")); // NOI18N
        E16.setBorder(javax.swing.BorderFactory.createLineBorder(resourceMap.getColor("E16.border.lineColor"))); // NOI18N
        E16.setMaximumSize(new java.awt.Dimension(15, 15));
        E16.setMinimumSize(new java.awt.Dimension(15, 15));
        E16.setName("E16"); // NOI18N

        javax.swing.GroupLayout E16Layout = new javax.swing.GroupLayout(E16);
        E16.setLayout(E16Layout);
        E16Layout.setHorizontalGroup(
            E16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );
        E16Layout.setVerticalGroup(
            E16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        E17.setBackground(resourceMap.getColor("E17.background")); // NOI18N
        E17.setBorder(javax.swing.BorderFactory.createLineBorder(resourceMap.getColor("E17.border.lineColor"))); // NOI18N
        E17.setMaximumSize(new java.awt.Dimension(15, 15));
        E17.setMinimumSize(new java.awt.Dimension(15, 15));
        E17.setName("E17"); // NOI18N

        javax.swing.GroupLayout E17Layout = new javax.swing.GroupLayout(E17);
        E17.setLayout(E17Layout);
        E17Layout.setHorizontalGroup(
            E17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );
        E17Layout.setVerticalGroup(
            E17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        E18.setBackground(resourceMap.getColor("E18.background")); // NOI18N
        E18.setBorder(javax.swing.BorderFactory.createLineBorder(resourceMap.getColor("E18.border.lineColor"))); // NOI18N
        E18.setMaximumSize(new java.awt.Dimension(15, 15));
        E18.setMinimumSize(new java.awt.Dimension(15, 15));
        E18.setName("E18"); // NOI18N

        javax.swing.GroupLayout E18Layout = new javax.swing.GroupLayout(E18);
        E18.setLayout(E18Layout);
        E18Layout.setHorizontalGroup(
            E18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );
        E18Layout.setVerticalGroup(
            E18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        E19.setBackground(resourceMap.getColor("E19.background")); // NOI18N
        E19.setBorder(javax.swing.BorderFactory.createLineBorder(resourceMap.getColor("E19.border.lineColor"))); // NOI18N
        E19.setMaximumSize(new java.awt.Dimension(15, 15));
        E19.setMinimumSize(new java.awt.Dimension(15, 15));
        E19.setName("E19"); // NOI18N

        javax.swing.GroupLayout E19Layout = new javax.swing.GroupLayout(E19);
        E19.setLayout(E19Layout);
        E19Layout.setHorizontalGroup(
            E19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );
        E19Layout.setVerticalGroup(
            E19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        E20.setBackground(resourceMap.getColor("E20.background")); // NOI18N
        E20.setBorder(javax.swing.BorderFactory.createLineBorder(resourceMap.getColor("E20.border.lineColor"))); // NOI18N
        E20.setMaximumSize(new java.awt.Dimension(15, 15));
        E20.setMinimumSize(new java.awt.Dimension(15, 15));
        E20.setName("E20"); // NOI18N

        javax.swing.GroupLayout E20Layout = new javax.swing.GroupLayout(E20);
        E20.setLayout(E20Layout);
        E20Layout.setHorizontalGroup(
            E20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );
        E20Layout.setVerticalGroup(
            E20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        F1.setBackground(resourceMap.getColor("F1.background")); // NOI18N
        F1.setBorder(javax.swing.BorderFactory.createLineBorder(resourceMap.getColor("F1.border.lineColor"))); // NOI18N
        F1.setMaximumSize(new java.awt.Dimension(15, 15));
        F1.setMinimumSize(new java.awt.Dimension(15, 15));
        F1.setName("F1"); // NOI18N

        javax.swing.GroupLayout F1Layout = new javax.swing.GroupLayout(F1);
        F1.setLayout(F1Layout);
        F1Layout.setHorizontalGroup(
            F1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );
        F1Layout.setVerticalGroup(
            F1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        F3.setBackground(resourceMap.getColor("F3.background")); // NOI18N
        F3.setBorder(javax.swing.BorderFactory.createLineBorder(resourceMap.getColor("F3.border.lineColor"))); // NOI18N
        F3.setMaximumSize(new java.awt.Dimension(15, 15));
        F3.setMinimumSize(new java.awt.Dimension(15, 15));
        F3.setName("F3"); // NOI18N

        javax.swing.GroupLayout F3Layout = new javax.swing.GroupLayout(F3);
        F3.setLayout(F3Layout);
        F3Layout.setHorizontalGroup(
            F3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );
        F3Layout.setVerticalGroup(
            F3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        F2.setBackground(resourceMap.getColor("F2.background")); // NOI18N
        F2.setBorder(javax.swing.BorderFactory.createLineBorder(resourceMap.getColor("F2.border.lineColor"))); // NOI18N
        F2.setMaximumSize(new java.awt.Dimension(15, 15));
        F2.setMinimumSize(new java.awt.Dimension(15, 15));
        F2.setName("F2"); // NOI18N

        javax.swing.GroupLayout F2Layout = new javax.swing.GroupLayout(F2);
        F2.setLayout(F2Layout);
        F2Layout.setHorizontalGroup(
            F2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );
        F2Layout.setVerticalGroup(
            F2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        F4.setBackground(resourceMap.getColor("F4.background")); // NOI18N
        F4.setBorder(javax.swing.BorderFactory.createLineBorder(resourceMap.getColor("F4.border.lineColor"))); // NOI18N
        F4.setMaximumSize(new java.awt.Dimension(15, 15));
        F4.setMinimumSize(new java.awt.Dimension(15, 15));
        F4.setName("F4"); // NOI18N

        javax.swing.GroupLayout F4Layout = new javax.swing.GroupLayout(F4);
        F4.setLayout(F4Layout);
        F4Layout.setHorizontalGroup(
            F4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );
        F4Layout.setVerticalGroup(
            F4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        F5.setBackground(resourceMap.getColor("F5.background")); // NOI18N
        F5.setBorder(javax.swing.BorderFactory.createLineBorder(resourceMap.getColor("F5.border.lineColor"))); // NOI18N
        F5.setMaximumSize(new java.awt.Dimension(15, 15));
        F5.setMinimumSize(new java.awt.Dimension(15, 15));
        F5.setName("F5"); // NOI18N

        javax.swing.GroupLayout F5Layout = new javax.swing.GroupLayout(F5);
        F5.setLayout(F5Layout);
        F5Layout.setHorizontalGroup(
            F5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );
        F5Layout.setVerticalGroup(
            F5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        F6.setBackground(resourceMap.getColor("F6.background")); // NOI18N
        F6.setBorder(javax.swing.BorderFactory.createLineBorder(resourceMap.getColor("F6.border.lineColor"))); // NOI18N
        F6.setMaximumSize(new java.awt.Dimension(15, 15));
        F6.setMinimumSize(new java.awt.Dimension(15, 15));
        F6.setName("F6"); // NOI18N

        javax.swing.GroupLayout F6Layout = new javax.swing.GroupLayout(F6);
        F6.setLayout(F6Layout);
        F6Layout.setHorizontalGroup(
            F6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );
        F6Layout.setVerticalGroup(
            F6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        F7.setBackground(resourceMap.getColor("F7.background")); // NOI18N
        F7.setBorder(javax.swing.BorderFactory.createLineBorder(resourceMap.getColor("F7.border.lineColor"))); // NOI18N
        F7.setMaximumSize(new java.awt.Dimension(15, 15));
        F7.setMinimumSize(new java.awt.Dimension(15, 15));
        F7.setName("F7"); // NOI18N

        javax.swing.GroupLayout F7Layout = new javax.swing.GroupLayout(F7);
        F7.setLayout(F7Layout);
        F7Layout.setHorizontalGroup(
            F7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );
        F7Layout.setVerticalGroup(
            F7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        F8.setBackground(resourceMap.getColor("F8.background")); // NOI18N
        F8.setBorder(javax.swing.BorderFactory.createLineBorder(resourceMap.getColor("F8.border.lineColor"))); // NOI18N
        F8.setMaximumSize(new java.awt.Dimension(15, 15));
        F8.setMinimumSize(new java.awt.Dimension(15, 15));
        F8.setName("F8"); // NOI18N

        javax.swing.GroupLayout F8Layout = new javax.swing.GroupLayout(F8);
        F8.setLayout(F8Layout);
        F8Layout.setHorizontalGroup(
            F8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );
        F8Layout.setVerticalGroup(
            F8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        F9.setBackground(resourceMap.getColor("F9.background")); // NOI18N
        F9.setBorder(javax.swing.BorderFactory.createLineBorder(resourceMap.getColor("F9.border.lineColor"))); // NOI18N
        F9.setMaximumSize(new java.awt.Dimension(15, 15));
        F9.setMinimumSize(new java.awt.Dimension(15, 15));
        F9.setName("F9"); // NOI18N

        javax.swing.GroupLayout F9Layout = new javax.swing.GroupLayout(F9);
        F9.setLayout(F9Layout);
        F9Layout.setHorizontalGroup(
            F9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );
        F9Layout.setVerticalGroup(
            F9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        F10.setBackground(resourceMap.getColor("F10.background")); // NOI18N
        F10.setBorder(javax.swing.BorderFactory.createLineBorder(resourceMap.getColor("F10.border.lineColor"))); // NOI18N
        F10.setMaximumSize(new java.awt.Dimension(15, 15));
        F10.setMinimumSize(new java.awt.Dimension(15, 15));
        F10.setName("F10"); // NOI18N

        javax.swing.GroupLayout F10Layout = new javax.swing.GroupLayout(F10);
        F10.setLayout(F10Layout);
        F10Layout.setHorizontalGroup(
            F10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );
        F10Layout.setVerticalGroup(
            F10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        F11.setBackground(resourceMap.getColor("F11.background")); // NOI18N
        F11.setBorder(javax.swing.BorderFactory.createLineBorder(resourceMap.getColor("F11.border.lineColor"))); // NOI18N
        F11.setMaximumSize(new java.awt.Dimension(15, 15));
        F11.setMinimumSize(new java.awt.Dimension(15, 15));
        F11.setName("F11"); // NOI18N

        javax.swing.GroupLayout F11Layout = new javax.swing.GroupLayout(F11);
        F11.setLayout(F11Layout);
        F11Layout.setHorizontalGroup(
            F11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );
        F11Layout.setVerticalGroup(
            F11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        F12.setBackground(resourceMap.getColor("F12.background")); // NOI18N
        F12.setBorder(javax.swing.BorderFactory.createLineBorder(resourceMap.getColor("F12.border.lineColor"))); // NOI18N
        F12.setMaximumSize(new java.awt.Dimension(15, 15));
        F12.setMinimumSize(new java.awt.Dimension(15, 15));
        F12.setName("F12"); // NOI18N

        javax.swing.GroupLayout F12Layout = new javax.swing.GroupLayout(F12);
        F12.setLayout(F12Layout);
        F12Layout.setHorizontalGroup(
            F12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );
        F12Layout.setVerticalGroup(
            F12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        F13.setBackground(resourceMap.getColor("F13.background")); // NOI18N
        F13.setBorder(javax.swing.BorderFactory.createLineBorder(resourceMap.getColor("F13.border.lineColor"))); // NOI18N
        F13.setMaximumSize(new java.awt.Dimension(15, 15));
        F13.setMinimumSize(new java.awt.Dimension(15, 15));
        F13.setName("F13"); // NOI18N

        javax.swing.GroupLayout F13Layout = new javax.swing.GroupLayout(F13);
        F13.setLayout(F13Layout);
        F13Layout.setHorizontalGroup(
            F13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );
        F13Layout.setVerticalGroup(
            F13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        F14.setBackground(resourceMap.getColor("F14.background")); // NOI18N
        F14.setBorder(javax.swing.BorderFactory.createLineBorder(resourceMap.getColor("F14.border.lineColor"))); // NOI18N
        F14.setMaximumSize(new java.awt.Dimension(15, 15));
        F14.setMinimumSize(new java.awt.Dimension(15, 15));
        F14.setName("F14"); // NOI18N

        javax.swing.GroupLayout F14Layout = new javax.swing.GroupLayout(F14);
        F14.setLayout(F14Layout);
        F14Layout.setHorizontalGroup(
            F14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );
        F14Layout.setVerticalGroup(
            F14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        F15.setBackground(resourceMap.getColor("F15.background")); // NOI18N
        F15.setBorder(javax.swing.BorderFactory.createLineBorder(resourceMap.getColor("F15.border.lineColor"))); // NOI18N
        F15.setMaximumSize(new java.awt.Dimension(15, 15));
        F15.setMinimumSize(new java.awt.Dimension(15, 15));
        F15.setName("F15"); // NOI18N

        javax.swing.GroupLayout F15Layout = new javax.swing.GroupLayout(F15);
        F15.setLayout(F15Layout);
        F15Layout.setHorizontalGroup(
            F15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );
        F15Layout.setVerticalGroup(
            F15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        F16.setBackground(resourceMap.getColor("F16.background")); // NOI18N
        F16.setBorder(javax.swing.BorderFactory.createLineBorder(resourceMap.getColor("F16.border.lineColor"))); // NOI18N
        F16.setMaximumSize(new java.awt.Dimension(15, 15));
        F16.setMinimumSize(new java.awt.Dimension(15, 15));
        F16.setName("F16"); // NOI18N

        javax.swing.GroupLayout F16Layout = new javax.swing.GroupLayout(F16);
        F16.setLayout(F16Layout);
        F16Layout.setHorizontalGroup(
            F16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );
        F16Layout.setVerticalGroup(
            F16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        F17.setBackground(resourceMap.getColor("F17.background")); // NOI18N
        F17.setBorder(javax.swing.BorderFactory.createLineBorder(resourceMap.getColor("F17.border.lineColor"))); // NOI18N
        F17.setMaximumSize(new java.awt.Dimension(15, 15));
        F17.setMinimumSize(new java.awt.Dimension(15, 15));
        F17.setName("F17"); // NOI18N

        javax.swing.GroupLayout F17Layout = new javax.swing.GroupLayout(F17);
        F17.setLayout(F17Layout);
        F17Layout.setHorizontalGroup(
            F17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );
        F17Layout.setVerticalGroup(
            F17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        F18.setBackground(resourceMap.getColor("F18.background")); // NOI18N
        F18.setBorder(javax.swing.BorderFactory.createLineBorder(resourceMap.getColor("F18.border.lineColor"))); // NOI18N
        F18.setMaximumSize(new java.awt.Dimension(15, 15));
        F18.setMinimumSize(new java.awt.Dimension(15, 15));
        F18.setName("F18"); // NOI18N

        javax.swing.GroupLayout F18Layout = new javax.swing.GroupLayout(F18);
        F18.setLayout(F18Layout);
        F18Layout.setHorizontalGroup(
            F18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );
        F18Layout.setVerticalGroup(
            F18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        F19.setBackground(resourceMap.getColor("F19.background")); // NOI18N
        F19.setBorder(javax.swing.BorderFactory.createLineBorder(resourceMap.getColor("F19.border.lineColor"))); // NOI18N
        F19.setMaximumSize(new java.awt.Dimension(15, 15));
        F19.setMinimumSize(new java.awt.Dimension(15, 15));
        F19.setName("F19"); // NOI18N

        javax.swing.GroupLayout F19Layout = new javax.swing.GroupLayout(F19);
        F19.setLayout(F19Layout);
        F19Layout.setHorizontalGroup(
            F19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );
        F19Layout.setVerticalGroup(
            F19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        F20.setBackground(resourceMap.getColor("F20.background")); // NOI18N
        F20.setBorder(javax.swing.BorderFactory.createLineBorder(resourceMap.getColor("F20.border.lineColor"))); // NOI18N
        F20.setMaximumSize(new java.awt.Dimension(15, 15));
        F20.setMinimumSize(new java.awt.Dimension(15, 15));
        F20.setName("F20"); // NOI18N

        javax.swing.GroupLayout F20Layout = new javax.swing.GroupLayout(F20);
        F20.setLayout(F20Layout);
        F20Layout.setHorizontalGroup(
            F20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );
        F20Layout.setVerticalGroup(
            F20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        G1.setBackground(resourceMap.getColor("G1.background")); // NOI18N
        G1.setBorder(javax.swing.BorderFactory.createLineBorder(resourceMap.getColor("G1.border.lineColor"))); // NOI18N
        G1.setMaximumSize(new java.awt.Dimension(15, 15));
        G1.setMinimumSize(new java.awt.Dimension(15, 15));
        G1.setName("G1"); // NOI18N

        javax.swing.GroupLayout G1Layout = new javax.swing.GroupLayout(G1);
        G1.setLayout(G1Layout);
        G1Layout.setHorizontalGroup(
            G1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );
        G1Layout.setVerticalGroup(
            G1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        G3.setBackground(resourceMap.getColor("G3.background")); // NOI18N
        G3.setBorder(javax.swing.BorderFactory.createLineBorder(resourceMap.getColor("G3.border.lineColor"))); // NOI18N
        G3.setMaximumSize(new java.awt.Dimension(15, 15));
        G3.setMinimumSize(new java.awt.Dimension(15, 15));
        G3.setName("G3"); // NOI18N

        javax.swing.GroupLayout G3Layout = new javax.swing.GroupLayout(G3);
        G3.setLayout(G3Layout);
        G3Layout.setHorizontalGroup(
            G3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );
        G3Layout.setVerticalGroup(
            G3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        G2.setBackground(resourceMap.getColor("G2.background")); // NOI18N
        G2.setBorder(javax.swing.BorderFactory.createLineBorder(resourceMap.getColor("G2.border.lineColor"))); // NOI18N
        G2.setMaximumSize(new java.awt.Dimension(15, 15));
        G2.setMinimumSize(new java.awt.Dimension(15, 15));
        G2.setName("G2"); // NOI18N

        javax.swing.GroupLayout G2Layout = new javax.swing.GroupLayout(G2);
        G2.setLayout(G2Layout);
        G2Layout.setHorizontalGroup(
            G2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );
        G2Layout.setVerticalGroup(
            G2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        G4.setBackground(resourceMap.getColor("G4.background")); // NOI18N
        G4.setBorder(javax.swing.BorderFactory.createLineBorder(resourceMap.getColor("G4.border.lineColor"))); // NOI18N
        G4.setMaximumSize(new java.awt.Dimension(15, 15));
        G4.setMinimumSize(new java.awt.Dimension(15, 15));
        G4.setName("G4"); // NOI18N

        javax.swing.GroupLayout G4Layout = new javax.swing.GroupLayout(G4);
        G4.setLayout(G4Layout);
        G4Layout.setHorizontalGroup(
            G4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );
        G4Layout.setVerticalGroup(
            G4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        G5.setBackground(resourceMap.getColor("G5.background")); // NOI18N
        G5.setBorder(javax.swing.BorderFactory.createLineBorder(resourceMap.getColor("G5.border.lineColor"))); // NOI18N
        G5.setMaximumSize(new java.awt.Dimension(15, 15));
        G5.setMinimumSize(new java.awt.Dimension(15, 15));
        G5.setName("G5"); // NOI18N

        javax.swing.GroupLayout G5Layout = new javax.swing.GroupLayout(G5);
        G5.setLayout(G5Layout);
        G5Layout.setHorizontalGroup(
            G5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );
        G5Layout.setVerticalGroup(
            G5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        G6.setBackground(resourceMap.getColor("G6.background")); // NOI18N
        G6.setBorder(javax.swing.BorderFactory.createLineBorder(resourceMap.getColor("G6.border.lineColor"))); // NOI18N
        G6.setMaximumSize(new java.awt.Dimension(15, 15));
        G6.setMinimumSize(new java.awt.Dimension(15, 15));
        G6.setName("G6"); // NOI18N

        javax.swing.GroupLayout G6Layout = new javax.swing.GroupLayout(G6);
        G6.setLayout(G6Layout);
        G6Layout.setHorizontalGroup(
            G6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );
        G6Layout.setVerticalGroup(
            G6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        G7.setBackground(resourceMap.getColor("G7.background")); // NOI18N
        G7.setBorder(javax.swing.BorderFactory.createLineBorder(resourceMap.getColor("G7.border.lineColor"))); // NOI18N
        G7.setMaximumSize(new java.awt.Dimension(15, 15));
        G7.setMinimumSize(new java.awt.Dimension(15, 15));
        G7.setName("G7"); // NOI18N

        javax.swing.GroupLayout G7Layout = new javax.swing.GroupLayout(G7);
        G7.setLayout(G7Layout);
        G7Layout.setHorizontalGroup(
            G7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );
        G7Layout.setVerticalGroup(
            G7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        G8.setBackground(resourceMap.getColor("G8.background")); // NOI18N
        G8.setBorder(javax.swing.BorderFactory.createLineBorder(resourceMap.getColor("G8.border.lineColor"))); // NOI18N
        G8.setMaximumSize(new java.awt.Dimension(15, 15));
        G8.setMinimumSize(new java.awt.Dimension(15, 15));
        G8.setName("G8"); // NOI18N

        javax.swing.GroupLayout G8Layout = new javax.swing.GroupLayout(G8);
        G8.setLayout(G8Layout);
        G8Layout.setHorizontalGroup(
            G8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );
        G8Layout.setVerticalGroup(
            G8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        G9.setBackground(resourceMap.getColor("G9.background")); // NOI18N
        G9.setBorder(javax.swing.BorderFactory.createLineBorder(resourceMap.getColor("G9.border.lineColor"))); // NOI18N
        G9.setMaximumSize(new java.awt.Dimension(15, 15));
        G9.setMinimumSize(new java.awt.Dimension(15, 15));
        G9.setName("G9"); // NOI18N

        javax.swing.GroupLayout G9Layout = new javax.swing.GroupLayout(G9);
        G9.setLayout(G9Layout);
        G9Layout.setHorizontalGroup(
            G9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );
        G9Layout.setVerticalGroup(
            G9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        G10.setBackground(resourceMap.getColor("G10.background")); // NOI18N
        G10.setBorder(javax.swing.BorderFactory.createLineBorder(resourceMap.getColor("G10.border.lineColor"))); // NOI18N
        G10.setMaximumSize(new java.awt.Dimension(15, 15));
        G10.setMinimumSize(new java.awt.Dimension(15, 15));
        G10.setName("G10"); // NOI18N

        javax.swing.GroupLayout G10Layout = new javax.swing.GroupLayout(G10);
        G10.setLayout(G10Layout);
        G10Layout.setHorizontalGroup(
            G10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );
        G10Layout.setVerticalGroup(
            G10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        G11.setBackground(resourceMap.getColor("G11.background")); // NOI18N
        G11.setBorder(javax.swing.BorderFactory.createLineBorder(resourceMap.getColor("G11.border.lineColor"))); // NOI18N
        G11.setMaximumSize(new java.awt.Dimension(15, 15));
        G11.setMinimumSize(new java.awt.Dimension(15, 15));
        G11.setName("G11"); // NOI18N

        javax.swing.GroupLayout G11Layout = new javax.swing.GroupLayout(G11);
        G11.setLayout(G11Layout);
        G11Layout.setHorizontalGroup(
            G11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );
        G11Layout.setVerticalGroup(
            G11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        G12.setBackground(resourceMap.getColor("G12.background")); // NOI18N
        G12.setBorder(javax.swing.BorderFactory.createLineBorder(resourceMap.getColor("G12.border.lineColor"))); // NOI18N
        G12.setMaximumSize(new java.awt.Dimension(15, 15));
        G12.setMinimumSize(new java.awt.Dimension(15, 15));
        G12.setName("G12"); // NOI18N

        javax.swing.GroupLayout G12Layout = new javax.swing.GroupLayout(G12);
        G12.setLayout(G12Layout);
        G12Layout.setHorizontalGroup(
            G12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );
        G12Layout.setVerticalGroup(
            G12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        G13.setBackground(resourceMap.getColor("G13.background")); // NOI18N
        G13.setBorder(javax.swing.BorderFactory.createLineBorder(resourceMap.getColor("G13.border.lineColor"))); // NOI18N
        G13.setMaximumSize(new java.awt.Dimension(15, 15));
        G13.setMinimumSize(new java.awt.Dimension(15, 15));
        G13.setName("G13"); // NOI18N

        javax.swing.GroupLayout G13Layout = new javax.swing.GroupLayout(G13);
        G13.setLayout(G13Layout);
        G13Layout.setHorizontalGroup(
            G13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );
        G13Layout.setVerticalGroup(
            G13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        G14.setBackground(resourceMap.getColor("G14.background")); // NOI18N
        G14.setBorder(javax.swing.BorderFactory.createLineBorder(resourceMap.getColor("G14.border.lineColor"))); // NOI18N
        G14.setMaximumSize(new java.awt.Dimension(15, 15));
        G14.setMinimumSize(new java.awt.Dimension(15, 15));
        G14.setName("G14"); // NOI18N

        javax.swing.GroupLayout G14Layout = new javax.swing.GroupLayout(G14);
        G14.setLayout(G14Layout);
        G14Layout.setHorizontalGroup(
            G14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );
        G14Layout.setVerticalGroup(
            G14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        G15.setBackground(resourceMap.getColor("G15.background")); // NOI18N
        G15.setBorder(javax.swing.BorderFactory.createLineBorder(resourceMap.getColor("G15.border.lineColor"))); // NOI18N
        G15.setMaximumSize(new java.awt.Dimension(15, 15));
        G15.setMinimumSize(new java.awt.Dimension(15, 15));
        G15.setName("G15"); // NOI18N

        javax.swing.GroupLayout G15Layout = new javax.swing.GroupLayout(G15);
        G15.setLayout(G15Layout);
        G15Layout.setHorizontalGroup(
            G15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );
        G15Layout.setVerticalGroup(
            G15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        G16.setBackground(resourceMap.getColor("G16.background")); // NOI18N
        G16.setBorder(javax.swing.BorderFactory.createLineBorder(resourceMap.getColor("G16.border.lineColor"))); // NOI18N
        G16.setMaximumSize(new java.awt.Dimension(15, 15));
        G16.setMinimumSize(new java.awt.Dimension(15, 15));
        G16.setName("G16"); // NOI18N

        javax.swing.GroupLayout G16Layout = new javax.swing.GroupLayout(G16);
        G16.setLayout(G16Layout);
        G16Layout.setHorizontalGroup(
            G16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );
        G16Layout.setVerticalGroup(
            G16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        G17.setBackground(resourceMap.getColor("G17.background")); // NOI18N
        G17.setBorder(javax.swing.BorderFactory.createLineBorder(resourceMap.getColor("G17.border.lineColor"))); // NOI18N
        G17.setMaximumSize(new java.awt.Dimension(15, 15));
        G17.setMinimumSize(new java.awt.Dimension(15, 15));
        G17.setName("G17"); // NOI18N

        javax.swing.GroupLayout G17Layout = new javax.swing.GroupLayout(G17);
        G17.setLayout(G17Layout);
        G17Layout.setHorizontalGroup(
            G17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );
        G17Layout.setVerticalGroup(
            G17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        G18.setBackground(resourceMap.getColor("G18.background")); // NOI18N
        G18.setBorder(javax.swing.BorderFactory.createLineBorder(resourceMap.getColor("G18.border.lineColor"))); // NOI18N
        G18.setMaximumSize(new java.awt.Dimension(15, 15));
        G18.setMinimumSize(new java.awt.Dimension(15, 15));
        G18.setName("G18"); // NOI18N

        javax.swing.GroupLayout G18Layout = new javax.swing.GroupLayout(G18);
        G18.setLayout(G18Layout);
        G18Layout.setHorizontalGroup(
            G18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );
        G18Layout.setVerticalGroup(
            G18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        G19.setBackground(resourceMap.getColor("G19.background")); // NOI18N
        G19.setBorder(javax.swing.BorderFactory.createLineBorder(resourceMap.getColor("G19.border.lineColor"))); // NOI18N
        G19.setMaximumSize(new java.awt.Dimension(15, 15));
        G19.setMinimumSize(new java.awt.Dimension(15, 15));
        G19.setName("G19"); // NOI18N

        javax.swing.GroupLayout G19Layout = new javax.swing.GroupLayout(G19);
        G19.setLayout(G19Layout);
        G19Layout.setHorizontalGroup(
            G19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );
        G19Layout.setVerticalGroup(
            G19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        G20.setBackground(resourceMap.getColor("G20.background")); // NOI18N
        G20.setBorder(javax.swing.BorderFactory.createLineBorder(resourceMap.getColor("G20.border.lineColor"))); // NOI18N
        G20.setMaximumSize(new java.awt.Dimension(15, 15));
        G20.setMinimumSize(new java.awt.Dimension(15, 15));
        G20.setName("G20"); // NOI18N

        javax.swing.GroupLayout G20Layout = new javax.swing.GroupLayout(G20);
        G20.setLayout(G20Layout);
        G20Layout.setHorizontalGroup(
            G20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );
        G20Layout.setVerticalGroup(
            G20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        H1.setBackground(resourceMap.getColor("H1.background")); // NOI18N
        H1.setBorder(javax.swing.BorderFactory.createLineBorder(resourceMap.getColor("H1.border.lineColor"))); // NOI18N
        H1.setMaximumSize(new java.awt.Dimension(15, 15));
        H1.setMinimumSize(new java.awt.Dimension(15, 15));
        H1.setName("H1"); // NOI18N

        javax.swing.GroupLayout H1Layout = new javax.swing.GroupLayout(H1);
        H1.setLayout(H1Layout);
        H1Layout.setHorizontalGroup(
            H1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );
        H1Layout.setVerticalGroup(
            H1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        H3.setBackground(resourceMap.getColor("H3.background")); // NOI18N
        H3.setBorder(javax.swing.BorderFactory.createLineBorder(resourceMap.getColor("H3.border.lineColor"))); // NOI18N
        H3.setMaximumSize(new java.awt.Dimension(15, 15));
        H3.setMinimumSize(new java.awt.Dimension(15, 15));
        H3.setName("H3"); // NOI18N

        javax.swing.GroupLayout H3Layout = new javax.swing.GroupLayout(H3);
        H3.setLayout(H3Layout);
        H3Layout.setHorizontalGroup(
            H3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );
        H3Layout.setVerticalGroup(
            H3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        H2.setBackground(resourceMap.getColor("H2.background")); // NOI18N
        H2.setBorder(javax.swing.BorderFactory.createLineBorder(resourceMap.getColor("H2.border.lineColor"))); // NOI18N
        H2.setMaximumSize(new java.awt.Dimension(15, 15));
        H2.setMinimumSize(new java.awt.Dimension(15, 15));
        H2.setName("H2"); // NOI18N

        javax.swing.GroupLayout H2Layout = new javax.swing.GroupLayout(H2);
        H2.setLayout(H2Layout);
        H2Layout.setHorizontalGroup(
            H2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );
        H2Layout.setVerticalGroup(
            H2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        H4.setBackground(resourceMap.getColor("H4.background")); // NOI18N
        H4.setBorder(javax.swing.BorderFactory.createLineBorder(resourceMap.getColor("H4.border.lineColor"))); // NOI18N
        H4.setMaximumSize(new java.awt.Dimension(15, 15));
        H4.setMinimumSize(new java.awt.Dimension(15, 15));
        H4.setName("H4"); // NOI18N

        javax.swing.GroupLayout H4Layout = new javax.swing.GroupLayout(H4);
        H4.setLayout(H4Layout);
        H4Layout.setHorizontalGroup(
            H4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );
        H4Layout.setVerticalGroup(
            H4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        H5.setBackground(resourceMap.getColor("H5.background")); // NOI18N
        H5.setBorder(javax.swing.BorderFactory.createLineBorder(resourceMap.getColor("H5.border.lineColor"))); // NOI18N
        H5.setMaximumSize(new java.awt.Dimension(15, 15));
        H5.setMinimumSize(new java.awt.Dimension(15, 15));
        H5.setName("H5"); // NOI18N

        javax.swing.GroupLayout H5Layout = new javax.swing.GroupLayout(H5);
        H5.setLayout(H5Layout);
        H5Layout.setHorizontalGroup(
            H5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );
        H5Layout.setVerticalGroup(
            H5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        H6.setBackground(resourceMap.getColor("H6.background")); // NOI18N
        H6.setBorder(javax.swing.BorderFactory.createLineBorder(resourceMap.getColor("H6.border.lineColor"))); // NOI18N
        H6.setMaximumSize(new java.awt.Dimension(15, 15));
        H6.setMinimumSize(new java.awt.Dimension(15, 15));
        H6.setName("H6"); // NOI18N

        javax.swing.GroupLayout H6Layout = new javax.swing.GroupLayout(H6);
        H6.setLayout(H6Layout);
        H6Layout.setHorizontalGroup(
            H6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );
        H6Layout.setVerticalGroup(
            H6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        H7.setBackground(resourceMap.getColor("H7.background")); // NOI18N
        H7.setBorder(javax.swing.BorderFactory.createLineBorder(resourceMap.getColor("H7.border.lineColor"))); // NOI18N
        H7.setMaximumSize(new java.awt.Dimension(15, 15));
        H7.setMinimumSize(new java.awt.Dimension(15, 15));
        H7.setName("H7"); // NOI18N

        javax.swing.GroupLayout H7Layout = new javax.swing.GroupLayout(H7);
        H7.setLayout(H7Layout);
        H7Layout.setHorizontalGroup(
            H7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );
        H7Layout.setVerticalGroup(
            H7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        H8.setBackground(resourceMap.getColor("H8.background")); // NOI18N
        H8.setBorder(javax.swing.BorderFactory.createLineBorder(resourceMap.getColor("H8.border.lineColor"))); // NOI18N
        H8.setMaximumSize(new java.awt.Dimension(15, 15));
        H8.setMinimumSize(new java.awt.Dimension(15, 15));
        H8.setName("H8"); // NOI18N

        javax.swing.GroupLayout H8Layout = new javax.swing.GroupLayout(H8);
        H8.setLayout(H8Layout);
        H8Layout.setHorizontalGroup(
            H8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );
        H8Layout.setVerticalGroup(
            H8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        H9.setBackground(resourceMap.getColor("H9.background")); // NOI18N
        H9.setBorder(javax.swing.BorderFactory.createLineBorder(resourceMap.getColor("H9.border.lineColor"))); // NOI18N
        H9.setMaximumSize(new java.awt.Dimension(15, 15));
        H9.setMinimumSize(new java.awt.Dimension(15, 15));
        H9.setName("H9"); // NOI18N

        javax.swing.GroupLayout H9Layout = new javax.swing.GroupLayout(H9);
        H9.setLayout(H9Layout);
        H9Layout.setHorizontalGroup(
            H9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );
        H9Layout.setVerticalGroup(
            H9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        H10.setBackground(resourceMap.getColor("H10.background")); // NOI18N
        H10.setBorder(javax.swing.BorderFactory.createLineBorder(resourceMap.getColor("H10.border.lineColor"))); // NOI18N
        H10.setMaximumSize(new java.awt.Dimension(15, 15));
        H10.setMinimumSize(new java.awt.Dimension(15, 15));
        H10.setName("H10"); // NOI18N

        javax.swing.GroupLayout H10Layout = new javax.swing.GroupLayout(H10);
        H10.setLayout(H10Layout);
        H10Layout.setHorizontalGroup(
            H10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );
        H10Layout.setVerticalGroup(
            H10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        H11.setBackground(resourceMap.getColor("H11.background")); // NOI18N
        H11.setBorder(javax.swing.BorderFactory.createLineBorder(resourceMap.getColor("H11.border.lineColor"))); // NOI18N
        H11.setMaximumSize(new java.awt.Dimension(15, 15));
        H11.setMinimumSize(new java.awt.Dimension(15, 15));
        H11.setName("H11"); // NOI18N

        javax.swing.GroupLayout H11Layout = new javax.swing.GroupLayout(H11);
        H11.setLayout(H11Layout);
        H11Layout.setHorizontalGroup(
            H11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );
        H11Layout.setVerticalGroup(
            H11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        H12.setBackground(resourceMap.getColor("H12.background")); // NOI18N
        H12.setBorder(javax.swing.BorderFactory.createLineBorder(resourceMap.getColor("H12.border.lineColor"))); // NOI18N
        H12.setMaximumSize(new java.awt.Dimension(15, 15));
        H12.setMinimumSize(new java.awt.Dimension(15, 15));
        H12.setName("H12"); // NOI18N

        javax.swing.GroupLayout H12Layout = new javax.swing.GroupLayout(H12);
        H12.setLayout(H12Layout);
        H12Layout.setHorizontalGroup(
            H12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );
        H12Layout.setVerticalGroup(
            H12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        H13.setBackground(resourceMap.getColor("H13.background")); // NOI18N
        H13.setBorder(javax.swing.BorderFactory.createLineBorder(resourceMap.getColor("H13.border.lineColor"))); // NOI18N
        H13.setMaximumSize(new java.awt.Dimension(15, 15));
        H13.setMinimumSize(new java.awt.Dimension(15, 15));
        H13.setName("H13"); // NOI18N

        javax.swing.GroupLayout H13Layout = new javax.swing.GroupLayout(H13);
        H13.setLayout(H13Layout);
        H13Layout.setHorizontalGroup(
            H13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );
        H13Layout.setVerticalGroup(
            H13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        H14.setBackground(resourceMap.getColor("H14.background")); // NOI18N
        H14.setBorder(javax.swing.BorderFactory.createLineBorder(resourceMap.getColor("H14.border.lineColor"))); // NOI18N
        H14.setMaximumSize(new java.awt.Dimension(15, 15));
        H14.setMinimumSize(new java.awt.Dimension(15, 15));
        H14.setName("H14"); // NOI18N

        javax.swing.GroupLayout H14Layout = new javax.swing.GroupLayout(H14);
        H14.setLayout(H14Layout);
        H14Layout.setHorizontalGroup(
            H14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );
        H14Layout.setVerticalGroup(
            H14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        H15.setBackground(resourceMap.getColor("H15.background")); // NOI18N
        H15.setBorder(javax.swing.BorderFactory.createLineBorder(resourceMap.getColor("H15.border.lineColor"))); // NOI18N
        H15.setMaximumSize(new java.awt.Dimension(15, 15));
        H15.setMinimumSize(new java.awt.Dimension(15, 15));
        H15.setName("H15"); // NOI18N

        javax.swing.GroupLayout H15Layout = new javax.swing.GroupLayout(H15);
        H15.setLayout(H15Layout);
        H15Layout.setHorizontalGroup(
            H15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );
        H15Layout.setVerticalGroup(
            H15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        H16.setBackground(resourceMap.getColor("H16.background")); // NOI18N
        H16.setBorder(javax.swing.BorderFactory.createLineBorder(resourceMap.getColor("H16.border.lineColor"))); // NOI18N
        H16.setMaximumSize(new java.awt.Dimension(15, 15));
        H16.setMinimumSize(new java.awt.Dimension(15, 15));
        H16.setName("H16"); // NOI18N

        javax.swing.GroupLayout H16Layout = new javax.swing.GroupLayout(H16);
        H16.setLayout(H16Layout);
        H16Layout.setHorizontalGroup(
            H16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );
        H16Layout.setVerticalGroup(
            H16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        H17.setBackground(resourceMap.getColor("H17.background")); // NOI18N
        H17.setBorder(javax.swing.BorderFactory.createLineBorder(resourceMap.getColor("H17.border.lineColor"))); // NOI18N
        H17.setMaximumSize(new java.awt.Dimension(15, 15));
        H17.setMinimumSize(new java.awt.Dimension(15, 15));
        H17.setName("H17"); // NOI18N

        javax.swing.GroupLayout H17Layout = new javax.swing.GroupLayout(H17);
        H17.setLayout(H17Layout);
        H17Layout.setHorizontalGroup(
            H17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );
        H17Layout.setVerticalGroup(
            H17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        H18.setBackground(resourceMap.getColor("H18.background")); // NOI18N
        H18.setBorder(javax.swing.BorderFactory.createLineBorder(resourceMap.getColor("H18.border.lineColor"))); // NOI18N
        H18.setMaximumSize(new java.awt.Dimension(15, 15));
        H18.setMinimumSize(new java.awt.Dimension(15, 15));
        H18.setName("H18"); // NOI18N

        javax.swing.GroupLayout H18Layout = new javax.swing.GroupLayout(H18);
        H18.setLayout(H18Layout);
        H18Layout.setHorizontalGroup(
            H18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );
        H18Layout.setVerticalGroup(
            H18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        H19.setBackground(resourceMap.getColor("H19.background")); // NOI18N
        H19.setBorder(javax.swing.BorderFactory.createLineBorder(resourceMap.getColor("H19.border.lineColor"))); // NOI18N
        H19.setMaximumSize(new java.awt.Dimension(15, 15));
        H19.setMinimumSize(new java.awt.Dimension(15, 15));
        H19.setName("H19"); // NOI18N

        javax.swing.GroupLayout H19Layout = new javax.swing.GroupLayout(H19);
        H19.setLayout(H19Layout);
        H19Layout.setHorizontalGroup(
            H19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );
        H19Layout.setVerticalGroup(
            H19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        H20.setBackground(resourceMap.getColor("H20.background")); // NOI18N
        H20.setBorder(javax.swing.BorderFactory.createLineBorder(resourceMap.getColor("H20.border.lineColor"))); // NOI18N
        H20.setMaximumSize(new java.awt.Dimension(15, 15));
        H20.setMinimumSize(new java.awt.Dimension(15, 15));
        H20.setName("H20"); // NOI18N

        javax.swing.GroupLayout H20Layout = new javax.swing.GroupLayout(H20);
        H20.setLayout(H20Layout);
        H20Layout.setHorizontalGroup(
            H20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );
        H20Layout.setVerticalGroup(
            H20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        I1.setBackground(resourceMap.getColor("I1.background")); // NOI18N
        I1.setBorder(javax.swing.BorderFactory.createLineBorder(resourceMap.getColor("I1.border.lineColor"))); // NOI18N
        I1.setMaximumSize(new java.awt.Dimension(15, 15));
        I1.setMinimumSize(new java.awt.Dimension(15, 15));
        I1.setName("I1"); // NOI18N

        javax.swing.GroupLayout I1Layout = new javax.swing.GroupLayout(I1);
        I1.setLayout(I1Layout);
        I1Layout.setHorizontalGroup(
            I1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );
        I1Layout.setVerticalGroup(
            I1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        I3.setBackground(resourceMap.getColor("I3.background")); // NOI18N
        I3.setBorder(javax.swing.BorderFactory.createLineBorder(resourceMap.getColor("I3.border.lineColor"))); // NOI18N
        I3.setMaximumSize(new java.awt.Dimension(15, 15));
        I3.setMinimumSize(new java.awt.Dimension(15, 15));
        I3.setName("I3"); // NOI18N

        javax.swing.GroupLayout I3Layout = new javax.swing.GroupLayout(I3);
        I3.setLayout(I3Layout);
        I3Layout.setHorizontalGroup(
            I3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );
        I3Layout.setVerticalGroup(
            I3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        I2.setBackground(resourceMap.getColor("I2.background")); // NOI18N
        I2.setBorder(javax.swing.BorderFactory.createLineBorder(resourceMap.getColor("I2.border.lineColor"))); // NOI18N
        I2.setMaximumSize(new java.awt.Dimension(15, 15));
        I2.setMinimumSize(new java.awt.Dimension(15, 15));
        I2.setName("I2"); // NOI18N

        javax.swing.GroupLayout I2Layout = new javax.swing.GroupLayout(I2);
        I2.setLayout(I2Layout);
        I2Layout.setHorizontalGroup(
            I2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );
        I2Layout.setVerticalGroup(
            I2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        I4.setBackground(resourceMap.getColor("I4.background")); // NOI18N
        I4.setBorder(javax.swing.BorderFactory.createLineBorder(resourceMap.getColor("I4.border.lineColor"))); // NOI18N
        I4.setMaximumSize(new java.awt.Dimension(15, 15));
        I4.setMinimumSize(new java.awt.Dimension(15, 15));
        I4.setName("I4"); // NOI18N

        javax.swing.GroupLayout I4Layout = new javax.swing.GroupLayout(I4);
        I4.setLayout(I4Layout);
        I4Layout.setHorizontalGroup(
            I4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );
        I4Layout.setVerticalGroup(
            I4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        I5.setBackground(resourceMap.getColor("I5.background")); // NOI18N
        I5.setBorder(javax.swing.BorderFactory.createLineBorder(resourceMap.getColor("I5.border.lineColor"))); // NOI18N
        I5.setMaximumSize(new java.awt.Dimension(15, 15));
        I5.setMinimumSize(new java.awt.Dimension(15, 15));
        I5.setName("I5"); // NOI18N

        javax.swing.GroupLayout I5Layout = new javax.swing.GroupLayout(I5);
        I5.setLayout(I5Layout);
        I5Layout.setHorizontalGroup(
            I5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );
        I5Layout.setVerticalGroup(
            I5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        I6.setBackground(resourceMap.getColor("I6.background")); // NOI18N
        I6.setBorder(javax.swing.BorderFactory.createLineBorder(resourceMap.getColor("I6.border.lineColor"))); // NOI18N
        I6.setMaximumSize(new java.awt.Dimension(15, 15));
        I6.setMinimumSize(new java.awt.Dimension(15, 15));
        I6.setName("I6"); // NOI18N

        javax.swing.GroupLayout I6Layout = new javax.swing.GroupLayout(I6);
        I6.setLayout(I6Layout);
        I6Layout.setHorizontalGroup(
            I6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );
        I6Layout.setVerticalGroup(
            I6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        I7.setBackground(resourceMap.getColor("I7.background")); // NOI18N
        I7.setBorder(javax.swing.BorderFactory.createLineBorder(resourceMap.getColor("I7.border.lineColor"))); // NOI18N
        I7.setMaximumSize(new java.awt.Dimension(15, 15));
        I7.setMinimumSize(new java.awt.Dimension(15, 15));
        I7.setName("I7"); // NOI18N

        javax.swing.GroupLayout I7Layout = new javax.swing.GroupLayout(I7);
        I7.setLayout(I7Layout);
        I7Layout.setHorizontalGroup(
            I7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );
        I7Layout.setVerticalGroup(
            I7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        I8.setBackground(resourceMap.getColor("I8.background")); // NOI18N
        I8.setBorder(javax.swing.BorderFactory.createLineBorder(resourceMap.getColor("I8.border.lineColor"))); // NOI18N
        I8.setMaximumSize(new java.awt.Dimension(15, 15));
        I8.setMinimumSize(new java.awt.Dimension(15, 15));
        I8.setName("I8"); // NOI18N

        javax.swing.GroupLayout I8Layout = new javax.swing.GroupLayout(I8);
        I8.setLayout(I8Layout);
        I8Layout.setHorizontalGroup(
            I8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );
        I8Layout.setVerticalGroup(
            I8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        I9.setBackground(resourceMap.getColor("I9.background")); // NOI18N
        I9.setBorder(javax.swing.BorderFactory.createLineBorder(resourceMap.getColor("I9.border.lineColor"))); // NOI18N
        I9.setMaximumSize(new java.awt.Dimension(15, 15));
        I9.setMinimumSize(new java.awt.Dimension(15, 15));
        I9.setName("I9"); // NOI18N

        javax.swing.GroupLayout I9Layout = new javax.swing.GroupLayout(I9);
        I9.setLayout(I9Layout);
        I9Layout.setHorizontalGroup(
            I9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );
        I9Layout.setVerticalGroup(
            I9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        I10.setBackground(resourceMap.getColor("I10.background")); // NOI18N
        I10.setBorder(javax.swing.BorderFactory.createLineBorder(resourceMap.getColor("I10.border.lineColor"))); // NOI18N
        I10.setMaximumSize(new java.awt.Dimension(15, 15));
        I10.setMinimumSize(new java.awt.Dimension(15, 15));
        I10.setName("I10"); // NOI18N

        javax.swing.GroupLayout I10Layout = new javax.swing.GroupLayout(I10);
        I10.setLayout(I10Layout);
        I10Layout.setHorizontalGroup(
            I10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );
        I10Layout.setVerticalGroup(
            I10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        I11.setBackground(resourceMap.getColor("I11.background")); // NOI18N
        I11.setBorder(javax.swing.BorderFactory.createLineBorder(resourceMap.getColor("I11.border.lineColor"))); // NOI18N
        I11.setMaximumSize(new java.awt.Dimension(15, 15));
        I11.setMinimumSize(new java.awt.Dimension(15, 15));
        I11.setName("I11"); // NOI18N

        javax.swing.GroupLayout I11Layout = new javax.swing.GroupLayout(I11);
        I11.setLayout(I11Layout);
        I11Layout.setHorizontalGroup(
            I11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );
        I11Layout.setVerticalGroup(
            I11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        I12.setBackground(resourceMap.getColor("I12.background")); // NOI18N
        I12.setBorder(javax.swing.BorderFactory.createLineBorder(resourceMap.getColor("I12.border.lineColor"))); // NOI18N
        I12.setMaximumSize(new java.awt.Dimension(15, 15));
        I12.setMinimumSize(new java.awt.Dimension(15, 15));
        I12.setName("I12"); // NOI18N

        javax.swing.GroupLayout I12Layout = new javax.swing.GroupLayout(I12);
        I12.setLayout(I12Layout);
        I12Layout.setHorizontalGroup(
            I12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );
        I12Layout.setVerticalGroup(
            I12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        I13.setBackground(resourceMap.getColor("I13.background")); // NOI18N
        I13.setBorder(javax.swing.BorderFactory.createLineBorder(resourceMap.getColor("I13.border.lineColor"))); // NOI18N
        I13.setMaximumSize(new java.awt.Dimension(15, 15));
        I13.setMinimumSize(new java.awt.Dimension(15, 15));
        I13.setName("I13"); // NOI18N

        javax.swing.GroupLayout I13Layout = new javax.swing.GroupLayout(I13);
        I13.setLayout(I13Layout);
        I13Layout.setHorizontalGroup(
            I13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );
        I13Layout.setVerticalGroup(
            I13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        I14.setBackground(resourceMap.getColor("I14.background")); // NOI18N
        I14.setBorder(javax.swing.BorderFactory.createLineBorder(resourceMap.getColor("I14.border.lineColor"))); // NOI18N
        I14.setMaximumSize(new java.awt.Dimension(15, 15));
        I14.setMinimumSize(new java.awt.Dimension(15, 15));
        I14.setName("I14"); // NOI18N

        javax.swing.GroupLayout I14Layout = new javax.swing.GroupLayout(I14);
        I14.setLayout(I14Layout);
        I14Layout.setHorizontalGroup(
            I14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );
        I14Layout.setVerticalGroup(
            I14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        I15.setBackground(resourceMap.getColor("I15.background")); // NOI18N
        I15.setBorder(javax.swing.BorderFactory.createLineBorder(resourceMap.getColor("I15.border.lineColor"))); // NOI18N
        I15.setMaximumSize(new java.awt.Dimension(15, 15));
        I15.setMinimumSize(new java.awt.Dimension(15, 15));
        I15.setName("I15"); // NOI18N

        javax.swing.GroupLayout I15Layout = new javax.swing.GroupLayout(I15);
        I15.setLayout(I15Layout);
        I15Layout.setHorizontalGroup(
            I15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );
        I15Layout.setVerticalGroup(
            I15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        I16.setBackground(resourceMap.getColor("I16.background")); // NOI18N
        I16.setBorder(javax.swing.BorderFactory.createLineBorder(resourceMap.getColor("I16.border.lineColor"))); // NOI18N
        I16.setMaximumSize(new java.awt.Dimension(15, 15));
        I16.setMinimumSize(new java.awt.Dimension(15, 15));
        I16.setName("I16"); // NOI18N

        javax.swing.GroupLayout I16Layout = new javax.swing.GroupLayout(I16);
        I16.setLayout(I16Layout);
        I16Layout.setHorizontalGroup(
            I16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );
        I16Layout.setVerticalGroup(
            I16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        I17.setBackground(resourceMap.getColor("I17.background")); // NOI18N
        I17.setBorder(javax.swing.BorderFactory.createLineBorder(resourceMap.getColor("I17.border.lineColor"))); // NOI18N
        I17.setMaximumSize(new java.awt.Dimension(15, 15));
        I17.setMinimumSize(new java.awt.Dimension(15, 15));
        I17.setName("I17"); // NOI18N

        javax.swing.GroupLayout I17Layout = new javax.swing.GroupLayout(I17);
        I17.setLayout(I17Layout);
        I17Layout.setHorizontalGroup(
            I17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );
        I17Layout.setVerticalGroup(
            I17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        I18.setBackground(resourceMap.getColor("I18.background")); // NOI18N
        I18.setBorder(javax.swing.BorderFactory.createLineBorder(resourceMap.getColor("I18.border.lineColor"))); // NOI18N
        I18.setMaximumSize(new java.awt.Dimension(15, 15));
        I18.setMinimumSize(new java.awt.Dimension(15, 15));
        I18.setName("I18"); // NOI18N

        javax.swing.GroupLayout I18Layout = new javax.swing.GroupLayout(I18);
        I18.setLayout(I18Layout);
        I18Layout.setHorizontalGroup(
            I18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );
        I18Layout.setVerticalGroup(
            I18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        I19.setBackground(resourceMap.getColor("I19.background")); // NOI18N
        I19.setBorder(javax.swing.BorderFactory.createLineBorder(resourceMap.getColor("I19.border.lineColor"))); // NOI18N
        I19.setMaximumSize(new java.awt.Dimension(15, 15));
        I19.setMinimumSize(new java.awt.Dimension(15, 15));
        I19.setName("I19"); // NOI18N

        javax.swing.GroupLayout I19Layout = new javax.swing.GroupLayout(I19);
        I19.setLayout(I19Layout);
        I19Layout.setHorizontalGroup(
            I19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );
        I19Layout.setVerticalGroup(
            I19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        I20.setBackground(resourceMap.getColor("I20.background")); // NOI18N
        I20.setBorder(javax.swing.BorderFactory.createLineBorder(resourceMap.getColor("I20.border.lineColor"))); // NOI18N
        I20.setMaximumSize(new java.awt.Dimension(15, 15));
        I20.setMinimumSize(new java.awt.Dimension(15, 15));
        I20.setName("I20"); // NOI18N

        javax.swing.GroupLayout I20Layout = new javax.swing.GroupLayout(I20);
        I20.setLayout(I20Layout);
        I20Layout.setHorizontalGroup(
            I20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );
        I20Layout.setVerticalGroup(
            I20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        J1.setBackground(resourceMap.getColor("J1.background")); // NOI18N
        J1.setBorder(javax.swing.BorderFactory.createLineBorder(resourceMap.getColor("J1.border.lineColor"))); // NOI18N
        J1.setMaximumSize(new java.awt.Dimension(15, 15));
        J1.setMinimumSize(new java.awt.Dimension(15, 15));
        J1.setName("J1"); // NOI18N

        javax.swing.GroupLayout J1Layout = new javax.swing.GroupLayout(J1);
        J1.setLayout(J1Layout);
        J1Layout.setHorizontalGroup(
            J1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );
        J1Layout.setVerticalGroup(
            J1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        J3.setBackground(resourceMap.getColor("J3.background")); // NOI18N
        J3.setBorder(javax.swing.BorderFactory.createLineBorder(resourceMap.getColor("J3.border.lineColor"))); // NOI18N
        J3.setMaximumSize(new java.awt.Dimension(15, 15));
        J3.setMinimumSize(new java.awt.Dimension(15, 15));
        J3.setName("J3"); // NOI18N

        javax.swing.GroupLayout J3Layout = new javax.swing.GroupLayout(J3);
        J3.setLayout(J3Layout);
        J3Layout.setHorizontalGroup(
            J3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );
        J3Layout.setVerticalGroup(
            J3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        J2.setBackground(resourceMap.getColor("J2.background")); // NOI18N
        J2.setBorder(javax.swing.BorderFactory.createLineBorder(resourceMap.getColor("J2.border.lineColor"))); // NOI18N
        J2.setMaximumSize(new java.awt.Dimension(15, 15));
        J2.setMinimumSize(new java.awt.Dimension(15, 15));
        J2.setName("J2"); // NOI18N

        javax.swing.GroupLayout J2Layout = new javax.swing.GroupLayout(J2);
        J2.setLayout(J2Layout);
        J2Layout.setHorizontalGroup(
            J2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );
        J2Layout.setVerticalGroup(
            J2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        J4.setBackground(resourceMap.getColor("J4.background")); // NOI18N
        J4.setBorder(javax.swing.BorderFactory.createLineBorder(resourceMap.getColor("J4.border.lineColor"))); // NOI18N
        J4.setMaximumSize(new java.awt.Dimension(15, 15));
        J4.setMinimumSize(new java.awt.Dimension(15, 15));
        J4.setName("J4"); // NOI18N

        javax.swing.GroupLayout J4Layout = new javax.swing.GroupLayout(J4);
        J4.setLayout(J4Layout);
        J4Layout.setHorizontalGroup(
            J4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );
        J4Layout.setVerticalGroup(
            J4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        J5.setBackground(resourceMap.getColor("J5.background")); // NOI18N
        J5.setBorder(javax.swing.BorderFactory.createLineBorder(resourceMap.getColor("J5.border.lineColor"))); // NOI18N
        J5.setMaximumSize(new java.awt.Dimension(15, 15));
        J5.setMinimumSize(new java.awt.Dimension(15, 15));
        J5.setName("J5"); // NOI18N

        javax.swing.GroupLayout J5Layout = new javax.swing.GroupLayout(J5);
        J5.setLayout(J5Layout);
        J5Layout.setHorizontalGroup(
            J5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );
        J5Layout.setVerticalGroup(
            J5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        J6.setBackground(resourceMap.getColor("J6.background")); // NOI18N
        J6.setBorder(javax.swing.BorderFactory.createLineBorder(resourceMap.getColor("J6.border.lineColor"))); // NOI18N
        J6.setMaximumSize(new java.awt.Dimension(15, 15));
        J6.setMinimumSize(new java.awt.Dimension(15, 15));
        J6.setName("J6"); // NOI18N

        javax.swing.GroupLayout J6Layout = new javax.swing.GroupLayout(J6);
        J6.setLayout(J6Layout);
        J6Layout.setHorizontalGroup(
            J6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );
        J6Layout.setVerticalGroup(
            J6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        J7.setBackground(resourceMap.getColor("J7.background")); // NOI18N
        J7.setBorder(javax.swing.BorderFactory.createLineBorder(resourceMap.getColor("J7.border.lineColor"))); // NOI18N
        J7.setMaximumSize(new java.awt.Dimension(15, 15));
        J7.setMinimumSize(new java.awt.Dimension(15, 15));
        J7.setName("J7"); // NOI18N

        javax.swing.GroupLayout J7Layout = new javax.swing.GroupLayout(J7);
        J7.setLayout(J7Layout);
        J7Layout.setHorizontalGroup(
            J7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );
        J7Layout.setVerticalGroup(
            J7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        J8.setBackground(resourceMap.getColor("J8.background")); // NOI18N
        J8.setBorder(javax.swing.BorderFactory.createLineBorder(resourceMap.getColor("J8.border.lineColor"))); // NOI18N
        J8.setMaximumSize(new java.awt.Dimension(15, 15));
        J8.setMinimumSize(new java.awt.Dimension(15, 15));
        J8.setName("J8"); // NOI18N

        javax.swing.GroupLayout J8Layout = new javax.swing.GroupLayout(J8);
        J8.setLayout(J8Layout);
        J8Layout.setHorizontalGroup(
            J8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );
        J8Layout.setVerticalGroup(
            J8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        J9.setBackground(resourceMap.getColor("J9.background")); // NOI18N
        J9.setBorder(javax.swing.BorderFactory.createLineBorder(resourceMap.getColor("J9.border.lineColor"))); // NOI18N
        J9.setMaximumSize(new java.awt.Dimension(15, 15));
        J9.setMinimumSize(new java.awt.Dimension(15, 15));
        J9.setName("J9"); // NOI18N

        javax.swing.GroupLayout J9Layout = new javax.swing.GroupLayout(J9);
        J9.setLayout(J9Layout);
        J9Layout.setHorizontalGroup(
            J9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );
        J9Layout.setVerticalGroup(
            J9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        J10.setBackground(resourceMap.getColor("J10.background")); // NOI18N
        J10.setBorder(javax.swing.BorderFactory.createLineBorder(resourceMap.getColor("J10.border.lineColor"))); // NOI18N
        J10.setMaximumSize(new java.awt.Dimension(15, 15));
        J10.setMinimumSize(new java.awt.Dimension(15, 15));
        J10.setName("J10"); // NOI18N

        javax.swing.GroupLayout J10Layout = new javax.swing.GroupLayout(J10);
        J10.setLayout(J10Layout);
        J10Layout.setHorizontalGroup(
            J10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );
        J10Layout.setVerticalGroup(
            J10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        J11.setBackground(resourceMap.getColor("J11.background")); // NOI18N
        J11.setBorder(javax.swing.BorderFactory.createLineBorder(resourceMap.getColor("J11.border.lineColor"))); // NOI18N
        J11.setMaximumSize(new java.awt.Dimension(15, 15));
        J11.setMinimumSize(new java.awt.Dimension(15, 15));
        J11.setName("J11"); // NOI18N

        javax.swing.GroupLayout J11Layout = new javax.swing.GroupLayout(J11);
        J11.setLayout(J11Layout);
        J11Layout.setHorizontalGroup(
            J11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );
        J11Layout.setVerticalGroup(
            J11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        J12.setBackground(resourceMap.getColor("J12.background")); // NOI18N
        J12.setBorder(javax.swing.BorderFactory.createLineBorder(resourceMap.getColor("J12.border.lineColor"))); // NOI18N
        J12.setMaximumSize(new java.awt.Dimension(15, 15));
        J12.setMinimumSize(new java.awt.Dimension(15, 15));
        J12.setName("J12"); // NOI18N

        javax.swing.GroupLayout J12Layout = new javax.swing.GroupLayout(J12);
        J12.setLayout(J12Layout);
        J12Layout.setHorizontalGroup(
            J12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );
        J12Layout.setVerticalGroup(
            J12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        J13.setBackground(resourceMap.getColor("J13.background")); // NOI18N
        J13.setBorder(javax.swing.BorderFactory.createLineBorder(resourceMap.getColor("J13.border.lineColor"))); // NOI18N
        J13.setMaximumSize(new java.awt.Dimension(15, 15));
        J13.setMinimumSize(new java.awt.Dimension(15, 15));
        J13.setName("J13"); // NOI18N

        javax.swing.GroupLayout J13Layout = new javax.swing.GroupLayout(J13);
        J13.setLayout(J13Layout);
        J13Layout.setHorizontalGroup(
            J13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );
        J13Layout.setVerticalGroup(
            J13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        J14.setBackground(resourceMap.getColor("J14.background")); // NOI18N
        J14.setBorder(javax.swing.BorderFactory.createLineBorder(resourceMap.getColor("J14.border.lineColor"))); // NOI18N
        J14.setMaximumSize(new java.awt.Dimension(15, 15));
        J14.setMinimumSize(new java.awt.Dimension(15, 15));
        J14.setName("J14"); // NOI18N

        javax.swing.GroupLayout J14Layout = new javax.swing.GroupLayout(J14);
        J14.setLayout(J14Layout);
        J14Layout.setHorizontalGroup(
            J14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );
        J14Layout.setVerticalGroup(
            J14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        J15.setBackground(resourceMap.getColor("J15.background")); // NOI18N
        J15.setBorder(javax.swing.BorderFactory.createLineBorder(resourceMap.getColor("J15.border.lineColor"))); // NOI18N
        J15.setMaximumSize(new java.awt.Dimension(15, 15));
        J15.setMinimumSize(new java.awt.Dimension(15, 15));
        J15.setName("J15"); // NOI18N

        javax.swing.GroupLayout J15Layout = new javax.swing.GroupLayout(J15);
        J15.setLayout(J15Layout);
        J15Layout.setHorizontalGroup(
            J15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );
        J15Layout.setVerticalGroup(
            J15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        J16.setBackground(resourceMap.getColor("J16.background")); // NOI18N
        J16.setBorder(javax.swing.BorderFactory.createLineBorder(resourceMap.getColor("J16.border.lineColor"))); // NOI18N
        J16.setMaximumSize(new java.awt.Dimension(15, 15));
        J16.setMinimumSize(new java.awt.Dimension(15, 15));
        J16.setName("J16"); // NOI18N

        javax.swing.GroupLayout J16Layout = new javax.swing.GroupLayout(J16);
        J16.setLayout(J16Layout);
        J16Layout.setHorizontalGroup(
            J16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );
        J16Layout.setVerticalGroup(
            J16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        J17.setBackground(resourceMap.getColor("J17.background")); // NOI18N
        J17.setBorder(javax.swing.BorderFactory.createLineBorder(resourceMap.getColor("J17.border.lineColor"))); // NOI18N
        J17.setMaximumSize(new java.awt.Dimension(15, 15));
        J17.setMinimumSize(new java.awt.Dimension(15, 15));
        J17.setName("J17"); // NOI18N

        javax.swing.GroupLayout J17Layout = new javax.swing.GroupLayout(J17);
        J17.setLayout(J17Layout);
        J17Layout.setHorizontalGroup(
            J17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );
        J17Layout.setVerticalGroup(
            J17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        J18.setBackground(resourceMap.getColor("J18.background")); // NOI18N
        J18.setBorder(javax.swing.BorderFactory.createLineBorder(resourceMap.getColor("J18.border.lineColor"))); // NOI18N
        J18.setMaximumSize(new java.awt.Dimension(15, 15));
        J18.setMinimumSize(new java.awt.Dimension(15, 15));
        J18.setName("J18"); // NOI18N

        javax.swing.GroupLayout J18Layout = new javax.swing.GroupLayout(J18);
        J18.setLayout(J18Layout);
        J18Layout.setHorizontalGroup(
            J18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );
        J18Layout.setVerticalGroup(
            J18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        J19.setBackground(resourceMap.getColor("J19.background")); // NOI18N
        J19.setBorder(javax.swing.BorderFactory.createLineBorder(resourceMap.getColor("J19.border.lineColor"))); // NOI18N
        J19.setMaximumSize(new java.awt.Dimension(15, 15));
        J19.setMinimumSize(new java.awt.Dimension(15, 15));
        J19.setName("J19"); // NOI18N

        javax.swing.GroupLayout J19Layout = new javax.swing.GroupLayout(J19);
        J19.setLayout(J19Layout);
        J19Layout.setHorizontalGroup(
            J19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );
        J19Layout.setVerticalGroup(
            J19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        J20.setBackground(resourceMap.getColor("J20.background")); // NOI18N
        J20.setBorder(javax.swing.BorderFactory.createLineBorder(resourceMap.getColor("J20.border.lineColor"))); // NOI18N
        J20.setMaximumSize(new java.awt.Dimension(15, 15));
        J20.setMinimumSize(new java.awt.Dimension(15, 15));
        J20.setName("J20"); // NOI18N

        javax.swing.GroupLayout J20Layout = new javax.swing.GroupLayout(J20);
        J20.setLayout(J20Layout);
        J20Layout.setHorizontalGroup(
            J20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );
        J20Layout.setVerticalGroup(
            J20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        K1.setBackground(resourceMap.getColor("K1.background")); // NOI18N
        K1.setBorder(javax.swing.BorderFactory.createLineBorder(resourceMap.getColor("K1.border.lineColor"))); // NOI18N
        K1.setMaximumSize(new java.awt.Dimension(15, 15));
        K1.setMinimumSize(new java.awt.Dimension(15, 15));
        K1.setName("K1"); // NOI18N

        javax.swing.GroupLayout K1Layout = new javax.swing.GroupLayout(K1);
        K1.setLayout(K1Layout);
        K1Layout.setHorizontalGroup(
            K1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );
        K1Layout.setVerticalGroup(
            K1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        K3.setBackground(resourceMap.getColor("K3.background")); // NOI18N
        K3.setBorder(javax.swing.BorderFactory.createLineBorder(resourceMap.getColor("K3.border.lineColor"))); // NOI18N
        K3.setMaximumSize(new java.awt.Dimension(15, 15));
        K3.setMinimumSize(new java.awt.Dimension(15, 15));
        K3.setName("K3"); // NOI18N

        javax.swing.GroupLayout K3Layout = new javax.swing.GroupLayout(K3);
        K3.setLayout(K3Layout);
        K3Layout.setHorizontalGroup(
            K3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );
        K3Layout.setVerticalGroup(
            K3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        K2.setBackground(resourceMap.getColor("K2.background")); // NOI18N
        K2.setBorder(javax.swing.BorderFactory.createLineBorder(resourceMap.getColor("K2.border.lineColor"))); // NOI18N
        K2.setMaximumSize(new java.awt.Dimension(15, 15));
        K2.setMinimumSize(new java.awt.Dimension(15, 15));
        K2.setName("K2"); // NOI18N

        javax.swing.GroupLayout K2Layout = new javax.swing.GroupLayout(K2);
        K2.setLayout(K2Layout);
        K2Layout.setHorizontalGroup(
            K2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );
        K2Layout.setVerticalGroup(
            K2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        K4.setBackground(resourceMap.getColor("K4.background")); // NOI18N
        K4.setBorder(javax.swing.BorderFactory.createLineBorder(resourceMap.getColor("K4.border.lineColor"))); // NOI18N
        K4.setMaximumSize(new java.awt.Dimension(15, 15));
        K4.setMinimumSize(new java.awt.Dimension(15, 15));
        K4.setName("K4"); // NOI18N

        javax.swing.GroupLayout K4Layout = new javax.swing.GroupLayout(K4);
        K4.setLayout(K4Layout);
        K4Layout.setHorizontalGroup(
            K4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );
        K4Layout.setVerticalGroup(
            K4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        K5.setBackground(resourceMap.getColor("K5.background")); // NOI18N
        K5.setBorder(javax.swing.BorderFactory.createLineBorder(resourceMap.getColor("K5.border.lineColor"))); // NOI18N
        K5.setMaximumSize(new java.awt.Dimension(15, 15));
        K5.setMinimumSize(new java.awt.Dimension(15, 15));
        K5.setName("K5"); // NOI18N

        javax.swing.GroupLayout K5Layout = new javax.swing.GroupLayout(K5);
        K5.setLayout(K5Layout);
        K5Layout.setHorizontalGroup(
            K5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );
        K5Layout.setVerticalGroup(
            K5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        K6.setBackground(resourceMap.getColor("K6.background")); // NOI18N
        K6.setBorder(javax.swing.BorderFactory.createLineBorder(resourceMap.getColor("K6.border.lineColor"))); // NOI18N
        K6.setMaximumSize(new java.awt.Dimension(15, 15));
        K6.setMinimumSize(new java.awt.Dimension(15, 15));
        K6.setName("K6"); // NOI18N

        javax.swing.GroupLayout K6Layout = new javax.swing.GroupLayout(K6);
        K6.setLayout(K6Layout);
        K6Layout.setHorizontalGroup(
            K6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );
        K6Layout.setVerticalGroup(
            K6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        K7.setBackground(resourceMap.getColor("K7.background")); // NOI18N
        K7.setBorder(javax.swing.BorderFactory.createLineBorder(resourceMap.getColor("K7.border.lineColor"))); // NOI18N
        K7.setMaximumSize(new java.awt.Dimension(15, 15));
        K7.setMinimumSize(new java.awt.Dimension(15, 15));
        K7.setName("K7"); // NOI18N

        javax.swing.GroupLayout K7Layout = new javax.swing.GroupLayout(K7);
        K7.setLayout(K7Layout);
        K7Layout.setHorizontalGroup(
            K7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );
        K7Layout.setVerticalGroup(
            K7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        K8.setBackground(resourceMap.getColor("K8.background")); // NOI18N
        K8.setBorder(javax.swing.BorderFactory.createLineBorder(resourceMap.getColor("K8.border.lineColor"))); // NOI18N
        K8.setMaximumSize(new java.awt.Dimension(15, 15));
        K8.setMinimumSize(new java.awt.Dimension(15, 15));
        K8.setName("K8"); // NOI18N

        javax.swing.GroupLayout K8Layout = new javax.swing.GroupLayout(K8);
        K8.setLayout(K8Layout);
        K8Layout.setHorizontalGroup(
            K8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );
        K8Layout.setVerticalGroup(
            K8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        K9.setBackground(resourceMap.getColor("K9.background")); // NOI18N
        K9.setBorder(javax.swing.BorderFactory.createLineBorder(resourceMap.getColor("K9.border.lineColor"))); // NOI18N
        K9.setMaximumSize(new java.awt.Dimension(15, 15));
        K9.setMinimumSize(new java.awt.Dimension(15, 15));
        K9.setName("K9"); // NOI18N

        javax.swing.GroupLayout K9Layout = new javax.swing.GroupLayout(K9);
        K9.setLayout(K9Layout);
        K9Layout.setHorizontalGroup(
            K9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );
        K9Layout.setVerticalGroup(
            K9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        K10.setBackground(resourceMap.getColor("K10.background")); // NOI18N
        K10.setBorder(javax.swing.BorderFactory.createLineBorder(resourceMap.getColor("K10.border.lineColor"))); // NOI18N
        K10.setMaximumSize(new java.awt.Dimension(15, 15));
        K10.setMinimumSize(new java.awt.Dimension(15, 15));
        K10.setName("K10"); // NOI18N

        javax.swing.GroupLayout K10Layout = new javax.swing.GroupLayout(K10);
        K10.setLayout(K10Layout);
        K10Layout.setHorizontalGroup(
            K10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );
        K10Layout.setVerticalGroup(
            K10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        K11.setBackground(resourceMap.getColor("K11.background")); // NOI18N
        K11.setBorder(javax.swing.BorderFactory.createLineBorder(resourceMap.getColor("K11.border.lineColor"))); // NOI18N
        K11.setMaximumSize(new java.awt.Dimension(15, 15));
        K11.setMinimumSize(new java.awt.Dimension(15, 15));
        K11.setName("K11"); // NOI18N

        javax.swing.GroupLayout K11Layout = new javax.swing.GroupLayout(K11);
        K11.setLayout(K11Layout);
        K11Layout.setHorizontalGroup(
            K11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );
        K11Layout.setVerticalGroup(
            K11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        K12.setBackground(resourceMap.getColor("K12.background")); // NOI18N
        K12.setBorder(javax.swing.BorderFactory.createLineBorder(resourceMap.getColor("K12.border.lineColor"))); // NOI18N
        K12.setMaximumSize(new java.awt.Dimension(15, 15));
        K12.setMinimumSize(new java.awt.Dimension(15, 15));
        K12.setName("K12"); // NOI18N

        javax.swing.GroupLayout K12Layout = new javax.swing.GroupLayout(K12);
        K12.setLayout(K12Layout);
        K12Layout.setHorizontalGroup(
            K12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );
        K12Layout.setVerticalGroup(
            K12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        K13.setBackground(resourceMap.getColor("K13.background")); // NOI18N
        K13.setBorder(javax.swing.BorderFactory.createLineBorder(resourceMap.getColor("K13.border.lineColor"))); // NOI18N
        K13.setMaximumSize(new java.awt.Dimension(15, 15));
        K13.setMinimumSize(new java.awt.Dimension(15, 15));
        K13.setName("K13"); // NOI18N

        javax.swing.GroupLayout K13Layout = new javax.swing.GroupLayout(K13);
        K13.setLayout(K13Layout);
        K13Layout.setHorizontalGroup(
            K13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );
        K13Layout.setVerticalGroup(
            K13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        K14.setBackground(resourceMap.getColor("K14.background")); // NOI18N
        K14.setBorder(javax.swing.BorderFactory.createLineBorder(resourceMap.getColor("K14.border.lineColor"))); // NOI18N
        K14.setMaximumSize(new java.awt.Dimension(15, 15));
        K14.setMinimumSize(new java.awt.Dimension(15, 15));
        K14.setName("K14"); // NOI18N

        javax.swing.GroupLayout K14Layout = new javax.swing.GroupLayout(K14);
        K14.setLayout(K14Layout);
        K14Layout.setHorizontalGroup(
            K14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );
        K14Layout.setVerticalGroup(
            K14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        K15.setBackground(resourceMap.getColor("K15.background")); // NOI18N
        K15.setBorder(javax.swing.BorderFactory.createLineBorder(resourceMap.getColor("K15.border.lineColor"))); // NOI18N
        K15.setMaximumSize(new java.awt.Dimension(15, 15));
        K15.setMinimumSize(new java.awt.Dimension(15, 15));
        K15.setName("K15"); // NOI18N

        javax.swing.GroupLayout K15Layout = new javax.swing.GroupLayout(K15);
        K15.setLayout(K15Layout);
        K15Layout.setHorizontalGroup(
            K15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );
        K15Layout.setVerticalGroup(
            K15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        K16.setBackground(resourceMap.getColor("K16.background")); // NOI18N
        K16.setBorder(javax.swing.BorderFactory.createLineBorder(resourceMap.getColor("K16.border.lineColor"))); // NOI18N
        K16.setMaximumSize(new java.awt.Dimension(15, 15));
        K16.setMinimumSize(new java.awt.Dimension(15, 15));
        K16.setName("K16"); // NOI18N

        javax.swing.GroupLayout K16Layout = new javax.swing.GroupLayout(K16);
        K16.setLayout(K16Layout);
        K16Layout.setHorizontalGroup(
            K16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );
        K16Layout.setVerticalGroup(
            K16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        K17.setBackground(resourceMap.getColor("K17.background")); // NOI18N
        K17.setBorder(javax.swing.BorderFactory.createLineBorder(resourceMap.getColor("K17.border.lineColor"))); // NOI18N
        K17.setMaximumSize(new java.awt.Dimension(15, 15));
        K17.setMinimumSize(new java.awt.Dimension(15, 15));
        K17.setName("K17"); // NOI18N

        javax.swing.GroupLayout K17Layout = new javax.swing.GroupLayout(K17);
        K17.setLayout(K17Layout);
        K17Layout.setHorizontalGroup(
            K17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );
        K17Layout.setVerticalGroup(
            K17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        K18.setBackground(resourceMap.getColor("K18.background")); // NOI18N
        K18.setBorder(javax.swing.BorderFactory.createLineBorder(resourceMap.getColor("K18.border.lineColor"))); // NOI18N
        K18.setMaximumSize(new java.awt.Dimension(15, 15));
        K18.setMinimumSize(new java.awt.Dimension(15, 15));
        K18.setName("K18"); // NOI18N

        javax.swing.GroupLayout K18Layout = new javax.swing.GroupLayout(K18);
        K18.setLayout(K18Layout);
        K18Layout.setHorizontalGroup(
            K18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );
        K18Layout.setVerticalGroup(
            K18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        K19.setBackground(resourceMap.getColor("K19.background")); // NOI18N
        K19.setBorder(javax.swing.BorderFactory.createLineBorder(resourceMap.getColor("K19.border.lineColor"))); // NOI18N
        K19.setMaximumSize(new java.awt.Dimension(15, 15));
        K19.setMinimumSize(new java.awt.Dimension(15, 15));
        K19.setName("K19"); // NOI18N

        javax.swing.GroupLayout K19Layout = new javax.swing.GroupLayout(K19);
        K19.setLayout(K19Layout);
        K19Layout.setHorizontalGroup(
            K19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );
        K19Layout.setVerticalGroup(
            K19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        K20.setBackground(resourceMap.getColor("K20.background")); // NOI18N
        K20.setBorder(javax.swing.BorderFactory.createLineBorder(resourceMap.getColor("K20.border.lineColor"))); // NOI18N
        K20.setMaximumSize(new java.awt.Dimension(15, 15));
        K20.setMinimumSize(new java.awt.Dimension(15, 15));
        K20.setName("K20"); // NOI18N

        javax.swing.GroupLayout K20Layout = new javax.swing.GroupLayout(K20);
        K20.setLayout(K20Layout);
        K20Layout.setHorizontalGroup(
            K20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );
        K20Layout.setVerticalGroup(
            K20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        L1.setBackground(resourceMap.getColor("L1.background")); // NOI18N
        L1.setBorder(javax.swing.BorderFactory.createLineBorder(resourceMap.getColor("L1.border.lineColor"))); // NOI18N
        L1.setMaximumSize(new java.awt.Dimension(15, 15));
        L1.setMinimumSize(new java.awt.Dimension(15, 15));
        L1.setName("L1"); // NOI18N

        javax.swing.GroupLayout L1Layout = new javax.swing.GroupLayout(L1);
        L1.setLayout(L1Layout);
        L1Layout.setHorizontalGroup(
            L1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );
        L1Layout.setVerticalGroup(
            L1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        L3.setBackground(resourceMap.getColor("L3.background")); // NOI18N
        L3.setBorder(javax.swing.BorderFactory.createLineBorder(resourceMap.getColor("L3.border.lineColor"))); // NOI18N
        L3.setMaximumSize(new java.awt.Dimension(15, 15));
        L3.setMinimumSize(new java.awt.Dimension(15, 15));
        L3.setName("L3"); // NOI18N

        javax.swing.GroupLayout L3Layout = new javax.swing.GroupLayout(L3);
        L3.setLayout(L3Layout);
        L3Layout.setHorizontalGroup(
            L3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );
        L3Layout.setVerticalGroup(
            L3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        L2.setBackground(resourceMap.getColor("L2.background")); // NOI18N
        L2.setBorder(javax.swing.BorderFactory.createLineBorder(resourceMap.getColor("L2.border.lineColor"))); // NOI18N
        L2.setMaximumSize(new java.awt.Dimension(15, 15));
        L2.setMinimumSize(new java.awt.Dimension(15, 15));
        L2.setName("L2"); // NOI18N

        javax.swing.GroupLayout L2Layout = new javax.swing.GroupLayout(L2);
        L2.setLayout(L2Layout);
        L2Layout.setHorizontalGroup(
            L2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );
        L2Layout.setVerticalGroup(
            L2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        L4.setBackground(resourceMap.getColor("L4.background")); // NOI18N
        L4.setBorder(javax.swing.BorderFactory.createLineBorder(resourceMap.getColor("L4.border.lineColor"))); // NOI18N
        L4.setMaximumSize(new java.awt.Dimension(15, 15));
        L4.setMinimumSize(new java.awt.Dimension(15, 15));
        L4.setName("L4"); // NOI18N

        javax.swing.GroupLayout L4Layout = new javax.swing.GroupLayout(L4);
        L4.setLayout(L4Layout);
        L4Layout.setHorizontalGroup(
            L4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );
        L4Layout.setVerticalGroup(
            L4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        L5.setBackground(resourceMap.getColor("L5.background")); // NOI18N
        L5.setBorder(javax.swing.BorderFactory.createLineBorder(resourceMap.getColor("L5.border.lineColor"))); // NOI18N
        L5.setMaximumSize(new java.awt.Dimension(15, 15));
        L5.setMinimumSize(new java.awt.Dimension(15, 15));
        L5.setName("L5"); // NOI18N

        javax.swing.GroupLayout L5Layout = new javax.swing.GroupLayout(L5);
        L5.setLayout(L5Layout);
        L5Layout.setHorizontalGroup(
            L5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );
        L5Layout.setVerticalGroup(
            L5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        L6.setBackground(resourceMap.getColor("L6.background")); // NOI18N
        L6.setBorder(javax.swing.BorderFactory.createLineBorder(resourceMap.getColor("L6.border.lineColor"))); // NOI18N
        L6.setMaximumSize(new java.awt.Dimension(15, 15));
        L6.setMinimumSize(new java.awt.Dimension(15, 15));
        L6.setName("L6"); // NOI18N

        javax.swing.GroupLayout L6Layout = new javax.swing.GroupLayout(L6);
        L6.setLayout(L6Layout);
        L6Layout.setHorizontalGroup(
            L6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );
        L6Layout.setVerticalGroup(
            L6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        L7.setBackground(resourceMap.getColor("L7.background")); // NOI18N
        L7.setBorder(javax.swing.BorderFactory.createLineBorder(resourceMap.getColor("L7.border.lineColor"))); // NOI18N
        L7.setMaximumSize(new java.awt.Dimension(15, 15));
        L7.setMinimumSize(new java.awt.Dimension(15, 15));
        L7.setName("L7"); // NOI18N

        javax.swing.GroupLayout L7Layout = new javax.swing.GroupLayout(L7);
        L7.setLayout(L7Layout);
        L7Layout.setHorizontalGroup(
            L7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );
        L7Layout.setVerticalGroup(
            L7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        L8.setBackground(resourceMap.getColor("L8.background")); // NOI18N
        L8.setBorder(javax.swing.BorderFactory.createLineBorder(resourceMap.getColor("L8.border.lineColor"))); // NOI18N
        L8.setMaximumSize(new java.awt.Dimension(15, 15));
        L8.setMinimumSize(new java.awt.Dimension(15, 15));
        L8.setName("L8"); // NOI18N

        javax.swing.GroupLayout L8Layout = new javax.swing.GroupLayout(L8);
        L8.setLayout(L8Layout);
        L8Layout.setHorizontalGroup(
            L8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );
        L8Layout.setVerticalGroup(
            L8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        L9.setBackground(resourceMap.getColor("L9.background")); // NOI18N
        L9.setBorder(javax.swing.BorderFactory.createLineBorder(resourceMap.getColor("L9.border.lineColor"))); // NOI18N
        L9.setMaximumSize(new java.awt.Dimension(15, 15));
        L9.setMinimumSize(new java.awt.Dimension(15, 15));
        L9.setName("L9"); // NOI18N

        javax.swing.GroupLayout L9Layout = new javax.swing.GroupLayout(L9);
        L9.setLayout(L9Layout);
        L9Layout.setHorizontalGroup(
            L9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );
        L9Layout.setVerticalGroup(
            L9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        L10.setBackground(resourceMap.getColor("L10.background")); // NOI18N
        L10.setBorder(javax.swing.BorderFactory.createLineBorder(resourceMap.getColor("L10.border.lineColor"))); // NOI18N
        L10.setMaximumSize(new java.awt.Dimension(15, 15));
        L10.setMinimumSize(new java.awt.Dimension(15, 15));
        L10.setName("L10"); // NOI18N

        javax.swing.GroupLayout L10Layout = new javax.swing.GroupLayout(L10);
        L10.setLayout(L10Layout);
        L10Layout.setHorizontalGroup(
            L10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );
        L10Layout.setVerticalGroup(
            L10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        L11.setBackground(resourceMap.getColor("L11.background")); // NOI18N
        L11.setBorder(javax.swing.BorderFactory.createLineBorder(resourceMap.getColor("L11.border.lineColor"))); // NOI18N
        L11.setMaximumSize(new java.awt.Dimension(15, 15));
        L11.setMinimumSize(new java.awt.Dimension(15, 15));
        L11.setName("L11"); // NOI18N

        javax.swing.GroupLayout L11Layout = new javax.swing.GroupLayout(L11);
        L11.setLayout(L11Layout);
        L11Layout.setHorizontalGroup(
            L11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );
        L11Layout.setVerticalGroup(
            L11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        L12.setBackground(resourceMap.getColor("L12.background")); // NOI18N
        L12.setBorder(javax.swing.BorderFactory.createLineBorder(resourceMap.getColor("L12.border.lineColor"))); // NOI18N
        L12.setMaximumSize(new java.awt.Dimension(15, 15));
        L12.setMinimumSize(new java.awt.Dimension(15, 15));
        L12.setName("L12"); // NOI18N

        javax.swing.GroupLayout L12Layout = new javax.swing.GroupLayout(L12);
        L12.setLayout(L12Layout);
        L12Layout.setHorizontalGroup(
            L12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );
        L12Layout.setVerticalGroup(
            L12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        L13.setBackground(resourceMap.getColor("L13.background")); // NOI18N
        L13.setBorder(javax.swing.BorderFactory.createLineBorder(resourceMap.getColor("L13.border.lineColor"))); // NOI18N
        L13.setMaximumSize(new java.awt.Dimension(15, 15));
        L13.setMinimumSize(new java.awt.Dimension(15, 15));
        L13.setName("L13"); // NOI18N

        javax.swing.GroupLayout L13Layout = new javax.swing.GroupLayout(L13);
        L13.setLayout(L13Layout);
        L13Layout.setHorizontalGroup(
            L13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );
        L13Layout.setVerticalGroup(
            L13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        L14.setBackground(resourceMap.getColor("L14.background")); // NOI18N
        L14.setBorder(javax.swing.BorderFactory.createLineBorder(resourceMap.getColor("L14.border.lineColor"))); // NOI18N
        L14.setMaximumSize(new java.awt.Dimension(15, 15));
        L14.setMinimumSize(new java.awt.Dimension(15, 15));
        L14.setName("L14"); // NOI18N

        javax.swing.GroupLayout L14Layout = new javax.swing.GroupLayout(L14);
        L14.setLayout(L14Layout);
        L14Layout.setHorizontalGroup(
            L14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );
        L14Layout.setVerticalGroup(
            L14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        L15.setBackground(resourceMap.getColor("L15.background")); // NOI18N
        L15.setBorder(javax.swing.BorderFactory.createLineBorder(resourceMap.getColor("L15.border.lineColor"))); // NOI18N
        L15.setMaximumSize(new java.awt.Dimension(15, 15));
        L15.setMinimumSize(new java.awt.Dimension(15, 15));
        L15.setName("L15"); // NOI18N

        javax.swing.GroupLayout L15Layout = new javax.swing.GroupLayout(L15);
        L15.setLayout(L15Layout);
        L15Layout.setHorizontalGroup(
            L15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );
        L15Layout.setVerticalGroup(
            L15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        L16.setBackground(resourceMap.getColor("L16.background")); // NOI18N
        L16.setBorder(javax.swing.BorderFactory.createLineBorder(resourceMap.getColor("L16.border.lineColor"))); // NOI18N
        L16.setMaximumSize(new java.awt.Dimension(15, 15));
        L16.setMinimumSize(new java.awt.Dimension(15, 15));
        L16.setName("L16"); // NOI18N

        javax.swing.GroupLayout L16Layout = new javax.swing.GroupLayout(L16);
        L16.setLayout(L16Layout);
        L16Layout.setHorizontalGroup(
            L16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );
        L16Layout.setVerticalGroup(
            L16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        L17.setBackground(resourceMap.getColor("L17.background")); // NOI18N
        L17.setBorder(javax.swing.BorderFactory.createLineBorder(resourceMap.getColor("L17.border.lineColor"))); // NOI18N
        L17.setMaximumSize(new java.awt.Dimension(15, 15));
        L17.setMinimumSize(new java.awt.Dimension(15, 15));
        L17.setName("L17"); // NOI18N

        javax.swing.GroupLayout L17Layout = new javax.swing.GroupLayout(L17);
        L17.setLayout(L17Layout);
        L17Layout.setHorizontalGroup(
            L17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );
        L17Layout.setVerticalGroup(
            L17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        L18.setBackground(resourceMap.getColor("L18.background")); // NOI18N
        L18.setBorder(javax.swing.BorderFactory.createLineBorder(resourceMap.getColor("L18.border.lineColor"))); // NOI18N
        L18.setMaximumSize(new java.awt.Dimension(15, 15));
        L18.setMinimumSize(new java.awt.Dimension(15, 15));
        L18.setName("L18"); // NOI18N

        javax.swing.GroupLayout L18Layout = new javax.swing.GroupLayout(L18);
        L18.setLayout(L18Layout);
        L18Layout.setHorizontalGroup(
            L18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );
        L18Layout.setVerticalGroup(
            L18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        L19.setBackground(resourceMap.getColor("L19.background")); // NOI18N
        L19.setBorder(javax.swing.BorderFactory.createLineBorder(resourceMap.getColor("L19.border.lineColor"))); // NOI18N
        L19.setMaximumSize(new java.awt.Dimension(15, 15));
        L19.setMinimumSize(new java.awt.Dimension(15, 15));
        L19.setName("L19"); // NOI18N

        javax.swing.GroupLayout L19Layout = new javax.swing.GroupLayout(L19);
        L19.setLayout(L19Layout);
        L19Layout.setHorizontalGroup(
            L19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );
        L19Layout.setVerticalGroup(
            L19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        L20.setBackground(resourceMap.getColor("L20.background")); // NOI18N
        L20.setBorder(javax.swing.BorderFactory.createLineBorder(resourceMap.getColor("L20.border.lineColor"))); // NOI18N
        L20.setMaximumSize(new java.awt.Dimension(15, 15));
        L20.setMinimumSize(new java.awt.Dimension(15, 15));
        L20.setName("L20"); // NOI18N

        javax.swing.GroupLayout L20Layout = new javax.swing.GroupLayout(L20);
        L20.setLayout(L20Layout);
        L20Layout.setHorizontalGroup(
            L20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );
        L20Layout.setVerticalGroup(
            L20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        M1.setBackground(resourceMap.getColor("M1.background")); // NOI18N
        M1.setBorder(javax.swing.BorderFactory.createLineBorder(resourceMap.getColor("M1.border.lineColor"))); // NOI18N
        M1.setMaximumSize(new java.awt.Dimension(15, 15));
        M1.setMinimumSize(new java.awt.Dimension(15, 15));
        M1.setName("M1"); // NOI18N

        javax.swing.GroupLayout M1Layout = new javax.swing.GroupLayout(M1);
        M1.setLayout(M1Layout);
        M1Layout.setHorizontalGroup(
            M1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );
        M1Layout.setVerticalGroup(
            M1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        M3.setBackground(resourceMap.getColor("M3.background")); // NOI18N
        M3.setBorder(javax.swing.BorderFactory.createLineBorder(resourceMap.getColor("M3.border.lineColor"))); // NOI18N
        M3.setMaximumSize(new java.awt.Dimension(15, 15));
        M3.setMinimumSize(new java.awt.Dimension(15, 15));
        M3.setName("M3"); // NOI18N

        javax.swing.GroupLayout M3Layout = new javax.swing.GroupLayout(M3);
        M3.setLayout(M3Layout);
        M3Layout.setHorizontalGroup(
            M3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );
        M3Layout.setVerticalGroup(
            M3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        M2.setBackground(resourceMap.getColor("M2.background")); // NOI18N
        M2.setBorder(javax.swing.BorderFactory.createLineBorder(resourceMap.getColor("M2.border.lineColor"))); // NOI18N
        M2.setMaximumSize(new java.awt.Dimension(15, 15));
        M2.setMinimumSize(new java.awt.Dimension(15, 15));
        M2.setName("M2"); // NOI18N

        javax.swing.GroupLayout M2Layout = new javax.swing.GroupLayout(M2);
        M2.setLayout(M2Layout);
        M2Layout.setHorizontalGroup(
            M2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );
        M2Layout.setVerticalGroup(
            M2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        M4.setBackground(resourceMap.getColor("M4.background")); // NOI18N
        M4.setBorder(javax.swing.BorderFactory.createLineBorder(resourceMap.getColor("M4.border.lineColor"))); // NOI18N
        M4.setMaximumSize(new java.awt.Dimension(15, 15));
        M4.setMinimumSize(new java.awt.Dimension(15, 15));
        M4.setName("M4"); // NOI18N

        javax.swing.GroupLayout M4Layout = new javax.swing.GroupLayout(M4);
        M4.setLayout(M4Layout);
        M4Layout.setHorizontalGroup(
            M4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );
        M4Layout.setVerticalGroup(
            M4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        M5.setBackground(resourceMap.getColor("M5.background")); // NOI18N
        M5.setBorder(javax.swing.BorderFactory.createLineBorder(resourceMap.getColor("M5.border.lineColor"))); // NOI18N
        M5.setMaximumSize(new java.awt.Dimension(15, 15));
        M5.setMinimumSize(new java.awt.Dimension(15, 15));
        M5.setName("M5"); // NOI18N

        javax.swing.GroupLayout M5Layout = new javax.swing.GroupLayout(M5);
        M5.setLayout(M5Layout);
        M5Layout.setHorizontalGroup(
            M5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );
        M5Layout.setVerticalGroup(
            M5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        M6.setBackground(resourceMap.getColor("M6.background")); // NOI18N
        M6.setBorder(javax.swing.BorderFactory.createLineBorder(resourceMap.getColor("M6.border.lineColor"))); // NOI18N
        M6.setMaximumSize(new java.awt.Dimension(15, 15));
        M6.setMinimumSize(new java.awt.Dimension(15, 15));
        M6.setName("M6"); // NOI18N

        javax.swing.GroupLayout M6Layout = new javax.swing.GroupLayout(M6);
        M6.setLayout(M6Layout);
        M6Layout.setHorizontalGroup(
            M6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );
        M6Layout.setVerticalGroup(
            M6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        M7.setBackground(resourceMap.getColor("M7.background")); // NOI18N
        M7.setBorder(javax.swing.BorderFactory.createLineBorder(resourceMap.getColor("M7.border.lineColor"))); // NOI18N
        M7.setMaximumSize(new java.awt.Dimension(15, 15));
        M7.setMinimumSize(new java.awt.Dimension(15, 15));
        M7.setName("M7"); // NOI18N

        javax.swing.GroupLayout M7Layout = new javax.swing.GroupLayout(M7);
        M7.setLayout(M7Layout);
        M7Layout.setHorizontalGroup(
            M7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );
        M7Layout.setVerticalGroup(
            M7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        M8.setBackground(resourceMap.getColor("M8.background")); // NOI18N
        M8.setBorder(javax.swing.BorderFactory.createLineBorder(resourceMap.getColor("M8.border.lineColor"))); // NOI18N
        M8.setMaximumSize(new java.awt.Dimension(15, 15));
        M8.setMinimumSize(new java.awt.Dimension(15, 15));
        M8.setName("M8"); // NOI18N

        javax.swing.GroupLayout M8Layout = new javax.swing.GroupLayout(M8);
        M8.setLayout(M8Layout);
        M8Layout.setHorizontalGroup(
            M8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );
        M8Layout.setVerticalGroup(
            M8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        M9.setBackground(resourceMap.getColor("M9.background")); // NOI18N
        M9.setBorder(javax.swing.BorderFactory.createLineBorder(resourceMap.getColor("M9.border.lineColor"))); // NOI18N
        M9.setMaximumSize(new java.awt.Dimension(15, 15));
        M9.setMinimumSize(new java.awt.Dimension(15, 15));
        M9.setName("M9"); // NOI18N

        javax.swing.GroupLayout M9Layout = new javax.swing.GroupLayout(M9);
        M9.setLayout(M9Layout);
        M9Layout.setHorizontalGroup(
            M9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );
        M9Layout.setVerticalGroup(
            M9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        M10.setBackground(resourceMap.getColor("M10.background")); // NOI18N
        M10.setBorder(javax.swing.BorderFactory.createLineBorder(resourceMap.getColor("M10.border.lineColor"))); // NOI18N
        M10.setMaximumSize(new java.awt.Dimension(15, 15));
        M10.setMinimumSize(new java.awt.Dimension(15, 15));
        M10.setName("M10"); // NOI18N

        javax.swing.GroupLayout M10Layout = new javax.swing.GroupLayout(M10);
        M10.setLayout(M10Layout);
        M10Layout.setHorizontalGroup(
            M10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );
        M10Layout.setVerticalGroup(
            M10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        M11.setBackground(resourceMap.getColor("M11.background")); // NOI18N
        M11.setBorder(javax.swing.BorderFactory.createLineBorder(resourceMap.getColor("M11.border.lineColor"))); // NOI18N
        M11.setMaximumSize(new java.awt.Dimension(15, 15));
        M11.setMinimumSize(new java.awt.Dimension(15, 15));
        M11.setName("M11"); // NOI18N

        javax.swing.GroupLayout M11Layout = new javax.swing.GroupLayout(M11);
        M11.setLayout(M11Layout);
        M11Layout.setHorizontalGroup(
            M11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );
        M11Layout.setVerticalGroup(
            M11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        M12.setBackground(resourceMap.getColor("M12.background")); // NOI18N
        M12.setBorder(javax.swing.BorderFactory.createLineBorder(resourceMap.getColor("M12.border.lineColor"))); // NOI18N
        M12.setMaximumSize(new java.awt.Dimension(15, 15));
        M12.setMinimumSize(new java.awt.Dimension(15, 15));
        M12.setName("M12"); // NOI18N

        javax.swing.GroupLayout M12Layout = new javax.swing.GroupLayout(M12);
        M12.setLayout(M12Layout);
        M12Layout.setHorizontalGroup(
            M12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );
        M12Layout.setVerticalGroup(
            M12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        M13.setBackground(resourceMap.getColor("M13.background")); // NOI18N
        M13.setBorder(javax.swing.BorderFactory.createLineBorder(resourceMap.getColor("M13.border.lineColor"))); // NOI18N
        M13.setMaximumSize(new java.awt.Dimension(15, 15));
        M13.setMinimumSize(new java.awt.Dimension(15, 15));
        M13.setName("M13"); // NOI18N

        javax.swing.GroupLayout M13Layout = new javax.swing.GroupLayout(M13);
        M13.setLayout(M13Layout);
        M13Layout.setHorizontalGroup(
            M13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );
        M13Layout.setVerticalGroup(
            M13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        M14.setBackground(resourceMap.getColor("M14.background")); // NOI18N
        M14.setBorder(javax.swing.BorderFactory.createLineBorder(resourceMap.getColor("M14.border.lineColor"))); // NOI18N
        M14.setMaximumSize(new java.awt.Dimension(15, 15));
        M14.setMinimumSize(new java.awt.Dimension(15, 15));
        M14.setName("M14"); // NOI18N

        javax.swing.GroupLayout M14Layout = new javax.swing.GroupLayout(M14);
        M14.setLayout(M14Layout);
        M14Layout.setHorizontalGroup(
            M14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );
        M14Layout.setVerticalGroup(
            M14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        M15.setBackground(resourceMap.getColor("M15.background")); // NOI18N
        M15.setBorder(javax.swing.BorderFactory.createLineBorder(resourceMap.getColor("M15.border.lineColor"))); // NOI18N
        M15.setMaximumSize(new java.awt.Dimension(15, 15));
        M15.setMinimumSize(new java.awt.Dimension(15, 15));
        M15.setName("M15"); // NOI18N

        javax.swing.GroupLayout M15Layout = new javax.swing.GroupLayout(M15);
        M15.setLayout(M15Layout);
        M15Layout.setHorizontalGroup(
            M15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );
        M15Layout.setVerticalGroup(
            M15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        M16.setBackground(resourceMap.getColor("M16.background")); // NOI18N
        M16.setBorder(javax.swing.BorderFactory.createLineBorder(resourceMap.getColor("M16.border.lineColor"))); // NOI18N
        M16.setMaximumSize(new java.awt.Dimension(15, 15));
        M16.setMinimumSize(new java.awt.Dimension(15, 15));
        M16.setName("M16"); // NOI18N

        javax.swing.GroupLayout M16Layout = new javax.swing.GroupLayout(M16);
        M16.setLayout(M16Layout);
        M16Layout.setHorizontalGroup(
            M16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );
        M16Layout.setVerticalGroup(
            M16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        M17.setBackground(resourceMap.getColor("M17.background")); // NOI18N
        M17.setBorder(javax.swing.BorderFactory.createLineBorder(resourceMap.getColor("M17.border.lineColor"))); // NOI18N
        M17.setMaximumSize(new java.awt.Dimension(15, 15));
        M17.setMinimumSize(new java.awt.Dimension(15, 15));
        M17.setName("M17"); // NOI18N

        javax.swing.GroupLayout M17Layout = new javax.swing.GroupLayout(M17);
        M17.setLayout(M17Layout);
        M17Layout.setHorizontalGroup(
            M17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );
        M17Layout.setVerticalGroup(
            M17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        M18.setBackground(resourceMap.getColor("M18.background")); // NOI18N
        M18.setBorder(javax.swing.BorderFactory.createLineBorder(resourceMap.getColor("M18.border.lineColor"))); // NOI18N
        M18.setMaximumSize(new java.awt.Dimension(15, 15));
        M18.setMinimumSize(new java.awt.Dimension(15, 15));
        M18.setName("M18"); // NOI18N

        javax.swing.GroupLayout M18Layout = new javax.swing.GroupLayout(M18);
        M18.setLayout(M18Layout);
        M18Layout.setHorizontalGroup(
            M18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );
        M18Layout.setVerticalGroup(
            M18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        M19.setBackground(resourceMap.getColor("M19.background")); // NOI18N
        M19.setBorder(javax.swing.BorderFactory.createLineBorder(resourceMap.getColor("M19.border.lineColor"))); // NOI18N
        M19.setMaximumSize(new java.awt.Dimension(15, 15));
        M19.setMinimumSize(new java.awt.Dimension(15, 15));
        M19.setName("M19"); // NOI18N

        javax.swing.GroupLayout M19Layout = new javax.swing.GroupLayout(M19);
        M19.setLayout(M19Layout);
        M19Layout.setHorizontalGroup(
            M19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );
        M19Layout.setVerticalGroup(
            M19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        M20.setBackground(resourceMap.getColor("M20.background")); // NOI18N
        M20.setBorder(javax.swing.BorderFactory.createLineBorder(resourceMap.getColor("M20.border.lineColor"))); // NOI18N
        M20.setMaximumSize(new java.awt.Dimension(15, 15));
        M20.setMinimumSize(new java.awt.Dimension(15, 15));
        M20.setName("M20"); // NOI18N

        javax.swing.GroupLayout M20Layout = new javax.swing.GroupLayout(M20);
        M20.setLayout(M20Layout);
        M20Layout.setHorizontalGroup(
            M20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );
        M20Layout.setVerticalGroup(
            M20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        N1.setBackground(resourceMap.getColor("N1.background")); // NOI18N
        N1.setBorder(javax.swing.BorderFactory.createLineBorder(resourceMap.getColor("N1.border.lineColor"))); // NOI18N
        N1.setMaximumSize(new java.awt.Dimension(15, 15));
        N1.setMinimumSize(new java.awt.Dimension(15, 15));
        N1.setName("N1"); // NOI18N

        javax.swing.GroupLayout N1Layout = new javax.swing.GroupLayout(N1);
        N1.setLayout(N1Layout);
        N1Layout.setHorizontalGroup(
            N1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );
        N1Layout.setVerticalGroup(
            N1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        N2.setBackground(resourceMap.getColor("N2.background")); // NOI18N
        N2.setBorder(javax.swing.BorderFactory.createLineBorder(resourceMap.getColor("N2.border.lineColor"))); // NOI18N
        N2.setMaximumSize(new java.awt.Dimension(15, 15));
        N2.setMinimumSize(new java.awt.Dimension(15, 15));
        N2.setName("N2"); // NOI18N

        javax.swing.GroupLayout N2Layout = new javax.swing.GroupLayout(N2);
        N2.setLayout(N2Layout);
        N2Layout.setHorizontalGroup(
            N2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );
        N2Layout.setVerticalGroup(
            N2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        N3.setBackground(resourceMap.getColor("N3.background")); // NOI18N
        N3.setBorder(javax.swing.BorderFactory.createLineBorder(resourceMap.getColor("N3.border.lineColor"))); // NOI18N
        N3.setMaximumSize(new java.awt.Dimension(15, 15));
        N3.setMinimumSize(new java.awt.Dimension(15, 15));
        N3.setName("N3"); // NOI18N

        javax.swing.GroupLayout N3Layout = new javax.swing.GroupLayout(N3);
        N3.setLayout(N3Layout);
        N3Layout.setHorizontalGroup(
            N3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );
        N3Layout.setVerticalGroup(
            N3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        N4.setBackground(resourceMap.getColor("N4.background")); // NOI18N
        N4.setBorder(javax.swing.BorderFactory.createLineBorder(resourceMap.getColor("N4.border.lineColor"))); // NOI18N
        N4.setMaximumSize(new java.awt.Dimension(15, 15));
        N4.setMinimumSize(new java.awt.Dimension(15, 15));
        N4.setName("N4"); // NOI18N

        javax.swing.GroupLayout N4Layout = new javax.swing.GroupLayout(N4);
        N4.setLayout(N4Layout);
        N4Layout.setHorizontalGroup(
            N4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );
        N4Layout.setVerticalGroup(
            N4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        N5.setBackground(resourceMap.getColor("N5.background")); // NOI18N
        N5.setBorder(javax.swing.BorderFactory.createLineBorder(resourceMap.getColor("N5.border.lineColor"))); // NOI18N
        N5.setMaximumSize(new java.awt.Dimension(15, 15));
        N5.setMinimumSize(new java.awt.Dimension(15, 15));
        N5.setName("N5"); // NOI18N

        javax.swing.GroupLayout N5Layout = new javax.swing.GroupLayout(N5);
        N5.setLayout(N5Layout);
        N5Layout.setHorizontalGroup(
            N5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );
        N5Layout.setVerticalGroup(
            N5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        N6.setBackground(resourceMap.getColor("N6.background")); // NOI18N
        N6.setBorder(javax.swing.BorderFactory.createLineBorder(resourceMap.getColor("N6.border.lineColor"))); // NOI18N
        N6.setMaximumSize(new java.awt.Dimension(15, 15));
        N6.setMinimumSize(new java.awt.Dimension(15, 15));
        N6.setName("N6"); // NOI18N

        javax.swing.GroupLayout N6Layout = new javax.swing.GroupLayout(N6);
        N6.setLayout(N6Layout);
        N6Layout.setHorizontalGroup(
            N6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );
        N6Layout.setVerticalGroup(
            N6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        N7.setBackground(resourceMap.getColor("N7.background")); // NOI18N
        N7.setBorder(javax.swing.BorderFactory.createLineBorder(resourceMap.getColor("N7.border.lineColor"))); // NOI18N
        N7.setMaximumSize(new java.awt.Dimension(15, 15));
        N7.setMinimumSize(new java.awt.Dimension(15, 15));
        N7.setName("N7"); // NOI18N

        javax.swing.GroupLayout N7Layout = new javax.swing.GroupLayout(N7);
        N7.setLayout(N7Layout);
        N7Layout.setHorizontalGroup(
            N7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );
        N7Layout.setVerticalGroup(
            N7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        N8.setBackground(resourceMap.getColor("N8.background")); // NOI18N
        N8.setBorder(javax.swing.BorderFactory.createLineBorder(resourceMap.getColor("N8.border.lineColor"))); // NOI18N
        N8.setMaximumSize(new java.awt.Dimension(15, 15));
        N8.setMinimumSize(new java.awt.Dimension(15, 15));
        N8.setName("N8"); // NOI18N

        javax.swing.GroupLayout N8Layout = new javax.swing.GroupLayout(N8);
        N8.setLayout(N8Layout);
        N8Layout.setHorizontalGroup(
            N8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );
        N8Layout.setVerticalGroup(
            N8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        N9.setBackground(resourceMap.getColor("N9.background")); // NOI18N
        N9.setBorder(javax.swing.BorderFactory.createLineBorder(resourceMap.getColor("N9.border.lineColor"))); // NOI18N
        N9.setMaximumSize(new java.awt.Dimension(15, 15));
        N9.setMinimumSize(new java.awt.Dimension(15, 15));
        N9.setName("N9"); // NOI18N

        javax.swing.GroupLayout N9Layout = new javax.swing.GroupLayout(N9);
        N9.setLayout(N9Layout);
        N9Layout.setHorizontalGroup(
            N9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );
        N9Layout.setVerticalGroup(
            N9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        N10.setBackground(resourceMap.getColor("N10.background")); // NOI18N
        N10.setBorder(javax.swing.BorderFactory.createLineBorder(resourceMap.getColor("N10.border.lineColor"))); // NOI18N
        N10.setMaximumSize(new java.awt.Dimension(15, 15));
        N10.setMinimumSize(new java.awt.Dimension(15, 15));
        N10.setName("N10"); // NOI18N

        javax.swing.GroupLayout N10Layout = new javax.swing.GroupLayout(N10);
        N10.setLayout(N10Layout);
        N10Layout.setHorizontalGroup(
            N10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );
        N10Layout.setVerticalGroup(
            N10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        N11.setBackground(resourceMap.getColor("N11.background")); // NOI18N
        N11.setBorder(javax.swing.BorderFactory.createLineBorder(resourceMap.getColor("N11.border.lineColor"))); // NOI18N
        N11.setMaximumSize(new java.awt.Dimension(15, 15));
        N11.setMinimumSize(new java.awt.Dimension(15, 15));
        N11.setName("N11"); // NOI18N

        javax.swing.GroupLayout N11Layout = new javax.swing.GroupLayout(N11);
        N11.setLayout(N11Layout);
        N11Layout.setHorizontalGroup(
            N11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );
        N11Layout.setVerticalGroup(
            N11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        N12.setBackground(resourceMap.getColor("N12.background")); // NOI18N
        N12.setBorder(javax.swing.BorderFactory.createLineBorder(resourceMap.getColor("N12.border.lineColor"))); // NOI18N
        N12.setMaximumSize(new java.awt.Dimension(15, 15));
        N12.setMinimumSize(new java.awt.Dimension(15, 15));
        N12.setName("N12"); // NOI18N

        javax.swing.GroupLayout N12Layout = new javax.swing.GroupLayout(N12);
        N12.setLayout(N12Layout);
        N12Layout.setHorizontalGroup(
            N12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );
        N12Layout.setVerticalGroup(
            N12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        N13.setBackground(resourceMap.getColor("N13.background")); // NOI18N
        N13.setBorder(javax.swing.BorderFactory.createLineBorder(resourceMap.getColor("N13.border.lineColor"))); // NOI18N
        N13.setMaximumSize(new java.awt.Dimension(15, 15));
        N13.setMinimumSize(new java.awt.Dimension(15, 15));
        N13.setName("N13"); // NOI18N

        javax.swing.GroupLayout N13Layout = new javax.swing.GroupLayout(N13);
        N13.setLayout(N13Layout);
        N13Layout.setHorizontalGroup(
            N13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );
        N13Layout.setVerticalGroup(
            N13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        N14.setBackground(resourceMap.getColor("N14.background")); // NOI18N
        N14.setBorder(javax.swing.BorderFactory.createLineBorder(resourceMap.getColor("N14.border.lineColor"))); // NOI18N
        N14.setMaximumSize(new java.awt.Dimension(15, 15));
        N14.setMinimumSize(new java.awt.Dimension(15, 15));
        N14.setName("N14"); // NOI18N

        javax.swing.GroupLayout N14Layout = new javax.swing.GroupLayout(N14);
        N14.setLayout(N14Layout);
        N14Layout.setHorizontalGroup(
            N14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );
        N14Layout.setVerticalGroup(
            N14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        N15.setBackground(resourceMap.getColor("N15.background")); // NOI18N
        N15.setBorder(javax.swing.BorderFactory.createLineBorder(resourceMap.getColor("N15.border.lineColor"))); // NOI18N
        N15.setMaximumSize(new java.awt.Dimension(15, 15));
        N15.setMinimumSize(new java.awt.Dimension(15, 15));
        N15.setName("N15"); // NOI18N

        javax.swing.GroupLayout N15Layout = new javax.swing.GroupLayout(N15);
        N15.setLayout(N15Layout);
        N15Layout.setHorizontalGroup(
            N15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );
        N15Layout.setVerticalGroup(
            N15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        N16.setBackground(resourceMap.getColor("N16.background")); // NOI18N
        N16.setBorder(javax.swing.BorderFactory.createLineBorder(resourceMap.getColor("N16.border.lineColor"))); // NOI18N
        N16.setMaximumSize(new java.awt.Dimension(15, 15));
        N16.setMinimumSize(new java.awt.Dimension(15, 15));
        N16.setName("N16"); // NOI18N

        javax.swing.GroupLayout N16Layout = new javax.swing.GroupLayout(N16);
        N16.setLayout(N16Layout);
        N16Layout.setHorizontalGroup(
            N16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );
        N16Layout.setVerticalGroup(
            N16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        N17.setBackground(resourceMap.getColor("N17.background")); // NOI18N
        N17.setBorder(javax.swing.BorderFactory.createLineBorder(resourceMap.getColor("N17.border.lineColor"))); // NOI18N
        N17.setMaximumSize(new java.awt.Dimension(15, 15));
        N17.setMinimumSize(new java.awt.Dimension(15, 15));
        N17.setName("N17"); // NOI18N

        javax.swing.GroupLayout N17Layout = new javax.swing.GroupLayout(N17);
        N17.setLayout(N17Layout);
        N17Layout.setHorizontalGroup(
            N17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );
        N17Layout.setVerticalGroup(
            N17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        N18.setBackground(resourceMap.getColor("N18.background")); // NOI18N
        N18.setBorder(javax.swing.BorderFactory.createLineBorder(resourceMap.getColor("N18.border.lineColor"))); // NOI18N
        N18.setMaximumSize(new java.awt.Dimension(15, 15));
        N18.setMinimumSize(new java.awt.Dimension(15, 15));
        N18.setName("N18"); // NOI18N

        javax.swing.GroupLayout N18Layout = new javax.swing.GroupLayout(N18);
        N18.setLayout(N18Layout);
        N18Layout.setHorizontalGroup(
            N18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );
        N18Layout.setVerticalGroup(
            N18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        N19.setBackground(resourceMap.getColor("N19.background")); // NOI18N
        N19.setBorder(javax.swing.BorderFactory.createLineBorder(resourceMap.getColor("N19.border.lineColor"))); // NOI18N
        N19.setMaximumSize(new java.awt.Dimension(15, 15));
        N19.setMinimumSize(new java.awt.Dimension(15, 15));
        N19.setName("N19"); // NOI18N

        javax.swing.GroupLayout N19Layout = new javax.swing.GroupLayout(N19);
        N19.setLayout(N19Layout);
        N19Layout.setHorizontalGroup(
            N19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );
        N19Layout.setVerticalGroup(
            N19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        N20.setBackground(resourceMap.getColor("N20.background")); // NOI18N
        N20.setBorder(javax.swing.BorderFactory.createLineBorder(resourceMap.getColor("N20.border.lineColor"))); // NOI18N
        N20.setMaximumSize(new java.awt.Dimension(15, 15));
        N20.setMinimumSize(new java.awt.Dimension(15, 15));
        N20.setName("N20"); // NOI18N

        javax.swing.GroupLayout N20Layout = new javax.swing.GroupLayout(N20);
        N20.setLayout(N20Layout);
        N20Layout.setHorizontalGroup(
            N20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );
        N20Layout.setVerticalGroup(
            N20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        O1.setBackground(resourceMap.getColor("O1.background")); // NOI18N
        O1.setBorder(javax.swing.BorderFactory.createLineBorder(resourceMap.getColor("O1.border.lineColor"))); // NOI18N
        O1.setMaximumSize(new java.awt.Dimension(15, 15));
        O1.setMinimumSize(new java.awt.Dimension(15, 15));
        O1.setName("O1"); // NOI18N

        javax.swing.GroupLayout O1Layout = new javax.swing.GroupLayout(O1);
        O1.setLayout(O1Layout);
        O1Layout.setHorizontalGroup(
            O1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );
        O1Layout.setVerticalGroup(
            O1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        O3.setBackground(resourceMap.getColor("O3.background")); // NOI18N
        O3.setBorder(javax.swing.BorderFactory.createLineBorder(resourceMap.getColor("O3.border.lineColor"))); // NOI18N
        O3.setMaximumSize(new java.awt.Dimension(15, 15));
        O3.setMinimumSize(new java.awt.Dimension(15, 15));
        O3.setName("O3"); // NOI18N

        javax.swing.GroupLayout O3Layout = new javax.swing.GroupLayout(O3);
        O3.setLayout(O3Layout);
        O3Layout.setHorizontalGroup(
            O3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );
        O3Layout.setVerticalGroup(
            O3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        O2.setBackground(resourceMap.getColor("O2.background")); // NOI18N
        O2.setBorder(javax.swing.BorderFactory.createLineBorder(resourceMap.getColor("O2.border.lineColor"))); // NOI18N
        O2.setMaximumSize(new java.awt.Dimension(15, 15));
        O2.setMinimumSize(new java.awt.Dimension(15, 15));
        O2.setName("O2"); // NOI18N

        javax.swing.GroupLayout O2Layout = new javax.swing.GroupLayout(O2);
        O2.setLayout(O2Layout);
        O2Layout.setHorizontalGroup(
            O2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );
        O2Layout.setVerticalGroup(
            O2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        O4.setBackground(resourceMap.getColor("O4.background")); // NOI18N
        O4.setBorder(javax.swing.BorderFactory.createLineBorder(resourceMap.getColor("O4.border.lineColor"))); // NOI18N
        O4.setMaximumSize(new java.awt.Dimension(15, 15));
        O4.setMinimumSize(new java.awt.Dimension(15, 15));
        O4.setName("O4"); // NOI18N

        javax.swing.GroupLayout O4Layout = new javax.swing.GroupLayout(O4);
        O4.setLayout(O4Layout);
        O4Layout.setHorizontalGroup(
            O4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );
        O4Layout.setVerticalGroup(
            O4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        O5.setBackground(resourceMap.getColor("O5.background")); // NOI18N
        O5.setBorder(javax.swing.BorderFactory.createLineBorder(resourceMap.getColor("O5.border.lineColor"))); // NOI18N
        O5.setMaximumSize(new java.awt.Dimension(15, 15));
        O5.setMinimumSize(new java.awt.Dimension(15, 15));
        O5.setName("O5"); // NOI18N

        javax.swing.GroupLayout O5Layout = new javax.swing.GroupLayout(O5);
        O5.setLayout(O5Layout);
        O5Layout.setHorizontalGroup(
            O5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );
        O5Layout.setVerticalGroup(
            O5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        O6.setBackground(resourceMap.getColor("O6.background")); // NOI18N
        O6.setBorder(javax.swing.BorderFactory.createLineBorder(resourceMap.getColor("O6.border.lineColor"))); // NOI18N
        O6.setMaximumSize(new java.awt.Dimension(15, 15));
        O6.setMinimumSize(new java.awt.Dimension(15, 15));
        O6.setName("O6"); // NOI18N

        javax.swing.GroupLayout O6Layout = new javax.swing.GroupLayout(O6);
        O6.setLayout(O6Layout);
        O6Layout.setHorizontalGroup(
            O6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );
        O6Layout.setVerticalGroup(
            O6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        O7.setBackground(resourceMap.getColor("O7.background")); // NOI18N
        O7.setBorder(javax.swing.BorderFactory.createLineBorder(resourceMap.getColor("O7.border.lineColor"))); // NOI18N
        O7.setMaximumSize(new java.awt.Dimension(15, 15));
        O7.setMinimumSize(new java.awt.Dimension(15, 15));
        O7.setName("O7"); // NOI18N

        javax.swing.GroupLayout O7Layout = new javax.swing.GroupLayout(O7);
        O7.setLayout(O7Layout);
        O7Layout.setHorizontalGroup(
            O7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );
        O7Layout.setVerticalGroup(
            O7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        O8.setBackground(resourceMap.getColor("O8.background")); // NOI18N
        O8.setBorder(javax.swing.BorderFactory.createLineBorder(resourceMap.getColor("O8.border.lineColor"))); // NOI18N
        O8.setMaximumSize(new java.awt.Dimension(15, 15));
        O8.setMinimumSize(new java.awt.Dimension(15, 15));
        O8.setName("O8"); // NOI18N

        javax.swing.GroupLayout O8Layout = new javax.swing.GroupLayout(O8);
        O8.setLayout(O8Layout);
        O8Layout.setHorizontalGroup(
            O8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );
        O8Layout.setVerticalGroup(
            O8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        O9.setBackground(resourceMap.getColor("O9.background")); // NOI18N
        O9.setBorder(javax.swing.BorderFactory.createLineBorder(resourceMap.getColor("O9.border.lineColor"))); // NOI18N
        O9.setMaximumSize(new java.awt.Dimension(15, 15));
        O9.setMinimumSize(new java.awt.Dimension(15, 15));
        O9.setName("O9"); // NOI18N

        javax.swing.GroupLayout O9Layout = new javax.swing.GroupLayout(O9);
        O9.setLayout(O9Layout);
        O9Layout.setHorizontalGroup(
            O9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );
        O9Layout.setVerticalGroup(
            O9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        O10.setBackground(resourceMap.getColor("O10.background")); // NOI18N
        O10.setBorder(javax.swing.BorderFactory.createLineBorder(resourceMap.getColor("O10.border.lineColor"))); // NOI18N
        O10.setMaximumSize(new java.awt.Dimension(15, 15));
        O10.setMinimumSize(new java.awt.Dimension(15, 15));
        O10.setName("O10"); // NOI18N

        javax.swing.GroupLayout O10Layout = new javax.swing.GroupLayout(O10);
        O10.setLayout(O10Layout);
        O10Layout.setHorizontalGroup(
            O10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );
        O10Layout.setVerticalGroup(
            O10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        O11.setBackground(resourceMap.getColor("O11.background")); // NOI18N
        O11.setBorder(javax.swing.BorderFactory.createLineBorder(resourceMap.getColor("O11.border.lineColor"))); // NOI18N
        O11.setMaximumSize(new java.awt.Dimension(15, 15));
        O11.setMinimumSize(new java.awt.Dimension(15, 15));
        O11.setName("O11"); // NOI18N

        javax.swing.GroupLayout O11Layout = new javax.swing.GroupLayout(O11);
        O11.setLayout(O11Layout);
        O11Layout.setHorizontalGroup(
            O11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );
        O11Layout.setVerticalGroup(
            O11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        O12.setBackground(resourceMap.getColor("O12.background")); // NOI18N
        O12.setBorder(javax.swing.BorderFactory.createLineBorder(resourceMap.getColor("O12.border.lineColor"))); // NOI18N
        O12.setMaximumSize(new java.awt.Dimension(15, 15));
        O12.setMinimumSize(new java.awt.Dimension(15, 15));
        O12.setName("O12"); // NOI18N

        javax.swing.GroupLayout O12Layout = new javax.swing.GroupLayout(O12);
        O12.setLayout(O12Layout);
        O12Layout.setHorizontalGroup(
            O12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );
        O12Layout.setVerticalGroup(
            O12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        O13.setBackground(resourceMap.getColor("O13.background")); // NOI18N
        O13.setBorder(javax.swing.BorderFactory.createLineBorder(resourceMap.getColor("O13.border.lineColor"))); // NOI18N
        O13.setMaximumSize(new java.awt.Dimension(15, 15));
        O13.setMinimumSize(new java.awt.Dimension(15, 15));
        O13.setName("O13"); // NOI18N

        javax.swing.GroupLayout O13Layout = new javax.swing.GroupLayout(O13);
        O13.setLayout(O13Layout);
        O13Layout.setHorizontalGroup(
            O13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );
        O13Layout.setVerticalGroup(
            O13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        O14.setBackground(resourceMap.getColor("O14.background")); // NOI18N
        O14.setBorder(javax.swing.BorderFactory.createLineBorder(resourceMap.getColor("O14.border.lineColor"))); // NOI18N
        O14.setMaximumSize(new java.awt.Dimension(15, 15));
        O14.setMinimumSize(new java.awt.Dimension(15, 15));
        O14.setName("O14"); // NOI18N

        javax.swing.GroupLayout O14Layout = new javax.swing.GroupLayout(O14);
        O14.setLayout(O14Layout);
        O14Layout.setHorizontalGroup(
            O14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );
        O14Layout.setVerticalGroup(
            O14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        O15.setBackground(resourceMap.getColor("O15.background")); // NOI18N
        O15.setBorder(javax.swing.BorderFactory.createLineBorder(resourceMap.getColor("O15.border.lineColor"))); // NOI18N
        O15.setMaximumSize(new java.awt.Dimension(15, 15));
        O15.setMinimumSize(new java.awt.Dimension(15, 15));
        O15.setName("O15"); // NOI18N

        javax.swing.GroupLayout O15Layout = new javax.swing.GroupLayout(O15);
        O15.setLayout(O15Layout);
        O15Layout.setHorizontalGroup(
            O15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );
        O15Layout.setVerticalGroup(
            O15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        O16.setBackground(resourceMap.getColor("O16.background")); // NOI18N
        O16.setBorder(javax.swing.BorderFactory.createLineBorder(resourceMap.getColor("O16.border.lineColor"))); // NOI18N
        O16.setMaximumSize(new java.awt.Dimension(15, 15));
        O16.setMinimumSize(new java.awt.Dimension(15, 15));
        O16.setName("O16"); // NOI18N

        javax.swing.GroupLayout O16Layout = new javax.swing.GroupLayout(O16);
        O16.setLayout(O16Layout);
        O16Layout.setHorizontalGroup(
            O16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );
        O16Layout.setVerticalGroup(
            O16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        O17.setBackground(resourceMap.getColor("O17.background")); // NOI18N
        O17.setBorder(javax.swing.BorderFactory.createLineBorder(resourceMap.getColor("O17.border.lineColor"))); // NOI18N
        O17.setMaximumSize(new java.awt.Dimension(15, 15));
        O17.setMinimumSize(new java.awt.Dimension(15, 15));
        O17.setName("O17"); // NOI18N

        javax.swing.GroupLayout O17Layout = new javax.swing.GroupLayout(O17);
        O17.setLayout(O17Layout);
        O17Layout.setHorizontalGroup(
            O17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );
        O17Layout.setVerticalGroup(
            O17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        O18.setBackground(resourceMap.getColor("O18.background")); // NOI18N
        O18.setBorder(javax.swing.BorderFactory.createLineBorder(resourceMap.getColor("O18.border.lineColor"))); // NOI18N
        O18.setMaximumSize(new java.awt.Dimension(15, 15));
        O18.setMinimumSize(new java.awt.Dimension(15, 15));
        O18.setName("O18"); // NOI18N

        javax.swing.GroupLayout O18Layout = new javax.swing.GroupLayout(O18);
        O18.setLayout(O18Layout);
        O18Layout.setHorizontalGroup(
            O18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );
        O18Layout.setVerticalGroup(
            O18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        O19.setBackground(resourceMap.getColor("O19.background")); // NOI18N
        O19.setBorder(javax.swing.BorderFactory.createLineBorder(resourceMap.getColor("O19.border.lineColor"))); // NOI18N
        O19.setMaximumSize(new java.awt.Dimension(15, 15));
        O19.setMinimumSize(new java.awt.Dimension(15, 15));
        O19.setName("O19"); // NOI18N

        javax.swing.GroupLayout O19Layout = new javax.swing.GroupLayout(O19);
        O19.setLayout(O19Layout);
        O19Layout.setHorizontalGroup(
            O19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );
        O19Layout.setVerticalGroup(
            O19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        O20.setBackground(resourceMap.getColor("O20.background")); // NOI18N
        O20.setBorder(javax.swing.BorderFactory.createLineBorder(resourceMap.getColor("O20.border.lineColor"))); // NOI18N
        O20.setMaximumSize(new java.awt.Dimension(15, 15));
        O20.setMinimumSize(new java.awt.Dimension(15, 15));
        O20.setName("O20"); // NOI18N

        javax.swing.GroupLayout O20Layout = new javax.swing.GroupLayout(O20);
        O20.setLayout(O20Layout);
        O20Layout.setHorizontalGroup(
            O20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );
        O20Layout.setVerticalGroup(
            O20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        P1.setBackground(resourceMap.getColor("P1.background")); // NOI18N
        P1.setBorder(javax.swing.BorderFactory.createLineBorder(resourceMap.getColor("P1.border.lineColor"))); // NOI18N
        P1.setMaximumSize(new java.awt.Dimension(15, 15));
        P1.setMinimumSize(new java.awt.Dimension(15, 15));
        P1.setName("P1"); // NOI18N

        javax.swing.GroupLayout P1Layout = new javax.swing.GroupLayout(P1);
        P1.setLayout(P1Layout);
        P1Layout.setHorizontalGroup(
            P1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );
        P1Layout.setVerticalGroup(
            P1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        P3.setBackground(resourceMap.getColor("P3.background")); // NOI18N
        P3.setBorder(javax.swing.BorderFactory.createLineBorder(resourceMap.getColor("P3.border.lineColor"))); // NOI18N
        P3.setMaximumSize(new java.awt.Dimension(15, 15));
        P3.setMinimumSize(new java.awt.Dimension(15, 15));
        P3.setName("P3"); // NOI18N

        javax.swing.GroupLayout P3Layout = new javax.swing.GroupLayout(P3);
        P3.setLayout(P3Layout);
        P3Layout.setHorizontalGroup(
            P3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );
        P3Layout.setVerticalGroup(
            P3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        P2.setBackground(resourceMap.getColor("P2.background")); // NOI18N
        P2.setBorder(javax.swing.BorderFactory.createLineBorder(resourceMap.getColor("P2.border.lineColor"))); // NOI18N
        P2.setMaximumSize(new java.awt.Dimension(15, 15));
        P2.setMinimumSize(new java.awt.Dimension(15, 15));
        P2.setName("P2"); // NOI18N

        javax.swing.GroupLayout P2Layout = new javax.swing.GroupLayout(P2);
        P2.setLayout(P2Layout);
        P2Layout.setHorizontalGroup(
            P2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );
        P2Layout.setVerticalGroup(
            P2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        P4.setBackground(resourceMap.getColor("P4.background")); // NOI18N
        P4.setBorder(javax.swing.BorderFactory.createLineBorder(resourceMap.getColor("P4.border.lineColor"))); // NOI18N
        P4.setMaximumSize(new java.awt.Dimension(15, 15));
        P4.setMinimumSize(new java.awt.Dimension(15, 15));
        P4.setName("P4"); // NOI18N

        javax.swing.GroupLayout P4Layout = new javax.swing.GroupLayout(P4);
        P4.setLayout(P4Layout);
        P4Layout.setHorizontalGroup(
            P4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );
        P4Layout.setVerticalGroup(
            P4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        P5.setBackground(resourceMap.getColor("P5.background")); // NOI18N
        P5.setBorder(javax.swing.BorderFactory.createLineBorder(resourceMap.getColor("P5.border.lineColor"))); // NOI18N
        P5.setMaximumSize(new java.awt.Dimension(15, 15));
        P5.setMinimumSize(new java.awt.Dimension(15, 15));
        P5.setName("P5"); // NOI18N

        javax.swing.GroupLayout P5Layout = new javax.swing.GroupLayout(P5);
        P5.setLayout(P5Layout);
        P5Layout.setHorizontalGroup(
            P5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );
        P5Layout.setVerticalGroup(
            P5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        P6.setBackground(resourceMap.getColor("P6.background")); // NOI18N
        P6.setBorder(javax.swing.BorderFactory.createLineBorder(resourceMap.getColor("P6.border.lineColor"))); // NOI18N
        P6.setMaximumSize(new java.awt.Dimension(15, 15));
        P6.setMinimumSize(new java.awt.Dimension(15, 15));
        P6.setName("P6"); // NOI18N

        javax.swing.GroupLayout P6Layout = new javax.swing.GroupLayout(P6);
        P6.setLayout(P6Layout);
        P6Layout.setHorizontalGroup(
            P6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );
        P6Layout.setVerticalGroup(
            P6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        P7.setBackground(resourceMap.getColor("P7.background")); // NOI18N
        P7.setBorder(javax.swing.BorderFactory.createLineBorder(resourceMap.getColor("P7.border.lineColor"))); // NOI18N
        P7.setMaximumSize(new java.awt.Dimension(15, 15));
        P7.setMinimumSize(new java.awt.Dimension(15, 15));
        P7.setName("P7"); // NOI18N

        javax.swing.GroupLayout P7Layout = new javax.swing.GroupLayout(P7);
        P7.setLayout(P7Layout);
        P7Layout.setHorizontalGroup(
            P7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );
        P7Layout.setVerticalGroup(
            P7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        P8.setBackground(resourceMap.getColor("P8.background")); // NOI18N
        P8.setBorder(javax.swing.BorderFactory.createLineBorder(resourceMap.getColor("P8.border.lineColor"))); // NOI18N
        P8.setMaximumSize(new java.awt.Dimension(15, 15));
        P8.setMinimumSize(new java.awt.Dimension(15, 15));
        P8.setName("P8"); // NOI18N

        javax.swing.GroupLayout P8Layout = new javax.swing.GroupLayout(P8);
        P8.setLayout(P8Layout);
        P8Layout.setHorizontalGroup(
            P8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );
        P8Layout.setVerticalGroup(
            P8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        P9.setBackground(resourceMap.getColor("P9.background")); // NOI18N
        P9.setBorder(javax.swing.BorderFactory.createLineBorder(resourceMap.getColor("P9.border.lineColor"))); // NOI18N
        P9.setMaximumSize(new java.awt.Dimension(15, 15));
        P9.setMinimumSize(new java.awt.Dimension(15, 15));
        P9.setName("P9"); // NOI18N

        javax.swing.GroupLayout P9Layout = new javax.swing.GroupLayout(P9);
        P9.setLayout(P9Layout);
        P9Layout.setHorizontalGroup(
            P9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );
        P9Layout.setVerticalGroup(
            P9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        P10.setBackground(resourceMap.getColor("P10.background")); // NOI18N
        P10.setBorder(javax.swing.BorderFactory.createLineBorder(resourceMap.getColor("P10.border.lineColor"))); // NOI18N
        P10.setMaximumSize(new java.awt.Dimension(15, 15));
        P10.setMinimumSize(new java.awt.Dimension(15, 15));
        P10.setName("P10"); // NOI18N

        javax.swing.GroupLayout P10Layout = new javax.swing.GroupLayout(P10);
        P10.setLayout(P10Layout);
        P10Layout.setHorizontalGroup(
            P10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );
        P10Layout.setVerticalGroup(
            P10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        P11.setBackground(resourceMap.getColor("P11.background")); // NOI18N
        P11.setBorder(javax.swing.BorderFactory.createLineBorder(resourceMap.getColor("P11.border.lineColor"))); // NOI18N
        P11.setMaximumSize(new java.awt.Dimension(15, 15));
        P11.setMinimumSize(new java.awt.Dimension(15, 15));
        P11.setName("P11"); // NOI18N

        javax.swing.GroupLayout P11Layout = new javax.swing.GroupLayout(P11);
        P11.setLayout(P11Layout);
        P11Layout.setHorizontalGroup(
            P11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );
        P11Layout.setVerticalGroup(
            P11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        P12.setBackground(resourceMap.getColor("P12.background")); // NOI18N
        P12.setBorder(javax.swing.BorderFactory.createLineBorder(resourceMap.getColor("P12.border.lineColor"))); // NOI18N
        P12.setMaximumSize(new java.awt.Dimension(15, 15));
        P12.setMinimumSize(new java.awt.Dimension(15, 15));
        P12.setName("P12"); // NOI18N

        javax.swing.GroupLayout P12Layout = new javax.swing.GroupLayout(P12);
        P12.setLayout(P12Layout);
        P12Layout.setHorizontalGroup(
            P12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );
        P12Layout.setVerticalGroup(
            P12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        P13.setBackground(resourceMap.getColor("P13.background")); // NOI18N
        P13.setBorder(javax.swing.BorderFactory.createLineBorder(resourceMap.getColor("P13.border.lineColor"))); // NOI18N
        P13.setMaximumSize(new java.awt.Dimension(15, 15));
        P13.setMinimumSize(new java.awt.Dimension(15, 15));
        P13.setName("P13"); // NOI18N

        javax.swing.GroupLayout P13Layout = new javax.swing.GroupLayout(P13);
        P13.setLayout(P13Layout);
        P13Layout.setHorizontalGroup(
            P13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );
        P13Layout.setVerticalGroup(
            P13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        P14.setBackground(resourceMap.getColor("P14.background")); // NOI18N
        P14.setBorder(javax.swing.BorderFactory.createLineBorder(resourceMap.getColor("P14.border.lineColor"))); // NOI18N
        P14.setMaximumSize(new java.awt.Dimension(15, 15));
        P14.setMinimumSize(new java.awt.Dimension(15, 15));
        P14.setName("P14"); // NOI18N

        javax.swing.GroupLayout P14Layout = new javax.swing.GroupLayout(P14);
        P14.setLayout(P14Layout);
        P14Layout.setHorizontalGroup(
            P14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );
        P14Layout.setVerticalGroup(
            P14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        P15.setBackground(resourceMap.getColor("P15.background")); // NOI18N
        P15.setBorder(javax.swing.BorderFactory.createLineBorder(resourceMap.getColor("P15.border.lineColor"))); // NOI18N
        P15.setMaximumSize(new java.awt.Dimension(15, 15));
        P15.setMinimumSize(new java.awt.Dimension(15, 15));
        P15.setName("P15"); // NOI18N

        javax.swing.GroupLayout P15Layout = new javax.swing.GroupLayout(P15);
        P15.setLayout(P15Layout);
        P15Layout.setHorizontalGroup(
            P15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );
        P15Layout.setVerticalGroup(
            P15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        P16.setBackground(resourceMap.getColor("P16.background")); // NOI18N
        P16.setBorder(javax.swing.BorderFactory.createLineBorder(resourceMap.getColor("P16.border.lineColor"))); // NOI18N
        P16.setMaximumSize(new java.awt.Dimension(15, 15));
        P16.setMinimumSize(new java.awt.Dimension(15, 15));
        P16.setName("P16"); // NOI18N

        javax.swing.GroupLayout P16Layout = new javax.swing.GroupLayout(P16);
        P16.setLayout(P16Layout);
        P16Layout.setHorizontalGroup(
            P16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );
        P16Layout.setVerticalGroup(
            P16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        P17.setBackground(resourceMap.getColor("P17.background")); // NOI18N
        P17.setBorder(javax.swing.BorderFactory.createLineBorder(resourceMap.getColor("P17.border.lineColor"))); // NOI18N
        P17.setMaximumSize(new java.awt.Dimension(15, 15));
        P17.setMinimumSize(new java.awt.Dimension(15, 15));
        P17.setName("P17"); // NOI18N

        javax.swing.GroupLayout P17Layout = new javax.swing.GroupLayout(P17);
        P17.setLayout(P17Layout);
        P17Layout.setHorizontalGroup(
            P17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );
        P17Layout.setVerticalGroup(
            P17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        P18.setBackground(resourceMap.getColor("P18.background")); // NOI18N
        P18.setBorder(javax.swing.BorderFactory.createLineBorder(resourceMap.getColor("P18.border.lineColor"))); // NOI18N
        P18.setMaximumSize(new java.awt.Dimension(15, 15));
        P18.setMinimumSize(new java.awt.Dimension(15, 15));
        P18.setName("P18"); // NOI18N

        javax.swing.GroupLayout P18Layout = new javax.swing.GroupLayout(P18);
        P18.setLayout(P18Layout);
        P18Layout.setHorizontalGroup(
            P18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );
        P18Layout.setVerticalGroup(
            P18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        P19.setBackground(resourceMap.getColor("P19.background")); // NOI18N
        P19.setBorder(javax.swing.BorderFactory.createLineBorder(resourceMap.getColor("P19.border.lineColor"))); // NOI18N
        P19.setMaximumSize(new java.awt.Dimension(15, 15));
        P19.setMinimumSize(new java.awt.Dimension(15, 15));
        P19.setName("P19"); // NOI18N

        javax.swing.GroupLayout P19Layout = new javax.swing.GroupLayout(P19);
        P19.setLayout(P19Layout);
        P19Layout.setHorizontalGroup(
            P19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );
        P19Layout.setVerticalGroup(
            P19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        P20.setBackground(resourceMap.getColor("P20.background")); // NOI18N
        P20.setBorder(javax.swing.BorderFactory.createLineBorder(resourceMap.getColor("P20.border.lineColor"))); // NOI18N
        P20.setMaximumSize(new java.awt.Dimension(15, 15));
        P20.setMinimumSize(new java.awt.Dimension(15, 15));
        P20.setName("P20"); // NOI18N

        javax.swing.GroupLayout P20Layout = new javax.swing.GroupLayout(P20);
        P20.setLayout(P20Layout);
        P20Layout.setHorizontalGroup(
            P20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );
        P20Layout.setVerticalGroup(
            P20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(A1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(B1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(C1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(A4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(B4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(C4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(A5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(B5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(C5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(A6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(B6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(C6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(A7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(B7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(C7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(A8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(B8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(C8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(A9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(B9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(C9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(A10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(B10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(C10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(A11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(B11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(C11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(A12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(B12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(C12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(A3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(A2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(B3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(B2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(C3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(C2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(A13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(B13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(C13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(A14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(B14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(C14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(A15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(B15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(C15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(A16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(B16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(C16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(A18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(B18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(C18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(A19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(B19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(C19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(A20, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(B20, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(C20, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(A17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(B17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(C17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(D1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(D4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(D5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(D6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(D7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(D8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(D9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(D10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(D11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(D12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(D3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(D2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(D13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(D14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(D15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(D16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(D18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(D19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(D20, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(D17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(E1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(E4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(E5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(E6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(E7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(E8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(E9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(E10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(E11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(E12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(E3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(E2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(E13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(E14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(E15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(E16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(E18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(E19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(E20, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(E17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(F1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(F4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(F5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(F6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(F7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(F8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(F9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(F10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(F11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(F12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(F3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(F2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(F13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(F14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(F15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(F16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(F18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(F19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(F20, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(F17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(G1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(G4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(G5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(G6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(G7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(G8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(G9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(G10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(G11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(G12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(G3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(G2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(G13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(G14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(G15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(G16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(G18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(G19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(G20, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(G17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(H1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(H4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(H5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(H6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(H7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(H8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(H9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(H10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(H11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(H12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(H3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(H2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(H13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(H14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(H15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(H16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(H18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(H19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(H20, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(H17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(I1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(I4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(I5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(I6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(I7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(I8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(I9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(I10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(I11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(I12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(I3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(I2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(I13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(I14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(I15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(I16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(I18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(I19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(I20, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(I17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(J1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(J4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(J5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(J6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(J7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(J8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(J9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(J10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(J11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(J12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(J3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(J2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(J13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(J14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(J15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(J16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(J18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(J19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(J20, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(J17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(K1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(K4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(K5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(K6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(K7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(K8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(K9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(K10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(K11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(K12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(K3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(K2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(K13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(K14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(K15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(K16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(K18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(K19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(K20, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(K17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(L1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(L4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(L5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(L6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(L7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(L8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(L9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(L10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(L11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(L12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(L3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(L2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(L13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(L14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(L15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(L16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(L18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(L19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(L20, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(L17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(M1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(M4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(M5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(M6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(M7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(M8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(M9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(M10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(M11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(M12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(M3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(M2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(M13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(M14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(M15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(M16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(M18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(M19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(M20, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(M17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(N1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(N4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(N5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(N6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(N7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(N8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(N9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(N10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(N11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(N12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(N2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(N3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(N13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(N14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(N15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(N16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(N18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(N19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(N20, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(N17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(O1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(O4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(O5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(O6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(O7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(O8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(O9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(O10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(O11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(O12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(O3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(O2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(O13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(O14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(O15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(O16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(O18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(O19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(O20, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(O17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(P1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(P4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(P5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(P6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(P7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(P8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(P9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(P10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(P11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(P12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(P3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(P2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(P13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(P14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(P15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(P16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(P18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(P19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(P20, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(P17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(59, 59, 59))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(P1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(P2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(P3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(P4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(P5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(P6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(P7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(P8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(P9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(P10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(P11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(P12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(P13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(P14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(P15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(P16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(P17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(P18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(P19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(P20, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(O1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(O2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(O3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(O4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(O5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(O6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(O7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(O8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(O9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(O10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(O11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(O12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(O13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(O14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(O15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(O16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(O17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(O18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(O19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(O20, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addComponent(N1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(N3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(N2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(N4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(N5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(N6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(N7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(N8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(N9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(N10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(N11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(N12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(N13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(N14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(N15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(N16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(N17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(N18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(N19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(N20, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(M1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(M2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(M3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(M4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(M5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(M6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(M7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(M8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(M9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(M10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(M11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(M12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(M13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(M14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(M15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(M16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(M17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(M18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(M19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(M20, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(L1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(L2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(L3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(L4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(L5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(L6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(L7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(L8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(L9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(L10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(L11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(L12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(L13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(L14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(L15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(L16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(L17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(L18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(L19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(L20, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(K1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(K2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(K3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(K4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(K5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(K6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(K7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(K8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(K9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(K10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(K11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(K12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(K13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(K14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(K15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(K16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(K17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(K18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(K19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(K20, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(J1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(J2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(J3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(J4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(J5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(J6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(J7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(J8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(J9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(J10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(J11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(J12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(J13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(J14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(J15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(J16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(J17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(J18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(J19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(J20, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(I1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(I2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(I3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(I4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(I5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(I6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(I7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(I8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(I9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(I10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(I11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(I12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(I13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(I14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(I15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(I16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(I17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(I18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(I19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(I20, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(H1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(H2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(H3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(H4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(H5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(H6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(H7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(H8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(H9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(H10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(H11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(H12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(H13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(H14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(H15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(H16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(H17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(H18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(H19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(H20, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(G1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(G2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(G3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(G4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(G5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(G6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(G7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(G8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(G9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(G10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(G11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(G12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(G13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(G14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(G15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(G16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(G17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(G18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(G19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(G20, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(F1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(F2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(F3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(F4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(F5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(F6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(F7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(F8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(F9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(F10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(F11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(F12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(F13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(F14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(F15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(F16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(F17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(F18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(F19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(F20, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel2Layout.createSequentialGroup()
                                    .addComponent(E1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(E2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(E3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(E4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(E5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(E6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(E7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(E8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(E9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(E10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(E11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(E12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(E13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(E14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(E15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(E16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(E17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(E18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(E19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(E20, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanel2Layout.createSequentialGroup()
                                    .addComponent(D1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(D2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(D3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(D4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(D5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(D6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(D7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(D8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(D9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(D10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(D11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(D12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(D13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(D14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(D15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(D16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(D17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(D18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(D19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(D20, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanel2Layout.createSequentialGroup()
                                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(A1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(B1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(C1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(A2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(B2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(C2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(A3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(B3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(C3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(A4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(B4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(C4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(A5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(B5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(C5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addGroup(jPanel2Layout.createSequentialGroup()
                                            .addComponent(C6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(C7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(jPanel2Layout.createSequentialGroup()
                                            .addComponent(A6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(A7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(jPanel2Layout.createSequentialGroup()
                                            .addComponent(B6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(B7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(A8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(B8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(C8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addGroup(jPanel2Layout.createSequentialGroup()
                                            .addComponent(C9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(C10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(jPanel2Layout.createSequentialGroup()
                                            .addComponent(A9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(A10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(jPanel2Layout.createSequentialGroup()
                                            .addComponent(B9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(B10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(A11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(B11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(C11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(A12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(B12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(C12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(A13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(B13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(C13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(A14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(B14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(C14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(A15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(B15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(C15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addGroup(jPanel2Layout.createSequentialGroup()
                                            .addComponent(C16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(C17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(jPanel2Layout.createSequentialGroup()
                                            .addComponent(A16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(A17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(jPanel2Layout.createSequentialGroup()
                                            .addComponent(B16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(B17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(A18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(B18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(C18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(A19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(B19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(C19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(C20, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(B20, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(A20, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addGap(18, 18, 18)
                        .addComponent(jComboBox6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel2))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(4, 4, 4)
                                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel8))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(label2, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(label1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jComboBox7, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addGap(18, 18, 18)
                        .addComponent(jComboBox5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addGap(18, 18, 18)
                        .addComponent(jComboBox4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addGap(18, 18, 18)
                        .addComponent(jComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(332, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(74, 74, 74)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel3)
                            .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(jComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(jComboBox4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(jComboBox5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(jComboBox6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8)
                            .addComponent(jComboBox7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(33, 33, 33)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(label2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(label1, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(132, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout mainPanelLayout = new javax.swing.GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addComponent(jLayeredPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 450, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        mainPanelLayout.setVerticalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLayeredPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 590, Short.MAX_VALUE)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(11, 11, 11))
        );

        menuBar.setName("menuBar"); // NOI18N

        fileMenu.setText(resourceMap.getString("fileMenu.text")); // NOI18N
        fileMenu.setName("fileMenu"); // NOI18N

        javax.swing.ActionMap actionMap = org.jdesktop.application.Application.getInstance(ratatouille.RatatouilleApp.class).getContext().getActionMap(RatatouilleView.class, this);
        exitMenuItem.setAction(actionMap.get("quit")); // NOI18N
        exitMenuItem.setName("exitMenuItem"); // NOI18N
        fileMenu.add(exitMenuItem);

        menuBar.add(fileMenu);

        helpMenu.setText(resourceMap.getString("helpMenu.text")); // NOI18N
        helpMenu.setName("helpMenu"); // NOI18N

        aboutMenuItem.setAction(actionMap.get("showAboutBox")); // NOI18N
        aboutMenuItem.setName("aboutMenuItem"); // NOI18N
        helpMenu.add(aboutMenuItem);

        menuBar.add(helpMenu);

        statusPanel.setName("statusPanel"); // NOI18N

        statusPanelSeparator.setName("statusPanelSeparator"); // NOI18N

        statusMessageLabel.setName("statusMessageLabel"); // NOI18N

        statusAnimationLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        statusAnimationLabel.setName("statusAnimationLabel"); // NOI18N

        progressBar.setName("progressBar"); // NOI18N

        javax.swing.GroupLayout statusPanelLayout = new javax.swing.GroupLayout(statusPanel);
        statusPanel.setLayout(statusPanelLayout);
        statusPanelLayout.setHorizontalGroup(
            statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(statusPanelSeparator, javax.swing.GroupLayout.DEFAULT_SIZE, 1574, Short.MAX_VALUE)
            .addGroup(statusPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(statusMessageLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 1404, Short.MAX_VALUE)
                .addComponent(progressBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(statusAnimationLabel)
                .addContainerGap())
        );
        statusPanelLayout.setVerticalGroup(
            statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(statusPanelLayout.createSequentialGroup()
                .addComponent(statusPanelSeparator, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(statusMessageLabel)
                    .addComponent(statusAnimationLabel)
                    .addComponent(progressBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(3, 3, 3))
        );

        setComponent(mainPanel);
        setMenuBar(menuBar);
        setStatusBar(statusPanel);
        addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                formPropertyChange(evt);
            }
        });
    }// </editor-fold>//GEN-END:initComponents

    private void formPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_formPropertyChange
        // TODO add your handling code here:
    }//GEN-LAST:event_formPropertyChange

    private void label1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_label1MouseEntered
        label1.setBackground(Color.DARK_GRAY);
        label1.setForeground(Color.white);
    }//GEN-LAST:event_label1MouseEntered

    private void label1MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_label1MouseExited
        label1.setBackground(Color.white);
        label1.setForeground(Color.DARK_GRAY);
    }//GEN-LAST:event_label1MouseExited

    private void label1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_label1MouseClicked
        label2.setBackground(Color.black);
        label2.setForeground(Color.white);
        falseFirstVisible();
        try {
            setValuesInMasterBridge();
        } catch (IOException ex) {
            Logger.getLogger(RatatouilleView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_label1MouseClicked

    private void label2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_label2MouseClicked
        jPanel2.setVisible(false);
        CancelaCores();
        trueFirstVisible();
    }//GEN-LAST:event_label2MouseClicked

    private void label2MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_label2MouseEntered
        label2.setBackground(Color.white);
        label2.setForeground(Color.black);
    }//GEN-LAST:event_label2MouseEntered

    private void label2MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_label2MouseExited
        label2.setBackground(Color.black);
        label2.setForeground(Color.white);
    }//GEN-LAST:event_label2MouseExited

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel A1;
    private javax.swing.JPanel A10;
    private javax.swing.JPanel A11;
    private javax.swing.JPanel A12;
    private javax.swing.JPanel A13;
    private javax.swing.JPanel A14;
    private javax.swing.JPanel A15;
    private javax.swing.JPanel A16;
    private javax.swing.JPanel A17;
    private javax.swing.JPanel A18;
    private javax.swing.JPanel A19;
    private javax.swing.JPanel A2;
    private javax.swing.JPanel A20;
    private javax.swing.JPanel A3;
    private javax.swing.JPanel A4;
    private javax.swing.JPanel A5;
    private javax.swing.JPanel A6;
    private javax.swing.JPanel A7;
    private javax.swing.JPanel A8;
    private javax.swing.JPanel A9;
    private javax.swing.JPanel B1;
    private javax.swing.JPanel B10;
    private javax.swing.JPanel B11;
    private javax.swing.JPanel B12;
    private javax.swing.JPanel B13;
    private javax.swing.JPanel B14;
    private javax.swing.JPanel B15;
    private javax.swing.JPanel B16;
    private javax.swing.JPanel B17;
    private javax.swing.JPanel B18;
    private javax.swing.JPanel B19;
    private javax.swing.JPanel B2;
    private javax.swing.JPanel B20;
    private javax.swing.JPanel B3;
    private javax.swing.JPanel B4;
    private javax.swing.JPanel B5;
    private javax.swing.JPanel B6;
    private javax.swing.JPanel B7;
    private javax.swing.JPanel B8;
    private javax.swing.JPanel B9;
    private javax.swing.JPanel C1;
    private javax.swing.JPanel C10;
    private javax.swing.JPanel C11;
    private javax.swing.JPanel C12;
    private javax.swing.JPanel C13;
    private javax.swing.JPanel C14;
    private javax.swing.JPanel C15;
    private javax.swing.JPanel C16;
    private javax.swing.JPanel C17;
    private javax.swing.JPanel C18;
    private javax.swing.JPanel C19;
    private javax.swing.JPanel C2;
    private javax.swing.JPanel C20;
    private javax.swing.JPanel C3;
    private javax.swing.JPanel C4;
    private javax.swing.JPanel C5;
    private javax.swing.JPanel C6;
    private javax.swing.JPanel C7;
    private javax.swing.JPanel C8;
    private javax.swing.JPanel C9;
    private javax.swing.JPanel D1;
    private javax.swing.JPanel D10;
    private javax.swing.JPanel D11;
    private javax.swing.JPanel D12;
    private javax.swing.JPanel D13;
    private javax.swing.JPanel D14;
    private javax.swing.JPanel D15;
    private javax.swing.JPanel D16;
    private javax.swing.JPanel D17;
    private javax.swing.JPanel D18;
    private javax.swing.JPanel D19;
    private javax.swing.JPanel D2;
    private javax.swing.JPanel D20;
    private javax.swing.JPanel D3;
    private javax.swing.JPanel D4;
    private javax.swing.JPanel D5;
    private javax.swing.JPanel D6;
    private javax.swing.JPanel D7;
    private javax.swing.JPanel D8;
    private javax.swing.JPanel D9;
    private javax.swing.JPanel E1;
    private javax.swing.JPanel E10;
    private javax.swing.JPanel E11;
    private javax.swing.JPanel E12;
    private javax.swing.JPanel E13;
    private javax.swing.JPanel E14;
    private javax.swing.JPanel E15;
    private javax.swing.JPanel E16;
    private javax.swing.JPanel E17;
    private javax.swing.JPanel E18;
    private javax.swing.JPanel E19;
    private javax.swing.JPanel E2;
    private javax.swing.JPanel E20;
    private javax.swing.JPanel E3;
    private javax.swing.JPanel E4;
    private javax.swing.JPanel E5;
    private javax.swing.JPanel E6;
    private javax.swing.JPanel E7;
    private javax.swing.JPanel E8;
    private javax.swing.JPanel E9;
    private javax.swing.JPanel F1;
    private javax.swing.JPanel F10;
    private javax.swing.JPanel F11;
    private javax.swing.JPanel F12;
    private javax.swing.JPanel F13;
    private javax.swing.JPanel F14;
    private javax.swing.JPanel F15;
    private javax.swing.JPanel F16;
    private javax.swing.JPanel F17;
    private javax.swing.JPanel F18;
    private javax.swing.JPanel F19;
    private javax.swing.JPanel F2;
    private javax.swing.JPanel F20;
    private javax.swing.JPanel F3;
    private javax.swing.JPanel F4;
    private javax.swing.JPanel F5;
    private javax.swing.JPanel F6;
    private javax.swing.JPanel F7;
    private javax.swing.JPanel F8;
    private javax.swing.JPanel F9;
    private javax.swing.JPanel G1;
    private javax.swing.JPanel G10;
    private javax.swing.JPanel G11;
    private javax.swing.JPanel G12;
    private javax.swing.JPanel G13;
    private javax.swing.JPanel G14;
    private javax.swing.JPanel G15;
    private javax.swing.JPanel G16;
    private javax.swing.JPanel G17;
    private javax.swing.JPanel G18;
    private javax.swing.JPanel G19;
    private javax.swing.JPanel G2;
    private javax.swing.JPanel G20;
    private javax.swing.JPanel G3;
    private javax.swing.JPanel G4;
    private javax.swing.JPanel G5;
    private javax.swing.JPanel G6;
    private javax.swing.JPanel G7;
    private javax.swing.JPanel G8;
    private javax.swing.JPanel G9;
    private javax.swing.JPanel H1;
    private javax.swing.JPanel H10;
    private javax.swing.JPanel H11;
    private javax.swing.JPanel H12;
    private javax.swing.JPanel H13;
    private javax.swing.JPanel H14;
    private javax.swing.JPanel H15;
    private javax.swing.JPanel H16;
    private javax.swing.JPanel H17;
    private javax.swing.JPanel H18;
    private javax.swing.JPanel H19;
    private javax.swing.JPanel H2;
    private javax.swing.JPanel H20;
    private javax.swing.JPanel H3;
    private javax.swing.JPanel H4;
    private javax.swing.JPanel H5;
    private javax.swing.JPanel H6;
    private javax.swing.JPanel H7;
    private javax.swing.JPanel H8;
    private javax.swing.JPanel H9;
    private javax.swing.JPanel I1;
    private javax.swing.JPanel I10;
    private javax.swing.JPanel I11;
    private javax.swing.JPanel I12;
    private javax.swing.JPanel I13;
    private javax.swing.JPanel I14;
    private javax.swing.JPanel I15;
    private javax.swing.JPanel I16;
    private javax.swing.JPanel I17;
    private javax.swing.JPanel I18;
    private javax.swing.JPanel I19;
    private javax.swing.JPanel I2;
    private javax.swing.JPanel I20;
    private javax.swing.JPanel I3;
    private javax.swing.JPanel I4;
    private javax.swing.JPanel I5;
    private javax.swing.JPanel I6;
    private javax.swing.JPanel I7;
    private javax.swing.JPanel I8;
    private javax.swing.JPanel I9;
    private javax.swing.JPanel J1;
    private javax.swing.JPanel J10;
    private javax.swing.JPanel J11;
    private javax.swing.JPanel J12;
    private javax.swing.JPanel J13;
    private javax.swing.JPanel J14;
    private javax.swing.JPanel J15;
    private javax.swing.JPanel J16;
    private javax.swing.JPanel J17;
    private javax.swing.JPanel J18;
    private javax.swing.JPanel J19;
    private javax.swing.JPanel J2;
    private javax.swing.JPanel J20;
    private javax.swing.JPanel J3;
    private javax.swing.JPanel J4;
    private javax.swing.JPanel J5;
    private javax.swing.JPanel J6;
    private javax.swing.JPanel J7;
    private javax.swing.JPanel J8;
    private javax.swing.JPanel J9;
    private javax.swing.JPanel K1;
    private javax.swing.JPanel K10;
    private javax.swing.JPanel K11;
    private javax.swing.JPanel K12;
    private javax.swing.JPanel K13;
    private javax.swing.JPanel K14;
    private javax.swing.JPanel K15;
    private javax.swing.JPanel K16;
    private javax.swing.JPanel K17;
    private javax.swing.JPanel K18;
    private javax.swing.JPanel K19;
    private javax.swing.JPanel K2;
    private javax.swing.JPanel K20;
    private javax.swing.JPanel K3;
    private javax.swing.JPanel K4;
    private javax.swing.JPanel K5;
    private javax.swing.JPanel K6;
    private javax.swing.JPanel K7;
    private javax.swing.JPanel K8;
    private javax.swing.JPanel K9;
    private javax.swing.JPanel L1;
    private javax.swing.JPanel L10;
    private javax.swing.JPanel L11;
    private javax.swing.JPanel L12;
    private javax.swing.JPanel L13;
    private javax.swing.JPanel L14;
    private javax.swing.JPanel L15;
    private javax.swing.JPanel L16;
    private javax.swing.JPanel L17;
    private javax.swing.JPanel L18;
    private javax.swing.JPanel L19;
    private javax.swing.JPanel L2;
    private javax.swing.JPanel L20;
    private javax.swing.JPanel L3;
    private javax.swing.JPanel L4;
    private javax.swing.JPanel L5;
    private javax.swing.JPanel L6;
    private javax.swing.JPanel L7;
    private javax.swing.JPanel L8;
    private javax.swing.JPanel L9;
    private javax.swing.JPanel M1;
    private javax.swing.JPanel M10;
    private javax.swing.JPanel M11;
    private javax.swing.JPanel M12;
    private javax.swing.JPanel M13;
    private javax.swing.JPanel M14;
    private javax.swing.JPanel M15;
    private javax.swing.JPanel M16;
    private javax.swing.JPanel M17;
    private javax.swing.JPanel M18;
    private javax.swing.JPanel M19;
    private javax.swing.JPanel M2;
    private javax.swing.JPanel M20;
    private javax.swing.JPanel M3;
    private javax.swing.JPanel M4;
    private javax.swing.JPanel M5;
    private javax.swing.JPanel M6;
    private javax.swing.JPanel M7;
    private javax.swing.JPanel M8;
    private javax.swing.JPanel M9;
    private javax.swing.JPanel N1;
    private javax.swing.JPanel N10;
    private javax.swing.JPanel N11;
    private javax.swing.JPanel N12;
    private javax.swing.JPanel N13;
    private javax.swing.JPanel N14;
    private javax.swing.JPanel N15;
    private javax.swing.JPanel N16;
    private javax.swing.JPanel N17;
    private javax.swing.JPanel N18;
    private javax.swing.JPanel N19;
    private javax.swing.JPanel N2;
    private javax.swing.JPanel N20;
    private javax.swing.JPanel N3;
    private javax.swing.JPanel N4;
    private javax.swing.JPanel N5;
    private javax.swing.JPanel N6;
    private javax.swing.JPanel N7;
    private javax.swing.JPanel N8;
    private javax.swing.JPanel N9;
    private javax.swing.JPanel O1;
    private javax.swing.JPanel O10;
    private javax.swing.JPanel O11;
    private javax.swing.JPanel O12;
    private javax.swing.JPanel O13;
    private javax.swing.JPanel O14;
    private javax.swing.JPanel O15;
    private javax.swing.JPanel O16;
    private javax.swing.JPanel O17;
    private javax.swing.JPanel O18;
    private javax.swing.JPanel O19;
    private javax.swing.JPanel O2;
    private javax.swing.JPanel O20;
    private javax.swing.JPanel O3;
    private javax.swing.JPanel O4;
    private javax.swing.JPanel O5;
    private javax.swing.JPanel O6;
    private javax.swing.JPanel O7;
    private javax.swing.JPanel O8;
    private javax.swing.JPanel O9;
    private javax.swing.JPanel P1;
    private javax.swing.JPanel P10;
    private javax.swing.JPanel P11;
    private javax.swing.JPanel P12;
    private javax.swing.JPanel P13;
    private javax.swing.JPanel P14;
    private javax.swing.JPanel P15;
    private javax.swing.JPanel P16;
    private javax.swing.JPanel P17;
    private javax.swing.JPanel P18;
    private javax.swing.JPanel P19;
    private javax.swing.JPanel P2;
    private javax.swing.JPanel P20;
    private javax.swing.JPanel P3;
    private javax.swing.JPanel P4;
    private javax.swing.JPanel P5;
    private javax.swing.JPanel P6;
    private javax.swing.JPanel P7;
    private javax.swing.JPanel P8;
    private javax.swing.JPanel P9;
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JComboBox jComboBox2;
    private javax.swing.JComboBox jComboBox3;
    private javax.swing.JComboBox jComboBox4;
    private javax.swing.JComboBox jComboBox5;
    private javax.swing.JComboBox jComboBox6;
    private javax.swing.JComboBox jComboBox7;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLayeredPane jLayeredPane1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private java.awt.Label label1;
    private java.awt.Label label2;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JProgressBar progressBar;
    private javax.swing.JLabel statusAnimationLabel;
    private javax.swing.JLabel statusMessageLabel;
    private javax.swing.JPanel statusPanel;
    // End of variables declaration//GEN-END:variables

    private final Timer messageTimer;
    private final Timer busyIconTimer;
    private final Icon idleIcon;
    private final Icon[] busyIcons = new Icon[15];
    private int busyIconIndex = 0;

    private JDialog aboutBox;
}
