package pet.store.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import pet.store.controller.model.PetStoreData;
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

}