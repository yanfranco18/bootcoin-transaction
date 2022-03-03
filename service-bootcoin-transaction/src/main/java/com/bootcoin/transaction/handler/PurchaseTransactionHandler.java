package com.bootcoin.transaction.handler;

import com.bootcoin.transaction.model.ApproveTransaction;
import com.bootcoin.transaction.model.PurchaseTransaction;
import com.bootcoin.transaction.model.dto.PurchaseTransactionDto;
import com.bootcoin.transaction.service.IPurchaseTransactionService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.time.LocalDate;

import static org.springframework.web.reactive.function.BodyInserters.fromValue;

@Slf4j
@RequiredArgsConstructor
@Component
public class PurchaseTransactionHandler {

    private final IPurchaseTransactionService service;

    @Autowired
    private Validator validator;

    @CircuitBreaker(name="bootcointransaction", fallbackMethod = "fallback")
    @TimeLimiter(name="bootcointransaction")
    public Mono<ServerResponse> findAll(ServerRequest request) {
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(service.findAll(), PurchaseTransactionDto.class);
    }

    @CircuitBreaker(name="bootcointransaction", fallbackMethod = "fallback")
    @TimeLimiter(name="bootcointransaction")
    public Mono<ServerResponse> findId(ServerRequest request) {
        String id = request.pathVariable("id");

        return service.findById(id)
                .flatMap(dto -> ServerResponse.status(HttpStatus.FOUND)
                        .contentType(MediaType.APPLICATION_JSON).bodyValue(dto))
                .switchIfEmpty(
                        ErrorResponse.buildBadResponse("The purchase transaction whith  id: ".concat(id).concat(" not found"), HttpStatus.NOT_FOUND))
                .onErrorResume(throwable ->
                        ErrorResponse.buildBadResponse(throwable.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR));
    }

    @CircuitBreaker(name="bootcointransaction", fallbackMethod = "fallback")
    @TimeLimiter(name="bootcointransaction")
    public Mono<ServerResponse> approveTransaction (ServerRequest request){
        Mono<ApproveTransaction> approve = request.bodyToMono(ApproveTransaction.class);
        return approve.flatMap(at -> {
            Errors errors = new BeanPropertyBindingResult(at, PurchaseTransaction.class.getName());
            validator.validate(at, errors);

            if (errors.hasErrors()) {
                return Flux.fromIterable(errors.getFieldErrors())
                        .map(fieldError -> "The field " + fieldError.getField() + " " + fieldError.getDefaultMessage())
                        .collectList()
                        .flatMap(list -> ServerResponse.badRequest().body(fromValue(list)));
            }else{
                if(at.getCreateDate()==null){
                    at.setCreateDate(LocalDate.now());
                }
                return service.createApproveTransaction(at).flatMap(app -> ServerResponse
                        .created(URI.create("/approveTransaction/".concat(app.getIdPurchaseTransaction())))
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(fromValue(app)));
            }
        });
    }

    public Mono<ServerResponse> create(ServerRequest request) {

        Mono<PurchaseTransaction> transaction = request.bodyToMono(PurchaseTransaction.class);

        return transaction.flatMap(t -> {
            Errors errors = new BeanPropertyBindingResult(t, PurchaseTransaction.class.getName());
            validator.validate(t, errors);

            if (errors.hasErrors()) {
                return Flux.fromIterable(errors.getFieldErrors())
                        .map(fieldError -> "El campo " + fieldError.getField() + " " + fieldError.getDefaultMessage())
                        .collectList()
                        .flatMap(list -> ServerResponse.badRequest().body(fromValue(list)));
            } else {
                if (t.getCreateDate() == null) {
                    t.setCreateDate(LocalDate.now());
                }
                t.setStatus(PurchaseTransaction.Status.PENDING);
                log.info("Post sendMessage...");

                return service.create(t).flatMap(mdb -> ServerResponse
                        .status(HttpStatus.CREATED)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(fromValue(mdb)));
            }
        });
    }

    //metodo para manejar el error
    private String fallback (HttpServerErrorException ex){
        return "Response 200, fallback method for error:  " + ex.getMessage();
    }
}
