package pet.store.controller.model;

import java.util.HashSet;
import java.util.Set;
import lombok.Data;
import lombok.NoArgsConstructor;
import pet.store.entity.PetStore;

@Data
@NoArgsConstructor
public class PetStoreData {
	
	
	private Long petStoreId; 
	private String petStoreName; 
	private String petStoreAddress; 
	private String petStoreCity;
	private String petStoreState;
	private String petStoreZip; 
	private String petStorePhone; 
	private Set<PetStoreCustomer> customers = new HashSet<>(); 
	private Set<PetStoreEmployee> employees = new HashSet<>(); 
	

	public PetStoreData(PetStore petStore) {
		petStoreId = petStore.getPetStoreId();
		petStoreName = petStore.getPetStoreName();
		petStoreAddress = petStore.getPetStoreAddress();
		petStoreCity = petStore.getPetStoreCity();
		petStoreState = petStore.getPetStoreState();
		petStoreZip = petStore.getPetStoreZip(); 
		petStorePhone = petStore.getPetStorePhone(); 
	}

	public Long getPetStoreId() {
		return petStoreId;
	}
	public void setPetStoreId(Long petStoreId) {
		this.petStoreId = petStoreId;
	}
	public String getPetStoreName() {
		return petStoreName;
	}
	public void setPetStoreName(String petStoreName) {
		this.petStoreName = petStoreName;
	}
	public String getPetStoreAddress() {
		return petStoreAddress;
	}
	public void setPetStoreAddress(String petStoreAddress) {
		this.petStoreAddress = petStoreAddress;
	}
	public String getPetStoreCity() {
		return petStoreCity;
	}
	public void setPetStoreCity(String petStoreCity) {
		this.petStoreCity = petStoreCity;
	}
	public String getPetStoreState() {
		return petStoreState;
	}
	public void setPetStoreState(String petStoreState) {
		this.petStoreState = petStoreState;
	}
	public String getPetStoreZip() {
		return petStoreZip;
	}
	public void setPetStoreZip(String petStoreZip) {
		this.petStoreZip = petStoreZip;
	}
	public String getPetStorePhone() {
		return petStorePhone;
	}
	public void setPetStorePhone(String petStorePhone) {
		this.petStorePhone = petStorePhone;
	}




















}
