import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.sound.sampled.*;
import java.io.File;

public class MedicineReminder {

    static ArrayList<String> medicineList = new ArrayList<>();
    static ArrayList<String> timeList = new ArrayList<>();

    // 🔔 SOUND METHOD
    public static void playSound() {
        try {
            File file = new File("alert.wav");

            if (!file.exists()) {
                System.out.println("Sound file not found!");
                return;
            }

            AudioInputStream audio = AudioSystem.getAudioInputStream(file);
            Clip clip = AudioSystem.getClip();
            clip.open(audio);
            clip.start();

        } catch (Exception e) {
            System.out.println("Error playing sound: " + e.getMessage());
        }
    }

    public static void main(String[] args) {

        // 🖥️ FRAME
        JFrame frame = new JFrame("Medicine Reminder");
        frame.setSize(350, 250);
        frame.setLayout(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // LABELS
        JLabel medLabel = new JLabel("Medicine Name:");
        medLabel.setBounds(20, 30, 120, 20);
        frame.add(medLabel);

        JLabel timeLabel = new JLabel("Time (HH:MM):");
        timeLabel.setBounds(20, 70, 120, 20);
        frame.add(timeLabel);

        // TEXTFIELDS
        JTextField medField = new JTextField();
        medField.setBounds(150, 30, 150, 20);
        frame.add(medField);

        JTextField timeField = new JTextField();
        timeField.setBounds(150, 70, 150, 20);
        frame.add(timeField);

        // BUTTON
        JButton addBtn = new JButton("Add Reminder");
        addBtn.setBounds(100, 120, 150, 30);
        frame.add(addBtn);

        // BUTTON ACTION
        addBtn.addActionListener(e -> {
            String medicine = medField.getText();
            String time = timeField.getText();

            if (!medicine.isEmpty() && !time.isEmpty()) {
                medicineList.add(medicine);
                timeList.add(time);
                JOptionPane.showMessageDialog(frame, "✅ Reminder Added!");
                medField.setText("");
                timeField.setText("");
            } else {
                JOptionPane.showMessageDialog(frame, "⚠️ Enter all fields!");
            }
        });

        frame.setVisible(true);

        // ⏰ TIMER
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {

                String currentTime = new SimpleDateFormat("HH:mm").format(new Date());

                for (int i = 0; i < timeList.size(); i++) {
                    if (timeList.get(i).equals(currentTime)) {

                        playSound(); // 🔔 SOUND

                        JOptionPane.showMessageDialog(frame,
                                "🔔 Time to take: " + medicineList.get(i));
                    }
                }
            }
        }, 0, 60000);
    }
}