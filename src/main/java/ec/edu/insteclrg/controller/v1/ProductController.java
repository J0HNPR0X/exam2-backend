package ec.edu.insteclrg.controller.v1;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ec.edu.insteclrg.domain.Product;
import ec.edu.insteclrg.dto.ApiResponseDTO;
import ec.edu.insteclrg.dto.ProductoDTO;
import ec.edu.insteclrg.service.crud.ProductService;

@RestController
@RequestMapping("/api/v1.0/Producto")
public class ProductController {
	
	@Autowired
	ProductService service;
	
	@PostMapping
	public ResponseEntity<Object> guardar(@RequestBody ProductoDTO dto) {
		service.save(dto);
		return new ResponseEntity<>(new ApiResponseDTO<>(true, null), HttpStatus.CREATED);
	}

	@PutMapping
	public ResponseEntity<Object> actualizar(@RequestBody ProductoDTO dto) {
		service.update(dto);
		return new ResponseEntity<>(new ApiResponseDTO<>(false, null), HttpStatus.CREATED);
	}

	@GetMapping
	public ResponseEntity<Object> listar() {
		List<ProductoDTO> list = service.findAll(new ProductoDTO());
		if (!list.isEmpty()) {
			ApiResponseDTO<List<ProductoDTO>> response = new ApiResponseDTO<>(true, list);
			return (new ResponseEntity<Object>(response, HttpStatus.OK));
		} else {
			return new ResponseEntity<>(new ApiResponseDTO<>(false, null), HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping(path = "{id}")
	public ResponseEntity<Object> buscar(@PathVariable Long id) {
		ProductoDTO dto = new ProductoDTO();
		dto.setId(id);
		Optional<Product> domain = service.find(dto);
		if (!domain.isEmpty()) {
			dto = service.mapToDto(domain.get());
			return new ResponseEntity<>(new ApiResponseDTO<>(true, dto), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(new ApiResponseDTO<>(false, null), HttpStatus.NOT_FOUND);
		}
	}

	@DeleteMapping(path = "{id}")
	public ResponseEntity<Object> eliminar(@RequestBody Long id) {
		ProductoDTO dto = new ProductoDTO();
		dto.setId(id);
		Optional<Product> productOptional = service.find(dto);
		
		if(productOptional.isPresent()) {
			service.delete(dto);
			return new ResponseEntity<>(new ApiResponseDTO<>(true, dto), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(new ApiResponseDTO<>(false, null), HttpStatus.NOT_FOUND);
		}
	}
}
