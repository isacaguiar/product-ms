package br.com.domain.product;

import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import br.com.commons.IDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class ProductDTO implements IDto {

	@Id
	private Long id;

	@NotEmpty(message = "{field.notempty}")
    @Length(max = 50, message = "{field.maxlength}")
    private String name;
	
	@NotEmpty(message = "{field.notempty}")
    @Length(max = 150, message = "{field.maxlength}")
    private String description;
	
	private Double price;

}
