package br.com.domain.product;


import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.commons.GenericService;
import br.com.commons.exception.ApplicationNotFoundException;

@Service
public class ProductService extends GenericService<Product, Long, ProductRepository> {
	
	@Autowired
    public ProductService(ProductRepository repository) {
        super(repository, Product.class);
    }
	
	public List<Product> findAll() {
		return getRepository().findAll();
	}
	
	public Optional<Product> findById(Long id) {
		Optional<Product> product = getRepository().findById(id);
		if (product.isPresent()) {
			return product;
		} else {
			//throw new ApplicationNotFoundException("Registro não encontrado!");
			return null;
		}
	}
	
	public Product save(Product obj) {
		return getRepository().save(obj);
	}
	
	public boolean deleteById(Long id) {
		try {
			getRepository().deleteById(id);
		} catch(NoSuchElementException nsee) {
			return false;
		}
		return true;
	}
	
	public Product update(Long id, Product obj) {
		Optional<Product> tutorialData = getRepository().findById(id);
		if (tutorialData.isPresent()) {
			Product _product = tutorialData.get();
			_product.setName(obj.getName());
			_product.setDescription(obj.getDescription());
			_product.setPrice(obj.getPrice());
			return getRepository().save(_product);
		} else {
			throw new ApplicationNotFoundException("Registro não encontrado!");
		}
	}

}
