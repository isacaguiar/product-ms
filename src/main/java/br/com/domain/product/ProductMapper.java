package br.com.domain.product;

import org.springframework.stereotype.Component;

import br.com.util.Mapper;

import java.util.function.Function;

@Component
public class ProductMapper {

	public Function<ProductDTO, Product> getEntityFromDtoFunction(){
        return  dto -> Mapper.map(dto, Product.class);
    }

    public Function<Product, ProductDTO> getDtoFromEntityFunction(){
        return entity -> Mapper.map(entity, ProductDTO.class);
    }
}
