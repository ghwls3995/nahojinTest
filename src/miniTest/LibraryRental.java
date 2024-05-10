package miniTest;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

public class LibraryRental {
    private static JButton loginButton; 
    private static JButton signUpButton; 
    private static JTable bookTable; 
    private static DefaultTableModel tableModel;
    
    public static void main(String[] args) {
      
        JFrame frame = new JFrame("OO도서관");
        frame.setSize(1000, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

       
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

   
        JPanel imagePanel = new JPanel();
        try {
    
            URL imageUrl = new URL("https://upload.wikimedia.org/wikipedia/commons/a/a3/%EB%B3%84%EB%A7%88%EB%8B%B9%EB%8F%84%EC%84%9C%EA%B4%803.jpg");
            ImageIcon imageIcon = new ImageIcon(imageUrl);
            Image image = imageIcon.getImage().getScaledInstance(800, 500, Image.SCALE_SMOOTH);
            // SCALE_SMOOTH 이미지 품질 개선하는거 
            
            
            imageIcon = new ImageIcon(image);
            JLabel imageLabel = new JLabel(imageIcon);
            imagePanel.add(imageLabel);
        } catch (Exception e) {
            e.printStackTrace();
        }
        panel.add(imagePanel, BorderLayout.NORTH);

       
        loginButton = new JButton("로그인");
        setButtonSize(loginButton); 
        // 버튼 크기
        loginButton.addActionListener(e -> {
           
            JTextField idField = new JTextField(10);
            JPasswordField passwordField = new JPasswordField(10);

            JPanel loginPanel = new JPanel();
            loginPanel.add(new JLabel("아이디:"));
            loginPanel.add(idField);
            loginPanel.add(new JLabel("비밀번호:"));
            loginPanel.add(passwordField);

            int result = JOptionPane.showConfirmDialog(null, loginPanel, "로그인", JOptionPane.OK_CANCEL_OPTION);
            if (result == JOptionPane.OK_OPTION) {
                JOptionPane.showMessageDialog(null, "로그인이 완료되었습니다.");
                panel.remove(imagePanel);
                loginButton.setVisible(false);
                signUpButton.setVisible(false);
                displayBookList(frame, panel);
            }
        });
        panel.add(loginButton, BorderLayout.CENTER);

 
        signUpButton = new JButton("회원가입");
        setButtonSize(signUpButton); 
        signUpButton.addActionListener(e -> {
            JTextField nameField = new JTextField(10);
            JTextField idField = new JTextField(10);
            JPasswordField passwordField = new JPasswordField(10);

            JPanel signUpPanel = new JPanel();
            signUpPanel.add(new JLabel("이름:"));
            signUpPanel.add(nameField);
            signUpPanel.add(new JLabel("아이디:"));
            signUpPanel.add(idField);
            signUpPanel.add(new JLabel("비밀번호:"));
            signUpPanel.add(passwordField);

            int result = JOptionPane.showConfirmDialog(null, signUpPanel, "회원가입", JOptionPane.OK_CANCEL_OPTION);
            if (result == JOptionPane.OK_OPTION) {
                JOptionPane.showMessageDialog(null, "회원가입이 완료되었습니다.");
            }
        });
        panel.add(signUpButton, BorderLayout.WEST); 

        frame.add(panel);

        frame.setVisible(true);
    }

    private static void displayBookList(JFrame frame, JPanel panel) {
        tableModel = new DefaultTableModel();
        tableModel.addColumn("도서명");
        tableModel.addColumn("저자");
        tableModel.addColumn("대여 여부");

        String[] books = {
                "나와 그녀의 왼손 - 츠지도 유메 - 대여 가능",
                "틴틴팅클! - 난 - 대여 가능",
                "말하고 싶은 비밀 - 사쿠라 아이요 - 대여 중",
                "혼자여서 괜찮은 하루 - 곽정은 - 대여 가능",
                "나는 나로 살기로 했다 - 김수현 - 대여 중",
                "인스타 브레인 - 안데르스한센 - 대여 가능",
                "내가 틀릴 수도 있습니다 - 비욘 나티코 린데 - 대여 가능",
                "불안의 서 - 페르난두페소아 - 대여 중",
                "노가다 칸타빌레 - 송주홍 - 대여 가능",
                "영웅 죽음의 무게01권 - 에레닉스 - 대여 중"
        };
        Arrays.asList(books).forEach(book -> {
            String[] bookData = book.split(" - ");
            tableModel.addRow(new Object[]{bookData[0], bookData[1], bookData[2]});
        });

        bookTable = new JTable(tableModel);
        bookTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(bookTable);
        panel.add(scrollPane, BorderLayout.NORTH);

        JButton rentButton = new JButton("대여하기");
        rentButton.addActionListener(e -> {
            int selectedRow = bookTable.getSelectedRow(); 
            if (selectedRow != -1) {
                String bookStatus = (String) tableModel.getValueAt(selectedRow, 2);
                if (bookStatus.equals("대여 중")) {
                    JOptionPane.showMessageDialog(frame, "선택한 책은 현재 대여 중입니다.");
                    // 선택한 행이 대여중이면 대여중이라고 알람띄우기 
                    
                } else {
                    // 대여 시작일 선택
                    JSpinner startDateSpinner = new JSpinner(new SpinnerDateModel());
                    JSpinner.DateEditor startDateEditor = new JSpinner.DateEditor(startDateSpinner, "MM/dd/yyyy");
                    startDateSpinner.setEditor(startDateEditor);
                    startDateSpinner.setValue(new Date());

                    JPanel startDatePanel = new JPanel();
                    startDatePanel.add(new JLabel("대여 시작일:"));
                    startDatePanel.add(startDateSpinner);
                    //Spinner로 일수 오르고 내리기 가능 

                    int result = JOptionPane.showConfirmDialog(frame, startDatePanel, "대여 시작일 선택", JOptionPane.OK_CANCEL_OPTION);
                    if (result == JOptionPane.OK_OPTION) {
                        Date startDate = (Date) startDateSpinner.getValue();
                        Calendar endDateCalendar = Calendar.getInstance();
                        endDateCalendar.setTime(startDate);
                        endDateCalendar.add(Calendar.DATE, 7); // 대여 기간은  대여기간일로부터 +7일 설정 

                        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
                        String rentalStartDate = dateFormat.format(startDate);
                        String rentalEndDate = dateFormat.format(endDateCalendar.getTime());
                        JOptionPane.showMessageDialog(frame, "대여가 완료되었습니다.\n대여일: " + rentalStartDate + " ~ " + rentalEndDate);
                    }
                }
            } else {
                JOptionPane.showMessageDialog(frame, "책을 선택하세요.");
            }
        });
        panel.add(rentButton, BorderLayout.SOUTH);

       
        frame.validate();
        frame.repaint();
    }

    private static void setButtonSize(JButton button) {
        Dimension buttonSize = new Dimension(100, 30); 
        button.setPreferredSize(buttonSize); 
    }
}

