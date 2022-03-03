// import java.awt.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.util.*;

public class Test {
//    static JDialog d, derr;

    public static DefaultTableModel buildTableModel(ResultSet rs)
            throws SQLException {

        ResultSetMetaData metaData = rs.getMetaData();

        // имена колонок
        Vector<String> columnNames = new Vector<String>();
        int columnCount = metaData.getColumnCount();
        for (int column = 1; column <= columnCount; column++) {
            columnNames.add(metaData.getColumnName(column));
        }

        // данные таблицы
        Vector<Vector<Object>> data = new Vector<Vector<Object>>();
        while (rs.next()) {
            Vector<Object> vector = new Vector<Object>();
            for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
                vector.add(rs.getObject(columnIndex));
            }
            data.add(vector);
        }
        return new DefaultTableModel(data, columnNames);
    }

    public static void query1 (String urlConn, JFrame frame) {
        try {
//            testing connection string
//            String url = "jdbc:msql://200.210.220.1:1114/Demo";
//            DriverManager.registerDriver(new com.microsoft.sqlserver.jdbc.SQLServerDriver());
//            String connectionUrl =
//                    "jdbc:sqlserver://SYSOPER\\SQLEXPRESS;databaseName=test;";
//            String connectionUrl = "jdbc:sqlserver://127.0.0.1:1433;databaseName=test;encrypt=true; trustServerCertificate=false;";

//            String connectionUrl = "jdbc:jtds:sqlserver://127.0.0.1:1433/test;instance=SQLEXPRESS;integratedSecurity=true;";
            Connection conn = DriverManager.getConnection(urlConn);
//            if (d == null) {
//                System.err.println("d is null");
//            } else {
//                System.err.println("d not null");
//            }
            Statement stmt = conn.createStatement();
            ResultSet rs;
            rs = stmt.executeQuery("SELECT Item, Price FROM Items");
            JTable table = new JTable(buildTableModel(rs));
            JDialog d = new JDialog(frame, "dialog Box");
            JPanel p = new JPanel();
            JLabel l = new JLabel("SQL подключено");
            p.add(l);
            p.add(table);
            d.add(p);
            d.setSize(200, 200);
            d.setVisible(true);
            conn.close();
        } catch (Exception e) {
            JDialog derr = new JDialog(frame, "Error Connection");
            JPanel perr = new JPanel();
            JLabel l = new JLabel(e.getMessage());
            perr.add(l);
            derr.add(perr);
            derr.setSize(500, 70);
            derr.setVisible(true);
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }

    }

    public static void main(String[] args) {
        JFrame f=new JFrame();//creating instance of JFrame
        // Release the window and quit the application when it has been closed
        f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Creating a button and setting its action
        final JButton clickMeButton = new JButton("Click Me!");
        clickMeButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                String name1 = (String) JOptionPane.showInputDialog(f,
                        "Edit Connection String",
                        "Server Connection\n", JOptionPane.OK_CANCEL_OPTION, null,
                        null, "jdbc:jtds:sqlserver://127.0.0.1:1433/test;instance=SQLEXPRESS;integratedSecurity=true;");
//                query1("jdbc:jtds:sqlserver://127.0.0.1:1433/test;instance=SQLEXPRESS;integratedSecurity=true;", f);
                query1(name1, f);
            }
        });
        // Add the button to the window and resize it to fit the button
        f.getContentPane().add(clickMeButton);
        f.pack();

        f.setSize(400,500);//400 width and 500 height
        f.setLayout(null);//using no layout managers
        f.setVisible(true);//making the frame visible
    }
}
