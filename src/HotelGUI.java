import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class HotelGUI extends JFrame {

    private JTextField nameField;
    private JTextField roomField;
    private JTextField phoneField;
    private JTextArea displayArea;

    public HotelGUI() {
        setTitle("Hotel Management System");
        setSize(500, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel inputPanel = new JPanel(new GridLayout(4, 2, 5, 5));

        inputPanel.add(new JLabel("Guest Name:"));
        nameField = new JTextField();
        inputPanel.add(nameField);

        inputPanel.add(new JLabel("Room Number:"));
        roomField = new JTextField();
        inputPanel.add(roomField);

        inputPanel.add(new JLabel("Phone:"));
        phoneField = new JTextField();
        inputPanel.add(phoneField);

        JButton addBtn = new JButton("Add Guest");
        JButton viewBtn = new JButton("View Guests");
        JButton exitBtn = new JButton("Exit");

        inputPanel.add(addBtn);
        inputPanel.add(viewBtn);

        displayArea = new JTextArea();
        displayArea.setEditable(false);

        add(inputPanel, BorderLayout.NORTH);
        add(new JScrollPane(displayArea), BorderLayout.CENTER);
        add(exitBtn, BorderLayout.SOUTH);

        addBtn.addActionListener(e -> addGuest());
        viewBtn.addActionListener(e -> viewGuests());
        exitBtn.addActionListener(e -> System.exit(0));
    }

    private void addGuest() {
        try (Connection conn = DBConnection.getConnection()) {
            String sql =
                "INSERT INTO guests (name, room_number, phone) VALUES (?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, nameField.getText());
            ps.setInt(2, Integer.parseInt(roomField.getText()));
            ps.setString(3, phoneField.getText());
            ps.executeUpdate();

            displayArea.setText("Guest added successfully!\n");
            nameField.setText("");
            roomField.setText("");
            phoneField.setText("");

        } catch (Exception ex) {
            displayArea.setText("Error adding guest\n");
            ex.printStackTrace();
        }
    }

    private void viewGuests() {
        try (Connection conn = DBConnection.getConnection()) {
            displayArea.setText("");
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM guests");

            while (rs.next()) {
                displayArea.append(
                    rs.getInt("id") + " | " +
                    rs.getString("name") + " | Room " +
                    rs.getInt("room_number") + " | " +
                    rs.getString("phone") + "\n"
                );
            }

        } catch (Exception ex) {
            displayArea.setText("Error loading guests\n");
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new HotelGUI().setVisible(true);
        });
    }
}
