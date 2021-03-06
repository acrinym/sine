/*
 * Copyright (C) 2014 Federico Dossena
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.dosse.bwentrain.editor;

import com.dosse.bwentrain.core.Preset;
import com.dosse.bwentrain.renderers.IRenderer;
import com.dosse.bwentrain.renderers.isochronic.IsochronicRenderer;
import com.dosse.bwentrain.sound.backends.flac.FLACFileSoundBackend;
import com.dosse.bwentrain.sound.backends.mp3.MP3FileSoundBackend;
import com.dosse.bwentrain.sound.backends.wav.WavFileSoundBackend;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import javax.swing.filechooser.FileFilter;

/**
 *
 * @author dosse
 */
public class ExportDialog extends javax.swing.JDialog {

    private static final int DEFAULT_WIDTH = 350;

    private IRenderer ren = null;
    private Preset p;
    private File x;
    private FileFilter f;

    private Timer t; //timer used to update the progress bar

    /**
     * Creates new form ExportDialog
     */
    public ExportDialog(Preset p, File x, FileFilter f) {
        super(new JFrame(), true);
        initComponents();
        Point ml = MouseInfo.getPointerInfo().getLocation();
        setLocation(ml.x, ml.y);
        setSize((int) (DEFAULT_WIDTH * Main.SCALE + getInsets().left + getInsets().right), getHeight());
        this.p = p;
        this.x = x;
        this.f = f;
        if (!p.loops()) {
            loopPanel.setVisible(false);
        }
        t = new Timer(20, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (ren == null) { //not started yet
                    progress.setValue(0);
                } else { //started
                    if (ren.isPlaying()) { //exporting
                        float p = ren.getPosition() / ExportDialog.this.p.getLength();
                        progress.setValue((int) (progress.getMaximum() * p));
                        progress.setString((int) (100 * p) + "%");
                    } else { //done exporting
                        ren.stopPlaying();
                        ren = null;
                        t.stop();
                        dispose();
                    }
                }
            }
        });
        t.setRepeats(true);
        t.start();
        //listener for ESCAPE (cancel) and ENTER (confirm) keys
        KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(new KeyEventDispatcher() {
            @Override
            public boolean dispatchKeyEvent(KeyEvent e) {
                if (!isFocused()) {
                    return false;
                }
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE && e.getID() == KeyEvent.KEY_PRESSED) { //ESC=cancel
                    cancelActionPerformed(null);
                }
                if (e.getKeyCode() == KeyEvent.VK_ENTER && e.getID() == KeyEvent.KEY_PRESSED) { //ENTER=confirm
                    if (!export.isEnabled()) {
                        return false;
                    }
                    try {
                        //commit spinner
                        loopN.commitEdit();
                    } catch (Throwable t) {
                        //one of the spinners contains invalid values
                        return false;
                    }
                    exportActionPerformed(null);
                }
                return false;
            }
        });
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        loopPanel = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        loopN = new javax.swing.JSpinner();
        jLabel8 = new javax.swing.JLabel();
        cancel = new javax.swing.JButton();
        export = new javax.swing.JButton();
        progress = new javax.swing.JProgressBar();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("com/dosse/bwentrain/editor/locale"); // NOI18N
        setTitle(bundle.getString("ExportDialog.title")); // NOI18N
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        jLabel7.setText(bundle.getString("ExportDialog.jLabel7.text")); // NOI18N

        loopN.setModel(new javax.swing.SpinnerNumberModel(Integer.valueOf(3), Integer.valueOf(0), null, Integer.valueOf(1)));

        jLabel8.setText(bundle.getString("ExportDialog.jLabel8.text")); // NOI18N

        javax.swing.GroupLayout loopPanelLayout = new javax.swing.GroupLayout(loopPanel);
        loopPanel.setLayout(loopPanelLayout);
        loopPanelLayout.setHorizontalGroup(
            loopPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(loopPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(loopN, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel8)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        loopPanelLayout.setVerticalGroup(
            loopPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(loopPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jLabel7)
                .addComponent(loopN, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(jLabel8))
        );

        cancel.setText(bundle.getString("ExportDialog.cancel.text")); // NOI18N
        cancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelActionPerformed(evt);
            }
        });

        export.setText(bundle.getString("ExportDialog.export.text")); // NOI18N
        export.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exportActionPerformed(evt);
            }
        });

        progress.setString(""); // NOI18N
        progress.setStringPainted(true);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(loopPanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(export)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cancel))
                    .addComponent(progress, javax.swing.GroupLayout.DEFAULT_SIZE, 342, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(progress, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(loopPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cancel)
                    .addComponent(export))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void exportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exportActionPerformed
        try {
            if (f == Main.MP3_FILE_FILTER) {
                ren = new IsochronicRenderer(p, new MP3FileSoundBackend(x.getAbsolutePath(), 44100, 1, 96), p.loops() ? (Integer) (loopN.getValue()) : -1);
            }
            if (f == Main.FLAC_FILE_FILTER) {
                ren = new IsochronicRenderer(p, new FLACFileSoundBackend(x.getAbsolutePath(), 44100, 1), p.loops() ? (Integer) (loopN.getValue()) : -1);
            }
            if (f == Main.WAV_FILE_FILTER) {
                ren = new IsochronicRenderer(p, new WavFileSoundBackend(x.getAbsolutePath(), 44100, 1), p.loops() ? (Integer) (loopN.getValue()) : -1);
            }
            export.setEnabled(false);
            loopN.setEnabled(false);
            jLabel7.setEnabled(false);
            jLabel8.setEnabled(false);
            ren.play();
        } catch (Throwable ex) {
        }
    }//GEN-LAST:event_exportActionPerformed

    private void cancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelActionPerformed
        if (ren != null) {
            int sel = JOptionPane.showConfirmDialog(this, Utils.getLocString("EXPORT_CANCEL_CONFIRM"), getTitle(), JOptionPane.YES_NO_OPTION);
            if (sel == JOptionPane.NO_OPTION || sel == -1) { //confirm?
                //no or cancelled dialog
                return;
            }
            //cancel
            if (ren != null) { //must check again because it might have finished while showing the export dialog
                ren.stopPlaying();
            }
            x.delete(); //delete partially exported file
        }
        t.stop();
        dispose();
    }//GEN-LAST:event_cancelActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        cancelActionPerformed(null);
    }//GEN-LAST:event_formWindowClosing

    //show dialog and wait
    public static void export(Preset p, File x, FileFilter f) {
        ExportDialog d = new ExportDialog(p, x, f);
        d.setVisible(true);
        while (d.isVisible()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException ex) {
            }
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton cancel;
    private javax.swing.JButton export;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JSpinner loopN;
    private javax.swing.JPanel loopPanel;
    private javax.swing.JProgressBar progress;
    // End of variables declaration//GEN-END:variables
}
