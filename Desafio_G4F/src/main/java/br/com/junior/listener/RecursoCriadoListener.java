package br.com.junior.listener;

import java.net.URI;

import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.junior.evento.RecursoCriadoEvento;


@Component
public class RecursoCriadoListener implements ApplicationListener<RecursoCriadoEvento> {

    @Override
    public void onApplicationEvent(RecursoCriadoEvento recursoCriadoEvento) {
        HttpServletResponse response = recursoCriadoEvento.getResponse();
        Integer id = recursoCriadoEvento.getCodigo();
        adicionarHeaderLocation(response, id);
    }

    private void adicionarHeaderLocation(HttpServletResponse response, Integer id) {
        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}").buildAndExpand(id).toUri();
        response.setHeader("Location", uri.toASCIIString());
    }
}
