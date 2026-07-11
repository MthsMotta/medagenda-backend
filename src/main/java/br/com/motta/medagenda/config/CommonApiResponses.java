package br.com.motta.medagenda.config;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@ApiResponses(value = {
        @ApiResponse(responseCode = "400", description = "Dados invalidos ou regra de negocio violada"),
        @ApiResponse(responseCode = "401", description = "Nao autenticado"),
        @ApiResponse(responseCode = "403", description = "Sem permissao para acessar este recurso"),
        @ApiResponse(responseCode = "404", description = "Recurso nao encontrado"),
        @ApiResponse(responseCode = "409", description = "Conflito - dado ja cadastrado")
})
public @interface CommonApiResponses {
}