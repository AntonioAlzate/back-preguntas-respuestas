package co.com.sofka.questions.usecases;

import co.com.sofka.questions.model.EmailDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;

class SendEmailUseCaseTest {

    JavaMailSender javaMailSender;
    SendEmailUseCase sendEmailUseCase;
    private static final String EMAIL_FROM = "soporteantonio7@gamil.com";
    private static final String EMAIL_SUBJECT = "Respuesta agregada a tu pregunta";

    @BeforeEach
    public void setup() {
        javaMailSender = mock(JavaMailSender.class);
        sendEmailUseCase = new SendEmailUseCase(javaMailSender);
    }

    @Test
    void sendEmailValidation() {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom(EMAIL_FROM);
        mailMessage.setTo("emailDTO.getEmailTo()");
        mailMessage.setSubject(EMAIL_SUBJECT);
        mailMessage.setText("emailDTO.getBody()");

        doNothing().when(javaMailSender).send(mailMessage);

        EmailDTO emailDTO = new EmailDTO("email", "body");

        StepVerifier.create(sendEmailUseCase.apply(emailDTO))
                .expectNextMatches(result -> {
                    assert result.equals(true);
                    return true;
                }).verifyComplete();

    }

}