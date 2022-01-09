package co.com.sofka.questions.usecases;

import co.com.sofka.questions.model.EmailDTO;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Mono;

import java.util.function.Function;

@Service
@Validated
public class SendEmailUseCase implements Function<EmailDTO, Mono<Boolean>> {

    private final JavaMailSender javaMailSender;
    private static final String EMAIL_FROM = "soporteantonio7@gamil.com";
    private static final String EMAIL_SUBJECT = "Respuesta agregada a tu pregunta";

    public SendEmailUseCase(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Override
    public Mono<Boolean> apply(EmailDTO emailDTO) {
        try {
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setFrom(EMAIL_FROM);
            mailMessage.setTo(emailDTO.getEmailTo());
            mailMessage.setSubject(EMAIL_SUBJECT);
            mailMessage.setText(emailDTO.getBody());
            javaMailSender.send(mailMessage);

            return Mono.just(true);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return Mono.just(false);
        }
    }
}
