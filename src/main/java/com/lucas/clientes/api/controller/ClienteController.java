package com.lucas.clientes.api.controller;

import com.lucas.clientes.domain.model.Cliente;
import com.lucas.clientes.domain.repository.ClienteRepository;
import com.lucas.clientes.domain.service.CadastroClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private CadastroClienteService cadastroCliente;

    @GetMapping
    public List<Cliente> listar(){
        //Mostra tudo
        return clienteRepository.findAll();
        //Usando implementação do JPA para mostrar somente nome, veja a interface ClienteRepository.java
        //return clienteRepository.findByNome("João da Silva");
        //Usando algo que contenha "si"
        //return clienteRepository.findByNomeContaining("Si");
    }
    @GetMapping("/{clienteID}")
    public ResponseEntity<Cliente> buscar(@PathVariable Long clienteID){
        Optional<Cliente> cliente = clienteRepository.findById(clienteID);

        if(cliente.isPresent()){
            return ResponseEntity.ok(cliente.get());
        }
        return ResponseEntity.notFound().build();
    }
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Cliente adicionar(@Valid @RequestBody Cliente cliente){
        return cadastroCliente.salvar(cliente);
    }
    @PutMapping("/{clienteId}")
    public ResponseEntity<Cliente> atualizar(@Valid @PathVariable Long clienteId, @RequestBody Cliente cliente){
        if(!clienteRepository.existsById(clienteId)){
            return ResponseEntity.notFound().build();
        }
        cliente.setId(clienteId);
        cliente = cadastroCliente.salvar(cliente);
        return ResponseEntity.ok(cliente);
    }
    @DeleteMapping("/{clienteId}")
    public ResponseEntity<Void> remover(@PathVariable Long clienteId){
        if(!clienteRepository.existsById(clienteId)){
            return ResponseEntity.notFound().build();
        }
        cadastroCliente.excluir(clienteId);
        return ResponseEntity.noContent().build();
    }
}