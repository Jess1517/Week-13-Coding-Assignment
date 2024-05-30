package pet.store.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import pet.store.controller.model.PetStoreData;
import pet.store.controller.model.PetStoreEmployee;
import pet.store.service.PetStoreService;

@RestController 
@RequestMapping("/pet_store")
@Slf4j
public class PetStoreController {
	
	@Autowired 
	private PetStoreService petStoreService; 

	@PostMapping("/pet_store")
	@ResponseStatus(HttpStatus.CREATED)
	public PetStoreData insertPetStoreData(@RequestBody PetStoreData petStoreData) {
		log.info("Received POST request to create a pet store: {}", petStoreData); 
		PetStoreData savedPetStoreData = petStoreService.savePetStore(petStoreData); 
		log.info("Pet store created successfully: {}", savedPetStoreData);
		return savedPetStoreData; 
				
}
	
	@PutMapping("/{petStoreId}")
	public PetStoreData updatePetStore(@PathVariable Long petStoreId, @RequestBody PetStoreData petStoreData) {
		log.info("Received request to update pet store with Id: {}", petStoreId); 
		
		petStoreData.setPetStoreId(petStoreId);
		PetStoreData updatedPetStoreData = petStoreService.savePetStore(petStoreData);
		
		log.info("Pet store with Id: {} updated successfully", petStoreId);
		return updatedPetStoreData; 
	}

	
	@PostMapping("/{petStoreId}/employee")
	@ResponseStatus(code = HttpStatus.CREATED)
	public PetStoreEmployee insertEmployee(@PathVariable Long petStoreId, @RequestBody PetStoreEmployee petStoreEmployee) {
		log.info("Creating employee {} for pet store with ID={}", petStoreEmployee, petStoreId); 
		return petStoreService.saveEmployee(petStoreId, petStoreEmployee); 
	}
	
	//List all Pet Stores //
	//1//
	@GetMapping()
	public List<PetStoreData> retrieveAllPetStores() {
		log.info("Retrieve all pet stores.");
		return petStoreService.retrieveAllPetStores(); 
	}

	@GetMapping("/{petStoreId}")
	public PetStoreData retrievePetStoreById(@PathVariable Long petStoreId) {
		log.info("Retrieving pet store by ID={}", petStoreId); 
		return petStoreService.retrievePetStoreById(petStoreId);
	}
	
	//DELETE PET STORE//
	@DeleteMapping("/{petStoreId}")
	public Map<String, String> deletePetStoreId(@PathVariable Long petStoreId) {
		log.info("Deleting pet store with ID={}", petStoreId);
		petStoreService.deletePetStoreById(petStoreId); 
		
		return Map.of("message", "Deletion of pet store with ID=" + petStoreId
				      + " was successful."); 
	}
}