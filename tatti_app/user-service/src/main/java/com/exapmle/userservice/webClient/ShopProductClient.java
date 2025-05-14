package com.exapmle.userservice.webClient;

import com.exapmle.userservice.dto.ProductUniqueDTO;
import com.exapmle.userservice.dto.ShopProductDTO;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
public class ShopProductClient {

    private final WebClient webClient;

    public ShopProductClient(@Qualifier("shopProductWebClient") WebClient webClient) {
        this.webClient = webClient;
    }

    public Mono<ShopProductDTO> getProductById(UUID id) {
        return webClient
                .get()
                .uri("/{id}", id)
                .retrieve()
                .bodyToMono(ShopProductDTO.class);
    }

    public Flux<ShopProductDTO> getProductsByShopId(UUID id) {
        return webClient
                .get()
                .uri("/shop/{id}", id)
                .retrieve()
                .bodyToFlux(ShopProductDTO.class);
    }

    public Mono<ShopProductDTO> addStandardProduct(ShopProductDTO shopProductDTO) {
        return webClient
                .post()
                .uri("/") // передаём только путь
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(shopProductDTO) // вот это — тело
                .retrieve()
                .bodyToMono(ShopProductDTO.class);
    }



    public Mono<ProductUniqueDTO> addUniqueProduct(ProductUniqueDTO shopProductDTO) {
        return webClient
                .post()
                .uri("/unique") // передаём только путь
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(shopProductDTO) // вот это — тело
                .retrieve()
                .bodyToMono(ProductUniqueDTO.class);
    }

}
