package co.com.sofka.questions.model;

public class EmailDTO {

    private String emailTo;

    private String body;

    public EmailDTO(String emailTo, String body) {
        this.emailTo = emailTo;
        this.body = body;
    }

    public String getEmailTo() {
        return emailTo;
    }

    public void setEmailTo(String emailTo) {
        this.emailTo = emailTo;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
