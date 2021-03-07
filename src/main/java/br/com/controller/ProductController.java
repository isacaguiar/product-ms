package br.com.controller;

import java.util.function.Function;
import java.util.stream.Collectors;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.commons.CustomErrorResponse;
import br.com.commons.GenericController;
import br.com.domain.product.Product;
import br.com.domain.product.ProductDTO;
import br.com.domain.product.ProductMapper;
import br.com.domain.product.ProductService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;


@RestController
@RequestMapping(value = "/products")
public class ProductController extends GenericController<Product, ProductDTO, ProductService> {
	
	@Autowired
	private ProductMapper mapper;
	
	public ProductController(ProductService service) {
		super(service);
	}

	@Override
    protected Function<Product, ProductDTO> getDtoFromEntity() {
        return mapper.getDtoFromEntityFunction();
    }

    @Override
    protected Function<ProductDTO, Product> getEntityFromDto() {
        return mapper.getEntityFromDtoFunction();
    }
    
	@SuppressWarnings("rawtypes")
	@ApiOperation(value = "Criação de um produto", response=Product.class, notes = "Essa operação cria um novo produto.")
	@PostMapping
	@Transactional
	@ApiResponses(value= {@ApiResponse(code=400, message="Not Found",response=CustomErrorResponse.class)})
	public ResponseEntity createProduct(@Valid @RequestBody ProductDTO dto) {
		return postEntity(dto);
	}
	
	//@ApiOperation(value = "Atualização de um produto", notes = "Essa operação atualiza um registro.")
	//@PutMapping
	//@Transactional
	@SuppressWarnings("rawtypes")
	public ResponseEntity updateProduct(@Valid @RequestBody ProductDTO dto) {
		return putEntity(dto);
	}
	
	@SuppressWarnings("rawtypes")
	@ApiOperation(value = "Atualização de um produto", response=Product.class, notes = "Essa operação atualiza um registro.")
	@PutMapping(value = "/{id}")
	@Transactional
	@ApiResponses(value= {@ApiResponse(code=400, message="Not Found",response=CustomErrorResponse.class),
							@ApiResponse(code=404, message="Produto não encontrado",response=CustomErrorResponse.class)})
	public ResponseEntity updateProduct(@PathVariable Long id, @Valid @RequestBody ProductDTO dto) {
		return putEntity(id, dto);
	}
	
	@SuppressWarnings("rawtypes")
	@ApiOperation(value = "Busca de um produto por ID", notes = "Essa operação localiza um produto a partir do ID.")
	@GetMapping("/{id}")
	public ResponseEntity findProductById(@PathVariable Long id) {
		return findById(id);
	}
	
	@SuppressWarnings("rawtypes")
	@ApiOperation(value = "Lista de produtos", notes = "Essa operação retorna todos os produtos.")
	@GetMapping
	public ResponseEntity listAllProduct() {
		return listAll();
	}
	
	@SuppressWarnings("rawtypes")
	@ApiOperation(value = "Deleção de um produto", notes = "Essa operação deleta um produt.")
	@DeleteMapping("/{id}")
	@ApiResponses(value= {@ApiResponse(code=400, message="Not Found",response=CustomErrorResponse.class),
							@ApiResponse(code=404, message="Produto não encontrado",response=CustomErrorResponse.class)})
	public ResponseEntity deleteProduct(@PathVariable Long id) {
		return deleteEntity(id);
	}
    
    @ApiOperation(value="Lista de produtos filtrados", response=Product.class, notes="Essa operação lista os produtos de acordo com o filtro informado.")
	@ApiResponses(value= {@ApiResponse(code=200,message="Retorna o produto cadastrado",response=Product.class),
							@ApiResponse(code=500,message="Caso tenhamos algum erro vamos retornar uma Exception",response=CustomErrorResponse.class)})
    @GetMapping("/search")
    public ResponseEntity<?> listAll(@RequestParam(value = "q", required = false) String texto, 
						    			@RequestParam(value = "min_price", required = false) Double minPrice,  
						    			@RequestParam(value="max_price", required = false) Double maxPrice){
        return ResponseEntity.ok(this.getService()
                .findAll().stream().filter(obj -> { return filterSearch(obj, texto, minPrice, maxPrice); })
                .map(getDtoFromEntity()).collect(Collectors.toList()));
    }
    
    private boolean filterSearch(Product obj, String texto, Double minPrice, Double maxPrice) {
    	return ( (texto == null || (texto != null && (obj.getName().contains(texto) || obj.getDescription().contains(texto))))
   			 && ((minPrice == null && maxPrice == null) || (minPrice != null && maxPrice != null && obj.getPrice() >= minPrice && obj.getPrice() <= maxPrice) ));
    }
    
}
