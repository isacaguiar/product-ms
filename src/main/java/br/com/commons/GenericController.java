package br.com.commons;

import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import br.com.commons.exception.ApplicationException;
import br.com.commons.exception.ApplicationNotFoundException;

@SuppressWarnings("rawtypes")
public abstract class GenericController<E, D extends IDto, S extends GenericService> {

	private S service;

	public GenericController(S service) {
		this.service = service;
	}

	public S getService() {
		return this.service;
	}

	protected abstract Function<E, D> getDtoFromEntity();

	protected abstract Function<D, E> getEntityFromDto();

	@SuppressWarnings("unchecked")
	private E getEntityById(Long id) {
		Optional<E> optional = this.getService().findById(id);

		if (optional == null || (optional != null && !optional.isPresent()))
			throw new ApplicationNotFoundException("Entidade não encontrada para o id " + id.toString());
		return optional.get();
	}

	@SuppressWarnings("unchecked")
	protected ResponseEntity postEntity(@Valid @RequestBody D dto) {
		return ResponseEntity.ok(Optional.of((E) this.getService().save(Optional.of(dto).map(getEntityFromDto()).get()))
				.map(getDtoFromEntity()).get());
	}
	
	@SuppressWarnings("unchecked")
	protected ResponseEntity putEntity(@Valid @RequestBody D dto) {
		Optional.ofNullable(dto.getId()).orElseThrow(
				() -> new ApplicationNotFoundException("Não é possível realizar update de objeto sem identificador"));

		Optional.ofNullable(this.getService().findById(dto.getId())).orElseThrow(
				() -> new ApplicationNotFoundException("Entidade não encontrada para o id " + dto.getId().toString()));

		return ResponseEntity.ok(this.getService().save(Optional.of(dto).map(getEntityFromDto()).get()));
	}

	@SuppressWarnings("unchecked")
	protected ResponseEntity putEntity(@PathVariable Long id, @Valid @RequestBody D dto) {
		Optional.ofNullable(id).orElseThrow(
				() -> new ApplicationException("Não é possível realizar update de objeto sem identificador"));

		Optional.ofNullable(this.getService().findById(id)).orElseThrow(
				() -> new ApplicationNotFoundException("Entidade não encontrada para o id " + id.toString()));

		dto.setId(id);
		return ResponseEntity.ok(this.getService().save(Optional.of(dto).map(getEntityFromDto()).get()));
	}
	
	public ResponseEntity findById(@PathVariable Long id) {
		E entity = getEntityById(id);
		return ResponseEntity.ok(Optional.of(entity).map(getDtoFromEntity()).get());
	}

	@SuppressWarnings("unchecked")
	protected ResponseEntity listAll() {
		return ResponseEntity
				.ok(this.getService().findAll().stream().map(getDtoFromEntity()).collect(Collectors.toList()));
	}
	
	@SuppressWarnings("unchecked")
	public ResponseEntity deleteEntity(@PathVariable Long id) {
		E entity = getEntityById(id);
		this.getService().delete(entity);
		return ResponseEntity.ok().build();
	}

}