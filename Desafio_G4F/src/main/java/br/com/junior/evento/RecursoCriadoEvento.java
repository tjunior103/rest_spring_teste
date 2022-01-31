package br.com.junior.evento;

import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationEvent;

public class RecursoCriadoEvento extends ApplicationEvent {

    private static final long serialVersionUID = 1L;
    private HttpServletResponse response;
    private Integer id;

    public RecursoCriadoEvento(Object source, HttpServletResponse response, Integer id) {
        super(source);
        this.id = id;
        this.response = response;
    }

    public HttpServletResponse getResponse() {
        return response;
    }

    public Integer getCodigo() {
        return id;
    }

}
