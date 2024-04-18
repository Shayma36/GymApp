package Controllers;

import com.example.projet.Models.Cours;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.Properties;

public class ResrvationController {

    @FXML
    private TextField email;

    @FXML
    private Button btnannuler;

    @FXML
    private Button btnvalider;
    private Cours selectedCours;
    private clientController clientController;

    public void initData(Cours cours, clientController controller) {
        selectedCours = cours;
        clientController = controller;
    }

    @FXML
    void validation(ActionEvent event) {
        String userEmail = email.getText();

        if (!isValidEmail(userEmail)) {
            showAlert("Erreur", "Adresse email invalide.",
                    "Veuillez saisir une adresse email valide.", Alert.AlertType.ERROR);
            return;
        }

        boolean emailSent = sendConfirmationEmail(userEmail, selectedCours.getNom());

        if (emailSent) {
            showAlert("Confirmation", "Réservation effectuée avec succès.",
                    "Un email de confirmation a été envoyé à " + userEmail, Alert.AlertType.INFORMATION);
            if (selectedCours != null && selectedCours.getPlaces() > 0) {
                int newPlaces = selectedCours.getPlaces() - 1;
                clientController.updateCoursePlaces(selectedCours.getId(), newPlaces);
                clientController.showCours();
            }
        } else {
            showAlert("Erreur", "Échec de la réservation.",
                    "Échec de l'envoi de l'email de confirmation. Veuillez réessayer plus tard.", Alert.AlertType.ERROR);
        }
    }

    @FXML
    void annuler(ActionEvent event) {
        email.clear();


        Stage stage = (Stage) btnannuler.getScene().getWindow();
        stage.close();
    }



    private boolean sendConfirmationEmail(String userEmail, String coursNom) {

        Properties props = new Properties();
        //Enable authentication
        props.put("mail.smtp.auth", "true");
        //Set TLS encryption enabled
        props.put("mail.smtp.starttls.enable", "true");
        //Set SMTP host
        props.put("mail.smtp.host", "smtp.gmail.com");
        //Set smtp port
        props.put("mail.smtp.port", "587");

        //Your gmail address
        String fromEmail = "rihab.kefi@esprit.tn";
        //Your gmail password

        String password = "233AMT1914";
        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            @Override
            protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
                return new javax.mail.PasswordAuthentication(fromEmail, password);
            }
        });

        try {
            MimeMessage msg = new MimeMessage(session);
            msg.addHeader("Content-type", "text/HTML; charset=UTF-8");
            msg.addHeader("format", "flowed");
            msg.addHeader("Content-Transfer-Encoding", "8bit");

            msg.setFrom(new InternetAddress(fromEmail, "NoReply"));
            msg.setReplyTo(InternetAddress.parse(fromEmail, false));

            msg.setSubject("Confirmation de réservation", "UTF-8");

            // Mettre à jour le texte du message pour inclure le nom du cours
            String messageText = "Votre réservation a été effectuée avec succès au cours : " + coursNom + ".";
            msg.setText(messageText, "UTF-8");

            msg.setSentDate(new Date());
            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(userEmail, false));
            Transport.send(msg);

            System.out.println("L'email a été envoyé avec succès.");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }}

    @FXML
    void mailtext(ActionEvent event) {
        String userEmail = email.getText();

        if (isValidEmail(userEmail)) {
            btnvalider.setDisable(false);
        } else {
            btnvalider.setDisable(true);
        }
    }

    private boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        return email.matches(emailRegex);
    }

    private void showAlert(String title, String header, String content, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }


}
