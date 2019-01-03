package main.java.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import org.controlsfx.control.Rating;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.net.URL;
import java.util.Date;
import java.util.Properties;
import java.util.ResourceBundle;

public class SimulationEndController extends Controller implements Initializable {

    @FXML
    private Rating ratingBar;

    @FXML
    private TextArea textArea;

    @FXML
    public void sendOpinion() {
        final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";
        Properties props = System.getProperties();
        props.setProperty("mail.smtp.host", "smtp.gmail.com");
        props.setProperty("mail.smtp.socketFactory.class", SSL_FACTORY);
        props.setProperty("mail.smtp.socketFactory.fallback", "false");
        props.setProperty("mail.smtp.port", "465");
        props.setProperty("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.auth", "true");
        props.put("mail.debug", "true");
        props.put("mail.store.protocol", "pop3");
        props.put("mail.transport.protocol", "smtp");
        final String username = "filmhub.po@gmail.com";//
        final String password = "#filmhub2019";
        try {
            Session session = Session.getDefaultInstance(props,
                new Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });
            Message msg = new MimeMessage(session);

            msg.setFrom(new InternetAddress("filmhub@op.pl"));
            msg.setRecipients(Message.RecipientType.TO,
                InternetAddress.parse("joskar1999@gmail.com", false));
            msg.setSubject("FilmHub");
            msg.setText("Rating: " + ratingBar.getRating() + "\n" + "Opinia: " + textArea.getText());
            msg.setSentDate(new Date());
            Transport.send(msg);
            showNotification("FilmHub", "Dziękujemy za przesłanie opini");
        } catch (MessagingException e) {
            System.out.println("Erreur d'envoi, cause: " + e);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.initialize(location, resources);
    }
}
